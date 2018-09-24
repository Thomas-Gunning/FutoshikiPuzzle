
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * This is the futoshikiPuzzleMenuFrame it generates the menu for the user to
 * choose
 *
 * @author Thomas
 * @version may 2016
 */
public class FutoshikiPuzzleMenuFrame extends JFrame implements ActionListener {

    public FutoshikiPuzzleMenuFrame() {
        super("Futoshiki Puzzle");

        createWindow();
    }

    // private final FutoshikiPuzzle	    game;
    private JLabel label;
    private JTextField numberField;
    private JTextField counterField;
    private FutoshikiPuzzle game;

    private void createWindow() {
        JButton b;
        JLabel info;
        JPanel cp, menuPanel, numberPanel, buttonPanel, scorePanel;

        // Window controls
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create the content pane
        cp = (JPanel) getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
        cp.setBorder(new LineBorder(Color.BLACK, 2, true));
        cp.setBackground(new Color(153, 204, 255));

       //adds a new menu panel 
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        menuPanel.setBackground(new Color(153, 204, 255));
        //add a new label
        label = new JLabel("WELCOME TO FUTOSHIKI");
        label.setAlignmentX(CENTER_ALIGNMENT);
        label.setFont(new Font("Univers Condenced, Linotype Universe", Font.BOLD, 40));

        menuPanel.add(label);
        b = new JButton("New Game");
        //adds a new button to the jPanel
        b.setFont(new Font("Univers Condenced, Linotype Universe", Font.BOLD, 40));
        b.setAlignmentX(CENTER_ALIGNMENT);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        //adds an action listener to create a new game
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String puzzleSize;
                puzzleSize = JOptionPane.showInputDialog("Puzzle Size");
                int sizeOfPuzzle = Integer.parseInt(puzzleSize);
                Color textColour = new Color(112, 217, 169);
                dispose();
                game = new FutoshikiPuzzle(sizeOfPuzzle);
                game.fillPuzzle();
                while (game.isLegal() == false) {
                    game = new FutoshikiPuzzle(sizeOfPuzzle);
                    game.fillPuzzle();
                }
                /*                game.setSquare(0, 4, 4);
                game.setSquare(1,1,4);
                game.setSquare(2, 3, 1);
               game.setColumnConstraints(2, 1, "^", game.getSquare(2, 1), game.getSquare(3,1));
                 game.setColumnConstraints(3, 1, "v", game.getSquare(3, 1), game.getSquare(4,1));
                 game.setRowConstraints(3, 1, "<", game.getSquare(3, 1), game.getSquare(3,2));
                 game.setRowConstraints(3, 3, "<", game.getSquare(3, 3), game.getSquare(3,4));*/

                System.out.println(game.toString());
                FutoshikiPuzzleFrame test = null;
                try {
                    test = new FutoshikiPuzzleFrame(game);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FutoshikiPuzzleMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
                test.setVisible(true);
            }

        });
      
        menuPanel.add(b);
        menuPanel.add(Box.createRigidArea(new Dimension(5, 5)));

        b = new JButton("Load Game");
        
        b.setAlignmentX(CENTER_ALIGNMENT);

        b.setFont(new Font("Univers Condenced, Linotype Universe", Font.BOLD, 40));
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        menuPanel.add(b);
        menuPanel.add(Box.createRigidArea(new Dimension(5, 5)));
        //creates a new button and adds an action listener to load a file
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    loadFromFile();
                } catch (IOException ex) {
                    Logger.getLogger(FutoshikiPuzzleMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(FutoshikiPuzzleMenuFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
            //adds an exit button 
        b = new JButton("Exit");
        b.setAlignmentX(CENTER_ALIGNMENT);
        b.setForeground(Color.WHITE);
        b.setBackground(Color.LIGHT_GRAY);
        b.setFont(new Font("Univers Condenced, Linotype Universe", Font.BOLD, 40));
        menuPanel.add(b);
        b.setAlignmentX(CENTER_ALIGNMENT);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        cp.add(menuPanel, BoxLayout.X_AXIS);
        //panel for the menu 
        pack();
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
/**
 * loads from a file location 
 * @throws FileNotFoundException
 * @throws IOException
 * @throws ClassNotFoundException 
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
            System.out.println("Game not found");

        }
        FutoshikiPuzzleFrame loadedFrame = new FutoshikiPuzzleFrame(game);

    }
}
