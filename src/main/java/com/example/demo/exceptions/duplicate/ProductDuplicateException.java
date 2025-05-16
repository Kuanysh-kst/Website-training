package com.example.demo.exceptions.duplicate;

import java.util.List;
import java.util.Map;

public class ProductDuplicateException extends DuplicateException{
    public ProductDuplicateException(Map<String, List<String>> errors) {
        super(errors);
    }
}
