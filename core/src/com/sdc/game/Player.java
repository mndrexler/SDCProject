package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;

public class Player {
    private Vector2 pos;
    private String name;
    private int health;
    private int score;
    private Sprite sprite;
    private float speed;


    public Player(String name, Stage stage, Sprite sprite) {
        this.name = name;
        this.sprite = sprite;
        health = 100;
//        posX = (int) (Math.random() * stage.getWidth());
//        posY = (int) (Math.random() * stage.getHeight());
        score = 0;
    }

    public void move(float delta) {
        if (Gdx.input.isButtonPressed(Keys.A)){

        }
    }
    /*
    texture = new Texture...boolean
    sprite = new Sprite...
    this.sprite.setposition(...);
    */

    //public void render(SpriteBatch batch) {batch.draw(this);}
}