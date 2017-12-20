package cn.com.taiji.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import cn.com.taiji.domain.Department;
import cn.com.taiji.domain.DepartmentRepository;
import cn.com.taiji.dto.TreeDto;
import cn.com.taiji.dto.UserDto;
import cn.com.taiji.service.DepartmentService;

@Controller
public class TreeApiController {
	@Autowired
	DepartmentService ds;
	@Autowired
	DepartmentRepository dr;
	@RequestMapping(value="/showtree",method=RequestMethod.POST)
	@ResponseBody
	public List<TreeDto> showTree(String data) {
		List<TreeDto> tds = new ArrayList<TreeDto>();
		System.out.println("请求了");
		Department d = dr.findDepartmentById("1");
		if(null!=d) {
			
		TreeDto td = new TreeDto(d);
		System.out.println(td);
		tds.add(td);
		return tds;
		}
		return null;
	}
}
