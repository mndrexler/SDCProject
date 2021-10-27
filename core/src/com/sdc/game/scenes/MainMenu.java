package com.sdc.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.sdc.game.Main;
import com.sdc.game.Player;

public class MainMenu implements Screen {
    private Texture background;
    private Main game;
    private OrthographicCamera cam;

    //Testing player
    Player player;

    public MainMenu(Main g) {
        this.game = g;
        cam = new OrthographicCamera();
        cam.setToOrtho(false, 800, 480);
        background = new Texture(Gdx.files.internal("space-background.jpg"));

        //Testing player
        player = new Player(game, "tester", new Texture(Gdx.files.internal("player.png")));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        cam.update();
        game.batch.setProjectionMatrix(cam.combined);

        game.batch.begin();
        game.batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        game.font.draw(game.batch, "Asteroids",0,0);

        //Testing player
        player.update(delta);

        game.batch.end();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        background.dispose();

    }
}
