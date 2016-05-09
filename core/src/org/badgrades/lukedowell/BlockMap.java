package org.badgrades.lukedowell;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ldowell on 5/5/16.
 */
public class BlockMap {

    /** The width and height of the tetris game area */
    public static final int MAP_WIDTH = 10;
    public static final int MAP_HEIGHT = 20;

    /** A list of upcoming blocks */
    private LinkedList<Block> blockQueue;

    /** A map of blocks, their positions are the keys */
    private LinkedList<Block> activeBlocks;

    public BlockMap() {
        activeBlocks = new LinkedList<>();
        blockQueue = new LinkedList<>();

        // init queue
        for(int i = 0; i < 30; i++) {
            blockQueue.add(Block.getRandomBlock());
        }
    }


    /**
     * Gets the block being controlled by the player
     * @return
     */
    public Block getPlayerBlock() {
        return activeBlocks.getLast();
    }

    public boolean canBlockMoveTo(Block b, Point p) {
        Block potentialBlock = new Block(b.getType());
        potentialBlock.setPosition(p);

        if(!isWithinBounds(potentialBlock)) {
            return false;
        }

        List<Block> blockCollisions = activeBlocks.stream()
                .filter(block -> !b.equals(block))
                .filter(block -> block.isCollidingWith(potentialBlock)) // Filter out any blocks that wont collide
                .collect(Collectors.toList());

        return blockCollisions.size() == 0; // If there are no blocks to collide with, the block can move to the given point
    }

    /**
     * Whether or not this block is inside the bounds of the map
     * @param b
     * @return
     */
    public boolean isWithinBounds(Block b) {
        for(Point p : b.getFilledPoints(true)) {
            if(p.x > MAP_WIDTH || p.x < 0 || p.y < 0 || p.y > MAP_HEIGHT + 4) {
                return false;
            }
        }

        return true;
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
        activeBlocks.add(b);
    }

    @Override
    public String toString() {
        int[][] grid = new int[MAP_WIDTH][MAP_HEIGHT];
        StringBuilder sb = new StringBuilder();
        for(int x = 0; x < MAP_WIDTH; x++) {
            for(int y = 0; y < MAP_HEIGHT; y++) {
                grid[x][y] = 0;
            }
        }

        getActiveBlocks()
                .stream()
                .forEach(block -> block.getFilledPoints(true)
                        .stream()
                        .forEach(point -> grid[point.x][point.y] = 1));

        for(int x = 0; x < MAP_WIDTH; x++) {
            for(int y = 0; y < MAP_HEIGHT; y++) {
                sb.append(grid[x][y] + " - ");
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
