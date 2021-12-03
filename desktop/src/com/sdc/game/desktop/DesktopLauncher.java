package com.sdc.game.desktop;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.sdc.game.Main;

public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

		config.setTitle("Asteroids");
		config.setResizable(true);
		//config.setWindowSizeLimits(800,400,800,400);
		new Lwjgl3Application(new Main(), config);
	}
}
