# SYSC 3303 Group Project - Iteration 3
------------------------------------------------------------------------------------------------------------------------------------------------------------------

Contributors and Respective Contributions - Group 2:

Anthony Massaad 101150282
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator,
  ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler, Log
- Uml Class Diagram
- Scheduler State Diagram

Dorothy Tran 101141902 
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator,
  ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler,
- JUnit Testing

Max Curkovic 101139937
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator,
  ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler,
- Sequence Diagram
- JUnit Testing

Elisha Catherasoo 101148507
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator,
  ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler,
- JUnit Testing

Cassidy Pacada 101143345
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator,
  ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler,
- Elevator State Diagram

Overall in general, everyone contributed evening as the general code was done as a group. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Project Description 

The purpose of this project is to design and create a system that simulates an elevator. It consists of three subsystems, the Elevator, the Scheduler, and the Floor.

For the purpose of Iteration 3, the system now supports multiple elevators which are co-ordinated so that each one will carry around the same number of passengers.
As well, this iteration implements UDP to communicate as opposed to the MessageQueue implementation that was used in the previous two milestones. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Files Included

This Iteration consists of 33 Java files where 6 are test files and 1 textfile listed below:
- Clock.java: grabs the time 

- ElevatorStatus.java: keeps track of how many passengers an elevator has, its current flor, its direction, and its next destination

- ElevatorState.java: enum that defines the elevator states

- ElevatorComReceiver: polls for elevator responses to send to the SchedulerMidTask

- FloorButtonState.java: enum that defines the floor button states

- MotorDirection: enum that defines the motor directions

- SimulationConstants.java: Miscellaneous constants like number of elevators, number of floors, and maximum passengers

- FloorManager.java: Initializes each of the floors and managers events for each of the floors

- FloorScheduler.java: processes events for the floors

- FloorRequestElevator.java: request for elevators to a specific floor

- RequestElevatorMessage.java: request for elevators from the scheduler

- UpdatePositionMessage.java: updates the elevators position to the scheduler

- SchedulerMidTask.java: intermediary between the Scheduler and the Elevators

- Elevator.java: A thread that simulates an elevator car. It continuously checks if the Scheduler has sent a message. Once it receives a message, it will send a 
  response message back to the Scheduler.

- Floor.java: A thread that simulates the people on various floors who wish to use the elevator system. It takes the parsed inputs from the sim.txt file and 
  turns the instructions into messages that it then sends to the Scheduler.

- Scheduler.java: A thread that simulates the control system of the elevator. It receives messages from the floor system and relays the instructions to the   
  ElevatorSubSystem. The Scheduler then receives the elevator's response and passes it back to the floor. 
  
- ElevatorSubSystem: A thread that has an x amount of Elevators to handle. It will relay messages given from the scheduler to an elevator, and vice versa.

- Message.java: Defines the messasge that is sent between the Floor and Elevator systems.

- Time.java: Enum that defines the Time states and their integer values

- ArrivalMessage.java: Inherits from Message.java and implies that an Elevator has arrived to a floor. 

- MoveToMessage.java: Inherits from Message.java and tells an elevator to move to a designated floor.

- ElevatorRequestMessage.java, Inherits from Message.java and it is to request an elevator to a floor.

- Parser.java: Parses the textfile containing the system instructions.

- Log.java: A Logging system used to help with debugging.

- TestParser.java: JUnit tests for the Parser class to ensure that the instructions are being parsed correctly.

- TestElevator.java: JUnit test for the Elevator

- TestElevatorSubSystem.java: JUnit test for the ElevatorSubSystem

- TestFloor.java: JUnit test for the Floor

- TestScheduler: JUnit test for the Scheduler

- TestFloorManager: JUnit test for the FloorManager

- UDPBoth: receives and sends UDP messages

- UDPImpl: formats messages to be able to send through UDP and sends and receives UDP messages

- UDPReceive: receives UDP messages

- UDPSend: sends UDP messages

- sim.txt: The textfile containing the instructions to be parsed and sent to the elevator system. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Setup Instructions

1. Open Eclipse IDE and import the project by going to File -> Import -> Projects from Folder or Archive and select the submitted archive file. 

2. To run the program, Navigate to the toolbar and find the green run button and select the down arrow next to it. A dropdown menu will appear, 
   hover over the "Run As" option and select "1 Java Application". Run the Following in order: Scheduler.java, ElevatorSubSystem.java (wait about 3 second) then FloorManager.java.


Test Instructions 
3. To run the JUnit test for the Parser, open TestParser.java. Find the green run button and select the down arrow next to it. A dropdown menu will appear, 
   hover over the "Run As" option and select "1 JUnit Test". The output will appear in the JUnit window and will tell you how many errors and failures
   occurred.

3. The other JUnit tests are run in the same way but open their respective files. 



