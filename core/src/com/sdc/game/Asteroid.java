package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Asteroid {
    // Game information
    private Main game;

    // Object information
    private int x,y,velX,velY;
    private float rot = 0;
    private float rotUp = (float)(100*Math.random()) * Math.random()>=.5?1:-1 + 200;

    // Graphics and physics
    private Texture texture = new Texture(Gdx.files.internal("asteroid.png"));
    private Sprite sprite = new Sprite(texture);
    //private Rectangle collider;
    private Body body;

    private GlyphLayout layout;

    public Asteroid(Main game, World world, int x, int y) {
        this.game = game;
        //collider = new Rectangle(x, y, 40, 40);
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;
        this.velX = (int)(1000*Math.random()) * Math.random()>=.5?1:-1 + 5000;
        this.velY = (int)(1000*Math.random()) * Math.random()>=.5?1:-1 + 4000;

        // Body setup
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        //PolygonShape shape = new PolygonShape();
        //shape.setAsBox(30 / game.PIXELS_PER_METER / 2, 30 / game.PIXELS_PER_METER / 2);
        CircleShape shape = new CircleShape();
        shape.setRadius(20 / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);

        body.applyForceToCenter(velX, velY, true);
        body.setAngularVelocity(rotUp);
    }

    private void draw() {
        /**
        game.batch.draw(texture, collider.x, collider.y,collider.width/2,collider.height/2, collider.width, collider.height,1,1,rot,0,0,860,693,false,false);
        game.playerFont.draw(game.batch, layout, collider.x + (collider.width - layout.width) / 2, collider.y - 10);
         **/

        sprite.setRotation(body.getAngle() / game.DEGREES_TO_RADIANS - 90);
        sprite.setPosition(body.getPosition().x * game.PIXELS_PER_METER - sprite.getWidth() / 2, body.getPosition().y * game.PIXELS_PER_METER - sprite.getHeight() / 2);
        sprite.draw(game.batch);
    }

    public void update(){
        /**
        this.rot+=5;
        this.x += this.velX;
        this.y += this.velY;

        // wraps asteroid around screen
        if(this.x + (int) collider.width <= 0)
            this.x += game.camWidth + collider.width;
        else if(this.x >= game.camWidth)
            this.x = this.x % game.camWidth - (int) collider.width;
        if(this.y + (int) collider.height <=0)
            this.y += game.camHeight + collider.width;
        else if(this.y >= game.camHeight)
            this.y = this.y % game.camHeight - (int) collider.height;
        this.collider.setX(x);
        this.collider.setY(y);
         **/

        this.draw();
    }

    public void dispose(){
        texture.dispose();
    }
}
