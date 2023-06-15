# Elevator Simulator Project 
------------------------------------------------------------------------------------------------------------------------------------------------------------------
## Authors (Group L1G2)
[Anthony Massaad](https://github.com/Anthony-Massaad) \
[Dorothy Tran](https://github.com/dorothytran) \
[Max Curkovic](https://github.com/maxcurkovic) \
[Elisha Catherasoo](https://github.com/elishajcat) \
[Cassidy Pacada](https://github.com/cassidypacada)

## Description
The purpose of this project is to simulate the behaviour of an elevator system in real time. The Elevator Simulator is composed of the Scheduler, Elevator, and Floor subsystems, that are constantly interacting with each other through User Datagram Protocol (UDP).

## Design
- All the diagrams for the project can be found at the [diagrams](https://github.com/Anthony-Massaad/Group2_SYSC3303/tree/main/diagrams) folder. The current version of the project is iteration 5, which contains:
     - The [UML class diagram](https://github.com/Anthony-Massaad/Group2_SYSC3303/blob/main/diagrams/iteration5/Iteration5%20uml%20class%20diagram.svg) for the system.
     - The [sequence diagrams](https://github.com/Anthony-Massaad/Group2_SYSC3303/blob/main/diagrams/iteration5/3303M5SD.jpg) showing the interactions between the components.
     - The [state machine](https://github.com/Anthony-Massaad/Group2_SYSC3303/blob/main/diagrams/iteration5/Iteration%205%20Elevator%20State%20Diagram.png) for the elevator subsystem.
     - The [state machine](https://github.com/Anthony-Massaad/Group2_SYSC3303/blob/main/diagrams/iteration5/Scheduler%20State%20Machine.png) scheduler subsystem.

- You can also review the [project report](https://github.com/Anthony-Massaad/Group2_SYSC3303/blob/main/Group%202_SYSC%203303%20A1%20Final%20Project%20Report.pdf) describing the work breakdown in each iteration of the project. 

## Files Included

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

## Run the project

### setup
1. Unzip the submission file.
2. Navigate the eclipse IDE.
3. Navigate the File menu.
4. Open the `Open Project from File System..` option.
5. Select the root folder for the project `Elevator-Simulator`.
6. Click the `Finish button`.
7. Select the project folder. 

### Launch
To run the program, Navigate to the toolbar and find the green run button and select the down arrow next to it. A dropdown menu will appear, hover over the "Run As" option and select "1 Java Application". Run the following in order: MainGUI.java, Scheduler.java, ElevatorSubSystem.java (wait about 3 seconds), then FloorManager.java. 
