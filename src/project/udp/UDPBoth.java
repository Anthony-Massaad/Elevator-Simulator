package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;
import project.messageSystem.Message;

public class UDPBoth extends UDPImpl{
    
    private DatagramSocket sendSocket, receiveSocket; 
    public UDPBoth(int port, String systemName) {
        super(systemName);
        try {
            this.sendSocket = new DatagramSocket();
            this.receiveSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        
    }

    protected Message receive(int byteSize) throws IOException, InterruptedException {
        return this.receive(this.receiveSocket, byteSize);
    }

    protected void send(Message msg, int destinationPort) throws IOException, InterruptedException {
        this.send(this.sendSocket, msg, destinationPort);
    }

    @Override
    protected void closeSockets() {
        this.sendSocket.close();
        this.receiveSocket.close();
    }
}
