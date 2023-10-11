import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ParseBoard {
    public static void parse(int boardNum, boolean solution) {
        String filePathInput = "";
        String filePathOutput = "";
        if (solution) {
            filePathInput = "assets/toParseSOL.txt";
            filePathOutput = "assets/board" + boardNum + "Solution.txt";
        }
        else {
            filePathInput = "assets/toParse.txt";
            filePathOutput = "assets/board" + boardNum + ".txt";
        }
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePathInput));
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePathOutput));
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    String line = reader.readLine();
                    if (line.equals(" ")) {
                        line = "0";
                    }
                    if (j != 8) writer.write(line + ",");
                    else writer.write(line);
                }
                if (i != 8) writer.write("\n");
            }
            reader.close();
            writer.close();
        }
        catch (FileNotFoundException e) {
            System.out.println("File could not be found!");
        }
        catch (IOException e) {
            System.out.println("Error writing to or reading file!");
        }
    }
    
    public static void parse2(int boardNum) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("assets/sudoku/board" + boardNum + ".txt"));
            BufferedWriter writer = new BufferedWriter(new FileWriter("assets/sudoku/boardNEW" + boardNum + ".txt"));
            String nextLine = reader.readLine();
            while (!nextLine.isEmpty()) {
                String[] nums = nextLine.split(",");
                for(int i = 0; i < nums.length; i++) {
                    if (nums[i].equals("0")) {
                        nums[i] = " ";
                    }
                    if (i == nums.length - 1) {
                        writer.write(nums[i]);
                    }
                    else writer.write(nums[i] + ",");
                }
                nextLine = reader.readLine();
                if (nextLine == null) {
                    break;
                }
                writer.write("\n");
            }
            reader.close();
            writer.close();
        }
        catch(FileNotFoundException e) {
            System.out.println("File could not be found");
        }
        catch (IOException e) {
            System.out.println("Problem reading from file");
        }
    }
}