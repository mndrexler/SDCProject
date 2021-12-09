package com.sdc.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
    private Texture backgroundTile = new Texture(Gdx.files.internal("spaceTile.jpeg"));
    private SpriteBatch batch;
    private Sprite spriteBG;

    private int tilesTall = 2;
    private int tilesWide = 2;

    private int height;
    private int width;

    public Map(SpriteBatch batch){
       this.batch = batch;
       this.width = backgroundTile.getWidth() * tilesWide;
       this.height = backgroundTile.getHeight() * tilesTall;
       spriteBG = new Sprite(backgroundTile);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public void render(float delta){
        for(int i = 0; i < this.tilesWide; i++){
            for(int j = 0; j < this.tilesTall; j++){
                spriteBG.setPosition(spriteBG.getWidth() * i, spriteBG.getHeight() *j);
                spriteBG.draw(this.batch);
            }
        }
    }

}
