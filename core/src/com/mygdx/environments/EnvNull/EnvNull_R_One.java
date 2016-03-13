/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.mygdx.game.MainGame;
import com.mygdx.managers.ResourceManager;

/**
 *
 * @author looch
 */
public class EnvNull_R_One extends EnvNull_R{

    
    
    public EnvNull_R_One(int id, int linkid) {
        super(id, linkid, 0);
        
        bg = MainGame.am.get(ResourceManager.NULL_BG1);
        
    }
    
    
}
