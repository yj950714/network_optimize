package network.optimize.tool.response;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 任务详细信息
 * @author Jie.Yao
 *
 */
public class TaskDetailedInfoResponse extends BaseResponse {

	private Long taskId;
	private Long taskTypeId;
	private String taskTypeName;
	private Integer status;
	private List<Long> paramId;
	private List<String> paramName;
	private List<String> paramValue;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date createTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date updateTime;
	
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getTaskTypeId() {
		return taskTypeId;
	}
	public void setTaskTypeId(Long taskTypeId) {
		this.taskTypeId = taskTypeId;
	}
	public String getTaskTypeName() {
		return taskTypeName;
	}
	public void setTaskTypeName(String taskTypeName) {
		this.taskTypeName = taskTypeName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Long> getParamId() {
		return paramId;
	}
	public void setParamId(List<Long> paramId) {
		this.paramId = paramId;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
