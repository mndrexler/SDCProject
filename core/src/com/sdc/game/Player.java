package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;
import java.awt.event.MouseEvent;

/**
 * Class representing all the player spaceships in the game
 */
public class Player {
    private final float DEGREES_TO_RADIANS = (float)Math.PI / 180;

    // Game information
    private Main game;

    // Player information
    private String name;
    private int health;
    private int score;

    // Graphics and physics
    Body body;
    private Texture texture = new Texture(Gdx.files.internal("player.png"));
    private float linAcceleration;
    private float rotAcceleration;

    //UI
    private GlyphLayout layout;

    /**
     * Initializes the Player objects with appropriate variables
     *
     * @param game Reference to Game object
     * @param name Name of player
     * @param world World
     */
    public Player(Main game, String name, World world) {
        this.game = game;
        this.name = name;
        health = 100;
        score = 0;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(10, 10);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / game.PIXELS_PER_METER / 2, 50 / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();

        linAcceleration = 300;
        rotAcceleration = 20;
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
        //movement
        if (Gdx.input.isKeyPressed(Keys.W)) body.applyForceToCenter((float)Math.cos(body.getAngle()) * linAcceleration * delta, (float)Math.sin(body.getAngle()) * linAcceleration * delta, true);
        if (Gdx.input.isKeyPressed(Keys.S)) body.applyForceToCenter((float)-Math.cos(body.getAngle()) * linAcceleration * delta, (float)-Math.sin(body.getAngle()) * linAcceleration * delta, true);

        //rotation
        if (Gdx.input.isKeyPressed(Keys.A)) body.setAngularVelocity(body.getAngularVelocity() + rotAcceleration * DEGREES_TO_RADIANS * delta);
        if (Gdx.input.isKeyPressed(Keys.D)) body.setAngularVelocity(body.getAngularVelocity() - rotAcceleration * DEGREES_TO_RADIANS * delta);
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
        game.batch.draw(texture, body.getPosition().x * game.PIXELS_PER_METER - texture.getWidth() / 2, body.getPosition().y * game.PIXELS_PER_METER - texture.getHeight() / 2);
        layout.setText(game.playerFont, name);
        game.playerFont.draw(game.batch, layout, body.getPosition().x + (texture.getWidth() - layout.width) / 2, body.getPosition().y - 10);

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

    public void dispose(){
        this.texture.dispose();
    }

    public Body getBody() {
        return body;
    }
}
