package network.optimize.tool.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import network.optimize.tool.interceptor.SecurityInterceptor;
import network.optimize.tool.service.AuthService;
import network.optimize.tool.service.UserService;

/**
 * 拦截器定义
 *
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	UserService userService;
	@Autowired
	AuthService authService;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		// 添加检查权限的拦截器
		String[] excludePatterns = new String[]{"/",
												"/user/get_token"};
		registry.addInterceptor(new SecurityInterceptor(userService,authService)).excludePathPatterns(excludePatterns);
		super.addInterceptors(registry);
	}
}
