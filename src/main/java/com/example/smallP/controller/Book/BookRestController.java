package com.example.smallP.controller.Book;

import com.example.smallP.entity.Book;
import com.example.smallP.service.Book.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookRestController {
    private BookService bookService;


    public BookRestController(BookService theBookService){
        bookService = theBookService;
    }

    @GetMapping("/books")
    public BookResponse<List<Book>> findAll(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false ) String category
                ){
            List<Book> books = bookService.findAll();
            // category
            if (category != null && !category.isEmpty()) {
                books = books.stream()
                        .filter(book -> category.equals(book.getCategory()))
                        .collect(Collectors.toList());
            }

            //current and pageSize
            int totalBooks = books.size();
            int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

            int startIndex = (current - 1) * pageSize;
            int endIndex = Math.min(startIndex + pageSize, totalBooks);

            List<Book> paginatedBooks = books.subList(startIndex, endIndex);

            BookResponse<List<Book>> response = new BookResponse<>();
            BookResponse.Meta meta = new BookResponse.Meta(current, pageSize, totalPages, totalBooks);
            response.setMeta(meta);
            response.setResult(paginatedBooks);

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
