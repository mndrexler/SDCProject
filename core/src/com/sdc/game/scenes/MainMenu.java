package com.sdc.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sdc.game.Main;

public class MainMenu implements Screen {
    private Texture background;
    private Main game;
    private OrthographicCamera cam;
    private Stage stage;

    public MainMenu(Main g){
        this.game = g;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        //Skin skin = new Skin(Gdx.files.internal("skin/sgx-ui.json"));
        //TextButton button = new TextButton("Play",skin);

        this.stage.addActor(button);

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, 800, 480);
        this.background = new Texture(Gdx.files.internal("space-background.jpg"));
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
        game.font.draw(game.batch, "Asteroids",150,450);
        game.batch.end();

        stage.act();
        stage.draw();
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
