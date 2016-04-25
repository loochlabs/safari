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
public class NullSection_R extends NullSection{

    public NullSection_R(Vector2 pos, float width, float height, EnvNull env, Coordinate coord, int depth) {
        super(pos, width, height, env, coord, depth);
    }
    
    /*
    @Override
    public void setTexture(){
        
        Sides
        0 - Wall
        1 - Connected
        2 - Pit
        
        1000 
        0100 
        0010 
        0001 
        1200 
        1020
        1002
        2100
        0120
        0102
        2010
        0210
        0012
        2001
        0201
        0021
        1100
        1010
        1001
        0110
        0101
        0011
        1120
        1102
        1210
        1012
        1201
        1021
        2110
        0112
        2101
        0121
        2011
        0211
        
        
        
        
        if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1000);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1000);//2000
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0100);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0010);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0001);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1200);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1020);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1002);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2100);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0120);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0102);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2010);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0210);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0012);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2001);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0201);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0021);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1100);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1010);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1001);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0110);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0101);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0011);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1120);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1102);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1210);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1012);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1201);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1021);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2110);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.PIT_HIGHER){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0112);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2101);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.PIT_HIGHER && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0121);
        }else if(sideTypes[0] == WallType.PIT_HIGHER && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_2011);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.PIT_HIGHER && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0211);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.WALL){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1110);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.WALL && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1101);
        }else if(sideTypes[0] == WallType.CONNECTED && sideTypes[1] == WallType.WALL && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_1011);
        }else if(sideTypes[0] == WallType.WALL && sideTypes[1] == WallType.CONNECTED && sideTypes[2] == WallType.CONNECTED && sideTypes[3] == WallType.CONNECTED){
            bg = MainGame.am.get(ResourceManager.NULL_SECTION_0111);
        }
        
    }
    */
}
