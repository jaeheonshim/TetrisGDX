package com.jaeheonshim.tetris.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.jaeheonshim.tetris.util.ObfuscatedMemoryInteger;
import com.jaeheonshim.tetris.util.Timer;
import com.jaeheonshim.tetris.util.Util;

import java.util.*;

public class GameState {
    private Random random = new Random();

    private ObfuscatedMemoryInteger level = new ObfuscatedMemoryInteger(0);
    private ObfuscatedMemoryInteger score = new ObfuscatedMemoryInteger(0);
    private ObfuscatedMemoryInteger linesCleared = new ObfuscatedMemoryInteger(0);

    private int width;
    private int height;

    private int currentRotation = 0;

    private BlockState[][] blockStates;
    private BlockType currentDrop;
    private Timer dropTimer = new Timer(Util.getLevelSpeed(level.get()));

    public static final Color[] COLORS = {Color.valueOf("0341AE"), Color.valueOf("72CB3B"), Color.valueOf("FFD500"), Color.valueOf("FF971C"), Color.valueOf("FF3213")};

    public GameState(int width, int height) {
        this.width = width;
        this.height = height;

        blockStates = new BlockState[height][width];
//        blockStates[2][0] = new BlockState(Color.valueOf("0341AE"));
//        blockStates[2][1] = new BlockState(Color.valueOf("0341AE"));
//        blockStates[2][1].setPivot(true);
//        blockStates[1][1] = new BlockState(Color.valueOf("0341AE"));
//        blockStates[2][2] = new BlockState(Color.valueOf("0341AE"));
//        spawnBlock(BlockType.I, Color.GREEN);
    }

    public BlockState[][] getBlockStates() {
        return blockStates;
    }

    public void spawnBlock(BlockType blockType, Color c) {
        currentDrop = blockType;
        switch (blockType) {
            case I: {
                blockStates[0][width / 2 - 2] = new BlockState(c);
                blockStates[0][width / 2 - 1] = new BlockState(c);
                blockStates[0][width / 2] = new BlockState(c).setPivot(true);
                blockStates[0][width / 2 + 1] = new BlockState(c);
                break;
            }
            case J: {
                blockStates[0][width / 2 - 2] = new BlockState(c);
                blockStates[1][width / 2 - 2] = new BlockState(c);
                blockStates[1][width / 2 - 1] = new BlockState(c).setPivot(true);
                blockStates[1][width / 2] = new BlockState(c);
                break;
            }
            case L: {
                blockStates[0][width / 2] = new BlockState(c);
                blockStates[1][width / 2 - 2] = new BlockState(c);
                blockStates[1][width / 2 - 1] = new BlockState(c).setPivot(true);
                blockStates[1][width / 2] = new BlockState(c);
                break;
            }
            case O: {
                blockStates[0][width / 2 - 1] = new BlockState(c);
                blockStates[0][width / 2] = new BlockState(c);
                blockStates[1][width / 2 - 1] = new BlockState(c);
                blockStates[1][width / 2] = new BlockState(c);
                break;
            }
            case S: {
                blockStates[0][width / 2 - 1] = new BlockState(c);
                blockStates[0][width / 2] = new BlockState(c);
                blockStates[1][width / 2 - 2] = new BlockState(c);
                blockStates[1][width / 2 - 1] = new BlockState(c).setPivot(true);
                break;
            }
            case T: {
                blockStates[0][width / 2 - 1] = new BlockState(c);
                blockStates[1][width / 2 - 2] = new BlockState(c);
                blockStates[1][width / 2 - 1] = new BlockState(c).setPivot(true);
                blockStates[1][width / 2] = new BlockState(c);
                break;
            }
            case Z: {
                blockStates[0][width / 2 - 2] = new BlockState(c);
                blockStates[0][width / 2 - 1] = new BlockState(c);
                blockStates[1][width / 2 - 1] = new BlockState(c).setPivot(true);
                blockStates[1][width / 2] = new BlockState(c);
                break;
            }
        }
    }

