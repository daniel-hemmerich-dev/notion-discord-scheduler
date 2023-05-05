package com.example.notion2scheduler;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;
import java.time.ZonedDateTime;

/**
 * A post that can be posted on social media platforms.
 */
public class Post {
    @NotBlank
    @Length(min = 36, max = 36)
    @Pattern(regexp = "^[a-z0-9-]+$")
    private String id;

    @NotNull
    private Status status;

    @NotNull
    private Channel channel;

    @NotBlank
    @Length(min = 1, max = 4096)
    private String content;

    @NotNull
    private ZonedDateTime postAt;

    public String getId() {
        return id;
    }

    public void setId(String id) { this.id = id; }

    public Status getStatus() { return status; }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ZonedDateTime getPostAt() {
        return postAt;
    }

    public void setPostAt(ZonedDateTime postAt) {
        this.postAt = postAt;
    }
}
