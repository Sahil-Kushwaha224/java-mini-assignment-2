package com.example.UserDetailsProject.sorting;

import com.example.UserDetailsProject.model.UserEntity;
import java.util.List;

public interface UserSortStrategy {

  List<UserEntity> sort(List<UserEntity> users);
}
