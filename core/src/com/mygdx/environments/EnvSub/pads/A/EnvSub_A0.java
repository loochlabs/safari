/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.pads.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvSub.pads.EndPad;
import com.mygdx.environments.EnvSub.pads.EndWarp;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvSub_A0 extends EnvSub{

    public EnvSub_A0(int id, int linkid, EndWarp endPad) {
        super(id, linkid, endPad);
    }
    
    @Override
    public void createPad(){
        pad = new EndPad(new Vector2(bd.position.x*PPM, bd.position.y*PPM), 3, -30);
    }
    
}
