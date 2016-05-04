package org.badgrades.lukedowell;

import com.badlogic.gdx.graphics.Color;

import java.awt.*;

/**
 * Created by ldowell on 5/4/16.
 */
public class Block {


    /**
     * Stores the X+Y coordinates for this block, using shape[0][0] as
     * an origin point.
     */
    private Point position;

    private BlockType type;

    public Block(BlockType type) {
        this.type = type;
    }

    /**
     * Returns an array that represents what this block would look like if it was
     * rotated clockwise by 90 degrees
     */
    public void rotateCW() {
        final int M = this.type.shape.length;
        final int N = this.type.shape[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = this.type.shape[r][c];
            }
        }
        this.type.shape = ret;
    }

    public BlockType getType() {
        return type;
    }

    public void setPosition(Point point) {
        this.position = point;
    }

    public Point getPosition() {
        return this.position;
    }

    /**
     * Enumerated list of all the different block types this game supports
     * and their associate color
     */
    public enum BlockType {

        I(new int[][] {
                {1},
                {1},
                {1},
                {1}
        }, Color.GREEN),

        J(new int[][] {
                {0, 1},
                {0, 1},
                {1, 1},
        }, Color.BLACK),

        L(new int[][] {
                {1, 0},
                {1, 0},
                {1, 1},
        }, Color.BLUE),

        O(new int[][]{
                {1, 1},
                {1, 1}
        }, Color.YELLOW),

        S(new int[][]{
                {0, 1, 1},
                {1, 1, 0}
        }, Color.ORANGE),

        T(new int[][]{
                {1, 1, 1},
                {0, 1, 0},
                {0, 1, 0}
        }, Color.RED),

        Z(new int[][]{
                {1, 1, 0},
                {0, 1, 1}
        }, Color.MAGENTA);

        /**
         * A 2d array that contains the current shape of this block. With 1 representing a block and 0 representing
         * empty space, an L block could look like
         *
         * [0, 0, 1]
         * [1, 1, 1]
         *
         * but also the following after being rotated:
         *
         * [1, 0]
         * [1, 0]
         * [1, 1]
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
}
