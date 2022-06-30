package com.cs.app.logReader.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hsqldb.jdbc.JDBCDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.cs.app.logReader.dao.EventEntryDAO;
import com.cs.app.logReader.model.EventEntries;


@Component
public class EventEntryDAOImpl implements EventEntryDAO {

	private static final Logger logger = LoggerFactory.getLogger(EventEntryDAOImpl.class);
	
	@Override
	public void saveEventEntries(List<EventEntries> listEventEntries) {
		logger.info("In class EventEntryDAOImpl :: method saveEventEntries :: start getting DB configs");
		try {
			JDBCDataSource dataSource = new JDBCDataSource();
			dataSource.setDatabase("jdbc:hsqldb:file:eventsDb");
			Connection conn = dataSource.getConnection("SA", "");
			fillFileEvents(conn, listEventEntries);
			conn.close();
		} catch (Exception e) {
			logger.error("Caught error while saving entries :: ", e);
			e.printStackTrace();
		}
	}

	private void fillFileEvents(Connection conn, List<EventEntries> listEventEntries) throws SQLException {
		// Create a statement object
				Statement stat = conn.createStatement();
				PreparedStatement prep = null;
				// Try to drop the table
				try {
					stat.executeUpdate("DROP TABLE EventEntries");
				} catch (SQLException e) {
					logger.error("Error while dropping table", e);
				}
				String sql = "CREATE TABLE EVENTENTRIES (id varchar(255), "
						+ " type varchar(255), host varchar(255), eventduration bigint,"
						+ " alert varchar(10), primary key(id));";
				stat.executeUpdate(sql);
				// Close the Statement object, it is no longer used
				stat.close();
				for (EventEntries eventEntry : listEventEntries) {
					String values = "VALUES ('" 
							+ eventEntry.getId()
							+ "', '"
							+ (eventEntry.getType() != null ? eventEntry.getType()
									: "")
							+ "','"
							+ (eventEntry.getHost() != null ? eventEntry.getHost()
									: "") + "'," + eventEntry.getEventDuration()
							+ ",'true'";
					String insertStatement = "INSERT INTO EVENTENTRIES (id,type,host,eventduration,alert) "
							+ values + ");";
					prep = conn.prepareCall(insertStatement);
					// Close the PreparedStatement
					prep.close();
				}
				logger.info("Data inserted succcessfully");
	}
}
