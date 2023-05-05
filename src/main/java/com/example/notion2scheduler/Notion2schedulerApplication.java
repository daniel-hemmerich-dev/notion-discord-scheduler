package com.example.notion2scheduler;

import com.example.notion2scheduler.sdk.Discord;
import com.example.notion2scheduler.sdk.Notion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Notion2schedulerApplication {
	@Autowired
	Discord discordSDK;

	@Autowired
	Notion notionSDK;

	/**
	 * The entry point of the application.
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(
			Notion2schedulerApplication.class,
			args
		);

		try (PostScheduler postScheduler = new PostScheduler()) {
			postScheduler.start();
			Thread.sleep(120000); // to let it run only for two minutes
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

}
