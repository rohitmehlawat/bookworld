package com.admin.domain;



import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection="school_details")
public class SchoolDetail {

	@Id
	private String id;
	private String name;
	private String email;
	private String website;
	private Address address;
	private ContactDetail contact;
	private String dealStatus;
	@DBRef
	@JsonIgnore
	private UserDetail userDetail;
	
	@DBRef
	@JsonIgnore
	private Employee assignedEmp;
	
	private VisitStatus visitStatus;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getDealStatus() {
		return dealStatus;
	}
	public void setDealStatus(String dealStatus) {
		this.dealStatus = dealStatus;
	}
	public ContactDetail getContact() {
		return contact;
	}
	public void setContact(ContactDetail contact) {
		this.contact = contact;
	}
	public UserDetail getUserDetail() {
		return userDetail;
	}
	public void setUserDetail(UserDetail userDetail) {
		this.userDetail = userDetail;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Employee getAssignedEmp() {
		return assignedEmp;
	}
	public void setAssignedEmp(Employee assignedEmp) {
		this.assignedEmp = assignedEmp;
	}
	public VisitStatus getVisitStatus() {
		return visitStatus;
	}
	public void setVisitStatus(VisitStatus visitStatus) {
		this.visitStatus = visitStatus;
	}

}
