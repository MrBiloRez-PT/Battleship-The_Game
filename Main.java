import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("====================================");
        System.out.println("   WELCOME TO BATTLESHIP GAME!");
        System.out.println("====================================\n");

        // Create two players
        Player player1 = new Player();
        Player player2 = new Player();

        // Setup ships for Player 1
        System.out.println("PLAYER 1 - Ship Placement");
        System.out.println("--------------------------------------");
        setupPlayerShips(player1, "Player 1");

        // Clear console (simulated)
        clearScreen();

        // Setup ships for Player 2
        System.out.println("\nPLAYER 2 - Ship Placement");
        System.out.println("--------------------------------------");
        setupPlayerShips(player2, "Player 2");

        clearScreen();

        // Main game loop
        boolean gameOver = false;
        int currentPlayer = 1;

        System.out.println("\n====================================");
        System.out.println("         GAME START!");
        System.out.println("====================================\n");

        while (!gameOver) {
            Player attacker = (currentPlayer == 1) ? player1 : player2;
            Player defender = (currentPlayer == 1) ? player2 : player1;
            String attackerName = (currentPlayer == 1) ? "Player 1" : "Player 2";

            System.out.println("\n--- " + attackerName + "'s Turn ---");

            // Show attack board
            System.out.println("\nYour attack board:");
            attacker.oppGrid.printStatus();

            System.out.println("\nYour defense board:");
            attacker.playerGrid.printCombined();

            // Get attack coordinates
            int[] coords = getAttackCoordinates(attacker);
            int row = coords[0];
            int col = coords[1];

            // Process attack
            boolean hit = processAttack(attacker, defender, row, col);

            if (hit) {
                System.out.println("\n*** HIT! ***");
            } else {
                System.out.println("\n*** MISS! ***");
            }

            // Check if game ended
            if (defender.playerGrid.hasLost()) {
                gameOver = true;
                System.out.println("\n====================================");
                System.out.println("  " + attackerName + " WON THE GAME!");
                System.out.println("====================================");

                System.out.println("\nWinner's final board:");
                attacker.playerGrid.printShips();

                System.out.println("\nLoser's final board:");
                defender.playerGrid.printCombined();
            } else {
                // Switch player
                currentPlayer = (currentPlayer == 1) ? 2 : 1;

                System.out.println("\nPress ENTER for next player...");
                scanner.nextLine();
                clearScreen();
            }
        }

        scanner.close();
    }


     // Setup ships for a human player
    private static void setupPlayerShips(Player player, String playerName) {
        String[] shipNames = {"Destroyer (2)", "Submarine (3)", "Cruiser (3)",
                "Battleship (4)", "Carrier (5)"};

        for (int i = 0; i < player.ships.length; i++) {
            System.out.println("\nPlacing: " + shipNames[i]);
            System.out.println("Current board:");
            player.playerGrid.printShips();

            boolean validPlacement = false;

            while (!validPlacement) {
                try {
                    System.out.print("Enter row (A-J): ");
                    String rowInput = scanner.nextLine().trim().toUpperCase();

                    if (rowInput.length() != 1) {
                        System.out.println("Invalid input! Use a letter from A to J.");
                        continue;
                    }

                    int row = rowInput.charAt(0) - 'A';

                    System.out.print("Enter column (1-10): ");
                    int col = Integer.parseInt(scanner.nextLine().trim()) - 1;

                    System.out.print("Enter direction (0=horizontal, 1=vertical): ");
                    int direction = Integer.parseInt(scanner.nextLine().trim());

                    // Validate placement
                    if (isValidPlacement(player.playerGrid, player.ships[i], row, col, direction)) {
                        player.chooseShipLocation(player.ships[i], row, col, direction);
                        validPlacement = true;
                        System.out.println("Ship placed successfully!");
                    } else {
                        System.out.println("Invalid placement! Try again.");
                    }

                } catch (Exception e) {
                    System.out.println("Invalid input! Try again.");
                }
            }
        }

        System.out.println("\nAll ships for " + playerName + " placed!");
        player.playerGrid.printShips();
    }


     //Validates if a ship can be placed at the specified position
    private static boolean isValidPlacement(Grid grid, Ship ship, int row, int col, int direction) {
        int length = ship.getLength();

        // Check board limits
        if (direction == 0) { // Horizontal
            if (col + length > grid.numCols()) return false;

            for (int i = col; i < col + length; i++) {
                if (grid.hasShip(row, i)) return false;
            }
        } else { // Vertical
            if (row + length > grid.numRows()) return false;

            for (int i = row; i < row + length; i++) {
                if (grid.hasShip(i, col)) return false;
            }
        }

        return true;
    }


     //Gets attack coordinates from the player
    private static int[] getAttackCoordinates(Player attacker) {
        int row = -1;
        int col = -1;
        boolean valid = false;

        while (!valid) {
            try {
                System.out.print("\nEnter row to attack (A-J): ");
                String rowInput = scanner.nextLine().trim().toUpperCase();

                if (rowInput.length() != 1) {
                    System.out.println("Invalid input!");
                    continue;
                }

                row = rowInput.charAt(0) - 'A';

                System.out.print("Enter column to attack (1-10): ");
                col = Integer.parseInt(scanner.nextLine().trim()) - 1;

                // Validate coordinates
                if (row < 0 || row >= 10 || col < 0 || col >= 10) {
                    System.out.println("Coordinates out of bounds!");
                    continue;
                }

                // Check if already attacked
                if (attacker.oppGrid.alreadyGuessed(row, col)) {
                    System.out.println("You already attacked this position! Try another.");
                    continue;
                }

                valid = true;

            } catch (Exception e) {
                System.out.println("Invalid input! Try again.");
            }
        }

        return new int[]{row, col};
    }

    /**
     * Processes an attack and returns whether it was a hit or not
     */
    private static boolean processAttack(Player attacker, Player defender, int row, int col) {
        boolean hit = defender.playerGrid.hasShip(row, col);

        if (hit) {
            attacker.oppGrid.markHit(row, col);
            defender.playerGrid.markHit(row, col);
        } else {
            attacker.oppGrid.markMiss(row, col);
            defender.playerGrid.markMiss(row, col);
        }

        return hit;
    }

    /**
     * Simulates screen clearing
     */
    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}