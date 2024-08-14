package com.example.UserDetailsProject.repository;

import com.example.UserDetailsProject.model.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  List<UserEntity> findAllByOrderByNameAsc();

  List<UserEntity> findAllByOrderByAgeAsc();

}
