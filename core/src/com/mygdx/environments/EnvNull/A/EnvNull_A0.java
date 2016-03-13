/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull.A;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.DynamicEntities.enemies.En_DarklingSm;
import com.mygdx.environments.EnvNull.NullSection;
import com.mygdx.utilities.Coordinate;

/**
 *
 * @author looch
 */
public class EnvNull_A0 extends EnvNull_A{

    public EnvNull_A0(int id, int linkid) {
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
        NullSection s = envSections.peek();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.55f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        
        //east seciton
        createSection(new Coordinate(1,0));
        
        s = envSections.peek();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.55f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        
        //south seciton
        createSection(new Coordinate(0,-1));
        
        s = envSections.peek();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.55f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        
        //west seciton
        createSection(new Coordinate(-1,0));
        
        s = envSections.peek();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.55f)));
        this.addEnemyCount();
        this.spawnEntity(new En_DarklingSm(new Vector2(s.getPos().x + s.getWidth()*0.55f,s.getPos().y + s.getHeight()*0.4f)));
        this.addEnemyCount();
        */
    }
    
}
