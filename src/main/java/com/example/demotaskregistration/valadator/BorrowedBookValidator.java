package com.example.demotaskregistration.valadator;

import com.example.demotaskregistration.dto.BorrowedBookDto;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BorrowedBookValidator {

    public static List<String> validate(BorrowedBookDto dto)
    {
        List<String> errors = new ArrayList<>();

        if (dto == null)
        {
            errors.add("please add a the book to borrow");
            log.info("dto empty");
        }
//        if (dto.getUser() != null)
//        {
//            errors.add("add the name!");
//            log.info("user in valid");
//        }
//
//        if (dto.getBook()!= null)
//        {
//            errors.add("add the name!");
//            log.info("book invalid");
//        }
        if (dto.getBorrowDate() == null) {
            errors.add("Add the borrow date!");
            log.info("borrow date invalid");
        }

        if (dto.getReturnDate() == null) {
            errors.add("Add the return date!");
            log.info("retern date invalid");
        }

        return errors;
    }
}
