package network.optimize.tool.controller;

import network.optimize.tool.entity.User;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.info.FileInfo;
import network.optimize.tool.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileController {

	@Autowired
	FileService fileService;
	
	/**
	 * 接收上传到服务器的文件
	 * @throws Exception 
	 */
    @RequestMapping(value="/file/upload", method=RequestMethod.POST)
    BaseResponse uploadFile(@RequestAttribute("user") User user, @RequestParam("file") MultipartFile uploadfile) throws Exception {
    	BaseResponse response = fileService.uploadFile(user, uploadfile);
    	return response;
    }
    
    
	/**
	 * 获取一个用户的所有文件,由于有token，不用涉及id
	 * @throws Exception 
	 */
    @RequestMapping(value="/user/files", method=RequestMethod.GET)
    ListResponse<FileInfo> getFilesByUser(@RequestAttribute("user") User user) throws Exception {
    	ListResponse<FileInfo> response = fileService.getFilesByUser(user.getId());
    	return response;
    }  
    
}
