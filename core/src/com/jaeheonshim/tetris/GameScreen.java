package com.jaeheonshim.tetris;

import com.badlogic.gdx.Gdx;
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

    public GameScreen() {
        viewport = new FitViewport(700, 700);

        gameScene = new GameScene();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

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
