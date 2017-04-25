package Util;

import network.optimize.tool.util.SshExecClientUtil;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class SshExecClientUtilTest {

	@Test
	public void testSshExec() throws Exception{
		SshExecClientUtil.runCmd("mkdir hello \n");
	}
}
