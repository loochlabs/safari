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
import com.mygdx.entities.DynamicEntities.enemies.EnemyEntity;
import com.mygdx.entities.Entity;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
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
     
            if (fa.getUserData().toString().contains("att_norm")) {
                for (Entity e : entities) {
                    if (e.getUserData() != null 
                            && e.getUserData().equals(fb.getUserData())) {

                        GameScreen.player.addTarget(e);
                    }
                }

            }

            if (fb.getUserData().toString().contains("att_norm")) {
                for (Entity e : entities) {
                    if (e.getUserData() != null 
                            && e.getUserData().equals(fa.getUserData())) {

                        GameScreen.player.addTarget(e);
                    }
                }

            }

            /**
             * ***********************
             * WARP
             */
            if (fa.getUserData().toString().contains("warp_")) {
                //warp to new environment
                System.out.println("@MainContactListener warp");

                for (Entity e : entities) {
                    if (e.getUserData().equals(fa.getUserData())) {
                        e.alert();
                    }
                }
            }

            if (fb.getUserData().toString().contains("warp_")) {
                //warp to new environment
                System.out.println("@MainContactLSistener warp");

                for (Entity e : entities) {
                    if (e.getUserData().equals(fb.getUserData())) {
                        e.alert();
                    }
                }
            }

            //glyph wall
            if (fa.getUserData().toString().contains("action_")) {
                //warp to new environment

                for (Entity e : entities) {
                    if (e.getUserData().toString().contains(fa.getUserData().toString())) {
                        e.alert("active");
                        e.alert(fb.getUserData().toString());
                    }
                }
            }

            if (fb.getUserData().toString().contains("action_")) {
                //warp to new environment

                for (Entity e : entities) {
                    if (e.getUserData().toString().contains(fb.getUserData().toString())) {
                        e.alert("active");
                        e.alert(fa.getUserData().toString());
                    }
                }
            }

            /**
             * ***************************
             * FRAGMENTS
             */
            if (fa.getUserData().toString().contains("fragment")) {

                if (fb.getUserData().toString().contains("player_")) {
                    for (Entity e : entities) {
                        if (e.getUserData().equals(fa.getUserData())) {

                            e.alert();
                        }
                    }
                }
            }

            if (fb.getUserData().toString().contains("fragment")) {
                if (fa.getUserData().toString().contains("player_")) {
                    for (Entity e : entities) {
                        if (e.getUserData().equals(fb.getUserData())) {

                            e.alert();
                        }
                    }
                }
            }

            //*****************
            //  ENEMY DAMAGE TO PLAYER
            //TODO: Fork project for Demo, EnemyEntity2 AI dev
            if (fa.getUserData().toString().contains("en_att_sensor")
                    && fb.getUserData().toString().contains("player_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().contains("en_")) {
                        EnemyEntity ent = (EnemyEntity) e;
                        if (ent.getSensorData().equals(fa.getUserData())) {
                            e.alert("en_damage_begin");
                        }
                    }

                }
            }

            if (fb.getUserData().toString().contains("en_att_sensor")
                    && fa.getUserData().toString().contains("player_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().contains("en_")) {
                        EnemyEntity ent = (EnemyEntity) e;
                        if (ent.getSensorData().equals(fb.getUserData())) {
                            e.alert("en_damage_begin");
                        }
                    }
                }
            }

            //**********************
            //  NULL SECTION FALLING ALERT
            //alert section that player colided with fall sensor
            if (fa.getUserData().toString().contains("fallsensor_")) {

                if (fb.getUserData().toString().contains("player_")) {
                    for (Entity e : entities) {
                        if (e.getUserData().equals(fa.getUserData())) {
                            e.alert();
                        }
                    }
                } else {
                    for (Entity e : entities) {
                        if (e.getUserData().equals(fb.getUserData())) {
                            e.fall();
                        }
                    }
                }

                System.out.println("@MainContactListener fall");
            }

            if (fb.getUserData().toString().contains("fallsensor_")) {

                if (fa.getUserData().toString().contains("player_")) {
                    for (Entity e : entities) {
                        if (e.getUserData().equals(fb.getUserData())) {
                            e.alert();
                        }
                    }
                } else {
                    for (Entity e : entities) {
                        if (e.getUserData().equals(fa.getUserData())) {
                            e.fall();
                        }
                    }
                }

                System.out.println("@MainContactListener fall");
            }

            //VOID
            //EnvSub end
            if (fa.getUserData().toString().contains("end_pad_")
                    && fb.getUserData().toString().contains("player_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().equals(fa.getUserData())) {
                        e.alert();
                    }

                }
            }

            if (fb.getUserData().toString().contains("end_pad_")
                    && fa.getUserData().toString().contains("player_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().equals(fb.getUserData())) {
                        e.alert();
                    }
                }
            }

            //**************
            //  ENEMY BULLETS
            if (fa.getUserData().toString().contains("bullet_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().equals(fa.getUserData())) {
                        e.alert(fb.getUserData().toString());
                    }

                }
            }

            if (fb.getUserData().toString().contains("bullet_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().equals(fb.getUserData())) {
                        e.alert(fa.getUserData().toString());
                    }
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
        
            if (fa.getUserData().toString().contains("att_norm")) {
                for (Entity e : entities) {
                    if (e.getUserData() != null && e.getUserData().equals(fb.getUserData())) {
                        GameScreen.player.removeTarget(e);
                    }
                }
            }

            if (fb.getUserData().toString().contains("att_norm")) {
                for (Entity e : entities) {
                    if (e.getUserData() != null && e.getUserData().equals(fa.getUserData())) {
                        GameScreen.player.removeTarget(e);
                    }
                }
            }

            //*****************
            //  ENEMY DAMAGE TO PLAYER
            if (fa.getUserData().toString().contains("en_att_sensor")
                    && fb.getUserData().toString().contains("player_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().contains("en_")) {
                        EnemyEntity ent = (EnemyEntity) e;
                        if (ent.getSensorData().equals(fa.getUserData())) {
                            e.alert("en_damage_end");
                        }
                    }

                }
            }

            if (fb.getUserData().toString().contains("en_att_sensor")
                    && fa.getUserData().toString().contains("player_")) {
                for (Entity e : entities) {
                    if (e.getUserData().toString().contains("en_")) {
                        EnemyEntity ent = (EnemyEntity) e;
                        if (ent.getSensorData().equals(fb.getUserData())) {
                            e.alert("en_damage_end");
                        }
                    }
                }
            }

            //VOID
            //EnvSub end
            if (fa.getUserData().toString().contains("envEnd_wall_")
                    && fb.getUserData().toString().contains("player_")) {

                //warp player back to EnvVoid
                EnvironmentManager.currentEnv.end(
                        EnvironmentManager.currentEnv.getLinkid(),
                        0);
            }

            if (fb.getUserData().toString().contains("envEnd_wall_")
                    && fa.getUserData().toString().contains("player_")) {
                //warp player back to EnvVoid
                EnvironmentManager.currentEnv.end(
                        EnvironmentManager.currentEnv.getLinkid(),
                        0);

            }

            //ACTION EVENTS
            if (fa.getUserData().toString().contains("action_")) {

                for (Entity e : entities) {
                    if (e.getUserData().toString().contains(fa.getUserData().toString())) {
                        e.alert("inactive");
                    }
                }
            }

            if (fb.getUserData().toString().contains("action_")) {

                for (Entity e : entities) {
                    if (e.getUserData().toString().contains(fb.getUserData().toString())) {
                        e.alert("inactive");
                    }
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
