package com.cs.app.logReader.dao;

import java.util.List;

import com.cs.app.logReader.model.EventEntries;

public interface EventEntryDAO {

	void saveEventEntries(List<EventEntries> eventEntriesListToSave);
}
