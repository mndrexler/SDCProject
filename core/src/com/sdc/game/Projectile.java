package com.sdc.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Projectile {
    private int x;
    private int y;
    private Vector2 direction;
    private Player parent;
    private Texture texture;
    private Rectangle collider;

    public Projectile(int x, int y, Vector2 direction, Player parent) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.parent = parent;
    }
}
