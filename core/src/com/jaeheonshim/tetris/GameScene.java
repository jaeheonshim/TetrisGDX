package com.jaeheonshim.tetris;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameScene {
    private Texture blockTexture = new Texture("Block.png");
    private int innerGameWidth = 10;
    private int innerGameHeight = 20;

    public void draw(float x, float y, SpriteBatch spriteBatch) {
        drawFrame(x, y, spriteBatch);
    }

    private void drawFrame(float x, float y, SpriteBatch spriteBatch) {
        // draw left and right
        for(int i = 0; i < innerGameHeight + 1; i++) {
            spriteBatch.draw(blockTexture, x, y + 32 * i + 32);
            spriteBatch.draw(blockTexture, x + 32 * 11, y + 32 * i + 32);
        }

        // draw bottom and top
        for(int i = 0; i < innerGameWidth + 2; i++) {
            spriteBatch.draw(blockTexture, x + 32 * i, y);
            spriteBatch.draw(blockTexture, x + 32 * i, y + 32 * 21);
        }
    }

    public int getInnerGameWidthDimension() {
        return (innerGameWidth + 2) * 32;
    }

    public int getInnerGameHeightDimension() {
        return (innerGameHeight + 2) * 32;
    }
}
