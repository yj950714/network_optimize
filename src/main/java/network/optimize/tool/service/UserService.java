package network.optimize.tool.service;

import java.sql.Timestamp;
import java.util.List;

import javax.servlet.http.HttpSession;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.User;
import network.optimize.tool.entity.UserExample;
import network.optimize.tool.entity.UserToken;
import network.optimize.tool.entity.UserTokenExample;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.UserMapper;
import network.optimize.tool.mapper.UserTokenMapper;
import network.optimize.tool.request.GetTokenRequest;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.FileUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserTokenMapper userTokenMapper;
	@Autowired
	Environment env;
	
	
	/**
	 * 获取用户列表
	 * @throws Exception 
	 */
	public List<User> getUserList() throws Exception {
		List<User> response = userMapper.selectByExample(new UserExample());
		return response;
	}
	
	/**
	 * 获取用户信息
	 * @throws WebBackendException
	 */
	public User getUser(Long id) throws WebBackendException {
		User response = userMapper.selectByPrimaryKey(id);
		if (response==null){
			throw new WebBackendException(ErrorCode.USER_NOT_FOUND);
		}
		return response;
	}
	

	/**
	 * 获取用户新token,返回token
	 */
	public String updateToken(HttpSession session,UserToken userToken){
		//设定token
		userToken.setToken(CommonUtil.getNewToken(session));
		updateTokenOverDue(userToken);
		return userToken.getToken();
	}
	
	/**
	 * 每次访问时延长用户token
	 */
	public void updateTokenOverDue(UserToken userToken){
		//token过期时间
		userToken.setTokenOverdue(new Timestamp(System.currentTimeMillis()+CommonUtil.parseIntegerDefaultZero(env.getProperty("token-overdue"))));
		//写入数据库
		userTokenMapper.updateByPrimaryKeySelective(userToken);
	}
	
	/**
	 * 根据用户获取User_token
	 */
	public UserToken getUserTokenByUser(HttpSession session,User user) {
		UserTokenExample userTokenExample = new UserTokenExample();
		userTokenExample.or().andUserIdEqualTo(user.getId());
		UserToken userToken = CommonUtil.getFirst(userTokenMapper.selectByExample(userTokenExample));
		//若无token则创建token
		if(userToken==null){
			userToken = new UserToken();
			userToken.setToken(CommonUtil.getNewToken(session));
			userToken.setUserId(user.getId());
			userTokenMapper.insertSelective(userToken);
		}
		//更新过期时间
		updateTokenOverDue(userToken);
		//否则返回token
		return userToken;
	}
	
	/**
	 * 根据token获取User_Token
	 */
	public UserToken getUserTokenByToken(String token) {
		UserTokenExample userTokenExample = new UserTokenExample();
		userTokenExample.or().andTokenEqualTo(token);
		List<UserToken> userTokenList = userTokenMapper.selectByExample(userTokenExample);
		return CommonUtil.getFirst(userTokenList);
	}
	
	/**
	 * 根据token获取用户
	 */
	public User getUserByToken(String token){
		User user = userMapper.selectByPrimaryKey(getUserTokenByToken(token).getUserId());
		return user;
	}
	
	/**
	 * 验证用户名密码
	 */
	public User validUser(GetTokenRequest request) {
		UserExample userExample = new UserExample();
		userExample.or().andUserNameEqualTo(request.getUsername()).andPasswordEqualTo(CommonUtil.getMd5(request.getPassword()));
		List<User> userList = userMapper.selectByExample(userExample);
		return CommonUtil.getFirst(userList);
	}
	
	/**
	 * 上传文件
	 */
	public BaseResponse uploadFile(User user, MultipartFile uploadfile) throws Exception {
		//获取上传根目录
		String uploadRootDir = env.getProperty("network_optimize.task.uploadDir");
		//获取相对路径
		String uploadDir = FileUtil.getFileDirByUserId(user.getId());
		//保存文件
		String saveFileName = FileUtil.uploadFile(uploadfile, uploadRootDir, uploadDir);
		
		return new BaseResponse();
	}
	
	
}
