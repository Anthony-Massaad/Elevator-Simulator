package project.gui;

import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedDeque;

import project.constants.SimulationConstants;
import project.messageSystem.Message;
import project.udp.UDPReceive;

/**
 * Responsible for collecting update requests and storing it for the gui
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class MessageCollections extends UDPReceive implements Runnable{
    
    private ConcurrentLinkedDeque<Message> updateRequests;
    /**
     * Constructor
     * @param updateRequests ConcurrentLinkedDeque<Message>, the shared queue between this and the gui class
     */
    public MessageCollections(ConcurrentLinkedDeque<Message> updateRequests){
        super(SimulationConstants.GUI_PORT, "GUI");
        this.updateRequests = updateRequests;
    }

    /**
     * overriden method from runnable
     */
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
