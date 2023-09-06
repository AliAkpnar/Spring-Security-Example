package com.app.securityexample.service.impl;

import com.app.securityexample.model.BookRequest;
import com.app.securityexample.model.mapper.BookMapper;
import com.app.securityexample.persistence.BookEntity;
import com.app.securityexample.persistence.repository.BookRepository;
import com.app.securityexample.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService
{

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public BookEntity saveBook(BookRequest bookRequest) {
        return savedBook(bookRequest);
    }

    @Override
    public List<BookEntity> getBooks() {
        return bookRepository.findAll();
    }

    @Override
    public BookEntity updateBook(BookRequest bookRequest, long bookId) {
        return updatedBook(bookRequest,bookId);
    }

    @Override
    public BookEntity getBookById(long bookId) {
       return getBook(bookId);
    }

    @Override
    public void deleteBook(long bookId) {
        BookEntity book = getBook(bookId);
        bookRepository.delete(book);
    }

    private BookEntity getBook(long bookId){
        Optional<BookEntity> book =  bookRepository.findById(bookId);
        if (book.isEmpty()){
            throw new IllegalArgumentException("Not found!!!!");
        }
        return book.get();
    }

    private BookEntity savedBook(BookRequest bookRequest) {
        //BookEntity bookEntity = bookMapper.toBook(bookRequest);
        BookEntity bookEntity = new BookEntity();
        bookEntity.setName(bookRequest.getName());
        bookEntity.setDescription(bookRequest.getDescription());
        bookRepository.save(bookEntity);
        return bookEntity;
    }

    private BookEntity updatedBook(BookRequest bookRequest, long bookId) {
        BookEntity bookEntity = getBook(bookId);
        bookMapper.updateBookEntityFromRequest(bookRequest, bookEntity);
        return bookRepository.save(bookEntity);
    }
}
