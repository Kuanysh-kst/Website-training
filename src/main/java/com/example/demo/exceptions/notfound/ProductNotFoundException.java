package com.example.demo.exceptions.notfound;

import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
public class ProductNotFoundException extends NotFoundException {
    public ProductNotFoundException(Map<String, List<String>> errors) {
        super(errors);
    }
}
