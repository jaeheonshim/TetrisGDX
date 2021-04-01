package com.jaeheonshim.tetris;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jaeheonshim.tetris.game.BlockState;
import com.jaeheonshim.tetris.game.GameState;

public class GameScene {
    private Texture blockTexture = new Texture("Block.png");
    private int innerGameWidth = 10;
    private int innerGameHeight = 20;
    private float tileWidth = 32;

    private GameState gameState = new GameState(innerGameWidth, innerGameHeight);

    public void draw(float x, float y, SpriteBatch spriteBatch, ShapeRenderer shapeRenderer) {
        spriteBatch.setColor(Color.WHITE);

        drawFrame(x, y, spriteBatch);
        drawGridLines(x, y, shapeRenderer);
        drawGame(x, y, spriteBatch);
    }

    private void drawGame(float x, float y, SpriteBatch spriteBatch) {
        BlockState[][] states = gameState.getBlockStates();

        for(int i = 0; i < states.length; i++) {
            for(int j = 0; j < states[i].length; j++) {
                BlockState state = states[i][j];
                if(state != null) {
                    spriteBatch.setColor(state.getBlockColor());
                    spriteBatch.draw(blockTexture, x + tileWidth * (j + 1), y + ((innerGameHeight + 1) * tileWidth) - tileWidth * (i + 1));
                }
            }
        }
    }

    private void drawFrame(float x, float y, SpriteBatch spriteBatch) {
        spriteBatch.setColor(Color.GRAY);
        // draw left and right
        for(int i = 0; i < innerGameHeight + 1; i++) {
            spriteBatch.draw(blockTexture, x, y + tileWidth * i + tileWidth);
            spriteBatch.draw(blockTexture, x + tileWidth * (innerGameWidth + 1), y + tileWidth * i + tileWidth);
        }

        // draw bottom and top
        for(int i = 0; i < innerGameWidth + 2; i++) {
            spriteBatch.draw(blockTexture, x + tileWidth * i, y);
            spriteBatch.draw(blockTexture, x + tileWidth * i, y + tileWidth * (innerGameHeight + 1));
        }
    }

    private void drawGridLines(float x, float y, ShapeRenderer shapeRenderer) {
        shapeRenderer.setColor(Color.DARK_GRAY);

        for(int i = 2; i <= innerGameWidth; i++) {
            shapeRenderer.line(x + tileWidth * i, y + tileWidth, x + tileWidth * i, y + tileWidth + ((tileWidth) * innerGameHeight));
        }

        for(int i = 2; i <= innerGameHeight; i++) {
            shapeRenderer.line(x + tileWidth, y + tileWidth * i, x + tileWidth + (tileWidth * innerGameWidth), y + tileWidth * i);
        }
    }

    public int getInnerGameWidthDimension() {
        return (innerGameWidth + 2) * 32;
    }

    public int getInnerGameHeightDimension() {
        return (innerGameHeight + 2) * 32;
    }

    public GameState getGameState() {
        return gameState;
    }
}
