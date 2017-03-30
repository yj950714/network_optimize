package network.optimize.tool.constant;

public enum ErrorCode {
	NOT_VALID_PARAM(10001,"参数错误,请确认参数格式正确"),
	//用户相关错误
	USER_NOT_FOUND(20001,"找不到用户"),
	TOKEN_ERROR(20002,"token不正确"),
	AUTH_ERROR(20003,"您无该操作权限"),
	DATABASE_ERROR(20004,"数据库错误"),
	ROLELIST_NOT_FOUND(20005,"未定义角色列表"),
	ROLE_NOT_FOUND(20006,"未找到角色"),
	BATCH_TASK_NOT_EXIST(20007,"编辑参数错误"),
	EMAIL_ERROR(20008, "无效的邮件地址"),
	PAGE_MODE_ERROR(20009,"批量查询任务不存在"),
	USER_PASSWORD_NOT_VALID(20010,"用户名密码不正确"),
	
	NOT_VALID_DATE(30001,"时间格式错误"),
	//文件相关错误
	FILE_TYPE_ERROR(40001,"文件类型错误"),
	UPLOAD_TO_REMOTE_ERROR(40005,"上传文件到计算服务器错误"),
	DOWNLOAD_FROM_REMOTE_ERROR(40006,"从计算服务器下载文件出错"),
	
	
	OTHER(10099,"其他类型错误");
	
	private int errorCode;
	private String errorInfo;
	
	ErrorCode(int errorCode,String errorInfo){
		this.errorCode = errorCode;
		this.errorInfo = errorInfo;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
	
}
