package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import project.messageSystem.Message;

public class UDPSend extends UDPImpl{

	/**
	 * Constructor for UDPSend.
	 */
    private DatagramSocket sendSocket; 
    public UDPSend(String systemName){
        super(systemName);
        try {
            this.sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Protected method to send a packet to a socket.
     * @param msg String message.
     * @param destinationPort Integer destination port.
     * @throws IOException
     * @throws InterruptedException
     */
    protected void send(Message msg, int destinationPort) throws IOException, InterruptedException {
        this.send(this.sendSocket, msg, destinationPort);
    }

    /**
     * Overriden method to close this socket once completed, and exit the program.
     */
    @Override
    protected void closeSockets() {
        this.sendSocket.close();
        System.exit(1);
    }

}
