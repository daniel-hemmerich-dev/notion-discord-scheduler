package com.example.notion2scheduler.task;

import com.example.notion2scheduler.sdk.Notion;
import org.quartz.*;

/**
 * Post a scheduled message to Discord.
 */
public class Discord implements Job {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) { this.content = content; }

    /**
     * Post the message on Discord and update the status in Notion accordingly.
     * @param context The context of the job which contains the job key used for identification.
     */
    @Override
    public void execute(final JobExecutionContext context) {
        Notion notionSDK = new Notion();
        com.example.notion2scheduler.sdk.Discord discordSDK = new com.example.notion2scheduler.sdk.Discord();
        JobKey key = context.getJobDetail().getKey();

        if(discordSDK.postMessage(getContent())) {
            System.out.printf(
                "Sent to Discord message: \"%s\" with key: %s%n",
                getContent(),
                key
            );
            notionSDK.markPostAsPosted(key.getName());
        } else {
            notionSDK.markPostAsFailed(key.getName());
        }
    }
}
