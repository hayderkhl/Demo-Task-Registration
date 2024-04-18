package com.example.demotaskregistration.controller;

import com.example.demotaskregistration.dto.BorrowedBookDto;
import com.example.demotaskregistration.models.BorrowedBook;
import com.example.demotaskregistration.service.BorrowedBookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/borrowed-books")
public class BorrowedBookController {

    private final BorrowedBookService borrowedBookService;

    public BorrowedBookController(BorrowedBookService borrowedBookService) {
        this.borrowedBookService = borrowedBookService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<BorrowedBookDto>> getAllBorrowedBooks() {
        List<BorrowedBookDto> borrowedBooks = borrowedBookService.getAllBorrowedBooks();
        return ResponseEntity.ok(borrowedBooks);
    }

    @GetMapping("/{borrowedBookId}")
    public ResponseEntity<BorrowedBookDto> getBorrowedBookById(@PathVariable Long borrowedBookId) {
        BorrowedBookDto borrowedBook = borrowedBookService.getBorrowedBookById(borrowedBookId);
        return ResponseEntity.ok(borrowedBook);
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@RequestBody BorrowedBookDto borrowedBookDto) {
        ResponseEntity<String> response = borrowedBookService.borrowBook(borrowedBookDto);
        return ResponseEntity.ok(response.getBody());
    }

    @PutMapping("/return/{borrowedBookId}")
    public ResponseEntity<String> returnBook(@PathVariable Long borrowedBookId) {
        borrowedBookService.returnBook(borrowedBookId);
        return ResponseEntity.ok("Book returned successfully");
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowedBookDto>> getOverdueBooks() {
        List<BorrowedBookDto> overdueBooks = borrowedBookService.findOverdueBooks();
        return ResponseEntity.ok(overdueBooks);
    }

    @GetMapping("/borrowedBook-byUser/{identityNumber}")
    public ResponseEntity<List<BorrowedBookDto>> getBorrowedBooksByUserIdentityNumber(@PathVariable String identityNumber) {
        List<BorrowedBook> borrowedBooks = borrowedBookService.findBorrowedBooksByUserIdentityNumber(identityNumber);
        List<BorrowedBookDto> borrowedBookDtos = borrowedBooks.stream()
                .map(BorrowedBookDto::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(borrowedBookDtos);
    }
}
