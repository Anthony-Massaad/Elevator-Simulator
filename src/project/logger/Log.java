package project.logger;

import java.net.DatagramPacket;
import java.util.Date;

public class Log {

	/**
	 * Log Messages as notifications (None urgent messages) 
	 * @param system String, The Class in which is using method
	 * @param msg String, the message to display
	 * @param timestamp Date, the date in which it occurred
	 * @param systemName String, the exact name/index of the system calling this method
	 */
	public static void notification(String system, String msg, Date timestamp, String systemName) {
        System.out.println("[Time: " + timestamp.getTime() + "] " + "[" + system + "] " + "[Class: " + systemName + "] " + "[Notfication] " + msg);
	}
	
	/**
	 * Log Messages as Errors (urgent messages) 
	 * @param system String, The Class in which is using method
	 * @param msg String, the message to display
	 * @param timestamp Date, the date in which it occurred
	 * @param systemName String, the exact name/index of the system calling this method
	 */
	public static void error(String system, String msg, Date timestamp, String systemName) {
		System.out.println("[Time: " + timestamp.getTime() + "] " + "[" + system + "] " + "[Class: " + systemName + "] " + "[Notfication] " + msg);
	}


	public static void logSendMsg(String systemName, DatagramPacket packet, String msgAsString) {
		// Process the send datagram.
		System.out.println(systemName + ": Preparing to send packet:");
		System.out.println("To host -> " + packet.getAddress());
		System.out.println("Destination port -> " + packet.getPort());
		int len = packet.getLength();
		System.out.println("Length of data -> " + len);
		System.out.println("Send data as a string -> " + msgAsString);
	}

	/**
	 * Print the formarted messages for receiving. Print all the data in the packet
	 * @param systemName String, the name of the system
	 * @param packet DatagramPacket, the packet to collect information from
	 */
	public static void logReceiveMsg(String systemName, DatagramPacket packet, String msgAsString) {
		// Process the received datagram.
		System.out.println(systemName + ": Packet received:");
		System.out.println("From host -> " + packet.getAddress());
		System.out.println("From Host port -> " + packet.getPort());
		int len = packet.getLength();
		System.out.println("Length of data -> " + len);
		System.out.println("Receive data as a String -> " + msgAsString);
	}
    
}
