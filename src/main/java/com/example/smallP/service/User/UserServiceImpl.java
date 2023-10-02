package com.example.smallP.service.User;


import com.example.smallP.entity.User;
import com.example.smallP.security.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private EntityManager entityManager;
    private UserService userService;

    private UserRepository userRepository;

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
    public User updateUser(int userId, User newData) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            // Kiểm tra và cập nhật các thông tin có thay đổi
            if (newData.getFullName() != null) {
                user.setFullName(newData.getFullName());
            }

            if (newData.getEmail() != null) {
                user.setEmail(newData.getEmail());
            }

            if (newData.getPhone() != null) {
                user.setPhone(newData.getPhone());
            }

            // Lưu thông tin người dùng đã cập nhật
            entityManager.merge(user);

            return user;
        } else {
            throw new RuntimeException("Không tìm thấy người dùng có id: " + userId);
        }
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
