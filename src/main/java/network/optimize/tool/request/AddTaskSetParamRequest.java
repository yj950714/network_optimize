package network.optimize.tool.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AddTaskSetParamRequest extends BaseRequest{
	
	@NotNull
	private Long taskId;
	private List<String> paramName;
	private List<String> paramValue;

	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public List<String> getParamName() {
		return paramName;
	}
	public void setParamName(List<String> paramName) {
		this.paramName = paramName;
	}
	public List<String> getParamValue() {
		return paramValue;
	}
	public void setParamValue(List<String> paramValue) {
		this.paramValue = paramValue;
	}
	
	

}
