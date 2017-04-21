package network.optimize.tool.client;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Properties;

import network.optimize.tool.constant.RemoteServerConstant;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshExecClient {

	/** 主机 */ 
	private String host = RemoteServerConstant.REMOTE_SERVER_HOST; 
	/** 端口 */ 
	private int port = RemoteServerConstant.REMOTE_SERVER_PORT; 
	/** 用户名 */ 
	private String username = RemoteServerConstant.REMOTE_SERVER_USERNAME; 
	/** 密码 */ 
	private String password = RemoteServerConstant.REMOTE_SERVER_PASSWORD; 
	
	private ChannelExec channelExec; 
     
	private Session session = null;
	
	/** 
	* 构造函数            
	*/ 
	public SshExecClient() throws Exception{
		JSch jsch = new JSch();
		session = jsch.getSession(username, host, port);
		session.setPassword(password); 
		Properties config = new Properties(); 
        config.put("StrictHostKeyChecking", "no"); 
        session.setConfig(config); // 为Session对象设置properties  
        session.setTimeout(60000); // 设置timeout时间  
        session.connect(); // 通过Session建立链接 
	}
	
	/** 
	* 构造函数 
	* 
	* @param host 主机 
	* @param port 端口 
	* @param username 用户名 
	* @param password 密码 
	*            
	*/ 
	public SshExecClient(String host, int port, String username,String password) throws Exception{ 
		this.host = host; 
	    this.port = port; 
	    this.username = username; 
	    this.password = password; 
	    JSch jsch = new JSch();
		session = jsch.getSession(username, host, port);
		session.setPassword(password); 
		Properties config = new Properties(); 
        config.put("StrictHostKeyChecking", "no"); 
        session.setConfig(config); // 为Session对象设置properties  
        session.setTimeout(60000); // 设置timeout时间  
        session.connect(); // 通过Session建立链接 
	}
	
	public void runCmd(String cmd) throws Exception { 
			
        channelExec = (ChannelExec)session.openChannel("exec"); 
        channelExec.setCommand(cmd); 
        channelExec.setInputStream(null); 
        channelExec.setErrStream(System.err); 
        channelExec.connect(); 
        InputStream in = channelExec.getInputStream(); 
        BufferedReader reader = new BufferedReader(new InputStreamReader(in, Charset.forName("UTF-8"))); 
        String buf = null; 
        while ((buf = reader.readLine()) != null) 
        { 
            System.out.println(buf); 
        } 
        reader.close(); 
        channelExec.disconnect(); 
    }
	
	 public void close(){ 
		 session.disconnect(); 
	 } 
	
	
}
