package project.gui;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.SimulationConstants;
import project.messageSystem.Message;
import project.udp.UDPReceive;

public class MessageCollections extends UDPReceive implements Runnable{
    
    private ConcurrentLinkedDeque<Message> updateRequests;
    public MessageCollections(ConcurrentLinkedDeque<Message> updateRequests){
        super(SimulationConstants.GUI_PORT, "GUI");
        this.updateRequests = updateRequests;
    }

    @Override
    public void run() {
        while (true){
            Message msg = null;
            try {
                msg = this.receive(SimulationConstants.BYTE_SIZE);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            this.updateRequests.addFirst(msg);
        }   
    }

}
