package com.example.notion2scheduler.task;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the Twitter Task.
 */
public class TwitterTests {
    @Test
    public void thatTwitterIsNotImplementedYet() {
        Twitter twitterTask = new Twitter();

        try {
            twitterTask.execute(null);
            assertThat(true).isEqualTo(false);
        } catch (RuntimeException runtimeException) {
            assertThat(true).isEqualTo(true);
        }
    }
}
