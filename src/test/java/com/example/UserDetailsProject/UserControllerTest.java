package com.example.UserDetailsProject;

import com.example.UserDetailsProject.controller.UserController;
import com.example.UserDetailsProject.exception.CustomApiException;
import com.example.UserDetailsProject.model.CreateUserRequestDto;
import com.example.UserDetailsProject.model.PageInfo;
import com.example.UserDetailsProject.model.UserEntity;
import com.example.UserDetailsProject.model.UserWrapperDto;
import com.example.UserDetailsProject.service.UserService;
import com.example.UserDetailsProject.validator.NumericValidator;
import com.example.UserDetailsProject.validator.Validator;
import com.example.UserDetailsProject.validator.ValidatorFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static javafx.css.SizeUnits.S;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ValidatorFactory validatorFactory;

    @InjectMocks
    private UserController userController;
    @Test
    void createUsers_ValidRequest_ReturnsCreatedUsers() throws Exception {
        // Arrange
        CreateUserRequestDto requestDto = new CreateUserRequestDto();
        requestDto.setNumberOfUsers(2);
        List<Optional<UserEntity>> createdUsers = Arrays.asList(
                Optional.of(new UserEntity(1L, "Alice Johnson", 30, "Female", "1992-05-15", "US", "verified", "2023-10-20", "1992-05-15")),
                Optional.of(new UserEntity(2L, "Bob Smith", 28, "Male", "1994-02-28", "UK", "verified", "2023-09-12", "1994-02-28"))
        );

        when(userService.createUser(requestDto)).thenReturn(createdUsers);

        // Act
        ResponseEntity<List<Optional<UserEntity>>> responseEntity =
                userController.createUsers(requestDto);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(createdUsers, responseEntity.getBody());
    }


    @Test
    void getUsers_ValidRequest_ReturnsUserWrapperDto() {
        // Arrange
        String sortType = "name";
        String sortOrder = "asc";
        int limit = 5;
        int offset = 0;
        List<UserEntity> createdUsers = Arrays.asList(
                (new UserEntity(1L, "Alice Johnson", 30, "Female", "1992-05-15", "US", "verified", "2023-10-20", "1992-05-15")),
                (new UserEntity(2L, "Bob Smith", 28, "Male", "1994-02-28", "UK", "verified", "2023-09-12", "1994-02-28"))
        );
        UserWrapperDto userWrapperDto = new UserWrapperDto(createdUsers, new PageInfo());

        when(userService.getUsers(sortType, sortOrder, limit, offset))
                .thenReturn(userWrapperDto);

        // Act
        ResponseEntity<UserWrapperDto> responseEntity =
                userController.getUsers(sortType, sortOrder, limit, offset);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(userWrapperDto, responseEntity.getBody());
    }

}