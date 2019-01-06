package com.admin.repository;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.admin.domain.UserDetail;

@Repository
public interface UserRepository extends MongoRepository<UserDetail, String>{

	//UserDetail find(String email);
	
	UserDetail findByEmailAndPassword(String email,String password);
	
	UserDetail findByEmail(String email);
	
	boolean existsByEmail(String email);
	
}
