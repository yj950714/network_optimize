package network.optimize.tool.util;

import network.optimize.tool.client.SftpClient;
import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.exception.WebBackendException;

public class SftpClientUtil {

	/**
	 *  通过SFTP上传文件到服务器
	 */
	public static void sftpUpload(String directory, String uploadFile) throws WebBackendException{
		try{
			SftpClient client = new SftpClient();
			client.connect();
			client.upload(directory, uploadFile);
			client.disconnect();
		}
		catch (Exception e){
			throw new WebBackendException(ErrorCode.UPLOAD_TO_REMOTE_ERROR);
		}
	}
	
	/**
	 *  通过SFTP从服务器下载文件
	 */
	public static void sftpDownload(String directory, String downloadFile, String saveDirectory) throws WebBackendException{
		try {
			SftpClient client = new SftpClient();
			client.connect();
			client.download(directory, downloadFile, saveDirectory);
			client.disconnect();
		}
		catch (Exception e){
			throw new WebBackendException(ErrorCode.DOWNLOAD_FROM_REMOTE_ERROR);
		}
	}
}
