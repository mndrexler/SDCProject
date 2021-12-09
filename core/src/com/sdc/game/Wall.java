package com.sdc.game;
import com.badlogic.gdx.physics.box2d.*;

public class Wall {
    private Body body;

    public Wall(int x, int y, int width, int height, Main game, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / game.PIXELS_PER_METER / 2, height / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);
    }

}
