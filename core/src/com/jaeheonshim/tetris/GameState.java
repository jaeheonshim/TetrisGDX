package com.jaeheonshim.tetris;

import com.badlogic.gdx.graphics.Color;

public class GameState {
    private int width;
    private int height;

    private BlockState[][] blockStates;

    public GameState(int width, int height) {
        this.width = width;
        this.height = height;

        blockStates = new BlockState[height][width];
        blockStates[0][0] = new BlockState(Color.valueOf("0341AE"));
        blockStates[0][1] = new BlockState(Color.valueOf("0341AE"));
        blockStates[1][1] = new BlockState(Color.valueOf("0341AE"));
        blockStates[0][2] = new BlockState(Color.valueOf("0341AE"));
    }

    public BlockState[][] getBlockStates() {
        return blockStates;
    }

    public void tickBlocks() {
        for(int i = blockStates.length - 1; i >= 0; i--) {
            for(int j = blockStates[i].length - 1; j >= 0; j--) {
                BlockState blockState = blockStates[i][j];
                if(blockState != null && !blockState.isFixed() && i + 1 < blockStates.length) {
                    blockStates[i + 1][j] = blockState;
                    blockStates[i][j] = null;
                } else if(blockState != null && i + 1 >= blockStates.length && !blockState.isFixed()) {
                    fixMoving();
                }
            }
        }
    }

    private void fixMoving() {
        for(int i = 0; i < blockStates.length; i++) {
            for(int j = 0; j < blockStates[i].length; j++) {
                BlockState blockState = blockStates[i][j];
                if(blockState != null && !blockState.isFixed()) {
                    blockState.setFixed(true);
                }
            }
        }
    }
}
