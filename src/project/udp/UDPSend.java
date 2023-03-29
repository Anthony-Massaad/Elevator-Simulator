package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import project.constants.Addresses;
import project.messageSystem.Message;

/**
 * udp implemnentation for sending
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class UDPSend extends UDPImpl{

    private InetAddress addr;

	/**
	 * Constructor for UDPSend.
	 */
    private DatagramSocket sendSocket; 
    public UDPSend(String systemName, String addr){
        super(systemName);
        try {
            this.sendSocket = new DatagramSocket();

            // use localhost address for defualt, otherwise use custom address
            if (addr.equals(Addresses.DEFAULT.getAddress())) {
                this.addr = InetAddress.getLocalHost();
            }else{
                this.addr = InetAddress.getByName(addr);
            }
            // this.sendSocket.setSoTimeout(30000);
        } catch (SocketException | UnknownHostException e) {
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
        this.send(this.sendSocket, msg, destinationPort, this.addr);
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
