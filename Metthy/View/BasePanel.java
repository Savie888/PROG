package Metthy.View;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

/**
 * BasePanel is an abstract utility JPanel that provides reusable methods
 * for creating styled buttons with hover animations and sound effects.
 */

public class BasePanel extends JPanel {

    protected JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFocusPainted(false);
        button.setOpaque(true);

        button.setFont(new Font("SansSerif", Font.BOLD, 18));
        button.setBackground(Color.black);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 4));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        Dimension originalSize = new Dimension(250, 70);
        button.setMaximumSize(originalSize);

        addButtonHoverAnimation(button, originalSize);

        return button;
    }

     /**
     * Plays a sound from a specified file located in the resources directory.
     *
     * @param soundFileName the name of the sound file
     */
    protected void playSound(String soundFileName) {
        try {
            URL soundURL = getClass().getResource(soundFileName);
            if (soundURL == null) {
                System.err.println("Sound file not found: " + soundFileName);
                return;
            }

            AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
            AudioFormat baseFormat = audioStream.getFormat();

            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);

            try (SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info)) {
                line.open(decodedFormat);
                line.start();

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = decodedAudioStream.read(buffer, 0, buffer.length)) != -1) {
                    line.write(buffer, 0, bytesRead);
                }

                line.drain();
            }

            decodedAudioStream.close();
            audioStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a hover animation to the specified JButton.
     * The button enlarges when hovered and returns to its original size when the mouse exits.
     *
     * @param btn     the JButton to animate
     * @param ogSize  the original size of the button
     */
    protected void addButtonHoverAnimation(JButton btn, Dimension ogSize) {

        Dimension hoverSize = new Dimension(310, 100);
        Timer[] currentTimer = {null};

        // Set initial size
        btn.setPreferredSize(ogSize);
        btn.setMaximumSize(ogSize);

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startAnimation(btn, ogSize, hoverSize, 5, currentTimer);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                startAnimation(btn, hoverSize, ogSize, 5, currentTimer);
            }
        });
    }

    /**
     * Performs animated resizing of a JButton between two sizes over a number of steps.
     *
     * @param button       the button to animate
     * @param from         the starting size
     * @param to           the target size
     * @param steps        number of animation frames/steps
     * @param currentTimer reference holder for stopping previous animations
     */
    protected void startAnimation(JButton button, Dimension from, Dimension to, int steps, Timer[] currentTimer) {
        if (currentTimer[0] != null && currentTimer[0].isRunning()) {
            currentTimer[0].stop();
        }

        int deltaW = (to.width - from.width) / steps;
        int deltaH = (to.height - from.height) / steps;

        final int[] currentStep = {0};

        currentTimer[0] = new Timer(15, null);

        currentTimer[0].addActionListener(e -> {
            if (currentStep[0] >= steps) {
                button.setPreferredSize(to);
                button.setMaximumSize(to);
                button.revalidate();
                currentTimer[0].stop();
                return;
            }

            int newW = from.width + deltaW * (currentStep[0] + 1);
            int newH = from.height + deltaH * (currentStep[0] + 1);

            button.setPreferredSize(new Dimension(newW, newH));
            button.setMaximumSize(new Dimension(newW, newH));
            button.revalidate();

            currentStep[0]++;
        });

        currentTimer[0].start();
    }
}
