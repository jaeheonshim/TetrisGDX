package com.jaeheonshim.tetris;

import com.badlogic.gdx.graphics.Color;

public class GameState {
    private int width;
    private int height;

    private BlockState[][] blockStates;

    public GameState(int width, int height) {
        this.width = width;
        this.height = height;

        blockStates = new BlockState[width][height];
        blockStates[0][0] = new BlockState(Color.valueOf("0341AE"));
        blockStates[0][1] = new BlockState(Color.valueOf("0341AE"));
        blockStates[1][1] = new BlockState(Color.valueOf("0341AE"));
        blockStates[0][2] = new BlockState(Color.valueOf("0341AE"));
    }

    public BlockState[][] getBlockStates() {
        return blockStates;
    }
}
