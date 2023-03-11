package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import project.messageSystem.Message;

public class UDPReceive extends UDPImpl{
    
    private DatagramSocket receiveSocket; 
    /**
     * Constructor for UDPReceive.
     * @param port Integer port.
     * @param systemName A string system name.
     */
    public UDPReceive(int port, String systemName){
        super(systemName);
        try {
            this.receiveSocket = new DatagramSocket(port);
            this.receiveSocket.setSoTimeout(30000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to receive a message.
     * @param byteSize Integer byteSize.
     * @return A received message.
     * @throws IOException
     * @throws InterruptedException
     */
    protected Message receive(int byteSize) throws IOException, InterruptedException {
        return this.receive(this.receiveSocket, byteSize);
    }

    /**
     * Overriden method to close this socket once completed, and exit the program.
     */
    @Override
    protected void closeSockets() {
        this.receiveSocket.close();
        System.exit(1);
    }

}
