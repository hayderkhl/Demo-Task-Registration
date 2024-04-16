package com.example.demotaskregistration.valadator;

import com.example.demotaskregistration.dto.BookDto;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class BookValidator {

    public static List<String> validate(BookDto dto)
    {
        List<String> errors = new ArrayList<>();

        if (dto == null)
        {
            errors.add("please add a book");
        }
        if (!StringUtils.hasLength(dto.getAuthor()))
        {
            errors.add("add the Author of the book!");
        }
        if (!StringUtils.hasLength(dto.getISBN()))
        {
            errors.add("add the ISBN of the book!");
        }
        if (!StringUtils.hasLength(dto.getGenre()))
        {
            errors.add("add the genre of the book!");
        }
        if (!StringUtils.hasLength(dto.getTitle()))
        {
            errors.add("add the title of the book!");
        }
        if (!StringUtils.hasLength(dto.getQuantity()))
        {
            errors.add("add the quantity of the book!");
        }
        return errors;
    }
}
