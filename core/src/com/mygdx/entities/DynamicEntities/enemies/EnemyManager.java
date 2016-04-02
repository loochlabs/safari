/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.Entity;
import com.mygdx.environments.EnvNull.NullSection;
import java.util.Random;

/**
 *
 * @author looch
 * 
 * Class Description:  Manage all enemies
 * 
 */
public class EnemyManager {
    
    public static Array<Entity> getGroup(int dif, NullSection sec){
        Array<Entity> e = new Array<Entity>();
        switch(dif){
            case 0:
                e = getTier0(sec);
                break;
            case 1:
                e = getTier1(sec);
                break;
            case 2:
                e = getTier2(sec);
                break;
                
            case 3:
                e = getTier3(sec);
                break;
                
            case 4:
                e = getTier4(sec);
                break;
            default:
                
                e = getTier0(sec);
                break;
            
        }
        
        return e;
    }
    
    private static Array<Entity> getTier0(NullSection s){
        Array<Entity> e = new Array<Entity>();
        
        Random rng = new Random();
        int index = (int)(5 * rng.nextFloat());
        
        switch(index){
            case 0:
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth()*0.2f,s.getPos().y + s.getHeight()*0.8f)));
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth()*0.8f,s.getPos().y + s.getHeight()*0.8f)));
                
                break;
            case 1:
                
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth()*0.2f,s.getPos().y + s.getHeight()*0.8f)));
                
                break;
                
            case 2:
                e.add(new En_Peeker(new Vector2(s.getPos().x + s.getWidth()*0.6f, s.getPos().y + s.getHeight()*0.5f)));
                
                break;
                
            case 3:
                e.add(new En_Sloober(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.6f)));
                e.add(new En_Sloober(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.65f)));
            case 4:
                e.add(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.6f)));
                e.add(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.65f)));
            default:
                break;
        }
        return e;
    }
    
    private static Array<Entity> getTier1(NullSection s){
        Array<Entity> e = new Array<Entity>();
        
        Random rng = new Random();
        int index = (int)(5 * rng.nextFloat());
        
        switch(index){
            case 0:
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth()*0.2f,s.getPos().y + s.getHeight()*0.8f)));
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth()*0.8f,s.getPos().y + s.getHeight()*0.8f)));
                
                break;
            case 1:
                
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth()*0.2f,s.getPos().y + s.getHeight()*0.8f)));
                
                break;
                
            case 2:
                e.add(new En_Peeker(new Vector2(s.getPos().x + s.getWidth()*0.6f, s.getPos().y + s.getHeight()*0.5f)));
                
                break;
                
            case 3:
                e.add(new En_Sloober(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.6f)));
                e.add(new En_Sloober(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.65f)));
            case 4:
                e.add(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.4f,s.getPos().y + s.getHeight()*0.6f)));
                e.add(new En_Worm(new Vector2(s.getPos().x + s.getWidth()*0.5f,s.getPos().y + s.getHeight()*0.65f)));
            default:
                break;
        }
        return e;
    }
    
    private static Array<Entity> getTier2(NullSection s) {
        Array<Entity> e = new Array<Entity>();

        Random rng = new Random();
        int index = (int) (5 * rng.nextFloat());

        switch (index) {
            case 0:
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth() * 0.2f, s.getPos().y + s.getHeight() * 0.8f)));
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth() * 0.8f, s.getPos().y + s.getHeight() * 0.8f)));

                break;
            default:
                break;
        }
        return e;
    }

    private static Array<Entity> getTier3(NullSection s) {
        Array<Entity> e = new Array<Entity>();

        Random rng = new Random();
        int index = (int) (1 * rng.nextFloat());

        switch (index) {
            case 0:
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth() * 0.2f, s.getPos().y + s.getHeight() * 0.8f)));
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth() * 0.8f, s.getPos().y + s.getHeight() * 0.8f)));

                break;
            default:
                break;
        }
        return e;
    }

    private static Array<Entity> getTier4(NullSection s) {
        Array<Entity> e = new Array<Entity>();

        Random rng = new Random();
        int index = (int) (1 * rng.nextFloat());

        switch (index) {
            case 0:
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth() * 0.2f, s.getPos().y + s.getHeight() * 0.8f)));
                e.add(new En_Goober(new Vector2(s.getPos().x + s.getWidth() * 0.8f, s.getPos().y + s.getHeight() * 0.8f)));

                break;
            default:
                break;
        }
        return e;
    }
    
}
