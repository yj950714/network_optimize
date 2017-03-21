package network.optimize.tool.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.User;
import network.optimize.tool.entity.UserToken;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.UserMapper;
import network.optimize.tool.request.GetTokenRequest;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.GetTokenResponse;
import network.optimize.tool.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



@RestController
public class UserController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/admin/user",method=RequestMethod.GET)
	public List<User> getUserList() throws Exception{
		List<User> response = userService.getUserList();
		return response;
	}
	
	@RequestMapping(value = "/admin/user/{id}",method=RequestMethod.GET)
	public User getUserInfo(@PathVariable Long id) throws WebBackendException {
		User response = userService.getUser(id);
		return response;
	}
	
	/**
	 * 用户登录，返回token
	 * @throws WebBackendException 
	 */
    @RequestMapping(value="/user/get_token", method=RequestMethod.POST)
    GetTokenResponse getToken(HttpSession session,@RequestBody @Valid GetTokenRequest request,HttpServletRequest httpRequest) throws WebBackendException{
    	GetTokenResponse response = new GetTokenResponse();
    	User user = userService.validUser(request);
    	if(user==null){
    		throw new WebBackendException(ErrorCode.USER_PASSWORD_NOT_VALID);
    	} else {
    		//获取token
    		UserToken userToken = userService.getUserTokenByUser(session,user);
    		//返回token
    		response.setToken(userToken.getToken());
    		response.setRealName(user.getRealName());
    		return response;
    	}
    }
    
	/**
	 * 上传文件
	 * @throws Exception 
	 */
    @RequestMapping(value="/user/upload", method=RequestMethod.POST)
    BaseResponse uploadResult(@RequestAttribute("user") User user, @RequestParam("file") MultipartFile uploadfile) throws Exception {
    	BaseResponse response = userService.uploadFile(user, uploadfile);
    	return response;
    }
    
	
}