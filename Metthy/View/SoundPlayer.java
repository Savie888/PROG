package Metthy.View;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundPlayer {

    public static void playSound(URL soundURL) {

        if (soundURL == null) {
            System.err.println("Sound URL is null — check your path.");
            return;
        }

        System.out.println("Loading sound from: " + soundURL);

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL)) {
            AudioFormat format = audioStream.getFormat();
            System.out.println("Audio format: " + format);

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            System.err.println("Unsupported audio format.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO error when loading sound.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.err.println("Audio line unavailable.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            System.err.println("Illegal audio data (maybe empty or bad format).");
            e.printStackTrace();
        }
    }

}
