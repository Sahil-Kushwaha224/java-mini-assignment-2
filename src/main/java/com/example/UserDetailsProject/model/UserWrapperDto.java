package com.example.UserDetailsProject.model;

import java.util.List;
import lombok.Data;

@Data
public class UserWrapperDto {
  private List<UserEntity> users;
  private PageInfo pageInfo;

  public UserWrapperDto(List<UserEntity> users, PageInfo pageInfo) {
    this.users = users;
    this.pageInfo = pageInfo;
  }
}
