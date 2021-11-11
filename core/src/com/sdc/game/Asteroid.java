package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Texture;
import java.awt.event.MouseEvent;

public class Asteroid {

    private Main game;

    private Texture texture = new Texture(Gdx.files.internal("asteroid.png"));
    private Rectangle collider;

    private GlyphLayout layout;


    private int x,y,velX,velY;
    private float rot = 0;
    private float rotUp = (float)(5*Math.random()) * Math.random()>=.5?1:-1;

    public Asteroid(Main game,int x, int y) {
        this.game = game;
        collider = new Rectangle(x, y, 40, 40);
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;
        this.velX = (int)(5*Math.random()) * Math.random()>=.5?1:-1;
        this.velY = (int)(4*Math.random()) * Math.random()>=.5?1:-1;
    }

    private void draw() {
        game.batch.draw(texture, collider.x, collider.y,collider.width/2,collider.height/2, collider.width, collider.height,1,1,rot,0,0,860,693,false,false);
        game.playerFont.draw(game.batch, layout, collider.x + (collider.width - layout.width) / 2, collider.y - 10);
    }

    public void update(float delta){
        this.rot+=5;
        this.x += this.velX;
        this.y += this.velY;
        // wraps asteroid around screen
        if(this.x + (int) collider.width <= 0)
            this.x += Gdx.graphics.getWidth() + collider.width;
        else if(this.x >= Gdx.graphics.getWidth())
            this.x = this.x % Gdx.graphics.getWidth() - (int) collider.width;
        if(this.y + (int) collider.height <=0)
            this.y += Gdx.graphics.getHeight() + collider.width;
        else if(this.y >= Gdx.graphics.getHeight())
            this.y = this.y % Gdx.graphics.getHeight() - (int) collider.height;
        this.collider.setX(x);
        this.collider.setY(y);
        this.draw();
    }

    public void dispose(){
        texture.dispose();
    }
}
