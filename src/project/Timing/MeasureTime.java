package project.Timing;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class responsible for measuring time
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class MeasureTime {
    private final String FILENAME = "src/resources/measure/measurements.txt";

    private long startTime;
    private long endTime;
    /**
     * Constructor
     */
    public MeasureTime(){
        File f = new File(this.FILENAME);
        try {
            if (f.createNewFile()){
                // create file
                System.out.println("Measurement text file added");
            }else{
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.startTime = 0;
        this.endTime = 0;
    }

    /**
     * start the time
     */
    public void start(){
        this.startTime = System.currentTimeMillis();
    }

    /**
     * end the time and append it to the measuring text file under resources 
     * @param output String, the output
     */
    public void end(String output){
        this.endTime = System.currentTimeMillis();
        long diff = this.endTime - this.startTime;
        System.out.println("DIFFERENCE IS: " + diff);

        try (FileWriter write = new FileWriter(this.FILENAME, true)) {
            write.write(output + ": " + diff + "ms\n");
            write.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("SOMETHING WENT WRONG BBABBYYY");
            e.printStackTrace();
        }

        this.startTime = 0;
        this.endTime = 0;
    }
}
