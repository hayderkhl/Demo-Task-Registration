package com.example.demotaskregistration.controller;

import com.example.demotaskregistration.dto.BookDto;
import com.example.demotaskregistration.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerBook(@RequestBody BookDto dto) {
        try {
            return bookService.register(dto);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("register failed: Invalid credentials");
        }
    }

    @GetMapping("/all")
    public List<BookDto> getAllBooks() {
        try {
            return bookService.findAll();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only for Admin Or your Token Expired");
        }
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable("id") Integer bookId) {
        try {
            return bookService.findById(bookId);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book Id Not Found");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") Integer id) {
        try {
            return bookService.delete(id);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Book Id Not Found");
        }
    }
}
