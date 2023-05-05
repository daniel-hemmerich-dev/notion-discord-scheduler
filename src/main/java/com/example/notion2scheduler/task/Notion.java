package com.example.notion2scheduler.task;

import com.example.notion2scheduler.Channel;
import com.example.notion2scheduler.Post;
import com.example.notion2scheduler.Status;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import java.time.ZonedDateTime;

/**
 * The Task which pulls the posts from Notion and add them to the publishing queue
 */
public class Notion implements Job {
    /**
     * Schedule a job to be published at a specific time
     * @param jobDetail The job which will be executed
     * @param post The post of the job which contains all the necessary data
     */
    private void schedulePost(
        final JobDetail jobDetail,
        final Post post) throws SchedulerException {

        JobKey jobKey = new JobKey(post.getId());

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        if (scheduler.checkExists(jobKey)) {
            System.out.printf("Job with id: %s already exists.%n", post.getId());
            return;
        }

        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity(post.getId())
            .startAt(java.util.Date.from(post.getPostAt().toInstant()))
            .build();

        scheduler.scheduleJob(
            jobDetail,
            trigger
        );
    }

    /**
     * Adds a post to the scheduler which will be published at the date time given.
     * @param post The post to schedule for publication
     */
    private void addPost(final Post post) throws SchedulerException {
        switch (post.getChannel()) {
            case DISCORD -> schedulePost(
                JobBuilder.newJob(Discord.class)
                    .withIdentity(post.getId())
                    .usingJobData("content", post.getContent())
                    .build(),
                post
            );
            case TWITTER -> schedulePost(
                JobBuilder.newJob(Twitter.class)
                    .withIdentity(post.getId())
                    .usingJobData("content", post.getContent())
                    .build(),
                post
            );
            default -> throw new RuntimeException("Unknown channel: " + post.getChannel());
        }
    }

    /**
     * Converts a json element to a post
     * @param jsonElement The json element containing the post data
     * @return The post object converted from the specified json
     */
    private Post createPostFromJson(final JsonElement jsonElement) {
        JsonObject postObject = jsonElement.getAsJsonObject();
        JsonObject postProperties = postObject.get("properties").getAsJsonObject();

        Post post = new Post();
        post.setId(postObject.get("id").getAsString());
        post.setChannel(Channel.valueOf(postProperties.get("Channel").getAsJsonObject().get("multi_select").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString().toUpperCase()));
        post.setStatus(Status.valueOf(postProperties.get("Status").getAsJsonObject().get("status").getAsJsonObject().get("name").getAsString().toUpperCase()));
        post.setContent(postProperties.get("Content").getAsJsonObject().get("rich_text").getAsJsonArray().get(0).getAsJsonObject().get("text").getAsJsonObject().get("content").getAsString());

        //String postAt = postProperties.get("Post at").getAsJsonObject().get("date").getAsJsonObject().get("start").getAsString();
        //ZonedDateTime zonedDateTime = ZonedDateTime.parse(postAt);
        ZonedDateTime zonedDateTime = ZonedDateTime.now().minusMinutes(1); // ToDo: uncomment for testing
        post.setPostAt(zonedDateTime);

        (new com.example.notion2scheduler.validator.Post()).validate(post);

        return post;
    }

    /**
     * Pull the posts from notion with the status scheduled and add them to the scheduler.
     * @param context Contains information about the task
     */
    @Override
    public void execute(final JobExecutionContext context) {
        com.example.notion2scheduler.sdk.Notion notionSDK = new com.example.notion2scheduler.sdk.Notion();
        JsonObject scheduledPosts = notionSDK.selectScheduledPosts();

        for (JsonElement postEntry : scheduledPosts.getAsJsonArray("results")) {
            try {
                addPost(createPostFromJson(postEntry));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}
