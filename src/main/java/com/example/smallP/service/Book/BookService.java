package com.example.smallP.service.Book;

import com.example.smallP.entity.Book;
import com.example.smallP.entity.User;

import java.util.List;

public interface BookService {
    List<Book> findAll();

    Book findById (int theId);

    Book save (Book theBook);

    void deleteById(int theId);

    Book updateBook(int bookId, Book newData);
}
