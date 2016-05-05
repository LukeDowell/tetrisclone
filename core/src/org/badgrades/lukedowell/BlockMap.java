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
    public boolean canBlockMove(Block b, Point p) {
        // The block CANNOT drop if:
        // 1. A filled portion of the bounding box has reached a wall
        // 2. A filled portion of the bounding box will run into an existing block

        // Does it matter if these points are negative?
        // TODO this is probably broken
        Point diffPos = new Point(
                b.getPosition().x - p.x,
                b.getPosition().y - p.y);

        for(Point blockPos : b.getFilledPoints()) {
            int mapX = blockPos.x - diffPos.x;
            int mapY = blockPos.y - diffPos.y;

            if(mapX > MAP_WIDTH || mapX < 0 || mapY < 0 || mapY > MAP_HEIGHT) {
                return false;
            }

            if(map[mapX][mapY] > 0) {
                return false;
            }
        }

        return true;
    }

    public void placePlayerBlock() {
        for(Point blockPos : playerBlock.getFilledPoints()) {
            map[blockPos.x][blockPos.y] = 1;
        }

        activeBlocks.add(playerBlock);
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
        Point spawnPoint = new Point(5, 16);
        b.setPosition(spawnPoint);
        playerBlock = b;
    }
}
