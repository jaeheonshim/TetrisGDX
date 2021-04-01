package com.jaeheonshim.tetris;

import com.badlogic.gdx.graphics.Color;

public class BlockState {
    private Color blockColor;
    private boolean fixed;

    public BlockState(Color blockColor) {
        this.blockColor = blockColor;
    }

    public Color getBlockColor() {
        return blockColor;
    }

    public boolean isFixed() {
        return fixed;
    }
}