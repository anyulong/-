package cn.com.taiji.api;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;

import cn.com.taiji.dto.UserDto;
import cn.com.taiji.service.UserService;
@Controller
public class UserApiController {

	@Autowired
	UserService us;
	@Autowired
	ObjectMapper objectMapper;
	private static final Logger log = LoggerFactory
			.getLogger(UserApiController.class);
	@RequestMapping(value="/getuserpage",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getUserPage(Model model,String models){
		System.out.println(models);
		//System.out.println("加载了");
		Map searchParameters = new HashMap();
		if (models != null && models.length() > 0) {
			try {
				searchParameters = objectMapper.readValue(models,
						new TypeReference<Map>() {
						});
			} catch (JsonParseException e) {
				log.error("JsonParseException{}:", e.getMessage());
			} catch (JsonMappingException e) {
				log.error("JsonMappingException{}:", e.getMessage());
			} catch (IOException e) {
				log.error("IOException{}:", e.getMessage());
			}
		}
		return us.getPage(searchParameters);
		
	}
	
	@RequestMapping(value="/adduser",method=RequestMethod.POST)
	@ResponseBody
	public void toAddUser(String data) {
		System.out.println(data);
		Map<String, String> result = new HashMap<>();
		if(data!=null) {
			UserDto userDto;
			try {
				userDto = objectMapper.readValue(data, UserDto.class);
				System.out.println("controller"+userDto.getId());
				us.updUser(userDto);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
}
