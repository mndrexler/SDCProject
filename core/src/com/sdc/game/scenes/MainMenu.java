package com.sdc.game.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sdc.game.Asteroid;
import com.sdc.game.Main;

public class MainMenu implements Screen {
    private Texture background;
    private Main game;
    private OrthographicCamera cam;
    private Stage stage;
    private Viewport view;

    private Asteroid[] asteroids;

    public MainMenu(Main g) {
        this.game = g;
        this.cam = new OrthographicCamera();
        view = new FitViewport(game.camWidth, game.camHeight, cam);
        this.cam.setToOrtho(false,game.camWidth,game.camHeight);

        asteroids = new Asteroid[5];
        for(int i = 0 ; i < asteroids.length; i++){
            asteroids[i] = new Asteroid(game, (int)(game.camWidth * Math.random()), (int)(game.camHeight * Math.random()));
        }

        //UI Elements
        this.stage = new Stage(view);
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.defaults().pad(10f);

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

        final Label title = new Label("Asteroids",new Label.LabelStyle(game.titleFont, Color.WHITE));
        title.setAlignment(Align.center);
        table.add(title).padBottom(Value.percentHeight(1));

        table.row();
        final TextField nameInput = new TextField("", skin);
        nameInput.setMessageText("Enter Your Name");
        nameInput.setMaxLength(12);
        table.add(nameInput).prefWidth(200f);

        table.row();
        Table buttonTable = new Table();
        table.setFillParent(true);

        final TextButton playButton = new TextButton("Play",skin);
        buttonTable.add(playButton).fill().padRight(20f);

        final TextButton joinButton = new TextButton("Join",skin);
        buttonTable.add(joinButton).fill().padLeft(20f);

        table.add(buttonTable);

        playButton.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y){
               String name = nameInput.getText();
               if(name.equals(""))
                   nameInput.setMessageText("You need to enter a name");
               else {
                   game.setScreen(new GameScreen(game, name));
                   dispose();
               }
           }
        });

        this.stage.addActor(table);
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
        game.batch.draw(background,0,0, game.camWidth,game.camHeight);
        for(Asteroid as: asteroids){
            as.update(delta);
        }
        game.batch.end();

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        view.update(width,height);
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
