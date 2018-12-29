package com.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.Employee;
import com.admin.repository.EmployeeRepository;
import com.admin.util.ResponseUtil;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	EmployeeRepository employeeRepository;
	
	@PostMapping("/register")
	public ResponseUtil<String> register(@RequestBody Employee employee) {
		ResponseUtil<String> response=new ResponseUtil<>();
		employee=employeeRepository.save(employee);	
		if(employee!=null) {
			response.setStatus("success");
			response.setResponseObject(null);
		}
		else {
			response.setStatus("failure");
			response.setResponseObject(null);
		}
		
		return response;
		
	}
	
	@PostMapping("/login")
	public ResponseUtil<String> login(@RequestBody String phoneNo, String password ) {
		ResponseUtil<String> response=new ResponseUtil<>();
		response.setStatus("success");
		response.setResponseObject(null);
		return response;
		
	}
	
	
	
	
}

