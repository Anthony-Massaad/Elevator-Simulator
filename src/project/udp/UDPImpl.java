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
	
	public UDPImpl(String systemName) {
		this.systemName = systemName; 
	}

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
	
	protected void send(DatagramSocket sendSocket, Message msg, int destinationPort) throws IOException, InterruptedException {
		byte[] data = this.serializeMessage(msg);  
		this.sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), destinationPort);
		Log.logSendMsg(this.systemName, this.sendPacket, msg.toString());
		Thread.sleep(1000);		// slow things down
		sendSocket.send(this.sendPacket);
		System.out.println("Packet sent.");
	}
	
	protected Message receive(DatagramSocket receiveSocket, int byteSize) throws IOException, InterruptedException {
		byte[] data = new byte[byteSize];
		this.receivePacket = new DatagramPacket(data, data.length);
		receiveSocket.receive(this.receivePacket);
		Message msg = this.deserializeMessage(this.receivePacket.getData());
		Log.logReceiveMsg(this.systemName, this.receivePacket, msg.toString());
		return msg; 
	}
	
	protected abstract void closeSockets();
	
}
