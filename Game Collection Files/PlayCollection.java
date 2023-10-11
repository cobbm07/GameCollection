import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

public class PlayCollection {
    public static void main(String[] args) {
        boolean keepPlaying = true;
        System.out.println("Welcome to the Game Collection!\n");
        while (keepPlaying) {
            System.out.println("What would you like to play:");
            System.out.println("1) Wordle");
            System.out.println("2) Sudoku");
            System.out.println("3) Memory Cards");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            
            boolean validGameChoice = false;
            while (!validGameChoice) {
                try {
                    String answer = reader.readLine();
                    int ansInt = Integer.parseInt(answer);
                    if (ansInt < 1 || ansInt > 3) {
                        System.out.println("Invalid input! Please select a valid option!");
                    }
                    else {
                        switch (ansInt) {
                            case 1:
                                validGameChoice = true;
                                Wordle.welcome(reader);
                                break;
                            case 2:
                                validGameChoice = true;
                                Sudoku.startGame();
                                break;
                            case 3:
                                validGameChoice = true;
                                MemoryGame.startGame();
                                break;
                            default:
                                System.out.println("Invalid Option!");
                        }
                    }
                }
                catch (IOException e) {
                    System.out.println("Invalid input! Please select a valid option!");
                }
                catch (NumberFormatException e) {
                    System.out.println("Invalid input! Please select a valid option!");
                }
            }
            System.out.println("\nWould you like to play another game? (Y/N)");
            boolean valid = false;
            while (!valid) {
                try {
                    String userChoice = reader.readLine();
                    if (!userChoice.equalsIgnoreCase("n") && !userChoice.equalsIgnoreCase("y")) {
                        System.out.println("Please enter a valid option!");
                    }
                    else if (userChoice.equalsIgnoreCase("n")) {
                        System.out.println("Okay, goodbye!");
                        reader.close();
                        System.exit(0);
                    }
                    else if (userChoice.equalsIgnoreCase("y")){
                        valid = true;
                        keepPlaying = true;
                    }
                }
                catch (IOException e) {
                    System.out.println(e.getMessage());
                    System.out.println("Please enter a valid option!");
                }
            }
        }
    }
}