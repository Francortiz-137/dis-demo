package com.disneydemo.demo.repository;

import com.disneydemo.demo.model.DisneyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<DisneyUser, Long> {

    List<DisneyUser> findAll();

    Optional<DisneyUser> findById(Long id);

    void deleteById(Long id);

    DisneyUser findByEmail(String s);

    DisneyUser findByUserName(String s);

}
