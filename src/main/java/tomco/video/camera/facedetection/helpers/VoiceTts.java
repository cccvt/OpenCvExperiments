package tomco.video.camera.facedetection.helpers;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by tomco on 22/01/2017.
 */
public class VoiceTts {

    private VoiceManager voiceManager;
    private Voice voice;
    private boolean isSpeaking = false;

    private final Executor EXEC = Executors.newSingleThreadExecutor();

    public VoiceTts() {
        voiceManager = VoiceManager.getInstance();
        voice = voiceManager.getVoice(Config.VOICE_NAME);
        voice.allocate();
    }

    public void speak(String text) {
        if(!isSpeaking) {
            EXEC.execute(() -> {
                isSpeaking = true;
                voice.speak(text);
                isSpeaking = false;
            });
        }
    }
}
