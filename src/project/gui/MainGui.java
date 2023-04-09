package project.gui;

import javax.swing.*;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.gui.components.Floor.FloorComponent;
import project.gui.components.Text.ElevatorInput.ElevatorInfo;
import project.gui.components.Text.FloorInput.FloorInputText;
import project.messageSystem.messages.UpdatePositionMessage;

import java.awt.*;

public class MainGui extends JFrame{
    private static int WIDTH = 1400; 
    private static int HEIGHT = 760; 

    private JPanel mainPanel, simPanel, textPanel, elevatorInfoPanel; 
    private FloorComponent[] floorComponents;
    private ElevatorInfo[] elevatorInfos; 
    private FloorInputText floorInputText; 

    public MainGui(){
        super("Elevator Project");
        GridBagConstraints constraints = new GridBagConstraints();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int widthScreen = screenSize.width;
        int heightScreen = screenSize.height;
        
        if (WIDTH < widthScreen){
            // create a maximum width the frame can be
            WIDTH = widthScreen;
        }
        HEIGHT = heightScreen; 
        // initialize the panels
        this.mainPanel = new JPanel();
        this.simPanel = new JPanel();
        this.textPanel = new JPanel();
        this.elevatorInfoPanel = new JPanel();
        this.floorComponents = new FloorComponent[SimulationConstants.NUM_OF_FLOORS];
        this.elevatorInfos = new ElevatorInfo[SimulationConstants.NUM_OF_ELEVATORS];

        this.floorInputText = new FloorInputText();

        // setting the layout for the jpanels 
        this.mainPanel.setLayout(new GridBagLayout());
        this.simPanel.setLayout(new BoxLayout(this.simPanel, BoxLayout.Y_AXIS));
        this.textPanel.setLayout(new GridBagLayout());
        this.elevatorInfoPanel.setLayout(new GridLayout(2, 2, 5, 5));

        // add components to sim panel
        for (int i = SimulationConstants.NUM_OF_FLOORS - 1; i >= 0; i--){
            this.floorComponents[i] = new FloorComponent(HEIGHT/SimulationConstants.NUM_OF_FLOORS, Integer.toString(i + 1));
            this.simPanel.add(this.floorComponents[i]);
        }

        // add elevator info to elevator info panel
        for (int i = 0; i < SimulationConstants.NUM_OF_ELEVATORS; i++){
            // should only be 4!
            this.elevatorInfos[i] = new ElevatorInfo(i + 1);
            this.elevatorInfoPanel.add(this.elevatorInfos[i]);
        }

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1.0;
        constraints.weighty = 0.8;
        this.textPanel.add(this.elevatorInfoPanel, constraints);

        constraints.gridy = 1;
        constraints.weighty = 0.6;
        this.textPanel.add(this.floorInputText, constraints);

        // adding to the main panel
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1.0;
        this.mainPanel.add(this.simPanel, constraints);
        
        constraints.gridx = 1;
        constraints.weightx = 0.4;
        constraints.insets = new Insets(0, 5, 0, 5);
        this.mainPanel.add(this.textPanel, constraints);

        // finish setup for the frame
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(this.mainPanel);
        this.pack();
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void updateElevatorInfo(int elevatorID, UpdatePositionMessage msg){
        this.elevatorInfos[elevatorID].update(msg);
    }

    public void appendFloorInput(String msg){
        this.floorInputText.appendText(msg);
    }

    public void updateFloorComponentRequest(int floorNumber, MotorDirection direction){
        this.floorComponents[floorNumber - 1].enableButtons(direction);
    }

    public void updateFloorComponentArrive(int floorNumber, MotorDirection direction){
        this.floorComponents[floorNumber - 1].disableButton(direction);
    }

    public static void main(String[] args) {
        new MainGui();
    }
}
