package project.main;

import project.constants.Addresses;
import project.constants.SimulationConstants;
import project.elevatorImpl.ElevatorSubSystem;
import project.floorImpl.FloorManager;
import project.gui.MainGui;
import project.schedulerImpl.Scheduler;
import project.simulationParser.Parser;

public class Main {
    
    public static void main(String[] args) throws InterruptedException {
        MainGui g = new MainGui();
        Thread t = new Thread(g);
        t.start();

        String addressToSend = Addresses.ELEVATOR.getAddress();
        Scheduler s = new Scheduler(SimulationConstants.SCHEDULER_PORT, "Scheduler", addressToSend);
        Thread tS = new Thread(s);
        tS.start();

        Thread.sleep(1000);

        ElevatorSubSystem el = new ElevatorSubSystem(SimulationConstants.ELEVATOR_MANAGER_PORT, "Elevator Subsystem");
		Thread tE = new Thread(el);
		tE.start();

        Thread.sleep(20000);

        Parser p = new Parser(); 
        FloorManager manager = new FloorManager(SimulationConstants.FLOOR_MANAGER_PORT, "Floor Manager", p.getEventLines());
        Thread tF = new Thread(manager);
        tF.start();

    }
}
