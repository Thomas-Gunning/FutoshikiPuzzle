
import java.io.Serializable;


/**
 * This is the FutoshikiConstraint class it sets the constraint it is an
 * abstract class and has two subclasses which are GreaterThan and LessThan
 * classes
 *
 * @author Thomas Gunning
 * @version 1.0 02/04/2016
 */
abstract class FutoshikiConstraint implements Serializable {

    private String constraint;
    FutoshikiSquare num1;
    FutoshikiSquare num2;

    /**
     * this is the FutoshikiConstraint constructor method it sets the constraint
     * and the two FutoshikiSquares next to the constraint
     *
     * @param constraint passes a string
     * @param num1 passes the first FutoshikiSquare
     * @param num2 passes the second FutoshikiSquare
     */
    public FutoshikiConstraint(String constraint, FutoshikiSquare num1, FutoshikiSquare num2) {
        this.constraint = constraint;
        this.num1 = num1;
        this.num2 = num2;
    }

    /**
     * gets the constraint
     *
     * @return constraint return the constraint
     */
    public String getConstraint() {
        return constraint;
    }

    /**
     * sets the constraint
     *
     * @param constraint sets the constraint
     */
    public void setConstraint(String constraint) {
        this.constraint = constraint;
    }

    /**
     * This returns the first FutoshikiSquare
     *
     * @return num1 get the first number
     */
    public FutoshikiSquare getNum1() {
        return num1;
    }

    /**
     * This sets the first FutoshikiSquare
     *
     * @param num1 set first number
     */
    public void setNum1(FutoshikiSquare num1) {
        this.num1 = num1;
    }

    /**
     * This returns the second FutoshikiSquare
     *
     * @return num2 return the second number
     */
    public FutoshikiSquare getNum2() {
        return num2;
    }

    /**
     * This sets the second FutoshikiSquare
     *
     * @param num2 sets the first number
     */
    public void setNum2(FutoshikiSquare num2) {
        this.num2 = num2;
    }

    /**
     * this the abstract method used in GreaterThan and LessThan classes
     *
     * @return if the constraint is correct
     */
    public abstract boolean checkConstraint();
}
