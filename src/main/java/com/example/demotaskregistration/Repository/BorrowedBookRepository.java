package com.example.demotaskregistration.Repository;

import com.example.demotaskregistration.models.BorrowedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface BorrowedBookRepository extends JpaRepository<BorrowedBook, Long> {

    @Query("SELECT b FROM BorrowedBook b WHERE b.returnDate < :currentDate AND b.status = :status")
    List<BorrowedBook> findOverdueBooks(Date currentDate, String status);

    List<BorrowedBook> findAllByBookId(Long id);


//    findByReturnDateBeforeAndStatus is a method name derived from Spring Data JPA's method naming conventions. These conventions allow you to declare query methods in your repository interfaces without writing the query explicitly.
//
//    Here's a breakdown of the method name findByReturnDateBeforeAndStatus:
//
//    findBy: This prefix indicates that the method will retrieve entities based on certain criteria.
//            ReturnDate: This part of the method name specifies the property of the entity by which we want to filter the results. In this case, it refers to the returnDate attribute of the BorrowedBook entity.
//    Before: This is a keyword that signals to Spring Data JPA that we want to filter entities where the returnDate is before a certain value.
//    And: This keyword is used to combine multiple conditions in the query method.
//    Status: This part of the method name specifies another property of the entity by which we want to filter the results. In this case, it refers to the status attribute of the BorrowedBook entity.
//    So, when you call findByReturnDateBeforeAndStatus(currentDate, "Not returned"), Spring Data JPA will automatically generate a query to retrieve borrowed books where the returnDate is before currentDate and the status is "Not returned".
//
//    This method naming convention is powerful because it allows you to write concise and readable query methods without having to write SQL or JPQL queries explicitly, while still providing flexibility and customization options when needed.

}
