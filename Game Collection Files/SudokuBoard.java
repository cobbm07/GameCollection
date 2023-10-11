import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;

public class SudokuBoard implements GameBoard{
    char boardNums[][];
    char baseBoard[][];
    char solutionBoard[][];
    public SudokuBoard() {
        boardNums = new char[][] {{' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '},
                                {' ',' ',' ',' ',' ',' ',' ',' ',' '}};
        baseBoard = newRandBoard();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                boardNums[i][j] = baseBoard[i][j];
            }
        }
    }
    
    public char[][] newRandBoard() {
        Random rand = new Random();
        char randBoard[][] = new char[9][9];
        int boardNum = rand.nextInt(10) + 1;
        try {
            String filePath = "assets/sudoku/board" + boardNum + ".txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 9; i++) {
                String[] currentLine = reader.readLine().split(",");
                for (int j = 0; j < 9; j++) {
                    char currentChar = currentLine[j].charAt(0);
                    randBoard[i][j] = currentChar;
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
        }
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
        solutionBoard = createSolution(boardNum);
        return randBoard;
    }
    
    public char[][] createSolution(int boardNum) {
        String filePath = "assets/sudoku/board" + boardNum + "Solution.txt";
        char solutionBoard[][] = new char[9][9];
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            for (int i = 0; i < 9; i++) {
                String[] currentLine = reader.readLine().split(",");
                for (int j = 0; j < 9; j++) {
                    int currentInt = Integer.parseInt(currentLine[j]);
                    solutionBoard[i][j] = (char)currentInt;
                }
            }
            reader.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
        }
        catch (IOException e) {
            System.out.println("Error reading from file!");
        }
        return solutionBoard;
    }
    
    public void printBoard() {
        System.out.println("   A   B   C   D   E   F   G   H   I");
        for (int i = 0; i < 9; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + boardNums[i][j]);
                if (j != 8) {
                    System.out.print(" |");
                }
            }
            if (i != 8) {
                System.out.println("\n  -----------------------------------");
            }
        }
        System.out.println();
    }
    
    public void printBaseBoard() {
        System.out.println("   A   B   C   D   E   F   G   H   I");
        for (int i = 0; i < 9; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < 9; j++) {
                System.out.print(" " + baseBoard[i][j]);
                if (j != 8) {
                    System.out.print(" |");
                }
            }
            if (i != 8) {
                System.out.println("\n  -----------------------------------");
            }
        }
        System.out.println();
    }
    
    public char[][] getBaseBoard() {
        return baseBoard;
    }
    public char[][] getBoard() {
        return boardNums;
    }
    public char[][] getSolution() {
        return solutionBoard;
    }
    public void setValue(int x, int y, int value) {
        boardNums[x][y] = (char)(value + '0');
        System.out.println((char)(value + '0'));
    }
    public char getValue(int x, int y) {
        return boardNums[x][y];
    }
}