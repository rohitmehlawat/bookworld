package com.admin.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

public class ResponseService {

	   public static void failureResponse(HttpServletResponse response) throws IOException {
	    	ResponseUtil<String> responseObject=new ResponseUtil();
	        responseObject.setStatus("failure");
	        responseObject.setResponseObject("Wrong creditionals");
			PrintWriter out = response.getWriter();
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
		    out.print(new Gson().toJson(responseObject));
		    out.flush();
	    }
	   
}
