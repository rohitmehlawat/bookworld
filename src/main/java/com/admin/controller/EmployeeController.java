package com.admin.controller;

import static com.admin.security.SecurityConstants.HEADER_STRING;
import static com.admin.security.SecurityConstants.SECRET;
import static com.admin.security.SecurityConstants.TOKEN_PREFIX;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.Employee;
import com.admin.domain.UserDetail;
import com.admin.repository.EmployeeRepository;
import com.admin.util.ResponseUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

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
	
	@GetMapping("/getEmployee")
	public ResponseUtil<Employee> getUser(HttpServletRequest request){
		ResponseUtil<Employee> response=new ResponseUtil<>();
		String token = request.getHeader(HEADER_STRING);
		String user = (String) request.getAttribute("user");
		if(!user.contains("@")) {
			  response.setResponseObject(employeeRepository.findByPhoneNo(user));
		      response.setStatus("success");
		}
		else {
			response.setResponseObject(null);
		      response.setStatus("failure");
		}
      
		return response;
	}
	
	
	
	
}

