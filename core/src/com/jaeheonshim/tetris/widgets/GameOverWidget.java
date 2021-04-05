package com.jaeheonshim.tetris.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jaeheonshim.tetris.TetrisGame;
import com.jaeheonshim.tetris.screens.GameScreen;

public class GameOverWidget extends Table {
    private BitmapFont headingFont;
    private BitmapFont detailFont;
    private NinePatch background;
    private TextButton.TextButtonStyle buttonStyle;
    private NinePatchDrawable buttonUp = new NinePatchDrawable(new NinePatch(new Texture("ui/button_up.png"), 1, 1, 1, 1));
    private NinePatchDrawable buttonDown = new NinePatchDrawable(new NinePatch(new Texture("ui/button_down.png"), 1, 1, 1, 1));

    private int level;
    private int score;
    private int lines;

    private Texture newGameTexture = new Texture("ui/new_game_button.png");
    private final TextButton newGameButton;
    private final TextButton exitButton;

    public GameOverWidget(int level, int score, int lines) {
        background = new NinePatch(new Texture("ui/Panel.png"), 1, 1, 1, 1);

        headingFont = new BitmapFont(Gdx.files.internal("fonts/ui.fnt"), new TextureRegion(new Texture("fonts/ui.png")));
        headingFont.getData().setScale(0.1f);
        headingFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        detailFont = new BitmapFont(Gdx.files.internal("fonts/ui.fnt"), new TextureRegion(new Texture("fonts/ui.png")));
        detailFont.getData().setScale(0.07f);
        detailFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        detailFont.setUseIntegerPositions(false);

        buttonStyle = new TextButton.TextButtonStyle(buttonUp, buttonDown, buttonUp, detailFont);

        Label heading = new Label("GAME OVER!", new Label.LabelStyle(headingFont, Color.WHITE));
        newGameButton = new TextButton("NEW GAME", buttonStyle);
        exitButton = new TextButton("EXIT", buttonStyle);

        this.level = level;
        this.lines = lines;
        this.score = score;

        background(new NinePatchDrawable(background));
        setDebug(false);

        pad(4, 4, 4, 4);
        top();
        add(heading).colspan(3).expandX();

        row();
        add(getStatsTable()).colspan(3).fillX();

        row();
        add(newGameButton).expandX().padTop(2).height(10);
        add(exitButton).expandX().padTop(2).height(10);

        pack();
    }

    private Table getStatsTable() {
        Label.LabelStyle style = new Label.LabelStyle(detailFont, Color.WHITE);
        Label scoreLabel = new Label("SCORE: ", style);
        Label scoreValue = new Label(String.format("%06d", score), style);

        Label rowsLabel = new Label("LINES CLEARED: ", style);
        Label rowsValue = new Label(Integer.toString(lines), style);

        Label levelLabel = new Label("LEVEL REACHED: ", style);
        Label levelValue = new Label(Integer.toString(level), style);


        Table statsTable = new Table();
        statsTable.add(scoreLabel).expandX().left();
        statsTable.add(scoreValue).expandX().right();
        statsTable.row();

        statsTable.add(rowsLabel).expandX().left();
        statsTable.add(rowsValue).expandX().right();
        statsTable.row();

        statsTable.add(levelLabel).expandX().left();
        statsTable.add(levelValue).expandX().right();

        return statsTable;
    }

    public TextButton getNewGameButton() {
        return newGameButton;
    }

    public TextButton getExitButton() {
        return exitButton;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getLines() {
        return lines;
    }

    public void setLines(int lines) {
        this.lines = lines;
    }
}
