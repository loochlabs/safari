/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.tears;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvRoom.binary.EnvRoom_Binary;
import com.mygdx.environments.EnvironmentManager;

/**
 *
 * @author looch
 */
public class Tear_Room_Binary extends TearPortal{
    
    public Tear_Room_Binary(Vector2 pos,int linkid) {
        super(pos, linkid);
        
        teardata = "bosst_" + id;
        userdata = teardata;
        
        warpenv = new EnvRoom_Binary(id, linkid);
        EnvironmentManager.add(warpenv);
        
        
    }
}
