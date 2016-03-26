/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments;

import com.badlogic.gdx.graphics.Color;
import com.mygdx.managers.MainContactListener;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.entities.Entity;
import com.mygdx.entities.Entity.EntityComp;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.text.TextDamage;
import com.mygdx.entities.text.TextEntity;
import com.mygdx.entities.text.TextHealing;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.FrameManager;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.screen.GameOver.GameOverScreen;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.UtilityVars;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author looch
 */
public class Environment {
    
    protected float width, height;
    protected Texture playTexture, introTexture, outroTexture;
    protected ImageSprite beginSprite, endSprite, spectralPlayerSprite;
    protected Texture bg, fg;
    protected float fgx, fgy, fgw, fgh;
    protected int renderLayers;
    protected float bgParallaxX = 0, bgParallaxY = 0, fgParallaxX = 1.0f, fgParallaxY = 1.0f;
    
    protected int idwarp = 0;
    protected int id, linkid;
    
    protected StateManager sm = new StateManager();
    protected Random rng = new Random();
    protected final Array<Integer> rngNegSet = new Array<Integer>();
    
    //camera zooms
    protected float cameraZoom = 1.0f;
    protected float currentTopZoom = 1.0f, TOP_LAYER_ZOOM = 1.0f;
    protected float currentPlayerZoom = 1.0f, PLAYER_LAYER_ZOOM = 1.0f;
    protected float currentSectionZoom = 5.0f, SECTION_LAYER_ZOOM = 5.0f;
    protected float currentPitZoom = 5.0f, PIT_LAYER_ZOOM = 5.0f;
    protected float BEGIN_TOP_ZOOM = 10.0f;
    protected float currentBgZoom = 1.0f, BG_LAYER_ZOOM = 1.0f;
    
    
    
    //frame counters
    protected FrameManager fm = new FrameManager();
    protected FrameCounter_BEGIN beginFC = new FrameCounter_BEGIN(2);
    protected FrameCounter_END endFC = new FrameCounter_END(3);
    protected FrameCounter diveFC = new FrameCounter(1);
    
    //b2d
    protected World world;
    protected Vector2 playerPos;
    protected Vector2 startPos;
    
    protected final ArrayList<Entity> entities = new ArrayList<Entity>();
    protected final Array<Entity> entToRemove = new Array<Entity>();
    protected final Array<Body> bodyToRemove = new Array<Body>();
    protected final Array<Entity> entToAdd = new Array<Entity>();
    
    //env compelted;
    protected boolean complete = false;
    protected int enemyCount = 0, killCount = 0;
    protected boolean enemiesClear = true;
    
    //entity damage text
    protected BitmapFont dmgFont;
    protected final Array<TextEntity> dmgTexts = new Array<TextEntity>();
    protected final Array<TextEntity> dmgTextToRemove = new Array<TextEntity>();
    
    //reward items 
    protected Array<Pickup> rewardItems = new Array<Pickup>();
    
    
    //TODO: Intro description text
    //Display for first 5 seconds when entering env
    //Need seperate large font 
    protected String introDescription = "";
    
    public float getWidth() {return width;}
    public float getHeight() {return height;}
    public int getRenderLayers() { return renderLayers; }
    public int getId() {return id;}
    public int getLinkid() { return linkid; }
    public World getWorld() {return world;}
    public FrameCounter_BEGIN getBeginFC() { return beginFC; }
    public FrameCounter_END getEndFC() { return endFC; }
    public Texture getBg() {return bg;}
    public float getBgParallaxX() { return bgParallaxX; }
    public float getBgParallaxY() { return bgParallaxY; } 
    public float getFgParallaxX() { return fgParallaxX; } 
    public float getFgParallaxY() { return fgParallaxY; }
    public Vector2 getStartPos() { return startPos; }
    public ArrayList<Entity> getEntities() { return entities; }
    public StateManager getStateManager() { return sm; }
    public FrameManager getFrameManager() { return fm; }
    public float getCameraZoom() { return cameraZoom; }
    public Array<Pickup> getRewardItems() { return rewardItems; }
    public boolean isComplete() { return complete; }
    
    public void setIdWarp(int id) { this.idwarp = id; }
    public void setComplete(boolean complete) { this.complete = complete; }
    
    public Environment(int id){
        world = new World(new Vector2(0.0f, 0.0f), true);
        world.setContactListener(new MainContactListener(this));
        
        this.id = id;
        
        //rng set
        rngNegSet.add(1);
        rngNegSet.add(-1);
        
        dmgFont = MainGame.FONT_DMG;
        dmgFont.setColor(Color.YELLOW);
        dmgFont.setScale(0.55f * RATIO);
        
        //endSpectralSprite = new EntitySprite("endSpectralSprite", false);
        //endSpectralSprite.sprite.setScale(1.06f*RATIO);
        
        
    }
    
