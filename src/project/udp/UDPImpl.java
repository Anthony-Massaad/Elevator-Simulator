package project.udp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;

import project.logger.Log;
import project.messageSystem.Message;

public abstract class UDPImpl {
	
	private DatagramPacket sendPacket, receivePacket; 
	protected final String systemName; 
	
	/**
	 * Constructor for UDPImpl.
	 * @param systemName String system name.
	 */
	public UDPImpl(String systemName) {
		this.systemName = systemName; 
	}

	/**
	 * Method to serialize a message into bytes.
	 * @param msg String message.
	 * @return The converted message into bytes.
	 */
	private byte[] serializeMessage(Message msg){
		try{
			ByteArrayOutputStream sout = new ByteArrayOutputStream(); 
			ObjectOutputStream out = new ObjectOutputStream(sout);    
			out.writeObject(msg);    
			out.flush();     

			byte[] data = Arrays.copyOf(sout.toByteArray(), sout.toByteArray().length);
			return data;
		}catch (Exception e){
			System.out.println("ERROR: Failed to convert to bytes");
			e.printStackTrace();
		}
		throw new Error("Failed to Serialize a message");
	}

	/**
	 * Method to deserialize a message into a String.
	 * @param data Byte message of data.
	 * @return The converted message into a string.
	 */
	private Message deserializeMessage(byte[] data){
		try{
			ByteArrayInputStream in = new ByteArrayInputStream(data);
			ObjectInputStream out = new ObjectInputStream(in);
			return (Message) out.readObject();
		}catch (Exception e){
			System.out.println("ERROR: Failed to convert to object");
			e.printStackTrace();
		}
		throw new Error("Failed to Deserialize a byte list");
	}
	
	/**
	 * Protected method to send a packet to a specified socket.
	 * @param sendSocket A specified socket.
	 * @param msg A string message.
	 * @param destinationPort An integer destination port number.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected void send(DatagramSocket sendSocket, Message msg, int destinationPort) throws IOException, InterruptedException {
		byte[] data = this.serializeMessage(msg);  
		this.sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), destinationPort);
		Log.logSendMsg(this.systemName, this.sendPacket, msg.toString());
		Thread.sleep(1000);		// slow things down
		sendSocket.send(this.sendPacket);
		// System.out.println("Packet sent.");
	}
	
	/**
	 * Protected method for receiving a packet.
	 * @param receiveSocket The socket that the message is being received to.
	 * @param byteSize An integer byteSize.
	 * @return A string message.
	 * @throws IOException
	 * @throws InterruptedException
	 */
	protected Message receive(DatagramSocket receiveSocket, int byteSize) throws IOException, InterruptedException {
		byte[] data = new byte[byteSize];
		this.receivePacket = new DatagramPacket(data, data.length);
		receiveSocket.receive(this.receivePacket);
		Message msg = this.deserializeMessage(this.receivePacket.getData());
		Log.logReceiveMsg(this.systemName, this.receivePacket, msg.toString());
		return msg; 
	}
	
	/**
	 * Abstract method used by UDPReceive, UDPImpl and UDPBoth to close all necessary sockets.
	 */
	protected abstract void closeSockets();
	
}
