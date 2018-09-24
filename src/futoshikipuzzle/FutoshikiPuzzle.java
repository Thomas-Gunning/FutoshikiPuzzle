/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package futoshikipuzzle;

/**
 *
 * @author Thomas
 */
public class FutoshikiPuzzle {

    /**
     * @param args the command line arguments
     */
    int[][] grid = new int[5][5];
    String[][] rowContraints = new String[4][5];
    String[][] columnConstraints = new String[4][5];
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
    public void setSquare(int column, int row, int number){
       grid[column][row] = number; 
    }
    public void setRowConstraint(int column, int row, String constraint){
       rowContraints[column][row] = constraint;
        
    }
    public void setColumnConstraints(int column, int row, String constraint){
        columnConstraints[column][row] = constraint;
    }
    
    public void fillPuzzle(){
        
    }
}
