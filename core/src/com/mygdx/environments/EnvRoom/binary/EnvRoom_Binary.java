/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom.binary;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.EnvRoom.RoomArc;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvRoom_Binary extends EnvRoom{

    private Wall_Binary binWall;
    private RoomArc roomArc;
    private CodePad codePad;
    private boolean wallLocked = true;
    
    public EnvRoom_Binary(int id, int linkid) {
        super(id, linkid);
        
        fg = MainGame.am.get(ResourceManager.ROOM_BIN_BG);
        
        roomArc = new RoomArc(new Vector2((fgx) + width/2, height*0.83f), 650*RATIO, 180*RATIO);
        //binWall = new Wall_Binary(new Vector2( (fgx) + width/2, height*0.785f),  width/2,  85*RATIO);
        binWall = new Wall_Binary(new Vector2( (fgx) + width/2, height*0.83f),  500f*RATIO,  180f*RATIO);
        codePad = new CodePad(new Vector2((fgx) + width/2, height*0.435f), width*0.285f, 105f*RATIO, binWall);
        
        EnvVoid env = (EnvVoid)EnvironmentManager.get(linkid);
        Vector2 v = env.createSpawnLocation();
        env.spawnSprite(new CodeHintSprite(binWall.getHintSprite(), v.x, v.y));
    }
    
    @Override
    public void init(){
        super.init();
        
        if(wallLocked){
            spawnEntity(binWall);
        }
        
        spawnEntity(codePad);
        spawnEntity(roomArc);
        
        spawnEntity(new SkillPad(new Vector2( fgx + width * 0.5f, height * 0.8f)));
        
        
    }
    
    public void unlockWall(){
        if(wallLocked){
            wallLocked = false;
        }
    }
    
    /*
    @Override
    public void setRoomSize(){
        width = 1600*RATIO;
        height = 1600*RATIO;
        
        
        fgx = 0;
        fgy = 0;
        fgw = width;
        fgh = height;
        
        startPos = new Vector2(width*0.5f/PPM,height*0.2f/PPM);
        this.setPlayerToStart();
    }*/
    
    
}
