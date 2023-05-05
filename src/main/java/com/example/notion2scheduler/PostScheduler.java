package com.example.notion2scheduler;

import com.example.notion2scheduler.task.Notion;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

/**
 * Schedules posts to be posted on a specific date and time on a social media platform.
 */
@Service
@ConfigurationProperties(prefix = "notion")
public class PostScheduler implements AutoCloseable {
    final private Scheduler scheduler;

    private String uri;

    public String getUri() {
        return this.uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String database;

    public String getDatabase() {
        return this.database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    private String token;

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Create the scheduled job which will pull regular all posts with status scheduled from notion.
     * @throws SchedulerException If the scheduler was not able to schedule the job.
     */
    private void scheduleNotionSynchronization() throws SchedulerException {
        JobDetail job = JobBuilder.newJob(Notion.class)
            .withIdentity("Notion synchronization")
            .usingJobData("uri", this.getUri())
            .usingJobData("token", this.getToken())
            .usingJobData("database", this.getDatabase())
            .build();

        Trigger trigger = TriggerBuilder.newTrigger()
            .withIdentity("Notion synchronization trigger")
            .startNow()
            .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever())
            .build();

        scheduler.scheduleJob(
            job,
            trigger
        );
    }

    /**
     * Constructor. Instanciate the post scheduler.
     * @throws SchedulerException If the scheduler could not be initialized.
     */
    public PostScheduler() throws SchedulerException {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
    }

    /**
     * Start the scheduler. Posts will be processed and published.
     * @throws SchedulerException If the scheduler was not able to start.
     */
    public void start() throws SchedulerException {
        scheduleNotionSynchronization();
        scheduler.start();
    }

    /**
     * Auto closes the scheduler and shut it down.
     * @throws Exception If the scheduler was not able to shut down successfully.
     */
    @Override
    public void close() throws Exception {
        scheduler.shutdown();
    }
}
