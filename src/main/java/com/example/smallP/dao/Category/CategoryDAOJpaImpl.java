package com.example.smallP.dao.Category;

import com.example.smallP.entity.Category;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CategoryDAOJpaImpl implements CategoryDAO {

    private EntityManager entityManager;

    @Autowired
    public CategoryDAOJpaImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<Category> findAll() {

        // create a query
        TypedQuery<Category> theQuery = entityManager.createQuery("from Category", Category.class);

        // execute query and get result list
        List<Category> employees = theQuery.getResultList();

        return employees;
    }

    @Override
    public Category findById(int theId) {

        //get employee
        Category theCategory = entityManager.find(Category.class, theId);

        // return employee
        return theCategory;
    }

    @Override
    public Category save(Category theCategory) {
        Category dbCategory = entityManager.merge((theCategory)); // insert id = 0  and update if id > 0

        return dbCategory;
    }

    @Override
    public void deleteById(int theId) {
        Category theCategory = entityManager.find(Category.class, theId);

        entityManager.remove(theCategory);
    }
}
