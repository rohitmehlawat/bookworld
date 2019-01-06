package com.admin.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.admin.domain.Employee;
import com.admin.domain.UserDetail;
import com.admin.repository.EmployeeRepository;
import com.admin.repository.UserRepository;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
    private UserRepository userRepository;
	@Autowired
    private EmployeeRepository employeeRepository;

    /*public UserDetailsServiceImpl(UserRepository userRepository,EmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.employeeRepository=employeeRepository;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	
        UserDetail applicationUser = userRepository.findByEmail(username);
        if(applicationUser==null) {
        	Employee employeeUser=employeeRepository.findByPhoneNo(username);
        	if(employeeUser==null) {
        		 throw new UsernameNotFoundException(username);
        	}
        	else {
        		 return new User(employeeUser.getPhoneNo(), employeeUser.getPassword(), emptyList());
        	}
        }
        return new User(applicationUser.getEmail(), applicationUser.getPassword(), emptyList());
    }
}
