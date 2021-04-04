package com.jaeheonshim.tetris.util;

public class Timer {
    private float timer;
    private float value;

    public Timer(float value) {
        this.value = value;
        this.timer = value;
    }

    public void reset() {
        timer = value;
    }

    public boolean isReady() {
        return timer <= 0;
    }

    public void update(float delta) {
        timer -= delta;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
