package com.cs.app.logReader.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cs.app.logReader.dao.EventEntryDAO;
import com.cs.app.logReader.model.EventEntries;
import com.cs.app.logReader.service.LogEventProcessingService;
import com.cs.app.logReader.utils.FileParsingHelper;
import com.cs.app.logReader.utils.FinalResponseConstants;

@Component("logEventProcessingService")
public class LogEventProcessingServiceImpl implements LogEventProcessingService {
	
	private static final Logger logger = LoggerFactory.getLogger(LogEventProcessingServiceImpl.class);
	
	@Autowired
	private FileParsingHelper fileParsingHelper;
	
	@Autowired
	private EventEntryDAO eventEntryDAO;

	@Override
	public String processLogEntries(String fileName) {
		logger.info("In LogEventProcessingServiceImpl class :: processLogEntries method :: Start reading user file :: FileName :: {}", fileName);
		String response = FinalResponseConstants.FAILURE_RESPONSE;
		List<EventEntries> listEventEntries = new ArrayList<>();
		if(null != fileName && !fileName.isEmpty()){
			try {
				fileParsingHelper.parseFile(fileName, listEventEntries);
			} catch (IOException e) {
				response = FinalResponseConstants.FAILURE_RESPONSE;
				logger.error("Error while parsing file :: fileName :: {}", fileName);
				e.printStackTrace();
			}
		} else {
			logger.warn("Invalid File Name.");
			response = FinalResponseConstants.FAILURE_RESPONSE;
		}
		if(calculateTimeDuration(listEventEntries)){
			response = FinalResponseConstants.SUCCESS_RESPONSE;
		}
		
		return response;
	}

	private boolean calculateTimeDuration(List<EventEntries> listEventEntries) {
		Map<String, EventEntries> mapEventEntries = new HashMap<String, EventEntries>();
		List<EventEntries> eventEntriesListToSave = new ArrayList<EventEntries>();
		logger.info("In class LogEventProcessingServiceImpl :: method calculateTimeDuration :: Start calculating events time duration");
		for (EventEntries currentEventEntry : listEventEntries) {
			logger.debug("Processing event id: " + currentEventEntry.getId());
			if (mapEventEntries.get(currentEventEntry.getId()) != null) {
				EventEntries previousEventEntry = mapEventEntries
						.get(currentEventEntry.getId());
				Long previousTimeStamp = previousEventEntry.getTimestamp();
				Long currentTimeStamp = currentEventEntry.getTimestamp();
				if (previousTimeStamp > currentTimeStamp && (previousTimeStamp - currentTimeStamp > 4)) {
					currentEventEntry.setEventDuration(previousTimeStamp - currentTimeStamp);
					eventEntriesListToSave.add(currentEventEntry);
				} else if (currentTimeStamp > previousTimeStamp && (currentTimeStamp - previousTimeStamp > 4)) {
					currentEventEntry.setEventDuration(currentTimeStamp - previousTimeStamp);
					eventEntriesListToSave.add(currentEventEntry);
				}
			} else {
				mapEventEntries.put(currentEventEntry.getId(),
						currentEventEntry);
			}
		}
		if(!eventEntriesListToSave.isEmpty()){
			eventEntryDAO.saveEventEntries(eventEntriesListToSave);
			return true;
		}
		return false;
	}

}
