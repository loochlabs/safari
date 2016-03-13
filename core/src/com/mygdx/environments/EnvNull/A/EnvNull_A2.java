/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.enemies.En_DarklingMed;
import com.mygdx.environments.EnvNull.NullSection;
import com.mygdx.utilities.Coordinate;

/**
 *
 * @author looch
 */
public class EnvNull_A2 extends EnvNull_A{

    public EnvNull_A2(int id, int linkid) {
        super(id, linkid);
    }
    
    //CROSS LAYOUT
    @Override
    public void createLayout(){
        //center section
        createSection(new Coordinate(0,0));
        
        createSection(new Coordinate(0,1));
        createSection(new Coordinate(0,2));
        /*
        NullSection s = envSections.peek();
        this.spawnEntity(new En_DarklingMed(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingMed(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingMed(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.5f)));
        this.addEnemyCount();
        
        
        /*
        s = envSections.peek();
        this.spawnEntity(new En_Goober2(new Vector2(s.getPos().x + s.getWidth()*0.3f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        
        s = envSections.peek();
        this.spawnEntity(new En_Goober2(new Vector2(s.getPos().x + s.getWidth()*0.3f,s.getPos().y + s.getHeight()*0.3f)));
        this.addEnemyCount();
        
        s = envSections.peek();
        this.spawnEntity(new En_Goober2(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.5f)));
        this.addEnemyCount();
                */
        
        
    }
}
