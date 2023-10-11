import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Set;
import java.util.HashSet;

public class Word {
    //Word can be represented as String or Char[]
    
    private String wordStr;
    private char[] wordArr;
    private static Word[] wordList;
    public static Word[] guessList;
    
    private static Set<Word> wordListSet;
    public static Set<Word> guessListSet;
    
    //New Word given char[] or String
    public Word(char[] wordArr) {
        this.setWordArr(wordArr);
        this.setWordStr(String.valueOf(wordArr));
    }
    public Word(String wordStr) {
        this.setWordStr(wordStr);
        char[] wordArr = new char[wordStr.length()];
        for (int i = 0; i < wordStr.length(); i++) {
            wordArr[i] = wordStr.charAt(i);
        }
        this.setWordArr(wordArr);
    }
    
    //Create list of possible guesses
    public static void createGuessList(int letters) {
        try {
            String filePath = "assets/wordle/allowedGuesses" + letters + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            
            /*
            int listSize = Integer.parseInt(reader.readLine());
            guessList = new Word[listSize];
            String line = reader.readLine();
            for (int i = 0; i < listSize; i++) {
                guessList[i] = new Word(line);
                line = reader.readLine();
            }*/
            
            guessListSet = new HashSet<>();
            String line = reader.readLine();
            while (line != null && !line.isEmpty()) {
                guessListSet.add(new Word(line));
                line = reader.readLine();
            }
            guessList = guessListSet.toArray(new Word[guessListSet.size()]);
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("Specified file could not be found!");
        }
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
    }
    
    //Generate Random Word given Letters
    public static Word newRandWord(int letters) {
        try {
            //Get list of words
            String filePath = "assets/wordle/wordlist" + letters + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            
            /*
            int listSize = Integer.parseInt(reader.readLine());
            wordList = new Word[listSize];
            
            String line = reader.readLine();
            for (int i = 0; i < listSize; i++) {
                wordList[i] = new Word(line);
                line = reader.readLine();
            }
            */
            wordListSet = new HashSet<>();
            String line = reader.readLine();
            while(line != null && !line.isEmpty()) {
                wordListSet.add(new Word(line));
                line = reader.readLine();
            }
            
            Random random = new Random();
            int randIndex = random.nextInt(wordListSet.size());
            wordList = wordListSet.toArray(new Word[wordListSet.size()]);
            Word randWord = wordList[randIndex];
            //Generate Random Word
            
            /*
            Random random = new Random();
            int randIndex = random.nextInt(listSize);
            Word randWord = wordList[randIndex];
            */
            
            reader.close();
            return randWord;
        }
        //Word Answer List not found
        catch (FileNotFoundException e) {
            System.out.println("Specified file could not be found!");
        }
        //reader.readLine() throws exception
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
        
        //If Word fails to create
        System.out.println("Word Could Not Be Created!");
        System.exit(-1);
        return new Word("X");
    }
    
    //Set Word Components
    public void setWordStr(String wordStr) {
        this.wordStr = wordStr;
    }
    public void setWordArr(char[] wordArr) {
        this.wordArr = wordArr;
    }
    
    //Get Word Components
    public String getWordStr() {
        return this.wordStr;
    }
    public char[] getWordArr() {
        return this.wordArr;
    }
    
    //Word ToString
    public String toString() {
        return this.getWordStr();
    }
    
    public void lookUpWord() {
        Desktop d = Desktop.getDesktop();
        try {
            d.browse(new URI("https://www.merriam-webster.com/dictionary/" + this.getWordStr()));
        }
        catch (IOException e) {
            System.out.println("Error finding webpage");
        }
        catch (URISyntaxException e) {
            System.out.println("Error with webpage format");
        }
    }
}