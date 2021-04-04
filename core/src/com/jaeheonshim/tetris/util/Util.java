package com.jaeheonshim.tetris.util;

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

    public static float getLevelSpeed(int level) {
        if(level >= 0 && level <= 8) {
            return (48 - (level * 5)) / 60f;
        } else if(level == 9) {
            return 6 / 60f;
        } else if(level >= 10 && level <= 12) {
            return 5 / 60f;
        } else if(level >= 13 && level <= 15) {
            return 4 / 60f;
        } else if(level >= 16 && level <= 18) {
            return 3 / 60f;
        } else if(level >= 19 && level <= 28) {
            return 2 / 60f;
        } else {
            return 1 / 60f;
        }
    }
}
