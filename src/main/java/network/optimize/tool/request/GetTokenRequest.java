package network.optimize.tool.request;

import org.hibernate.validator.constraints.NotBlank;


/**
 * 获取token
 *
 */
public class GetTokenRequest {
	@NotBlank(message="{username.blank}")
	private String username;
	@NotBlank(message="{password.blank}")
	private String password;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
