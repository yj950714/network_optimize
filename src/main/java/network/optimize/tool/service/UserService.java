package network.optimize.tool.service;

import java.sql.Timestamp;
import java.util.List;

import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;

import network.optimize.tool.constant.EMailConstant;
import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.constant.NetworkOptimizeConstant;
import network.optimize.tool.entity.User;
import network.optimize.tool.entity.UserExample;
import network.optimize.tool.entity.UserToken;
import network.optimize.tool.entity.UserTokenExample;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.FileMapper;
import network.optimize.tool.mapper.UserMapper;
import network.optimize.tool.mapper.UserTokenMapper;
import network.optimize.tool.request.ForgetPasswordRequest;
import network.optimize.tool.request.GetTokenRequest;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.GetUserResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.info.UserInfo;
import network.optimize.tool.util.CheckUtil;
import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.MailUtil;
import network.optimize.tool.util.RowConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;


@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	@Autowired
	UserTokenMapper userTokenMapper;
	@Autowired
	FileMapper fileMapper;
	@Autowired
	Environment env;
	
	
	/**
	 * 获取用户列表
	 * @throws Exception 
	 */
	public ListResponse<UserInfo> getUserList() throws Exception {
		List<User> userList = userMapper.selectByExample(new UserExample());
		ListResponse<UserInfo> response = new ListResponse<UserInfo>(userList,new RowConverter<User,UserInfo>(){
				@Override
				@SuppressWarnings("null")
				public UserInfo convertRow (User user){
						UserInfo userInfo = new UserInfo();
						userInfo.setId(user.getId());
						userInfo.setUserName(user.getUserName());
						userInfo.setRealName(user.getRealName());
						userInfo.setEmail(user.getEmail());
						userInfo.setCreateTime(user.getCreateTime());
						return userInfo;
				}
		});
		return response;
	}
	
	/**
	 * 获取用户信息_包含密码
	 * @throws WebBackendException
	 */
	public User getUserInfo(Long id) throws WebBackendException {
		User user = userMapper.selectByPrimaryKey(id);
		if (user==null){
			throw new WebBackendException(ErrorCode.USER_NOT_FOUND);
		}
		return user;
	}
	
	/**
	 * 获取用户信息
	 * @throws WebBackendException
	 */
	public GetUserResponse getUser(Long id) throws WebBackendException {
		User user = userMapper.selectByPrimaryKey(id);
		if (user==null){
			throw new WebBackendException(ErrorCode.USER_NOT_FOUND);
		}
		GetUserResponse response = new GetUserResponse();
		response.setId(user.getId());
		response.setUserName(user.getUserName());
		response.setRealName(user.getRealName());
		response.setEmail(user.getEmail());
		response.setCreateTime(user.getCreateTime());
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
		userExample.or().andUserNameEqualTo(request.getUserName()).andPasswordEqualTo(CommonUtil.getMd5(request.getPassword()));
		List<User> userList = userMapper.selectByExample(userExample);
		return CommonUtil.getFirst(userList);
	}
	
	/**
	 * 忘记密码-重置为随机密码并发送邮件
	 * @param request
	 * @return
	 * @throws WebBackendException
	 */
	public BaseResponse forgetPassword(ForgetPasswordRequest request) throws WebBackendException{
		UserExample userExample = new UserExample();
		userExample.or().andEmailEqualTo(request.getEmail());
		User user = CommonUtil.getFirst(userMapper.selectByExample(userExample));
		if (user == null){
			throw new WebBackendException(ErrorCode.EMAIL_ERROR);
		}
		//发送邮件
		try{
			//生成随机密码并发送邮件
			String newPassword = CheckUtil.getRandomString(NetworkOptimizeConstant.COMMON_PASSWORD_LENGTH);
			user.setPassword(CommonUtil.getMd5(newPassword));
			userMapper.updateByPrimaryKey(user);
			MailUtil.sendPlatFormMessage(user.getEmail(), null, EMailConstant.PLATFORM_WATCHER_EMAILS, "邮件重置密码", "<h3>"+user.getRealName()+" 您好<br/>以下为您的新密码,请妥善保管:</h3><br/><h2>"+newPassword+"</h2>");
		}catch (MessagingException e){
			throw new WebBackendException(ErrorCode.EMAIL_FAIL);
		}
		return new BaseResponse();
	}
}
