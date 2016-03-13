/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class Tear_Room_Simple extends TearPortal{
    
    public Tear_Room_Simple(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_Simple_TEST(id, linkid);
        EnvironmentManager.add(warpenv);
        
        
    }
    
    //TODO: get rid of TEST in name
    
    private class EnvRoom_Simple_TEST extends EnvRoom {

        public EnvRoom_Simple_TEST(int id, int linkid) {
            super(id, linkid);

            fg = MainGame.am.get(ResourceManager.ROOM_SIMPLE_BG1);
        }

        @Override
        public void init() {
            super.init();

            spawnEntity(new SkillPad(new Vector2(fgx + width * 0.5f, height * 0.8f)));

            spawnEntity(new Cyst_Blue(new Vector2(fgx + width * 0.2f, height * 0.65f)));
            spawnEntity(new Cyst_Blue(new Vector2(fgx + width * 0.8f, height * 0.65f)));
        }

    }

}
