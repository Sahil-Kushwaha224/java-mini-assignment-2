package com.example.UserDetailsProject.sorting.impl;

import com.example.UserDetailsProject.model.UserEntity;
import com.example.UserDetailsProject.sorting.UserSortStrategy;
import java.util.Comparator;
import java.util.List;

public class SortByAgeStrategy implements UserSortStrategy {
  @Override
  public List<UserEntity> sort(List<UserEntity> users) {
    users.sort(Comparator.comparingInt(UserEntity::getAge));
    return users;
  }
}
