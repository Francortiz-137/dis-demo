package com.disneydemo.demo.service;

import com.disneydemo.demo.model.DisneyUser;

import java.util.List;

public interface UserService {

    DisneyUser saveUser(DisneyUser disneyUser);

    List<DisneyUser> getUser();

    DisneyUser updateUser(DisneyUser disneyUser);

    boolean existUser(Long id);

    DisneyUser findById(Long id);

    List<DisneyUser> findAll();

    void deleteUser(Long id);

    DisneyUser findByUserName(String s);

    DisneyUser findByEmail(String email);
}
