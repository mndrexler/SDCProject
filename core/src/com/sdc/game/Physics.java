package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class Physics {
    public World world;

    public Physics() {
        world = new World(new Vector2(0, 0), true);
    }

    // our game logic here
    public void logicStep(float delta){
        world.step(delta, 3, 3);
    }
}
