package com.gmail.sshekh;

import com.gmail.sshekh.model.User;

import java.util.List;

public interface UserService {

    User save(User user);

    User findUserByEmail(String email);

    List<User> findAll();
}
