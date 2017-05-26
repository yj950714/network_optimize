package network.optimize.tool.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AddTaskRequest extends BaseRequest {
	
	@NotNull
	private Long taskType;
	
	private List<Long> fileIdList;
	
	private List<Long> paramIdList;
	
	private List<String> paramValueList;

	public Long getTaskType() {
		return taskType;
	}

	public void setTaskType(Long taskType) {
		this.taskType = taskType;
	}

	public List<Long> getFileIdList() {
		return fileIdList;
	}

	public void setFileIdList(List<Long> fileIdList) {
		this.fileIdList = fileIdList;
	}

	public List<Long> getParamIdList() {
		return paramIdList;
	}

	public void setParamIdList(List<Long> paramIdList) {
		this.paramIdList = paramIdList;
	}

	public List<String> getParamValueList() {
		return paramValueList;
	}

	public void setParamValueList(List<String> paramValueList) {
		this.paramValueList = paramValueList;
	}

	
}
