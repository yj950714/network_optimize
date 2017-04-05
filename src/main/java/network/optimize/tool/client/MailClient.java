package network.optimize.tool.client;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import network.optimize.tool.constant.EMailConstant;

/**
 * email服务器
 *
 */
public class MailClient{
	
	// 设置服务器
	private static String KEY_SMTP = "mail.smtp.host";
	// 服务器验证
	private static String KEY_PROPS = "mail.smtp.auth";
	// 发件人用户名、密码
	private String SEND_UNAME = "network_optimize";
	// 建立会话
	private MimeMessage message;
	private Session s;

	/*
	 * 初始化方法
	 */
	public MailClient() throws Exception{
		Properties props = System.getProperties();
		props.setProperty(KEY_SMTP, EMailConstant.SMTP_HOST);
		props.put(KEY_PROPS, "true");
		s = Session.getDefaultInstance(props, new Authenticator(){
		      protected PasswordAuthentication getPasswordAuthentication() {
		          return new PasswordAuthentication(SEND_UNAME, EMailConstant.PLATFORM_EMAIL_PASSWORD);
		      }});
		s.setDebug(true);
		message = new MimeMessage(s);
	}

	/**
	 * 发送邮件
	 * 
	 * @param headName
	 *            邮件头文件名
	 * @param sendHtml
	 *            邮件内容
	 * @param receiveUser
	 *            收件人地址
	 */
	public void SendEmail(String headName, String sendHtml, String receiveUser) throws Exception{
		try {
			// 发件人
			InternetAddress from = new InternetAddress(EMailConstant.PLATFORM_EMAIL);
			message.setFrom(from);
			// 收件人
			InternetAddress to = new InternetAddress(receiveUser);
			message.setRecipient(Message.RecipientType.TO, to);
			// 邮件标题
			message.setSubject(headName);
			String content = sendHtml.toString();
			// 邮件内容,也可以使纯文本"text/plain"
			message.setContent(content, "text/html;charset=GBK");
			message.saveChanges();
			Transport transport = s.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(EMailConstant.SMTP_HOST, SEND_UNAME, EMailConstant.PLATFORM_EMAIL_PASSWORD);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			System.out.println("send success!");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

}
