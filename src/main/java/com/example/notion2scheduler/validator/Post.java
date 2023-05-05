package com.example.notion2scheduler.validator;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

/**
 * Validates the post object.
 */
public class Post {
    /**
     * Checks if the post is valid.
     * @param post The post object.
     */
    public void validate(final com.example.notion2scheduler.Post post) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

        Set<ConstraintViolation<com.example.notion2scheduler.Post>> violations = factory.getValidator().validate(post);

        if (!violations.isEmpty()) throw new ConstraintViolationException(violations);
    }
}
