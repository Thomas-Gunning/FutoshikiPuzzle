
/**
 * This is the LessThan class, it is a subclass to the FutoshikiConstraint
 * class it stores the Less than values
 *
 * @author tmeg20
 */
public class LessThan extends FutoshikiConstraint {

    private int num1;
    private int num2;
    private boolean legal = true;

    /**
     * This is the LessThan constructor method it passes the constraint and the
     * two FutoshikiSquare numbers and passes them to the constructor
     *
     * @param constraint sets the constraint
     * @param num1 sets the first number
     * @param num2 sets the second number
     */
    public LessThan(String constraint, FutoshikiSquare num1, FutoshikiSquare num2) {
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
        //this checks if the second number is less than the first number
        legal = true;
        this.num1 = getNum1().getSquareNumber();
        this.num2 = getNum2().getSquareNumber();
        if (num2 == 5) {
            legal = false;
            return legal;
        } else if (num1 == 1) {
            legal = false;
            return legal;
        } else if (num1 != 0 && num2 != 0) {
            legal = num2 < num1;
            return legal;
        }else{
        return legal;
        }
    }
}
