package network.optimize.tool.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.User;
import network.optimize.tool.entity.UserToken;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.UserMapper;
import network.optimize.tool.request.ForgetPasswordRequest;
import network.optimize.tool.request.GetTokenRequest;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.GetTokenResponse;
import network.optimize.tool.response.GetUserResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.info.UserInfo;
import network.optimize.tool.service.UserService;



@RestController
public class UserController {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserService userService;
	
	/**
	 * 获取用户信息列表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/admin/user",method=RequestMethod.GET)
	public ListResponse<UserInfo> getUserList() throws Exception{
		ListResponse<UserInfo> response = userService.getUserList();
		return response;
	}
	
	
	/**
	 * 获取单个用户信息
	 * @param id
	 * @return
	 * @throws WebBackendException
	 */
	@RequestMapping(value = "/admin/user/{id}",method=RequestMethod.GET)
	public GetUserResponse getUserInfo(@PathVariable Long id) throws WebBackendException {
		GetUserResponse response = userService.getUser(id);
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
     * 忘记密码-重置密码并发送邮件
     * @param request
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/user/forget_password",method=RequestMethod.POST)
    BaseResponse forgetPassword(@RequestBody @Valid ForgetPasswordRequest request, HttpServletResponse servletResponse) throws WebBackendException{
    	servletResponse.addHeader("Access-Control-Allow-Origin", "*");
    	BaseResponse response = userService.forgetPassword(request);
    	return response;
    }
	
}