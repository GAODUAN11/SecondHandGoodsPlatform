package org.example.secondhandgoodsplatform.service;

import org.example.secondhandgoodsplatform.dao.UserDAO;
import org.example.secondhandgoodsplatform.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public boolean registerUser(String username, String password, String email) {
        // Check if user already exists
        if (userDAO.findByUsername(username) != null) {
            return false; // User already exists
        }

        // Hash the password
        String hashedPassword = PasswordUtil.hashPassword(password);

        // Create new user
        User user = new User(username, hashedPassword, email);
        return userDAO.createUser(user);
    }

    public User authenticateUser(String username, String password) {
        User user = userDAO.findByUsername(username);
        if (user != null) {
            String hashedPassword = PasswordUtil.hashPassword(password);
            if (user.getPassword().equals(hashedPassword)) {
                return user;
            }
        }
        return null;
    }

    public User findById(int id) {
        return userDAO.findById(id);
    }
}