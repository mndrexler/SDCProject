package com.sdc.game.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sdc.game.Asteroid;
import com.sdc.game.Main;
import com.sdc.game.Player;

public class MainMenu implements Screen {
    private Texture background;
    private Main game;
    private OrthographicCamera cam;
    private Stage stage;

    private Asteroid[] asteroids;

    public MainMenu(Main g) {
        this.game = g;
        this.stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        this.cam = new OrthographicCamera();
        this.cam.setToOrtho(false, 800, 480);

        asteroids = new Asteroid[5];
        for(int i = 0 ; i < asteroids.length; i++){
            asteroids[i] = new Asteroid(game, (int)(Gdx.graphics.getWidth() * Math.random()), (int)(Gdx.graphics.getHeight() * Math.random()));
        }

        //UI Elements
        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json")); //placeholder skin
        final TextButton button = new TextButton("Play",skin,"big");
        button.setOrigin(button.getWidth()/2,button.getHeight()/2);
        button.setPosition(Gdx.graphics.getWidth()/2 - button.getWidth(),250);
        button.setTransform(true);
        button.setScale(3,2);

        final TextField field = new TextField("Enter Your Name", skin);
        field.setOrigin(field.getWidth()/2,field.getHeight()/2);
        field.setPosition(Gdx.graphics.getWidth()/2 - field.getWidth(),100);
        field.setWidth(field.getWidth() + 100);
        field.setScale(3,3);

        button.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               String name = field.getText();
               if(name.equals(""))
                   field.setText("You need to enter a name");
               else {
                   game.setScreen(new GameScreen(game, name));
                   dispose();
               }
           }
        });

        //this.stage.addActor(button);
        this.stage.addActor(field);
        this.stage.addActor(button);
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
        for(Asteroid as: asteroids){
            as.update(delta);
        }
        game.titleFont.draw(game.batch, "Asteroids",150,450);


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
