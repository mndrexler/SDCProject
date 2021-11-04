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
    private com.badlogic.gdx.math.Rectangle collider;

    private GlyphLayout layout;


    private int x,y,velX,velY;
    public Asteroid(Main game,int x, int y) {
        this.game = game;
        collider = new Rectangle(x, y, 40, 40);
        layout = new GlyphLayout();
        this.x = x;
        this.y = y;
        this.velX = (int)(8*Math.random() - 4);
        this.velY = (int)(8*Math.random() - 4);
    }

    private void draw() {
        game.batch.draw(texture, collider.x, collider.y, collider.width, collider.height);
        game.playerFont.draw(game.batch, layout, collider.x + (collider.width - layout.width) / 2, collider.y - 10);
    }

    public void update(float delta){
        this.x += this.velX;
        this.y += this.velY;
        this.x = this.x % Gdx.graphics.getWidth();
        this.y = this.y % Gdx.graphics.getHeight();
        this.collider.setX(x);
        this.collider.setY(y);
        this.draw();
    }

    public void dispose(){
        texture.dispose();
    }
}
