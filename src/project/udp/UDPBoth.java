package project.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import project.constants.Addresses;
import project.messageSystem.Message;

/**
 * UDP implementatyion for send and recieve
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class UDPBoth extends UDPImpl{
    
    private DatagramSocket sendSocket, receiveSocket; 
    private InetAddress addr; 
    
    /**
     * Constructor for UDPBoth.
     * @param port An integer port.
     * @param systemName A string system name.
     */
    public UDPBoth(int port, String systemName, String addr) {
        super(systemName);
        try {
            this.sendSocket = new DatagramSocket();
            this.receiveSocket = new DatagramSocket(port);

            // use localhost address for defualt, otherwise use custom address
            if (addr.equals(Addresses.DEFAULT.getAddress())){
                this.addr = InetAddress.getLocalHost();
            }else{
                this.addr = InetAddress.getByName(addr);
            }
            
            // this.receiveSocket.setSoTimeout(30000);
            // this.sendSocket.setSoTimeout(30000);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
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
     * Method to close all necessary sockets.
     */
    @Override
    protected void closeSockets() {
        this.sendSocket.close();
        this.receiveSocket.close();
    }
}
