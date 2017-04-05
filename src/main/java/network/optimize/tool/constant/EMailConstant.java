package network.optimize.tool.constant;

/**
 * 邮件相关常量
 *
 */
public class EMailConstant {
	/**
	 * 服务器
	 */
	public static final String SMTP_HOST = "smtp.163.com";
	/**
	 * 发件邮箱
	 */
	public static final String PLATFORM_EMAIL = "network_optimize@163.com";
	
	/**
	 * 密码
	 */
	public static final String PLATFORM_EMAIL_PASSWORD = "Hellokitty1995";
	/**
	 * 平台后台邮件密送人
	 */
	public static final String[] PLATFORM_WATCHER_EMAILS = {"network_optimize@163.com"};
	/**
	 * 默认邮件格式
	 */
	public static final String DEFAULT_MESSAGE_TYPE = "text/html;charset=gb2312";
	/**
	 * 默认邮件脚注
	 */
	public static final String PLATFORM_EMAIL_FOOTER = 
			  "<br/>"
			+ "<br/>"
			+ "<br/>"
			+ "<br/>"
			+ "<footer>"
			+ "	<div class=\"container\">"
			+ "		<div class=\"row text-center\">"
			+ "			<h5>此邮件由Network Optimize后台自动发送，请勿回复</h5>"
			+ "         <HR style=\"border:3 double #987cb9\" width=\"100%\" SIZE=3>"
			+ "			<h5>2017.Network Optimize Tool</h5>" 
			+ "		</div>"
			+ "	</div>"
			+ "</footer>";
}
