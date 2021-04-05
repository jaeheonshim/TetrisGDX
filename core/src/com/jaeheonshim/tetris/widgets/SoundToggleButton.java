package com.jaeheonshim.tetris.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.jaeheonshim.tetris.TetrisGame;

public class SoundToggleButton extends Actor {
    private Preferences preferences;
    private Texture soundIcon = new Texture("ui/sound_icon.png");
    private Texture soundOffIcon = new Texture("ui/sound_off_icon.png");

    private boolean soundOn;

    public SoundToggleButton() {
        preferences = Gdx.app.getPreferences(TetrisGame.PREFERENCES);

        soundOn = preferences.getInteger("soundOn") == 1;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if(soundOn) {
            batch.draw(soundIcon, getX(), getY(), 10, 10);
        } else {
            batch.draw(soundOffIcon, getX(), getY(), 10, 10);
        }

        setBounds(getX(), getY(), 10, 10);
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void setSoundOn(boolean soundOn) {
        this.soundOn = soundOn;
    }
}
