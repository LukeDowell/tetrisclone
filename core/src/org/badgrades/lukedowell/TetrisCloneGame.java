package org.badgrades.lukedowell;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.*;
import java.util.LinkedList;

import static com.badlogic.gdx.graphics.g2d.ParticleEmitter.SpawnShape.point;
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

	private boolean isPaused = false;

	private SpriteBatch batch;
	private BitmapFont font;
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
		batch = new SpriteBatch();
		font = new BitmapFont();
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

		// DEBUG PAUSE
		if(Gdx.input.isKeyJustPressed(Input.Keys.P)) {
			this.isPaused = !isPaused;
		}

		//////////////////
		////// TICK //////
		//////////////////

		// GRAVITY
		if(System.currentTimeMillis() - last_time_dropped >= TICK_LENGTH && !isPaused) {

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

		///////////////////
		///// DRAWING /////
		///////////////////

		// DRAW DEM BLOCKS
		blockMap.getActiveBlocks().forEach(this::debugDrawBlock);
		if(blockMap.getPlayerBlock() != null) {
			debugDrawBlock(blockMap.getPlayerBlock());
		}

		// Display isPaused
		if(isPaused) {
			batch.begin();
			font.setColor(0f, 0f, 1f, 1f);
			font.draw(batch, "Paused", 100, 150);
			batch.end();
		}


	}

	/**
	 *
	 * @param block
     */
	private void debugDrawBlock(Block block) {
		int[][] shape = block.getShape();
		for(int x = 0; x < shape.length; x++) {
			for(int y = 0; y < shape[0].length; y++) {
				// This is terrible, figure out how to batch draw unfilled and filled points

				int drawX = (block.getPosition().x + x) * BLOCK_SIZE; // Convert to drawing units
				int drawY = (block.getPosition().y + y) * BLOCK_SIZE;
				if(shape[x][y] == 1) {

					shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
					shapeRenderer.setColor(block.getType().color);
					shapeRenderer.rect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);

				} else {
					shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
					shapeRenderer.setColor(Color.RED);
					shapeRenderer.rect(drawX, drawY, BLOCK_SIZE, BLOCK_SIZE);
				}

				shapeRenderer.end();

			}
		}
	}

	/**
	 * Draws a block. ShapeRenderer.begin needs to be called
	 * before this method.
	 *
	 * @param block The block to draw
     */
	private void drawBlock (Block block) {
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(block.getType().color);

		for(Point point : block.getFilledPoints(true)) {
			shapeRenderer.rect(point.x * BLOCK_SIZE, point.y * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
		}
		shapeRenderer.end();
	}
}
