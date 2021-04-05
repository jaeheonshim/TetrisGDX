package com.jaeheonshim.tetris.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.jaeheonshim.tetris.util.HighScoreEntry;

public class HighScoresWidget extends Table {
    private BitmapFont font;
    private BitmapFont nameFont;
    private NinePatchDrawable background;
    private Texture closeTexture = new Texture("ui/Close.png");
    private Cell tableCell;
    private final Label heading;
    private final Button closeButton;

    public HighScoresWidget() {
        background = new NinePatchDrawable(new NinePatch(new Texture("ui/Panel.png"), 1, 1, 1, 1));

        font = new BitmapFont(Gdx.files.internal("fonts/ui.fnt"), new TextureRegion(new Texture("fonts/ui.png")));
        font.getData().setScale(0.1f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.setUseIntegerPositions(false);

        nameFont = new BitmapFont(Gdx.files.internal("fonts/ui.fnt"), new TextureRegion(new Texture("fonts/ui.png")));
        nameFont.getData().setScale(0.08f);
        nameFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        nameFont.setUseIntegerPositions(false);

        Label.LabelStyle haeadingStyle = new Label.LabelStyle(font, Color.WHITE);
        heading = new Label("HIGH SCORES", haeadingStyle);

        closeButton = new Button(new TextureRegionDrawable(closeTexture));
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                clearChildren();
                setupTable();
            }
        });

        setupTable();
        setBackground(background);
    }

    private void setupTable() {
        top();
        add(heading).colspan(2);
        top().left();
        add(closeButton).width(10).height(10).padTop(-5).padRight(-5);
        row();
        add().padTop(2);
        row();
    }

    public void setHighScores(HighScoreEntry[] scoreEntries) {
        Table table = new Table();
        Label.LabelStyle labelStyle = new Label.LabelStyle(nameFont, Color.WHITE);

        for(HighScoreEntry entry : scoreEntries) {
            Label name = new Label(entry.getName(), labelStyle);
            Label score = new Label(entry.getScore() + " PTS", labelStyle);
            table.add(name).padRight(10);
            table.add(score).padLeft(10);
            table.row();
        }

        tableCell = add(table).expandX();
    }
}
