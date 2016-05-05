package org.badgrades.lukedowell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;

import static org.badgrades.lukedowell.BlockMap.MAP_HEIGHT;
import static org.badgrades.lukedowell.BlockMap.MAP_WIDTH;

public class TetrisCloneGame extends ApplicationAdapter {

	/** The tile size of one block */
	private static final int BLOCK_SIZE = 32;

	/** How long between block movements in milliseconds */
	private static final long TICK_LENGTH = 1000;

	/** How long to wait between placing a block and spawning a new one in millis */
	private static final long ARE_DELAY_LENGTH = 500;

	/** The time in millis the last time we updated the block's position */
	private static long last_time_dropped = 0;

	/** The time in millis that the most recent block was set down */
	private static long last_time_placed = 0;

	private OrthographicCamera camera;
	private ShapeRenderer shapeRenderer;
	private BlockMap blockMap;

	@Override
	public void create () {
		camera = new OrthographicCamera();
		camera.setToOrtho(false, BLOCK_SIZE * MAP_WIDTH, BLOCK_SIZE * MAP_HEIGHT);
		shapeRenderer = new ShapeRenderer();
		blockMap = new BlockMap();
		blockMap.spawnBlock(new Block(BlockType.L));
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//////////////////
		///// INPUT  /////
		//////////////////

		// ROTATE LOGIC
		if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			Block player = blockMap.getPlayerBlock();
			if(player != null) {
				player.rotateClockwise();
			}
		}

		// SIDE TO SIDE LOGIC
		if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {

		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {

		}

		//////////////////
		////// TICK //////
		//////////////////

		// GRAVITY
		if(System.currentTimeMillis() - last_time_dropped >= TICK_LENGTH) {

			// YES WE CAN
			System.out.println("Updating block position");

			// TODO Implement SRS gravity, cells per tick
			Block player = blockMap.getPlayerBlock();
			if(blockMap.canBlockDrop(player)) {
				Point oldPoint = player.getPosition();
				player.setPosition(new Point(oldPoint.x, oldPoint.y - 2));
			} else {
				// TODO Set the block down
			}

			// RESET
			last_time_dropped = System.currentTimeMillis();
		}

		// ARE TIME
		// TODO

		///////////////////
		///// DRAWING /////
		///////////////////

		// DRAW DEM BLOCKS
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		blockMap.getActiveBlocks().forEach(this::drawBlock);
		drawBlock(blockMap.getPlayerBlock());
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
			int drawX = (originBlockX + x) * BLOCK_SIZE;

			for(int y = 0; y < shape[0].length; y++) {
				int drawY = (originBlockY + y) * BLOCK_SIZE;

				if(shape[x][y] == 1) {
					// We are supposed to draw this!
					shapeRenderer.rect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		}
	}
}
