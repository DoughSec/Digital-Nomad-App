package com.nomad.dao;

import com.nomad.model.User;
import com.nomad.model.dto.UserClient;

import java.util.List;

public interface UserDao {

    List<User> getUsers();

    User getUserById(int userId);

    User getUserByUsername(String username);

    User createUser(User newUser);

    UserClient getUserClient(String username);
    User updateUser(UserClient changedUser, UserClient userClient);
}
