package com.example.UserDetailsProject.service;

import com.example.UserDetailsProject.model.CreateUserRequestDto;
import com.example.UserDetailsProject.model.UserEntity;

import com.example.UserDetailsProject.model.UserWrapperDto;
import java.util.List;
import java.util.Optional;

public interface UserService {

  List<Optional<UserEntity>> createUser(CreateUserRequestDto dto) throws Exception;

  UserWrapperDto getUsers(String sortType, String sortOrder, int limit, int offset);
}
