package com.example.demotaskregistration.service;

import com.example.demotaskregistration.dto.BorrowedBookDto;
import com.example.demotaskregistration.models.BorrowedBook;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BorrowedBookService {

    List<BorrowedBookDto> getAllBorrowedBooks();

    BorrowedBookDto getBorrowedBookById(Long id);

    ResponseEntity<String> borrowBook(BorrowedBookDto borrowedBookDto);

    ResponseEntity<String> returnBook(Long id);

    List<BorrowedBookDto> findOverdueBooks();
    List<BorrowedBook> findBorrowedBooksByUserIdentityNumber(String identityNumber);

    void deleteBorrowedBookById(Long id);
}
