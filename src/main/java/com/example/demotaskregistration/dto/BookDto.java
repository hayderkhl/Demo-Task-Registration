package com.example.demotaskregistration.dto;


import com.example.demotaskregistration.models.Book;
import com.example.demotaskregistration.models.User;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String title;
    private String author;
    private String ISBN;
    private String genre;
    private String quantity;

    public static BookDto fromEntity(Book book)
    {
        if (book == null) {return null;}

        return BookDto.builder()
                .id(book.getId())
                .author(book.getAuthor())
                .ISBN(book.getISBN())
                .title(book.getTitle())
                .quantity(book.getQuantity())
                .genre(book.getGenre())
                .build();
    }

    public static Book toEntity(BookDto bookDto)
    {
        if (bookDto == null) {return null;}

        Book book = new Book();
        book.setId(bookDto.getId());
        book.setAuthor(bookDto.getAuthor());
        book.setISBN(bookDto.getISBN());
        book.setTitle(bookDto.getTitle());
        book.setQuantity(bookDto.getQuantity());
        book.setGenre(bookDto.getGenre());

        return book;
    }
}
