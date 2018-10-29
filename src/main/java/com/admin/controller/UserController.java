package com.admin.controller;

import java.util.List;

import javax.imageio.spi.RegisterableService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.UserDetail;
import com.admin.repository.UserRepository;
import com.admin.util.ResponseUtil;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseUtil<String> register(@RequestBody UserDetail user) {
		ResponseUtil<String> response=new ResponseUtil<>();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
		response.setStatus("success");
		response.setResponseObject(null);
		return response;
	}
	
	@GetMapping("/getAllUsers")
	public ResponseUtil<List<UserDetail>> getAllUsers(){
		ResponseUtil<List<UserDetail>> response=new ResponseUtil<>();
		response.setResponseObject(userRepository.findAll());
		response.setStatus("success");
		return response;
	}
	
	
}
