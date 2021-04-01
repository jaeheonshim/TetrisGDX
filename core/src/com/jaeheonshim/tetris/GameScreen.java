package com.jaeheonshim.tetris;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen implements Screen {
    private Viewport viewport;
    private GameScene gameScene;

    private SpriteBatch spriteBatch;
    private ShapeRenderer shapeRenderer;

    private float blockUpdateTimer = 1;

    public GameScreen() {
        viewport = new FitViewport(700, 700);

        gameScene = new GameScene();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    public void update(float delta) {
        blockUpdateTimer -= delta;
        if(blockUpdateTimer <= 0) {
            blockUpdateTimer = 1;
            gameScene.getGameState().tickBlocks();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            gameScene.getGameState().rotateMoving();
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            gameScene.getGameState().translateMoving(-1);
        }

        if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
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