    public void doGameTick(float delta, boolean drop) {
        if (dropTimer.isReady()) {
            // we want to clear rows and spawn blocks only when the timer goes off, not when the block is "fast-dropped"
            int clearedCount = clearRows();
            if (clearedCount > 0) {
                score.set(score.get() + getScoring(clearedCount));
                int prevCount = linesCleared.get() / 10;
                linesCleared.set(linesCleared.get() + clearedCount);

                if(linesCleared.get() / 10 > prevCount) {
                    advanceLevel();
                }
            } else if (newBlockReady()) {
                spawnBlock(BlockType.values()[random.nextInt(BlockType.values().length)], GameState.COLORS[random.nextInt(GameState.COLORS.length)]);
            } else {
                tickBlocks();
            }
            dropTimer.reset();
        } else if(drop) {
            tickBlocks();
        }

        if (!drop) {
            dropTimer.update(delta);
        }
    }

    private void advanceLevel() {
        level.set(level.get() + 1);
        dropTimer.setValue(Util.getLevelSpeed(level.get()));
    }

    public void tickBlocks() {
        BlockState[][] updated = new BlockState[height][width];

        for (int i = blockStates.length - 1; i >= 0; i--) {
            for (int j = blockStates[i].length - 1; j >= 0; j--) {
                BlockState blockState = blockStates[i][j];
                if (blockState != null && !blockState.isFixed() && i + 1 < blockStates.length) {
                    updated[i + 1][j] = blockState;
                } else if (blockState != null && i + 1 >= blockStates.length && !blockState.isFixed()) {
                    fixMoving();
                    applyUpdated(updated);
                    return;
                }
            }
        }

        if (isValidUpdate(updated)) {
            applyUpdated(updated);
        } else {
            fixMoving();
        }
    }

    public void translateMoving(int x) {
        BlockState[][] updatedState = new BlockState[blockStates.length][blockStates[0].length];

        for (int i = blockStates.length - 1; i >= 0; i--) {
            for (int j = blockStates[i].length - 1; j >= 0; j--) {
                BlockState blockState = blockStates[i][j];
                if (blockState != null && !blockState.isFixed()) {
                    if (j + x < width && j + x >= 0) {
                        updatedState[i][j + x] = blockState;
                    } else {
                        return;
                    }
                }
            }
        }

        if (isValidUpdate(updatedState))
            applyUpdated(updatedState);
    }

    public void rotateMoving() {
        Vector2 rotationPoint = getRotationPoint();

        if (rotationPoint == null) {
            return;
        }

        BlockState[][] updatedStates = new BlockState[height][width];
        Map<Vector2, BlockState> transformedStates = new HashMap<>();

        for (int i = 0; i < blockStates.length; i++) {
            for (int j = 0; j < blockStates[i].length; j++) {
                if (blockStates[i][j] != null && !blockStates[i][j].isFixed()) {
                    Vector2 originRelative = new Vector2(j, i).sub(rotationPoint);
                    Vector2 transformed = new Vector2(originRelative.y * -1, originRelative.x).add(rotationPoint);

                    transformedStates.put(transformed, blockStates[i][j]);
                } else if (blockStates[i][j] != null) {
                    updatedStates[i][j] = blockStates[i][j];
                }
            }
        }

        boolean transformSuccess = false;
        Vector2[] kickDataSet = getKickDataSet();
        for (int k = 0; k < kickDataSet.length; k++) {
            if (validVectorSet(transformSet(transformedStates.keySet(), kickDataSet[k], false))) {
                transformSet(transformedStates.keySet(), kickDataSet[k], true);
                applyTransformMap(updatedStates, transformedStates);
                transformSuccess = true;
                break;
            }
        }

        if (transformSuccess) {
            if (isValidUpdate(updatedStates))
                applyUpdated(updatedStates);
            currentRotation++;
        }
    }

