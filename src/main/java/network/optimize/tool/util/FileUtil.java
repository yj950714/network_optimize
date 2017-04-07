package network.optimize.tool.util;

import java.io.File;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

public class FileUtil {
	
	/**
	 *  获取文件后缀
	 */
	public static String getFileSuffix(String fileName){
		return fileName.substring(fileName.lastIndexOf(".")+1);
	}
	
	/**
	 * 上传文件的命名规范：
	 * datetimestamp.md5(filename).suffix
	 */
	public static String getUploadFileName(String fileName){
		return DatetimeUtil.getTimestamp()+"."+CommonUtil.getMd5(fileName);
	}
	
	/**
	 *  根据用户id,任务id，日期新建目录，返回新建的目录相对路径
	 */
	public static String getDirByUserTaskNoDate(Long userId, String batchTaskNo){
		// 获取今天的日期字符串
		String todayString = DatetimeUtil.getDateStr(new Date());
		// 获取相对路径
		String filePath = "/"+userId+"/"+batchTaskNo+"/"+todayString;
		return filePath;
	}
	
	/**
	 *  根据用户id新任务路径
	 */
	public static String getTaskDirByUserTaskNo(Long userId,String batchTaskNo) {
		// 获取相对路径
		String filePath = "/"+userId+"/"+batchTaskNo;
		return filePath;
	}

	/**
	 * 获取路径中的文件名
	 */
	public static String getFileNameFromPath(String path){
		return path.substring(path.lastIndexOf("/")+1);
	}
	
	/**
	 * 获取路径中的文件名, in windows
	 */
	public static String getFileNameFromPathW(String path){
		return path.substring(path.lastIndexOf("\\")+1);
	}
	
	
	/**
	 * 将文件保存至服务器并按规则生成新的文件名
	 * @param uploadfile 上传的文件
	 * @param uploadRootDir 上传的根目录
	 * @param uploadDir 上传的目录相对路径
	 * @return 最终目录中的文件名
	 * @throws Exception
	 */
	public static String uploadFile(MultipartFile uploadFile,String uploadRootDir,String uploadDir) throws Exception{
		//获得目录真实路径 如果目录不存在则创建文件夹
		File uploadFolder = new File(uploadRootDir+uploadDir);
		if(!uploadFolder.exists()){
			uploadFolder.mkdirs();
		}
		// 获取上传文件名
		String fileName = uploadFile.getOriginalFilename();
		// 获取后缀
		String suffix = getFileSuffix(fileName);
		String newFileName = FileUtil.getUploadFileName(fileName)+"."+suffix;
		// 获取保存上传文件的目录
		String uploadFilePath = uploadFolder + "/" + newFileName;
		// 保存文件
		File destFile = new File(uploadFilePath);
		uploadFile.transferTo(destFile);
		
		return destFile.getName();
	}
	

	/**
	 * 根据用户id获取/创建文件上传目录
	 * @param id
	 * @return
	 */
	public static String getFileDirByUserId(Long userId) {
		// 获取相对路径
		String filePath = "/"+userId;
		return filePath;
	}
	
	
	/**
	 * 删除文件
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}	
	}	

}
