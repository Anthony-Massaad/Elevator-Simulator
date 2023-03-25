package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.*;
import project.simulationParser.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Test Class for the Parser class to ensure the simulation text file 
 * found under resources is parsed currently. 
 * @author Anthony Massaad SYSC 3303 Winter 2023 Lab A1
 *
 */
public class TestParser {
	
	/**
	 * checks if the size of events parsed from the simulation text file 
	 * is equal to the expected amount (2) 
	 */
	@Test
	public void testEventLinesSize() {
		Parser p = new Parser(); 
		assertTrue(p.getEventLines().size() > 0);
	}
	
	/**
	 * checks if the events parsed from the simulation text file 
	 * is what is supposed to be. At the same time, checks the size of the 
	 * arraylist of events in parser as it is decremented through
	 * @throws FileNotFoundException
	 */
	@Test
	public void testEventLinesContents() throws FileNotFoundException {
		Parser p = new Parser();
		Scanner sc = new Scanner(new File(p.getFilePath()));
		int total = p.getEventLines().size();
		// checks the content of the parser, and the size as requests are made
		while (sc.hasNextLine()){
            assertEquals(sc.nextLine(), p.getRequest());
            p.removeRequest();
            total--;
            assertEquals(total, p.getEventLines().size());
        }
		
	}
}
