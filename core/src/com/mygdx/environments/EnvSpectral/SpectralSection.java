/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.environments.EnvSection;
import com.mygdx.environments.Environment;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.Coordinate;

/**
 *
 * @author looch
 */
public class SpectralSection {/*extends EnvSection{

    public SpectralSection(Vector2 pos, float width, float height, Environment env) {
        super(pos, width, height, env, new Coordinate(0,0));
        
        bg = MainGame.am.get(ResourceManager.SP_SECTION_PH);
    }
    
    
    @Override 
    public void init(){
        //north wall
        if(sideTypes[0] == WallType.WALL){
            //solid wall
            env.spawnEntity(new NullWall(new Vector2(pos.x + width/2, pos.y + height ),  width/2,  15f*RATIO));//north
        }
       
        //east wall
        if(sideTypes[1] == WallType.WALL){
            //solid wall
            env.spawnEntity(new NullWall(new Vector2(pos.x + width , pos.y + height/2), 15f*RATIO, height/2));
        }
        
        //south wall
        if(sideTypes[2] == WallType.WALL){
            //solid wall
            env.spawnEntity(new NullWall(new Vector2(pos.x + width/2, pos.y), width/2,  15f*RATIO));
        }
        
        
        //west wall
        if(sideTypes[3] == WallType.WALL){
            //solid wall
            env.spawnEntity(new NullWall(new Vector2(pos.x , pos.y + height/2), 15f*RATIO, height/2));//west
        }
        
        setTexture();
    }
    
    @Override
    public void setTexture(){
        
        if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1000);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0100);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0010);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0001);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1100);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1010);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1001);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0110);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0101);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0011);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1110);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1101);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_1011);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.SP_SECTION_0111);
        }
        
    }
    
    */
}
