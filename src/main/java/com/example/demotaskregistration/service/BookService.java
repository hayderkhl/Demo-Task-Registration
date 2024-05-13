package com.example.demotaskregistration.service;

import com.example.demotaskregistration.dto.BookDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookService {

    ResponseEntity<String> register(BookDto dto);

    List<BookDto> findAll();

    BookDto findById(Integer bookId);

    ResponseEntity<String> delete(Long id);


}
