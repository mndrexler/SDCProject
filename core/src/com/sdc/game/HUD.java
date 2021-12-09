package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class HUD {
    private Stage stage;
    private FitViewport viewport;
    private Label healthLabel;
    private Label scoreLabel;
    private int score;
    private int health;
    private Player player;


    public HUD(SpriteBatch batch, Player player){
        viewport = new FitViewport(800,480);
        stage = new Stage(viewport, batch);
        this.player = player;
        this.health = player.getHealth();
        this.score = player.getScore();

        Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
        Table table = new Table();
        table.setTouchable(Touchable.disabled);
        table.align(Align.top);
        table.setFillParent(true);
        table.defaults().pad(5);

        healthLabel = new Label("Lives: " + health,skin);
        healthLabel.setAlignment(Align.center);
        table.add(healthLabel).expandX().align(Align.left).spaceLeft(10);

        scoreLabel = new Label("Score: " + score, skin);
        scoreLabel.setAlignment(Align.center);
        table.add(scoreLabel).expandX().align(Align.right).spaceRight(10);

        stage.addActor(table);
    }

    public void update(float delta){
        scoreLabel.setText("Score: "+player.getHealth());
        healthLabel.setText("Health: " + player.getHealth());
        this.stage.act(delta);
        this.stage.draw();
    }

    public Stage getStage(){
        return this.stage;
    }

    public void dispose(){
        stage.dispose();
    }
}
