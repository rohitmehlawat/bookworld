package com.admin.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.admin.domain.UserDetail;

@Repository
public interface UserRepository extends MongoRepository<UserDetail, String>{

	UserDetail findByPhoneNoAndPassword(String phoneNo,String password);
	
	UserDetail findByPhoneNo(String email);
	
	
}
