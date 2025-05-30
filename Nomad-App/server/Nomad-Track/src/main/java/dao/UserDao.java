package com.techelevator.dao;

import com.techelevator.model.User;
import com.techelevator.model.dto.UserClient;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int userId);

    User getUserByUsername(String username);

    User createUser(User newUser);

    UserClient getUserClient(String username);
    User updateUser(UserClient changedUser, UserClient userClient);
}
