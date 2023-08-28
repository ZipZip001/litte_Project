package com.example.smallP.dao.Book;

import com.example.smallP.entity.Book;
import com.example.smallP.entity.User;

import java.util.List;

public interface BookDAO {
    List<Book> findAll();

    Book findById (int theId);

    Book save (Book theBook);

    void deleteById(int theId);
}
