package org.badgrades.lukedowell;


import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by ldowell on 5/6/16.
 */
public class BlockMapTests {

    private BlockMap blockMap;

    @Before
    public void setUp() throws Exception {
        blockMap = new BlockMap();
    }

    @Test
    public void blockCollisionTest() throws Exception {

        Point sharedPosition = new Point(5, 10);

        Point seperatePositionOne = new Point(0, 0);
        Point seperatePositionTwo = new Point(7, 14);

        Block randomBlockOne = Block.getRandomBlock();
        Block randomBlockTwo = Block.getRandomBlock();

        randomBlockOne.setPosition(sharedPosition);
        randomBlockTwo.setPosition(sharedPosition);

        assertEquals(true, randomBlockOne.isCollidingWith(randomBlockTwo));

        randomBlockOne.setPosition(seperatePositionOne);
        randomBlockTwo.setPosition(seperatePositionTwo);

        assertEquals(false, randomBlockOne.isCollidingWith(randomBlockTwo));
    }
}
