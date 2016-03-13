/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.SkillPad_Primary;
import com.mygdx.entities.StaticEntities.SkillPad_Secondary;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.entities.StaticEntities.walls.NullWall;
import com.mygdx.environments.EnvRoom.RoomArc;
import com.mygdx.environments.Environment;
import com.mygdx.environments.tears.NullWarp;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Bgm;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class EnvRoom_Start extends Environment{

    //sound
    protected SoundObject_Bgm bgm1;
    
    private final Wall_Start startWall;
    private final RoomArc roomArc;
    
    public EnvRoom_Start(int id, int linkid) {
        super(id);
        
        this.linkid = linkid;
        
        fg = MainGame.am.get(ResourceManager.ROOM_SIMPLE_BG1);
        
        beginFC.setTime(0);
        
        setRoomSize();
        
        //sound
        bgm1 = new SoundObject_Bgm(ResourceManager.BGM_NULL_END);
        
        roomArc = new RoomArc(new Vector2((fgx) + width / 2, height * 0.83f), 650 * RATIO, 180 * RATIO);
        startWall = new Wall_Start(
                new Vector2((fgx) + width / 2, height * 0.83f),
                500f * RATIO, 180f * RATIO);
    }
    
    @Override
    public void init(){
        super.init();
        
        
        float border = 25f;
        
        spawnEntity(new NullWall(new Vector2( (fgx) + width/2, height*0.1f),       width/2,  border));//south
        spawnEntity(new NullWall(new Vector2( (fgx) + width/2, height*0.95f),  width/2,  border));//north
        spawnEntity(new NullWall(new Vector2( (fgx) + width*0.92f, height/2),  border, height/2));//east
        spawnEntity(new NullWall(new Vector2( (fgx) + width*0.08f, height/2),   border, height/2));//west
        
        
        spawnEntity(new NullWarp(this.startPos.cpy().scl(PPM), linkid));
        
        /*
        this.spawnEntity(
                new SkillPad(
                        new Vector2(fgx + width * 0.3f, height * 0.55f), 
                        new Skill_GhostJab()));
        */
        
        this.spawnEntity(
                new SkillPad_Primary(
                        new Vector2(fgx + width * 0.3f, height * 0.55f)));
        
        this.spawnEntity(
                new SkillPad_Secondary(
                        new Vector2(fgx + width * 0.7f, height * 0.55f)));
        
        
        
        spawnEntity(new Cyst_Blue(new Vector2(fgx + width * 0.15f, height * 0.4f)));
        spawnEntity(new Cyst_Blue(new Vector2(fgx + width * 0.85f, height * 0.4f)));
        
        spawnEntity(startWall);
        spawnEntity(roomArc);
        
    }
    
    @Override
    public void begin(){
        super.begin();
        
        bgm1.play();
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        super.end(id, time);
    }
    
    public void setRoomSize(){
        width = 1300*RATIO;
        height = 1200*RATIO;
        
        
        fgx = 0;
        fgy = 0;
        fgw = width;
        fgh = height;
        
        startPos = new Vector2(width*0.5f/PPM,height*0.2f/PPM);
        this.setPlayerToStart();
    }
    
    private class Wall_Start extends NullWall {


        public Wall_Start(Vector2 pos, float w, float h) {
            super(pos, w, h);

            //wall texture
            texture = MainGame.am.get(ResourceManager.WALL_START);
        }

    }

}
