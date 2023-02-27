# SYSC 3303 Group Project - Iteration 2
------------------------------------------------------------------------------------------------------------------------------------------------------------------

Contributors and Respective Contributions - Group 2:

Anthony Massaad 101150282
- ElevatorSubSystem, Scheduler, Floor, MessageQueue, Message, ElevatorSubSystemMessageQueue, FloorMessageQueue, ArrivalMessage, MoveToMessage, ElevatorRequestMessage, Log
- Uml Class Diagram
- Scheduler State Diagram

Dorothy Tran 101141902 
- ElevatorSubSystem, Scheduler, Floor, MessageQueue, Message, ElevatorSubSystemMessageQueue, FloorMessageQueue, ArrivalMessage, MoveToMessage, ElevatorRequestMessage
- JUnit Testing

Max Curkovic 101139937
- ElevatorSubSystem, Scheduler, Floor, MessageQueue, Message, ElevatorSubSystemMessageQueue, FloorMessageQueue, ArrivalMessage, MoveToMessage, ElevatorRequestMessage
- Sequence Diagram
- JUnit Testing

Elisha Catherasoo 101148507
- ElevatorSubSystem, Scheduler, Floor, MessageQueue, Message, ElevatorSubSystemMessageQueue, FloorMessageQueue, ArrivalMessage, MoveToMessage, ElevatorRequestMessage
- JUnit Testing

Cassidy Pacada 101143345
- ElevatorSubSystem, Scheduler, Floor, MessageQueue, Message, ElevatorSubSystemMessageQueue, FloorMessageQueue, ArrivalMessage, MoveToMessage, ElevatorRequestMessage
- Elevator State Diagram

Overall in general, everyone contributed evening as the general code was done as a group. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Project Description 

The purpose of this project is to design and create a system that simulates an elevator. It consists of three subsystems, the Elevator, the Scheduler, and the Floor.

For the purpose of Iteration 2, the system is to simply run similar as Iteration 1 but with state machines, and the additional feature of the Elevator SubSystem. Request Message 
is passed from the floor to the scheduler then to the ElevatorSubSystem class, which would then tell a elevator to move to the designated floor. When the Elevator arrived, it will 
send an ArrivalMessage back to the ElevatorSubSystem, back to the scheduler, and back to the floor to notify it. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Files Included

This Iteration consists of 22 Java files where 5 are test files and 1 textfile listed below:
- Elevator.java: A thread that simulates an elevator car. It continuously checks if the Scheduler has sent a message. Once it receives a message, it will send a 
  response message back to the Scheduler.

- Floor.java: A thread that simulates the people on various floors who wish to use the elevator system. It takes the parsed inputs from the sim.txt file and 
  turns the instructions into messages that it then sends to the Scheduler.

- Scheduler.java: A thread that simulates the control system of the elevator. It receives messages from the floor system and relays the instructions to the   
  ElevatorSubSystem. The Scheduler then receives the elevator's response and passes it back to the floor. 
  
- ElevatorSubSystem: A thread that has an x amount of Elevators to handle. It will relay messages given from the scheduler to an elevator, and vice versa.

- Message.java: Defines the messasge that is sent between the Floor and Elevator systems.

- ArrivalMessage.java: Inherits from Message.java and implies that an Elevator has arrived to a floor. 

- MoveToMessage.java: Inherits from Message.java and tells an elevator to move to a designated floor.

- ElevatorRequestMessage.java, Inherits from Message.java and it is to request an elevator to a floor.

- MessageQueue.java: Contains the four kinds of messages that can be sent through the system and initializes them to null. If a particular message gets sent,
  the respective message in the MessageQueue is changed from null.
  
- ElevatorSubSystemMessageQueue.java: Inherits from MessageQueue.java and it is the queue of messages for the ElevatorSubSystem with the Scheduler

- FloorMessageQueue.java: inherits from MessageQueue.java and it is the queue of messages for the Floor with the Scheduler

- Parser.java: Parses the textfile containing the system instructions.

- Log.java: A Logging system used to help with debugging.

- Main.java: Contains the main method that creates instances of all the threads and starts them.

- TestParser.java: JUnit tests for the Parser class to ensure that the instructions are being parsed correctly.

- TestElevator.java: JUnit test for the Elevator

- TestElevatorSubSystem.java: JUnit test for the ElevatorSubSystem

- TestFloor.java: JUnit test for the Floor

- TestScheduler: JUnit test for the Scheduler

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



