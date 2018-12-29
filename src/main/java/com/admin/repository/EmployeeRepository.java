package com.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.admin.domain.Employee;

@Repository
public interface EmployeeRepository extends MongoRepository<Employee, String> {

	
}
