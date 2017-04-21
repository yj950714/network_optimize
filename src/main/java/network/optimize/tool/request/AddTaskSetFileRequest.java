package network.optimize.tool.request;

import java.util.List;

import javax.validation.constraints.NotNull;

public class AddTaskSetFileRequest extends BaseRequest{
	
	@NotNull
	private Long taskId;
	
	private List<String> fileList;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public List<String> getFileList() {
		return fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}
	
	

}
