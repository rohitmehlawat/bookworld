package com.admin.controller;

import static com.admin.security.SecurityConstants.HEADER_STRING;
import static com.admin.security.SecurityConstants.SECRET;
import static com.admin.security.SecurityConstants.TOKEN_PREFIX;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.admin.domain.Employee;
import com.admin.domain.SchoolDetail;
import com.admin.domain.UserDetail;
import com.admin.domain.VisitStatus;
import com.admin.exception.CustomException;
import com.admin.repository.EmployeeRepository;
import com.admin.repository.SchoolRepository;
import com.admin.util.AmazonClient;
import com.admin.util.ResponseUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@RestController
@RequestMapping("/school")
public class SchoolController {

	@Autowired
	private AmazonClient amazonClient;
	
	@Autowired
	private SchoolRepository schoolRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@PostMapping("/addSchool")
	public ResponseUtil<String> addSchoolDetail(@RequestBody SchoolDetail schoolDetail, HttpServletRequest request) {
		ResponseUtil<String> response = new ResponseUtil<>();
		System.out.println(schoolDetail);
		schoolDetail.setDealStatus("pending");
		String user = (String) request.getAttribute("user");
		if (user.contains("@")) {
			UserDetail userDetail = new UserDetail();
			userDetail.setEmail(user);
			schoolDetail.setUserDetail(userDetail);
			schoolRepository.save(schoolDetail);
			response.setResponseObject("sucessfully added");
			response.setStatus("success");
		}
		else {
			response.setResponseObject("not allowed for the user");
			response.setStatus("failure");
		}
		return response;
	}

	@PostMapping("/updateSchool")
	public ResponseUtil<String> updateSchoolDetail(@RequestBody SchoolDetail schoolDetail, HttpServletRequest request) {
		ResponseUtil<String> response = new ResponseUtil<>();
		System.out.println(schoolDetail);
		String user = (String) request.getAttribute("user");
		if (user.contains("@")) {
			UserDetail userDetail = new UserDetail();
			userDetail.setEmail(user);
			schoolDetail.setUserDetail(userDetail);
			schoolRepository.save(schoolDetail);
			response.setResponseObject("sucessfully added");
			response.setStatus("success");
		}
		else {
			response.setResponseObject("not allowed for the user");
			response.setStatus("failure");
		}
		return response;
	}
	
	@PutMapping("/updateSchool/{id}/{empId}")
	public ResponseUtil<String> assignSchool(@PathVariable("id") String id, @PathVariable("empId") String empId) {
		ResponseUtil<String> response = new ResponseUtil<>();
		Optional<SchoolDetail> schoolDetail=schoolRepository.findById(id);
		Optional<Employee> assignedEmp=employeeRepository.findById(empId);
		if(schoolDetail.isPresent() && assignedEmp.isPresent()) {
			schoolDetail.get().setAssignedEmp(assignedEmp.get());
			schoolRepository.save(schoolDetail.get());
			response.setResponseObject("school assigned to employee");
			response.setStatus("success");
		}	
		else {
			response.setResponseObject("school not assigned to employee");
			response.setStatus("failure");
		}
		return response;
	}
	
	// for Android
	@GetMapping(value = "/schoollist/{empId}")
	public ResponseUtil<List<SchoolDetail>> getSchoolDetails(@PathVariable("empId") String employeeId) {

		ResponseUtil<List<SchoolDetail>> response = new ResponseUtil<>();
		response.setResponseObject(schoolRepository.findByAssignedEmp(employeeId));
		response.setStatus("success");
		return response;
	}
	
	// for Android
	@PutMapping(value="/schoollVist/{schoolId}")
	public ResponseUtil<String> updateSchoolVist(@PathVariable("schoolId") String schoolId,@RequestPart(value = "image") MultipartFile file, @RequestParam Map<String,Object> mapObject){
		
		ResponseUtil<String> response=new ResponseUtil<>();
		VisitStatus visitStatus=null;
		try {
		visitStatus=new VisitStatus().asMap(mapObject);
		}
		catch(CustomException e) {
			e.printStackTrace();
			response.setStatus("failure");
			response.setResponseObject(e.getMessage());	
			return response;
		}
		Optional<SchoolDetail> school=schoolRepository.findById(schoolId);
		if(school!=null) {
			String url=this.amazonClient.uploadFile(file);
			visitStatus.setImageUrl(url);
			school.get().setVisitStatus(visitStatus);
			schoolRepository.save(school.get());
			response.setStatus("success");
			response.setResponseObject("school visit status updated");
		}
		else {
			response.setStatus("failure");
			response.setResponseObject("school visit status not updated");
		}
		
		return response;
	}
	
	
	@GetMapping(value = "/getAllSchool")
	public ResponseUtil<List<SchoolDetail>> getAllSchoolDetails() {
		ResponseUtil<List<SchoolDetail>> response = new ResponseUtil<>();
		response.setResponseObject(schoolRepository.findAll());
		response.setStatus("success");
		return response;
	}

}
