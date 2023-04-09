package project.gui.components.Floor;

import java.awt.*;
import javax.swing.*;

import project.constants.MotorDirection;

public class FloorInfo extends JPanel{
    private final Color DISABLED_COLOR = Color.GRAY;
    private final Color ENABLED_COLOR = Color.GREEN;
    private final Color TEXT_COLOR = Color.BLACK;
    private final int DEFAULT_BORDER_WIDTH = 1; 

    private JLabel floorNumber; 
    private JLabel upButton;
    private JLabel downButton;
    private JPanel buttonsPanel;
    public FloorInfo(String floorNumber, int height, int width){
        super();
        // floor info setup
        this.buttonsPanel = new JPanel();
        this.buttonsPanel.setLayout(new GridLayout(2, 1));
        this.floorNumber = new JLabel("Floor: " + floorNumber);
        this.upButton = new JLabel("up");
        this.downButton = new JLabel("Down");

        // set default information styling
        this.floorNumber.setHorizontalAlignment(SwingConstants.CENTER);
        this.floorNumber.setFont(new Font("Arial", Font.BOLD, 14));
        this.floorNumber.setForeground(this.TEXT_COLOR);
        this.floorNumber.setPreferredSize(new Dimension(64, this.upButton.getHeight()));

        this.upButton.setBackground(this.DISABLED_COLOR);
        this.upButton.setOpaque(true);
        this.upButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.upButton.setForeground(this.TEXT_COLOR);
        this.upButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.upButton.setPreferredSize(new Dimension(64, this.upButton.getHeight()));
        this.upButton.setBorder(BorderFactory.createMatteBorder(0, this.DEFAULT_BORDER_WIDTH, this.DEFAULT_BORDER_WIDTH, 0, Color.BLACK));

        this.downButton.setBackground(this.DISABLED_COLOR);
        this.downButton.setOpaque(true);
        this.downButton.setForeground(this.TEXT_COLOR);
        this.downButton.setHorizontalAlignment(SwingConstants.CENTER);
        this.downButton.setFont(new Font("Arial", Font.BOLD, 14));
        this.downButton.setPreferredSize(new Dimension(64, this.upButton.getHeight()));
        this.downButton.setBorder(BorderFactory.createMatteBorder(0, this.DEFAULT_BORDER_WIDTH, 0, 0, Color.BLACK));

        // layout 
        this.setLayout(new BorderLayout());
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 0, this.DEFAULT_BORDER_WIDTH, Color.BLACK));
        this.buttonsPanel.add(this.upButton);
        this.buttonsPanel.add(this.downButton);
        this.add(this.buttonsPanel, BorderLayout.CENTER);
        this.add(this.floorNumber, BorderLayout.WEST);
    }

    public void disableButton(MotorDirection isUp){
        if (isUp == MotorDirection.UP){
            this.upButton.setBackground(this.DISABLED_COLOR);
            return;
        }
        this.downButton.setBackground(this.DISABLED_COLOR);
    }

    public void enableButton(MotorDirection isUp){
        if (isUp == MotorDirection.UP){
            this.upButton.setBackground(this.ENABLED_COLOR);
            return;
        }
        this.downButton.setBackground(this.ENABLED_COLOR);
    }

}
