package com.example.UserDetailsProject.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ValidatorFactory {

  private final NumericValidator numericValidator;
  private final EnglishAlphabetsValidator englishAlphabetsValidator;

  public Validator getValidator(String parameterType) {
    if ("even".equalsIgnoreCase(parameterType) || "odd".equalsIgnoreCase(parameterType)) {
      return numericValidator;
    } else if ("name".equalsIgnoreCase(parameterType)) {
      return englishAlphabetsValidator;
    } else if ("age".equalsIgnoreCase(parameterType)) {
      return numericValidator;
    } else {
      throw new IllegalArgumentException("Invalid parameter type: " + parameterType);
    }
  }
}

