package com.example.demo.exceptions.duplicate;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class CategoryDuplicateException extends DuplicateException {
    public CategoryDuplicateException(Map<String, List<String>> errors) {
        super(errors);
    }
}
