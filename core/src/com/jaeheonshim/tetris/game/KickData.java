package com.jaeheonshim.tetris.game;

import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

public class KickData {
    public static Vector2[][] JLSTZ = {
            {v(0, 0), v(-1, 0), v(-1, 1), v(0, -2), v(-1, -2)},
            {v(0, 0), v(1, 0), v(1, -1), v(0, 2), v(1, 2)},
            {v(0, 0), v(1, 0), v(1, -1), v(0, 2), v(1, 2)},
            {v(0, 0), v(-1, 0), v(-1, 1), v(0, -2), v(-1, -2)},
            {v(0, 0), v(1, 0), v(1, 1), v(0, -2), v(1, -2)},
            {v(0, 0), v(-1, 0), v(-1, -1), v(0, 2), v(-1, 2)},
            {v(0, 0), v(-1, 0), v(-1, -1), v(0, 2), v(-1, 2)},
            {v(0, 0), v(1, 0), v(2, 1), v(0, -2), v(1, -2)}
    };

    public static Vector2[][] I = {
            {v(0, 0), v(-2, 0), v(1, 0), v(-2, -1), v(1, 2)}, // 0 >> 1
            {v(0, 0), v(2, 0), v(-1, 0), v(2, 1), v(-1, -2)},
            {v(0, 0), v(1, 0), v(-2, 0), v(-1, 2), v(2, -1)}, // 1 >> 2
            {v(0, 0), v(1, 0), v(-2, 0), v(1, -2), v(-2, 1)}, // 2 >> 1
            {v(0, 0), v(2, 0), v(-1, 0), v(2, 1), v(-1, -1)},
            {v(0, 0), v(-2, 0), v(1, 0), v(-2, -1), v(1, 2)},
            {v(0, 0), v(2, 0), v(-1, 0), v(1, -2), v(-2, 1)}, // 3 >> 0
            {v(0, 0), v(-1, 0), v(2, 0), v(-1, 2), v(2, 1)}
    };

    private static Vector2 v(int x, int y) {
        return new Vector2(x, -y);
    }
}
