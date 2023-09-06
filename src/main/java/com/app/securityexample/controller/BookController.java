package com.app.securityexample.controller;

import com.app.securityexample.model.BookRequest;
import com.app.securityexample.persistence.BookEntity;
import com.app.securityexample.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(value = "/api/book")
@RequiredArgsConstructor
public class BookController
{

    private final BookService bookService;

    @PostMapping
    @PreAuthorize("@roleChecker.checkRole('testRole1','testRole2')")
    public ResponseEntity<BookEntity> save(@RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(bookService.saveBook(bookRequest));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('user')")
    @Operation(summary = "Get All Books", security = @SecurityRequirement(name = AUTHORIZATION))
    public ResponseEntity<List<BookEntity>> getBooks(){
        return ResponseEntity.ok(bookService.getBooks());
    }

    @PutMapping(value = "/{bookId}")
    @Secured("admin")
    @Operation(summary = "Update Books", security = @SecurityRequirement(name = AUTHORIZATION))
    public ResponseEntity<BookEntity> updateBook(@PathVariable(name = "bookId") long bookId,
                                                 @RequestBody BookRequest bookRequest){
        return ResponseEntity.ok(bookService.updateBook(bookRequest, bookId));
    }

    @GetMapping(value = "/{bookId}")
    public ResponseEntity<BookEntity> getBook(@PathVariable(name = "bookId") long bookId){
        return ResponseEntity.ok(bookService.getBookById(bookId));
    }

    @DeleteMapping(value = "/{bookId}")
    public ResponseEntity<Void> removeBook(@PathVariable(name = "bookId") long bookId){
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
}
