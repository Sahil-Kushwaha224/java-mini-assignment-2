package com.example.UserDetailsProject.controller;

import com.example.UserDetailsProject.exception.CustomApiException;
import com.example.UserDetailsProject.model.CreateUserRequestDto;
import com.example.UserDetailsProject.model.UserEntity;
import com.example.UserDetailsProject.model.UserWrapperDto;
import com.example.UserDetailsProject.service.UserService;
import com.example.UserDetailsProject.validator.ValidatorFactory;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final ValidatorFactory validatorFactory;

  @PostMapping
  public ResponseEntity<List<Optional<UserEntity>>> createUsers(
          @RequestBody CreateUserRequestDto createUserRequestDto) {
    if (createUserRequestDto.getNumberOfUsers() > 5) {
      throw new CustomApiException("Number of users cannot be greater than 5",
              HttpStatus.BAD_REQUEST);
    }
    try {

      List<Optional<UserEntity>> createdUsers = userService.createUser(createUserRequestDto);
      return ResponseEntity.ok(createdUsers);
    } catch (Exception e) {
      throw new CustomApiException(e.getMessage(),
              HttpStatus.valueOf(e.hashCode()));
    }
  }

  @GetMapping
  public ResponseEntity<UserWrapperDto> getUsers(
      @RequestParam(required = false) String sortType,
      @RequestParam(required = false) String sortOrder,
      @RequestParam(defaultValue = "5") int limit,
      @RequestParam(defaultValue = "0") int offset) {
    try {
      validateParameters(sortType, sortOrder);
      UserWrapperDto users = userService.getUsers(sortType, sortOrder, limit, offset);
      return ResponseEntity.ok(users);
    } catch (CustomApiException e) {
      throw new CustomApiException(e.getMessage(),
          HttpStatus.valueOf(e.hashCode()));
    }
  }

  private void validateParameters(String p1, String p2) {
    validatorFactory.getValidator(p1);
    validatorFactory.getValidator(p2);
  }
}