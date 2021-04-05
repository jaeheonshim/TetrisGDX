package com.jaeheonshim.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.jaeheonshim.tetris.TetrisGame;
import com.jaeheonshim.tetris.screens.game.GameScreen;

public class MusicPreferenceScreen implements Screen {
    private TetrisGame tetrisGame;

    private Stage stage;
    private Table table;

    private Texture soundIcon = new Texture("ui/sound_icon.png");
    private Texture soundOffIcon = new Texture("ui/sound_off_icon.png");

    public MusicPreferenceScreen(final TetrisGame tetrisGame) {
        this.tetrisGame = tetrisGame;

        final Preferences preferences = Gdx.app.getPreferences(TetrisGame.PREFERENCES);

        stage = new Stage(new ExtendViewport(1000, 1000));
        table = new Table();
        table.setFillParent(true);

        Gdx.input.setInputProcessor(stage);

        Button musicOnButton = new Button(new TextureRegionDrawable(soundIcon));
        Button musicOffButton = new Button(new TextureRegionDrawable(soundOffIcon));

        table.add(musicOnButton).padRight(20);
        table.add(musicOffButton).padLeft(20);

        musicOnButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                preferences.putInteger("soundOn", 1);
                preferences.flush();
                tetrisGame.setScreen(new GameScreen(tetrisGame));
            }
        });

        musicOffButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                preferences.putInteger("soundOn", 0);
                preferences.flush();
                tetrisGame.setScreen(new GameScreen(tetrisGame));
            }
        });

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
        soundIcon.dispose();
        soundOffIcon.dispose();
        stage.dispose();
    }
}
