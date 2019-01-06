package com.admin.controller;

import static com.admin.security.SecurityConstants.HEADER_STRING;
import static com.admin.security.SecurityConstants.SECRET;
import static com.admin.security.SecurityConstants.TOKEN_PREFIX;

import java.util.ArrayList;
import java.util.List;

import javax.imageio.spi.RegisterableService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.UserDetail;
import com.admin.repository.UserRepository;
import com.admin.util.ResponseUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
@RequestMapping("/admin")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/register")
	public ResponseUtil<String> register(@RequestBody UserDetail user) {
		ResponseUtil<String> response=new ResponseUtil<>();
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user=userRepository.save(user);
		if(user!=null) {
			response.setStatus("success");
			response.setResponseObject("successfully registered");
		}
		else {
			response.setStatus("failure");
			response.setResponseObject("could not register");
		}
		
		return response;
	}
	
	@GetMapping("/getAllUsers")
	public ResponseUtil<List<UserDetail>> getAllUsers(){
		ResponseUtil<List<UserDetail>> response=new ResponseUtil<>();
		response.setResponseObject(userRepository.findAll());
		response.setStatus("success");
		return response;
	}
	
	@GetMapping("/getUser")
	public ResponseUtil<UserDetail> getUser(HttpServletRequest request){
		ResponseUtil<UserDetail> response=new ResponseUtil<>();
		String token = request.getHeader(HEADER_STRING);
        String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
        
        response.setResponseObject(userRepository.findByEmail(user));
        response.setStatus("success");
		return response;
	}
	
	
}
