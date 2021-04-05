package com.jaeheonshim.tetris.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jaeheonshim.tetris.util.Util;

public class LinesClearedPanel {
    private NinePatch boxFrame;
    private BitmapFont font;
    private GlyphLayout glyphLayout = new GlyphLayout();

    private float width = 500;
    private float height = 60;
    private int linesCleared = 0;

    public LinesClearedPanel() {
        boxFrame = new NinePatch(new Texture(Gdx.files.internal("BoxFrame.png")), 9, 9, 9, 9);
        font = new BitmapFont(Gdx.files.internal("fonts/tetris.fnt"));
        font.setColor(Color.valueOf("e6e6e6"));
        font.getData().setScale(0.6f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
    }

    public void draw(float x, float y, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);

        String text = "LINES: " + Util.padZeros(linesCleared, 3);
        glyphLayout.setText(font, text);
        boxFrame.draw(spriteBatch, x, y, width, height);
        font.draw(spriteBatch, text, x + (width / 2) - glyphLayout.width / 2, y + glyphLayout.height / 2 + height / 2);

        spriteBatch.end();
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setLinesCleared(int linesCleared) {
        this.linesCleared = linesCleared;
    }
}
