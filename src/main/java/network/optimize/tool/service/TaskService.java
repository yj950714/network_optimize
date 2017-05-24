package network.optimize.tool.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import network.optimize.tool.constant.ErrorCode;
import network.optimize.tool.constant.NetworkOptimizeConstant;
import network.optimize.tool.constant.RemoteServerConstant;
import network.optimize.tool.constant.TaskConstant;
import network.optimize.tool.entity.File;
import network.optimize.tool.entity.FileExample;
import network.optimize.tool.entity.FileType;
import network.optimize.tool.entity.Parameter;
import network.optimize.tool.entity.ParameterExample;
import network.optimize.tool.entity.Task;
import network.optimize.tool.entity.TaskExample;
import network.optimize.tool.entity.TaskFile;
import network.optimize.tool.entity.TaskFileExample;
import network.optimize.tool.entity.TaskParam;
import network.optimize.tool.entity.TaskParamExample;
import network.optimize.tool.entity.TaskType;
import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.mapper.FileMapper;
import network.optimize.tool.mapper.FileTypeMapper;
import network.optimize.tool.mapper.ParameterMapper;
import network.optimize.tool.mapper.TaskFileMapper;
import network.optimize.tool.mapper.TaskMapper;
import network.optimize.tool.mapper.TaskParamMapper;
import network.optimize.tool.mapper.TaskTypeMapper;
import network.optimize.tool.request.AddTaskSetFileRequest;
import network.optimize.tool.request.AddTaskSetParamRequest;
import network.optimize.tool.response.AddTaskResponse;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.TaskDetailedInfoResponse;
import network.optimize.tool.response.info.TaskInfo;
import network.optimize.tool.util.CommonUtil;
import network.optimize.tool.util.RowConverter;
import network.optimize.tool.util.SftpClientUtil;
import network.optimize.tool.util.SshExecClientUtil;

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
	@Autowired
	FileMapper fileMapper;
	@Autowired
	FileTypeMapper fileTypeMapper;
	@Autowired
	TaskFileMapper taskFileMapper;
	
	
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
					taskInfo.setStatusCode(TaskConstant.STATUS.get(task.getStatus()));
					taskInfo.setCreateTime(task.getCreateTime());
					taskInfo.setUpdateTime(task.getUpdateTime());
					return taskInfo;
			}
		});
		return response;
	}
	
	/**
	 * 获取用户特定类型的任务
	 * @param userId
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public ListResponse<TaskInfo> getTasksByUserAndType(Long userId, Long typeId) throws Exception{
		TaskExample taskExample = new TaskExample();
		taskExample.or().andUserIdEqualTo(userId).andTaskTypeIdEqualTo(typeId);
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
					taskInfo.setStatusCode(TaskConstant.STATUS.get(task.getStatus()));
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
	
	/**
	 * 新建一个任务
	 * @param user
	 * @param taskTypeId
	 * @return
	 * @throws WebBackendException
	 */
	public AddTaskResponse addTask(User user, Long taskTypeId) throws WebBackendException{
		if (taskTypeMapper.selectByPrimaryKey(taskTypeId) == null){
			throw new WebBackendException(ErrorCode.TASK_TYPE_NOT_EXIST);
		}
		//插入任务
		Task task = new Task();
		task.setTaskTypeId(taskTypeId);
		task.setUserId(user.getId());
		task.setCreateTime(new Date());
		task.setUpdateTime(new Date());
		task.setStatus(NetworkOptimizeConstant.TASK_STATUS_WAIT_TO_SET_FILE);
		taskMapper.insert(task);
		return new AddTaskResponse(task.getId());
	}
	
	/**
	 * 新建任务--设定文件
	 * @param user
	 * @param request
	 * @return
	 * @throws WebBackendException
	 */
	public BaseResponse addTaskSetFile(User user, AddTaskSetFileRequest request) throws WebBackendException{
		if (request.getFileList() == null || request.getFileList().size()==0) 
			return new BaseResponse();
		if (taskMapper.selectByPrimaryKey(request.getTaskId()) == null){
			throw new WebBackendException(ErrorCode.TASK_NOT_EXIST);
		}
		Task task = taskMapper.selectByPrimaryKey(request.getTaskId());
		if (task.getStatus() != NetworkOptimizeConstant.TASK_STATUS_WAIT_TO_SET_FILE){
			throw new WebBackendException(ErrorCode.TASK_STATUS_ERROR);
		}
		for (String fileName : request.getFileList()){
			FileExample fileExample = new FileExample();
			fileExample.or().andFileNameEqualTo(fileName);
			File file = CommonUtil.getFirst(fileMapper.selectByExample(fileExample));
			if (file == null){
				throw new WebBackendException(ErrorCode.FILE_NOT_EXIST);
			}
			//文件所属
			if (file.getUserId() != user.getId()){
				throw new WebBackendException(ErrorCode.FILE_NOT_BELONG_TO_USER);
			}
			//检查文件类型对应的任务类型
			FileType fileType = fileTypeMapper.selectByPrimaryKey(file.getFileTypeId());
			if (fileType.getTaskType() != task.getTaskTypeId()){
				throw new WebBackendException(ErrorCode.FILE_TYPE_NOT_MATCH_TASK);
			}
		}
		//设定文件
		for (String fileName : request.getFileList()){
			FileExample fileExample = new FileExample();
			fileExample.or().andFileNameEqualTo(fileName);
			File file = CommonUtil.getFirst(fileMapper.selectByExample(fileExample));
			TaskFile taskFile = new TaskFile();
			taskFile.setFileId(file.getId());
			taskFile.setTaskId(task.getId());
			taskFileMapper.insert(taskFile);
		}
		//更新Task状态
		task.setStatus(NetworkOptimizeConstant.TASK_STATUS_WAIT_TO_SET_PARAM);
		taskMapper.updateByPrimaryKey(task);
		return new BaseResponse();
	}
	
	/**
	 * 新建任务--设定参数
	 * @param user
	 * @param request
	 * @return
	 * @throws WebBackendException
	 */
	public BaseResponse addTaskSetParam(User user, AddTaskSetParamRequest request) throws WebBackendException{
		if (request.getParamName() == null || request.getParamValue() == null) 
			return new BaseResponse();
		if (request.getParamName().size()==0 || request.getParamValue().size()==0) 
			return new BaseResponse();
		if (taskMapper.selectByPrimaryKey(request.getTaskId()) == null){
			throw new WebBackendException(ErrorCode.TASK_NOT_EXIST);
		}
		if (request.getParamName().size() != request.getParamValue().size()){
			throw new WebBackendException(ErrorCode.PARAM_NAME_NOT_MATCH_VALUE);
		}
		Task task = taskMapper.selectByPrimaryKey(request.getTaskId());
		if (task.getStatus() != NetworkOptimizeConstant.TASK_STATUS_WAIT_TO_SET_PARAM){
			throw new WebBackendException(ErrorCode.TASK_STATUS_ERROR);
		}
		for (int i=0; i<request.getParamName().size(); i++){
			//检查参数存在
			ParameterExample parameterExample = new ParameterExample();
			parameterExample.or().andParamNameEqualTo(request.getParamName().get(i));
			Parameter parameter = CommonUtil.getFirst(parameterMapper.selectByExample(parameterExample));
			if (parameter == null){
				throw new WebBackendException(ErrorCode.PARAM_NOT_EXIST);
			}
			//检查参数和任务是否匹配
			if (parameter.getTaskTypeId() != task.getTaskTypeId()){
				throw new WebBackendException(ErrorCode.PARAM_NAME_NOT_MATCH_TASK);
			}
		}
		//设定参数
		for (int i=0; i<request.getParamName().size(); i++){
			TaskParam taskParam = new TaskParam();
			ParameterExample parameterExample = new ParameterExample();
			parameterExample.or().andParamNameEqualTo(request.getParamName().get(i));
			Parameter parameter = CommonUtil.getFirst(parameterMapper.selectByExample(parameterExample));
			taskParam.setParamId(parameter.getId());
			taskParam.setParamValue(request.getParamValue().get(i));
			taskParam.setTaskId(task.getId());
			taskParamMapper.insert(taskParam);
		}
		//更新Task状态
		task.setStatus(NetworkOptimizeConstant.TASK_STATUS_WAIT_TO_START);
		taskMapper.updateByPrimaryKey(task);
		return new BaseResponse();
	}
	
	
	/**
	 * 开始一个任务
	 * @param taskId
	 * @param user
	 * @return
	 * @throws WebBackendException
	 */
	public BaseResponse startTask(Long taskId, User user) throws WebBackendException{
		Task task = taskMapper.selectByPrimaryKey(taskId);
		if (task == null){
			throw new WebBackendException(ErrorCode.TASK_NOT_EXIST);
		}
		if (user.getId() != task.getUserId()){
			throw new WebBackendException(ErrorCode.TASK_NOT_BELONG_TO_USER);
		}
		if (task.getStatus() != NetworkOptimizeConstant.TASK_STATUS_WAIT_TO_START){
			throw new WebBackendException(ErrorCode.TASK_STATUS_ERROR);
		}
		//获取命令头
		String command = taskTypeMapper.selectByPrimaryKey(task.getTaskTypeId()).getCommandHead();
		//获取任务的所有文件
		TaskFileExample taskFileExample = new TaskFileExample();
		taskFileExample.or().andTaskIdEqualTo(task.getId());
		List<TaskFile> taskFileList = taskFileMapper.selectByExample(taskFileExample);
		//上传文件到远程服务器
		if (taskFileList != null){
			for (TaskFile taskFile : taskFileList){
				File file = fileMapper.selectByPrimaryKey(taskFile.getFileId());
				SftpClientUtil.sftpUpload(RemoteServerConstant.REMOTE_SERVER_ROOT_DIRECTORY + "/" + user.getId().toString(), file.getPosition()+file.getFileName());
				FileType fileType = fileTypeMapper.selectByPrimaryKey(file.getFileTypeId());
				command = command + " " + fileType.getFileCommandHead() + " " + RemoteServerConstant.REMOTE_SERVER_ROOT_DIRECTORY + "/" + user.getId().toString() + "/" +file.getFileName();
			}
		}
		//获取任务的所有参数
		TaskParamExample taskParamExample = new TaskParamExample();
		taskParamExample.or().andTaskIdEqualTo(task.getId());
		List<TaskParam> taskParamList = taskParamMapper.selectByExample(taskParamExample);
		if (taskParamList != null){
			for (TaskParam taskParam : taskParamList){
				Parameter parameter = parameterMapper.selectByPrimaryKey(taskParam.getParamId());
				command = command + " " + parameter.getParamCode() + " " + taskParam.getParamValue();
			}
		}
		SshExecClientUtil.runCmd(command);
		//更新Task状态
		task.setStatus(NetworkOptimizeConstant.TASK_STATUS_RUNNING);
		taskMapper.updateByPrimaryKey(task);

		return new BaseResponse();
	}
	
}
