package com.jaeheonshim.tetris;

import com.badlogic.gdx.utils.viewport.Viewport;

public class Util {
    public static float getViewportCenterCoordinateX(Viewport viewport, float objectWidth) {
        return (viewport.getWorldWidth() / 2) - (objectWidth / 2);
    }

    public static float getViewportCenterCoordinateY(Viewport viewport, float objectHeight) {
        return (viewport.getWorldHeight() / 2) - (objectHeight / 2);
    }
}
