# SYSC 3303 Group Project - Iteration 1
------------------------------------------------------------------------------------------------------------------------------------------------------------------

Contributors and Respective Contributions - Group 2:

Anthony Massaad 101150282
- Floor Subsystem, Parser, Logger, JavaDocs, UML Class Diagram, JUnit Testing

Dorothy Tran 101141902 
- Scheduler Subsystem, Message System, JavaDocs

Max Curkovic 101139937
- Scheduler Subsystem, Message System, JavaDocs, UML Sequence Diagram

Elisha Catherasoo 101148507
- Elevator Subsystem, JavaDocs

Cassidy Pacada 101143345
- JUnit Testing, Elevator Subsystem, ReadMe

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Project Description 

The purpose of this project is to design and create a system that simulates an elevator. It consists of three subsystems, the Elevator, the Scheduler, and the Floor.
For the purpose of Iteration 1, the system is simply meant to allow communication between the three subsystems which have been implemented as threads. Messages
are passed from the Floor thread to the Scheduler which then passes the message on to the Elevator. The Elevator then sends a message back through to Scheduler
to the Floor. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Files Included

This Iteration consists of 10 Java files and 1 textfile listed below:
- Elevator.java: A thread that simulates an elevator car. It continuously checks if the Scheduler has sent a message. Once it receives a message, it will send a 
  response message back to the Scheduler.

- Floor.java: A thread that simulates the people on various floors who wish to use the elevator system. It takes the parsed inputs from the sim.txt file and 
  turns the instructions into messages that it then sends to the Scheduler.

- Scheduler.java: A thread that simulates the control system of the elevator. It receives messages from the floor system and relays the instructions to the    
  elevator. The Scheduler then receives the elevator's response and passes it back to the floor. 

- Message.java: Defines the messasge that is sent between the Floor and Elevator systems.

- MessageQueue.java: Contains the four kinds of messages that can be sent through the system and initializes them to null. If a particular message gets sent,
  the respective message in the MessageQueue is changed from null.

- Parser.java: Parses the textfile containing the system instructions.

- Log.java: A Logging system used to help with debugging.

- Main.java: Contains the main method that creates instances of all the threads and starts them.

- TestParser.java: JUnit tests for the Parser class to ensure that the instructions are being parsed correctly.

- TestSystem.java: JUnit test for the system to ensure that the MessageQueue contains the expected message for each event. 

- sim.txt: The textfile containing the instructions to be parsed and sent to the elevator system. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Setup Instructions

1. Open Eclipse IDE and import the project by going to File -> Import -> Projects from Folder or Archive and select the submitted archive file. 

2. To run the program, open Main.Java and navigate to the toolbar. Find the green run button and select the down arrow next to it. A dropdown menu will appear, 
   hover over the "Run As" option and select "1 Java Application". The output will appear in the console window.


Test Instructions 
3. To run the JUnit test for the Parser, open TestParser.java. Find the green run button and select the down arrow next to it. A dropdown menu will appear, 
   hover over the "Run As" option and select "1 JUnit Test". The output will appear in the JUnit window and will tell you how many errors and failures
   occurred.

3. To run the JUnit test for the System, repeat step 3 but with the TestSystem.java file instead of TestParser.java.



