package com.jaeheonshim.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.jaeheonshim.tetris.game.BlockType;
import com.jaeheonshim.tetris.game.GameState;

import java.util.Random;

public class GameScreen implements Screen {
    private Viewport viewport;
    private GameScene gameScene;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private Random random = new Random();

    private float blockUpdateTimer = 1;

    private DelayedIntervalKeypressListener leftListener = new DelayedIntervalKeypressListener(Input.Keys.LEFT, 0.1f);
    private DelayedIntervalKeypressListener rightListener = new DelayedIntervalKeypressListener(Input.Keys.RIGHT, 0.1f);
    private DelayedIntervalKeypressListener upListener = new DelayedIntervalKeypressListener(Input.Keys.UP, 0.3f);
    private DelayedIntervalKeypressListener downListener = new DelayedIntervalKeypressListener(Input.Keys.DOWN, 0.07f);

    public GameScreen() {
        viewport = new FitViewport(700, 900);

        gameScene = new GameScene();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
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
        if(blockUpdateTimer <= 0 || downListener.isPressed()) {
            if(gameScene.getGameState().newBlockReady()) {
                gameScene.getGameState().spawnBlock(BlockType.values()[random.nextInt(BlockType.values().length)], GameState.COLORS[random.nextInt(GameState.COLORS.length)]);
            } else {
                gameScene.getGameState().tickBlocks();
            }
            blockUpdateTimer = 1;
        }

        if(upListener.isPressed()) {
            gameScene.getGameState().rotateMoving();
        }

        if(leftListener.isPressed()) {
            gameScene.getGameState().translateMoving(-1);
        }

        if(rightListener.isPressed()) {
            gameScene.getGameState().translateMoving(1);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        spriteBatch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        spriteBatch.begin();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        gameScene.draw(
                Util.getViewportCenterCoordinateX(viewport, gameScene.getInnerGameWidthDimension()),
                Util.getViewportCenterCoordinateY(viewport, gameScene.getInnerGameHeightDimension()),
                spriteBatch,
                shapeRenderer
        );

        spriteBatch.end();
        shapeRenderer.end();
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
