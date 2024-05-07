package clientSideGame.sounds;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class SoundController {
    private final String musicPath = "./music.wav";
    private final String applePath = "./eatingApple.wav";
    private final String crashPath = "./crash.wav";

    private enum SoundType {
        music,
        apple,
        crash
    }

    Clip appleClip;
    Clip musicClip;

    Clip crashClip;

    private void loadSound(String path, SoundType soundType) {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(path);
        if (inputStream != null) {
            try {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(inputStream);
                switch (soundType) {
                    case apple:
                        appleClip = AudioSystem.getClip();
                        appleClip.open(audioStream);
                        break;
                    case music:
                        musicClip = AudioSystem.getClip();
                        musicClip.open(audioStream);
                        break;
                    case crash:
                        crashClip = AudioSystem.getClip();
                        crashClip.open(audioStream);
                        break;
                }
                musicClip.open(audioStream);
            } catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
        }
    }


    public SoundController() {
        loadSound(musicPath, SoundType.music);
        loadSound(applePath, SoundType.apple);
        loadSound(crashPath, SoundType.crash);
    }

    public void appleSound() {
        if (appleClip != null) {
            appleClip.setFramePosition(0);
            appleClip.start();
        }
    }

    public void crashSound() {
        if (crashClip != null) {
            crashClip.setFramePosition(0);
            crashClip.start();
        }
    }

    public void startMusic() {
        if (musicClip != null) {
            musicClip.setFramePosition(0);
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void pauseMusic() {
        if (musicClip != null) {
            musicClip.stop();
        }
    }

    public void resumeMusic() {
        if (musicClip != null) {
            musicClip.start();
        }
    }
}
