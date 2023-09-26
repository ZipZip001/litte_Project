package com.example.smallP.service.User;




import com.example.smallP.dao.User.UserDAO;
import com.example.smallP.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserDAO {

    private UserDAO userDAO;

    public UserServiceImpl(UserDAO theUser){
        userDAO = theUser;
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll();
    }

    @Override
    public User findById(int theId) {
        return userDAO.findById(theId);
    }

    @Transactional // you chance something in database
    @Override
    public User save(User theUser) {
        return userDAO.save(theUser);
    }

    @Transactional
    @Override
    public void deleteById(int theId) {
        userDAO.deleteById(theId);
    }
}
