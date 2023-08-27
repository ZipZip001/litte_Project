package com.example.smallP.dao;

import com.example.smallP.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOJpaImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    public UserDAOJpaImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<User> findAll() {

        // create a query
        TypedQuery<User> theQuery = entityManager.createQuery("from User", User.class);

        // execute query and get result list
        List<User> employees = theQuery.getResultList();

        return employees;
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
        User dbEmployee = entityManager.merge((theUser)); // insert id = 0  and update if id > 0

        return dbEmployee;
    }

    @Override
    public void deleteById(int theId) {
        User theUser = entityManager.find(User.class, theId);

        entityManager.remove(theUser);
    }
}
