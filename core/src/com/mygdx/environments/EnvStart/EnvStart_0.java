/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.demo.DemoGameOverScreen;
import com.mygdx.demo.demo3.EnvVoid_D3_0;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.entities.StaticEntities.SkillPad_Defense;
import com.mygdx.entities.StaticEntities.SkillPad_DmLock_Secondary;
import com.mygdx.entities.StaticEntities.SkillPad_Primary;
import com.mygdx.entities.StaticEntities.SkillPad_Secondary;
import com.mygdx.entities.text.TextEntity;
import com.mygdx.environments.EnvStart.charstart.CharacterStart;
import com.mygdx.environments.EnvStart.charstart.CharacterStart_Lumen;
import com.mygdx.environments.EnvStart.charstart.CharacterStart_Poe;
import com.mygdx.environments.EnvStart.charstart.CharacterStart_Woogie;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.NullWarp;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.Overlay;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Collections;

/**
 *
 * @author saynt
 */
public class EnvStart_0 extends Environment{
    
    private float x, y;
    
    //character select
    private CharacterStart characterStart_lumen;
    private CharacterStart characterStart_woogie;
    private CharacterStart characterStart_poe;
    private int characterCount = 3;
    
    //intro state
    private enum IntroState { INTRO, POSTINTRO, NONE }
    private IntroState introState = IntroState.NONE; 
    
    //intro
    private Texture introBg;
    private BlankWall southHallwayWall;
    
    //post intro
    private Texture postIntroBg;
    
    private SkillPad skillPad_prim;
    private SkillPad skillPad_sec;
    private SkillPad skillPad_def;
    
    public EnvStart_0(int id) {
        super(id);
        
        this.linkid = linkid;
        
        width = 1500f*RATIO;
        height = 3250f*RATIO;
        
        x = 0;
        y = 0;
        
        startPos = new Vector2((width / 2)/PPM, (1600f*RATIO)/PPM);
        
        //render
        this.renderLayers = 2;
        
        //black bg
        bg = MainGame.am.get(ResourceManager.START_BLACK_BG);
        
        //intro
        introBg = MainGame.am.get(ResourceManager.START_INTRO_WHITE_BG);
        
        //post intro
        postIntroBg = MainGame.am.get(ResourceManager.START_BG_PH);
        
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
        
        
        
        /**************************
            CHARACTER STARTS
        *************************/
        characterStart_lumen = (CharacterStart)spawnEntity(new CharacterStart_Lumen(new Vector2(450f*RATIO , 2750f*RATIO),     0) );
        characterStart_poe = (CharacterStart)spawnEntity(new CharacterStart_Poe(new Vector2(750f*RATIO , 2750f*RATIO),       1) );
        characterStart_woogie = (CharacterStart)spawnEntity(new CharacterStart_Woogie(new Vector2(1050f*RATIO , 2750f*RATIO),   2) );
        
        
        
        
        
        
        //create new env, add to NullWarp
        EnvironmentManager.add(new EnvVoid_D3_0(-1));
        spawnEntity(new NullWarp(new Vector2(750f*RATIO, 125f*RATIO), -1));
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        if(bg != null)
            sb.draw(bg, 0,0,width,height);
        
        
        
        switch(introState){
            case INTRO:
                
                if(introBg != null){
                    sb.draw(introBg, x, y, width,height);
                }
                
                break;
            case POSTINTRO:
                
                if(postIntroBg != null){
                    sb.draw(postIntroBg, x, y, width, height);
                }
                
                break;
            default:
                break;
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
            
            switch (introState) {
                case INTRO:

                    if (introBg != null) {
                        sb.draw(introBg, x, y, width, height);
                    }

                    break;
                case POSTINTRO:

                    if (postIntroBg != null) {
                        sb.draw(postIntroBg, x, y, width, height);
                    }

                    break;
                default:
                    break;
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
            if (bg != null) {
                sb.draw(bg, -MainGame.WIDTH/2, -MainGame.HEIGHT/2, MainGame.WIDTH, MainGame.HEIGHT);
            }

            //need to set camera zoom one iteration before next render call
            //pit zoom
            //this.fgParallaxX = 0.05f;
            //this.fgParallaxY = 0.025f;
        }
    }
    
    
    
    @Override
    public void begin(){
        
        Overlay.enable = false;
        
        if(characterCount <= 0){
            ScreenManager.setScreen(new DemoGameOverScreen());
        }
        
        GameScreen.player = new PlayerEntity_Start();
        
        if(characterStart_lumen != null)    characterStart_lumen.active = true;
        if(characterStart_woogie != null)   characterStart_woogie.active = true;
        if(characterStart_poe != null)      characterStart_poe.active = true;
        
        
        
        
        super.begin();
        
        
        
    }
    
    @Override
    public void play(){
        super.play();
        
        startIntro();
    }
    
    
    @Override
    public void complete(){
        super.complete();
        
        
        this.setPlayerToStart();
        
    }
    
    
    /************************************
     * 
     * Spawn new Character
     * Left to right character select
     * 
    *************************************/
    public void characterSelect(int n){
        GameScreen.player.dispose();
        switch(n){
            case 0:
                //lumen
                GameScreen.player = (PlayerEntity) spawnEntity(characterStart_lumen.createPlayer());
                break;
            case 1:
                //poe
                GameScreen.player = (PlayerEntity) spawnEntity(characterStart_poe.createPlayer());
                break;
            case 2:
                //woogie
                GameScreen.player = (PlayerEntity) spawnEntity(characterStart_woogie.createPlayer());
                break;
            default:
                break;
        }
        
        if(characterStart_lumen != null)    characterStart_lumen.active = false;
        if(characterStart_woogie != null)   characterStart_woogie.active = false;
        if(characterStart_poe != null)      characterStart_poe.active = false;
        
        
        startPostIntro();
        
        characterCount--;
    }
    
    private void startIntro(){
        
        introState = IntroState.INTRO;

        Overlay.enable = false;
        
        
        //south wall
        southHallwayWall = (BlankWall) spawnEntity(new BlankWall(new Vector2(750f*RATIO, 1500f*RATIO), 200f*RATIO, 10f*RATIO));
        
    }
    
    private void startPostIntro() {
        introState = IntroState.POSTINTRO;

        Overlay.enable = true;
        
        
        //REMOVE all intro entities
        this.removeEntity(southHallwayWall);
        
        //POST INTRO
        
        //PRIMARY SKILL PAD
        if(skillPad_prim != null){
            skillPad_prim.reset();
        }else{
             skillPad_prim = (SkillPad) this.spawnEntity( 
                new SkillPad_Primary(
                        new Vector2(425f*RATIO, 850f*RATIO)));
        }
        
        //SECONDARY SKILL PAD
        if(skillPad_sec != null){
            skillPad_sec.reset();
        }else{
            skillPad_sec = (SkillPad)this.spawnEntity(
                new SkillPad_Secondary(
                        new Vector2(1125f*RATIO, 850f*RATIO)));
        }
        
        
        //DEFENSE SKILL PAD
        if(skillPad_def != null){
            skillPad_def.reset();
        }else{
            skillPad_def = (SkillPad)this.spawnEntity(
                new SkillPad_Defense(
                        new Vector2(750f*RATIO, 1800f*RATIO)));
        }
        
       
        
        //dm lock skill pads
        
        //5
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 650f*RATIO), 5));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(425f*RATIO, 650f*RATIO), 5));
        
        
        //10
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(1125f*RATIO, 350f*RATIO), 10));
        spawnEntity(new SkillPad_DmLock_Secondary(new Vector2(425f*RATIO, 350f*RATIO), 10));
        
    }
    
}
