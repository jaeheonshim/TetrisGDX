package com.jaeheonshim.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameDetailPanel {
    private NinePatch boxFrame;
    private BitmapFont font;
    private GlyphLayout glyphLayout = new GlyphLayout();

    private float width = 200;
    private float height = 275;
    private float paddingX = 18;
    private float paddingY = 25;

    private int score;
    private int best;
    private int record;

    public GameDetailPanel() {
        boxFrame = new NinePatch(new Texture(Gdx.files.internal("BoxFrame.png")), 9, 9, 9, 9);
        font = new BitmapFont(Gdx.files.internal("fonts/tetris.fnt"));
        font.getData().setScale(0.45f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setLineHeight(65f);
    }

    public void draw(float x, float y, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);

        boxFrame.draw(spriteBatch, x, y, width, height);

        glyphLayout.setText(font, "SCORE");
        font.draw(spriteBatch, String.format("SCORE\n%06d\n\nBEST\n%06d\n\nRECORD\n%06d", score, best, record), x + paddingX, y + height - paddingY);


        spriteBatch.end();
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getBest() {
        return best;
    }

    public void setBest(int best) {
        this.best = best;
    }

    public int getRecord() {
        return record;
    }

    public void setRecord(int record) {
        this.record = record;
    }
}
