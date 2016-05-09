package org.badgrades.lukedowell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.LinkedList;

import static org.badgrades.lukedowell.BlockMap.MAP_HEIGHT;
import static org.badgrades.lukedowell.BlockMap.MAP_WIDTH;

public class TetrisCloneGame extends ApplicationAdapter {

	/** The tile size of one block */
	private static final int BLOCK_SIZE = 32;

	/** How long between block movements in milliseconds */
	private static final long TICK_LENGTH = 500;

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
		shapeRenderer.setProjectionMatrix(camera.combined);
		blockMap = new BlockMap();
		blockMap.spawnBlock();
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
			Block player = blockMap.getPlayerBlock();
			if(player != null) {
				Point playerPos = player.getPosition();
				Point newPos = new Point(playerPos.x - 1, playerPos.y);
				if(blockMap.canBlockMoveTo(player, newPos)) {
					player.setPosition(newPos);
				}
			}
		}

		if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			Block player = blockMap.getPlayerBlock();
			if(player != null) {
				Point playerPos = player.getPosition();
				Point newPos = new Point(playerPos.x + 1, playerPos.y);
				if(blockMap.canBlockMoveTo(player, newPos)) {
					player.setPosition(newPos);
				}
			}
		}

		//////////////////
		////// TICK //////
		//////////////////

		// GRAVITY
		if(System.currentTimeMillis() - last_time_dropped >= TICK_LENGTH) {

			Block player = blockMap.getPlayerBlock();
			Point playerPos = player.getPosition();
			Point destPos = new Point(playerPos.x, playerPos.y - 1);

			if (blockMap.canBlockMoveTo(player, destPos)) {

				player.setPosition(destPos);

			} else {

				blockMap.spawnBlock();
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
		if(blockMap.getPlayerBlock() != null) {
			drawBlock(blockMap.getPlayerBlock());
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
		shapeRenderer.setColor(block.getType().color);

		for(Point point : block.getFilledPoints(true)) {
			shapeRenderer.rect(point.x * BLOCK_SIZE, point.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		}


//		for(int x = 0; x < shape.length; x++) {
//			// Convert from grid units to drawing units
//			int drawX = (originBlockX + x) * BLOCK_SIZE;
//
//			for(int y = 0; y < shape[0].length; y++) {
//				int drawY = (originBlockY + y) * BLOCK_SIZE;
//
//				if(shape[x][y] == 1) {
//					// We are supposed to draw this!
//					shapeRenderer.rect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
//				}
//			}
//		}
	}
}
