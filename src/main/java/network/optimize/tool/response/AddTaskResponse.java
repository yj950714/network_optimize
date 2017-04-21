package network.optimize.tool.response;

public class AddTaskResponse extends BaseResponse{

	private Long taskId;

	public AddTaskResponse(Long taskId){
		this.taskId = taskId;
	}
	
	public AddTaskResponse(){
	}
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	
}
