package network.optimize.tool.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.constant.RemoteServerConstant;
import network.optimize.tool.exception.WebBackendException;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;

public class SshExecClient {

	/** 主机 */ 
	private String host = RemoteServerConstant.REMOTE_SERVER_HOST; 
	/** 端口 */ 
	private int port = RemoteServerConstant.REMOTE_SERVER_PORT; 
	/** 用户名 */ 
	private String username = RemoteServerConstant.REMOTE_SERVER_USERNAME; 
	/** 密码 */ 
	private String password = RemoteServerConstant.REMOTE_SERVER_PASSWORD; 
	
	private String standardDir = "cd " + RemoteServerConstant.REMOTE_SERVER_ROOT_DIRECTORY + "; ";
	
	/** 
	* 构造函数            
	*/ 
	public SshExecClient() throws Exception{
	}
	
	public String runCmd(String cmd) throws Exception { 
		Connection conn = new Connection(host, port);
		conn.connect();
		boolean isAuthenticated = conn.authenticateWithPassword(username, password);
		if (! isAuthenticated){
			throw new WebBackendException(ErrorCode.REMOTE_SERVER_CONNECT_ERROR);
		}
		Session session = conn.openSession();	
		session.execCommand(standardDir + cmd);
		String result=processStdout(session.getStdout(),"UTF-8");  
		System.out.println(result);
		if(result.isEmpty() || result==null){  
            result=processStdout(session.getStderr(),"UTF-8");  
        }  
		conn.close();
		session.close(); 
		return result;
    }

	/** 
	* 解析脚本执行返回的结果集 
	* @author Ickes 
	* @param in 输入流对象 
	* @param charset 编码 
	* @since V0.1 
	* @return 
	* 以纯文本的格式返回 
	*/  
	private String processStdout(InputStream in, String charset){  
	    InputStream    stdout = new StreamGobbler(in);  
	    StringBuffer buffer = new StringBuffer();;  
	    try {  
	        BufferedReader br = new BufferedReader(new InputStreamReader(stdout,charset));  
	        String line=null;  
	        while((line=br.readLine()) != null){  
	        	buffer.append(line+"\n");  
	        }  
	    } catch (UnsupportedEncodingException e) {  
	    	e.printStackTrace();  
	    } catch (IOException e) {  
	    	e.printStackTrace();  
	    }  
	    return buffer.toString();  
	}  
	
	public String runCmdJsch(String command) throws Exception{
		com.jcraft.jsch.Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();
		session = jsch.getSession(username, host ,port);
		if (session == null) {
			throw new Exception("session is null");
		}
		session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		session.connect(30000);
		try {
			//创建sftp通信通道
			channel = (Channel) session.openChannel("shell");
			channel.connect(1000);
			//获取输入流和输出流
			InputStream instream = channel.getInputStream();
			OutputStream outstream = channel.getOutputStream();
			//发送需要执行的SHELL命令，需要用\n结尾，表示回车
			outstream.write(command.getBytes());
			outstream.flush();
			//获取命令执行的结果
			String temp = "";
			if (instream.available() > 0) {
				byte[] data = new byte[instream.available()];
				int nLen = instream.read(data);
				
				if (nLen < 0) {
					throw new Exception("network error.");
				}
				//转换输出结果并打印出来
				temp = new String(data, 0, nLen,"UTF-8");
			}
		    outstream.close();
		    instream.close();
		    return temp;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
			return "";
		}
	}
}
