package com.example.UserDetailsProject.validator;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class NumericValidator implements Validator {

  @Override
  public boolean validate(String input) {
    try {
      Integer.parseInt(input);
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
