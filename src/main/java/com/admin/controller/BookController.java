package com.admin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.admin.domain.Book;
import com.admin.repository.BookRepository;
import com.admin.util.ResponseUtil;

@RestController
public class BookController {

	@Autowired
	BookRepository bookRepository;
	
	@PostMapping("/addBook")
	public ResponseUtil<String> addBookDetail(@RequestBody Book book) {
		ResponseUtil<String> response=new ResponseUtil<>();
		if(bookRepository.save(book)!=null) {
			response.setStatus("success");
		}
		else {
			response.setStatus("failure");
		}
		
		return response;
	}
	
	@GetMapping("/booklist")
	public ResponseUtil<List<Book>> getAllBooks(){
		ResponseUtil<List<Book>> response=new ResponseUtil<>();
		response.setResponseObject(bookRepository.findAll());
		response.setStatus("success");
		return response;
	}
}
