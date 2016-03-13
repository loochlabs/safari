/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.environments.Environment;
import static com.mygdx.game.MainGame.RATIO;
import java.util.Random;

/**
 *
 * @author looch
 */
public class SpectralSection_Main {/*extends SpectralSection{

    //room1;    trap
    //room2;    connection
    //hallway;
    
    private Array<SpectralPath> paths = new Array<SpectralPath>();
    
    private Random rng = new Random();
    
    //provides a Vec2 for the next section in EnvSpectral
    private Vector2 connection_pos;
    
    public Vector2 getConnectionPos() { return connection_pos; }
    
    public SpectralSection_Main(Vector2 pos, float width, float height, Environment env) {
        super(pos, width, height, env);
        
        sideTypes[0] = WallType.CONNECTED;
        
        connection_pos = pos.cpy();
        createRooms();
    }
    
    @Override
    public void init(){
        super.init();
        
        for(SpectralPath p : paths){
            p.init();
        }
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        for(SpectralPath p : paths){
            p.render(sb);
        }
    }
    
    private void createRooms(){
        //create trap
        //create path
        
        float vwall_width = width / 8;
        float vwall_height = height;
        float hwall_width = width/2;
        float hwall_height = height / 4;
        
        
        //initial cooridoors
        paths.add(new SpectralPath(new Vector2(pos.x + width/2, pos.y + height), width/2, height, env));
        paths.add(new SpectralPath(new Vector2(pos.x , pos.y + height), width/2, height, env));
        
        
        //create path, 50% chance on right, left
        boolean pathRight = rng.nextFloat() < 0.5f ? true : false;
        
        //generate path on right
        if(pathRight){
            paths.add(new SpectralPath(new Vector2(pos.x + width/2, pos.y + height*2), width/2, height, env));
            paths.add(new SpectralPath_Trap(new Vector2(pos.x , pos.y + height*2), width/2, height, env));
        }else{
            paths.add(new SpectralPath(new Vector2(pos.x , pos.y + height*2), width/2, height, env));
            paths.add(new SpectralPath_Trap(new Vector2(pos.x + width/2, pos.y + height*2), width/2, height, env));
        }
        
        connection_pos.add(new Vector2(0, height*3));
    }
    */
}
