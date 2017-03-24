package network.optimize.tool.controller;

import network.optimize.tool.entity.User;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.service.FileService;

import org.springframework.beans.factory.annotation.Autowired;
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
	 * 上传文件
	 * @throws Exception 
	 */
    @RequestMapping(value="/file/upload", method=RequestMethod.POST)
    BaseResponse uploadResult(@RequestAttribute("user") User user, @RequestParam("file") MultipartFile uploadfile) throws Exception {
    	BaseResponse response = fileService.uploadFile(user, uploadfile);
    	return response;
    }
}
