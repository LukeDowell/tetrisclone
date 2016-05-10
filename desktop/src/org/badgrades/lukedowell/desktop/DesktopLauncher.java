package org.badgrades.lukedowell.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.badgrades.lukedowell.TetrisCloneGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tetris Clone";
		config.width = (520);
		config.height = (840);
		new LwjglApplication(new TetrisCloneGame(), config);
	}
}
