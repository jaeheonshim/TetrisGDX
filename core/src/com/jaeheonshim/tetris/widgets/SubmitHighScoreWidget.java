package com.jaeheonshim.tetris.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jaeheonshim.tetris.util.HighScoreSender;
import com.jaeheonshim.tetris.util.Util;

public class SubmitHighScoreWidget extends Table {
    private BitmapFont bitmapFont;
    private NinePatchDrawable background = new NinePatchDrawable(new NinePatch(new Texture("ui/Panel.png"), 1, 1, 1, 1));
    private TextField nameField;

    private NinePatchDrawable buttonUp = new NinePatchDrawable(new NinePatch(new Texture("ui/button_up.png"), 1, 1, 1, 1));
    private NinePatchDrawable buttonDown = new NinePatchDrawable(new NinePatch(new Texture("ui/button_down.png"), 1, 1, 1, 1));
    private final TextButton submitButton;
    private final Label.LabelStyle labelStyle;

    public SubmitHighScoreWidget() {
        bitmapFont = new BitmapFont(Gdx.files.internal("fonts/ui.fnt"), new TextureRegion(new Texture("fonts/ui.png")));
        bitmapFont.getData().setScale(0.1f);
        bitmapFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        bitmapFont.setUseIntegerPositions(false);

        labelStyle = new Label.LabelStyle(bitmapFont, Color.WHITE);
        nameField = new TextField("", new TextField.TextFieldStyle(bitmapFont, Color.WHITE, new TextureRegionDrawable(new Texture("ui/Cursor.png")), background, background));
        nameField.setTextFieldFilter(new TextField.TextFieldFilter() {
            @Override
            public boolean acceptChar(TextField textField, char c) {
                return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ';
            }
        });

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle(buttonUp, buttonDown, buttonUp, bitmapFont);
        submitButton = new TextButton("SUBMIT", style);

        nameField.setMaxLength(16);

        setBackground(background);

    }

    public void setHighScore(final int highScore) {
        top();
        add(new Label("SUBMIT HIGH SCORE", labelStyle)).colspan(2);
        row();
        add(new Label("SCORE: ", labelStyle)).padTop(5);
        add(new Label(Util.padZeros(highScore), labelStyle)).padTop(5);
        row();
        add(new Label("Enter Name: ", labelStyle));
        add(nameField).width(80).height(8);
        row();
        add(submitButton).colspan(2).expandX().fillX().padTop(5).padLeft(10).padRight(10);

        submitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(nameField.getText().trim().isEmpty()) {
                    return;
                }

                HighScoreSender.sendVerifiedScore(nameField.getText().trim(), highScore, new Runnable() {
                    @Override
                    public void run() {
                        setVisible(false);
                    }
                });
            }
        });
    }
}
