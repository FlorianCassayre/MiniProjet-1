package main;

/**
 * Represent a cursor moving in a spiral fashion through a two dimensional array
 */
public class SpiralCursor
{
    private static final int DIRECTIONS = 4;

    private static final int RIGHT = 0;
    private static final int DOWN = 1;
    private static final int LEFT = 2;
    private static final int UP = 3;

    private int col = 0, row = 0; // Top left corner
    private int direction = RIGHT; // Starts by moving to the right side
    private int minRow, maxRow, minCol, maxCol;

    public SpiralCursor(int rows, int cols)
    {
        minRow = 0;
        maxRow = rows - 1;
        minCol = 0;
        maxCol = cols - 1;
    }

    /**
     * Moves the cursor one step further through the spiral
     */
    public void step()
    {
        if(col == maxCol && direction == RIGHT)
            minRow++;
        else if(row == maxRow && direction == DOWN)
            maxCol--;
        else if(col == minCol && direction == LEFT)
            maxRow--;
        else if(row == minRow && direction == UP)
            minCol++;
        else
            direction--; // Decrementing (will be incremented again afterwards to restore the state back)
        direction = (direction + 1) % DIRECTIONS; // Incrementing and modulo 4

        col += projectionX(direction);
        row += projectionY(direction);
    }

    /**
     * Gets the current col
     * @return the current col
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Gets the current row
     * @return the current row
     */
    public int getRow()
    {
        return row;
    }

    /**
     * Projects the direction on the X-axis
     * @param direction a direction between 0 and 4 (excluded)
     * @return the x projection
     */
    private static int projectionX(int direction)
    {
        direction = getDirectionModulo(direction); // Positive modulo 4
        if(direction == RIGHT)
            return 1;
        if(direction == LEFT)
            return -1;
        return 0;
    }

    /**
     * Projects the direction on the Y-axis
     * @param direction a direction between 0 and 4 (excluded)
     * @return the y projection
     */
    private static int projectionY(int direction)
    {
        direction = getDirectionModulo(direction); // Positive modulo 4
        if(direction == DOWN)
            return 1;
        if(direction == UP)
            return -1;
        return 0;
    }

    /**
     * Return the positive modulo of this direction
     * @param direction a direction
     * @return a direction between 0 and 4 (excluded)
     */
    private static int getDirectionModulo(int direction)
    {
        return (direction + DIRECTIONS) % DIRECTIONS;
    }
}
