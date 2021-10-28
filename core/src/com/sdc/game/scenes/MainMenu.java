package com.sdc.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sdc.game.Main;
import com.sdc.game.Player;

public class MainMenu implements Screen {
    private Texture background;
    private Main game;
    private OrthographicCamera cam;
    private Stage stage;

    //Testing player
    Player player;

    public MainMenu(Main g) {
        this.game = g;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, 800, 480);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json")); //placeholder skin
        TextButton button = new TextButton("Play",skin);
        button.setOrigin(button.getWidth()/2,button.getHeight()/2);
        button.setPosition(Gdx.graphics.getWidth()/2 - button.getWidth(),200);
        button.setTransform(true);
        button.setScale(5,4);

        button.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game));
                dispose();
           }
        });

        this.stage.addActor(button);


        this.background = new Texture(Gdx.files.internal("space-background.jpg"));

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
        game.font.draw(game.batch, "Asteroids",150,450);

        //Testing player
        player.update(delta);

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
        stage.dispose();
    }
}
