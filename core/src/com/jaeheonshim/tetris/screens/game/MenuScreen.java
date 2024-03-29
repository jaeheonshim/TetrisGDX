package com.jaeheonshim.tetris.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.tetris.TetrisGame;
import com.jaeheonshim.tetris.util.HighScoreEntry;
import com.jaeheonshim.tetris.util.HighScoreSender;
import com.jaeheonshim.tetris.widgets.HighScoresWidget;

import java.util.function.Consumer;

public class MenuScreen implements Screen {
    private final HighScoresWidget highScoresWidget;
    private TetrisGame tetrisGame;

    private Viewport viewport;
    private Texture backgroundTexture = new Texture("backgroundtile.png");
    private Texture buttonDown = new Texture("ui/button_down.png");
    private Texture buttonUp = new Texture("ui/button_up.png");

    private Texture tetris = new Texture("tetris.png");

    private float backgroundScaling = 0.1f;

    private BitmapFont titleFont;
    private BitmapFont font;

    private SpriteBatch spriteBatch;
    private Stage stage;

    private final Stack stack;

    public MenuScreen(final TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;
        titleFont = new BitmapFont(Gdx.files.internal("fonts/tetris_outline.fnt"));
        titleFont.getData().setScale(0.05f);
        titleFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font = new BitmapFont(Gdx.files.internal("fonts/tetris.fnt"));
        font.getData().setScale(0.09f);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        viewport = new ExtendViewport(100, 100);
        stage = new Stage(viewport);
        spriteBatch = new SpriteBatch();

        Gdx.input.setInputProcessor(stage);

        Image logo = new Image(tetris);
        logo.setScaling(Scaling.fit);

        stack = new Stack();
        stack.setFillParent(true);

        Table table = new Table();
        table.setFillParent(true);
        table.padTop(10);
        //table.setDebug(true);

        table.top();
        table.add(logo).height(30);

        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle(new TextureRegionDrawable(buttonUp), new TextureRegionDrawable(buttonDown), new TextureRegionDrawable(buttonUp), font);

        TextButton playButton = new TextButton("PLAY", buttonStyle);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                tetrisGame.setScreen(new GameScreen(tetrisGame));
            }
        });

        TextButton highScores = new TextButton("HIGH SCORES", buttonStyle);
        highScores.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                displayHighScores();
            }
        });

        table.row();
        table.add(playButton).padTop(10).width(60);
        table.row();
        table.add(highScores).padTop(2).width(60);

        stack.addActor(table);

        highScoresWidget = new HighScoresWidget();
        highScoresWidget.setVisible(false);
        Container highScoresContainer = new Container(highScoresWidget);
        highScoresContainer.width(100);
        highScoresContainer.height(80);

        stack.add(highScoresContainer);

        stage.addActor(stack);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        spriteBatch.begin();

        renderBackgroundTiles(spriteBatch);

        spriteBatch.end();

        stage.act(delta);
        stage.draw();
    }

    private void renderBackgroundTiles(SpriteBatch spriteBatch) {
        spriteBatch.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < viewport.getWorldWidth() / (backgroundTexture.getWidth() * backgroundScaling); i++) {
            for (int j = 0; j < viewport.getWorldHeight() / (backgroundTexture.getHeight() * backgroundScaling); j++) {
                spriteBatch.draw(backgroundTexture,
                        i * backgroundTexture.getWidth() * backgroundScaling,
                        j * backgroundTexture.getHeight() * backgroundScaling,
                        backgroundTexture.getWidth() / 2f * backgroundScaling,
                        backgroundTexture.getHeight() / 2f * backgroundScaling,
                        backgroundTexture.getWidth() * backgroundScaling,
                        backgroundTexture.getHeight() * backgroundScaling,
                        1,
                        1,
                        0,
                        0, 0,
                        backgroundTexture.getWidth(),
                        backgroundTexture.getHeight(),
                        false, false
                );
            }
        }
    }

    private void displayHighScores() {
        HighScoreSender.getScores(new Consumer<HighScoreEntry[]>() {
            @Override
            public void accept(HighScoreEntry[] scoreEntries) {
                highScoresWidget.setHighScores(scoreEntries);
                highScoresWidget.setVisible(true);
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
