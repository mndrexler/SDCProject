package com.sdc.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.sdc.game.scenes.MainMenu;

public class Main extends Game {
	private Asteroid[] asteroids;
	private Player player;
	private MainMenu menu;

	@Override
	public void create () {
		setScreen(menu = new MainMenu(this));
	}

	@Override
	public void render () {
	}
	
	@Override
	public void dispose () {
	}
}
