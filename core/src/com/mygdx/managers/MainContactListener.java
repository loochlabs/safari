/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.mygdx.entities.Entity;
import com.mygdx.environments.Environment;
import com.mygdx.screen.GameScreen;
import java.util.ArrayList;

/**
 *
 * @author looch
 */
public class MainContactListener implements ContactListener{

    private final Environment env;
    private final ArrayList<Entity> entities;
    
    public MainContactListener(Environment e){
        this.env = e;
        entities = env.getEntities();
    }
    
    @Override
    public void beginContact(Contact contact) {
        
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
      
        //Stability Edit (2/11/16)
        //generallizing fa,fb null checks
        if(fa == null || fa.getUserData() == null || fb == null || fb.getUserData() == null ) return;
        
        
        try{
            
            //********************
            //   PLAYER ATT targets list update
            //*********************
     
            
            //String data;
            for (Entity e : entities) {
                
                if (e.getSensorData() != null && fa.getUserData().equals(e.getSensorData())) {
                    String[] str = {"begin", e.getSensorData().toString(), fb.getUserData().toString()};
                    e.alert(str);
                } else if(e.getUserData().equals(fa.getUserData())){
                    String[] str = {"begin", fa.getUserData().toString(), fb.getUserData().toString()};
                    e.alert(str);
                }

                if (e.getSensorData() != null && fb.getUserData().equals(e.getSensorData())) {
                    String[] str = {"begin", e.getSensorData().toString(), fa.getUserData().toString()};
                    e.alert(str);
                } else if(e.getUserData().equals(fb.getUserData())){
                    String[] str = {"begin", fb.getUserData().toString(), fa.getUserData().toString()};
                    e.alert(str);
                }

   
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
         
    }

    @Override
    public void endContact(Contact contact) {
        
        Fixture fa = contact.getFixtureA();
        Fixture fb = contact.getFixtureB();
        
        //Stability Edit (2/11/16)
        //generallizing fa,fb null checks
        if(fa == null || fa.getUserData() == null || fb == null || fb.getUserData() == null ) return;
        
        try{
            

            
            for(Entity e : entities){
                if (e.getSensorData() != null && fa.getUserData().equals(e.getSensorData())) {
                    String[] str = {"end", e.getSensorData().toString(), fb.getUserData().toString()};
                    e.alert(str);
                } else if(e.getUserData().equals(fa.getUserData())){
                    String[] str = {"end", fa.getUserData().toString(), fb.getUserData().toString()};
                    e.alert(str);
                }

                if (e.getSensorData() != null && fb.getUserData().equals(e.getSensorData())) {
                    String[] str = {"end", e.getSensorData().toString(), fa.getUserData().toString()};
                    e.alert(str);
                } else if(e.getUserData().equals(fb.getUserData())){
                    String[] str = {"end", fb.getUserData().toString(), fa.getUserData().toString()};
                    e.alert(str);
                }
            }
            
        
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
    
}
