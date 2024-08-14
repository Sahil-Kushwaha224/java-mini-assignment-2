package com.example.UserDetailsProject.model;

import lombok.Data;

@Data
public class PageInfo {

  private int hasNextPage;
  private int hasPreviousPage;
  private int total;
}
