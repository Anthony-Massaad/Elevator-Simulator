# SYSC 3303 Group Project - Iteration 5
------------------------------------------------------------------------------------------------------------------------------------------------------------------

Contributors and Respective Contributions - Group 2:

Anthony Massaad 101150282
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator, ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler, Log, Timing, SchedulerIdleState, SchedulerMidTaskIdleState, ScheduelrProcessElevatorState, SchedulerProcessFloorState, State, ElevatorBrokenState, ElevatorCloseDoorState, ElevatorDoorFaultState, ElelvatorIdleState, ElevatorMovingState, ElevatorOpenDoorState, ElevatorRequestProcessState, MainGui, MessageCollections, ElevatorComponent, FloorComponent, FloorInfo, ElevatorInfo, NextDestField, FloorInputTest
- Uml Class Diagram
- Scheduler State Diagram

Dorothy Tran 101141902 
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator, ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler, SchedulerIdleState, SchedulerMidTaskIdleState, ScheduelrProcessElevatorState, SchedulerProcessFloorState, State, ElevatorBrokenState, ElevatorCloseDoorState,  ElevatorDoorFaultState, ElelvatorIdleState, ElevatorMovingState, ElevatorOpenDoorState, ElevatorRequestProcessState, JUnit Testing, MainGui, MessageCollections, ElevatorComponent, FloorInfo, ElevatorInfo, NextDestField, FloorInputTest
- JUnit Testing

Max Curkovic 101139937
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator, ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler, SchedulerIdleState, SchedulerMidTaskIdleState, ScheduelrProcessElevatorState, SchedulerProcessFloorState, State, ElevatorBrokenState, ElevatorCloseDoorState, ElevatorDoorFaultState, ElevatorIdleState, ElevatorMovingState, ElevatorOpenDoorState, ElevatorRequestProcessState, JUnit Testing, MainGui, MessageCollections, ElevatorComponent, FloorInfo, ElevatorInfo, NextDestField, FloorInputTest
- Sequence Diagram
- JUnit Testing

Elisha Catherasoo 101148507
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator, ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler, SchedulerIdleState, SchedulerMidTaskIdleState, ScheduelrProcessElevatorState, SchedulerProcessFloorState, State, ElevatorBrokenState, ElevatorCloseDoorState, ElevatorDoorFaultState, ElevatorIdleState, ElevatorMovingState, ElevatorOpenDoorState, ElevatorRequestProcessState, JUnit Testing, MainGui, MessageCollections, ElevatorComponent, FloorInfo, ElevatorInfo, NextDestField, FloorInputTest
- JUnit Testing

Cassidy Pacada 101143345
- ElevatorSubSystem, Scheduler, Floor, Message, UpdatePositionMesesage, ArrivalMessage, MoveToMessage, RequestElevatorMessage, FloorRequestElevator, ElevatorComReceiver, Elevator, ElevatorStatus, SchedulerMidTask, UDPReceive, UDPBoth, UDPSend, UDPImpl, Clock, FloorManager, FloorScheduler, SchedulerIdleState, SchedulerMidTaskIdleState, ScheduelrProcessElevatorState, SchedulerProcessFloorState, State, ElevatorBrokenState, ElevatorCloseDoorState, ElevatorDoorFaultState, ElelvatorIdleState, ElevatorMovingState, ElevatorOpenDoorState, ElevatorRequestProcessState, Elevator State Diagram, MainGui, MessageCollections, ElevatorComponent, FloorInfo, ElevatorInfo, NextDestField, FloorInputTest
- Elevator State Diagram
- Transient and Hard Fault Timing Diagrams

Overall in general, everyone contributed evening as the general code was done as a group. 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Project Description 

The purpose of this project is to design and create a system that simulates an elevator. It consists of three subsystems, the Elevator, the Scheduler, and the Floor.

For the purpose of Iteration 5, the system utilizes a Graphical User Interface to display the original functionality of the Scheduler, the Elevator Subsystem, and the Floor Manager. The system now also takes measurements of particular tasks, including door open/close time as well as 

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Files Included

This Iteration consists of 46 Java files where 6 are test files and 4 textfile listed below:
- Clock.java: grabs the time 

- ElevatorStatus.java: keeps track of how many passengers an elevator has, its current flor, its direction, and its next destination

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

- SchedulerIdleState.java: Contains functionality for Scheduler's Idle state, used for state pattern purposes

- SchedulerMidTaskIdleState.java: Contains functionality for Scheduler Mid Task Idle state, used for state pattern purposes

- SchedulerProcessElevatorState.java: Contains functionality for Scheduler's Process Elevator state, used for state pattern purposes

- SchedulerProcessFloorState.java: Contains functionality for Scheduler's Process Floor state, used for state pattern purposes

- State.java: Abstract Class containing generic state functionality

- ElevatorBrokenState.java: Contains functionality for Elevator's Broken state, used for state pattern purposes

- ElevatorCloseDoorState.java: Contains functionality for Elevator's Close Door state, used for state pattern purposes

- ElevatorDoorFaultState.java: Contains functionality for Elevator's Door Fault state, used for state pattern purposes

- ElevatorIdleState.java: Contains functionality for Elevator's Idle state, used for state pattern purposes

- ElevatorMovingState.java: Contains functionality for Elevator's Moving state, used for state pattern purposes

- ElevatorOpenDoorState.java: Contains functionality for Elevator's Open Door state, used for state pattern purposes

- ElevatorRequestProcessState.java: Contains functionality for Elevator's Request Process state, used for state pattern purposes

- Main.java: Contains all the methods to run threads and the GUI.

- MainGUI.java: Responsible for displaying the main GUI frame.

- MessageCollections.java: Responsible for collecting update requests and storing it for the GUI.

- Timing.java: Timing class to ensure that sleep times are accurate (for the door fault)

- sim.txt: The textfile containing the instructions to be parsed and sent to the elevator system. 

- sim2.txt: Additional instructions to be parsed and sent to the elevator system. 

- sim3.txt: Additional instructions to be parsed and sent to the elevator system. 

- sim4.txt: Additional instructions to be parsed and sent to the elevator system. 

- measurements.txt: Text file that has measurements added to it when applicable.

------------------------------------------------------------------------------------------------------------------------------------------------------------------

Setup Instructions

1. Open Eclipse IDE and import the project by going to File -> Import -> Projects from Folder or Archive and select the submitted archive file. 

2. To run the program, Navigate to the toolbar and find the green run button and select the down arrow next to it. A dropdown menu will appear, 
   hover over the "Run As" option and select "1 Java Application". Run the Following in order: MainGUI.java, Scheduler.java, ElevatorSubSystem.java (wait about 3 seconds), then FloorManager.java.


Test Instructions 
3. To run the JUnit test for the Parser, open TestParser.java. Find the green run button and select the down arrow next to it. A dropdown menu will appear, 
   hover over the "Run As" option and select "1 JUnit Test". The output will appear in the JUnit window and will tell you how many errors and failures occurred.

4. The other JUnit tests (TestElevator.java, TestElevator.SubSystem.java, TestFloor.java, TestMeasureSystem.java,  TestScheduler.java, and TestFloorManager.java) are run in the same way but open their respective files. 
