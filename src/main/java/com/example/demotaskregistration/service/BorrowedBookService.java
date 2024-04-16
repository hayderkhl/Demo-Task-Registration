package com.example.demotaskregistration.service;

import com.example.demotaskregistration.dto.BorrowedBookDto;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowedBookService {

    List<BorrowedBookDto> getAllBorrowedBooks();

    BorrowedBookDto getBorrowedBookById(Long id);

    ResponseEntity<String> borrowBook(BorrowedBookDto borrowedBookDto);

    void returnBook(Long id);

    List<BorrowedBookDto> findOverdueBooks();
}
