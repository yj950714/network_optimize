package network.optimize.tool.request;

import org.hibernate.validator.constraints.NotBlank;


/**
 * 获取token
 *
 */
public class GetTokenRequest {
	@NotBlank(message="{username.blank}")
	private String userName;
	@NotBlank(message="{password.blank}")
	private String password;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
