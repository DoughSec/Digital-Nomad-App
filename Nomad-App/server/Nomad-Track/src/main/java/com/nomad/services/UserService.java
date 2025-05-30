package com.nomad.services;

import com.nomad.model.User;

public interface UserService {

    User getUserById(int id);
    User getUserByUsername(String userName);
    User createUser(User newUser);
}
