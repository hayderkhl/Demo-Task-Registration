package com.example.demotaskregistration.valadator;

import com.example.demotaskregistration.dto.BorrowedBookDto;
import org.springframework.util.StringUtils;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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
        if (!StringUtils.hasLength(dto.getUserName()))
        {
            errors.add("add the name!");
        }
        if (!StringUtils.hasLength((CharSequence) dto.getBorrowDate())) {
            errors.add("Add the borrow date!");
        } else {
            try {
                LocalDate.parse((CharSequence) dto.getBorrowDate()); // Attempt to parse the date string
            } catch (DateTimeParseException e) {
                errors.add("Invalid borrow date format. Please use yyyy-MM-dd format."); // Add error message if parsing fails
            }
        }
        if (!StringUtils.hasLength((CharSequence) dto.getReturnDate())) {
            errors.add("Add the return date!");
        } else {
            try {
                LocalDate.parse((CharSequence) dto.getReturnDate()); // Attempt to parse the date string
            } catch (DateTimeParseException e) {
                errors.add("Invalid return date format. Please use yyyy-MM-dd format."); // Add error message if parsing fails
            }
        }
        return errors;
    }
}
