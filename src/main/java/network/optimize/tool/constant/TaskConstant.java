package network.optimize.tool.constant;

import java.util.HashMap;
import java.util.Map;

public class TaskConstant {

	public final static Map<Integer, String> STATUS = new HashMap<Integer, String>();  

	static {
		STATUS.put(10, "等待开始");
		STATUS.put(20, "任务运行中");
		STATUS.put(30, "任务运行成功");
		STATUS.put(40, "任务运行失败");
	}

	public static final Integer STATUS_WAIT_TO_START = 10;
	public static final Integer STATUS_RUNNING = 20;
	public static final Integer STATUS_FINISHED = 30;
	public static final Integer STATUS_ERROR = 40;
}