    public void init(){
        for(Entity e: entities)
            e.init(world);
        
        this.setPlayerToStart();
        entities.add(GameScreen.player);
        
        
        //TODO: needed here?
        spectralPlayerSprite = new ImageSprite(GameScreen.player.getRecovSprite());
        spectralPlayerSprite.sprite.setScale(0.65f);
        
    }
    
    public void render(SpriteBatch sb){
        if(bg != null)
            sb.draw(bg, 0,0,width,height);
        
        if(fg != null){
            sb.draw(fg, fgx, fgy, fgw, fgh);
        }

        //for (ImageSprite e : sprites) {
            //e.render(sb);
        //}

        //entities.sort();
        Collections.sort(entities, new EntityComp());
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
            
           
        //if(sm.getState() == State.END){
            //endSpectralAnim(sb);
        //}
    }
    
    //render parralax layers (0-n, where 0 is the top layer)
    public void render(SpriteBatch sb, int layer){
        switch (layer){
            case 0:
                
                /*
                    for (ImageSprite e : sprites) {
                        e.step();
                        e.sprite.draw(sb);
                    }
                       */
                    for (Entity e : entities) {
                        e.render(sb);
                    }

                
                break;
                
            case 1:
                if (sm.getState() == State.PLAYING) {

                    if (fg != null) {
                        sb.draw(fg, fgx, fgy, fgw, fgh);
                    }

                }
                break;
            case 2:
                if(bg != null)
                    sb.draw(bg, 0,0,width,height);
                break;
            default:
                break;
        }
    }
    
    public void update(){
        
        //if (frameFreeze) {
            //updateFrameFreeze();
        //} else {
            //update frame coutners
            fm.update();

            if (sm.getState() == State.PLAYING) {

                entityCheck();

                world.step(UtilityVars.STEP, 6, 2);//new position (2/17/16)

            } else {
                GameScreen.player.update();
            }
        //}
        //TRANSITION SCENES
        envTransition();
        
        
        //step through isprites
        
    }
    
    public void updateCamera(OrthoCamera cam){
        
        if(sm.getState() == State.BEGIN){
            
        }else if(sm.getState() == State.PLAYING 
                || sm.getState() == State.FALLING){
            
            cam.setPosition(GameScreen.player.getBody().getPosition().x * PPM, GameScreen.player.getBody().getPosition().y * PPM);
            
        }
    }
    
    public void updateB2DCamera(OrthoCamera cam){
        if(sm.getState() == State.BEGIN){
            
        }else if(sm.getState() == State.PLAYING 
                || sm.getState() == State.FALLING){
            
            cam.setPosition(GameScreen.player.getBody().getPosition().x, GameScreen.player.getBody().getPosition().y);
            
        }
    }
    
    //todo:old code
    public void addEntity(Entity e){
        entities.add(e);
    }
    
    public void toAddEntity(Entity e){
        entToAdd.add(e);
    }
    
    public void removeEntity(Entity e){
        entToRemove.add(e);
    }
    
    
    //Description: called on EnvManager.setCurrent(this)
    public void begin(){
        sm.setState(0);
        
        beginFC.start(fm);
        endFC.stop();
        
        if(!sm.isPaused()){
            this.init();
        }else{
            GameScreen.player.getBody().setTransform(playerPos.cpy(),0);
        }
        
        entityCheck();
        
    }
    
    //Desription: starting playing
    public void play(){
        
        resume();
        
        sm.setState(1);
        sm.setPaused(false);
        
        GameScreen.overlay.addTitleAlert(introDescription); 
        
    }
    
    public void resume(){
        GameScreen.player.init(world);
        GameScreen.player.getBody().setTransform(playerPos.cpy(),0);
        
        //todo: make specfic to tears
        String [] str = {"", "tear_resume", ""};
        for(Entity e: entities){
            e.alert(str);
        }
    }
    
    //Description: called on warp out of level
    //@param: id = id of environment to warp to
    //        time - length of time for endFC before begin new env
    public void end(int id, float time){
        
        sm.setState(3);
        
        idwarp = id;
    }
    
    //Called after end() has completed
    public void complete(){
        EnvironmentManager.setCurrent(idwarp);
    }
    
    //Description: called when current environment is exited
    public void pause(){
        sm.setPaused(true);
        
        Vector2 vec = GameScreen.player.getBody().getPosition().cpy();
        playerPos = vec;
        reset();
        
    }
    
