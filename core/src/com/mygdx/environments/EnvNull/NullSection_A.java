/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.Coordinate;

/**
 *
 * @author looch
 */
public class NullSection_A extends NullSection{

    public NullSection_A(Vector2 pos, float width, float height, EnvNull env, Coordinate coord, int depth) {
        super(pos, width, height, env, coord, depth);
    }
    
    /*
    @Override
    public void setTexture(){
        
        if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1000);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0100);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0010);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0001);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1100);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1010);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1001);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0110);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0101);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0011);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1110);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1101);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1011);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_0111);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_A_1111);
        }
        
    }*/
    
}
