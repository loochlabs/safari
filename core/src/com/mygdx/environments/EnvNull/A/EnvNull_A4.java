/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.enemies.En_Worm;
import com.mygdx.environments.EnvNull.NullSection;
import com.mygdx.utilities.Coordinate;

/**
 *
 * @author looch
 */
public class EnvNull_A4 extends EnvNull_A{

    public EnvNull_A4(int id, int linkid) {
        super(id, linkid);
    }
    
    //CROSS LAYOUT
    @Override
    public void createLayout(){
        //center section
        createSection(new Coordinate(0,0));
        
                
        //north seciton
        createSection(new Coordinate(0,1));
        
        /*
        createSection(new Coordinate(-1,1));
        NullSection s = envSections.peek();
        this.spawnEntity(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.9f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.95f,s.getPos().y + s.getHeight()*0.5f)));
        this.addEnemyCount();
        this.spawnEntity(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.95f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        
        
        createSection(new Coordinate(1,1));
        s = envSections.peek();
        this.spawnEntity(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.1f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.15f,s.getPos().y + s.getHeight()*0.5f)));
        this.addEnemyCount();
        this.spawnEntity(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.15f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        
        */
    }
}
