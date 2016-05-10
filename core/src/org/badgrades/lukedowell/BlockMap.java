package org.badgrades.lukedowell;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.function.Predicate;
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

        // Create a condition that returns true if a block collides with another block
        Predicate<Block> doesCollidePredicate = (block) -> block.isCollidingWith(potentialBlock);

        // Remove the block we are checking against, then return true if none of the remaining blocks collide
        // with our potential future block.

        boolean bool = activeBlocks.stream()
                .filter(block -> !b.equals(block)) // Don't check collisions against itself
                .noneMatch(doesCollidePredicate);
        return bool;
    }

    /**
     * Whether or not this block is inside the bounds of the map
     * @param b
     * @return
     */
    public boolean isWithinBounds(Block b) {
        // Different shapes have different widths, so we calculate this to offset our distance from the right wall
        int widthOffset = b.getCurrentWidth();

        // We want to allow the shape to spawn offscreen and then drop into view
        int heightOffset = b.getCurrentHeight();

        for(Point p : b.getFilledPoints(true)) {
            if(p.x > MAP_WIDTH  ||
                    p.x < 0 ||
                    p.y < 0 ||
                    p.y > (MAP_HEIGHT + heightOffset)) {
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

    public boolean spawnBlock() {
        if(blockQueue.size() > 0) {
            this.spawnBlock(blockQueue.pop());
            return true;
        }
        return false;
    }

    public void spawnBlock(Block b) {
        int spawnX = (int) Math.floor(BlockMap.MAP_WIDTH / 2);
        int spawnY = (b.getCurrentHeight() + BlockMap.MAP_HEIGHT);
        Point spawnPoint = new Point(spawnX, spawnY);
        b.setPosition(spawnPoint);
        activeBlocks.add(b);
    }

    public void clear() {
        this.activeBlocks.clear();
        this.blockQueue.clear();
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
