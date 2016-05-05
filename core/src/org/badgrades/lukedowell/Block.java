package org.badgrades.lukedowell;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ldowell on 5/4/16.
 */
public class Block {


    /**
     * Stores the X+Y coordinates for this block, using shape[0][0] as
     * an origin point.
     */
    private Point position;

    /**
     * The type of this block
     */
    private BlockType type;

    /**
     * Creates a new block with the given type
     * @param type
     */
    public Block(BlockType type) {
        this.type = type;
    }

    /**
     * Returns an array that represents what this block would look like if it was
     * rotated clockwise by 90 degrees
     */
    public void rotateClockwise() {
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

    /**
     *
     */
    public void rotateCounterclockwise() {
        //TODO
    }

    /**
     * Returns points that are taken up by filled portions of this block's bounding box.
     * If an L block was placed at location (4, 4) then this method would return points with xy
     * values of: (5, 4), (5, 5), (5, 6) and (6, 6)
     * @return
     */
    public List<Point> getFilledPoints() {
        ArrayList<Point> pointList = new ArrayList<>();
        int originX = this.position.x;
        int originY = this.position.y;

        for(int x = 0; x < this.type.shape.length; x++) {
            for(int y = 0; y < this.type.shape[0].length; y++) {
                if(this.type.shape[x][y] == 1) {
                    pointList.add(new Point(originX + x, originY - y));
                }
            }
        }

        return pointList;
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
     * Creates a block with a random type and returns it
     * @return
     */
    public static Block getRandomBlock() {
        int numBlockTypes = BlockType.values().length;
        int randomIndex = (int) Math.floor(Math.random() * numBlockTypes);
        return new Block(BlockType.values()[randomIndex]);
    }
}
