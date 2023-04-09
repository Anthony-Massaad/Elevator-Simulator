package project.gui.components.Floor;

import javax.swing.*;

import project.constants.MotorDirection;

import java.awt.*;

public class FloorComponent extends JPanel{
    
    private FloorInfo floorInfo; 
    public FloorComponent(int height, String floorNumber){
        super(); 
        this.floorInfo = new FloorInfo(floorNumber, this.getHeight(), 64);
        this.setBorder(BorderFactory.createLineBorder(Color.black));
        this.setLayout(new BorderLayout()); 
        this.add(this.floorInfo, BorderLayout.WEST);
        this.setPreferredSize(new Dimension(this.getWidth(), height));
    }

    public void enableButtons(MotorDirection isUp){
        this.floorInfo.enableButton(isUp);
    }

    public void disableButton(MotorDirection isUp){
        this.floorInfo.disableButton(isUp);
    }

}
