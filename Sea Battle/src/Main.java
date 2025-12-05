import java.util.Scanner;
import java.util.Arrays;
import java.util.regex.*;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //ship sizes are: 1 of 5, 1 of 4, 2 of 3, and 1 of 2
        int[] ships = {5, 4, 3, 3, 2};
        //create game boards
        //this board shows where my ships are
        char[][] playerBoard = new char[10][10];
        //this board is secret because it has the computer's ships
        char[][] computerBoard = new char[10][10];
        //here I see where I have shot and if I hit
        char[][] playerShotsBoard = new char[10][10];
        //here the computer keeps track of where it has shot
        char[][] computerShotsBoard = new char[10][10];

        //fill the entire board with water (~)
        initializeBoard(playerBoard);
        initializeBoard(computerBoard);
        initializeBoard(playerShotsBoard);
        initializeBoard(computerShotsBoard);

        //here I call the function to place my ships manually
        placePlayerShips(playerBoard, playerShotsBoard, ships);
        //this function is where the computer places its ships automatically
        placeComputerShips(computerBoard, ships);

        //here is where I prepare the variables to control the game
        boolean gameActive = true;
        //now calculate how many ship cells I need to sink to win
        int totalCells = sumCells(ships);
        //keep track of my hits
        int playerHits = 0;
        //keep track of computer's hits
        int computerHits = 0;

        //now create a main loop to start the game, everything before were function calls
        while (gameActive) {
            clearScreen();
            //display my boards on screen
            displayBoard(playerBoard, playerShotsBoard);

            //now it's my turn to shoot at the computer's ships
            boolean playerHit = playerShot(playerBoard, playerShotsBoard, computerBoard);
            if (playerHit) {
                playerHits++;
            }

            //next, check if I won, meaning if I sank all the computer's ships
            if (playerHits >= totalCells) {
                System.out.println("WIN!!! YOU'RE AWESOME");
                System.out.println("You've hit all of the computer's ships");
                gameActive = false;
            } else {
                //if I didn't win, it's the computer's turn to shoot
                boolean computerHit = computerShot(computerBoard, computerShotsBoard, playerBoard);
                if (computerHit) {
                    computerHits++;
                }
                //now check if the computer has won, meaning it sank all my ships
                if (computerHits >= totalCells) {
                    System.out.println("TOO BAD :( THE COMPUTER WON)");
                    System.out.println("the computer sank all my ships");
                    gameActive = false;
                } else {
                    //this else creates a pause so I can see what happened and continue
                    System.out.println("press enter to continue...");
                    scanner.nextLine();
                }
            }
        }

        scanner.close();
    }

    //now create a function for me to shoot
    public static boolean playerShot(char[][] playerBoard, char[][] playerShotsBoard, char[][] computerBoard) {
        Scanner scanner = new Scanner(System.in);
        boolean validCoordinate = false;
        boolean hit = false;

        //this loop asks for the coordinates where I want to shoot
        while (!validCoordinate) {
            System.out.print("where do you want to shoot? 'example: B6': ");
            String input = scanner.nextLine().toUpperCase();

            //check that something was entered
            if (input.length() < 2) {
                System.out.println("you must enter a letter and a number");
            } else {
                char letter = input.charAt(0);
                //this takes the number part of the coordinate
                String numberPart = input.substring(1);

                //this conditional indicates that the letter must be from A to J, otherwise I won't be able to continue playing and will have to ask again
                //for the correct coordinate
                if (letter < 'A' || letter > 'J') {
                    System.out.println("that letter is not valid, it must be from A to J");
                } else {
                    //now when I've taken the number from the coordinate above, I say if this number is between 0 and 9, otherwise in the else that encompasses it
                    //everything will ask you not to make things up and to ask for the coordinate correctly again
                    int column;
                    if (numberPart.matches("[0-9]")) {
                        column = Integer.parseInt(numberPart);
                        int row = letter - 'A';
                        //now check if I already shot there before, if so, I'll press enter to continue
                        if (playerShotsBoard[row][column] == 'X' || playerShotsBoard[row][column] == '-') {
                            System.out.println("you already shot here, choose another spot");
                        } else {
                            validCoordinate = true;
                            //what I created here is to check if I hit a computer ship
                            if (computerBoard[row][column] == 'B') {
                                //if I hit, mark it as an X
                                playerShotsBoard[row][column] = 'X';
                                computerBoard[row][column] = 'X';
                                System.out.println("you hit! good job");
                                hit = true;
                            } else {
                                //if I miss, mark it as a -
                                playerShotsBoard[row][column] = '-';
                                computerBoard[row][column] = '-';
                                System.out.println("you missed, you hit water");
                                hit = false;
                            }
                        }
                    } else {
                        System.out.println("you must enter a number from 0 to 9 for the coordinates");
                    }
                }
            }
        }
        return hit;
    }

    //now create this function, this function makes the computer shoot at me
    public static boolean computerShot(char[][] computerBoard, char[][] computerShotsBoard, char[][] playerBoard) {
        int row, column;
        boolean validCoordinate = false;
        boolean hit = false;

        //in this loop, I make the computer randomly choose a coordinate to shoot at me
        while (!validCoordinate) {
            row = (int)(Math.random() * 10);
            column = (int)(Math.random() * 10);

            //here I make the computer check that it hasn't shot there before
            if (computerShotsBoard[row][column] != 'X' && computerShotsBoard[row][column] != '-') {
                validCoordinate = true;

                //here I check if the computer hit any of my ships, if so, it will be marked with an X
                if (playerBoard[row][column] == 'B') {
                    computerShotsBoard[row][column] = 'X';
                    playerBoard[row][column] = 'X';
                    System.out.println("noooooo, it hit me");
                    hit = true;
                    //otherwise with a dash, since it hit water
                } else {
                    computerShotsBoard[row][column] = '-';
                    playerBoard[row][column] = '-';
                    System.out.println("the computer hit water");
                    hit = false;
                }
            }
        }
        return hit;
    }

    /*this method fills the entire board with water
    A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
    J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
      0 1 2 3 4 5 6 7 8 9 */
    public static void initializeBoard(char[][] board) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = '~';
            }
        }
    }

    //this displays the board on the screen
    public static void displayBoard(char[][] board, char[][] shotsBoard) {
        //this is my board with my ships
        System.out.println("###################");
        System.out.println("--- my board ---");
        System.out.println("###################");
        //what I do in this nested loop is first show the letters (rows) and rows (columns)
        for (int i = 0; i < 10; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }

        //to show the numbers at the bottom and not at the top, I had to create this for loop because it gave me an error and put them at the top
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();

        //this is the board where I see where I shot at the computer
        System.out.println("###################");
        System.out.println("--- PC board ---");
        System.out.println("###################");
        //when creating the board, it's the same as the player's
        for (int i = 0; i < 10; i++) {
            System.out.print((char)('A' + i) + " ");
            for (int j = 0; j < 10; j++) {
                System.out.print(shotsBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for (int i = 0; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
        System.out.println();
    }

    //what I do here is clear the screen just to make the game look nicer visually (this method clears the screen)
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                Thread.sleep(100);
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
                Thread.sleep(100);
            }
        } catch (Exception e) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            for (int i = 0; i < 100; i++) {
                System.out.println();
            }
        }
    }

    //this method sums how many cells all ships occupy
    public static int sumCells(int[] array) {
        int sum = 0;
        for (int value : array) {
            sum += value;
        }
        return sum;
    }

    //in this function, I place my ships
    public static void placePlayerShips(char[][] board, char[][] shotsBoard, int[] ships) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("now place your ships: ");
        //here I show the empty board to see where to place the ships
        displayBoard(board, shotsBoard);

        //with this for loop, I go through the list of ships I need to place
        for (int i = 0; i < ships.length; i++) {
            //here I get the size of the ship I placed
            int length = ships[i];
            System.out.println("place a ship " + length + " cells long");

            boolean placed = false;
            //with this loop, I repeat until a valid position is found for the ship
            while (!placed) {
                //ask for the coordinates where I want to place the ship
                System.out.print("where do you want to place the ship? 'ex: G7': ");
                String coordinate = scanner.nextLine().toUpperCase();

                //check that the coordinates are entered correctly, meaning the letter and number
                if (coordinate.length() < 2) {
                    System.out.println("you must enter a letter and a number");
                } else {
                    //here I get the letter
                    char letter = coordinate.charAt(0);
                    //here the number
                    String numberPart = coordinate.substring(1);

                    //now verify that the letter is valid
                    if (letter < 'A' || letter > 'J') {
                        System.out.println("The letter must be from A to J");
                    } else {
                        int column;
                        //now verify if the number is valid
                        if (numberPart.matches("[0-9]")) {
                            //here convert the string to a number
                            column = Integer.parseInt(numberPart);
                            //here convert the letter to a number
                            int row = letter - 'A';

                            //ask if they want it horizontal or vertical
                            System.out.print("how do I want it? 0 horizontal || 1 vertical: ");
                            String orientationInput = scanner.nextLine();
                            int orientation;

                            //here I place the ship horizontally (right)
                            if (orientationInput.equals("0")) {
                                orientation = 0;
                                //here I place the ship vertically (down)
                            } else if (orientationInput.equals("1")) {
                                orientation = 1;
                                //create this else in case I make a mistake entering 1 and 0
                            } else {
                                System.out.println("you must enter 0 or 1");
                                orientation = -1;
                            }

                            //here I only try to place the ship if the orientation is valid
                            if (orientation == 0 || orientation == 1) {
                                //here I try to place the ship in the chosen position
                                placed = placeShip(board, length, row, column, orientation, true);

                                //check if the ship could be placed
                                if (placed) {
                                    System.out.println("the ship has been placed correctly");
                                    //show the updated board with the new ship
                                    displayBoard(board, shotsBoard);
                                } else {
                                    System.out.println("cannot place the ship there");
                                }
                            }
                        } else {
                            System.out.println("I must enter a number from 0 to 9");
                        }
                    }
                }
            }
        }
    }

    //this method places the computer's ships
    public static void placeComputerShips(char[][] board, int[] ships) {
        for (int length : ships) {
            boolean placed = false;
            //here the computer places its ships
            while (!placed) {
                int row = (int)(Math.random() * 10);
                int column = (int)(Math.random() * 10);
                int orientation = (int)(Math.random() * 2);
                placed = placeShip(board, length, row, column, orientation, false);
            }
        }
        System.out.println("the computer already has its ships placed");
    }

    //this method verifies if when placing a ship it would collide with an existing ship
    //or if it would go out of the board limits
    public static boolean hasCollision(char[][] board, int shipLength, int row, int column, int orientation) {
        if (orientation == 0) {
            //if the orientation is to the right
            for (int j = column; j < column + shipLength; j++) {
                //go through all cells where the ship would go horizontally
                //start at the chosen column and go to column plus ship length
                if (j >= 10 || board[row][j] != '~') {
                    return true;
                }
            }
            //if the orientation is downward
        } else {
            //go through all cells where the ship would go vertically
            //start at the chosen row and go to row plus ship length
            for (int i = row; i < row + shipLength; i++) {
                if (i >= 10 || board[i][column] != '~') {
                    return true; // with this true, what I do is if there is a collision, I cannot place the ship here, same as in the return true above
                }
            }
        }
        //this one, in contrast, does that if there is no collision, the ship can be placed here
        return false;
    }

    //this method places a ship on the board if possible
    public static boolean placeShip(char[][] board, int shipLength, int row, int column, int orientation, boolean player) {
        //first check if it fits on the board
        if (orientation == 0) {
            if (column + shipLength > 10) {
                return false;
            }
        } else {
            if (row + shipLength > 10) {
                return false;
            }
        }

        //then check that it doesn't collide with other ships
        if (hasCollision(board, shipLength, row, column, orientation)) {
            return false;
        }

        //if everything is ok, place the ship
        if (orientation == 0) {
            for (int j = column; j < column + shipLength; j++) {
                board[row][j] = 'B';
            }
        } else {
            for (int i = row; i < row + shipLength; i++) {
                board[i][column] = 'B';
            }
        }

        return true;
    }
}