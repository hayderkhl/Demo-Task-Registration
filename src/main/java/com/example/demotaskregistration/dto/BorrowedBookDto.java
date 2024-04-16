package com.example.demotaskregistration.dto;

import com.example.demotaskregistration.models.BorrowedBook;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BorrowedBookDto {

    private Long id;
    private BookDto book;
    private UserDto user;
    private Date borrowDate;
    private Date returnDate;
    private String status;

    public static BorrowedBookDto fromEntity(BorrowedBook borrowedBook) {
        if (borrowedBook == null) {
            return null;
        }

        return BorrowedBookDto.builder()
                .id(borrowedBook.getId())
                .book(BookDto.fromEntity(borrowedBook.getBook()))
                .user(UserDto.fromEntity(borrowedBook.getUser()))
                .borrowDate(borrowedBook.getBorrowDate())
                .returnDate(borrowedBook.getReturnDate())
                .status(borrowedBook.getStatus())
                .build();
    }

    public static BorrowedBook toEntity(BorrowedBookDto borrowedBookDto) {
        if (borrowedBookDto == null) {
            return null;
        }

        BorrowedBook borrowedBook = new BorrowedBook();
        borrowedBook.setId(borrowedBookDto.getId());
        borrowedBook.setBook(BookDto.toEntity(borrowedBookDto.getBook()));
        borrowedBook.setUser(UserDto.toEntity(borrowedBookDto.getUser()));
        borrowedBook.setBorrowDate(borrowedBookDto.getBorrowDate());
        borrowedBook.setReturnDate(borrowedBookDto.getReturnDate());
        borrowedBook.setStatus(borrowedBookDto.getStatus());

        return borrowedBook;
    }
}