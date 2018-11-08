package com.admin.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.admin.domain.SchoolDetail;

@Repository
public interface SchoolRepository extends MongoRepository<SchoolDetail, String> {
	

}
