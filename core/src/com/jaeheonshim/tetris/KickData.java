package com.jaeheonshim.tetris;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class KickData {
    public static Vector2[][] kickData = {
            {v(0, 0), v(-1, 0), v(-1, 1), v(0, -2), v(-1, -2)},
            {v(0, 0), v(1, 0), v(1, -1), v(0, 2), v(1, 2)},
            {v(0, 0), v(1, 0), v(1, -1), v(0, 2), v(1, 2)},
            {v(0, 0), v(-1, 0), v(-1, 1), v(0, -2), v(-1, -2)},
            {v(0, 0), v(1, 0), v(1, 1), v(0, -2), v(1, -2)},
            {v(0, 0), v(-1, 0), v(-1, -1), v(0, 2), v(-1, 2)},
            {v(0, 0), v(-1, 0), v(-1, -1), v(0, 2), v(-1, 2)},
            {v(0, 0), v(1, 0), v(1, 1), v(0, -2), v(1, -2)}
    };

    private static Vector2 v(int x, int y) {
        return new Vector2(x, -y);
    }
}
