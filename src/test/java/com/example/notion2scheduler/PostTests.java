package com.example.notion2scheduler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;
import java.time.ZonedDateTime;
import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the validation of a post.
 */
public class PostTests {
    @Test
    public void thatPostIsValid() {
        Post post = new Post();
        post.setId("350d1e52-123d-4025-8dc1-b6f9263ef73f");
        post.setChannel(Channel.DISCORD);
        post.setStatus(Status.SCHEDULED);
        post.setContent("Hey guys, a candidate is right now working on our test project to automatically deliver social media posts. Awesome! #teamenduco");
        post.setPostAt(ZonedDateTime.now());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Post>> violations = factory.getValidator().validate(post);

        assertThat(violations.isEmpty()).isEqualTo(true);
    }

    @Test
    public void thatPostIdIsNotValid() {
        Post post = new Post();
        post.setId("350d1e52-123d-4025-8dc1-b6f9263ef7");
        post.setChannel(Channel.DISCORD);
        post.setStatus(Status.SCHEDULED);
        post.setContent("Hey guys, a candidate is right now working on our test project to automatically deliver social media posts. Awesome! #teamenduco");
        post.setPostAt(ZonedDateTime.now());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Post>> violations = factory.getValidator().validate(post);

        assertThat(violations.isEmpty()).isEqualTo(false);
    }

    @Test
    public void thatPostContentIsNotValid() {
        Post post = new Post();
        post.setId("350d1e52-123d-4025-8dc1-b6f9263ef73f");
        post.setChannel(Channel.DISCORD);
        post.setStatus(Status.SCHEDULED);
        post.setContent("");
        post.setPostAt(ZonedDateTime.now());

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Set<ConstraintViolation<Post>> violations = factory.getValidator().validate(post);

        assertThat(violations.isEmpty()).isEqualTo(false);
    }
}
