package com.example.UserDetailsProject.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class EnglishAlphabetsValidator implements Validator {
  @Override
  public boolean validate(String input) {
    return input.matches("[a-zA-Z]+");
  }
}
