package network.optimize.tool.interceptor;


import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.User;
import network.optimize.tool.entity.UserToken;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.service.AuthService;
import network.optimize.tool.service.UserService;

/**
 * 全局拦截器，检查用户是否有权限访问页面
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter{
	
	final Logger LOG = LoggerFactory.getLogger(getClass());
	
	private UserService userService;
	private AuthService authService;
	
	public SecurityInterceptor(UserService userService,AuthService authService) {
		this.userService = userService;
		this.authService = authService;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{		
		String url = request.getServletPath().replace(".html", "");
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		
		/* 记录访问时间 */
		if(LOG.isDebugEnabled()){
			long startTime = System.currentTimeMillis();
			request.setAttribute("requestStartTime", startTime);
		}
		
		/* 在requestParam中寻找token */
		String token=request.getParameter("token");
		/* 检验是否有权限访问该url */
		UserToken userToken= userService.getUserTokenByToken(token);
		//判断是用户是否存在且未过期,否则返回错误
		if(userToken==null||userToken.getTokenOverdue().before(new Date())){
			LOG.debug("[interceptor exception], sessionId:{} auth denied",sessionId);
			throw new WebBackendException(ErrorCode.TOKEN_ERROR);
		} else {
		//未过期则刷新过期时间
			userService.updateTokenOverDue(userToken);
		}
//		//找不到相应权限则拒绝
//		int authCnt = authService.countUserAuth(userToken.getUserId(),url);
//		if(authCnt==0) {
//			LOG.debug("[interceptor exception], sessionId:{} no auth",sessionId);
//			throw new WebBackendException(ErrorCode.AUTH_ERROR);
//		}
		//将用户放入session
		User user = userService.getUserInfo(userToken.getUserId());
		request.setAttribute("user", user);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception{
		/* 收集一次用户访问的log信息  */
		if(LOG.isDebugEnabled()){
			// 计算整个请求花费的时间
			long startTime = (Long)request.getAttribute("requestStartTime");
			long endTime = System.currentTimeMillis();
			long executeTime = endTime - startTime;
			
			String sessionId = request.getSession().getId();
			LOG.debug("[response info], sessionId:{}, status:{}, handler:{}, executeTime:{}ms",
					sessionId, response.getStatus(), handler, executeTime);
		}
	}

}
