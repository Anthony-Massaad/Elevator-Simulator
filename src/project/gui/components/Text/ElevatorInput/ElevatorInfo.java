package project.gui.components.Text.ElevatorInput;

import javax.swing.*;

import project.constants.MotorDirection;
import project.constants.SimulationConstants;
import project.messageSystem.messages.UpdatePositionMessage;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class responsible for displaying the elvators information
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class ElevatorInfo extends JPanel{
    private final Color ACTIVE_STATUS = Color.GREEN;
    private final Color BROKEN_STATUS = Color.RED;
    private final String ACTIVE_TEXT = "Active";
    private final String INACTIVE_TEXT = "Fault";
    private final int DEFAULT_BORDER_WIDTH = 1; 

    private JLabel statusLabel, elevatorTitle, currFloorTitle, currFloorLabel, nextDestTitle;
    private JLabel nextDestLabel, directionLabel, directionTitle, destinationsQueueTitle;
    private JLabel status, stateTitle, stateLabel;
    private JPanel topLevelPanel, middleLevelPanel, bottomPanel, statusPanel, currFloorPanel, statePanel;
    private JPanel nextDestPanel, directionPanel, destinationsQueuePanel, orderedDestPanel; 
    private NextDestField[] nextDestinationsQueue;
    private JScrollPane destinationsQueueScroll; 
    /**
     * Constructor for the elevator info
     * @param elevatorNumber Integer, the elevator number
     */
    public ElevatorInfo(int elevatorNumber){
        super(); 
        this.setLayout(new BorderLayout()); 
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.nextDestinationsQueue = new NextDestField[SimulationConstants.NUM_OF_FLOORS];
        this.destinationsQueueScroll = new JScrollPane();
        // initialize the labels
        this.statusLabel = new JLabel("Status");
        this.elevatorTitle = new JLabel("Elevator#" + elevatorNumber);
        this.currFloorTitle = new JLabel("Current Floor");
        this.currFloorLabel = new JLabel(" ");
        this.nextDestTitle = new JLabel("Next Destination"); 
        this.nextDestLabel = new JLabel(" ");
        this.directionTitle = new JLabel("Direction");
        this.directionLabel = new JLabel(" ");
        this.destinationsQueueTitle = new JLabel("Ordered Destinations");
        this.stateTitle = new JLabel("State");
        this.stateLabel = new JLabel(" ");

        this.elevatorTitle.setOpaque(true);
        this.elevatorTitle.setForeground(Color.WHITE);
        this.elevatorTitle.setBackground(Color.BLACK);

        // initialize and default the status light
        this.status = new JLabel(this.ACTIVE_TEXT);
        this.status.setOpaque(true);
        this.status.setBackground(this.ACTIVE_STATUS);
        this.status.setHorizontalAlignment(SwingConstants.CENTER);
        this.status.setForeground(Color.black);

        // initialize panels 
        this.topLevelPanel = new JPanel();
        this.middleLevelPanel = new JPanel();
        this.bottomPanel = new JPanel();
        this.statusPanel = new JPanel();
        this.currFloorPanel = new JPanel();
        this.nextDestPanel = new JPanel();
        this.directionPanel = new JPanel();
        this.orderedDestPanel = new JPanel();
        this.destinationsQueuePanel = new JPanel();
        this.statePanel = new JPanel();

        for (int i = 0; i < SimulationConstants.NUM_OF_FLOORS; i++) {
            this.nextDestinationsQueue[i] = new NextDestField();
            this.destinationsQueuePanel.add(this.nextDestinationsQueue[i]);
        }

        this.destinationsQueueScroll.setViewportView(this.destinationsQueuePanel);

        // set layout for the respective panels
        this.topLevelPanel.setLayout(new BorderLayout());
        this.middleLevelPanel.setLayout(new GridLayout(1, 4));
        this.bottomPanel.setLayout(new BorderLayout());
        this.destinationsQueuePanel.setLayout(new GridLayout(5, 5, 5, 5));
        this.statusPanel.setLayout(new BoxLayout(this.statusPanel, BoxLayout.Y_AXIS));
        this.currFloorPanel.setLayout(new BoxLayout(this.currFloorPanel, BoxLayout.Y_AXIS));
        this.nextDestPanel.setLayout(new BoxLayout(this.nextDestPanel, BoxLayout.Y_AXIS));
        this.directionPanel.setLayout(new BoxLayout(this.directionPanel, BoxLayout.Y_AXIS));
        this.orderedDestPanel.setLayout(new BoxLayout(this.orderedDestPanel, BoxLayout.Y_AXIS));
        this.statePanel.setLayout(new BoxLayout(this.statePanel, BoxLayout.Y_AXIS));

        // border layout
        this.topLevelPanel.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.middleLevelPanel.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.elevatorTitle.setBorder((BorderFactory.createMatteBorder(0, 0, 0, this.DEFAULT_BORDER_WIDTH, Color.BLACK)));
        this.statusLabel.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.currFloorTitle.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.nextDestTitle.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.directionTitle.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.currFloorPanel.setBorder((BorderFactory.createMatteBorder(0, 0, 0, this.DEFAULT_BORDER_WIDTH, Color.BLACK)));
        this.nextDestPanel.setBorder((BorderFactory.createMatteBorder(0, 0, 0, this.DEFAULT_BORDER_WIDTH, Color.BLACK)));
        this.directionPanel.setBorder((BorderFactory.createMatteBorder(0, 0, 0, this.DEFAULT_BORDER_WIDTH, Color.BLACK)));
        this.destinationsQueuePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        this.stateTitle.setBorder((BorderFactory.createMatteBorder(0, 0, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK)));
        this.destinationsQueueScroll.setBorder((BorderFactory.createMatteBorder(this.DEFAULT_BORDER_WIDTH, 0, 0, 0, Color.BLACK)));

        // text alignment
        this.elevatorTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.currFloorTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.nextDestTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.directionTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.currFloorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.nextDestLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.directionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.destinationsQueueTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.stateTitle.setHorizontalAlignment(SwingConstants.CENTER);
        this.stateLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // preferred dimension
        this.statusPanel.setPreferredSize(new Dimension(50, 40));
        this.statusLabel.setPreferredSize(new Dimension(50, 15));
        this.currFloorTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.currFloorTitle.getPreferredSize().height));
        this.nextDestTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.nextDestTitle.getPreferredSize().height));
        this.directionTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.directionTitle.getPreferredSize().height));
        this.status.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.statusPanel.getPreferredSize().height));
        this.statusLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.statusLabel.getPreferredSize().height));
        this.currFloorLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.currFloorLabel.getPreferredSize().height));
        this.nextDestLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.nextDestLabel.getPreferredSize().height));
        this.directionLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.directionLabel.getPreferredSize().height));
        this.destinationsQueueTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.destinationsQueueTitle.getPreferredSize().height));
        this.stateLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.stateLabel.getPreferredSize().height));
        this.stateTitle.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.stateTitle.getPreferredSize().height));        

        // font setting
        this.elevatorTitle.setFont(new Font("Arial", Font.BOLD, 15));

        // adding to the top level component; 
        this.statusPanel.add(this.statusLabel);
        this.statusPanel.add(this.status);

        this.topLevelPanel.add(this.elevatorTitle, BorderLayout.CENTER);
        this.topLevelPanel.add(this.statusPanel, BorderLayout.EAST);

        // adding to the middle level component
        this.currFloorPanel.add(this.currFloorTitle);
        this.currFloorPanel.add(this.currFloorLabel);
        this.nextDestPanel.add(this.nextDestTitle);
        this.nextDestPanel.add(this.nextDestLabel);
        this.directionPanel.add(this.directionTitle);
        this.directionPanel.add(this.directionLabel);
        this.statePanel.add(this.stateTitle);
        this.statePanel.add(this.stateLabel);

        this.middleLevelPanel.add(this.currFloorPanel);
        this.middleLevelPanel.add(this.nextDestPanel);
        this.middleLevelPanel.add(this.directionPanel);
        this.middleLevelPanel.add(this.statePanel);

        // bottom component
        this.orderedDestPanel.add(this.destinationsQueueTitle);
        this.orderedDestPanel.add(this.destinationsQueueScroll);
        this.bottomPanel.add(this.orderedDestPanel);

        // add to total component 
        this.add(this.topLevelPanel, BorderLayout.NORTH);
        this.add(this.middleLevelPanel, BorderLayout.CENTER);
        this.add(this.bottomPanel, BorderLayout.SOUTH);

    }

    /**
     * update the elevators status
     * @param isErrorStatus boolean, true if error otherwise false
     */
    public void setStatus(boolean isErrorStatus){
        if (isErrorStatus){
            this.status.setBackground(this.BROKEN_STATUS);
            this.status.setText(this.INACTIVE_TEXT);
            return;
        }
        this.status.setBackground(this.ACTIVE_STATUS);
        this.status.setText(this.ACTIVE_TEXT);
    }

    /**
     * update the current floor of the elevator
     * @param currentFloor String, the currentFloor
     */
    public void setCurrentFloor(String currentFloor){
        this.currFloorLabel.setText(currentFloor);
    }

    /**
     * update the next destination of the elevator
     * @param nextDestination String, next destination
     */
    public void setNextDestination(String nextDestination){
        this.nextDestLabel.setText(nextDestination);
    }

    /**
     * update the elevators direction
     * @param direction String, direction
     */
    public void setDirection(String direction){
        this.directionLabel.setText(direction);
    }

    /**
     * update the ordered destinations for the elevator
     * @param destinations ArrayList<Integer>, the list of integers
     */
    public void setOrderedDestinationQueue(ArrayList<Integer> destinations){
        int index = 0;
        for (NextDestField feild: this.nextDestinationsQueue) {
            feild.setDestination(" ");
        }

        for (int i : destinations) {
            if (i <= SimulationConstants.DOOR_CLOSE_STUCK_INPUT || i == SimulationConstants.ELEVATOR_BROKEN_FAULT_INPUT){
                continue;
            }
            this.nextDestinationsQueue[index].setDestination(Integer.toString(i));
            index++;
        }
    }

    /**
     * update the state of the elevator
     * @param state String, the state
     */
    public void setState(String state){
        this.stateLabel.setText(state);
    }

    /**
     * update everything of the elevator
     * @param msg UpdatePositionMessage, the message for updating
     */
    public void update(UpdatePositionMessage msg){
        this.setStatus(msg.getIsStuck());
        this.setCurrentFloor(Integer.toString(msg.getCurrentFloor()));
        this.setNextDestination(Integer.toString(msg.getNextDestination()));
        this.setDirection(MotorDirection.toString(msg.getDirection()));
        this.setOrderedDestinationQueue(msg.getDestinations());
        this.setState(msg.getState());
    }

}
