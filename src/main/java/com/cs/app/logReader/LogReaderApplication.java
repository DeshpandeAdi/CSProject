package com.cs.app.logReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan(basePackages = "com.cs.app.logReader")
public class LogReaderApplication {

	private static final Logger logger = LoggerFactory.getLogger(LogReaderApplication.class);
	
	
	public static void main(String[] args) {
		logger.info("LogReaderApplication is starting");
		ApplicationContext ctx =  SpringApplication.run(LogReaderApplication.class, args);
		LogFileReader logFileReader = ctx.getBean(LogFileReader.class);
		logFileReader.readFileAsInput();
	}
	
	
}
