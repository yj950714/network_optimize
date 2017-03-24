package network.optimize.tool.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import network.optimize.tool.constant.RemoteServerConstant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SftpClient {
	
	private final Logger logger = LoggerFactory.getLogger(SftpClient.class); 

	/** Sftp */ 
	ChannelSftp sftp = null; 
	/** 主机 */ 
	private String host = RemoteServerConstant.REMOTE_SERVER_HOST; 
	/** 端口 */ 
	private int port = RemoteServerConstant.REMOTE_SERVER_PORT; 
	/** 用户名 */ 
	private String username = RemoteServerConstant.REMOTE_SERVER_USERNAME; 
	/** 密码 */ 
	private String password = RemoteServerConstant.REMOTE_SERVER_PASSWORD; 
	
	/** 
	* 构造函数            
	*/ 
	public SftpClient(){  
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
	public SftpClient(String host, int port, String username,String password){ 
		this.host = host; 
	    this.port = port; 
	    this.username = username; 
	    this.password = password; 
	}
	
	/** 
	* 连接sftp服务器 
	*            
	* @throws Exception      
	*/ 
	public void connect() throws Exception { 
		JSch jsch = new JSch(); 
		Session sshSession = jsch.getSession(this.username, this.host, this.port); 
		logger.debug(SftpClient.class + "Session created."); 

		sshSession.setPassword(password); 
		Properties sshConfig = new Properties(); 
		sshConfig.put("StrictHostKeyChecking", "no"); 
		sshSession.setConfig(sshConfig); 
		sshSession.connect(20000); 
		logger.debug(SftpClient.class + " Session connected."); 

		logger.debug(SftpClient.class + " Opening Channel."); 
		Channel channel = sshSession.openChannel("sftp"); 
		channel.connect(); 
		this.sftp = (ChannelSftp) channel; 
		logger.debug(SftpClient.class + " Connected to " + this.host + "."); 
	} 
	
    /**
     * Disconnect
     * @throws Exception
     */
    public void disconnect() throws Exception { 
        if(this.sftp != null){ 
            if(this.sftp.isConnected()){ 
                this.sftp.disconnect(); 
            }else if(this.sftp.isClosed()){ 
            	logger.debug(SftpClient.class + " sftp is closed already"); 
            } 
        } 
    } 
    
    /** 
    * 上传单个文件 
    * 
    * @param directory 上传的目录 
    * @param uploadFile 要上传的文件           
    * @throws Exception      
    */ 
    public void upload(String directory, String uploadFile) throws Exception { 
    	this.sftp.cd(directory); 
    	File file = new File(uploadFile); 
    	this.sftp.put(new FileInputStream(file), file.getName()); 
    }
    
    /** 
    * 下载单个文件 
    * 
    * @param directory 下载目录 
    * @param downloadFile 下载的文件 
    * @param saveDirectory 存在本地的路径 
    *            
    * @throws Exception      
    */ 
    public void download(String directory, String downloadFile, String saveDirectory) throws Exception { 
    	String saveFile = saveDirectory + "//" + downloadFile; 
    	this.sftp.cd(directory); 
    	File file = new File(saveFile); 
    	this.sftp.get(downloadFile, new FileOutputStream(file)); 
    } 

    
}
