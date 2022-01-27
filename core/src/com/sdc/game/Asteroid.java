package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.*;

public class Asteroid {
    // Game information
    private Main game;

    // Object information
    private double force;
    private double angle;
    private float rot = (float)Math.random() * 10 - 5;
    private float rotUp = (float)(100*Math.random()) * Math.random()>=.5?1:-1 + 200;

    // Graphics and physics
    private Texture texture = new Texture(Gdx.files.internal("asteroid.png"));
    private Sprite sprite = new Sprite(texture);

    //private Rectangle collider;
    private Body body;

    public Asteroid(Main game, World world, Map map) {
        this.game = game;

        int rand = (int)(Math.random() * 4);
        int x;
        int y;
        if (rand == 0) {
            x = (int) (map.getWidth() / game.PIXELS_PER_METER * Math.random());
            y = (int) (map.getHeight() / game.PIXELS_PER_METER) + 10;
            angle = Math.PI + Math.PI * Math.random();
        } else if (rand == 1) {
            x = (int) (map.getWidth() / game.PIXELS_PER_METER) + 10;
            y = (int) (map.getHeight() / game.PIXELS_PER_METER * Math.random());
//            angle =
        } else if (rand == 2) {
            x = (int) (map.getWidth() / game.PIXELS_PER_METER * Math.random());
            y = - 10;
            angle = Math.PI * Math.random();
        } else {
            x = -10;
            y = (int) (map.getHeight() / game.PIXELS_PER_METER * Math.random());
        }
        // Spawning behavior
//        if (x > map.getWidth() / 2) {
//            if (y > map.getHeight() / 2) { // 1st quadrant
//                angle = Math.PI + Math.PI / 2 * Math.random();
//            } else { // 4th quadrant
//                angle = Math.PI / 2 + Math.PI / 2 * Math.random();
//            }
//        } else {
//            if (y > map.getHeight()) { // 2nd quadrant
//                angle = Math.PI * 3 / 2 + Math.PI / 2 * Math.random();
//            } else { // 3rd quadrant
//                angle = Math.PI / 2 * Math.random();
//            }
//        }

        this.force = 5 + (int)(5*Math.random());

        // Body setup
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / game.PIXELS_PER_METER / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        body = world.createBody(bodyDef);
        body.createFixture(fixtureDef);
        shape.dispose();
        body.setUserData(this);

        sprite.setScale(0.06f);
        body.applyForceToCenter((float)(force * Math.cos(angle)), (float)(force * Math.sin(angle)), true);
        body.setAngularVelocity(rot);
        System.out.println(rot);
        System.out.println(body.getAngularVelocity());
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
