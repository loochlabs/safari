/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.demo.demo3.EnvVoid_D3_0;
import com.mygdx.entities.Entity;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.entities.StaticEntities.SkillPad_Default;
import com.mygdx.entities.StaticEntities.SkillPad_Defense;
import com.mygdx.entities.StaticEntities.SkillPad_DmLock_Secondary;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Small;
import com.mygdx.entities.text.TextEntity;
import com.mygdx.environments.BlackFaceBg;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.NullWarp;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.Overlay;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.SoundObject_Bgm;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Collections;

/**
 *
 * @author saynt
 */
public class EnvStart_0 extends Environment{
    
    private float x, y;
    
    private final BlackFaceBg blackFaceBg;
    
    //post intro
    private Texture postIntroBg;
    
    //private SkillPad skillPad_prim;
    //private SkillPad skillPad_sec;
    private SkillPad skillPad_defense;
    private SkillPad skillPad_default;
    
    private boolean firstEnable = true;
    
    private Entity_VomitNpc vomitNpc;
    
    
    //sound
    private SoundObject_Bgm bgm1, bgm2;
    
    
    public EnvStart_0(int id) {
        super(id);
        
        //this.linkid = linkid;
        
        width = 1500f*RATIO;
        height = 3250f*RATIO;
        
        x = 0;
        y = 0;
        
        startPos = new Vector2((width / 2)/PPM, (2750f*RATIO)/PPM);
        
        //render
        this.renderLayers = 2;
        
        //black bg
        blackFaceBg = new BlackFaceBg();
        
        
        
        //post intro
        postIntroBg = MainGame.am.get(ResourceManager.START_BG_PH);
        
        
        //sound
        bgm1 = new SoundObject_Bgm(ResourceManager.BGM_INTRO_1);
        bgm2 = new SoundObject_Bgm(ResourceManager.BGM_INTRO_2);
        
    }
    
