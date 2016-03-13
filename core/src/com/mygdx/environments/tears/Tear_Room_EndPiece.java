/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.breakable.BreakableObject;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author saynt
 */
public class Tear_Room_EndPiece extends TearPortal{
    
    public Tear_Room_EndPiece(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_EndPiece(id, linkid);
        EnvironmentManager.add(warpenv);
        
        
    }
    
    /**
     *
     *
     * Description: -Contains 1 end piece in rewardPool -Required for
     * MurphyInitial EnvVoid
     *
     */
    public class EnvRoom_EndPiece extends EnvRoom {

        public EnvRoom_EndPiece(int id, int linkid) {
            super(id, linkid);

            fg = MainGame.am.get(ResourceManager.ROOM_SIMPLE_BG1);
        }

        @Override
        public void init() {
            super.init();

            BreakableObject b = (BreakableObject) spawnEntity(
                    new Cyst_Blue(new Vector2(fgx + width * 0.5f, height * 0.6f)));

            //add reward item to this breakable object
            for (Pickup p : rewardItems) {
                b.addReward(p);
            }
            
            spawnEntity(
                    new Cyst_Blue(new Vector2(fgx + width * 0.4f, height * 0.5f)));
            spawnEntity(
                    new Cyst_Blue(new Vector2(fgx + width * 0.4f, height * 0.7f)));
            spawnEntity(
                    new Cyst_Blue(new Vector2(fgx + width * 0.6f, height * 0.5f)));
            spawnEntity(
                    new Cyst_Blue(new Vector2(fgx + width * 0.6f, height * 0.7f)));
        }

    }
    
}
