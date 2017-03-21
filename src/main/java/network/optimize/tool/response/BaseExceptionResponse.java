package network.optimize.tool.response;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.exception.WebBackendException;

/**
 * 异常时返回
 *
 */
public class BaseExceptionResponse extends BaseResponse{
	private String errorInfo;
	
	public BaseExceptionResponse(WebBackendException e){
		this.setErrorCode(e.getErrorCode());
		this.errorInfo = e.getErrorInfo();
	}
	
	public BaseExceptionResponse(ErrorCode e) {
		this.setErrorCode(e.getErrorCode());
		this.errorInfo = e.getErrorInfo();
	}

	public String getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(String errorInfo) {
		this.errorInfo = errorInfo;
	}
}
