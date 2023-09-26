package com.example.smallP.dao.User;

import com.example.smallP.dao.Book.BookDAO;
import com.example.smallP.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDAOJpalmpl implements UserDAO {

    private EntityManager entityManager;

    public class ResultWrapper {
        private List<User> result;

        public ResultWrapper(List<User> result) {
            this.result = result;
        }

        public List<User> getResult() {
            return result;
        }

        public void setResult(List<User> result) {
            this.result = result;
        }
    }
    @Autowired
    public UserDAOJpalmpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<User> findAll() {

        // create a query
        TypedQuery<User> theQuery = entityManager.createQuery("from User", User.class);

        // execute query and get result list
        List<User> allUser = theQuery.getResultList();


        return allUser;
    }

    @Override
    public User findById(int theId) {

        //get employee
        User theUser = entityManager.find(User.class, theId);

        // return employee
        return theUser;
    }

    @Override
    public User save(User theUser) {
        User dbUser = entityManager.merge((theUser)); // insert id = 0  and update if id > 0

        return dbUser;
    }

    @Override
    public void deleteById(int theId) {
        User theUser = entityManager.find(User.class, theId);

        entityManager.remove(theUser);
    }
}

