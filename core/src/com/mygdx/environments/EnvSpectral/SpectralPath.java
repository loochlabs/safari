/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.environments.Environment;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class SpectralPath {

    private Vector2 pos;
    private float width, height;
    protected Environment env;
    private Texture bg;
    
    private BlankWall leftWall, rightWall;
    
    public SpectralPath(Vector2 pos, float width, float height, Environment env) {
        
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.env = env;
        
        bg = MainGame.am.get(ResourceManager.SP_SECTION_PH);
        
        leftWall = new BlankWall(new Vector2(pos.x + width*0.25f/2, pos.y + height/2), width*0.25f/2, height/2);
        rightWall = new BlankWall(new Vector2(pos.x + width - width*0.25f/2, pos.y + height/2), width*0.25f/2, height/2);
    }
    
    public void init(){
        env.spawnEntity(leftWall);
        env.spawnEntity(rightWall);
    }
    
    public void render(SpriteBatch sb){
        sb.draw(bg, pos.x + width*0.25f, pos.y, width/2 , height);
    }
    
}
