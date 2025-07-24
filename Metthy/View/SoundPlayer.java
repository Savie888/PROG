package Metthy.View;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    public static void playSound(String soundFileName) {
        try {
            URL soundURL = SoundPlayer.class.getResource("/your/package/sound/" + soundFileName);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return;
            }
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
