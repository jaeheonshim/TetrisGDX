package com.jaeheonshim.tetris;

import com.badlogic.gdx.graphics.Color;

public class BlockState {
    private Color blockColor;
    private boolean fixed;
    private boolean pivot;

    public BlockState(Color blockColor) {
        this.blockColor = blockColor;
    }

    public Color getBlockColor() {
        return blockColor;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setBlockColor(Color blockColor) {
        this.blockColor = blockColor;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public boolean isPivot() {
        return pivot;
    }

    public BlockState setPivot(boolean pivot) {
        this.pivot = pivot;
        return this;
    }
}
