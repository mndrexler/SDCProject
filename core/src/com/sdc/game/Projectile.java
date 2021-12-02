package com.sdc.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Projectile {
    private Main game;
    private float angle;
    private Player parent;
    private Body body;
    private Texture texture;
    private Sprite sprite;
    private float speed;
    private float delta;

    public Projectile(float x, float y, float angle, Player parent, Main game, World world) {
        this.game = game;
        this.angle = angle;
        this.parent = parent;
        this.delta = delta;

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
        body.setTransform(x + (float)Math.cos(angle) * 10, y + (float)Math.sin(angle) * 10, angle);
        body.setUserData(this);

        speed = 7;
    }

    public void move() {
        float dx = (float) (Math.cos(angle) * speed);
        float dy = (float) (Math.sin(angle) * speed);

        body.setTransform(body.getPosition().x + dx, body.getPosition().y + dy, angle);
    }

    public boolean onCollision(Object other) {
        if (other instanceof Player) {
            if (((Player) other).setHealth(-10)) parent.setScore(10);
        }
        return true;
    }

    public void draw() {

    }

    public void dispose() {
        texture.dispose();
    }
}
