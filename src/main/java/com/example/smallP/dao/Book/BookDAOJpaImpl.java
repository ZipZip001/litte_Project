package com.example.smallP.dao.Book;

import com.example.smallP.dao.Book.BookDAO;
import com.example.smallP.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookDAOJpaImpl implements BookDAO {

    private EntityManager entityManager;

    public class ResultWrapper {
        private List<Book> result;

        public ResultWrapper(List<Book> result) {
            this.result = result;
        }

        public List<Book> getResult() {
            return result;
        }

        public void setResult(List<Book> result) {
            this.result = result;
        }
    }
    @Autowired
    public BookDAOJpaImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<Book> findAll() {

        // create a query
        TypedQuery<Book> theQuery = entityManager.createQuery("from Book", Book.class);

        // execute query and get result list
        List<Book> allBook = theQuery.getResultList();


        return allBook;
    }

    @Override
    public Book findById(int theId) {

        //get employee
        Book theBook = entityManager.find(Book.class, theId);

        // return employee
        return theBook;
    }

    @Override
    public Book save(Book theBook) {
        Book dbBook = entityManager.merge((theBook)); // insert id = 0  and update if id > 0

        return dbBook;
    }

    @Override
    public void deleteById(int theId) {
        Book theBook = entityManager.find(Book.class, theId);

        entityManager.remove(theBook);
    }
}