    @Override
    public void init(){
        super.init();
        
        float border = 15f * RATIO;

        //bounding walls
        spawnEntity(new BlankWall(new Vector2(x + width / 2, y), width / 2, border));       //south
        spawnEntity(new BlankWall(new Vector2(x + width / 2, height), width / 2, border));  //north
        spawnEntity(new BlankWall(new Vector2(x + width, height / 2), border, height / 2)); //east
        spawnEntity(new BlankWall(new Vector2(x, height / 2), border, height / 2));         //west
        
        //top section corners / hallway
        spawnEntity(new BlankWall(new Vector2(275*RATIO, 1850f*RATIO), 275f*RATIO, 400f*RATIO)); //w-225, h-600, x-225,h-2050
        spawnEntity(new BlankWall(new Vector2(1225*RATIO, 1850f*RATIO), 275f*RATIO, 400f*RATIO)); 
        
        
        
        //bottom section
        spawnEntity(new BlankWall(new Vector2(325 * RATIO, 125f * RATIO), 225f * RATIO, 125f * RATIO));     //bottom left corner
        spawnEntity(new BlankWall(new Vector2(1175 * RATIO, 125f * RATIO), 225f * RATIO, 125f * RATIO));    //bottom right corner
        spawnEntity(new BlankWall(new Vector2(100 * RATIO, 725f * RATIO), border, 725f * RATIO));          //left wall
        spawnEntity(new BlankWall(new Vector2(1400 * RATIO, 725f * RATIO), border, 725f * RATIO));         //right wall 
        
        
        //create new env, add to NullWarp
        EnvironmentManager.add(new EnvVoid_D3_0(-1));
        spawnEntity(new NullWarp(new Vector2(750f*RATIO, 125f*RATIO), -1));
        
        
        
        Overlay.enable = true;
        
        
        /*******************
            TOP SECTION
        *******************/
        
        //DEFAULT
        if(skillPad_default != null){
            skillPad_default.reset();
        }else{
            skillPad_default = (SkillPad)this.spawnEntity(
                    new SkillPad_Default(new Vector2(375f*RATIO, 2450f*RATIO)));
        }
        
        //DEFENSE SKILL PAD
        if(skillPad_defense != null){
            skillPad_defense.reset();
        }else{
            skillPad_defense = (SkillPad)this.spawnEntity(
                new SkillPad_Defense(
                        new Vector2(1125f*RATIO, 2450f*RATIO)));
        }
        
        
        /*******************
            HALLWAY
        *******************/
        if(firstEnable){
            this.spawnEntity(new Cyst_Small(new Vector2(750f*RATIO, 1800f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(585f*RATIO, 1775f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(625f*RATIO, 1790f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(900f*RATIO, 1775f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(880f*RATIO, 1790f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(830f*RATIO, 1790f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(685f*RATIO, 1765f*RATIO)));
            firstEnable = false;
        }
        
        /*******************
            BOTTOM SECTION
        *******************/
        
        //dm lock skill pads
        
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(375f*RATIO, 1150f*RATIO), 5));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 1150f*RATIO), 10));
        
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(375f*RATIO, 850f*RATIO), 15));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 850f*RATIO), 20));
        
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(375f*RATIO, 550f*RATIO), 25));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 550f*RATIO), 30));
        
        
        //vomit npc
        vomitNpc = (Entity_VomitNpc)spawnEntity(new Entity_VomitNpc(new Vector2(1125f*RATIO, 1430f*RATIO)));
    }
    
    
    @Override
    public void update(){
        super.update();
        
        blackFaceBg.update();
    }
    
    @Override
    public void render(SpriteBatch sb) {

        if (blackFaceBg != null) {
            blackFaceBg.render(sb);
        }

        if (postIntroBg != null) {
            sb.draw(postIntroBg, x, y, width, height);
        }
        
        
        
        
        Collections.sort(entities, new Entity.EntityComp());
        for (Entity e : entities) {
            e.render(sb);
        }
        
            //floating dmg text
        for (TextEntity text : dmgTexts) {
            text.render(dmgFont, sb);

            if (text.flagForDelete) {
                dmgTextToRemove.add(text);
            }
        }

        for (TextEntity text : dmgTextToRemove) {
            dmgTexts.removeValue(text, false);
        }

        dmgTextToRemove.clear();
    }
    
    @Override 
    public void render(SpriteBatch sb, int layer){
        if (0 == layer) {
            
            
            if (postIntroBg != null) {
                sb.draw(postIntroBg, x, y, width, height);
            }

            Collections.sort(entities, new Entity.EntityComp());
            for (Entity e : entities) {
                e.render(sb);
            }

            //floating dmg text
            for (TextEntity text : dmgTexts) {
                text.render(dmgFont, sb);

                if (text.flagForDelete) {
                    dmgTextToRemove.add(text);
                }
            }

            for (TextEntity text : dmgTextToRemove) {
                dmgTexts.removeValue(text, false);
            }

            dmgTextToRemove.clear();
        } else if (1 == layer) {
            if (blackFaceBg != null) {
                blackFaceBg.render(sb);
            }

        }
    }
    
    
    
    @Override
    public void begin(){
        
        Overlay.enable = true;
        
        //sound
        bgm1.play();
        bgm2.play();
        
        
        super.begin();
        
        
        
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        bgm2.stop();
        
        super.end(id, time);
    }
    
    @Override
    public void complete(){
        super.complete();
        
        
        this.setPlayerToStart();
        
        vomitNpc.respawn();
    }
    
    
    
    /*
    private void startIntro(){
        
        introState = IntroState.INTRO;

        Overlay.enable = false;
        
        
        //south wall
        southHallwayWall = (BlankWall) spawnEntity(new BlankWall(new Vector2(750f*RATIO, 1500f*RATIO), 200f*RATIO, 10f*RATIO));
        
    }*/
    
    
    /*
    private void startPostIntro() {
        //introState = IntroState.POSTINTRO;

        Overlay.enable = true;
        
        
        //REMOVE all intro entities
        //this.removeEntity(southHallwayWall);
        //this.removeEntity(introControls);  //does not need to respawn 
        
        /*******************
            TOP SECTION
        *******************
        
        //DEFAULT
        if(skillPad_default != null){
            skillPad_default.reset();
        }else{
            skillPad_default = (SkillPad)this.spawnEntity(
                    new SkillPad_Default(new Vector2(375f*RATIO, 2450f*RATIO)));
        }
        
        //DEFENSE SKILL PAD
        if(skillPad_defense != null){
            skillPad_defense.reset();
        }else{
            skillPad_defense = (SkillPad)this.spawnEntity(
                new SkillPad_Defense(
                        new Vector2(1125f*RATIO, 2450f*RATIO)));
        }
        
        
        /*******************
            HALLWAY
        *******************
        if(firstEnable){
            this.spawnEntity(new Cyst_Small(new Vector2(750f*RATIO, 1800f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(585f*RATIO, 1775f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(625f*RATIO, 1790f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(900f*RATIO, 1775f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(880f*RATIO, 1790f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(830f*RATIO, 1790f*RATIO)));
            this.spawnEntity(new Cyst_Small(new Vector2(685f*RATIO, 1765f*RATIO)));
            firstEnable = false;
        }
        
        /*******************
            BOTTOM SECTION
        *******************
        
        //dm lock skill pads
        
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(375f*RATIO, 1150f*RATIO), 5));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 1150f*RATIO), 10));
        
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(375f*RATIO, 850f*RATIO), 15));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 850f*RATIO), 20));
        
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(375f*RATIO, 550f*RATIO), 25));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 550f*RATIO), 30));
        
        
        
    }
    
    */
    
    
}
