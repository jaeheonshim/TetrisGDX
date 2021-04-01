package com.jaeheonshim.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class DelayedIntervalKeypressListener {
    private int key;
    private float timeLength;
    private float timer;

    public DelayedIntervalKeypressListener(int key, float timeLength) {
        this.key = key;
        this.timeLength = timeLength;
        this.timer = timeLength;
    }

    public boolean isPressed() {
        if(Gdx.input.isKeyPressed(key) && timer <= 0) {
            timer = timeLength;
            return true;
        } else if(!Gdx.input.isKeyPressed(key)) {
            timer = 0;
        }
        return false;
    }

    public void update(float delta) {
        timer -= delta;
    }
}
