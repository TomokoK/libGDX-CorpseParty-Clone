package com.groupofseven.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Timer;

public class SoundActions {

    private Music mainTheme = Gdx.audio.newMusic(Gdx.files.internal("music/11Chapter1MainTheme.mp3"));
    private Music Class5ATheme = Gdx.audio.newMusic(Gdx.files.internal("music/07HauntedRoom.mp3"));
    private Music Class3ATheme = Gdx.audio.newMusic(Gdx.files.internal("music/25Fear3.mp3"));
    private Music Class2ATheme = Gdx.audio.newMusic(Gdx.files.internal("music/09Fear.mp3"));

    public void disposeAudio(String passedAudio) {
        if (passedAudio.equalsIgnoreCase("Main theme")) {
            fadeOutMusic(mainTheme);
        } else if (passedAudio.equalsIgnoreCase("Class5A Theme")) {
            fadeOutMusic(Class5ATheme);
        } else if (passedAudio.equalsIgnoreCase("Class2A Theme")) {
            fadeOutMusic(Class2ATheme);
        } else if (passedAudio.equalsIgnoreCase("Class3A Theme")) {
            fadeOutMusic(Class3ATheme);
        }
    }

    public void setAudioPaused(String passedAudio) {
        if (passedAudio.equalsIgnoreCase("main theme")) {
            mainTheme.pause();
        } else if (passedAudio.equalsIgnoreCase("class5a theme")) {
            Class5ATheme.pause();
        } else if (passedAudio.equalsIgnoreCase("class2a theme")) {
            Class2ATheme.pause();
        } else if (passedAudio.equalsIgnoreCase("class3a theme")) {
            Class3ATheme.pause();
        }
    }

    public void setAudioLooping(String passedAudio, boolean looping) {
        if (passedAudio.equalsIgnoreCase("main theme")) {
            mainTheme.setLooping(looping);
        } else if (passedAudio.equalsIgnoreCase("class5a theme")) {
            Class5ATheme.setLooping(looping);
        } else if (passedAudio.equalsIgnoreCase("class2a theme")) {
            Class2ATheme.setLooping(looping);
        } else if (passedAudio.equalsIgnoreCase("class3a theme")) {
            Class3ATheme.setLooping(looping);
        }
    }

    public void setAudioVolume(String passedAudio, float num) {
        if (passedAudio.equalsIgnoreCase("Main theme")) {
            mainTheme.setVolume(num);
        } else if (passedAudio.equalsIgnoreCase("class5a theme")) {
            Class5ATheme.setVolume(num);
        } else if (passedAudio.equalsIgnoreCase("class2a theme")) {
            Class2ATheme.setVolume(num);
        } else if (passedAudio.equalsIgnoreCase("class3a theme")) {
            Class3ATheme.setVolume(num);
        }
    }

    public void setAudioPlaying(String passedAudio) {
        if (passedAudio.equalsIgnoreCase("Main theme")) {
            mainTheme.setVolume(0f);
            mainTheme.play();
            fadeInMusic(mainTheme);
        } else if (passedAudio.equalsIgnoreCase("class5a theme")) {
            Class5ATheme.setVolume(0f);
            Class5ATheme.play();
            fadeInMusic(Class5ATheme);
        } else if (passedAudio.equalsIgnoreCase("class2a theme")) {
            Class2ATheme.setVolume(0f);
            Class2ATheme.play();
            fadeInMusic(Class2ATheme);
        } else if (passedAudio.equalsIgnoreCase("class3a theme")) {
            Class3ATheme.setVolume(0f);
            Class3ATheme.play();
            fadeInMusic(Class3ATheme);
        }
    }

    private void fadeInMusic(Music passedAudio) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (passedAudio.getVolume() <= 0.500f) {
                    passedAudio.setVolume(passedAudio.getVolume() + 0.001f);
                } else {
                    this.cancel();
                }
            }
        }, 0f, 0.01f);
    }

    private void fadeOutMusic(Music passedAudio) {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                if (passedAudio.getVolume() >= 0.001f)
                    passedAudio.setVolume(passedAudio.getVolume() - 0.001f);
                else {
                    passedAudio.stop();
                    this.cancel();
                }
            }
        }, 0f, 0.005f);
    }
}
