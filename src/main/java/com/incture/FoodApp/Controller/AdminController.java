package com.incture.FoodApp.Controller;

import com.incture.FoodApp.Entity.AdminDTO;
import com.incture.FoodApp.Service.AdminService;
import com.incture.FoodApp.Entity.Item;
import com.incture.FoodApp.Entity.Menu;
import com.incture.FoodApp.Entity.RoleMaster;
import com.incture.FoodApp.Entity.UserDTO;
import com.incture.FoodApp.Entity.UserMaster;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private AdminService adminService;

//    @GetMapping("/hello")
//    public String hello()
//    {
//        return "Hello";
//    }
	@GetMapping("/analytics")
	public HashMap<String, Float> analytics(@RequestParam String date, @RequestParam int category) {
		return adminService.analytics(date, category);
	}

	@GetMapping("/graph1")
	public HashMap<String, Float> graph_DayVsAvgRating(@RequestParam String date, @RequestParam int category) {
		return adminService.graph_DayVsAvgRating(date, category);
	}

	@GetMapping("/graph2")
	public HashMap<String, HashMap<Integer, Integer>> graph_AttendanceVsEmployeeAte(@RequestParam String date,
			@RequestParam int category) {
		return adminService.graph_AttendanceVsEmployeeAte(date, category);
	}

	@GetMapping("/attendance")
	public ResponseEntity<AdminDTO> getAttendance(@RequestParam String date, @RequestParam int category) {
		AdminDTO adminDTO = null;
		try {
			adminDTO = adminService.getAttendance(date, category);
			return ResponseEntity.of(Optional.of(adminDTO));
		} catch (Exception e) {
			e.printStackTrace();
			adminDTO.setStatus(false);
			adminDTO.setMessage("Attendance Fetching Failed");
			adminDTO.setData(null);
			return ResponseEntity.of(Optional.of(adminDTO));
		}
	}
	@GetMapping("/prevattendance")
	public ResponseEntity<AdminDTO> getPrevAttendance(@RequestParam String date, @RequestParam int category) {
		AdminDTO adminDTO = null;
		try {
			adminDTO = adminService.getPrevAttendance(date, category);
			return ResponseEntity.of(Optional.of(adminDTO));
		} catch (Exception e) {
			e.printStackTrace();
			adminDTO.setStatus(false);
			adminDTO.setMessage("Previous Attendance Fetching Failed");
			adminDTO.setData(null);
			return ResponseEntity.of(Optional.of(adminDTO));
		}
	}

	@PostMapping("/attendance")
	public ResponseEntity<AdminDTO> addRealAttendance(@RequestParam String adminUserId, @RequestParam String date,
			@RequestBody HashMap<String, Integer> attendance) {
		AdminDTO adminDTO = null;
		try {
			adminDTO = adminService.addRealAttendance(adminUserId, date, attendance);
			return ResponseEntity.of(Optional.of(adminDTO));
		} catch (Exception e) {
			e.printStackTrace();
			adminDTO.setStatus(false);
			adminDTO.setMessage("Attendance Data Posting Failed");
			adminDTO.setData(null);
			return ResponseEntity.of(Optional.of(adminDTO));
		}
	}

	@GetMapping("/feedback")
	public ResponseEntity<AdminDTO> getFeedback(@RequestParam String date, @RequestParam int category) {
		AdminDTO adminDTO = null;
		try {
			adminDTO = adminService.getFeedback(date, category);
			return ResponseEntity.of(Optional.of(adminDTO));
		} catch (Exception e) {
			e.printStackTrace();
			adminDTO.setStatus(false);
			adminDTO.setMessage("Feedbacks Fetching Failed");
			adminDTO.setData(null);
			return ResponseEntity.of(Optional.of(adminDTO));
		}
	}

	@GetMapping("/feedback/count")
	public ResponseEntity<AdminDTO> getFeedbackCount(@RequestParam String date, @RequestParam int category) {
		AdminDTO adminDTO = null;
		try {
			adminDTO = adminService.getFeedbackCount(date, category);
			return ResponseEntity.of(Optional.of(adminDTO));
		} catch (Exception e) {
			e.printStackTrace();
			adminDTO.setStatus(false);
			adminDTO.setMessage("Feedback Count Fetching Failed");
			adminDTO.setData(null);
			return ResponseEntity.of(Optional.of(adminDTO));
		}
	}

	@PostMapping("/menu")
	public AdminDTO setMenu(@RequestBody Menu addMenu) {
		return adminService.setMenu(addMenu);
	}

	@PatchMapping("/menu")
	public AdminDTO updateMenu(@RequestBody Menu editMenu) {
		return adminService.updateMenu(editMenu);
	}

	@GetMapping("/prevmenu")
	public AdminDTO prevMenu(@RequestParam String category) {
		return adminService.prevMenu(category);
	}

	@GetMapping("/items")
	public AdminDTO getAllItems() {
		return adminService.getAllItems();
	}

	@PostMapping("/item")
	public AdminDTO addItem(@RequestBody Item item) {
		return adminService.addItem(item.getItemName());
	}

	@PostMapping("/auth/register")
	public AdminDTO register(@RequestBody UserMaster user) {
		return adminService.register(user);
	}

	@PatchMapping("/auth/update")
	public AdminDTO update(@RequestBody UserMaster user) {
		return adminService.updateEmp(user);
	}

	@PostMapping("/auth/role")
	public AdminDTO addNewRole(@RequestBody RoleMaster role) {
		return adminService.addNewRole(role);
	}
}
