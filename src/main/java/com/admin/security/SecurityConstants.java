package com.admin.security;

public class SecurityConstants {
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final long EXPIRATION_TIME_ADMIN = 86400; // 10 days
    public static final long EXPIRATION_TIME_EMPLOYEE = 86400000; // 10 days
    
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/admin/register";
    public static final String SIGN_UP_URL_EMPLOYEE = "/employee/register";
    
    
    
}