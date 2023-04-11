package project.gui.components.Text.FloorInput;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.*;

/**
 * Responsible for displaying the floor inputs
 * @author Anthony Massaad, Maximus Curkovic, Dorothy Tran, Elisha Catherasoo, Cassidy Pacada SYSC3303 Group 2
 */
public class FloorInputText extends JPanel {
    
    private JTextArea textArea;
    private JLabel title; 
    private JScrollPane scrollPane;
    /**
     * constructor
     */
    public FloorInputText(){
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.title = new JLabel("Floor Inputs");
        this.title.setFont(new Font("Arial", Font.BOLD, 16));
        this.title.setOpaque(true);
        this.title.setForeground(Color.WHITE);
        this.title.setBackground(Color.BLACK);
        this.title.setMaximumSize(new Dimension(Integer.MAX_VALUE, this.title.getPreferredSize().height));
        this.title.setBorder((BorderFactory.createMatteBorder(1, 1, 0, 1, Color.BLACK)));
        this.title.setHorizontalAlignment(SwingConstants.CENTER);
        this.scrollPane = new JScrollPane();
        this.scrollPane.setBorder((BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK)));
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.textArea = new JTextArea();
        this.textArea.setEditable(false);
        this.scrollPane.setViewportView(this.textArea);
        this.scrollPane.setPreferredSize(new Dimension(this.textArea.getWidth(), 200));
        this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); 
        this.add(this.title);
        this.add(this.scrollPane);
    }

    /**
     * append a text to the feild
     * @param text String, the input
     */
    public void appendText(String text){
        this.textArea.append(text + "\n");
    }
}
