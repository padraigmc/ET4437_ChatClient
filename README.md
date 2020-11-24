# ET4437 Project Specification

* Date: 18/11/2020
* Assignment: Group Project (groups of three students)
* Submission deadline: 18 / 12 /20 (Sulis)
* Submission details: PDF document and ZIP project file
* Demonstration provisional date: 16/12/20 @4pm (B2043)
* Worth: 40 %
* Make sure to include the module, your names, and student IDs in your submission.

## Specification and Deliverables:

**The web service Javadoc can be found under "Web Service Javadoc". Download this folder and open "index.html" to view.**

Each group must build a Chat/Messenger Application in Java that uses the provided Web Service and
database, and which enables the following:

1. The application has to be built as a Java GUI using the Swing library. You can give a name of your
    application (e.g. SuperMessenger).
    
2. A main screen that loads when the application starts, offering access to create an account (register)
    or login using existing credentials.
    
3. A login window to enter credentials.

4. A registration window to create a new account.

5. Once logged in you should move to the messenger window that shows the following:

    a. A list or popup menu that displays all registered users. You should be able to select a user
(recipient) from that list/popup menu.

    b. A Text Area that displays all messages between the logged user and the selected user
(recipient). You may need to include a timestamp and the name or username of sender for
each message. The Text Area should update periodically (every few seconds) using a
thread. Note that changing the recipient in (a) should update the content of the Text Area.

    c. A text field and a button for sending a message to the recipient.

    d. Another Text Area that displays all Multicast messages – messages that are sent by one
user to all registered users. You may need to include a timestamp and the name or
username of sender for each message. The Text Area should update periodically (every few
seconds) using a thread.

    e. A text field and a button for sending a Multicast message to the users.

    f. A button for logging out. This should close the messenger window.

6. Threading is essential in this project. Use can use either **MyThread extends Thread**
    implementation or **MyThread implements Runnable**.
    
7. Only one window must be visible on the screen at any time.

8. For the development of the Web client application Java you will need to import the attached
    **chatsystem.sql** database (in MySQL) and the Web Service **ChatService.zip** (in NetBeans). To run
    your Web client application, you will need to run the MySQL server in XAMPP and the ChatService
    in NetBeans.
    
9. You must make no changes to the ChatService. You can only generate a Javadoc in NetBeans by
    executing Run>Generate a Javadoc after selecting the ChatService project. The generated Javadoc
    is more user friendly to read than the source code files of the ChatService project.
    
10. For the demonstration, both the MySQL server and Web Service will be running on another
    machine, and you will need to update the provided WSDL link required for your application. You
    will need only to load your Web Client application in NetBeans and connect to the LAN. You should
    test the application before the demonstration.
    
11. During the demonstration in W12, you’ll need to demonstrate: registering a new user, login as the
    user, retrieving users, posting messages to a selected user, posting multicast messages, updating
    the message areas when a user posts a message, and logging out.
    
12. You will also need to write a report on the design of the final system and what part each member
    of your team has added to the system, (how you designed your system, implemented it, and tested
    it, and who did which parts). You must submit your report by the end of Week 12 in Sulis.
    
13. In addition to the report (as PDF), you must also submit in Sulis your Web Client project as an
    exported ZIP file.

## Grading:

This project is worth **40 %** of the total module grade. The marking scheme for this project is as follows:

- Demonstrate the program running in the lab (**25 %**)
- A well written report (**25 %**)
- All features implemented i.e. login, registration, etc. (**25 %**)
- Overall appearance & user experience, well written code (**25 %**)

Should you have any questions, please don’t hesitate to contact me (petar.iordanov@ul.ie).

PS. Notes on how to write a good technical report can be found at
[http://pslc.ul.ie/ssc/seminar_report.html](http://pslc.ul.ie/ssc/seminar_report.html)
