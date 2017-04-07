package network.optimize.tool.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.constant.NetworkOptimizeConstant;
import network.optimize.tool.entity.File;
import network.optimize.tool.entity.FileExample;
import network.optimize.tool.entity.FileType;
import network.optimize.tool.entity.FileTypeExample;
import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.FileMapper;
import network.optimize.tool.mapper.FileTypeMapper;
import network.optimize.tool.request.ChangeUserFileRequest;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.info.FileInfo;
import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.FileUtil;
import network.optimize.tool.util.RowConverter;

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
		FileExample fileExample = new FileExample();
		fileExample.or().andUserIdEqualTo(user.getId());
		
		File file = new File();
		file.setFileName(saveFileName);
		file.setFileIdToUser(fileMapper.countByExample(fileExample)+1);
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
					fileInfo.setFileIdToUser(file.getFileIdToUser());
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
	
	public BaseResponse changeUserFile(User user, ChangeUserFileRequest request) throws WebBackendException{
		if (request.getAction().equals(NetworkOptimizeConstant.FILE_DELETE)){
			//找到该用户的所有文件列表
			FileExample fileExample = new FileExample();
			fileExample.or().andUserIdEqualTo(user.getId());
			List<File> fileList = fileMapper.selectByExample(fileExample);
			//找到要操作的文件
			fileExample.clear();
			fileExample.or().andFileIdToUserEqualTo(request.getFile_Id()).andUserIdEqualTo(user.getId());
			File fileToDelete = CommonUtil.getFirst(fileMapper.selectByExample(fileExample));
			if (fileToDelete==null){
				throw new WebBackendException(ErrorCode.FILE_NOT_EXIST);
			}
			//删除要删除的文件
			FileUtil.deleteFile(fileToDelete.getPosition() + fileToDelete.getFileName());
			fileMapper.deleteByPrimaryKey(fileToDelete.getId());
			//修正每一个显示给用户的ID大于删除文件ID的记录，保证用户看到的ID连续
			for (File eachFile : fileList){
				if (eachFile.getFileIdToUser()>fileToDelete.getFileIdToUser()){
					eachFile.setFileIdToUser(eachFile.getFileIdToUser()-1);
					fileMapper.updateByPrimaryKey(eachFile);
				}
			}
			
		}
		else if (request.getAction().equals(NetworkOptimizeConstant.FILE_CHANGE)){
			FileExample fileExample = new FileExample();
			fileExample.or().andFileIdToUserEqualTo(request.getFile_Id()).andUserIdEqualTo(user.getId());
			File file = CommonUtil.getFirst(fileMapper.selectByExample(fileExample));
			if (file==null){
				throw new WebBackendException(ErrorCode.FILE_NOT_EXIST);
			}
			if (request.getFile_Name()!=null && !request.getFile_Name().isEmpty()){
				file.setFileNameToUser(request.getFile_Name());
				fileMapper.updateByPrimaryKey(file);
			}
		}
		return new BaseResponse();
	}
	
}
