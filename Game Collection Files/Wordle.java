import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Wordle {
    //Basic Game Variables
    static int guessesTaken = 0;
    static int winStreak = 0;
      static int totalPoints = 0;
      static int pointsThisGame = 0;
      static int guesses = 6;
    static int timerLength = 0;
      static double difficultyMultiplier = 1.00, timerMultiplier = 1.00;
      static Word wordVisual, wordAnswer;
      static Timer newTimer;
    static int letters = -1;
    static int timerOption = -1;
    static boolean timerValid = false;
    static boolean validLetters = false;
    static boolean failedWord = false;
    
    //Padding to make first guess prompt look nicer
      static String firstGuessPadding = " ";
    
    public static void welcome(BufferedReader reader) {
        System.out.println("***** Welcome to Wordle! *****");
        startGame(false, false, reader);
    }
    
    //Start New Game
    public static void startGame(boolean validLetters, boolean timerValid, BufferedReader userInGame) {
        //Variables within method
        //BufferedReader userInGame = new BufferedReader(new InputStreamReader(System.in));
        int loopNum = 0;
        boolean timerOn = false;
        
        //Other Setup Variables
        Word userGuess;
        guesses = 6;
        guessesTaken = 0;
        boolean correctWord = false;
        
        final int MIN_LETTERS = 5, MAX_LETTERS = 12;
        //Reset menu loop each game
        int menuOption = -1;
            
        //Prompt for timer option
        while (!timerValid) {
            System.out.println("Enable Timer Mode?: ");
            System.out.println("1) No timer");
            System.out.println("2) 90 Seconds to guess");
            System.out.println("3) 60 Seconds to guess");
            System.out.println("4) 30 Seconds to guess");
        
            try {
                timerOption = Integer.parseInt(userInGame.readLine());
                if (timerOption < 1 || timerOption > 4) {
                    System.out.print("Please select a valid option: ");
                }
                else timerValid = true;
            }
            catch (IOException e) {
                System.out.print("Please select a valid option: ");
            }
            catch (NumberFormatException e) {
                System.out.print("Please select a valid option: ");
            }
        }
        
        //Select timer option
        switch (timerOption) {
            case 1:
                timerOn = false;
                break;
            case 2:
                timerOn = true;
                timerLength = 90;
                break;
            case 3:
                timerOn = true;
                timerLength = 60;
                break;
            case 4:
                timerOn = true;
                timerLength = 30;
                break;
            default:
                System.out.println("Timer option invalid");
        }
        timerMultiplier = 1.00 + (timerOption - 1) * .1d;
        
        //Get letters from user
        while (!validLetters) {
            System.out.print("Enter the length of word to create (5-12 letters): ");
            try {
                letters = Integer.parseInt(userInGame.readLine());
                if (letters < MIN_LETTERS || letters > MAX_LETTERS) {
                    System.out.println("Invalid input!");
                }
                else { 
                    validLetters = true;
                }
            }
            catch (IOException e) {
                System.out.println("Invalid input!");
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid input!");
            }
        }  
        
        //New random x letter word
        if (letters == -1) {
            System.out.println("Unable to create word.");
            System.exit(-1);
        }
        wordAnswer = Word.newRandWord(letters);
        difficultyMultiplier = 1.00 + ((letters - 5) * .25d);
        
        //Word Displayed to User
        char[] charForVisual = new char[letters];
        for (int i = 0; i < letters; i++) {
            charForVisual[i] = '*';
        }
        wordVisual = new Word(charForVisual);
        
        //Create guess list -- Don't need to load all lists
        Word.createGuessList(letters);
        
        //Print current game settings
        String gameSettings = "\nGame Settings: The word length is " + letters;
        if (timerOn) {
            gameSettings += ", and the time per guess is " + timerLength + " seconds!";
        }
        else {
            gameSettings += ", and there is no limit on time per guess!";
        }
        System.out.println(gameSettings);
        
        //TEMP
        //System.out.println("The word is " + wordAnswer);
        
        //Game loop
        if (timerOn) {
            if (newTimer != null) newTimer.cancel();
            newTimer = resetTimer(userInGame);
        }
        
        while(guesses != 0 && !correctWord) {
            if (guesses == 6) {
                System.out.println("(If you get stuck, enter 0 to give up)");
                System.out.println("\nMake your first guess: " + wordVisual);
                System.out.print("\t\t      " + firstGuessPadding);
            }
            else {
                System.out.println("(If you get stuck, enter 0 to give up)");
                System.out.println("\nYou have " + guesses + " guesses left!");
                System.out.println("Make your next guess: " + wordVisual);
                System.out.print("\t\t      ");
            }
            
            try {
                String guessString = userInGame.readLine();
                if (guessString.equals("0")) {
                    guessesTaken++;
                    guesses--;
                    if (timerOn) {
                        newTimer.cancel();
                    }
                    fail(false, userInGame);
                    return;
                }
                userGuess = new Word(guessString);
                if (failedWord) {
                    fail(true, userInGame);
                    return;
                }
                boolean validGuess = validGuess(userGuess, wordAnswer);
                if (validGuess) {
                    if (timerOn) {
                        newTimer.cancel();
                        newTimer = resetTimer(userInGame);
                    }
                    guesses--;
                    correctWord = testGuess(userGuess, wordAnswer, wordVisual);
                }
            }
            catch (IOException e) {
                System.out.println("Invalid User Input!");
            }
        }
        
        //Check if user failed based on guesses
        if (timerOn) {
            newTimer.cancel();
        }
        if (correctWord) {
            win(userInGame);
            return;
        }
        else  {
            fail(false, userInGame);
            return;
        }
    }
    
    //Test for valid guess by user
    public static boolean validGuess(Word guess, Word answer) {
        //Test word length match
        if (guess.getWordStr().length() != answer.getWordStr().length()) {
            System.out.println("Incorrect word length, please try again!");
            return false;
        }
        
        //Test for illegal characters
        String legalChars = "qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";
        int count = 0;
        for (int i = 0; i < guess.getWordStr().length(); i++) {
            for (int j = 0; j < legalChars.length(); j++) {
                if (guess.getWordStr().charAt(i) == legalChars.charAt(j)) {
                    count++;
                }
            }
        }
        if (count < guess.getWordStr().length()) {
            System.out.println("Guess includes illegal characters, please try again!");
            return false;
        }
        
        //Test for illegal word
        for (int i = 0; i < Word.guessList.length; i++) {
            if (guess.getWordStr().equalsIgnoreCase(Word.guessList[i].getWordStr())) {
                return true;
            }
        }
        System.out.println("Word does not exist in library, try another word!");
        return false;
    }
    
    //Test for matches in the guess word and answer word
    public static boolean testGuess(Word guess, Word answer, Word visual) {
        guessesTaken += 1;
        if (guess.getWordStr().equalsIgnoreCase(answer.getWordStr())) {
            return true;
        }
        //If a letter in guess matches a letter in word, let user know
        else {
            int count = 0;
            Word tempAnswer = new Word(answer.getWordStr());
            
            //If the letter is in the right spot, show the user
            for (int i = 0; i < answer.getWordStr().length(); i++) {
                if (Character.toLowerCase(guess.getWordStr().charAt(i)) == Character.toLowerCase(tempAnswer.getWordStr().charAt(i))) {
                    //Build new word display
                    String newVisualStr = "";
                    char[] newVisualArr = new char[answer.getWordStr().length()];
                    for (int k = 0; k < answer.getWordStr().length(); k++) {
                        if (i == k) {
                            newVisualStr = newVisualStr + answer.getWordStr().charAt(k);
                            newVisualArr[k] = answer.getWordStr().charAt(k);
                        }
                        else {
                            if (visual.getWordStr().charAt(k) == '*') {
                                newVisualStr = newVisualStr + "*";
                                newVisualArr[k] = '*';
                            }
                            else {
                                newVisualStr = newVisualStr + visual.getWordStr().charAt(k);
                                newVisualArr[k] = visual.getWordArr()[k];
                            }
                        }
                    }
                    visual.setWordStr(newVisualStr);
                    visual.setWordArr(newVisualArr);
                }
            }
            for (int i = 0; i < answer.getWordStr().length(); i++) {
                for (int j = 0; j < answer.getWordStr().length(); j++) {
                    if (Character.toLowerCase(guess.getWordStr().charAt(i)) == Character.toLowerCase(tempAnswer.getWordStr().charAt(j))) {
                        System.out.println("The word contains " + answer.getWordStr().charAt(j));
                        count++;
                        
                        //Avoid double-counting letters
                        String newTempAnsStr = "";
                        char[] newTempAnsArr = new char[answer.getWordStr().length()];
                        for (int k = 0; k < answer.getWordStr().length(); k++) {
                            if (j == k) {
                                newTempAnsStr = newTempAnsStr + "*";
                                newTempAnsArr[k] = '*';
                            }
                            else {
                                if (tempAnswer.getWordStr().charAt(k) == '*') {
                                    newTempAnsStr = newTempAnsStr + "*";
                                    newTempAnsArr[k] = '*';
                                }
                                else {
                                    newTempAnsStr = newTempAnsStr + answer.getWordStr().charAt(k);
                                    newTempAnsArr[k] = answer.getWordArr()[k];
                                }
                            }
                        }
                        tempAnswer.setWordStr(newTempAnsStr);
                        tempAnswer.setWordArr(newTempAnsArr);
                        
                        //In case of double letters in word
                        break;
                    }
                }
            }
            if (count == 0) {
                System.out.println("No letters matching!");
            }
        }
        return false;
    }
    public static Timer resetTimer(BufferedReader userInGame) {
        Timer timer = new Timer();
        //Fail if ran out of time
        TimerTask triggerFail = new TimerTask() {
            @Override
            public void run() {
                System.out.println("Time is up! Press ENTER to continue...");
                failedWord = true;
            }
        };
        //10 seconds left
        TimerTask tenSeconds = new TimerTask() {
            @Override
            public void run() {
                System.out.print("You have 10 seconds left to guess!\n\t\t      ");
                if (guesses == 6) {
                    System.out.print(firstGuessPadding);
                }
            }
        };
        //5 seconds left
        TimerTask fiveSeconds = new TimerTask() {
            @Override
            public void run() {
                System.out.print("You have 5 seconds left to guess!\n\t\t      ");
                if (guesses == 6) {
                    System.out.print(firstGuessPadding);
                }
            }
        };
        timer.schedule(triggerFail, timerLength * 1000);
        timer.schedule(tenSeconds, (timerLength - 10) * 1000);
        timer.schedule(fiveSeconds, (timerLength - 5) * 1000);
        return timer;
    }
    public static void fail(boolean timeoutFail, BufferedReader userInLoop) {
        winStreak = 0;
        String failReason;
        if (timeoutFail) failReason = "in " + timerLength + " seconds";
        else failReason = "in " + guessesTaken + " guess(es)";
        pointsThisGame = (int)(20 * difficultyMultiplier * timerMultiplier);
        System.out.println("\nSorry! You failed to guess the word " + failReason);
        System.out.println("The word was " + wordAnswer);
        System.out.println("You earned " + pointsThisGame + " points!");
        totalPoints += pointsThisGame;
        System.out.println("You have " + totalPoints + " total points, and a winstreak of " + winStreak);
        menuLoop(userInLoop);
        return;
    }
    public static void win(BufferedReader userInLoop) {
        if (!failedWord) {
            failedWord = false;
            pointsThisGame = (int)(((1000 - 100 * (guessesTaken - 1)) * difficultyMultiplier * timerMultiplier));
            System.out.println("Congratulations! You win!");
            System.out.println("You guessed the word in " + guessesTaken + " guesses");
            System.out.println("You earned " + pointsThisGame + " points!");
            totalPoints += pointsThisGame;
            winStreak++;
            System.out.println("You have " + totalPoints + " total points, and a winstreak of " + winStreak);
            menuLoop(userInLoop);
        }
        menuLoop(userInLoop);
        return;
    }
    public static void menuLoop(BufferedReader userInLoop) {
        //BufferedReader userInLoop = new BufferedReader(new InputStreamReader(System.in));
        
        int menuOption = -1;
        boolean rePrompt = true;
        failedWord = false;
        pointsThisGame = 0;
        String whatToDo = "\nWhat would you like to do: ";
        while(menuOption <= 1 || menuOption > 7) {
            if (rePrompt) {
                rePrompt = false;
                System.out.println(whatToDo);
                System.out.println("1) Look up the last word");
                System.out.println("2) Replay game with the same settings");
                System.out.println("3) Change settings and play a new game");
                System.out.println("4) Play a new game with random word length, same timer");
                System.out.println("5) Play a new game with random timer, same word length");
                System.out.println("6) Play a new game with random word length and random timer");
                System.out.println("7) Quit game");
            }
            try {
                String input = userInLoop.readLine();
                menuOption = Integer.parseInt(input);
                if (input.length() > 1) {
                    System.out.println("Please enter a valid option AAAA");
                    menuOption = -1;
                }
                Random rand = new Random();
                switch (menuOption) {
                    case -1:
                        break;
                    case 1:
                        rePrompt = true;
                        whatToDo = "\nWhat would you like to do next: ";
                        wordAnswer.lookUpWord();
                        break;
                    case 2:
                        startGame(true, true, userInLoop);
                        break;
                    case 3:
                        startGame(false, false, userInLoop);
                        break;
                    case 4:
                        letters = rand.nextInt(8) + 5;
                        startGame(true, true, userInLoop);
                        break;
                    case 5:
                        timerOption = rand.nextInt(4) + 1;
                        startGame(true, true, userInLoop);
                        break;
                    case 6:
                        letters = rand.nextInt(8) + 5;
                        timerOption = rand.nextInt(4) + 1;
                        startGame(true, true, userInLoop);
                        break;
                    case 7:
                        quit();
                        return;
                    default:
                        System.out.println("Please enter a valid option BBBB");
                        menuOption = -1;
                }
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
                menuOption = -1;
            }
            catch (NumberFormatException e) {
                System.out.println("Please enter a valid option DDDDD");
                menuOption = -1;
            }
        }
    }
    public static void quit() {
        return;
    }
}