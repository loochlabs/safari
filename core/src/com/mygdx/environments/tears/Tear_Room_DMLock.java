/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.EnvRoom.RoomArc;
import com.mygdx.environments.EnvRoom.Wall_DMLock;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Sfx;

/**
 *
 * @author saynt
 */
public class Tear_Room_DMLock extends TearPortal{
    
    public Tear_Room_DMLock(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_DMLock(id, linkid);
        EnvironmentManager.add(warpenv);
        
        
          
    }
    
    public Tear_Room_DMLock(Vector2 pos,int linkid, int cost) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_DMLock(id, linkid, cost);
        EnvironmentManager.add(warpenv);
          
    }
    
    public class EnvRoom_DMLock extends EnvRoom {

        private final Wall_DMLock lockWall;
        private final RoomArc roomArc;
        private boolean glyphLocked = true;
        private SoundObject_Sfx activateSound;

        public EnvRoom_DMLock(int id, int linkid) {
            super(id, linkid);

            fg = MainGame.am.get(ResourceManager.ROOM_BIN_BG);
            roomArc = new RoomArc(new Vector2((fgx) + width / 2, height * 0.83f), 650 * RATIO, 180 * RATIO);
            lockWall = new Wall_DMLock(
                    new Vector2((fgx) + width / 2, height * 0.83f),
                    500f * RATIO, 180f * RATIO,
                    17 + rng.nextInt(10));

            //sound
            activateSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP);
        }

        public EnvRoom_DMLock(int id, int linkid, int cost) {
            super(id, linkid);

            fg = MainGame.am.get(ResourceManager.ROOM_BIN_BG);
            roomArc = new RoomArc(new Vector2((fgx) + width / 2, height * 0.83f), 650 * RATIO, 180 * RATIO);
            lockWall = new Wall_DMLock(
                    new Vector2((fgx) + width / 2, height * 0.83f),
                    500f * RATIO, 180f * RATIO,
                    cost);

            //sound
            activateSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP);
        }

        @Override
        public void init() {
            super.init();

            //glyph code
            if (glyphLocked) {
                spawnEntity(lockWall);
            }

            spawnEntity(new SkillPad(new Vector2(fgx + width * 0.5f, height * 0.8f)));

            spawnEntity(roomArc);

        }

        //Wall unlocks once player pays DM price
        public void unlockWall() {
            if (glyphLocked) {
                glyphLocked = false;
                activateSound.play(false);
            }

        }

    }
}
