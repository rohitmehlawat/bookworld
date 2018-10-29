package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.SchoolDetail;
import com.admin.repository.SchoolRepository;
import com.admin.util.ResponseUtil;

@RestController
public class SchoolController {

	@Autowired
	SchoolRepository schoolRepository;
	
	@PostMapping("/addSchool")
	public ResponseUtil<String> addSchoolDetail(@RequestBody SchoolDetail schoolDetail) {
		ResponseUtil<String> response=new ResponseUtil<>();
		System.out.println(schoolDetail);
		schoolRepository.save(schoolDetail);
		response.setResponseObject(null);
		response.setStatus("success");
		return response;
		
	}
	@GetMapping(value="/schoollist/{id}")
	public ResponseUtil<List<SchoolDetail>> getAllSchoolDetails(@PathVariable("id") String userid){	
		
		ResponseUtil<List<SchoolDetail>> response=new ResponseUtil<>();
		response.setResponseObject(schoolRepository.findAll());
		response.setStatus("success");
		return response;
	}
}
