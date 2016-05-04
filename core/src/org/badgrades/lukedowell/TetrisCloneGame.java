package org.badgrades.lukedowell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.LinkedList;

public class TetrisCloneGame extends ApplicationAdapter {

	/** The width and height of the tetris game area */
	private static final int MAP_WIDTH = 6;
	private static final int MAP_HEIGHT = 14;

	/** The tile size of one block */
	private static final int BLOCK_SIZE = 32;

	/** How long between block movements in milliseconds */
	private static final long TICK_LENGTH = 1000;

	/** The time in millis the last time we updated the block's position */
	private static long last_rendered = 0;

	/** The 2d grid tetris is played on */
	private int[][] gamemap;

	/** A list of upcoming blocks */
	private LinkedList<Block> blockQueue;

	/** A list of blocks currently on the grid */
	private LinkedList<Block> activeBlocks;

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;

	@Override
	public void create () {
		gamemap = new int[MAP_HEIGHT][MAP_WIDTH];
		camera = new OrthographicCamera();
		camera.setToOrtho(false, BLOCK_SIZE * MAP_WIDTH, BLOCK_SIZE * MAP_HEIGHT);
		shapeRenderer = new ShapeRenderer();
		blockQueue = new LinkedList<Block>();
		activeBlocks = new LinkedList<Block>();

		// Inject blocks into queue
		for(int i = 0; i < 30; i++) {
			blockQueue.push(getRandomBlock());
		}

		// Place one block on the screen
		Block someBlock = blockQueue.pop();
		someBlock.setPosition(new Point(10, 10));
		activeBlocks.add(someBlock);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		if(System.currentTimeMillis() - last_rendered >= TICK_LENGTH) {
			// We should update their position
			System.out.println("Updating block position");
			last_rendered = System.currentTimeMillis();
		}

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		for(Block b : activeBlocks) {
			drawBlock(b);
		}
		shapeRenderer.end();
	}

	/**
	 * Draws a block. ShapeRenderer.begin needs to be called
	 * before this method.
	 *
	 * @param block The block to draw
     */
	private void drawBlock (Block block) {
		int originBlockX = block.getPosition().x;
		int originBlockY = block.getPosition().y;
		int[][] shape = block.getType().shape;
		shapeRenderer.setColor(block.getType().color);

		for(int x = 0; x < shape.length; x++) {
			// Convert from grid units to drawing units
			int drawX = (originBlockX + ( x + 1)) * BLOCK_SIZE;

			for(int y = 0; y < shape[0].length; y++) {
				int drawY = (originBlockY + ( y + 1)) * BLOCK_SIZE;

				if(shape[x][y] == 1) {
					// We are supposed to draw this!
					shapeRenderer.rect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
	}

	/**
	 *
	 * @return
     */
	private Block getRandomBlock() {
		int numBlockTypes = Block.BlockType.values().length;
		int randomIndex = (int) Math.floor(Math.random() * numBlockTypes);
		System.out.println("Picking block at index: " + randomIndex);
		return new Block(Block.BlockType.values()[randomIndex]);
	}
}
