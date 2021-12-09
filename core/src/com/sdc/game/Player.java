package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;

/**
 * Class representing the player-controlled spaceships
 */
public class Player {
    // Game information
    private Main game;

    // Object information
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
     * Initializes the Player with appropriate variables
     *
     * @param game Reference to Game object
     * @param name Name of player
     * @param world Reference to the physics World
     * @param posX Initial x position of the player
     * @param posY Initial y position of the player
     */
    public Player(Main game, String name, World world, int posX, int posY) {
        this.game = game;
        this.name = name;
        health = 100;
        score = 0;
        this.world = world;
        bullets = new Array<>();

        // Body setup
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
        maxRotVelocity = 2;

        layout = new GlyphLayout();
    }

    /**
     * Calculates movement of the player. Implemented using a "fake" physics where user input corresponds
     * to an acceleration in a certain direction, accelerating the player without using actual physics
     * calculation
     */
    private void move() {
        //movement
        if (Gdx.input.isKeyPressed(Keys.W)) body.applyForceToCenter((float)Math.cos(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), (float)Math.sin(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), true);
        if (Gdx.input.isKeyPressed(Keys.S)) body.applyForceToCenter((float)-Math.cos(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), (float)-Math.sin(body.getAngle()) * linAcceleration * Gdx.graphics.getDeltaTime(), true);
        if (body.getLinearVelocity().len() > maxLinVelocity) body.setLinearVelocity(body.getLinearVelocity().setLength(maxLinVelocity));

        //rotation
        if (Gdx.input.isKeyPressed(Keys.A)) body.setAngularVelocity(body.getAngularVelocity() + rotAcceleration * game.DEGREES_TO_RADIANS * Gdx.graphics.getDeltaTime());
        if (Gdx.input.isKeyPressed(Keys.D)) body.setAngularVelocity(body.getAngularVelocity() - rotAcceleration * game.DEGREES_TO_RADIANS * Gdx.graphics.getDeltaTime());
        if (body.getAngularVelocity() > maxRotVelocity) body.setAngularVelocity(maxRotVelocity);
        if (body.getAngularVelocity() < -maxRotVelocity) body.setAngularVelocity(-maxRotVelocity);
    }

    /**
     * Spawns and fires a projectile
     */
    private void shoot() {
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            System.out.println("fire");
            Projectile bullet = new Projectile(body.getPosition().x, body.getPosition().y, body.getAngle(), this, game, world);
            bullets.add(bullet);
        }
    }

    /**
     * Changes the health of the player, kills the player if remaining health is below 0
     *
     * @param change Amount to be changed
     * @return True if the player dies, and false otherwise
     */
    public boolean setHealth(int change) {
        health += change;
        System.out.println("health: " + health);
        if (health <= 0) { // If the player is dead
            toBeDeleted = true;
            sprite.setAlpha(0.0f);
            //body.setActive(false);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Changes the player's score
     *
     * @param change Amount to be changed
     */
    public void setScore(int change) {
        score += change;
        System.out.println("score" + score);
    }

    /**
     * Renders the Player
     */
    private void draw() {
        //Currently manually setting size and position
        sprite.setRotation(body.getAngle() / game.DEGREES_TO_RADIANS - 90);
        sprite.setPosition(body.getPosition().x * game.PIXELS_PER_METER - sprite.getWidth() / 2, body.getPosition().y * game.PIXELS_PER_METER - sprite.getHeight() / 2);
        sprite.draw(game.batch);
        layout.setText(game.playerFont, name);
        game.playerFont.draw(game.batch, layout, body.getPosition().x * game.PIXELS_PER_METER - layout.width / 2, body.getPosition().y * game.PIXELS_PER_METER - 35);
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
        for (Projectile b : bullets) b.update();
    }

    /**
     * Disposes the player
     */
    public void dispose(){
        texture.dispose();
    }

    /**
     * Returns the body of the player object
     *
     * @return The body of the player object
     */
    public Body getBody() {
        return body;
    }

    public int getHealth(){
        return this.health;
    }

    public int getScore() {
        return score;
    }

    /**
     * Returns true if the player should be deleted, and false otherwise
     *
     * @return True if the player should be deleted, and false otherwise
     */
    public boolean toBeDeleted() {
        return toBeDeleted;
    }

    /**
     * Returns the projectiles array
     *
     * @return The projectiles array
     */
    public Array<Projectile> getBullets() {
        return bullets;
    }
}

