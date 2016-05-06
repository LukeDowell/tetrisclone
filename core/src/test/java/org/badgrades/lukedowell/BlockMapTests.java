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
    public void setUp() {
        blockMap = new BlockMap();
    }

    @Test
    public void canBlockMoveTest() {

        // Test
        Block lBlock = new Block(BlockType.L);
        Block sBlock = new Block(BlockType.S);
        Block oBlock = new Block(BlockType.O);

        // Some blocks will fit in these points, and some will not
        Point topMiddlePoint = new Point(5, 2);
        Point middleLeftPoint = new Point(2, 7);
        Point bottomRightPoint = new Point(8, 19);

        lBlock.setPosition(topMiddlePoint);
        sBlock.setPosition(middleLeftPoint);
        oBlock.setPosition(bottomRightPoint);

        blockMap.saveBlock(lBlock);
        blockMap.saveBlock(sBlock);
        blockMap.saveBlock(oBlock);

        System.out.println(blockMap.toString());

        assertEquals(false, blockMap.canBlockMove(oBlock, new Point(bottomRightPoint.x, bottomRightPoint.y + 1)));

    }

    private void printMap() {

    }
}
