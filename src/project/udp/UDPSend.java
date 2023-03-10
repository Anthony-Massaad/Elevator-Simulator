package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import project.messageSystem.Message;

public class UDPSend extends UDPImpl{

    private DatagramSocket sendSocket; 
    public UDPSend(String systemName){
        super(systemName);
        try {
            this.sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    protected void send(Message msg, int destinationPort) throws IOException, InterruptedException {
        this.send(this.sendSocket, msg, destinationPort);
    }

    @Override
    protected void closeSockets() {
        this.sendSocket.close();
        System.exit(1);
    }

}
