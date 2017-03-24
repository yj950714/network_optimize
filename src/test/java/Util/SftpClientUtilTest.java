package Util;

import network.optimize.tool.util.SftpClientUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SftpClientUtilTest {

	@Test
	public void testStfpClient() throws Exception{
		SftpClientUtil.sftpUpload("./", "D:\\user_file\\1\\20170324093428.6881478ce18f5dee8a403b1e5ef6c022.net");
	}
}
