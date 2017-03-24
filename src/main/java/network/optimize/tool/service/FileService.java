package network.optimize.tool.service;

import java.util.Date;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.File;
import network.optimize.tool.entity.FileType;
import network.optimize.tool.entity.FileTypeExample;
import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.FileTypeMapper;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Autowired
	FileTypeMapper fileTypeMapper;
	@Autowired
	Environment env;
	
	private Long getFileTypeId(String fileExtension) throws WebBackendException{
		FileTypeExample fileTypeExample = new FileTypeExample();
		fileTypeExample.or().andExtensionEqualTo(fileExtension);
		FileType fileType = CommonUtil.getFirst(fileTypeMapper.selectByExample(fileTypeExample));
		if (fileType == null){
			throw new WebBackendException(ErrorCode.FILE_TYPE_ERROR);
		}
		return fileType.getId();
	}
	
	
	/**
	 * 上传文件
	 */
	public BaseResponse uploadFile(User user, MultipartFile uploadFile) throws Exception {
		//获取上传根目录
		String uploadRootDir = env.getProperty("network_optimize.task.uploadDir");
		//获取相对路径
		String uploadDir = FileUtil.getFileDirByUserId(user.getId());
		//获取文件类型
		Long fileTypeId = getFileTypeId(FileUtil.getFileSuffix(uploadFile.getOriginalFilename()));
		//保存文件
		String saveFileName = FileUtil.uploadFile(uploadFile, uploadRootDir, uploadDir);
		//上传的文件信息写入数据库
		File file = new File();
		file.setFileName(saveFileName);
		file.setPosition(uploadRootDir + uploadDir + "/");
		file.setFileTypeId(fileTypeId);
		file.setUserId(user.getId());
		file.setUpdateTime(new Date());
		
		return new BaseResponse();
	}
}
