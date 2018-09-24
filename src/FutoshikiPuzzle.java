
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * The FutoshikiPuzzle class creates the Futoshiki grid it uses the
 * FutoshikiSquare and the FutoshikiConstraint classes to generate the grid, it
 * then checks the grid if it is legal or not
 *
 * @author Thomas Gunning
 * @version 1.0 02/04/2016
 */
public class FutoshikiPuzzle implements Serializable  {

    Random rdm = new Random();
    private ArrayList<String> problems;
    private int row = 0;
    private int column = 0;
    private boolean legal = true;
    private final int size;
    private FutoshikiPuzzleFrame ui;
    private final FutoshikiSquare[][] grid;
     private FutoshikiPuzzle originalGrid;
    
     
    private final FutoshikiConstraint[][] rowConstraints;
    private final FutoshikiConstraint[][] columnConstraints;
 
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        FutoshikiPuzzleMenuFrame frame = new FutoshikiPuzzleMenuFrame();

    }

    /**
     * This is the constructor method for the FutoshikiPuzzle it sets the size
     * of the grid, it creates and fills the array with objects
     *
     * @param sizeOfGrid this sets the size of the grid
     */
    
    public FutoshikiPuzzle(int sizeOfGrid) {
        problems = new ArrayList<>();
        size = sizeOfGrid;
        grid = new FutoshikiSquare[size][size];
        
        rowConstraints = new FutoshikiConstraint[size][size - 1];
        columnConstraints = new FutoshikiConstraint[size][size - 1];
        //Creates instances and fills in the grid
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = new FutoshikiSquare(0, j, i);
            }
        }
        
        //Creates instances and fills in the row and column constraints
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                rowConstraints[i][j] = new GreaterThan(null, null, null);
                columnConstraints[i][j] = new GreaterThan(null, null, null);
            }
        }
    }
    public void setGUI(FutoshikiPuzzleFrame ui) {
	this.ui = ui;
    }
    /**
     * This sets the square value with the column, row and number
     *
     * @param column this sets the column
     * @param row this sets the row
     * @param number this puts the number into the futoshikiSquare
     */
     void setSquare(int column, int row, int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Number is less than 0");
        }
        if (number > size) {
            throw new IllegalArgumentException("Number is larger than " + size);
        }
        grid[column][row].setSquareNumber(number);

    }

    /**
     * This sets the row constraints with the column, row, constraint and the
     * two numbers either side of the constraint
     *
     * @param column this is the rowConstraint
     * @param row this is the row for the rowConstraint
     * @param constraint this is the constraint to be inputted
     * @param num1 this is the first FustoshikiConstraint
     * @param num2 this is the second FutodhikiConstraint
     */
    public void setRowConstraints(int column, int row, String constraint, FutoshikiSquare num1, FutoshikiSquare num2) {
        //Checks the constraint that is being passed and puts them in the GreaterThan or LessThan classes else it throws an exception
        if ("<".equals(constraint)) {
            rowConstraints[column][row] = new GreaterThan(constraint, num1, num2);
        } else if (">".equals(constraint)) {
            rowConstraints[column][row] = new LessThan(constraint, num1, num2);
        } else {
            throw new IllegalArgumentException("invalid Row constraint");
        }

    }

    /**
     * This sets the row constraints with the column, row, constraint and the
     * two numbers either side of the constraint
     *
     * @param column this is the columnConstraint
     * @param row this is the row for the columnConstraint
     * @param constraint this is the constraint to be inputted
     * @param num1 this is the first FustoshikiConstraint
     * @param num2 this is the second FutodhikiConstraint
     */
    public void setColumnConstraints(int column, int row, String constraint, FutoshikiSquare num1, FutoshikiSquare num2) {
        //Checks the constraint that is being passed and puts them in the GreaterThan or LessThan classes else it throws an exception
        if ("^".equals(constraint)) {
            columnConstraints[column][row] = new GreaterThan(constraint, num1, num2);
        } else if ("v".equals(constraint)) {
            columnConstraints[column][row] = new LessThan(constraint, num1, num2);
        } else {
            throw new IllegalArgumentException("invalid Column constraint");
        }
    }

    /**
     * This fills the puzzle first with the numbers then columns constraints and
     * finally the row constraints
     */
    public void fillPuzzle() {
        //This fills the puzzle with then numbers, then the columns and the rows
        int numColumnConstraints = rdm.nextInt(size - 1) + 1;
        int numRowConstraints = rdm.nextInt(size - 1) + 1;
        fillNumbers();
        fillColumns(numColumnConstraints);
        fillRows(numRowConstraints);
        originalGrid = new FutoshikiPuzzle(size);
        for(int i = 0; i < size; i++){
            for(int j = 0; j< size; j++){
              originalGrid.setSquare(i, j, grid[i][j].getSquareNumber()); 
              
            }
        }
       
                
        
    }

    /**
     * This creates the grid using a toString method
     *
     * @return gridOutput this outputs the grid
     */
    @Override
    public String toString() {
        String gridOutput = "";

        for (int i = 0; i < size; i++) {
            gridOutput += "  ---   ";
        }
        gridOutput += "\n";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j].getSquareNumber() > 99) {
                    gridOutput += " |" + grid[i][j].getSquareNumber() + "| ";
                } else if (grid[i][j].getSquareNumber() > 9) {
                    gridOutput += " | " + grid[i][j].getSquareNumber() + "| ";
                } else if (grid[i][j].getSquareNumber() != 0) {
                    gridOutput += " | " + grid[i][j].getSquareNumber() + " | ";
                } else {
                    gridOutput += " |   | ";
                }
                if (j < size - 1) {
                    String cons = rowConstraints[i][j].getConstraint();
                    if (cons != null) {
                        gridOutput += cons;
                    } else {
                        gridOutput += " ";
                    }
                }

            }
            gridOutput += "\n";

            for (int k = 0; k < size; k++) {
                gridOutput += "  ---   ";
            }
            gridOutput += "\n";
            for (int j = 0; j < size; j++) {
                if (j < size - 1) {
                    String colCons = columnConstraints[i][j].getConstraint();
                    if (colCons != null) {
                        gridOutput += "   " + colCons + "    ";
                    } else {
                        gridOutput += "        ";
                    }
                }
            }

            gridOutput += "\n\n";
            for (int k = 0; k < size; k++) {
                gridOutput += "  ---   ";
            }
            gridOutput += "\n";
        }

        return gridOutput;

    }

    /**
     * This checks the number duplicates and checks the values according to the
     * constrains, first starting with the rows and then the columns
     *
     * @return legal this returns a boolean value
     */
    public boolean isLegal() {
        //check the rows first then the columns
        legal = true;
        int value;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                //get the value to test against the value in the same row and column
                value = grid[i][j].getSquareNumber();
                CheckRow(j, i, value);
                CheckColumn(j, i, value);
            }
            if(CheckConstraints(i) == false){
                legal = false;
            }
        }
        return legal;
    }

    /**
     * Passes the problem and adds it to a string
     *
     * @param problem this returns a string value
     */
    private void getProblems(String problem) {
        String allProblems = null;
        allProblems = problem + "\n";
        problems.add(problem);
        System.out.println(allProblems);
    }
    public void clearAllProblems(){
        problems.clear();
    }

    /**
     * This private method adds numbers to the grid
     */
    private void fillNumbers() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < rdm.nextInt(size ); j++) {

                int value = rdm.nextInt(size);

                setSquare(rdm.nextInt(size-1), rdm.nextInt(size - 1), value);

            }
        }
    }

    /**
     * This fills the row constraints
     *
     * @param numRowConstraints the amount of row constraints
     */
    private void fillRows(int numRowConstraints) {
        //this sets the constraints by getting the number of constraints to have and then setting them
        for (int i = 0; i < numRowConstraints; i++) {
            row = rdm.nextInt(size-1);
            column = rdm.nextInt(size-1);
            int randomConstraint = rdm.nextInt(2);
            if (randomConstraint == 1) {
                String constraint = "<";
                setRowConstraints(row, column, constraint, grid[row][column], grid[row][column + 1]);
            } else {
                String constraint = ">";
                setRowConstraints(row, column, constraint, grid[row][column], grid[row][column + 1]);
            }
        }
    }

    /**
     * This fills the column constraints
     *
     * @param numColumnConstraints the amount of column constraints
     */
    private void fillColumns(int numColumnConstraints) {
        //this sets the constraints by getting the number of constraints to have and then setting them
        for (int i = 0; i < numColumnConstraints; i++) {
            row = rdm.nextInt(size -1);
            column = rdm.nextInt(size -1);
            int randomConstraint = rdm.nextInt(2);
            if (randomConstraint == 1) {
                String constraint = "^";
                setColumnConstraints(row, column, constraint, grid[row][column], grid[row + 1][column]);
            } else {
                String constraint = "v";
                setColumnConstraints(row, column, constraint, grid[row][column], grid[row + 1][column]);
            }
        }
    }

    /**
     * This checks the constraints of both the rows and the columns
     *
     * @param loop this allows us to the the main value to compare with
     * @return legal this returns if the constraint are legal
     */
    private boolean CheckConstraints(int loop) {
        //Checks all of the constraints within the row and columns

        boolean constraintLegal = true;
        for (int k = 0; k < size - 1; k++) {
            String rowConstraint = rowConstraints[loop][k].getConstraint();
            String columnConstraint = columnConstraints[loop][k].getConstraint();
           
            if (rowConstraint != null) {

                constraintLegal = rowConstraints[loop][k].checkConstraint();
                if (constraintLegal == false) {
                    getProblems("There is an incorrect constraint between\n" + rowConstraints[loop][k].getNum1().getSquareNumber()
                            + rowConstraints[loop][k].getConstraint() + rowConstraints[loop][k].getNum2().getSquareNumber() + " at co ordinates " + (loop + 1) + "," + (k + 1));
                    constraintLegal = false;
                    return constraintLegal;
                }

            }
    
            
            if (columnConstraint != null) {
      
                constraintLegal = columnConstraints[loop][k].checkConstraint();
                if (constraintLegal == false) {
                    getProblems("There is an incorrect constraint between\n" + columnConstraints[loop][k].getNum1().getSquareNumber() + "\n"
                            + columnConstraints[loop][k].getConstraint() + "\n" + columnConstraints[loop][k].getNum2().getSquareNumber() + "\n" + "at co ordinates " + (loop + 1) + "," + (k + 1));
                    constraintLegal = false;
                    return constraintLegal;
                }
            }
        }
        //}
        return constraintLegal;
    }

    /**
     * This checks the rows for duplicate values
     *
     * @param j
     * @param i
     * @param value inputs the value to check if there are no other values
     * present
     * @return legal returns if it is legal or not
     */
    private boolean CheckRow(int j, int i, int value) {
        //checks the rows by inputting a value and checking against another value
        for (int k = j; k < size - 1; k++) {
            if (value != 0) {
                if (value == grid[i][k + 1].getSquareNumber()) {
                    legal = false;
                    getProblems("There is a duplicate number with " + grid[i][j].getSquareNumber() + " at a position of " + (grid[i][j].getxCoord() + 1)
                            + "," + (grid[i][j].getyCoord() + 1) + " and " + (grid[i][k + 1].getxCoord() + 1) + "," + (grid[i][k + 1].getyCoord() + 1));
                }
            }
        }
        return legal;
    }

    /**
     * This checks the columns for duplicates values
     *
     * @param j
     * @param i
     * @param value inputs the value to check if there are no other values
     * present
     * @return legal returns if it is legal or not
     */
    private boolean CheckColumn(int j, int i, int value) {
        //checks the columns by inputting a value and comparing it against another
        
        for (int k = i; k < size - 1; k++) {
            if (value != 0) {
                if (value == grid[k + 1][j].getSquareNumber()) {
                    legal = false;
                    getProblems("There is a duplicate number with " + grid[i][j].getSquareNumber() + " at a position of " + grid[i][j].getxCoord()
                            + "," + grid[i][j].getyCoord() + " and " + grid[k + 1][j].getxCoord() + "," + grid[k + 1][j].getyCoord());

                }
            }

        }

        return legal;
    }

    /**
     * returns a FutoshikiConstraint from the columns
     *
     * @param row use in columnConstraints
     * @param column use in columnConstraints
     * @return columnConstraints[row][column] returns the columnConstraint
     */
    public FutoshikiConstraint getColumnConstraint(int row, int column) {
        return columnConstraints[row][column];
    }

    /**
     * returns a FutoshikiConstraint from the rows
     *
     * @param row used in rowConstraints
     * @param column used in rowConstraints
     * @return rowConstraints[row][column] returns the rowConstraint
     */
    public FutoshikiConstraint getRowConstraint(int row, int column) {
        return rowConstraints[row][column];
    }

    /**
     * returns a FutoshikiSquare from the grid
     *
     * @param row used in grid
     * @param column used in grid
     * @return grid[row][column] returns the value in grid at that position
     */
    public FutoshikiSquare getSquare(int row, int column) {
        return grid[row][column];
    }

    /**
     * sets the pencil mark
     *
     * @param row row of the grid
     * @param column column of the grid
     * @param mark the string value passing
     */
    public void setPencilmark(int row, int column, String mark) {
        grid[row][column].setPencilMark(mark);
    }

    /**
     * gets the pencil mark
     *
     * @param row row of the grid
     * @param column column of the grid
     * @return String value of the pencil mark
     */
    public String getPencilMark(int row, int column) {
        return grid[row][column].getPencilMark();
    }
    
 /**
  * this checks if the whole grid is filled with any values
  * @return boolean
  */
    public boolean checkComplete(){
        boolean checkComplete = true;
        for(int i = 0; i < size ; i++){
            for(int j = 0; j < size; j++){
                if(grid[i][j].getSquareNumber() == 0){
                    checkComplete = false; 
                    break;
                }
            }
        }
        return checkComplete;
    }

   
    /**
     * this solves the puzzle recursively 
     * @param game FutoshikiPuzzle
     * @param row int
     * @param column int
     * @return boolean
     */
   public boolean solve(FutoshikiPuzzle game, int row, int column){
      FutoshikiPuzzle solvedGame = game;
      
       if(isLegal() == false ){
           grid[row][column].setSquareNumber(0);
           return false;
          
        }else{
           if(checkComplete() == true && isLegal() == true){
               return true;
           }else{
                for(int i = 0; i < size; i++){
                        for(int j = 0; j < size; j++){
                            boolean legal2 = isLegal();
                        
                               if(solvedGame.getSquare(i, j).getSquareNumber() != grid[i][j].getSquareNumber() ||  grid[i][j].getSquareNumber() == 0 )  {
                                   
                               int k = 0;
                                    while(k < size){
                                    grid[i][j].setSquareNumber(k +1);
                                     legal2 = solve(solvedGame, i, j);
                                     k++;
                                    if(legal2 == false && isLegal() == false){
                                        grid[row][column].setSquareNumber(0);
                                      return false;
                                      
                                    }else if(legal2 == true && checkComplete() == true){
                                        return true;
                                    }
                                    
                                    }
                                    if(legal2 == false){
                                        grid[row][column].setSquareNumber(0);
                                        return legal2;
                                    }else{
                                        legal2 = checkComplete();
                                        if(legal2 == true){
                                            return legal2;
                                        }
                                    }
                                }
                               if(legal2 == false){
                                   grid[row][column].setSquareNumber(0);
                                   return legal2;
                                   
                               }else{
                                   legal2 = checkComplete();
                                   if(legal2 == true){
                                       return legal2;
                                   }
                                         
                               }
                        }
                }
           }
       }
       return checkComplete();
   }
   /**
    * gets the arrayList
    * @return ArraList<Strings>
    */
  public ArrayList<String> getProblemList(){
      return problems;
  } 
  
 
 /**
  * gets the size
  * @return int
  */
  public int getSize(){
      return size;
  }
}
