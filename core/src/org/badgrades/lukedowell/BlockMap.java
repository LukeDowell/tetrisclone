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
        map = new int[MAP_WIDTH][MAP_HEIGHT];
        activeBlocks = new LinkedList<>();
        blockQueue = new LinkedList<>();

        // init queue
        for(int i = 0; i < 30; i++) {
            int randomTypeIndex = (int) Math.floor(Math.random() * BlockType.values().length);
            blockQueue.add(new Block(BlockType.values()[randomTypeIndex]));
        }
    }

    /**
     * Checks to see if this block will collide with any other block
     * on the next gravity tick
     *
     * @param b
     * @return
     */
    public boolean canBlockDrop(Block b) {
        // The block CANNOT drop if:
        // 1. A filled portion of the bounding box has reached a wall
        // 2. A filled portion of the bounding box will run into an existing block

        for(Point blockPos : b.getFilledPoints()) {
            if(blockPos.y - 1 < 0) {
                return false;
            }

            if(blockPos.y <= MAP_HEIGHT && map[blockPos.x][blockPos.y] == 1) {
                return false;
            }
        }

        return true;
    }

    public void placePlayerBlock() {
        for(Point blockPos : playerBlock.getFilledPoints()) {
            map[blockPos.x][blockPos.y] = 1;
        }

        playerBlock = null;
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

    public void spawnBlock() {
        this.spawnBlock(blockQueue.pop());
    }

    public void spawnBlock(Block b) {
        // Randomly rotate block
        // Pick random location at the top of the map within bounds of block
        int numRotations = (int) Math.floor((Math.random() * 10) + 1);
        for(int i = 0; i < numRotations; i++) {
            b.rotateClockwise();
        }

        Point spawnPoint = new Point(5, 15);
        b.setPosition(spawnPoint);
        playerBlock = b;
    }


}