    public boolean newBlockReady() {
        for (int i = 0; i < blockStates.length; i++) {
            for (int j = 0; j < blockStates[i].length; j++) {
                if (blockStates[i][j] != null && !blockStates[i][j].isFixed()) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isValidUpdate(BlockState[][] update) {
        for (int i = 0; i < blockStates.length; i++) {
            for (int j = 0; j < blockStates[j].length; j++) {
                if (update[i][j] != null && !update[i][j].isFixed() && blockStates[i][j] != null && blockStates[i][j].isFixed()) {
                    return false;
                }
            }
        }

        return true;
    }

    private void applyUpdated(BlockState[][] updated) {
        for (int i = 0; i < updated.length; i++) {
            for (int j = 0; j < updated[i].length; j++) {
                if (blockStates[i][j] != null && !blockStates[i][j].isFixed()) {
                    blockStates[i][j] = null;
                }

                if (updated[i][j] != null && !updated[i][j].isFixed()) {
                    blockStates[i][j] = updated[i][j];
                }
            }
        }
    }

    private void applyTransformMap(BlockState[][] array, Map<Vector2, BlockState> transformMap) {
        for (Map.Entry<Vector2, BlockState> entry : transformMap.entrySet()) {
            array[(int) entry.getKey().y][(int) entry.getKey().x] = entry.getValue();
        }
    }

    private Collection<Vector2> transformSet(Collection<Vector2> vector2s, Vector2 transformation, boolean mutate) {
        List<Vector2> trans = new ArrayList<>();
        for (Vector2 vector2 : vector2s) {
            if (mutate) {
                trans.add(vector2.add(transformation));
            } else {
                trans.add(new Vector2(vector2).add(transformation));
            }
        }

        return trans;
    }

    private boolean validVectorSet(Collection<Vector2> vector2s) {
        for (Vector2 vector2 : vector2s) {
            if (vector2.x < 0 || vector2.x >= width) {
                return false;
            } else if (vector2.y < 0 || vector2.y >= height) {
                return false;
            }
        }

        return true;
    }

    private Vector2[] getKickDataSet() {
        if (currentDrop == BlockType.I) {
            if (currentRotation % 4 == 0) {
                return KickData.I[0];
            } else if (currentRotation % 4 == 1) {
                return KickData.I[2];
            } else if (currentRotation % 4 == 2) {
                return KickData.I[4];
            } else {
                return KickData.I[6];
            }
        } else {
            if (currentRotation % 4 == 0) {
                return KickData.JLSTZ[0];
            } else if (currentRotation % 4 == 2) {
                return KickData.JLSTZ[3];
            } else if (currentRotation % 4 == 3) {
                return KickData.JLSTZ[6];
            } else {
                return KickData.JLSTZ[2];
            }
        }
    }

    private Vector2 getRotationPoint() {
        for (int i = 0; i < blockStates.length; i++) {
            for (int j = 0; j < blockStates[i].length; j++) {
                if (blockStates[i][j] != null && !blockStates[i][j].isFixed() && blockStates[i][j].isPivot()) {
                    return new Vector2(j, i);
                }
            }
        }

        return null;
    }

    private void fixMoving() {
        for (int i = 0; i < blockStates.length; i++) {
            for (int j = 0; j < blockStates[i].length; j++) {
                BlockState blockState = blockStates[i][j];
                if (blockState != null) {
                    blockState.setFixed(true);
                }
            }
        }
    }

    public int clearRows() {
        boolean cleared = false;
        int clearedCount = 0;

        rowLoop:
        for (int i = height - 1; i >= 0; i--) {
            for (int j = 0; j < width; j++) {
                if (blockStates[i][j] == null || !blockStates[i][j].isFixed()) {
                    continue rowLoop;
                }
            }

            cleared = true;
            for (int j = 0; j < width; j++) {
                blockStates[i][j] = null;
            }
            for (int j = i; j > 0; j--) {
                for (int k = 0; k < width; k++) {
                    blockStates[j][k] = blockStates[j - 1][k];
                }
            }
            clearedCount++;
            i++;
        }

        return clearedCount;
    }

    private int getScoring(int cleared) {
        if (cleared == 1) {
            return 40 * (level.get() + 1);
        } else if (cleared == 2) {
            return 100 * (level.get() + 1);
        } else if (cleared == 3) {
            return 300 * (level.get() + 1);
        } else {
            return 1200 * (level.get() - 1);
        }
    }

    public ObfuscatedMemoryInteger getLevel() {
        return level;
    }

    public ObfuscatedMemoryInteger getScore() {
        return score;
    }

    public ObfuscatedMemoryInteger getLinesCleared() {
        return linesCleared;
    }
}
