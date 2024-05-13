package com.example.demotaskregistration.service.serviceImpl;

import com.example.demotaskregistration.Repository.BookRepository;
import com.example.demotaskregistration.Repository.BorrowedBookRepository;
import com.example.demotaskregistration.dto.BookDto;
import com.example.demotaskregistration.dto.BorrowedBookDto;
import com.example.demotaskregistration.exception.EntityNotFoundException;
import com.example.demotaskregistration.exception.ErrorCodes;
import com.example.demotaskregistration.exception.InvalidEntityException;
import com.example.demotaskregistration.exception.UnauthorizedException;
import com.example.demotaskregistration.jwt.JwtFilter;
import com.example.demotaskregistration.models.Book;
import com.example.demotaskregistration.models.BorrowedBook;
import com.example.demotaskregistration.service.BookService;
import com.example.demotaskregistration.valadator.BookValidator;
import com.example.demotaskregistration.valadator.BorrowedBookValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    @Autowired
    JwtFilter jwtFilter;

    private final BookRepository bookRepository;
    private final BorrowedBookRepository borrowedBookRepository;


    public BookServiceImpl(BookRepository bookRepository, BorrowedBookRepository borrowedBookRepository) {
        this.bookRepository = bookRepository;
        this.borrowedBookRepository = borrowedBookRepository;
    }

    @Override
    public ResponseEntity<String> register(BookDto dto) {
        List<String> errors = BookValidator.validate(dto);
        if (!errors.isEmpty())
        {
            log.error("book is not valid", dto);
            throw new InvalidEntityException("this book is not valid", ErrorCodes.BOOK_NOT_VALID);
        }

        bookRepository.save(BookDto.toEntity(dto));
        return new ResponseEntity<String>("{\"message\":\"" + "book successfully registered "+"\"}", HttpStatus.OK);
    }

    @Override
    public List<BookDto> findAll() {
        if (jwtFilter.isAdmin()){
            return bookRepository.findAll().stream()
                    .map(BookDto::fromEntity).collect(Collectors.toList());
        }
        else throw new UnauthorizedException("Only for ADMIN", ErrorCodes.NOT_AUTHORIZED);
    }

    @Override
    public BookDto findById(Integer bookId) {

            if (jwtFilter.isAdmin()) {
                if (bookId == null) {
                    log.error("Article is null");
                    return null;
                }
                Optional<Book> optionalBook = bookRepository.findById(Long.valueOf(bookId));
                return Optional.of(BookDto.fromEntity(optionalBook.get()))
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No Borrowed with this ID" + bookId + " found in DB"
                                , ErrorCodes.Book_Not_Found)
                        );
            }
            else throw new UnauthorizedException("Only for ADMIN", ErrorCodes.NOT_AUTHORIZED);
        }

    @Override
    public ResponseEntity<String> delete(Long id) {
        if (jwtFilter.isAdmin()) {
            if (id == null) {
                log.error("Article is null");
                return null;
            }
            List<BorrowedBook> optionalBorrowedBook = borrowedBookRepository.findAllByBookId(Long.valueOf(id));
            if (!optionalBorrowedBook.isEmpty()) {
                throw new InvalidEntityException("Impossible de supprimer un article deja utilise dans des ventes",
                        ErrorCodes.THIS_BOOK_ALREADY_IN_USE);
            } else

            bookRepository.deleteById(Long.valueOf(id));
        }
        return new ResponseEntity<String>("{\"message\":\"" + "book successfully deleted "+"\"}", HttpStatus.OK);
    }
}
