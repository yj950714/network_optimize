package network.optimize.tool.util;

import network.optimize.tool.client.SshExecClient;
import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.exception.WebBackendException;

/**
 * SSH运行远程命令
 * @author Jie.Yao
 *
 */
public class SshExecClientUtil {

	/**
	 * 运行远程命令
	 * @param cmd
	 * @param charset
	 * @throws WebBackendException
	 */
	public static void runCmd(String cmd) throws WebBackendException{
		try{
			SshExecClient sshClient = new SshExecClient();
			sshClient.runCmdJsch(cmd);
		} catch (Exception e){
			throw new WebBackendException(ErrorCode.REMOTE_RUN_ERROR);
		}
		
	}
}
