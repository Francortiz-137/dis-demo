package com.disneydemo.demo.service.implementation;

import com.disneydemo.demo.model.DisneyUser;
import com.disneydemo.demo.repository.MovieRepository;
import com.disneydemo.demo.repository.UserRepository;
import com.disneydemo.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public DisneyUser saveUser(DisneyUser disneyUser) {
        return userRepository.save(disneyUser);
    }

    @Override
    public List<DisneyUser> getUser() {
        return null;
    }

    @Override
    public DisneyUser updateUser(DisneyUser disneyUser) {
        return null;
    }

    @Override
    public boolean existUser(Long id) {
        return false;
    }

    @Override
    public DisneyUser findById(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<DisneyUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public DisneyUser findByUserName(String s) {
        return userRepository.findByUserName(s);
    }

    @Override
    public DisneyUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
