package com.admin.controller;

import static com.admin.security.SecurityConstants.HEADER_STRING;
import static com.admin.security.SecurityConstants.SECRET;
import static com.admin.security.SecurityConstants.TOKEN_PREFIX;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.SchoolDetail;
import com.admin.domain.UserDetail;
import com.admin.repository.SchoolRepository;
import com.admin.util.ResponseUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
public class SchoolController {

	@Autowired
	SchoolRepository schoolRepository;
	
	@PostMapping("/addSchool")
	public ResponseUtil<String> addSchoolDetail(@RequestBody SchoolDetail schoolDetail,HttpServletRequest request) {
		ResponseUtil<String> response=new ResponseUtil<>();
		System.out.println(schoolDetail);
		schoolDetail.setDealStatus("pending");
		String token = request.getHeader(HEADER_STRING);
        String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
		
        UserDetail userDetail=new UserDetail();
        userDetail.setEmail(user);
		schoolDetail.setUserDetail(userDetail);
		schoolRepository.save(schoolDetail);
		response.setResponseObject(null);
		response.setStatus("success");
		return response;	
	}
	
	@PostMapping("/updateSchool")
	public ResponseUtil<String> updateSchoolDetail(@RequestBody SchoolDetail schoolDetail,HttpServletRequest request) {
		ResponseUtil<String> response=new ResponseUtil<>();
		System.out.println(schoolDetail);
		String token = request.getHeader(HEADER_STRING);
        String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
		
        UserDetail userDetail=new UserDetail();
        userDetail.setEmail(user);
		schoolDetail.setUserDetail(userDetail);
		schoolRepository.save(schoolDetail);
		response.setResponseObject(null);
		response.setStatus("success");
		return response;	
	}
	
	
	@GetMapping(value="/schoollist/{id}")
	public ResponseUtil<List<SchoolDetail>> getSchoolDetails(@PathVariable("id") String userid){	
		
		ResponseUtil<List<SchoolDetail>> response=new ResponseUtil<>();
		response.setResponseObject(schoolRepository.findAll());
		response.setStatus("success");
		return response;
	}
	
	
	
	@GetMapping(value="/getAllSchool")
	public ResponseUtil<List<SchoolDetail>> getAllSchoolDetails(){			
		ResponseUtil<List<SchoolDetail>> response=new ResponseUtil<>();
		response.setResponseObject(schoolRepository.findAll());
		response.setStatus("success");
		return response;
	}
	
}
