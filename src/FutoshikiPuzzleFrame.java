
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * This is the FutoshikiPuzzleFrame which allows to produce the GUI and use the
 * FutoshikiPuzzle methods
 *
 * @author Thomas Gunning
 */
public class FutoshikiPuzzleFrame extends JFrame implements Serializable {

    public FutoshikiPuzzleFrame(FutoshikiPuzzle game) throws FileNotFoundException {
        super("Futoshiki Puzzle");
        this.game = game;
        this.size = game.getSize();

        createWindow();
    }
    private Font font = new Font("SansSerif", Font.BOLD, 20);
    public FutoshikiPuzzle game;
    public FutoshikiPuzzle reset;
    private int size;
    private JTextField t;
    private FutoshikiSquare square;
    private FutoshikiConstraint constraint;
    public JPanel gridPanel, puzzlePanel, cp;
    public JTextField[][] textFields;

    /**
     * This creates the window of the GUI with panels, buttons, textboxes
     *
     * @throws FileNotFoundException
     */
    private void createWindow() throws FileNotFoundException {
        JButton b;
        JLabel info;
        JPanel menuPanel, buttonPanel, scorePanel;

        // Window controls
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        // JPanel for the contentPane, sets the layout,borders and colors
        cp = (JPanel) getContentPane();
        cp.setLayout(new BoxLayout(cp, FlowLayout.LEFT));
        cp.setBorder(new LineBorder(Color.BLACK, 2, true));
        cp.setBackground(new Color(153, 204, 255));
        //JPanel for the buttonPanel, sets the layout, borders and colors
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        buttonPanel.setBackground(new Color(153, 204, 255));
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("Check");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                check();
            }
        });
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("reset");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reset();
            }
        });
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("New Puzzle");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newPuzzle();
                }

        });
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("Load");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                puzzlePanel.removeAll();
                puzzlePanel.repaint();
                try {
                    loadFromFile();
                } catch (IOException ex) {
                    Logger.getLogger(FutoshikiPuzzleFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FutoshikiPuzzleFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                pack();
                setVisible(true);
            }
        });
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("Solve");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                solve();

            }
        });
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("Save");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        buttonPanel.add(Box.createRigidArea(new Dimension(10, 10)));
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
                
            }

        });
        //This sets up the check button, and sets up the colours and sizes with
        //an action listener
        b = new JButton("Exit");
        buttonPanel.add(b);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cp.add(buttonPanel);

        puzzlePanel = new JPanel();
        puzzlePanel.setBackground(new Color(153, 204, 255));
        setGrid();

        cp.add(puzzlePanel);
        pack();
        setVisible(true);
    }
    //this sets the grid of textboxes and labels to be filled 
    public void setGrid() {
        textFields = new JTextField[size][size];

        JLabel c, empty;
        //  sets up the new jpanel 
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout((size * 2), (size * 2), 2, 2));
        gridPanel.setMinimumSize(new Dimension(800, 800));
        gridPanel.setBackground(new Color(153, 204, 255));
        //iterates through FutoshikiPuzzle 
        for (int i = 0; i < size - 1; i++) {

            for (int j = 0; j < size; j++) {

                square = game.getSquare(i, j);

                String number = Integer.toString(square.getSquareNumber());
                //if the numbers equals 0 set up the textbox and set the text to null
                if ("0".equals(number)) {

                    textFields[i][j] = new JTextField("field" + (i * j + 1));
                    textFields[i][j].setHorizontalAlignment(SwingConstants.CENTER);
                    textFields[i][j].setPreferredSize(new Dimension(50, 50));
                    textFields[i][j].setFont(font);
                    textFields[i][j].setText(null);
                    if (i == size - 1 && j == size - 1) {
                        empty = new JLabel(" ");
                        gridPanel.add(textFields[i][j]);
                        gridPanel.add(empty);
                    } else {
                        gridPanel.add(textFields[i][j]);
                    }
                    //adds an action listener which doesnt allow numbers above the size of the grid
                    //also doesnt allow moer than 2 numbers
                    textFields[i][j].addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyTyped(KeyEvent e) {
                            char number = e.getKeyChar();
                            char maxNumber = Integer.toString(size).charAt(0);
                            if (number < '1' || number > maxNumber && number != KeyEvent.VK_BACK_SPACE) {
                                e.consume();
                            }
                            for (int i = 0; i < size; i++) {
                                for (int j = 0; j < size; j++) {
                                    int textLength = textFields[i][j].getText().length();
                                    if (textLength == 1) {

                                    } else {
                                        textLength++;
                                    }
                                    if (textLength > 1) {
                                        textFields[i][j].setText(null);
                                    }
                                }
                            }
                        }

                    });
                    //if number is not equal to 0 it will set the textfields to their number
                    //and make them uneditable
                } else {
                    t = new JTextField(number);
                    t.setHorizontalAlignment(SwingConstants.CENTER);
                    t.setPreferredSize(new Dimension(50, 50));
                    t.setFont(font);
                    t.setBackground(Color.LIGHT_GRAY);
                    t.setEditable(false);
                    textFields[i][j] = t;

                    gridPanel.add(t);
                }
                //sets the row constraints
                if (i < size - 1 && j < size - 1) {
                    constraint = game.getRowConstraint(i, j);
                    c = new JLabel(constraint.getConstraint(), SwingConstants.CENTER);
                    c.setFont(font);
                    gridPanel.add(c);

                }

            }
            
            //sets the column constraints
            for (int k = 0; k < size - 1; k++) {
                constraint = game.getColumnConstraint(i, k);

                c = new JLabel(constraint.getConstraint(), SwingConstants.CENTER);
                c.setFont(font);
                empty = new JLabel("");

                gridPanel.add(c);
                gridPanel.add(empty);
            }
            //because of the final row not being printed correctly, this helps
            // print it
            if (i == size - 2) {
                empty = new JLabel("");
                gridPanel.add(empty);
                for (int j = 0; j < size; j++) {
                    square = game.getSquare(size - 1, j);

                    String number = Integer.toString(square.getSquareNumber());
                    if ("0".equals(number)) {
                        textFields[size - 1][j] = new JTextField("field" + (size - 1) * j + 1);
                        textFields[size - 1][j].setHorizontalAlignment(SwingConstants.CENTER);
                        textFields[size - 1][j].setPreferredSize(new Dimension(50, 50));
                        textFields[size - 1][j].setFont(font);
                        textFields[size - 1][j].setText(null);
                    } else {
                        textFields[size - 1][j] = new JTextField("field" + (size - 1) * j + 1);
                        textFields[size - 1][j].setHorizontalAlignment(SwingConstants.CENTER);
                        textFields[size - 1][j].setPreferredSize(new Dimension(50, 50));
                        textFields[size - 1][j].setFont(font);
                        textFields[size - 1][j].setBackground(Color.LIGHT_GRAY);
                        textFields[size - 1][j].setEditable(false);
                        textFields[size - 1][j].setText(number);

                    }

                    empty = new JLabel(" ");
                    gridPanel.add(textFields[size - 1][j]);
                    gridPanel.add(empty);
                }
            }
            empty = new JLabel("");
            gridPanel.add(empty);
        }
        puzzlePanel.add(gridPanel);
    }
    /**
     * this saves to the file location with the date 
     */
    public void saveToFile() throws FileNotFoundException, IOException {
        SimpleDateFormat date = new SimpleDateFormat("ddMMyyyy");
        Date now = new Date();
        String strDate = date.format(now);
        FileOutputStream fileOut = new FileOutputStream("N:\\Futoshiki" + strDate + ".ser");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(game);
        out.close();
        fileOut.close();
         JOptionPane.showMessageDialog(null, "Saved", "Saved", JOptionPane.PLAIN_MESSAGE);
    }
