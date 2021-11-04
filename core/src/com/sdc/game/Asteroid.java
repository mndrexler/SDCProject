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

    private Texture texture;
    private com.badlogic.gdx.math.Rectangle collider;

    private BitmapFont font;
    private GlyphLayout layout;

    public Asteroid(Main game, Texture texture) {
        this.game = game;
        this.texture = texture;
        collider = new Rectangle(400, 300, 40, 40);
        font = new BitmapFont();
        layout = new GlyphLayout();
    }

    public void draw() {
        game.batch.draw(texture, collider.x, collider.y, collider.width, collider.height);
        font.draw(game.batch, layout, collider.x + (collider.width - layout.width) / 2, collider.y - 10);
    }

}
