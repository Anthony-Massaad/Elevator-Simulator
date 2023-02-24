package project.logger;

import java.util.Date;

/**
 * Class Log that acts as the logging system for the program.
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
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
    
}
