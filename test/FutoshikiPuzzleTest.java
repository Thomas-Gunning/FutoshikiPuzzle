
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thomas
 */
public class FutoshikiPuzzleTest {

    FutoshikiPuzzle fill;

    public FutoshikiPuzzleTest() {

        fill = new FutoshikiPuzzle(5);
    }

    @Test
    public void testFillPuzzle() {

        fill.fillPuzzle();
        assertNotNull(fill);
    }

    @Test
    public void testConstraintLessThanColumn() {

        fill.setSquare(2, 0, 5);
        fill.setColumnConstraints(2, 0, "v", new FutoshikiSquare(5, 2, 0), new FutoshikiSquare(0, 3, 0));
        assertEquals("v", fill.getColumnConstraint(2, 0).getConstraint());

    }

    @Test
    public void testConstraintGreaterThanColumn() {

        fill.setSquare(2, 0, 5);
        fill.setColumnConstraints(2, 0, "^", new FutoshikiSquare(5, 2, 0), new FutoshikiSquare(0, 3, 0));
        assertEquals("^", fill.getColumnConstraint(2, 0).getConstraint());
    }

    @Test
    public void testConstraintGreaterThanRow() {
        fill.setSquare(2, 0, 5);
        fill.setRowConstraints(2, 0, "<", new FutoshikiSquare(5, 2, 0), new FutoshikiSquare(0, 2, 1));
        assertEquals("<", fill.getRowConstraint(2, 0).getConstraint());
    }

    @Test
    public void testConstraintLessThanRow() {
        fill.setSquare(2, 0, 5);
        fill.setRowConstraints(2, 0, ">", new FutoshikiSquare(5, 2, 0), new FutoshikiSquare(0, 2, 1));
        assertEquals(">", fill.getRowConstraint(2, 0).getConstraint());
    }

    @Test
    public void testAddSquare() {

        fill.setSquare(2, 1, 2);
        assertEquals(2, fill.getSquare(2, 1).getSquareNumber());
    }

    @Test
    public void testToString() {
        fill.fillPuzzle();
        assertNotNull(fill.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSquareInvalidLowerBound() {
        fill.setSquare(0, 0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSquareInvalidUpperBound() {
        fill.setSquare(0, 0, 100);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectRowConstraint() {
        fill.setRowConstraints(2, 0, "A", new FutoshikiSquare(5, 2, 0), new FutoshikiSquare(0, 2, 1));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectColumnConstraint() {
        fill.setRowConstraints(2, 0, "A", new FutoshikiSquare(5, 2, 0), new FutoshikiSquare(0, 2, 1));

    }

    @Test
    public void testIsLegalNotValid() {

        fill.setSquare(0, 0, 1);
        fill.setSquare(0, 1, 1);
        assertFalse(fill.isLegal());

    }

    @Test
    public void testIsLegalValid() {
        fill.setSquare(0, 0, 1);
        fill.setSquare(0, 1, 2);
        assertTrue(fill.isLegal());
    }

    @Test
    public void testIsLegalNotValidConstraint() {
        fill.setSquare(0, 0, 1);
        fill.setSquare(0, 1, 2);
        fill.setColumnConstraints(0, 0, "v", new FutoshikiSquare(1, 0, 0), new FutoshikiSquare(2, 0, 1));
        assertFalse(fill.isLegal());
    }

    @Test
    public void testIsLegalValidConstraint() {
        fill.setSquare(0, 0, 1);
        fill.setSquare(0, 1, 2);
        fill.setColumnConstraints(0, 0, "^", new FutoshikiSquare(1, 0, 0), new FutoshikiSquare(2, 0, 1));
        assertTrue(fill.isLegal());
    }

    @Test
    public void testIsLegalNotValidRow() {
        fill.setSquare(0, 0, 1);
        fill.setSquare(0, 0, 2);
        fill.setRowConstraints(0, 0, ">", new FutoshikiSquare(1, 0, 0), new FutoshikiSquare(2, 0, 1));
        assertFalse(fill.isLegal());
    }

    @Test
    public void testIsLegalValidRow() {
        fill.setSquare(0, 0, 1);
        fill.setSquare(0, 0, 2);
        fill.setRowConstraints(0, 0, "<", new FutoshikiSquare(1, 0, 0), new FutoshikiSquare(2, 0, 1));
        assertTrue(fill.isLegal());
    }

    @Test
    public void testSetPencilMark() {
        fill.setSquare(2, 1, 2);
        fill.setPencilmark(2, 1, "4");
        assertEquals("4", fill.getSquare(2, 1).getPencilMark());
    }

    @Test
    public void testToStringGrid() {
        String grid = "  ---   \n"
                + " |   | \n"
                + "  ---   \n\n\n"
                + "  ---   \n";
        FutoshikiPuzzle gridString;
        gridString = new FutoshikiPuzzle(1);
        System.out.println(gridString.toString());
        assertEquals(gridString.toString(), grid);

    }

}
