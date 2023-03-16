package com.incture.FoodApp.Controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.incture.FoodApp.Entity.*;
import com.incture.FoodApp.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/hello")
	public String hello() {
		return "Hello";
	}

	@PostMapping("/feedback")
	public ResponseEntity<UserDTO> addFeedback(@RequestBody ObjectNode objectNode) {
		UserDTO userDTO = null;
		try {
			userDTO = userService.addFeedback(objectNode);
			return ResponseEntity.of(Optional.of(userDTO));
		} catch (Exception e) {
			e.printStackTrace();
			userDTO.setStatus(false);
			userDTO.setMessage("Feedback Posting Failed");
			userDTO.setData(null);
			return ResponseEntity.of(Optional.of(userDTO));
		}
	}

	@GetMapping("/feedback")
	public ResponseEntity<UserDTO> getFeedback(@RequestParam String userId,@RequestParam String date, @RequestParam int category) {
		UserDTO userDTO = null;
		try {
			userDTO = userService.getFeedback(userId,date,category);
			return ResponseEntity.of(Optional.of(userDTO));
		} catch (Exception e) {
			e.printStackTrace();
			userDTO.setStatus(false);
			userDTO.setMessage("Feedback Fetching Failed");
			userDTO.setData(null);
			return ResponseEntity.of(Optional.of(userDTO));
		}
	}

	@PostMapping("/consent")
	public ResponseEntity<UserDTO> userConsent(@RequestBody ObjectNode objectNode) {
		UserDTO userDTO = null;
		try {
			userDTO = userService.userConsent(objectNode);
			return ResponseEntity.of(Optional.of(userDTO));
		} catch (Exception e) {
			e.printStackTrace();
			userDTO.setStatus(false);
			userDTO.setMessage("User Consent Failed");
			userDTO.setData(null);
			return ResponseEntity.of(Optional.of(userDTO));
		}
	}

}
