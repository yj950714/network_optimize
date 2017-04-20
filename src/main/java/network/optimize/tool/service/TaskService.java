package network.optimize.tool.service;

import java.util.ArrayList;
import java.util.List;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.entity.Task;
import network.optimize.tool.entity.TaskExample;
import network.optimize.tool.entity.TaskParam;
import network.optimize.tool.entity.TaskParamExample;
import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.ParameterMapper;
import network.optimize.tool.mapper.TaskMapper;
import network.optimize.tool.mapper.TaskParamMapper;
import network.optimize.tool.mapper.TaskTypeMapper;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.TaskDetailedInfoResponse;
import network.optimize.tool.response.info.TaskInfo;
import network.optimize.tool.util.RowConverter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

	@Autowired
	TaskMapper taskMapper;
	@Autowired
	TaskTypeMapper taskTypeMapper;
	@Autowired
	TaskParamMapper taskParamMapper;
	@Autowired
	ParameterMapper parameterMapper;
	
	
	/**
	 * 获取用户全部任务
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public ListResponse<TaskInfo> getTasksByUser(Long userId) throws Exception{
		TaskExample taskExample = new TaskExample();
		taskExample.or().andUserIdEqualTo(userId);
		List<Task> taskList = taskMapper.selectByExample(taskExample);
		if (taskList == null) return null;
		ListResponse<TaskInfo> response = new ListResponse<TaskInfo>(taskList, new RowConverter<Task,TaskInfo>(){
			@Override
			@SuppressWarnings("null")
			public TaskInfo convertRow (Task task){
					TaskInfo taskInfo = new TaskInfo();
					taskInfo.setId(task.getId());
					taskInfo.setTaskTypeId(task.getTaskTypeId());
					taskInfo.setTaskTypeName(taskTypeMapper.selectByPrimaryKey(task.getTaskTypeId()).getTaskTypeName());
					taskInfo.setStatus(task.getStatus());
					taskInfo.setCreateTime(task.getCreateTime());
					taskInfo.setUpdateTime(task.getUpdateTime());
					return taskInfo;
			}
		});
		return response;
	}
	
	/**
	 * 获取单个任务的详细信息
	 * @param taskId
	 * @param userId
	 * @return
	 * @throws WebBackendException
	 */
	public TaskDetailedInfoResponse getTaskDetailedInfo(Long taskId, Long userId) throws WebBackendException{
		if (taskMapper.selectByPrimaryKey(taskId) == null){
			throw new WebBackendException(ErrorCode.TASK_NOT_EXIST);
		}
		if (userId != taskMapper.selectByPrimaryKey(taskId).getUserId()){
			throw new WebBackendException(ErrorCode.TASK_NOT_BELONG_TO_USER);
		}
		Task task = taskMapper.selectByPrimaryKey(taskId);
		TaskDetailedInfoResponse response = new TaskDetailedInfoResponse();
		response.setTaskId(task.getId());
		response.setTaskTypeId(task.getTaskTypeId());
		response.setTaskTypeName(taskTypeMapper.selectByPrimaryKey(task.getTaskTypeId()).getTaskTypeName());
		response.setStatus(task.getStatus());
		response.setCreateTime(task.getCreateTime());
		response.setUpdateTime(task.getUpdateTime());
		
		List<Long> paramIdList = new ArrayList<Long>();
		List<String> paramNameList = new ArrayList<String>();
		List<String> paramValueList = new ArrayList<String>();
		TaskParamExample taskParamExample = new TaskParamExample();
		taskParamExample.or().andTaskIdEqualTo(task.getId());
		List<TaskParam> taskParamList =taskParamMapper.selectByExample(taskParamExample);
		if (taskParamList != null){
			for (TaskParam taskParam : taskParamList){
				paramIdList.add(taskParam.getParamId());
				paramValueList.add(taskParam.getParamValue());
				paramNameList.add(parameterMapper.selectByPrimaryKey(taskParam.getParamId()).getParamName());
			}
		}
		response.setParamId(paramIdList);
		response.setParamName(paramNameList);
		response.setParamValue(paramValueList);
		return response;
	}
	
}
