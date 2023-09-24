package com.example.smallP.controller.Book;

import com.example.smallP.entity.Book;
import com.example.smallP.service.Book.BookService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BookRestController {
    private BookService bookService;


    public BookRestController(BookService theBookService){
        bookService = theBookService;
    }

    @GetMapping("/book")
    public ResponseEntity<ObjectNode> findAll(
            @RequestParam(defaultValue = "1") int current,
            @RequestParam(defaultValue = "5") int pageSize,
            @RequestParam(required = false ) String[] category
                ){
            ObjectMapper objectMapper = new ObjectMapper();
            List<Book> books = bookService.findAll();
            // category
            if (category != null && category.length > 0) {
                books = books.stream()
                        .filter(book -> Arrays.asList(category).contains(book.getCategory()))
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


            // Đặt "data" bên trong một đối tượng JSON bổ sung
            ObjectNode dataNode = JsonNodeFactory.instance.objectNode();
            dataNode.set("data", objectMapper.valueToTree(response));

            return new ResponseEntity<>(dataNode, HttpStatus.OK);
    }

    @GetMapping("/book/{bookId}")
    public ResponseEntity<ObjectNode> getBook(@PathVariable int bookId){
        Book theBook = bookService.findById(bookId);

        ObjectMapper objectMapper = new ObjectMapper();
        // Đặt "data" bên trong một đối tượng JSON bổ sung
        ObjectNode dataNode = JsonNodeFactory.instance.objectNode();
        dataNode.set("data", objectMapper.valueToTree(theBook));

        if (dataNode == null){
            throw new RuntimeException("Book id not found -" +dataNode);
        }
        else {
            return new ResponseEntity<>(dataNode, HttpStatus.OK);
        }
    }

    @PostMapping("/book")
    public Book addUser(@RequestBody Book theBook){

        theBook.setId(0);

        Book dbBook = bookService.save(theBook);

        return dbBook;
    }

    @PutMapping("/book")
    public Book updateBook(@RequestBody Book theBook){

        Book dbBook = bookService.save(theBook);
        return  dbBook;
    }

    @DeleteMapping("/book/{bookId}")
    public String delete(@PathVariable int bookId){

        Book tempUser = bookService.findById(bookId);

        if (tempUser == null){
            throw new RuntimeException("User id not found -" +bookId);
        }

        bookService.deleteById(bookId);

        return "Delete User with id- " +bookId;
    }

}
