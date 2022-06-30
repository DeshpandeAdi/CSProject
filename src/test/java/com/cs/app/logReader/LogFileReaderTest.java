package com.cs.app.logReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import com.cs.app.logReader.service.LogEventProcessingService;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class LogFileReaderTest {

	@Mock
	LogEventProcessingService logEventProcessingService;
	
	@Before
	public void init(){
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void readFileAsInputTest(){
		String fileName = "logfile.txt";
		InputStream in = new ByteArrayInputStream(fileName.getBytes());
		System.setIn(in);
	}
	
}
