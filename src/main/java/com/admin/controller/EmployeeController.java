package com.admin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseUtil<String> register(@RequestBody Employee employee) {
		ResponseUtil<String> response=new ResponseUtil<>();
		employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
		employee=employeeRepository.save(employee);	
		if(employee!=null) {
			response.setStatus("success");
			response.setResponseObject("successfully registered");
		}
		else {
			response.setStatus("failure");
			response.setResponseObject("could not register");
		}
		
		return response;
		
	}
	
	
	
	
}

