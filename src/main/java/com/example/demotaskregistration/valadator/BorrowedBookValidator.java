package com.example.demotaskregistration.valadator;

import com.example.demotaskregistration.dto.BorrowedBookDto;
import java.util.ArrayList;
import java.util.List;

public class BorrowedBookValidator {

    public static List<String> validate(BorrowedBookDto dto)
    {
        List<String> errors = new ArrayList<>();

        if (dto == null)
        {
            errors.add("please add a the book to borrow");
        }
        if (dto.getUser() != null)
        {
            errors.add("add the name!");
        }
        if (dto.getBook()!= null)
        {
            errors.add("add the name!");
        }
        if (dto.getBorrowDate() == null || dto.getBorrowDate().toString().isEmpty()) {
            errors.add("Add the borrow date!");
        }

        if (dto.getReturnDate() == null || dto.getReturnDate().toString().isEmpty()) {
            errors.add("Add the return date!");
        }

        return errors;
    }
}
