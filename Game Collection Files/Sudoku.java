import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Sudoku {
    public static void startGame() {
        boolean keepPlaying = true;
        while (keepPlaying) {
            SudokuBoard sudokuBoard = new SudokuBoard();
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            boolean solved = false;
            while(!solved) {
                System.out.println("Welcome to Sudoku!");
                System.out.println("\nHow to play:");
                System.out.println("1: Enter the location of the number you would like to change (EX: a1, e3, d6)");
                System.out.println("2: Enter the number you would like to change it to (EX: 4, 8, 3)\n");
                System.out.println("(At any point, type \"0\" to exit!)");
                System.out.println("(At any point, type \"ORG\" to view the original board!)\n");
                sudokuBoard.printBoard();
                
                try
                {
                    boolean valid = false;
                    while (!valid) {
                        boolean validLocation = false;
                        String guessLocation = "";
                        int location[] = {-1,-1};
                        while (!validLocation) {
                            System.out.print("\nEnter a location: ");
                            guessLocation = reader.readLine().trim();
                            if (guessLocation.equalsIgnoreCase("0")) {
                                System.out.println("Okay, goodbye!");
                                return;
                            }
                            else if (guessLocation.equalsIgnoreCase("ORG")) {
                                sudokuBoard.printBaseBoard();
                            }
                            else {
                                location = validLocation(guessLocation, sudokuBoard);
                                if (location[0] == -1 && location[1] == -1) {
                                    System.out.println("Invalid location! Please type a valid location (EX: a1, e3, d6)");
                                }
                                else validLocation = true;
                            }
                        }
                        
                        while (!valid) {
                            System.out.print("Enter the number to change to: ");
                            String value = reader.readLine().trim();
                            if (guessLocation.equalsIgnoreCase("0")) {
                                System.out.println("Okay, goodbye!");
                                return;
                            }
                            else if (guessLocation.equalsIgnoreCase("ORG")) {
                                sudokuBoard.printBaseBoard();
                            }
                            else if (validGuess(value, guessLocation, sudokuBoard)) {
                                valid = true;
                                int num = Integer.parseInt(value);
                                sudokuBoard.setValue(location[0], location[1], num);
                            }
                        }
                    }
                    clearConsole(); //ONLY WORKS IN BLUEJ
                    int correctNums = 0;
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            if (sudokuBoard.getSolution()[i][j] == sudokuBoard.getBoard()[i][j]) {
                                correctNums++;
                            }
                        }
                    }
                    if (correctNums == 81) {
                        solved = true;
                    }
                }
                catch (IOException e)
                {
                    System.out.println("Invalid format! Please type a location (EX: a1, e3, d6)");
                }
            }
            System.out.println("You win!\n");
            sudokuBoard.printBoard();
            System.out.print("\nWould you like to play again (Y/N): ");
            try
            {
                boolean valid = false;
                while (!valid) {
                    String userChoice = reader.readLine();
                    if (!userChoice.equalsIgnoreCase("n") && !userChoice.equalsIgnoreCase("y")) {
                        System.out.println("Please enter a valid option!");
                    }
                    else if (userChoice.equalsIgnoreCase("n")) {
                        System.out.println("Okay, goodbye!");
                        return;
                    }
                    else {
                        keepPlaying = true;
                    }
                }
            }
            catch (IOException e)
            {
                System.out.println("Please enter a valid option!");
            }
        }
    }
    
    public static int[] validLocation (String guessLocation, SudokuBoard sudokuBoard) {
        if (guessLocation.length() != 2) {
            return new int[]{-1,-1};
        }
        else {
            String validChars = "abcdefghi";
            boolean validChar = false;
            int charIndex = 0;
            for (int i = 0; i < 9; i++) {
                if (Character.toLowerCase(guessLocation.charAt(0)) == validChars.charAt(i)) {
                    validChar = true;
                    charIndex = i;
                    break;
                }
            }
            if (!validChar) {
                return new int[]{-1,-1};
            }
            else {
                String validNums = "123456789";
                boolean validNum = false;
                int numIndex = 0;
                for (int i = 0; i < 9; i++) {
                    if (guessLocation.charAt(1) == validNums.charAt(i)) {
                        validNum = true;
                        numIndex = i;
                        break;
                    }
                }
                if (!validNum) {
                    return new int[]{-1,-1};
                }
                else {
                    if (sudokuBoard.getBaseBoard()[numIndex][charIndex] != ' ') {
                        System.out.println("That value cannot be changed!");
                        return new int[]{-1,-1};
                    }
                    else return new int[]{numIndex, charIndex};
                }
            }
        }
    }
    
    //ONLY WORKS IN BLUEJ
    public static void clearConsole() {
        for(int i = 0; i < 10; i++) {
            System.out.println("\n");
        }
    }
    public static boolean validGuess(String value, String guessLocation, SudokuBoard sudokuBoard) {
        if (value.length() != 1) {
            return false;
        }
        else {
            String numLoc = "0123456789";
            int guessCharLoc = Character.getNumericValue(Character.toUpperCase(guessLocation.charAt(0)) - 16) - 1;
            int guessNumLoc = Character.getNumericValue(guessLocation.charAt(1)) - 1;
            //Check row
            for (int i = 0; i < 9; i++) {
                if (i != guessCharLoc) {
                    if (sudokuBoard.getValue(guessNumLoc, i) == value.charAt(0)) {
                        System.out.println("Invalid number | Number already exists on that row");
                        return false;
                    }
                }
            }
            System.out.println("Check complete");
            String charLoc = "abcdefghi";
            //Check Column
            for (int i = 0; i < 9; i++) {
                if (i != guessNumLoc) {
                    if (sudokuBoard.getValue(i, guessCharLoc) == value.charAt(0)) {
                        System.out.println("Invalid number | Number already exists on that column");
                        return false;
                    }
                }
            }
            //Check square
            char valueChar = value.charAt(0);
            String validNums = "0123456789";
            for (int i = 0; i < 10; i++) {
                if (valueChar == validNums.charAt(i)) {
                    return true;
                }
            }
        }
        System.out.println("Invalid value! Please type a number 0-9!");
        return false;
    }
}