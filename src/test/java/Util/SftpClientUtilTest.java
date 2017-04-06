package Util;

import network.optimize.tool.constant.RemoteServerConstant;
import network.optimize.tool.util.SftpClientUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SftpClientUtilTest {

	@Test
	public void testStfpClientUpload() throws Exception{
		SftpClientUtil.sftpUpload(RemoteServerConstant.REMOTE_SERVER_ROOT_DIRECTORY, "D:\\log_network.txt");
	}
	
	@Test
	public void testStfpClientdownload() throws Exception{
		SftpClientUtil.sftpDownload(RemoteServerConstant.REMOTE_SERVER_ROOT_DIRECTORY, "pca.py" ,"D:\\");
	}
}
