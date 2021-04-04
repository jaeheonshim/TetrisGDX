package com.jaeheonshim.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jaeheonshim.tetris.game.BlockType;
import com.jaeheonshim.tetris.game.GameState;

public class NextDropPanel {
    private NinePatch boxFrame;
    private BitmapFont font;
    private Texture blockTexture = new Texture("Block.png");
    private GlyphLayout glyphLayout = new GlyphLayout();

    private BlockType blockType;
    private Color color = GameState.randomColor();

    private float width = 160;
    private float height = 140;

    private int tileWidth = 32;

    public NextDropPanel() {
        boxFrame = new NinePatch(new Texture(Gdx.files.internal("BoxFrame.png")), 9, 9, 9, 9);
        font = new BitmapFont(Gdx.files.internal("fonts/tetris.fnt"));
        font.setColor(Color.valueOf("e6e6e6"));
        font.getData().setScale(0.45f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void draw(float x, float y, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);

        glyphLayout.setText(font, "NEXT");
        boxFrame.draw(spriteBatch, x, y, width, height);
        font.draw(spriteBatch, "NEXT", x + 20, y + height - 15);

        spriteBatch.setColor(color);
        drawType(x + (width / 2) - (getTypeWidth() / 2f), y + 20, spriteBatch);

        spriteBatch.end();
    }

    private void drawType(float x, float y, SpriteBatch spriteBatch) {
        boolean[][] blockArray = getBlockArray();
        for(int i = 0; i < blockArray.length; i++) {
            for(int j = 0; j < blockArray[i].length; j++) {
                if(blockArray[i][j])
                    spriteBatch.draw(blockTexture, x + j * tileWidth, y + (blockArray.length - i - 1) * tileWidth);
            }
        }
    }

    private int getTypeWidth() {
        boolean[][] blockArray = getBlockArray();
        int max = 0;
        for (boolean[] booleans : blockArray) {
            if (booleans.length > max) {
                max = booleans.length;
            }
        }

        return max * tileWidth;
    }

    private int getTypeHeight() {
        return getBlockArray().length * tileWidth;
    }

    private boolean[][] getBlockArray() {
        switch(blockType) {
            case I:
                return new boolean[][]{{true, true, true, true}};
            case J:
                return new boolean[][] {
                        {true, false, false},
                        {true, true, true}
                };
            case L:
                return new boolean[][] {
                        {false, false, true},
                        {true, true, true}
                };
            case O:
                return new boolean[][] {
                        {true, true},
                        {true, true}
                };
            case S:
                return new boolean[][] {
                        {false, true, true},
                        {true, true, false}
                };
            case T:
                return new boolean[][] {
                        {false, true, false},
                        {true, true, true}
                };
            case Z:
                return new boolean[][] {
                        {true, true, false},
                        {false, true, true}
                };
        }

        return null;
    }

    public void setBlockType(BlockType blockType) {
        this.blockType = blockType;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
