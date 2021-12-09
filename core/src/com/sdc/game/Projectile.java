package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

/**
 * Class representing the projectiles fired by players
 */
public class Projectile {
    // Game information
    private Main game;
    private Player parent;

    // Object information
    private float speed;
    private boolean toBeDeleted;

    // Graphics and physics
    private Texture texture = new Texture(Gdx.files.internal("bullet.png"));
    private Sprite sprite = new Sprite(texture);
    private Body body;

    /**
     * Initializes the player with appropriate objects
     *
     * @param x X position of the parent player
     * @param y Y position of the parent player
     * @param angle Angle of the parent player
     * @param parent Reference to the parent player
     * @param game Reference to Game object
     * @param world Reference to physics World
     */
    public Projectile(float x, float y, float angle, Player parent, Main game, World world) {
        this.game = game;
        this.parent = parent;

        // Body setup
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(16 / game.PIXELS_PER_METER / 2, 8 / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x + (float)(Math.cos(angle) * 1.1), y + (float)(Math.sin(angle) * 1.1), angle);
        body.setUserData(this);

        speed = 8;
        toBeDeleted = false;
        sprite.setScale(1.2f);
    }

    /**
     * Moves the projectile by setting the linear velocity
     */
    private void move() {
        body.setLinearVelocity((float)Math.cos(body.getAngle()) * speed, (float)Math.sin(body.getAngle()) * speed);
    }

    /**
     * Handles the collision behaviour of the projectile
     *
     * @param other Reference to the collided object
     * @return
     */
    public boolean onCollision(Object other) {
        if (other instanceof Player) {
            if (((Player) other).setHealth(-10)) parent.setScore(20);
        } else if (other instanceof Asteroid) {
            parent.setScore(5);
        }
        toBeDeleted = true;
        return true;
    }

    /**
     * Renders the projectile
     */
    private void draw() {
        sprite.setRotation(body.getAngle() / game.DEGREES_TO_RADIANS);
        sprite.setPosition(body.getPosition().x * game.PIXELS_PER_METER - sprite.getWidth() / 2, body.getPosition().y * game.PIXELS_PER_METER - sprite.getHeight() / 2);
        sprite.draw(game.batch);
    }

    /**
     * Functions as a layer of abstraction, calls the appropriate methods to update the object
     */
    public void update() {
        move();
        draw();
    }

    /**
     * Disposes the projectile
     */
    public void dispose() {
        texture.dispose();
    }

    /**
     * Returns the body of the projectile
     *
     * @return The body of the projectile
     */
    public Body getBody() {
        return body;
    }

    /**
     * Returns true if the projectile should be deleted, and false otherwise
     *
     * @return True if the projectile should be deleted, and false otherwise
     */
    public boolean toBeDeleted(){
        return this.toBeDeleted;
    }
}