    /*
    public void endSpectralAnim(SpriteBatch sb){
        
        if (GameScreen.player.isDead()) {
            
            spectralPlayerSprite.sprite.setPosition(
                    GameScreen.player.getBody().getPosition().x*PPM - spectralPlayerSprite.sprite.getWidth()/2, 
                    GameScreen.player.getBody().getPosition().y*PPM - spectralPlayerSprite.sprite.getHeight()/2);
            spectralPlayerSprite.render(sb);
            spectralPlayerSprite.sprite.scale(0.0055f);
            
            //endSpectralSprite.render(sb);
        }
        
    }*/
    
    public void gameOver(){
        ScreenManager.setScreen(new GameOverScreen());
    }
    
    
    
    
    //Description: clear b2d body after changing environments
    public void reset(){
        
        if(GameScreen.player.getBody() != null)
            world.destroyBody(GameScreen.player.getBody());
    }
    
    public void setPlayerToStart(){
        playerPos = startPos.cpy();
    }
    
    public void setStartPos(Vector2 pos){}
    public void setPlayerPos(Vector2 pos){}
    
    
    //TODO: create Overloaded method with the option for a flag to increase EnSpawnCounter
    public Entity spawnEntity(Entity e){
        toAddEntity(e);
        return e;
    }
    
    /*
    public ImageSprite spawnSprite(ImageSprite sprite){
        spriteToAdd.add(sprite);
        return sprite;
    }
    
    public ImageSprite removeSprite(ImageSprite sprite){
        spriteToRemove.add(sprite);
        return sprite;
    }
*/
    
    public void entityCheck(){
            
            //remove entities
            for (Entity e : entToRemove) {
                try {
                    if (e.getBody() != null) {
                        world.destroyBody(e.getBody());
                        e.setBody(null);
                    }
                    entities.remove(e);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                
            }
            
            if(entToRemove.size > 0)
                entToRemove.clear();
            
            //add entities
            for(Entity e: entToAdd){
                try {
                    addEntity(e);
                    e.init(world);
                    Collections.sort(entities, new EntityComp());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }  
            }
            
            if(entToAdd.size > 0){
                entToAdd.clear();
            }

            
            //update entities
            for (Entity e : entities) {
                e.update();
            }
    }
    
    public void addDamageText(String dmg, Vector2 pos){
        
        dmgTexts.add(new TextDamage("" + dmg + "", pos, false));
        
    }
    
    public void addDamageText(String dmg, Vector2 pos, String color){
        
        dmgTexts.add(new TextDamage("" + dmg + "", pos, false, color));
        
    }
    
    public void addDamageText(TextDamage text){
        Vector2 dv = new Vector2(0, dmgFont.getCapHeight());
        for(TextEntity dtext: dmgTexts){
            dtext.addToPos(dv);
        }
        dmgTexts.add(text);
    }
    
    public void addHealingText(String healing, Vector2 pos){
        dmgTexts.add(new TextHealing("" + healing + "", pos));
    }
    
    public void addEnemyCount(){
        enemyCount++;
        enemiesClear = false;
    }
    
    public void addKillCount(){
        killCount++;
        
        if(killCount >= enemyCount){
            enemiesClear = true;
        }
    }
    
    //rewards
    public void addReward(Pickup reward){
        rewardItems.add(reward);
    }

    public void envTransition() {
        if(sm.getState() == State.BEGIN ){
            
            //call Overlay transition out
            
            if(GameScreen.overlay.transition(true) || beginFC.complete){
                System.out.println("@Environment begin trans " + this);
                GameScreen.overlay.endTransition();
                play();
            }
            
        }
        
        //TRANSITION SCENES
        if(sm.getState() == State.END ){
            
            //call Overlay transition out
            if(GameScreen.overlay.transition(false)){
                System.out.println("@Environment end trans " + this);
                GameScreen.overlay.endTransition();
                complete();
                
            }
            
        }
    }

    
    //FRAME COUNTER CLASSES
    
    protected class FrameCounter_BEGIN extends FrameCounter{

        public FrameCounter_BEGIN(float sec) {
            super(sec);
        }
        
        @Override
        public void complete(){
            super.complete();
            //play();
        }
        
    }
    
    
    //Handles entire environment switch on FrameCounter_END endFC.complete
    protected class FrameCounter_END extends FrameCounter{

        public FrameCounter_END(float time) {
            super(time);
        }
        
        @Override
        public void complete(){
            super.complete();
            
            //if(!GameScreen.player.isDead()){
                //EnvironmentManager.setCurrent(idwarp);
            //}
        }
        
    }
    
}
