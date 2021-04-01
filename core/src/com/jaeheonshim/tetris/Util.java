package com.jaeheonshim.tetris;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Util {
    public static float getViewportCenterCoordinateX(Viewport viewport, float objectWidth) {
        return (viewport.getWorldWidth() / 2) - (objectWidth / 2);
    }

    public static float getViewportCenterCoordinateY(Viewport viewport, float objectHeight) {
        return (viewport.getWorldHeight() / 2) - (objectHeight / 2);
    }

    public static Vector2 transformBlockCoordsToPointCoords(Vector2 blockCoords) {
        if(blockCoords.x < 0) {
            if(blockCoords.y < 0) {
                return blockCoords;
            } else {
                return blockCoords.sub(0, 1);
            }
        } else {
            if(blockCoords.y < 0) {
                return blockCoords.sub(1, 0);
            } else {
                return blockCoords.sub(1, 1);
            }
        }
    }

    public static Vector2 transformPointCoordsToBlockCoords(Vector2 blockCoords) {
        if(blockCoords.x < 0) {
            if(blockCoords.y < 0) {
                return blockCoords;
            } else {
                return blockCoords.add(0, 1);
            }
        } else {
            if(blockCoords.y < 0) {
                return blockCoords.add(1, 0);
            } else {
                return blockCoords.add(1, 1);
            }
        }
    }
}
