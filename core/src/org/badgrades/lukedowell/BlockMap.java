package org.badgrades.lukedowell;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.point;
import static com.sun.tools.doclets.formats.html.markup.HtmlStyle.block;

/**
 * Created by ldowell on 5/5/16.
 */
public class BlockMap {

    /** The width and height of the tetris game area */
    public static final int MAP_WIDTH = 10;
    public static final int MAP_HEIGHT = 20;

    /** A list of upcoming blocks */
    private LinkedList<Block> blockQueue;

    /** A list of blocks currently on the grid */
    private LinkedList<Block> activeBlocks;

    /** The block that is controlled by the player */
    private Block playerBlock;

    public BlockMap() {
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
        Point diffPos = new Point(
                b.getPosition().x - p.x,
                b.getPosition().y - p.y);

        // Create this here so we aren't doing it over and over later
        List<Point> activeBlockPoints = activeBlocks.stream().flatMap(block -> {
            // We don't want to check against ourselves
            if(!block.equals(b)) {
                return block.getFilledPoints().stream();
            }
            return null;
        }).collect(Collectors.toList());

        Optional canMove = b.getFilledPoints().stream()
                .filter(point -> {
                    // Filter out the points that are within the bounds of the map
                    int mapX = point.x - diffPos.x;
                    int mapY = point.y - diffPos.y;

                    return !(mapX > MAP_WIDTH - 1 || mapX <= 0 || mapY <= 0 || mapY > MAP_HEIGHT - 1); // I shouldn't have to do these -1's here, how do design around that?
                })
                .filter(activeBlockPoints::contains).findAny();

        return !canMove.isPresent();
    }

    /**
     * Writes the block to the map array, then adds it to a
     * list of saved blocks.
     *
     * @param b
     */
    public void saveBlock(Block b) {
        activeBlocks.add(b);
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
