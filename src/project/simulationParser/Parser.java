package project.simulationParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import project.logger.Log;

/**
 * Class Parser implemented to parse the input text file
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Parser {
    protected final String FILE = "src/resources/sim2.txt";
    private Scanner sc;
    private static ArrayList<String> eventLines;

    /**
     * Constructor for the Parser class.
     */
    public Parser() {
        try {
            this.sc = new Scanner(new File(this.FILE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        eventLines = new ArrayList<>();
        this.parseTxtFile();
    }

    /**
     * Method that parses the simulation text file
     * found under resources
     */
    private void parseTxtFile(){
        String currLine;
        while (this.sc.hasNextLine()){
            currLine = sc.nextLine();
            eventLines.add(currLine);
        }
    }

    public String getFilePath(){
        return this.FILE;
    }
    
    /**
     * retreive the total requests as an arraylist
     * @return the event requests: ArrayList<String>
     */
    public ArrayList<String> getEventLines() {
        return eventLines;
    }
    
    /**
     * retreive the first request at the top of the arraylist
     * @return an event: String, the event request
     */
    public String getRequest() {
    	return eventLines.get(0);
    }
    
    /**
     * Method to remove the request from the event list
     */
    public void removeRequest() {
    	eventLines.remove(0);
    	Log.notification("PARSER", "Removed an Event", new Date(), "Parser");
    }
    
    /**
     * Checks if the number of requests is empty
     * @return true if empty, otherwise false
     */
    public static boolean isEmpty() {
    	return eventLines.isEmpty();
    }

}