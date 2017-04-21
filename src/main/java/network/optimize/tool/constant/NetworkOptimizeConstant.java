package network.optimize.tool.constant;


/**
 * 系统常量
 *
 */
public class NetworkOptimizeConstant {

	/**
	 * 默认日期时间格式
	 */
	public static final String COMMON_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 默认日期格式
	 */
	public static final String COMMON_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * 默认密码长度
	 */
	public static final int COMMON_PASSWORD_LENGTH = 12;
	
	/**
	 * 文件操作--删除
	 */
	public static final String FILE_DELETE = "delete";
	
	/**
	 * 文件操作--修改
	 */
	public static final String FILE_CHANGE = "edit";
	/**
	 * 任务状态--待设定文件
	 */
	public static final int TASK_STATUS_WAIT_TO_SET_FILE = 10;
	/**
	 * 任务状态--待设定参数
	 */
	public static final int TASK_STATUS_WAIT_TO_SET_PARAM = 20;
	/**
	 * 任务状态--待开始
	 */
	public static final int TASK_STATUS_WAIT_TO_START = 30;
	/**
	 * 任务状态--运行中
	 */
	public static final int TASK_STATUS_RUNNING = 40;
	/**
	 * 任务状态--已完成
	 */
	public static final int TASK_STATUS_DONE = 50;
	/**
	 * 任务状态--运行错误
	 */
	public static final int TASK_STATUS_ERROR = 60;
	
	
}
