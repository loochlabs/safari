/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.entities.pickups.statups.Pickup_Statup;
import com.mygdx.entities.pickups.statups.Pickup_Statup_Damage;
import com.mygdx.entities.pickups.statups.Pickup_Statup_Energy;
import com.mygdx.entities.pickups.statups.Pickup_Statup_Life;
import com.mygdx.entities.pickups.statups.Pickup_Statup_Speed;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author saynt
 */
public class Tear_Room_Statup extends TearPortal{
    
    public Tear_Room_Statup(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_Statup(id, linkid);
        EnvironmentManager.add(warpenv);
        
        
    }
    
    private class EnvRoom_Statup extends EnvRoom {

        private final Array<Pickup_Statup> statupPool = new Array<Pickup_Statup>();

        public EnvRoom_Statup(int id, int linkid) {
            super(id, linkid);

            fg = MainGame.am.get(ResourceManager.ROOM_SIMPLE_BG1);

            statupPool.add(new Pickup_Statup_Life());
            statupPool.add(new Pickup_Statup_Energy());
            statupPool.add(new Pickup_Statup_Damage());
            statupPool.add(new Pickup_Statup_Speed());
        }

        @Override
        public void init() {
            super.init();

            //spawn random statup
            generateStatup(new Vector2(fgx + width * 0.5f, height * 0.65f));

            spawnEntity(new Cyst_Blue(new Vector2(fgx + width * 0.2f, height * 0.65f)));
            spawnEntity(new Cyst_Blue(new Vector2(fgx + width * 0.8f, height * 0.65f)));
        }

        private void generateStatup(Vector2 pos) {

            //generate random statup from pool
            Pickup_Statup statup = statupPool.random();
            statup.setPosition(pos);
            Pickup_Statup p = (Pickup_Statup) EnvironmentManager.currentEnv.spawnEntity(statup);

        }
}
    
}
