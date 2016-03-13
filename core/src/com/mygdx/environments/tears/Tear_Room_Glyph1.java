/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.entities.pickups.Pickup_Item;
import com.mygdx.entities.pickups.Pickup_Key;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.EnvRoom.EnvRoom_Glyph1;
import com.mygdx.environments.EnvRoom.RoomArc;
import com.mygdx.environments.EnvRoom.Wall_Glyph1;
import com.mygdx.environments.EnvRoom.Wall_Lock;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class Tear_Room_Glyph1 extends TearPortal {

    public Tear_Room_Glyph1(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_Glyph1_TEST(id, linkid);
        EnvironmentManager.add(warpenv);
        
        
    }
    
    //TODO: Move Glyph wall lock class to be nested in this environment
    
    public class EnvRoom_Glyph1_TEST extends EnvRoom {

        private Pickup_Key key;
        private Wall_Lock glyphWall;
        private RoomArc roomArc;
        private boolean glyphLocked = true;

        public Pickup_Item getKey() {
            return key;
        }

        public EnvRoom_Glyph1_TEST(int id, int linkid) {
            super(id, linkid);

            roomArc = new RoomArc(new Vector2((fgx) + width / 2, height * 0.83f), 650 * RATIO, 180 * RATIO);
            glyphWall = new Wall_Glyph1(new Vector2((fgx) + width / 2, height * 0.785f), width / 2, 85 * RATIO);
            key = glyphWall.getKey();

        }

        @Override
        public void init() {
            super.init();

            //glyph code
            if (glyphLocked) {
                spawnEntity(glyphWall);
            }

            spawnEntity(new SkillPad(new Vector2(fgx + width * 0.5f, height * 0.8f)));

            spawnEntity(roomArc);
        }

        //Wall unlocks once player uses the key
        public void unlockWall() {
            if (glyphLocked) {

                glyphLocked = false;
            }
        }
    }
    
    
}
