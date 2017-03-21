package network.optimize.tool.response;

import java.util.List;

/**
 * 获取token
 */
public class GetTokenResponse extends BaseResponse {
	private String token;
	private String realName;
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
}
