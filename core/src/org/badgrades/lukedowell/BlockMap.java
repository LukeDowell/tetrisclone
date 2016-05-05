package org.badgrades.lukedowell;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ldowell on 5/5/16.
 */
public class BlockMap {

    /** The width and height of the tetris game area */
    public static final int MAP_WIDTH = 10;
    public static final int MAP_HEIGHT = 20;

    /** The 2d grid that represents the game map */
    private int[][] map;

    /** A list of upcoming blocks */
    private LinkedList<Block> blockQueue;

    /** A list of blocks currently on the grid */
    private LinkedList<Block> activeBlocks;

    /** The block that is controlled by the player */
    private Block playerBlock;

    public BlockMap() {
        map = new int[MAP_HEIGHT][MAP_WIDTH];
        activeBlocks = new LinkedList<>();
    }

    /**
     * Checks to see if this block will collide with any other block
     * on the next gravity tick
     *
     * @param b
     * @return
     */
    public boolean canBlockDrop(Block b) {
        return true;
    }

    /**
     * Gets the block being controlled by the player
     * @return
     */
    public Block getPlayerBlock() {
        return playerBlock;
    }

    /**
     *
     * @return
     */
    public List<Block> getActiveBlocks() {
        return activeBlocks;
    }

    public void spawnBlock(Block b) {
        // Randomly rotate block
        // Pick random location at the top of the map within bounds of block
        int numRotations = (int) Math.floor((Math.random() * 10) + 1);
        for(int i = 0; i < numRotations; i++) {
            b.rotateClockwise();
        }

        Point spawnPoint = new Point(6, 20);
        b.setPosition(spawnPoint);
        playerBlock = b;
    }
}
