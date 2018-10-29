package com.admin.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.admin.domain.Book;

public interface BookRepository extends MongoRepository<Book, String>{

}
