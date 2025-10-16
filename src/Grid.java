import javax.xml.stream.Location;

public class Grid {
    {
        Location[][] grid;
        int points;

        // Constants for number of rows and columns.
        public static final int NUM_ROWS = 10;
        public static final int NUM_COLS = 10;

    public Grid()
        {
            if (NUM_ROWS > 26)
            {
                throw new IllegalArgumentException("ERROR! NUM_ROWS CANNOT BE > 26");
            }

            grid = new Location[NUM_ROWS][NUM_COLS];

            for (int row = 0; row < grid.length; row++)
            {
                for (int col = 0; col < grid[row].length; col++)
                {
                    Location tempLoc = new Location();
                    grid[row][col] = tempLoc;
                }
            }
        }

