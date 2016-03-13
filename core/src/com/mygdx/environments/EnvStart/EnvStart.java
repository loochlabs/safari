/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.Environment;
import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * Description:  Start env for each character
 * 
 */
public class EnvStart extends Environment{

    public EnvStart(int id) {
        super(id);
        
        startPos = new Vector2(200/PPM,200/PPM);
        this.setPlayerToStart();
        
        fg = MainGame.am.get(ResourceManager.NULL_PH);
        fgx = 0;
        fgy = 0;
        fgw = 1000;
        fgh = 1000;
        
        
    }
    
    @Override
    public void init(){
        super.init();
        
        createStartSections();
    }
    
    public void createStartSections(){
        //create bounding walls
        
        
    }
    
}
