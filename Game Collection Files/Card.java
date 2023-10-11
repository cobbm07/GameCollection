import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import javax.sound.sampled.LineUnavailableException;

public class Card
{
    String cardName;
    public static void matchSound() {
        String filePath = "assets/memoryGame/cardNormal.wav";
        try
        {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
            Clip sound = AudioSystem.getClip();
            sound.open(audioIn);
            sound.start();
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("File type is not supported!");
        }
        catch (IOException e)
        {
            System.out.println("Error playing specified sound!");
        }
        catch (LineUnavailableException e)
        {
            System.out.println("Line unavailable!");
        }
    }
    
    public static void failSound() {
        String filePath = "assets/memoryGame/cardFail.wav";
        try
        {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
            Clip sound = AudioSystem.getClip();
            sound.open(audioIn);
            sound.start();
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("File type is not supported!");
        }
        catch (IOException e)
        {
            System.out.println("Error playing specified sound!");
        }
        catch (LineUnavailableException e)
        {
            System.out.println("Line unavailable!");
        }
    }
    
    public static void winSound() {
        String filePath = "assets/memoryGame/winNormal.wav";
        try
        {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File(filePath));
            Clip sound = AudioSystem.getClip();
            sound.open(audioIn);
            sound.start();
        }
        catch (UnsupportedAudioFileException e)
        {
            System.out.println("File type is not supported!");
        }
        catch (IOException e)
        {
            System.out.println("Error playing specified sound!");
        }
        catch (LineUnavailableException e)
        {
            System.out.println("Line unavailable!");
        }
    }
}
