package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import project.messageSystem.Message;

public class UDPReceive extends UDPImpl{
    
    private DatagramSocket receiveSocket; 
    public UDPReceive(int port, String systemName){
        super(systemName);
        try {
            this.receiveSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    protected Message receive(int byteSize) throws IOException, InterruptedException {
        return this.receive(this.receiveSocket, byteSize);
    }

    @Override
    protected void closeSockets() {
        this.receiveSocket.close();
        System.exit(1);
    }

}
