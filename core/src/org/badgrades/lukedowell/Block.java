package org.badgrades.lukedowell;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ldowell on 5/4/16.
 */
public class Block {

    /**
     * The type of this block
     */
    private BlockType type;

    /**
     * The mutable copy of type.shape, so we dont affect other blocks of the
     * same type when we move
     */
    private int[][] shape;

    private Point position;

    /**
     * Creates a new block with the given type
     * @param type
     */
    public Block(BlockType type) {
        this.type = type;
        this.shape = Arrays.stream(this.type.shape).map(int[]::clone).toArray(int[][]::new); // Copies array
    }

    /**
     * Returns an array that represents what this block would look like if it was
     * rotated clockwise by 90 degrees
     */
    public void rotateClockwise() {
        final int M = this.shape.length;
        final int N = this.shape[0].length;
        int[][] ret = new int[N][M];
        for (int r = 0; r < M; r++) {
            for (int c = 0; c < N; c++) {
                ret[c][M-1-r] = this.shape[r][c];
            }
        }
        this.shape = ret;
    }

    /**
     * Since blocks can be rotated their 'width' changes. This method
     * calculates the difference between the most-left point and the most-right
     * point from a list of filled points.
     *
     * @return
     */
    public int getCurrentWidth() {
        Point mostLeftPoint = new Point(0, 0);
        Point mostRightPoint = new Point(0, 0);

        List<Point> points = getFilledPoints(false);
        for(Point p : points) {
            if(p.x > mostRightPoint.x)
                mostRightPoint = p;

            if(p.x < mostLeftPoint.x)
                mostLeftPoint = p;
        }

        return (mostRightPoint.x - mostLeftPoint.x) + 1;
    }

    /**
     * Since blocks can be rotated their 'height' changes. This method
     * calculates the difference between the highest point and the lowest
     * point from a list of filled points.
     * @return
     */
    public int getCurrentHeight() {
        Point highestPoint = new Point(0, 0);
        Point lowestPoint = new Point(0, 0);

        List<Point> points = getFilledPoints(false);
        for(Point p : points) {
            if(p.y > highestPoint.y)
                highestPoint = p;

            if(p.y < lowestPoint.y)
                lowestPoint = p;
        }

        return (highestPoint.y - lowestPoint.y) + 1;
    }


    public BlockType getType() {
        return type;
    }

    /**
     * Returns a list of points representing the offset values
     * @return
     */
    public List<Point> getFilledPoints(boolean absolute) {
        ArrayList<Point> points = new ArrayList<>();
        for(int xOffset = 0; xOffset < this.shape.length; xOffset++) {
            for(int yOffset = 0; yOffset < this.shape[0].length; yOffset++) {
                if(this.shape[xOffset][yOffset] > 0) {
                    if(absolute)
                        points.add(new Point(xOffset + this.position.x, yOffset + this.position.y));
                    else
                        points.add(new Point(xOffset, yOffset));
                }
            }
        }
        return points;
    }

    /**
     *
     * @param otherBlock
     * @return
     */
    public boolean isCollidingWith(Block otherBlock) {
        List<Point> myPoints = this.getFilledPoints(true);
        List<Point> otherPoints = otherBlock.getFilledPoints(true);

        int originalSize = myPoints.size();
        myPoints.removeAll(otherPoints);
        return myPoints.size() != originalSize;
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

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }



    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
