# CSProject

It is a springboot application, which reads the user provided log file(logfile.txt), calculates the duration and inserts the details in HSQLDB.

How to run-

1) After cloning the repo, import it to eclipse as an existing Maven project.

2) Open LogReaderApplication class(class having main method). Right click and click "Run As". Then choose "Java Application" i.e. Run the class as Java Application.

3) Application will ask for file name. Enter the filename as "logfile.txt". Please make sure this file is available.

4) After successful run, please check logs for the event IDs which are having alert value as "true". These eventIDs are the ones taking longer than 4 milliseconds.

5) From the logs mentioned in point #4 above, it is clear that data is getting stored in the HSQLDB. But the ".data" file for this version of HSQLDB jar, is not getting created. Hence, to illustrate the data insertion into DB, I have used the logs(mentioned in point #4 above)

6) I have tried to create few JUnit test classes, but due to time crunch, could not complete all. 