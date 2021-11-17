package com.sdc.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Projectile {
    private Main game;
    private float direction;
    private Player parent;
    private Body body;
    private Texture texture;
    private Sprite sprite;
    private float velocity;

    public Projectile(float x, float y, float angle, Player parent, Main game, World world) {
        this.game = game;
        this.direction = direction;
        this.parent = parent;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 / game.PIXELS_PER_METER / 2, 10 / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
        body.setTransform(x, y, angle);

        velocity = 7;

        body.setLinearVelocity(body.getLinearVelocity().setLength(velocity));
    }

    public void draw() {

    }

    public Player getParent() {
        return parent;
    }

    public void dispose() {
        texture.dispose();
    }
}
