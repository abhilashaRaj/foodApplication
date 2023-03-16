package com.incture.FoodApp.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.incture.FoodApp.Entity.Login;
import com.incture.FoodApp.Entity.UserDTO;
import com.incture.FoodApp.Entity.UserMaster;
import com.incture.FoodApp.Service.CommonService;

@RestController
@CrossOrigin("*")
@RequestMapping("/common")
public class CommonController {
	
	@Autowired
	private CommonService commonService;
	
	@PostMapping("/login")
	public UserDTO login(@RequestBody UserMaster user) {
		return commonService.login(user);
	}
	
	@GetMapping("/menu")
	public UserDTO getMenu(@RequestParam String date,@RequestParam String category ) {
		return commonService.getMenu(date, category);
	}

}
