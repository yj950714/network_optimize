package network.optimize.tool.util;

import java.util.Calendar;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import network.optimize.tool.constant.EMailConstant;
import network.optimize.tool.exception.WebBackendException;

/**
 * email工具类
 *
 */
public class MailUtil {	
		/**
		 * 发送系统邮件，使用系统默认邮箱
		 * @param to 收件人邮箱
		 * @param subject 主题
		 * @param messageText 内容
		 * @param ccs 抄送
		 * @param bccs 密送
		 * @throws MessagingException
		 * @throws WebBackendException 
		 */
		public static void sendPlatFormMessage(String to,String[] ccs,String[] bccs,String subject,String messageText) throws MessagingException{
			sendMessage(EMailConstant.SMTP_HOST, EMailConstant.PLATFORM_EMAIL, EMailConstant.PLATFORM_EMAIL_PASSWORD, to, ccs, bccs, subject, messageText+EMailConstant.PLATFORM_EMAIL_FOOTER, EMailConstant.DEFAULT_MESSAGE_TYPE);
		}
	
	    @SuppressWarnings("static-access")
	    public static void sendMessage(String smtpHost, String from,
		    String fromUserPassword, String to, String[] ccs, String[] bccs, String subject,
		    String messageText, String messageType) throws MessagingException {
	    	
			// 第一步：配置javax.mail.Session对象
			Properties props = new Properties();
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.starttls.enable", "true");// 使用 STARTTLS安全连接
			// props.put("mail.smtp.port", "25"); //google使用465或587端口
			props.put("mail.smtp.auth", "true"); // 使用验证
			// props.put("mail.debug", "true");
			Session mailSession = Session.getInstance(props, new MyAuthenticator(
				from, fromUserPassword));
	
			// 第二步：编写消息
			MimeMessage message = new MimeMessage(mailSession);
			message.setSentDate(Calendar.getInstance().getTime());
			message.setSubject(subject);
			message.setContent(messageText, messageType);
			Transport transport = mailSession.getTransport("smtp");
			transport.connect(smtpHost, from, fromUserPassword);
			
			// 发件人
			InternetAddress fromAddress = new InternetAddress(from);
			message.setFrom(fromAddress);
			
			// 收件人
			InternetAddress toAddress = new InternetAddress(to);
			message.addRecipient(RecipientType.TO, toAddress);
			transport.send(message, message.getRecipients(RecipientType.TO));
			
			// 抄送
			if(ccs!=null&&ccs.length>0){
				for(String cc:ccs){
					InternetAddress ccAddress = new InternetAddress(cc);
					message.addRecipient(RecipientType.TO, ccAddress);
				}
				transport.send(message, message.getRecipients(RecipientType.CC));
			}
			
			// 密送
			if(bccs!=null&&bccs.length>0){
				for(String bcc:bccs){
					message.addRecipient(RecipientType.BCC, new InternetAddress(bcc));
				}
				transport.send(message, message.getRecipients(RecipientType.BCC));
			}
	    }
	}

	class MyAuthenticator extends Authenticator {
	    String userName = "";
	    String password = "";
	
	    public MyAuthenticator() {
	
	    }
	
	    public MyAuthenticator(String userName, String password) {
		this.userName = userName;
		this.password = password;
	    }
	
	    protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
    }
}
