package com.example.demo.exceptions.notfound;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(Map<String, List<String>> errors) {
      super(errors);
    }
}
