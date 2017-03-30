package network.optimize.tool.request;

import org.hibernate.validator.constraints.Email;


/**
 * 忘记密码邮件发送请求
 * @author jie.yao
 *
 */
public class ForgetPasswordRequest {
	
	@Email
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
