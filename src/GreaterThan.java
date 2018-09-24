
/**
 *
 * This is the GreaterThan class, it is a subclass to the FutoshikiConstraint
 * class it stores the greater than values
 *
 * @author Thomas Gunning
 * @version 1.0 2/04/2016
 */
public class GreaterThan extends FutoshikiConstraint {

    private int num1;
    private int num2;
    boolean legal = true;

    /**
     * This is the GreaterThan constructor method it passes the constraint and
     * the two FutoshikiSquare numbers and passes them to the constructor
     *
     * @param constraint sets the constraint
     * @param num1 sets the first number
     * @param num2 sets the second number
     */
    public GreaterThan(String constraint, FutoshikiSquare num1, FutoshikiSquare num2) {
        super(constraint, num1, num2);

    }

    /**
     * This gets the constraint
     *
     * @return getContraint()
     */
    public String getConstraints() {
        return getConstraint();
    }

    /**
     * This is the abstract method
     *
     * @return legal returns a boolean value
     */
    @Override
    public boolean checkConstraint() {
        legal = true;
        //this checks if the first number is less than the second number
        this.num1 = getNum1().getSquareNumber();
        this.num2 = getNum2().getSquareNumber();
        if (num1 == 5) {
            legal = false;
            return legal;
        } else if (num2 == 1) {
            legal = false;
            return legal;
        } else if (num2 != 0 && num1 != 0) {

            legal = num1 < num2;
            return legal;
        }else{
        return legal;
        }
    }
}
