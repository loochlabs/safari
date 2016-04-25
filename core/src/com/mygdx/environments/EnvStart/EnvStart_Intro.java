/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.demo.DemoGameOverScreen;
import com.mygdx.entities.Entity;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.environments.BlackFaceBg;
import com.mygdx.environments.EnvStart.charstart.CharacterStart;
import com.mygdx.environments.EnvStart.charstart.CharacterStart_Lumen;
import com.mygdx.environments.EnvStart.charstart.CharacterStart_Poe;
import com.mygdx.environments.EnvStart.charstart.CharacterStart_Woogie;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.gui.Overlay;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import com.mygdx.utilities.SoundObject_Bgm;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Collections;

/**
 *
 * @author saynt
 */
public class EnvStart_Intro extends Environment{
    
    private float x, y;
    
    //face bg
    private final BlackFaceBg blackFaceBg;
    
    //id of start env we are warping to
    private final int START_ENV_ID = -100;
    
    //character select
    private CharacterStart characterStart_lumen;
    private CharacterStart characterStart_woogie;
    private CharacterStart characterStart_poe;
    private int characterCount = 3;
    
    
    //intro
    private Texture introBg;
    //private IntroControls introControls;
    
    //SOUND
    private SoundObject_Bgm bgm1, bgm2;
    
    public EnvStart_Intro(int id) {
        super(id);
        
        width = 1500f*RATIO;
        height = 3250f*RATIO;
        
        x = 0;
        y = 0;
        
        GameScreen.player = new PlayerEntity_Start();
        startPos = new Vector2((width / 2)/PPM, (1600f*RATIO)/PPM);
        
        //render
        this.renderLayers = 2;
        
        //black bg
        blackFaceBg = new BlackFaceBg();
        
        //intro
        introBg = MainGame.am.get(ResourceManager.START_INTRO_WHITE_BG);
        
        
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
        
        spawnEntity(new BlankWall(new Vector2(750f*RATIO, 1500f*RATIO), 200f*RATIO, 10f*RATIO));
        
        //bottom section
        spawnEntity(new BlankWall(new Vector2(325 * RATIO, 125f * RATIO), 225f * RATIO, 125f * RATIO));     //bottom left corner
        spawnEntity(new BlankWall(new Vector2(1175 * RATIO, 125f * RATIO), 225f * RATIO, 125f * RATIO));    //bottom right corner
        spawnEntity(new BlankWall(new Vector2(100 * RATIO, 725f * RATIO), border, 725f * RATIO));          //left wall
        spawnEntity(new BlankWall(new Vector2(1400 * RATIO, 725f * RATIO), border, 725f * RATIO));         //right wall 
        
        spawnEntity(new IntroControls(new Vector2(750f*RATIO, 1400f*RATIO)));
        
        
        /************************
            CHARACTER STARTS
        *************************/
        characterStart_lumen = (CharacterStart)spawnEntity(new CharacterStart_Lumen(new Vector2(450f*RATIO , 2750f*RATIO),     0) );
        characterStart_poe = (CharacterStart)spawnEntity(new CharacterStart_Poe(new Vector2(750f*RATIO , 2750f*RATIO),       1) );
        characterStart_woogie = (CharacterStart)spawnEntity(new CharacterStart_Woogie(new Vector2(1050f*RATIO , 2750f*RATIO),   2) );
        
        
        /************************
            START ENV
        *************************/
        EnvironmentManager.add(new EnvStart_0(START_ENV_ID));    //todo: figure out proper system for env id's
        
        
        
        
        
        
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

        if (introBg != null) {
            sb.draw(introBg, x, y, width, height);
        }

        
        Collections.sort(entities, new Entity.EntityComp());
        for (Entity e : entities) {
            e.render(sb);
        }
    }
    
