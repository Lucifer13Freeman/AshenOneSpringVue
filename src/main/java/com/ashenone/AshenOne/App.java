package com.ashenone.AshenOne;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App
{
	public static void main(String[] args)
	{
		Sentry.init(options -> {
			options.setEnableExternalConfiguration(true);
		});

//		Sentry.captureMessage("Server has been started");
		SpringApplication.run(App.class, args);
	}
}
