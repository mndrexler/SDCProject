package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import java.awt.event.MouseEvent;

/**
 * Class representing all the player spaceships in the game
 */
public class Player {
    // Game information
    private Main game;

    // Player information
    private String name;
    private int health;
    private int score;

    // Graphics and physics
    private Texture texture;
    private com.badlogic.gdx.math.Rectangle collider;
    private float acceleration;
    private float dx;
    private float dy;

    // UI
    private BitmapFont font;
    private GlyphLayout layout;

    /**
     * Initializes the Player obejcts with appropriate variables
     *
     * @param game Reference to Game object
     * @param name Name of player
     * @param texture Texture of player (Might not be needed as all players appear the same)
     */
    public Player(Main game, String name, Texture texture) {
        this.game = game;
        this.name = name;
        this.texture = texture;
        health = 100;
        score = 0;
        acceleration = 2;
//        body = new Body();
        collider = new Rectangle(300, 300, 40, 40);
        font = new BitmapFont();
        layout = new GlyphLayout();
    }

    /**
     * Calculates movement of the player. Implemented using a "fake" physics where user input corresponds
     * to an acceleration in a certain direction, accelerating the player without using actual physics
     * calculation
     *
     * @param delta GDX.graphics.getDeltaTime
     */
    public void move(float delta) {
        if (Gdx.input.isKeyPressed(Keys.A)) dx -= acceleration * delta;
        if (Gdx.input.isKeyPressed(Keys.D)) dx += acceleration * delta;
        if (Gdx.input.isKeyPressed(Keys.S)) dy -= acceleration * delta;
        if (Gdx.input.isKeyPressed(Keys.W)) dy += acceleration * delta;

        collider.setPosition(collider.x + dx, collider.y + dy);
    }

    /**
     * Spawns and fires a projectile
     */
    public void shoot() {
        if (Gdx.input.isButtonPressed(MouseEvent.BUTTON1)) {
            // Call projectile instantiation
        }
    }

    /**
     * Renders the Player object
     */
    public void draw() {
        //Currently manually setting size and position
        game.batch.draw(texture, collider.x, collider.y, collider.width, collider.height);
        layout.setText(font, name);
        font.draw(game.batch, layout, collider.x + (collider.width - layout.width) / 2, collider.y - 10);

        /*
        texture = new Texture...boolean
            sprite = new Sprite...
        this.sprite.setposition(...);
        */
    }

    /**
     * Functions as a layer of abstraction and is to be called by Main. A high level script only needs
     * to call update(), and it will take care of the smaller methods within the Player class. Helps
     * to reduce redundancy in other scripts.
     *
     * @param delta GDX.graphics.getDeltaTime
     */
    public void update(float delta) {
        move(delta);
        shoot();
        draw();
    }
}
