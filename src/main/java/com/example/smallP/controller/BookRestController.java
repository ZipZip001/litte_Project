package com.example.smallP.controller;

import com.example.smallP.ApiResponse;
import com.example.smallP.entity.Book;
import com.example.smallP.service.Book.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookRestController {
    private BookService bookService;


    public BookRestController(BookService theBookService){
        bookService = theBookService;
    }

    @GetMapping("/books")
    public ApiResponse<List<Book>> findAll(){
        List<Book> books = bookService.findAll();

        ApiResponse<List<Book>> response = new ApiResponse<>();
        response.setResult(books);

        return response;
    }

    @GetMapping("/books/{bookId}")
    public Book getBook(@PathVariable int bookId){
        Book theBook = bookService.findById(bookId);

        if (theBook == null){
            throw new RuntimeException("Book id not found -" +theBook);
        }
        else {
            return theBook;
        }
    }

    @PostMapping("/books")
    public Book addUser(@RequestBody Book theBook){

        theBook.setId(0);

        Book dbBook = bookService.save(theBook);

        return dbBook;
    }

    @PutMapping("/books")
    public Book updateBook(@RequestBody Book theBook){

        Book dbBook = bookService.save(theBook);
        return  dbBook;
    }

    @DeleteMapping("/books/{bookId}")
    public String delete(@PathVariable int bookId){

        Book tempUser = bookService.findById(bookId);

        if (tempUser == null){
            throw new RuntimeException("User id not found -" +bookId);
        }

        bookService.deleteById(bookId);

        return "Delete User with id- " +bookId;
    }

}
