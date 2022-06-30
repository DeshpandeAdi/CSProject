package com.cs.app.logReader.utils;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cs.app.logReader.model.EventEntries;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component("fileParsingHelper")
public class FileParsingHelper {
	
	private static final Logger logger = LoggerFactory.getLogger(FileParsingHelper.class);
	
	

	public List<EventEntries> parseFile(String fileName, List<EventEntries> listEventEntries) throws IOException {
		logger.info("In class FileParsingHelper :: method parseFile :: Start file parsing");
		File file = new File(fileName);
		LineIterator it = FileUtils.lineIterator(file);
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz");
		mapper.setDateFormat(df);
		try {
			while (it.hasNext()) {
				String line = it.nextLine();
				listEventEntries.add(mapper.readValue(line, EventEntries.class));
			}
		} finally {
			it.close();
		}
		return listEventEntries;
	}
	
}
