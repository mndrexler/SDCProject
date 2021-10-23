package com.sdc.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Asteroid {

    private Sprite sprite;
    private Sprite texture;

    private int posX;
    private int posY;

    public Asteroid(Stage stage, Sprite sprite) {
        this.sprite = sprite;

        posX = (int) (Math.random() * stage.getWidth());
        posY = (int) (Math.random() * stage.getHeight());
    }

    public void create() {


    }

}