//this resets the textfields 
    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (textFields[i][j].isEditable() && textFields[i][j].getText() != "") {
                    textFields[i][j].setText("");
                }
            }
        }
    }
/**
 * this loads from the file location
 */
    public void loadFromFile() throws FileNotFoundException, IOException, ClassNotFoundException {
        SimpleDateFormat date = new SimpleDateFormat("ddMMyyyy");
        Date now = new Date();
        String strDate = date.format(now);

        try {
            FileInputStream fileIn = new FileInputStream("N:\\Futoshiki" + strDate + ".ser");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            game = (FutoshikiPuzzle) in.readObject();
            in.close();
            fileIn.close();

        } catch (IOException i) {
        } catch (ClassNotFoundException c) {
            JOptionPane.showMessageDialog(null, "File not found", "File not found", JOptionPane.PLAIN_MESSAGE);

        }
        setGrid();
    }
    /**
     * this saves the file 
     */
public void save(){
    try {
                    saveToFile();
                } catch (IOException ex) {
                    Logger.getLogger(FutoshikiPuzzleFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
}
/**
 * this checks if the whole grid is correct 
 */
public void check(){
    for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        String number = textFields[i][j].getText();
                        if (number == null || number.contains(" ") || number.equals("")) {
                            game.setSquare(i, j, 0);

                        } else {

                            game.setSquare(i, j, Integer.parseInt(number));
                        }
                    }
                }
                game.clearAllProblems();
                System.out.println(game.isLegal());

                ArrayList<String> problems = game.getProblemList();
                String allProblems = "";
                if (game.checkComplete() == false) {
                    allProblems = "The puzzle is not completely filled!\n";
                } else {
                    allProblems = "";
                }
                if (problems.size() == 0 && game.checkComplete() == true) {
                    allProblems = "WELL DONE!";
                    JOptionPane.showMessageDialog(null, allProblems, "PUZZLE LEGAL", JOptionPane.PLAIN_MESSAGE);
                } else {
                    for (int i = 0; i < problems.size(); i++) {
                        allProblems += (problems.get(i) + "\n");
                    }
                    JOptionPane.showMessageDialog(null, allProblems, "PUZZLE ILLEGAL", JOptionPane.PLAIN_MESSAGE);
                }
                System.out.println(game.toString());
}
/**
 * this solves the puzzle and presents an error message if it cant be solved 
 */
public void solve(){
    FutoshikiPuzzle solved = game;
                if (solved.solve(solved, 0, 0) == false) {
                    JOptionPane.showMessageDialog(null, "ERROR - Unable to solve, press new puzzle!", "PUZZLE ILLEGAL", JOptionPane.PLAIN_MESSAGE);
                }
                puzzlePanel.removeAll();
                puzzlePanel.repaint();
                setGrid();
                pack();
                setVisible(true);
}
/**
 * this generates a new puzzle
 */
public void newPuzzle(){
  
                String puzzleSize;
                puzzleSize = JOptionPane.showInputDialog("Puzzle Size");
                int sizeOfPuzzle = Integer.parseInt(puzzleSize);
                game = new FutoshikiPuzzle(sizeOfPuzzle);
                game.fillPuzzle();
                boolean isLegal = game.isLegal();
                while (isLegal == false) {
                    game = new FutoshikiPuzzle(sizeOfPuzzle);
                    game.fillPuzzle();
                    isLegal = game.isLegal();
                }
                  puzzlePanel.removeAll();
                puzzlePanel.repaint();
                size = sizeOfPuzzle;
                setGrid();
                pack();
                setVisible(true);
}
}
