package com.example.smallP.service.Book;

import com.example.smallP.dao.Book.BookDAO;


import com.example.smallP.entity.Book;
import com.example.smallP.service.Book.BookService;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {


    private BookDAO bookDAO;
    @Autowired
    public BookServiceImpl(BookDAO theBook){
        bookDAO = theBook;
    }

    private EntityManager entityManager;

    public BookServiceImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
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
    public Book updateBook(int bookId, Book newData) {
        Book book = entityManager.find(Book.class, bookId);
        if (book != null) {
            // Kiểm tra và cập nhật các thông tin có thay đổi
            if (newData.getThumnail() != null) {
                book.setThumnail(newData.getThumnail());
            }
            if (newData.getMaintext() != null) {
                book.setMaintext(newData.getMaintext());
            }
            if (newData.getCategory() != null) {
                book.setCategory(newData.getCategory());
            }
            if (newData.getAuthor() != null) {
                book.setThumnail(newData.getAuthor());
            }
            if (!Double.isNaN(newData.getPrice())) {
                book.setPrice(newData.getPrice());
            }
            if (!Double.isNaN(newData.getQuantity())) {
                book.setQuantity(newData.getQuantity());
            }


            // Lưu thông tin người dùng đã cập nhật
            entityManager.merge(book);

            return book;
        } else {
            throw new RuntimeException("Không tìm thấy người dùng có id: " + bookId);
        }
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        bookDAO.deleteById(theId);
    }
}
