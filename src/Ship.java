public class Ship {
    public Ship(int shipLength) {
            /* Instance Variables */
            private int row;
            private int col;
            private int length;
            private int direction;

            // Direction Constants
            public static final int UNSET = -1;
            public static final int HORIZONTAL = 0;
            public static final int VERTICAL = 1;

            // Constructor
            public Ship(int length)
            {
                this.length = length;
                this.row = -1;
                this.col = -1;
                this.direction = UNSET;
            }

            // Has the location been init
            public boolean isLocationSet()
            {
                if (row == -1 || col == -1)
                    return false;
                else
                    return true;
            }

            // Has the direction been init
            public boolean isDirectionSet()
            {
                if (direction == UNSET)
                    return false;
                else
                    return true;
            }
    }

    public boolean isLocationSet() {
    }

    public boolean isDirectionSet() {
    }

    public void setLocation(int row, int col) {
    }

    public void setDirection(int direction) {
    }
}
