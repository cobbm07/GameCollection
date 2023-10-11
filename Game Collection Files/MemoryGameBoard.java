import java.util.Random;
import java.util.List;
import java.util.LinkedList;

public class MemoryGameBoard implements GameBoard {
    private char[][] randomBoard;
    private char[][] currentBoard;
    public MemoryGameBoard() {
        newRandBoard();
        currentBoard = new char[4][3];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                currentBoard[i][j] = ' ';
            }
        }
    }
    
    public char[][] newRandBoard() {
        randomBoard = new char[4][3];
        int uChars = 6;
        List<Character> uCharsList = new LinkedList<>();
        String charsToPickFrom = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        Random rand = new Random();
        for (int i = 0; i < uChars; i++) {
            char randChar = charsToPickFrom.charAt(rand.nextInt(charsToPickFrom.length()));
            while (uCharsList.contains(randChar)) {
                randChar = charsToPickFrom.charAt(rand.nextInt(charsToPickFrom.length()));
            }
            uCharsList.add(randChar);
        }
        List<Character> dCharsList = new LinkedList<>();
        for (int i = 0; i < 6; i++) {
            dCharsList.add(uCharsList.get(i));
            dCharsList.add(uCharsList.get(i));
        }
        StringBuilder allChars = new StringBuilder("");
        for (Character c: dCharsList) {
            allChars.append(c);
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                int remove = rand.nextInt(allChars.length());
                randomBoard[j][i] = allChars.charAt(remove);
                allChars.deleteCharAt(remove);
            }
        }
        
        return randomBoard;
    }
    
    public void printBoard() {
        System.out.println();
        System.out.println("    a      b      c      d  ");
        System.out.println("  -----  -----  -----  -----");
        System.out.println("  |   |  |   |  |   |  |   |");
        System.out.printf("1 | %s |  | %s |  | %s |  | %s |%n", currentBoard[0][0], currentBoard[1][0], currentBoard[2][0], currentBoard[3][0]);
        System.out.println("  |   |  |   |  |   |  |   |");
        System.out.println("  -----  -----  -----  -----");
        
        System.out.println("  -----  -----  -----  -----");
        System.out.println("  |   |  |   |  |   |  |   |");
        System.out.printf("2 | %s |  | %s |  | %s |  | %s |%n", currentBoard[0][1], currentBoard[1][1], currentBoard[2][1], currentBoard[3][1]);
        System.out.println("  |   |  |   |  |   |  |   |");
        System.out.println("  -----  -----  -----  -----");
        
        System.out.println("  -----  -----  -----  -----");
        System.out.println("  |   |  |   |  |   |  |   |");
        System.out.printf("3 | %s |  | %s |  | %s |  | %s |%n", currentBoard[0][2], currentBoard[1][2], currentBoard[2][2], currentBoard[3][2]);
        System.out.println("  |   |  |   |  |   |  |   |");
        System.out.println("  -----  -----  -----  -----");
    }
    
    public char[][] getSolution() {
        return new char[][]{{}};
    }
    public void printSolution() {
        System.out.println();
        System.out.println("    a      b      c      d  ");
        System.out.println("  -----  -----  -----  -----");
        System.out.println("  -   -  -   -  -   -  -   -");
        System.out.printf("1 - %s -  - %s -  - %s -  - %s -%n", randomBoard[0][0], randomBoard[1][0], randomBoard[2][0], randomBoard[3][0]);
        System.out.println("  -   -  -   -  -   -  -   -");
        System.out.println("  -----  -----  -----  -----");
        
        System.out.println("  -----  -----  -----  -----");
        System.out.println("  -   -  -   -  -   -  -   -");
        System.out.printf("2 - %s -  - %s -  - %s -  - %s -%n", randomBoard[0][1], randomBoard[1][1], randomBoard[2][1], randomBoard[3][1]);
        System.out.println("  -   -  -   -  -   -  -   -");
        System.out.println("  -----  -----  -----  -----");
        
        System.out.println("  -----  -----  -----  -----");
        System.out.println("  -   -  -   -  -   -  -   -");
        System.out.printf("3 - %s -  - %s -  - %s -  - %s -%n", randomBoard[0][2], randomBoard[1][2], randomBoard[2][2], randomBoard[3][2]);
        System.out.println("  -   -  -   -  -   -  -   -");
        System.out.println("  -----  -----  -----  -----");
    }
    
    public void setValue(int x, int y, char val) {
        currentBoard[x][y] = val;
    }
    
    public char getValue(int x, int y) {
        return randomBoard[x][y];
    }
    
    public char getCurrValue(int x, int y) {
        return currentBoard[x][y];
    }
}
