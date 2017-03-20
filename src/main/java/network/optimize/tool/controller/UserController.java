package network.optimize.tool.controller;

import java.util.List;

import network.optimize.tool.entity.User;
import network.optimize.tool.entity.UserExample;
import network.optimize.tool.mapper.UserMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping({"/home"})
public class UserController {
	@Autowired
	UserMapper userMapper;
	@RequestMapping(value = "/user")
	public String user(){
		List<User> user = userMapper.selectByExample(new UserExample());
		return user.get(0).getUserName()+"-----"+user.get(0).getRealName();
	}
}