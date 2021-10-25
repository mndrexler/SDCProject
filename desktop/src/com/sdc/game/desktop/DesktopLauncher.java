package com.sdc.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.sdc.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.title = "Asteroids";
		config.resizable = false;
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new Main(), config);
	}
}
