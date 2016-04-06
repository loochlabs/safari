/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid.pads.test;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvVoid.pads.EndWarp;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvSub_Test extends EnvSub{

    public EnvSub_Test(int id, int linkid, EndWarp endPad) {
        super(id, linkid, endPad);
    }
    
    @Override
    public void createPad() {
        pad = new EndPad(new Vector2(bd.position.x*PPM, bd.position.y*PPM), 4, -30);
    }
    
}
