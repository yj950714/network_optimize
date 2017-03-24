package Util;

import network.optimize.tool.client.SftpClient;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SftpClientUtilTest {

	@Test
	public void testStfpClient() throws Exception{
		String host = "202.120.45.100"; 
		int port = 22; 
		String username = "fzshen"; 
		String password = "bio4348;"; 
		SftpClient client = new SftpClient(host, port, username, password);
		client.upload("./", "D:\\user_file\\1\\20170324093428.6881478ce18f5dee8a403b1e5ef6c022.net");
		client.disconnect();
	}
}
