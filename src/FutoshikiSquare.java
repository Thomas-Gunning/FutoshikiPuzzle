
import java.io.Serializable;


/**
 * This is the FutoshikiSquare class, this holds the square number pencilMark X
 * Coordinate and y Coordinate this class is used in FutoshikiPuzzle
 *
 * @author Thomas Gunning
 * @version 1.0 02/04/2016
 */
public class FutoshikiSquare implements Serializable  {

    int squareNumber;
    String pencilMark;
    int xCoord;
    int yCoord;

    /**
     * This is the FutoshikiSquare constructor it sets the value of the number,
     * x Coordinate and y Coordinate
     *
     * @param value this is the value of the square
     * @param xCoord this is the x coordinate
     * @param yCoord this is the y coordinate
     */
    public FutoshikiSquare(int value, int xCoord, int yCoord) {
        this.squareNumber = value;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    /**
     * returns the Square number
     *
     * @return squareNumber this returns the number
     */
    public int getSquareNumber() {
        return squareNumber;
    }

    /**
     * sets the SquareNumber
     *
     * @param squareNumber this sets the number of the square
     */
    public void setSquareNumber(int squareNumber) {
        this.squareNumber = squareNumber;
    }

    /**
     * gets the pencilMark
     *
     * @return pencilMark returns a string
     */
    public String getPencilMark() {
        return pencilMark;
    }

    /**
     * sets the pencil mark
     *
     * @param pencilMark the string value to set
     */
    public void setPencilMark(String pencilMark) {
        this.pencilMark = pencilMark;
    }

    /**
     * gets the x Coordinate
     *
     * @return the x Coordinate of the square
     */
    public int getxCoord() {
        return xCoord;
    }

    /**
     * gets the y Coordinate
     *
     * @return the y coordinate of the square
     */
    public int getyCoord() {
        return yCoord;
    }
}