    @Override
    public void render(SpriteBatch sb, int layer) {
        if (0 == layer) {

            if (introBg != null) {
                sb.draw(introBg, x, y, width, height);
            }

            Collections.sort(entities, new Entity.EntityComp());
            for (Entity e : entities) {
                e.render(sb);
            }

            
        } else if (1 == layer) {
            if (blackFaceBg != null) {
                blackFaceBg.render(sb);
            }
        }

    }
    
    
    @Override
    public void begin(){
        
        Overlay.enable = false;
        
        if(characterCount <= 0){
            ScreenManager.setScreen(new DemoGameOverScreen());
        }
        
        
        
        if(characterStart_lumen != null)    characterStart_lumen.active = true;
        if(characterStart_woogie != null)   characterStart_woogie.active = true;
        if(characterStart_poe != null)      characterStart_poe.active = true;
        
        
        bgm1.play();
        bgm2.play();
        
        super.begin();
        
    }
    
    @Override
    public void pause(){
        sm.setPaused(true);
    }
    
    @Override
    public void complete(){
        
        bgm1.stop();
        bgm2.stop();
        
        super.complete();
    }
    
    
    /************************************
     * 
     * Spawn new Character
     * Left to right character select
     * 
     * @param :  character id from left to right
    *************************************/
    public void characterSelect(int n){
        //GameScreen.player.dispose();
        entityCheck();
        switch(n){
            case 0:
                //lumen
                //GameScreen.player = (PlayerEntity) spawnEntity(characterStart_lumen.createPlayer());
                GameScreen.player = characterStart_lumen.createPlayer();
                break;
            case 1:
                //poe
                //GameScreen.player = (PlayerEntity) spawnEntity(characterStart_poe.createPlayer());
                GameScreen.player = characterStart_poe.createPlayer();
                break;
            case 2:
                //woogie
                //GameScreen.player = (PlayerEntity) spawnEntity(characterStart_woogie.createPlayer());
                GameScreen.player = characterStart_woogie.createPlayer();
                break;
            default:
                break;
        }
        
        if(characterStart_lumen != null)    characterStart_lumen.active = false;
        if(characterStart_woogie != null)   characterStart_woogie.active = false;
        if(characterStart_poe != null)      characterStart_poe.active = false;
        
        
        
        characterCount--;
        
        //create new EnvStart_0
        EnvironmentManager.setCurrent(START_ENV_ID);
    }
    
    
    private class IntroControls extends Entity{

        private boolean padEnabled = false;
        private final Texture padTexture, keyTexture;
        
        private BitmapFont uifont;
        private String upKey, downKey, rightKey, leftKey;
        
        public IntroControls(Vector2 pos) {
            super(pos, 200f * RATIO, 100f * RATIO);

            padTexture = MainGame.am.get(ResourceManager.START_CONTROLS_PAD);
            keyTexture = MainGame.am.get(ResourceManager.START_CONTROLS_KEY);

            //key bindings
            if (GameInputProcessor.controller) {
                padEnabled = true;
            }

            if (!padEnabled) {
                uifont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
                uifont.setScale(0.75f * RATIO);
                upKey = Input.Keys.toString(GameInputProcessor.KEY_UP);
                downKey = Input.Keys.toString(GameInputProcessor.KEY_DOWN);
                rightKey = Input.Keys.toString(GameInputProcessor.KEY_RIGHT);
                leftKey = Input.Keys.toString(GameInputProcessor.KEY_LEFT);
                
                texture = keyTexture;
                
            } else {
                //pad
                texture = padTexture;
            }
        }
        
        
        @Override
        public void init(World world) {}
        
        @Override
        public void render(SpriteBatch sb){
            super.render(sb);
            
            //if !padEnabled , render keys
            if(!padEnabled){
                uifont.draw(sb, upKey, 1f*RATIO + this.pos.x - uifont.getBounds(upKey).width/2, 7f*RATIO + this.pos.y + height*0.7f);
                uifont.draw(sb, downKey,3f*RATIO + this.pos.x - uifont.getBounds(downKey).width/2, -5f*RATIO + this.pos.y );
                uifont.draw(sb, leftKey, 5f*RATIO + this.pos.x - width*0.5f - uifont.getBounds(leftKey).width/2, -5f*RATIO + this.pos.y );
                uifont.draw(sb, rightKey, -5f*RATIO + this.pos.x + width*0.5f - uifont.getBounds(rightKey).width/2, -5f*RATIO + this.pos.y );
            }
        }
        
    }
}
