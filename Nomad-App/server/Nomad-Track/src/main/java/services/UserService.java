package com.techelevator.services;

import com.techelevator.model.User;

import java.util.List;

public interface UserService {

    User getUserById(int id);
    User getUserByUsername(String userName);
    User createUser(User newUser);
}
