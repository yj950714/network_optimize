package network.optimize.tool.controller;

import network.optimize.tool.entity.User;
import network.optimize.tool.exception.WebBackendException;
import network.optimize.tool.response.ListResponse;
import network.optimize.tool.response.TaskDetailedInfoResponse;
import network.optimize.tool.response.info.TaskInfo;
import network.optimize.tool.service.TaskService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
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
     * 获取单个任务的详细信息
     * @param id
     * @param user
     * @return
     * @throws WebBackendException
     */
    @RequestMapping(value="/tasks/{id}", method=RequestMethod.GET)
    TaskDetailedInfoResponse getTaskDetailedInfo(@PathVariable Long id, @RequestAttribute("user") User user) throws WebBackendException{
    	TaskDetailedInfoResponse response = taskService.getTaskDetailedInfo(id, user.getId());
    	return response;
    }
}
