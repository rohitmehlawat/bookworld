package com.admin.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.admin.exception.CustomException;
import com.fasterxml.jackson.annotation.JsonFormat;

public class VisitStatus {

	private boolean status;
	
	private String imageUrl;
	
	private Date dateTime;
	
	private Date appointmentTime;
	
	private String msg;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	public VisitStatus asMap(Map<String,Object> mapObj) throws CustomException {
		VisitStatus visitStatus=new VisitStatus();
		if(mapObj.containsKey("appointmentTime") && mapObj.get("appointmentTime") instanceof String) {
			SimpleDateFormat df=new SimpleDateFormat();
			df.applyPattern("dd-MM-YYYY HH:mm:ss");			
			try {
				visitStatus.setAppointmentTime(df.parse(mapObj.get("appointmentTime").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CustomException("Appointment Time should be in format dd-MM-YYYY HH:mm:ss",null);
			}
		}
		if(mapObj.containsKey("dateTime") && mapObj.get("dateTime") instanceof String) {
			SimpleDateFormat df=new SimpleDateFormat();
			df.applyPattern("dd-MM-YYYY HH:mm:ss");			
			try {
				visitStatus.setDateTime(df.parse(mapObj.get("dateTime").toString()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CustomException("Date Time should be in format dd-MM-YYYY HH:mm:ss",null);
			}
		}
		if(mapObj.containsKey("status") && mapObj.get("status") instanceof String) {
			visitStatus.setStatus(Boolean.parseBoolean(mapObj.get("status").toString()));
		}
		else {
			throw new CustomException("Status is not available",null);
		}
		
		if(mapObj.containsKey("msg") && mapObj.get("msg") instanceof String) {
			visitStatus.setMsg(mapObj.get("msg").toString());
		}
		
		return visitStatus;
	}
	
	
}
