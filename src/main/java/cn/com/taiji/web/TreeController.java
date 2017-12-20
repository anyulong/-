package cn.com.taiji.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TreeController {

	@GetMapping("/tree")
	public String userList() {
		return "tree";
	}
}
