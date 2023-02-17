package project.logger;

/**
 * Class Log that acts as the logging system for the program.
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Log {

    private static final boolean DEBUG = false;
    private static final boolean INFO = true;
    private static final boolean ERROR = true;

    /**
     * Method that displays a Log INFO message 
     * @param system, the name of the system
     * @param msg, the INFO message displayed
     */
    public static void info(String system, String msg){
        if (INFO){
            System.out.println("[" + system + " INFO] " + msg);
        }
    }

    /**
     * Method that displays a Log DEBUG message 
     * @param system, the name of the system
     * @param msg, the DEBUG message displayed
     */
    public static void debug(String system, String msg){
        if (DEBUG){
            System.out.println("[" + system + " DEBUG] " + msg);
        }
    }

    /**
     * Method that displays a Log ERROR message 
     * @param system, the name of the system
     * @param msg, the ERROR message displayed
     */
    public static void error(String system, String msg){
        if (ERROR){
            System.out.println("[" + system + " ERROR] " + msg);
        }
    }
}
