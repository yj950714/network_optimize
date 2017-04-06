package network.optimize.tool.service;

import java.util.Date;
import java.util.List;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.File;
import network.optimize.tool.entity.FileExample;
import network.optimize.tool.entity.FileType;
import network.optimize.tool.entity.FileTypeExample;
import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.FileMapper;
import network.optimize.tool.mapper.FileTypeMapper;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.info.FileInfo;
import network.optimize.tool.response.info.UserInfo;
import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.FileUtil;
import network.optimize.tool.util.RowConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

	@Autowired
	FileTypeMapper fileTypeMapper;
	@Autowired
	FileMapper fileMapper;
	@Autowired
	Environment env;
	
	
	/**
	 * 根据文件扩展名获取文件类型id
	 * @param fileExtension
	 * @return
	 * @throws WebBackendException
	 */
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
	 * 用户上传文件到服务器
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
		file.setFileNameToUser(uploadFile.getOriginalFilename());
		file.setFileSize(uploadFile.getSize());
		file.setPosition(uploadRootDir + uploadDir + "/");
		file.setFileTypeId(fileTypeId);
		file.setUserId(user.getId());
		file.setUpdateTime(new Date());
		fileMapper.insert(file);
		return new BaseResponse();
	}
	
	/**
	 * 获取一个用户的文件列表
	 * @throws Exception 
	 */
	public ListResponse<FileInfo> getFilesByUser(Long id) throws Exception{
		FileExample fileExample = new FileExample();
		fileExample.or().andUserIdEqualTo(id);
		List<File> fileList = fileMapper.selectByExample(fileExample);
		ListResponse<FileInfo> response = new ListResponse<FileInfo>(fileList, new RowConverter<File,FileInfo>(){
			@Override
			@SuppressWarnings("null")
			public FileInfo convertRow (File file){
					FileInfo fileInfo = new FileInfo();
					fileInfo.setId(file.getId());
					fileInfo.setUserId(file.getUserId());
					fileInfo.setFileTypeId(file.getFileTypeId());
					fileInfo.setFileTypeName(fileTypeMapper.selectByPrimaryKey(file.getFileTypeId()).getFileTypeName());
					fileInfo.setFileName(file.getFileName());
					fileInfo.setFileNameToUser(file.getFileNameToUser());
					fileInfo.setFileSize(file.getFileSize());
					fileInfo.setUpdateTime(file.getUpdateTime());
					return fileInfo;
			}
		});
		return response;
	}
	
}
