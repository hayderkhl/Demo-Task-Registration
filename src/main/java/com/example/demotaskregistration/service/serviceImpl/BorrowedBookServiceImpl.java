package com.example.demotaskregistration.service.serviceImpl;

import com.example.demotaskregistration.Repository.BookRepository;
import com.example.demotaskregistration.Repository.BorrowedBookRepository;
import com.example.demotaskregistration.Repository.UserRepository;
import com.example.demotaskregistration.dto.BorrowedBookDto;
import com.example.demotaskregistration.exception.EntityNotFoundException;
import com.example.demotaskregistration.exception.ErrorCodes;
import com.example.demotaskregistration.exception.InvalidEntityException;
import com.example.demotaskregistration.exception.UnauthorizedException;
import com.example.demotaskregistration.jwt.JwtFilter;
import com.example.demotaskregistration.models.Book;
import com.example.demotaskregistration.models.BorrowedBook;
import com.example.demotaskregistration.models.User;
import com.example.demotaskregistration.service.BorrowedBookService;
import com.example.demotaskregistration.valadator.BorrowedBookValidator;
import com.example.demotaskregistration.valadator.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BorrowedBookServiceImpl implements BorrowedBookService {

        @Autowired
        JwtFilter jwtFilter;
        private final BorrowedBookRepository borrowedBookRepository;
        private final BookRepository bookRepository;
        private final UserRepository userRepository;

        @Autowired
        public BorrowedBookServiceImpl(BorrowedBookRepository borrowedBookRepository,
                                       BookRepository bookRepository,
                                       UserRepository userRepository) {
            this.borrowedBookRepository = borrowedBookRepository;
            this.bookRepository = bookRepository;
            this.userRepository = userRepository;
        }

        @Override
        public List<BorrowedBookDto> getAllBorrowedBooks() {
            if(jwtFilter.isAdmin()) {
                return borrowedBookRepository.findAll().stream()
                        .map(BorrowedBookDto::fromEntity)
                        .collect(Collectors.toList());
            }
            else throw new UnauthorizedException("Only for ADMIN", ErrorCodes.NOT_AUTHORIZED);
        }

        @Override
        public BorrowedBookDto getBorrowedBookById(Long id) {

            if (jwtFilter.isAdmin()) {
                if (id == null) {
                    log.error("Article is null");
                    return null;
                }
                Optional<BorrowedBook> borrowedBook = borrowedBookRepository.findById(id);
                return Optional.of(BorrowedBookDto.fromEntity(borrowedBook.get()))
                        .orElseThrow(() -> new EntityNotFoundException(
                                "No Borrowed with this ID" + id + " found in DB"
                                , ErrorCodes.BorrowedBook_Not_Found)
                        );
            }
            else throw new UnauthorizedException("Only for ADMIN", ErrorCodes.NOT_AUTHORIZED);
        }

        @Override
        public ResponseEntity<String> borrowBook(BorrowedBookDto dto) {
            // Implement logic to borrow a book
            List<String> errors = BorrowedBookValidator.validate(dto);
            if (!errors.isEmpty())
                {
                    log.error("borrwed book is not valid", dto);
                    throw new InvalidEntityException("this Borrwed book is not valid", ErrorCodes.BORROWED_BOOK_NOT_VALID);
                }

            if (ifBook_ID_Exist(dto.getBook().getId()) && ifUerIDExist(dto.getUser().getId())) {

                dto.setStatus("Not_returned");
                borrowedBookRepository.save(BorrowedBookDto.toEntity(dto));

                return new ResponseEntity<String>("{\"message\":\"" + "Borrowed book successfully registered "+"\"}", HttpStatus.OK);

            } else throw new EntityNotFoundException(
                    "No user and book with this ID" + dto.getUser().getId() + dto.getBook().getId()+ " found in DB"
                    , ErrorCodes.Book_And_User_Not_Found);
        }

    private boolean ifUerIDExist(Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            return true;
        }
        else return false;
    }

    private boolean ifBook_ID_Exist(Long id) {

            Optional<Book> optionalBook = bookRepository.findById(id);
            if (optionalBook.isPresent()) {
                return true;
            }
            else return false;
    }


    @Override
        public void returnBook(Long id) {

            if (id == null) {
                log.error("BorrowedBook ID is null");
                throw new IllegalArgumentException("BorrowedBook ID cannot be null");
            }

            Optional<BorrowedBook> optionalBorrowedBook = borrowedBookRepository.findById(id);
            if (optionalBorrowedBook.isPresent()) {
                BorrowedBook borrowedBook = optionalBorrowedBook.get();
                borrowedBook.setStatus("Returned");
                borrowedBookRepository.save(borrowedBook);
            } else {
                throw new EntityNotFoundException(
                        "No BorrowedBook with ID " + id + " found in the database",
                        ErrorCodes.BorrowedBook_Not_Found
                );
            }
        }

        @Override
        public List<BorrowedBookDto> findOverdueBooks() {
            Date currentDate = new Date();

            return borrowedBookRepository.findOverdueBooks(currentDate, "Not_returned").stream()
                    .map(BorrowedBookDto::fromEntity)
                    .collect(Collectors.toList());
        }

}
