package com.example.smallP.controller.Book;

import com.example.smallP.entity.Book;
import com.example.smallP.security.BookRepository;
import com.example.smallP.service.Book.BookService;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
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
            @RequestParam(required = false) Integer current,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false ) String[] category,
            @RequestParam(required = false) String sort,
            @RequestParam(required = false) String maintext
                ){
        ObjectMapper objectMapper = new ObjectMapper();
        List<Book> books = bookService.findAll();

        List<Book> filteredBooks = new ArrayList<>(books);

        // category
        if (category != null && category.length > 0) {
            filteredBooks = filteredBooks.stream()
                    .filter(book -> Arrays.asList(category).contains(book.getCategory()))
                    .collect(Collectors.toList());
        }
//        int totalBooks = books.size();


        if (sort != null) {
            if (sort.equals("-price")) {
                // Sắp xếp từ cao đến thấp dựa trên giá
                filteredBooks.sort(Comparator.comparing(Book::getPrice).reversed());
            } else if (sort.equals("-updatedAt")) {
                // Sắp xếp ngược lại dựa trên updatedAt
                filteredBooks.sort(Comparator.comparing(Book::getUpdateAt).reversed());
            } else if (sort.equals("-sold")) {
                // Sắp xếp ngược lại dựa trên số lượng đã bán (sold)
                filteredBooks.sort(Comparator.comparing(Book::getSold).reversed());
            }
        } else {
            // Mặc định, sắp xếp từ thấp đến cao dựa trên giá
            filteredBooks.sort(Comparator.comparing(Book::getPrice));
        }


        if (maintext != null && !maintext.isEmpty()) {
            String searchText = maintext.toLowerCase(); // Chuyển đổi sang chữ thường để tìm kiếm không phân biệt hoa thường
            filteredBooks = filteredBooks.stream()
                    .filter(book -> book.getMaintext().toLowerCase().contains(searchText))
                    .collect(Collectors.toList());
        }
        int totalBooks = filteredBooks.size();


        // Kiểm tra nếu current và pageSize bị null, thì trả về toàn bộ dữ liệu
            if (current == null || pageSize == null) {
                current = 1; // Giá trị mặc định nếu current bị null
                pageSize = totalBooks; // Trả về tất cả nếu pageSize bị null
            }

        //current and pageSize
        int totalPages = (int) Math.ceil((double) totalBooks / pageSize);

        int startIndex = (current - 1) * pageSize;
        int endIndex = Math.min(startIndex + pageSize, totalBooks);

        List<Book> paginatedBooks = filteredBooks.subList(startIndex, endIndex);

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
    public Book addBook(@RequestBody Book theBook){

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
