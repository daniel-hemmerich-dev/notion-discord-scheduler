package com.example.notion2scheduler.task;

import jdk.jshell.spi.ExecutionControl;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * Post a scheduled message to Twitter.
 */
public class Twitter implements Job {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    /**
     * Post the message on Twitter and update the status in Notion accordingly.
     * @param context The context of the job which contains the job key used for identification.
     */
    @Override
    public void execute(final JobExecutionContext context) {
        try {
            throw new ExecutionControl.NotImplementedException("Should publish a Twitter post on a specific date and time.");
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }
    }
}
