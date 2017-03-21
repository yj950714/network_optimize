package network.optimize.tool.config;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.exceptions.IbatisException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.response.BaseExceptionResponse;
import network.optimize.tool.util.CommonUtil;


/**
 * 错误控制器
 * 出错时显示错误页面
 *
 */
@ControllerAdvice
public class ExceptionConfig {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public BaseExceptionResponse defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
		e.printStackTrace();
		//参数无法转换错误
		if(e instanceof HttpMessageNotReadableException){
			return new BaseExceptionResponse(ErrorCode.NOT_VALID_PARAM);
		}
		//校验错误则返回校验错误
		if(e instanceof MethodArgumentNotValidException){
			try{
				//调用共通处理方法
				CommonUtil.handleValidation(((MethodArgumentNotValidException) e).getBindingResult());
			} catch (WebBackendException paramException){
				return new BaseExceptionResponse(paramException);
			}
		}
		// 使用@Validated校验参数错误返回的异常
		if(e instanceof BindException) {
			try{
				//调用共通处理方法
				CommonUtil.handleValidation(((BindException) e).getBindingResult());
			} catch (WebBackendException paramException){
				return new BaseExceptionResponse(paramException);
			}
			
		}
    	//若有后台定义的错误码则按该错误码返回
    	if(e instanceof WebBackendException){
	    	return new BaseExceptionResponse((WebBackendException)e);
    	} else if(e instanceof IbatisException){
    	//数据库错误则返回数据库错误
    		e.printStackTrace();
    		return new BaseExceptionResponse(ErrorCode.DATABASE_ERROR);
    	} else {
    	//其他
    		e.printStackTrace();
    		return new BaseExceptionResponse(ErrorCode.OTHER);
    	}
    }
}
