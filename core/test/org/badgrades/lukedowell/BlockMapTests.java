package org.badgrades.lukedowell;


import org.badgrades.lukedowell.Block;
import org.badgrades.lukedowell.BlockMap;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by ldowell on 5/6/16.
 */
public class BlockMapTests {

    private static BlockMap blockMap;

    @BeforeClass
    public static void setUp() throws Exception {
        blockMap = new BlockMap();
    }

    @Before
    public void preTest() throws Exception {
        blockMap.clear();
    }


    @Test
    public void blockCollisionTest() throws Exception {
        Point sharedPosition = new Point(5, 10);

        Block randomBlockOne = Block.getRandomBlock();
        Block randomBlockTwo = Block.getRandomBlock();

        randomBlockOne.setPosition(sharedPosition);
        randomBlockTwo.setPosition(sharedPosition);

        assertEquals(true, randomBlockOne.isCollidingWith(randomBlockTwo));


    }

    @Test
    public void blockCollisionTest_separate() throws Exception {
        Point separatePositionOne = new Point(0, 0);
        Point separatePositionTwo = new Point(7, 14);

        Block randomBlockOne = Block.getRandomBlock();
        Block randomBlockTwo = Block.getRandomBlock();

        randomBlockOne.setPosition(separatePositionOne);
        randomBlockTwo.setPosition(separatePositionTwo);

        assertEquals(false, randomBlockOne.isCollidingWith(randomBlockTwo));
    }


    @Test
    public void canBlockMoveTest() throws Exception {
        Point startingPosition = new Point(5, 10);
        Point destinationPosition = new Point(5, 11);

        Block someBlock = Block.getRandomBlock();
        someBlock.setPosition(startingPosition);

        assertEquals(true, blockMap.canBlockMoveTo(someBlock, destinationPosition));
    }

    @Test
    public void withinBoundsTest_inside() throws Exception {

    }

    @Test
    public void withinBoundsTest_outside() throws Exception {
        Block lBlock = new Block(BlockType.L);
        lBlock.setPosition(new Point(BlockMap.MAP_WIDTH, BlockMap.MAP_HEIGHT));

        assertEquals(false, blockMap.isWithinBounds(lBlock));
    }

    @Test
    public void blockWidthTest() throws Exception {

        // L blocks spawn laying down, so the width should be 4
        Block lBlock = new Block(BlockType.L);

        assertEquals(3, lBlock.getCurrentWidth());

        lBlock.rotateClockwise();

        // Now the block is standing up so it should be 1

        assertEquals(2, lBlock.getCurrentWidth());
    }

    @Test
    public void blockWidthTest_iBlock() throws Exception {
        Block iBlock = new Block(BlockType.I);

        assertEquals(4, iBlock.getCurrentWidth());
        iBlock.rotateClockwise();
        assertEquals(1, iBlock.getCurrentWidth());
    }
}
