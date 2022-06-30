package com.cs.app.logReader;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.app.logReader.service.LogEventProcessingService;

@Component
public class LogFileReader {

	private static final Logger logger = LoggerFactory.getLogger(LogFileReader.class);
	
	@Autowired
	LogEventProcessingService logEventProcessingService;
	
	public void readFileAsInput() {
		logger.info("In LogFileReader class :: readFileAsInput method :: User to input file name");
		Scanner reader = new Scanner(System.in);
		System.out.println("Filename that contains logs :: ");
		String fileName = reader.next();
		reader.close();
		logEventProcessingService.processLogEntries(fileName);
	}
}
