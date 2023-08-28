package com.example.smallP.service.Book;

import com.example.smallP.dao.Book.BookDAO;


import com.example.smallP.entity.Book;
import com.example.smallP.service.Book.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    private BookDAO bookDAO;

    public BookServiceImpl(BookDAO theBook){
        bookDAO = theBook;
    }

    @Override
    public List<Book> findAll() {
        return bookDAO.findAll();
    }

    @Override
    public Book findById(int theId) {
        return bookDAO.findById(theId);
    }

    @Transactional // you chance something in database
    @Override
    public Book save(Book theBook) {
        return bookDAO.save(theBook);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        bookDAO.deleteById(theId);
    }
}
