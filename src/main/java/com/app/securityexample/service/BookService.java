package com.app.securityexample.service;

import com.app.securityexample.model.BookRequest;
import com.app.securityexample.persistence.BookEntity;

import java.util.List;

public interface BookService
{
    BookEntity saveBook(BookRequest bookRequest);

    List<BookEntity> getBooks();

    BookEntity updateBook(BookRequest bookRequest, long bookId);

    BookEntity getBookById(long bookId);

    void deleteBook(long bookId);
}
