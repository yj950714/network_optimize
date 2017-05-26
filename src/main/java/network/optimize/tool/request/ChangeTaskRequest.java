package network.optimize.tool.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class ChangeTaskRequest extends BaseRequest {
	
	@NotNull
	private Long taskId;
	
	private List<Long> fileIdList;
	
	private List<Long> paramIdList;
	
	private List<String> paramValueList;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
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
