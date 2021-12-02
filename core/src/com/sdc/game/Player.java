package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

import java.util.List;

import java.awt.event.MouseEvent;
import java.util.ArrayList;

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
    private Array<Projectile> bullets;
    private boolean toBeDeleted;

    // Graphics and physics
    private World world;
    private Body body;
    private Texture texture = new Texture(Gdx.files.internal("player.png"));
    private Sprite sprite = new Sprite(texture);
    private float linAcceleration;
    private float rotAcceleration;
    private float maxLinVelocity;
    private float maxRotVelocity;

    //UI
    private GlyphLayout layout;

    /**
     * Initializes the Player objects with appropriate variables
     *
     * @param game Reference to Game object
     * @param name Name of player
     * @param world World
     */
    public Player(Main game, String name, World world, int posX, int posY) {
        this.game = game;
        this.name = name;
        health = 100;
        score = 0;
        this.world = world;
        bullets = new Array<>();

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(posX, posY);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50 / game.PIXELS_PER_METER / 2, 50 / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);

        sprite.setScale(.1f);
        linAcceleration = 1000;
        rotAcceleration = 70;
        maxLinVelocity = 5;
        maxRotVelocity = 1;

        layout = new GlyphLayout();
    }

    /**
     * Calculates movement of the player. Implemented using a "fake" physics where user input corresponds
     * to an acceleration in a certain direction, accelerating the player without using actual physics
     * calculation
     */
    public void move() {
        //movement
        if (Gdx.input.isKeyPressed(Keys.W)) body.applyForceToCenter((float)Math.cos(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), (float)Math.sin(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), true);
        if (Gdx.input.isKeyPressed(Keys.S)) body.applyForceToCenter((float)-Math.cos(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), (float)-Math.sin(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), true);
        if (body.getLinearVelocity().len() > maxLinVelocity) body.setLinearVelocity(body.getLinearVelocity().setLength(maxLinVelocity));

        //rotation
        if (Gdx.input.isKeyPressed(Keys.A)) body.setAngularVelocity(body.getAngularVelocity() + rotAcceleration * DEGREES_TO_RADIANS * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.D)) body.setAngularVelocity(body.getAngularVelocity() - rotAcceleration * DEGREES_TO_RADIANS * Gdx.graphics.getDeltaTime());
        if (body.getAngularVelocity() > maxRotVelocity) body.setAngularVelocity(maxRotVelocity);
        if (body.getAngularVelocity() < -maxRotVelocity) body.setAngularVelocity(-maxRotVelocity);
    }

    /**
     * Spawns and fires a projectile
     */
    public void shoot() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            System.out.println("fire");
            Projectile bullet = new Projectile(body.getPosition().x, body.getPosition().y, body.getAngle(), this, game, world);
            bullets.add(bullet);
        }
    }

    public boolean setHealth(int change) {
        health += change;
        System.out.println("health: " + health);
        if (health <= 0) {
            toBeDeleted = true;
            sprite.setAlpha(0.0f);
            //body.setActive(false);
            return true;
        } else {
            return false;
        }
    }

    public void setScore(int change) {
        score += change;
        System.out.println("score" + score);
    }

    /**
     * Renders the Player object
     */
    public void draw() {
        //Currently manually setting size and position
        sprite.setRotation(body.getAngle() / DEGREES_TO_RADIANS - 90);
        sprite.setPosition(body.getPosition().x * game.PIXELS_PER_METER - sprite.getWidth() / 2, body.getPosition().y * game.PIXELS_PER_METER - sprite.getHeight() / 2);
        sprite.draw(game.batch);
        //game.batch.draw(sprite, body.getPosition().x * game.PIXELS_PER_METER - sprite.getWidth() / 2, body.getPosition().y * game.PIXELS_PER_METER - sprite.getHeight() / 2);
        layout.setText(game.playerFont, name);
        game.playerFont.draw(game.batch, layout, body.getPosition().x + (sprite.getWidth() - layout.width) / 2, body.getPosition().y - 10);

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
     */
    public void update() {
        move();
        shoot();
        draw();
        for (Projectile b : bullets) {
            b.move();
            b.draw();
        }
    }

    public void dispose(){
        texture.dispose();
    }

    public Body getBody() {
        return body;
    }

    public boolean toBeDeleted() {
        return toBeDeleted;
    }

    public Array<Projectile> getBullets() {
        return bullets;
    }
}

