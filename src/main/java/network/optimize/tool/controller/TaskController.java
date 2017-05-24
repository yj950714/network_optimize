package network.optimize.tool.controller;

import javax.validation.Valid;

import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.request.AddTaskSetFileRequest;
import network.optimize.tool.request.AddTaskSetParamRequest;
import network.optimize.tool.response.AddTaskResponse;
import network.optimize.tool.response.BaseResponse;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.TaskDetailedInfoResponse;
import network.optimize.tool.response.info.TaskInfo;
import network.optimize.tool.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TaskController {

	@Autowired
	TaskService taskService;
	
	/**
	 * 获取一个用户的所有任务
	 * @throws Exception 
	 */
    @RequestMapping(value="/tasks", method=RequestMethod.GET)
    ListResponse<TaskInfo> getTasksByUser(@RequestAttribute("user") User user) throws Exception {
    	ListResponse<TaskInfo> response = taskService.getTasksByUser(user.getId());
    	return response;
    }
    
    /**
     * 获取一个用户的特定类型任务
     * @param user
     * @param type
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/tasks/{type}", method=RequestMethod.GET)
    ListResponse<TaskInfo> getTasksByUserAndType(@RequestAttribute("user") User user, @PathVariable Long type) throws Exception {
    	ListResponse<TaskInfo> response = taskService.getTasksByUserAndType(user.getId(), type);
    	return response;
    }
    
    /**
     * 获取单个任务的详细信息
     * @param id
     * @param user
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/tasks/detail/{id}", method=RequestMethod.GET)
    TaskDetailedInfoResponse getTaskDetailedInfo(@PathVariable Long id, @RequestAttribute("user") User user) throws WebBackendException{
    	TaskDetailedInfoResponse response = taskService.getTaskDetailedInfo(id, user.getId());
    	return response;
    }
    
    /**
     * 新建一个任务
     * @param user
     * @param taskTypeId
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/tasks/add/{type}", method=RequestMethod.GET)
    AddTaskResponse addTask(@RequestAttribute("user") User user, @PathVariable Long type) throws WebBackendException{
    	AddTaskResponse response = taskService.addTask(user, type);
    	return response;
    }
    
    /**
     * 新建任务-设定文件
     * @param user
     * @param request
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/tasks/add/file", method=RequestMethod.POST)
    BaseResponse addTaskSetFile(@RequestAttribute("user") User user, @RequestBody @Valid AddTaskSetFileRequest request) throws WebBackendException{
    	BaseResponse response = taskService.addTaskSetFile(user, request);
    	return response;
    }
    
    /**
     * 新建任务-设定参数
     * @param user
     * @param request
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/tasks/add/param", method=RequestMethod.POST)
    BaseResponse addTaskSetParam(@RequestAttribute("user") User user, @RequestBody @Valid AddTaskSetParamRequest request) throws WebBackendException{
    	BaseResponse response = taskService.addTaskSetParam(user, request);
    	return response;
    }
    
    /**
     * 开始任务
     * @param id
     * @param user
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/tasks/start/{taskId}", method=RequestMethod.GET)
    BaseResponse startTask(@PathVariable Long taskId, @RequestAttribute("user") User user) throws WebBackendException{
    	BaseResponse response = taskService.startTask(taskId, user);
    	return response;
    }
    
    
}
