package com.sdc.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.esotericsoftware.kryonet.Server;
import com.sdc.game.scenes.MainMenu;

/**
 * TODO:
 * Sprite Rotation
 * Entire projectile class
 * Title Screen -buttons etc
 * Camera/Game map
 * Collisions
 *
 *
 *
 *
 *
 * ...Multiplayer
 */
public class Main extends Game {
	public final float PIXELS_PER_METER = 32f;

	public SpriteBatch batch;
	public BitmapFont titleFont;
	public BitmapFont playerFont;

	public int camWidth = 800;
	public int camHeight = 480;

	@Override
	public void create () {
		batch = new SpriteBatch();
		playerFont = new BitmapFont();
		titleFont = new BitmapFont(Gdx.files.internal("asteroidFont.fnt"));
		titleFont.setColor(Color.WHITE);
		titleFont.getData().setScale(1);
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		titleFont.dispose();
		playerFont.dispose();
	}
}
