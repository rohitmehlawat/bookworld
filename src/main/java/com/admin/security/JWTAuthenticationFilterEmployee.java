package com.admin.security;

import static com.admin.security.SecurityConstants.EXPIRATION_TIME_EMPLOYEE;
import static com.admin.security.SecurityConstants.HEADER_STRING;
import static com.admin.security.SecurityConstants.SECRET;
import static com.admin.security.SecurityConstants.TOKEN_PREFIX;
import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.admin.domain.Employee;
import com.admin.domain.UserDetail;
import com.admin.exception.CustomException;
import com.admin.util.ResponseService;
import com.admin.util.ResponseUtil;
import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JWTAuthenticationFilterEmployee extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager;
	
	JWTAuthenticationFilterEmployee(AuthenticationManager authenticationManager){
		this.authenticationManager=authenticationManager;
	}
	
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res)  {
		Employee creds=null;
		try {
            creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Employee.class);

            if(Long.parseLong(creds.getPhoneNo())>0) {
            	 return authenticationManager.authenticate(
                         new UsernamePasswordAuthenticationToken(
                                 creds.getPhoneNo(),
                                 creds.getPassword(),
                                 new ArrayList<>())
                 );
            }
            else {
            	ResponseService.failureResponse(res);
            	return null;
            }
            
           
        } catch (IOException  | CustomException | NumberFormatException e ) {
        	
        	try {
        		ResponseService.failureResponse(res);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        	return null;
            //throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        String token = JWT.create()
                .withSubject(((User) auth.getPrincipal()).getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME_EMPLOYEE))
                .sign(HMAC512(SECRET.getBytes()));
        res.addHeader("Access-Control-Expose-Headers", "authorization");
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
        
        ResponseUtil<String> responseObject=new ResponseUtil();
        responseObject.setStatus("success");
        responseObject.setResponseObject("Successfully logged in");
		PrintWriter out = res.getWriter();
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
	    out.print(new Gson().toJson(responseObject));
	    out.flush();
    }
    
    @Override
   	protected void unsuccessfulAuthentication(HttpServletRequest request,
   			HttpServletResponse response, AuthenticationException failed)
   			throws IOException, ServletException {
   		SecurityContextHolder.clearContext();

   		ResponseService.failureResponse(response);

   	}
    
}
