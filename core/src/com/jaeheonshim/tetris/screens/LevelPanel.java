package com.jaeheonshim.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class LevelPanel {
    private NinePatch boxFrame;
    private BitmapFont font;
    private GlyphLayout glyphLayout = new GlyphLayout();

    private float width = 160;
    private float height = 90;
    private int level = 0;

    public LevelPanel() {
        boxFrame = new NinePatch(new Texture(Gdx.files.internal("BoxFrame.png")), 9, 9, 9, 9);
        font = new BitmapFont(Gdx.files.internal("fonts/tetris.fnt"));
        font.setColor(Color.valueOf("e6e6e6"));
        font.getData().setScale(0.45f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setLineHeight(65f);
    }

    public void draw(float x, float y, SpriteBatch spriteBatch) {
        spriteBatch.begin();
        boxFrame.draw(spriteBatch, x, y, width, height);

        String text = String.format("LEVEL\n%d", level);
        glyphLayout.setText(font, text);

        font.draw(spriteBatch, text, x + (width / 2) - (glyphLayout.width / 2), y + (height / 2) + (glyphLayout.height / 2));

        spriteBatch.end();
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
