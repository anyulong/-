package cn.com.taiji.web;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.taiji.domain.User;
import cn.com.taiji.dto.UserDto;
import cn.com.taiji.service.UserService;

@Controller
public class UserController {

	
	@GetMapping("/userlist")
	public String userList() {
		return "userList";
	}
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public  String doLogin() {
		return "login";
	}
	@GetMapping("/servicelist")
	public String serviceList() {
		return "serviceList";
	}
	
	@GetMapping("/toadduser")
	public String toAddUser(Model model) {
		UserDto userDto = new UserDto();
		model.addAttribute("u", userDto);
		return "serviceEdit";
	}
	
}
