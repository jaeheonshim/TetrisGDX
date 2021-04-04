package com.jaeheonshim.tetris.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.tetris.util.DelayedIntervalKeypressListener;
import com.jaeheonshim.tetris.util.Util;
import com.jaeheonshim.tetris.game.BlockType;
import com.jaeheonshim.tetris.game.GameState;

import java.util.Random;

public class GameScreen implements Screen {
    private Viewport viewport;
    private Viewport backgroundViewport;
    private GameScene gameScene;
    private GameDetailPanel gameDetailPanel;
    private LinesClearedPanel linesClearedPanel;

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

    public GameScreen() {
        viewport = new FitViewport(900, 900);
        backgroundViewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        gameScene = new GameScene();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        gameDetailPanel = new GameDetailPanel();
        linesClearedPanel = new LinesClearedPanel();
        linesClearedPanel.setWidth(gameScene.getInnerGameWidthDimension());
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        leftListener.update(delta);
        rightListener.update(delta);
        upListener.update(delta);
        downListener.update(delta);

        blockUpdateTimer -= delta;
        gameScene.getGameState().doGameTick(delta, downListener.isPressed());
        gameDetailPanel.setScore(gameScene.getGameState().getScore().get());
        linesClearedPanel.setLinesCleared(gameScene.getGameState().getLinesCleared().get());

        if (upListener.isPressed()) {
            gameScene.getGameState().rotateMoving();
        }

        if (leftListener.isPressed()) {
            gameScene.getGameState().translateMoving(-1);
        }

        if (rightListener.isPressed()) {
            gameScene.getGameState().translateMoving(1);
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

    @Override
    public void resize(int width, int height) {
        backgroundViewport.update(width, height, true);
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
        spriteBatch.dispose();
        shapeRenderer.dispose();
        backgroundTexture.dispose();
    }
}
