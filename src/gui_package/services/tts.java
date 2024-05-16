package gui_package.services;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

public class tts extends Thread {

    String word;

    public tts(String word) {
        this.word = word;
    }

    @Override
    public void run() {
        speak(word);
    }

    public static void speak(String word) {
        System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
        Voice voice = VoiceManager.getInstance().getVoice("kevin16");
        if (voice != null) {
            voice.allocate();
            voice.setRate(140);
            voice.speak(word);
            voice.deallocate();
        } else {
            System.out.println("TTS Error");
        }
    }
}
