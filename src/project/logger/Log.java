package project.logger;

import java.util.Date;

import project.messageSystem.Message;

/**
 * Class Log that acts as the logging system for the program.
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Log {

	public static void notification(String system, String msg, Date timestamp, String systemName) {
        System.out.println("[Time: " + timestamp.getTime() + "] " + "[" + system + "] " + "[Class: " + systemName + "] " + "[Notfication] " + msg);
		
	}
    
}
