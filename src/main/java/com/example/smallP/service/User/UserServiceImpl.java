package com.example.smallP.service.User;


import com.example.smallP.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private EntityManager entityManager;
    private UserService userService;

    public UserServiceImpl(UserService theUser){
        userService = theUser;
    }

    @Autowired
    public UserServiceImpl(EntityManager theEntityManager){
        entityManager = theEntityManager;
    }
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    public User findById(int theId) {
        return entityManager.find(User.class, theId);
    }

    @Transactional
    @Override
    public User save(User theUser) {
        return userService.save(theUser);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
         User user = entityManager.find(User.class, theId);
         if (user != null) {
             entityManager.remove(user);
         }
    }

    @Override
    public User findByEmail(String email) {
        // Tạo một truy vấn SQL hoặc HQL để tìm người dùng theo địa chỉ email
        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class);
        query.setParameter("email", email);

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            // Trả về null nếu không tìm thấy email nào phù hợp
            return null;
        }
    }

}
