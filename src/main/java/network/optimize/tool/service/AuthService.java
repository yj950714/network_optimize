package network.optimize.tool.service;

import network.optimize.tool.mapper.AuthMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * 权限
 *
 */
@Service
public class AuthService {
	@Autowired
	AuthMapper authMapper;

	/**
	 * 判断用户是否有相应权限
	 */
	public Integer countUserAuth(Long userId, String url) {
		return 1;
	}
	

	
}
