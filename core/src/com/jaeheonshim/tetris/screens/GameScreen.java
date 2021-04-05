package com.jaeheonshim.tetris.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.tetris.TetrisGame;
import com.jaeheonshim.tetris.util.DelayedIntervalKeypressListener;
import com.jaeheonshim.tetris.util.Util;
import com.jaeheonshim.tetris.game.BlockType;
import com.jaeheonshim.tetris.game.GameState;
import com.jaeheonshim.tetris.widgets.GameOverWidget;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class GameScreen implements Screen {
    private final TetrisGame tetrisGame;

    private Stage stage;
    private Table table;

    private Viewport viewport;
    private Viewport backgroundViewport;
    private GameScene gameScene;
    private GameDetailPanel gameDetailPanel;
    private LinesClearedPanel linesClearedPanel;
    private LevelPanel levelPanel;
    private NextDropPanel nextDropPanel;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Random random = new Random();

    private float blockUpdateTimer = 1;

    private Texture backgroundTexture = new Texture("backgroundtile.png");
    private float backgroundScaling = 0.1f;

    private DelayedIntervalKeypressListener leftListener = new DelayedIntervalKeypressListener(Input.Keys.LEFT, 0.1f);
    private DelayedIntervalKeypressListener rightListener = new DelayedIntervalKeypressListener(Input.Keys.RIGHT, 0.1f);
    private DelayedIntervalKeypressListener upListener = new DelayedIntervalKeypressListener(Input.Keys.UP, 0.3f);
    private DelayedIntervalKeypressListener downListener = new DelayedIntervalKeypressListener(Input.Keys.DOWN, 0.07f);

    private Music music = Gdx.audio.newMusic(Gdx.files.internal("audio/tetris.mp3"));

    private Queue<Integer> keypressQueue = new LinkedList<>();
    private Integer[] konami = {Input.Keys.UP, Input.Keys.UP, Input.Keys.DOWN, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.LEFT, Input.Keys.RIGHT, Input.Keys.B, Input.Keys.A};
    private boolean konamiActivated = false;

    private boolean gameOver = false;

    public GameScreen(TetrisGame game) {
        this.tetrisGame = game;

        viewport = new FitViewport(900, 900);
        backgroundViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameScene = new GameScene();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        gameDetailPanel = new GameDetailPanel();
        linesClearedPanel = new LinesClearedPanel();
        linesClearedPanel.setWidth(gameScene.getInnerGameWidthDimension());
        levelPanel = new LevelPanel();
        nextDropPanel = new NextDropPanel();

        music.setVolume(0.6f);
        music.setLooping(true);

        stage = new Stage(new ExtendViewport(100, 100));
        Gdx.input.setInputProcessor(stage);

        table = new Table();
        table.setFillParent(true);
        //table.add(new GameOverWidget(99, 99, 99)).width(100);

        stage.addActor(table);
    }

    @Override
    public void show() {
        music.play();
    }

    public void update(float delta) {
        leftListener.update(delta);
        rightListener.update(delta);
        upListener.update(delta);
        downListener.update(delta);

        blockUpdateTimer -= delta;

        GameState gameState = gameScene.getGameState();
        nextDropPanel.setBlockType(gameState.getNextDrop());

        gameState.doGameTick(delta, downListener.isPressed());
        gameDetailPanel.setScore(gameState.getScore().get());
        linesClearedPanel.setLinesCleared(gameState.getLinesCleared().get());
        levelPanel.setLevel(gameState.getLevel().get());

        if(gameState.isGameOver() && !gameOver) {
            GameOverWidget gameOverWidget = new GameOverWidget(gameState.getLevel().get(), gameState.getScore().get(), gameState.getLinesCleared().get());
            gameOverWidget.getNewGameButton().addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    dispose();
                    tetrisGame.setScreen(new GameScreen(tetrisGame));
                }
            });

            table.add(gameOverWidget).width(100);

            gameOver = true;
        }

        if (upListener.isPressed()) {
            gameState.rotateMoving();
        }

        if (leftListener.isPressed()) {
            gameState.translateMoving(-1);
        }

        if (rightListener.isPressed()) {
            gameState.translateMoving(1);
        }

        for(int key : konami) {
            if(Gdx.input.isKeyJustPressed(key)) {
                registerKeypress(key);
                break;
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        spriteBatch.setProjectionMatrix(backgroundViewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        spriteBatch.setColor(Color.WHITE);
        backgroundViewport.apply();
        renderBackgroundTiles(spriteBatch);
        spriteBatch.end();

        viewport.apply();
        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);

        float gameCenterCoordinateX = Util.getViewportCenterCoordinateX(viewport, gameScene.getInnerGameWidthDimension());
        float gameCenterCoordinateY = Util.getViewportCenterCoordinateY(viewport, gameScene.getInnerGameHeightDimension());

        gameScene.draw(
                gameCenterCoordinateX,
                gameCenterCoordinateY,
                spriteBatch,
                shapeRenderer
        );

        gameDetailPanel.draw(gameCenterCoordinateX + gameScene.getInnerGameWidthDimension() + 20, gameCenterCoordinateY + gameScene.getInnerGameHeightDimension() - gameDetailPanel.getHeight(), spriteBatch);
        linesClearedPanel.draw(gameCenterCoordinateX, gameCenterCoordinateY + gameScene.getInnerGameHeightDimension() + 10, spriteBatch);
        nextDropPanel.draw(gameCenterCoordinateX + gameScene.getInnerGameWidthDimension() + 20, gameCenterCoordinateY + gameScene.getInnerGameHeightDimension() - gameDetailPanel.getHeight() - nextDropPanel.getHeight() - 60, spriteBatch);
        levelPanel.draw(gameCenterCoordinateX + gameScene.getInnerGameWidthDimension() + 20, gameCenterCoordinateY + gameScene.getInnerGameHeightDimension() - gameDetailPanel.getHeight() - nextDropPanel.getHeight() - levelPanel.getHeight() - 70, spriteBatch);

        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    private void renderBackgroundTiles(SpriteBatch spriteBatch) {
        spriteBatch.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < backgroundViewport.getWorldWidth() / (backgroundTexture.getWidth() * backgroundScaling); i++) {
            for (int j = 0; j < backgroundViewport.getWorldHeight() / (backgroundTexture.getHeight() * backgroundScaling); j++) {
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

    private void registerKeypress(int key) {
        keypressQueue.add(key);
        if(keypressQueue.size() > 10) {
            keypressQueue.poll();
        }

        if(Arrays.equals(keypressQueue.toArray(), konami) && !konamiActivated) {
            activateCoolTheme();
        }
    }

    private void activateCoolTheme() {
        music.stop();
        music = Gdx.audio.newMusic(Gdx.files.internal("audio/cooltheme.mp3"));
        music.setVolume(0.6f);
        music.setLooping(true);
        music.play();
        konamiActivated = true;

        backgroundTexture = new Texture("coolbackgroundtile.png");
    }

    @Override
    public void resize(int width, int height) {
        backgroundViewport.update(width, height, true);
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
A
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
        stage.dispose();
        music.dispose();
    }
}
