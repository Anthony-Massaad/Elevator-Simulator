package project.simulationParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class Parser implemented to parse the input text file
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
public class Parser {
    private final String FILE = "src/resources/sim.txt";
    private Scanner sc;
    private ArrayList<String> eventLines;

    /**
     * Constructor for the Parser class.
     */
    public Parser() {
        try {
            this.sc = new Scanner(new File(this.FILE));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.eventLines = new ArrayList<>();
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
            this.eventLines.add(currLine);
        }
    }

    
    /**
     * retreive the total requests as an arraylist
     * @return the event requests: ArrayList<String>
     */
    public ArrayList<String> getEventLines() {
        return this.eventLines;
    }
    
    /**
     * retreive the first request at the top of the arraylist
     * @return an event: String, the event request
     */
    public String getRequest() {
    	return this.eventLines.get(0);
    }
    
    /**
     * Method to remove the request from the event list
     */
    public void removeRequest() {
    	this.eventLines.remove(0);
    }
    
    /**
     * Checks if the number of requests is empty
     * @return true if empty, otherwise false
     */
    public boolean isEmpty() {
    	return this.eventLines.isEmpty();
    }

}
