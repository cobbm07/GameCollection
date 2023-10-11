import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class MemoryGame {
    public static void startGame() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MemoryGameBoard board = new MemoryGameBoard();
        int matches = 0;
        int failCount = 0;
        int matchCount = 0;
        int specialCount = 0;
        boolean lastSpecial = false;
        while (matches != 6) {
            board.printBoard();
            boolean validX = false;
            while (!validX) {
                String location = "";
                System.out.print("Select your first card (ex: a2, c3) or type 0 to exit: ");
                try {
                    location = reader.readLine();
                    while (location.isEmpty() || location == null || location.length() != 2) {
                        if (location != null && !location.isEmpty() && location.length() == 1 && location.charAt(0) == '0') {
                            board.printSolution();
                            return;
                        }
                        System.out.println("Please enter a valid location");
                        location = reader.readLine();
                    }
                    int guessCharLoc1 = Character.getNumericValue(Character.toUpperCase(location.charAt(0)) - 16) - 1;
                    int guessNumLoc1 = Character.getNumericValue(location.charAt(1)) - 1;
                    
                    boolean valid = validGuess(board, location, guessCharLoc1, guessNumLoc1);
                    if (valid) {
                        validX = true;
                        board.setValue(guessCharLoc1, guessNumLoc1, board.getValue(guessCharLoc1, guessNumLoc1));
                        char valX = board.getValue(guessCharLoc1, guessNumLoc1);
                        
                        board.printBoard();
                        boolean validY = false;
                        while (!validY) {
                            System.out.print("Select your second card (ex: a2, c3) or type 0 to exit: ");
                            try {
                                location = reader.readLine();
                                
                                while (location.isEmpty() || location == null || location.length() != 2) {
                                    if (location != null && !location.isEmpty() && location.length() == 1 && location.charAt(0) == '0') {
                                        board.printSolution();
                                        return;
                                    }
                                    System.out.println("Please enter a valid location");
                                    location = reader.readLine();
                                }
                                int guessCharLoc2 = Character.getNumericValue(Character.toUpperCase(location.charAt(0)) - 16) - 1;
                                int guessNumLoc2 = Character.getNumericValue(location.charAt(1)) - 1;
                                
                                valid = validGuess(board, location, guessCharLoc2, guessNumLoc2);
                                if (valid) {
                                    validY= true;
                                    board.setValue(guessCharLoc2, guessNumLoc2, board.getValue(guessCharLoc2, guessNumLoc2));
                                    char valY = board.getValue(guessCharLoc2, guessNumLoc2);
                                    board.printBoard();
                                    if ((valX == valY) && (Character.getNumericValue(valX) >= 0 && Character.getNumericValue(valX) <= 9)) {
                                        System.out.println("Special cards match!");
                                        SpecialCard.matchSound();
                                        matches++;
                                        specialCount++;
                                        lastSpecial = true;
                                    }
                                    else if (valX == valY) {
                                        System.out.println("Cards match!");
                                        Card.matchSound();
                                        matches++;
                                        matchCount++;
                                        lastSpecial = false;
                                    }
                                    else {
                                        System.out.println("Cards do not match!");
                                        Card.failSound();
                                        failCount++;
                                        
                                        board.setValue(guessCharLoc1, guessNumLoc1, ' ');
                                        board.setValue(guessCharLoc2, guessNumLoc2, ' ');
                                        lastSpecial = false;
                                    }
                                    try {
                                        TimeUnit.SECONDS.sleep(2);
                                    }
                                    catch (InterruptedException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    clearScreen();
                                }
                                else {
                                    System.out.println("Please enter a valid location");
                                }
                            }
                            catch (IOException e) {
                                System.out.println("Error reading input");
                            }
                        }
                    }
                    else {
                        System.out.println("Please enter a valid location");
                    }
                }
                catch (IOException e) {
                    System.out.println("Error reading input");
                }
            }
        }
        if (lastSpecial) {
            SpecialCard.winSound();
        }
        else {
            Card.winSound();
        }
        board.printBoard();
        System.out.println("You win!");
        System.out.println("This game took you " + (failCount + matches) + " guesses!");
        System.out.println("Play again? (Y/N):");
        boolean yn = false;
        while (!yn) {
            try
            {
                String yesOrNo = reader.readLine();
                if (Character.toUpperCase(yesOrNo.charAt(0)) == 'Y') {
                    yn = true;
                    startGame();
                }
                else if (Character.toUpperCase(yesOrNo.charAt(0)) == 'N') {
                    yn = true;
                    return;
                }
                else {
                    System.out.println("Invalid response!");
                }
            }
            catch (IOException e)
            {
                System.out.println("Invalid response!");
            }
        }
        return;
    }
    
    public static boolean validGuess(MemoryGameBoard board, String location, int guessCharLoc, int guessNumLoc) {
        
        if (location.length() != 2) {
            return false;
        }
        if (guessCharLoc < 0 || guessCharLoc > 3) {
            return false;
        }
        if (guessNumLoc < 0 || guessNumLoc > 2) {
            return false;
        }
        if (board.getCurrValue(guessCharLoc, guessNumLoc) != ' ') {
            return false;
        }
        return true;
    }
    public static void clearScreen() {
        for (int i = 0; i < 20; i++) {
            System.out.println("\n");
        }
    }
}