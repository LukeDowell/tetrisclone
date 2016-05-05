package org.badgrades.lukedowell;

import com.badlogic.gdx.graphics.Color;

/**
 * Enumerated list of all the different block types this game supports
 * and their associate color. Shape arrays are set to match those in the
 * SRS from Tetris
 *
 * http://tetris.wikia.com/wiki/SRS
 */
public enum BlockType {

    I(new int[][] {
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0}
    }, Color.GREEN),

    J(new int[][] {
            {0, 1, 0},
            {0, 1, 0},
            {1, 1, 0},
    }, Color.BLACK),

    L(new int[][] {
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 1},
    }, Color.BLUE),

    O(new int[][]{
            {1, 1},
            {1, 1}
    }, Color.YELLOW),

    S(new int[][]{
            {0, 0, 0},
            {0, 1, 1},
            {1, 1, 0}
    }, Color.ORANGE),

    T(new int[][]{
            {0, 0, 0},
            {1, 1, 1},
            {0, 1, 0}
    }, Color.RED),

    Z(new int[][]{
            {1, 1, 0},
            {0, 1, 1},
            {0, 0, 0}
    }, Color.MAGENTA);

    /**
     * A 2d array that contains the current shape of this block. With 1 representing a block and 0 representing
     * empty space, an L block could look like
     *
     * [0, 0, 1]
     * [1, 1, 1]
     * [0, 0, 0]
     *
     * but also the following after being rotated:
     *
     * [0, 1, 0]
     * [0, 1, 0]
     * [0, 1, 1]
     *
     */
    int[][] shape;

    /** The color the block will be rendered at */
    Color color;

    BlockType(int[][] shape, Color color) {
        this.shape = shape;
        this.color = color;
    }

}