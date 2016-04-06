/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.demo.demo1;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.pads.EndWarp;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvSub_Demo1 extends EnvSub{

    public EnvSub_Demo1(int id, int linkid, EndWarp endPad) {
        super(id, linkid, endPad);
    }

    @Override
    public void createPad() {
        pad = new EndPad_Demo1(new Vector2(bd.position.x*PPM, bd.position.y*PPM), 3);
    }
    
}
