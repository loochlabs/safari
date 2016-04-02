/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.DynamicEntities.enemies.EnemyManager;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.text.TextEntity;
import com.mygdx.environments.Environment;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.Coordinate;
import com.mygdx.utilities.SoundObject_Bgm;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author looch
 */
public abstract class EnvNull extends Environment {
    
    protected EntitySprite impactSprite, introTextSprite;
    protected ImageSprite playerDiveSprite, bgRockSprite;
    protected final float PLAYER_DIVE_SCALE;
    protected final Vector2 PLAYER_DIVE_POS;
    protected long diveTime;
    protected final float moveSpeed = 10.0f;
    protected float diveMovement = 0.1f;
    protected boolean diveIn = false;
    
    //**************
    //  DIFFICULTY
    //************
    protected final int DIFFICULTY;
    protected int sectionCount;
    
    //Section generation
    protected float sectionWidth = 800f * RATIO;
    protected float sectionHeight = 800f * RATIO;
    protected Coordinate nullCoord = new Coordinate(0, 0);
    protected Array<Coordinate> gridCoords = new Array<Coordinate>();
    
    
    //null sections
    protected NullSection currentSection; 
    protected int currentDepth = 0;
    
    //new null sections
    protected Array<LayerManager> layerManagers = new Array<LayerManager>();
    
    //change to array of enemies
    //check if empty to finish this null
    protected int currentEnemies = 0;
    
    
    //end warp entity
    protected En_EndNullArm endNullArm;
    
    //******************
    //  SOUND
    //*****************
    
    private final Array<SoundObject_Bgm> bgm = new Array<SoundObject_Bgm>();
    private final SoundObject_Bgm bgm_end;
    private SoundObject_Bgm bgm1;
    private SoundObject_Sfx impactSound;
    
    
    public EnvNull(int id, int linkid, int difficulty){
        super(id);
        
        this.linkid = linkid;
        this.width = MainGame.WIDTH;
        this.height = MainGame.HEIGHT;
        this.DIFFICULTY = difficulty;
        
        introDescription = "The Null";
        
        playTexture = MainGame.am.get(ResourceManager.NULL_BG1);
        introTexture = MainGame.am.get(ResourceManager.NULL_BG1);
        outroTexture = MainGame.am.get(ResourceManager.NULL_PH);
        
        fgx = 0;
        fgy = 0;
        fgw = width;
        fgh = height;
        
        //todo: dont need 7 render layers
        renderLayers = 7;
        cameraZoom = 1.0f;
        
        //todo: adjust position - coordinate with first section
        startPos = new Vector2(MainGame.WIDTH*0.55f/PPM,MainGame.HEIGHT*0.65f/PPM);
        this.setPlayerToStart();
        
        playerDiveSprite = GameScreen.player.getDiveSprite();
        playerDiveSprite.sprite.setPosition(width * 0.5f, height * 0.15f);
        PLAYER_DIVE_POS = new Vector2(playerDiveSprite.sprite.getX(), playerDiveSprite.sprite.getY());
        PLAYER_DIVE_SCALE = playerDiveSprite.sprite.getScaleX();
        diveTime = (long)(2000 * 0.6);
        
        impactSprite = new EntitySprite(new Vector2(playerPos.x*PPM, playerPos.y*PPM), 185f, 390f, 
                "player-impact", 1.0f, true, false);
        //todo: change pos
        impactSprite.setPosition(new Vector2(playerPos.x*PPM - impactSprite.getWidth()/2, 
                playerPos.y*PPM - impactSprite.getHeight()*0.175f));
        
        introTextSprite = new EntitySprite(new Vector2(0,0), 350f*RATIO,100f*RATIO, "kill-text",  
                false, true, false, false, 1.0f, false, false, false, false);
        
        bgRockSprite = new ImageSprite("null-bg-rocks", false);
        bgRockSprite.sprite.setScale(1.0f * RATIO);
        bgRockSprite.sprite.setPosition(
                 - width/2 + 150f*RATIO, 
                 - height/2 + 75*RATIO);
        
        //end
        endFC.setTime(4.0f);
        
        //sound
        bgm.add(new SoundObject_Bgm(ResourceManager.BGM_NULL_3));
        bgm_end = new SoundObject_Bgm(ResourceManager.BGM_NULL_END);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_NULL_IMPACT);
            
    }
    
    
    @Override
    public void init(){
        super.init();
        
        //call after init to organize entities in proper layerManager
        this.initSections();
        
    }
    
    @Override
    public void update(){
        
        if(sm.getState() == StateManager.State.BEGIN)
            fallingBeginUpdate();
        
        super.update();
        
        for(LayerManager lm : layerManagers){
            lm.update();
        }
        
        
        if(sm.getState() == StateManager.State.FALLING )
            fallingUpdate();
            
        
        
        bgZoomUpdate();
        
        
    }
    
    
    boolean beginCheck = false;     //used for initial beginning camera zoom
    float topRate = (9 * MainGame.STEP) / (2000 / 1000);
    
    //fall camera zoom during State.BEGIN
    
    public void fallingBeginUpdate(){
        
        if(beginCheck){
            if(beginFC.complete){
                //todo: remove?
                currentTopZoom = TOP_LAYER_ZOOM;
                currentPlayerZoom = PLAYER_LAYER_ZOOM;
                
                for(LayerManager lm : layerManagers){
                    lm.zoom = lm.zoom <= lm.maxZoom ? lm.maxZoom : lm.zoom - topRate;
                }
            }else{
                
                //adjust layerManagers.zoom 
                //todo: remove?
                currentTopZoom = currentTopZoom <= TOP_LAYER_ZOOM ? TOP_LAYER_ZOOM : currentTopZoom - topRate;
                
                for(LayerManager lm : layerManagers){
                    lm.updateBeginZoom();
                }
            }
        }
        
        else{      //initial check to init begin zoom
            beginCheck = true;
            
            currentTopZoom = BEGIN_TOP_ZOOM;
            currentPlayerZoom = PLAYER_LAYER_ZOOM;

            playerDiveSprite.sprite.setScale(1.0f);
            playerDiveSprite.sprite.setPosition(startPos.x * PPM - playerDiveSprite.sprite.getWidth() / 2,
                    startPos.y * PPM - playerDiveSprite.sprite.getHeight() / 2);

            for (LayerManager lm : layerManagers) {
                lm.setBeginZoom();
            }
        }
        
        playerDiveSprite.step();
        
    }
    
    //fall camera zoom during State.FALLING
    public void fallingUpdate(){
        if (diveFC.complete) {

            GameScreen.player.getBody().setTransform(
                    new Vector2(
                            (currentSection.getPos().x + currentSection.getWidth() / 2) / PPM,
                            (currentSection.getPos().y + currentSection.getHeight() / 2) / PPM), 0);
            GameScreen.player.getBody().setLinearVelocity(new Vector2(0, 0));

            //zoom out to normal
            if (fallDown) {
                currentPlayerZoom = currentPlayerZoom >= PLAYER_LAYER_ZOOM ? PLAYER_LAYER_ZOOM : currentPlayerZoom + 0.065f;
            } else {

                //fallUp
                currentPlayerZoom = currentPlayerZoom <= PLAYER_LAYER_ZOOM ? PLAYER_LAYER_ZOOM : currentPlayerZoom + 0.065f;

            }

            //complete current fall, resume play
            if (currentPlayerZoom == PLAYER_LAYER_ZOOM) {

                sm.setState(1);

                currentTopZoom = TOP_LAYER_ZOOM;
                currentPlayerZoom = PLAYER_LAYER_ZOOM;

                diveIn = false;
                diveMovement = 0.1f;

                /*
                if (fallDown) {
                    this.spawnEntity(new EntitySprite(
                            impactSprite,
                            GameScreen.player.getPos().x - impactSprite.getWidth() / 2,
                            GameScreen.player.getPos().y - impactSprite.getHeight() * 0.175f,
                            impactSprite.getWidth(),
                            impactSprite.getHeight(),
                            true, false));
                }*/

                //play impact sound
                impactSound.play(false);
            }

        } else {
            //fall zoom into current section
            //currentSectionZoom = currentSectionZoom <= 1.0f ? 1.0f : currentSectionZoom - 0.0678f;
            for (LayerManager lm : layerManagers) {
                lm.updatePitZoom(fallDown);
            }

            //move player towards center of pit section
            Vector2 sectionPos = new Vector2(
                    (currentSection.getPos().x + currentSection.getWidth() / 2) / PPM,
                    (currentSection.getPos().y + currentSection.getHeight() / 2) / PPM);
            //prevSection
            Vector2 dir = sectionPos.cpy().sub(prevSectionPosition).nor();
            float dist = sectionPos.dst(GameScreen.player.getBody().getPosition());

            //move player to center of currentSection
            GameScreen.player.getBody().setTransform(
                    prevSectionPosition.add(dir.scl(diveMovement * dist)), 0);
            diveMovement = diveMovement >= 1 ? 1 : diveMovement / 0.99f;

            if (fallDown) {
                //zoom for sections falling FROM
                currentTopZoom = currentTopZoom <= 0.03f ? 0.03f : currentTopZoom - 0.055f;

                //needed for player sprite going out of view at end of dive
                if (!diveIn) {
                    currentPlayerZoom = currentPlayerZoom <= 0.80f ? 0.80f : currentPlayerZoom - 0.0075f;
                    if (currentPlayerZoom == 0.8f) {
                        diveIn = true;
                    }
                } else {
                    currentPlayerZoom = currentPlayerZoom >= 1.0f ? 1.0f : currentPlayerZoom + 0.016f;
                }

            } else {

                //fallUp
                currentTopZoom = currentTopZoom >= 0.03f ? 0.03f : currentTopZoom + 0.055f;

                //needed for player sprite going out of view at end of dive
                if (!diveIn) {
                    currentPlayerZoom = currentPlayerZoom >= 0.80f ? 0.80f : currentPlayerZoom + 0.0075f;
                    if (currentPlayerZoom == 0.8f) {
                        diveIn = true;
                    }
                } else {
                    currentPlayerZoom = currentPlayerZoom <= 1.0f ? 1.0f : currentPlayerZoom - 0.016f;
                }

            }

        }
        
        GameScreen.player.fall();
        //playerDiveSprite.step();
    }
    
    
    
    public void resetDiveSprite(ImageSprite e){
        e.sprite.setPosition(PLAYER_DIVE_POS.x, PLAYER_DIVE_POS.y);
        e.sprite.setScale(PLAYER_DIVE_SCALE);
    }
    
    //Used during player dive
    public void moveDiveSprite(ImageSprite e){
        e.sprite.setPosition(e.sprite.getX() + moveSpeed, e.sprite.getY() + moveSpeed);
        e.sprite.setScale(e.sprite.getScaleX()*1.015f);
    }
    
    private final float BG_ZOOM_RATE = 0.00065f, BG_ZOOM_IN = 0.9f;
    private boolean zoomIn = true;
    //zoom in/out on bg
    //wobble effect
    private void bgZoomUpdate(){
        
        currentBgZoom = zoomIn ? currentBgZoom - BG_ZOOM_RATE : currentBgZoom + BG_ZOOM_RATE;
        if(!zoomIn && currentBgZoom > BG_LAYER_ZOOM)   zoomIn = true;
        if(zoomIn && currentBgZoom < BG_ZOOM_IN)   zoomIn = false;
    }
    
    //called when player falls off platform
    private boolean fallDown = true;
    private Vector2 prevSectionPosition;     //used for fallingUpdate
    
    public void fall(NullSection section, boolean fallDown){
        
        this.fallDown = fallDown;
        
        currentSection  = section;
        
        if (fallDown) {
            prevSectionPosition = new Vector2(
                    (currentSection.parentSection.getPos().x + currentSection.parentSection.getWidth() / 2) /PPM,
                    (currentSection.parentSection.getPos().y + currentSection.parentSection.getHeight() / 2) /PPM);
        } else {
            prevSectionPosition = new Vector2(
                    (currentSection.childSection.getPos().x + currentSection.childSection.getWidth() / 2)/PPM,
                    (currentSection.childSection.getPos().y + currentSection.childSection.getHeight() / 2)/PPM);
        }
        
        int tempDepth = currentDepth - section.LAYER_DEPTH;
        currentDepth = section.LAYER_DEPTH;
        
        for(LayerManager lm : layerManagers) { 
            lm.adjustZoom(tempDepth);
        }
        
        //add player to new section layerManager
        int tdepth = 0;
        for(LayerManager lm : layerManagers){
            for(Entity e : lm.layerEntities){
                if(e.equals(GameScreen.player)){
                    lm.layerEntToRemove.add(e);
                    tdepth = lm.depth;
                }
            }
        }
        tdepth = fallDown ? tdepth + 1 : tdepth - 1;
        layerManagers.get(tdepth).layerEntities.add(GameScreen.player);
        
        
        GameScreen.player.fall();
        
        diveFC.start(fm);
        sm.setState(4);
        
    }
    
    
    protected int top0;
    protected int top1;
    protected int bottom0;
    protected int bottom1;
    
    @Override
    public void render(SpriteBatch sb, int layer){
        
        if (top0 == layer) {
            if (sm.getState() == StateManager.State.BEGIN) {

                if (diveFC.complete) {
                    moveDiveSprite(playerDiveSprite);
                }

                playerDiveSprite.render(sb);

            }

            if (sm.getState() == StateManager.State.FALLING) {
                GameScreen.player.render(sb);
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

            //set zoom for bg
            GameScreen.camera.zoom = currentBgZoom;
        } 
        
        
        else if (top1 == layer) {
            

            //set zoom for player
            GameScreen.camera.zoom = currentPlayerZoom;
            //GameScreen.camera.zoom = currentTopZoom;
        } 
        
        
        /*
                RENDER LAYER MANAGERS
        */
        
        //accessed by layerManagers index (with top1 as offset)
        else if (layer > top1 && layer <= (layerManagers.size + top1)) {

            layerManagers.get(layer - top1 - 1).render(sb);
            
            //set zoom for next layer
            if(layer-top1-2 < 0){   //if at end of layerManagers, set zoom to currentTopZoom
                GameScreen.camera.zoom = currentTopZoom;
            }else{
                GameScreen.camera.zoom = layerManagers.get(layer - top1 - 2).zoom;
            }
            
            
        } 
        
        
        
        else if (bottom0 == layer) {
            bgRockSprite.render(sb);

            this.fgParallaxX = 1.0f;
            this.fgParallaxY = 1.0f;
            
            //GameScreen.camera.zoom = currentTopZoom;
            GameScreen.camera.zoom = layerManagers.peek().zoom;
            
        } 
        
        
        else if (bottom1 == layer) {
            if (bg != null) {
                sb.draw(bg, -width / 2, -height / 2, width, height);
            }

            //need to set camera zoom one iteration before next render call
            //pit zoom
            this.fgParallaxX = 0.05f;
            this.fgParallaxY = 0.025f;
        }
        
    }
    
    
    
    
    @Override
    public void begin(){
        super.begin();
        
        this.setPlayerToStart();
        
        bg = introTexture;
        
        GameScreen.camera.setPosition(playerPos.x*PPM, playerPos.y*PPM);
        
        //for beginnign player dive
        beginCheck = false;
        
        
        playerDiveSprite = GameScreen.player.getDiveSprite();
        playerDiveSprite.sprite.setScale(1.0f);
        playerDiveSprite.sprite.setPosition(
                playerPos.x*PPM - playerDiveSprite.sprite.getWidth()/2, 
                playerPos.y*PPM - playerDiveSprite.sprite.getHeight()/2);
        
        
        //sound
        bgm1 = bgm.random();
        bgm1.play();
        
        
    }
    
    @Override
    public void play(){
        super.play();
        
        //spawnEntity(impactSprite);
        
        //introTextSprite.setPosition(new Vector2(
                //playerPos.x*PPM - introTextSprite.getWidth()/2, 
                //playerPos.y*PPM - introTextSprite.getHeight()*2)); // todo:  *2  why????
        //spawnEntity(introTextSprite);
        //begin null section
        
        //play impact sound
        impactSound.play(false);
        
        entityCheck();
        this.renderSectionSort();
        
    }
    
    @Override
    public void pause(){
        sm.setPaused(true);
        
        playerPos = startPos.cpy();
        reset();
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        //sprites.clear();
        
        super.end(id, time);
    }
    
    //used for end arm warp
    //todo: remove, old code
    public void end(){
        
        this.end(linkid, 0);
    }
    
    @Override
    public void complete(){
        
        for(LayerManager lm : layerManagers){
            for(Entity e : lm.layerEntities){
                if(e.equals(GameScreen.player)){
                    lm.layerEntToRemove.add(e);
                }
            }
            lm.removeUpdate();
        }
        
        //initLayerBuffer = true;
        //initLayerSort = true;
        
        this.setPlayerToStart();
        
        
        
        super.complete();
    }
    
    
    //init all sections in layerMap, layerManagers
    public void initSections(){
        
        this.generateSections();
        
        for(LayerManager lm : layerManagers){
            lm.init();
        }
        
        
        //layers + top 2 and bottom 2
        renderLayers = layerManagers.size + 4;

        top0 = 0;
        top1 = 1;
        bottom0 = renderLayers - 2;
        bottom1 = renderLayers - 1;
    }
    
    /**************************************
    Sort entities/sprites for each section
    *****************************************/
    
    private void renderSectionSort(){
        //entites
        for(Entity e : entities){
            renderSectionSortEntity(e);
        }
    }
    
    private void renderSectionSortEntity(Entity e){
        //entites
            Vector2 ep = e.getPos().cpy();
            
            for(LayerManager lm : layerManagers){
                for(NullSection ns : lm.layerSections){
                    if(ep.x >= ns.getPos().x 
                            && ep.x < ns.getPos().x + ns.getWidth() 
                            && ep.y >= ns.getPos().y 
                            && ep.y < ns.getPos().y + ns.getHeight()
                            && !lm.layerEntities.contains(e)){
                        
                        lm.layerEntities.add(e);
                    }
                }
            }
    }
    
    @Override 
    public Entity spawnEntity(Entity e){
        renderSectionSortEntity(e);
        return super.spawnEntity(e);
    }
    
    
    //Set sectionCount based on difficulty
    public void generateSections(){
        
        int scount;
        int layers;
        
        switch(DIFFICULTY){
            case 0:
                scount = 3;
                layers = 1;
                break;
                
            case 1:
                scount = 3;
                layers = 2;
                break;
            
            case 2:
                scount = 4;
                layers = 2;
                break;
                
            case 3:
                scount = 5;
                layers = 3;
                break;
                
            case 4:
                scount = 5;
                layers = 4;
                break;
                
            default:
                scount = 2;
                layers = 1;
                break;
        }
        
        generateLayer0(scount, false);
        
        generateLayer(layers-1, scount-1);
    }
    
    /*******************************
        GENERATE FIRST LAYER
    *******************************/
    
    private void generateLayer0(int scount, boolean reset){
        sectionCount = scount;

        if (layerManagers.size == 0) {
            layerManagers.add(new LayerManager(0));
        }
    
        LayerManager mainLayer = layerManagers.peek();
        
        
        for(int i = 0; i < sectionCount; i++){
            
            //create first null section
            if(mainLayer.layerSections.size == 0){
                mainLayer.layerSections.add(new NullSection(
                        new Vector2(200*RATIO , 100*RATIO), 
                        sectionWidth, 
                        sectionHeight, 
                        this,
                        nullCoord,
                        mainLayer.depth));
                gridCoords.add(nullCoord);
            }else{
                
                //create ajoined null sections
                NullSection prevSection = reset ? mainLayer.layerSections.random() : mainLayer.layerSections.peek();
                boolean [] prevSides = prevSection.getAvailableSides();
                int index;
                
                
                Vector2 secPosition;

                Array<Boolean> sideCheck = new Array<Boolean>();
                sideCheck.add(false);
                sideCheck.add(false);
                sideCheck.add(false);
                sideCheck.add(false);
                boolean occupied;
                int count;
                
                do{
                    
                    index = rng.nextInt(prevSides.length);
                    
                    
                    switch (index){
                        case 0:
                            nullCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY()+1);
                            secPosition = new Vector2(0, sectionHeight);
                            break;
                        case 1:
                            nullCoord = new Coordinate(prevSection.getCoord().getX()+1, prevSection.getCoord().getY());
                            secPosition = new Vector2(sectionWidth, 0);
                            break;
                        case 2:
                            nullCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY()-1);
                            secPosition = new Vector2(0, -sectionHeight);
                            break;
                        default: //3
                            nullCoord = new Coordinate(prevSection.getCoord().getX()-1, prevSection.getCoord().getY());
                            secPosition = new Vector2(-sectionWidth, 0);
                            break;
                    }
                    
                    //if coord is already taken
                    occupied = false;
                    try {
                        for (Coordinate coord : gridCoords) {
                            if (coord.compareTo(nullCoord)) {
                                sideCheck.set(index, true);
                                occupied = true;
                            }
                        }
                    } catch (IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                    }
                    
                    
                    //todo: fix the "spiral problem" w/ section generation
                    //perhaps just choose random coord??
                    //use array[s,s,s,s] to check all four sides
                    //if(count == 4) return;
                    count = 0;
                    for(Boolean s : sideCheck){
                        if(s) count++;
                    }
                    if(count >= 4){
                        generateLayer0(sectionCount - i, true);
                        return;
                    }
                    
                }while(occupied);
                    
                prevSection.setSide(index, false, NullSection.WallType.CONNECTED);
                gridCoords.add(nullCoord);
                
                //create section at specifies coord(x,y)
                mainLayer.layerSections.add(new NullSection(
                        prevSection.getPos().cpy().add(secPosition),
                        sectionWidth,
                        sectionHeight,
                        this,
                        nullCoord,
                        mainLayer.depth)); //layerDepth
                
                switch(index){
                    case 0:
                        mainLayer.layerSections.peek().setSide(2, false, NullSection.WallType.CONNECTED);
                        break;
                    case 1:
                        mainLayer.layerSections.peek().setSide(3, false, NullSection.WallType.CONNECTED);
                        
                        break;
                    case 2:
                        mainLayer.layerSections.peek().setSide(0, false, NullSection.WallType.CONNECTED);
                        
                        break;
                    case 3:
                        mainLayer.layerSections.peek().setSide(1, false, NullSection.WallType.CONNECTED);
                        break;
                    default:
                        break;
                }
                
            }
        }
    }
    
    
    
    
    /*********************************************************************************
        CREATES AN INITIAL PIT SECTION AND NEW LAYERMANAGER, based on piChance
    *********************************************************************************/
    
    
    private int generateLayer(int layers, int scount){
        
        //recursive termination
        if(layers <= 0) return 0;
        
        int depth = layerManagers.size;
        
        System.out.println("@EnvNull generate layer : depth:" + depth);
        
        LayerManager prevLayer = layerManagers.get(depth - 1);
        layerManagers.add(new LayerManager(depth));
        LayerManager currentLayer = layerManagers.peek();

        /**
         * *****************************************
         *
         * PIT SECTION GENERATION
         *
         ******************************************
         */
        //go through sections of prevLayer
        NullSection prevSection = prevLayer.layerSections.peek();

        //check section for available adjecent sections
        boolean[] sides = prevSection.getAvailableSides();

        int index;

        Array<Boolean> sideCheck = new Array<Boolean>();
        sideCheck.add(false);
        sideCheck.add(false);
        sideCheck.add(false);
        sideCheck.add(false);
        int count;
        boolean occupied;

        do {
            index = rng.nextInt(sides.length);

            switch (index) {
                case 0:
                    nullCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY() + 1);
                    break;
                case 1:
                    nullCoord = new Coordinate(prevSection.getCoord().getX() + 1, prevSection.getCoord().getY());
                    break;
                case 2:
                    nullCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY() - 1);
                    break;
                case 3:
                    nullCoord = new Coordinate(prevSection.getCoord().getX() - 1, prevSection.getCoord().getY());
                    break;
                default:
                    nullCoord = new Coordinate(prevSection.getCoord().getX() - 1, prevSection.getCoord().getY());
                    break;
            }

            //if coord is already taken
            occupied = false;
            try {
                for (Coordinate coord : gridCoords) {
                    if (coord.compareTo(nullCoord)) {
                        sideCheck.set(index, true);
                        occupied = true;
                    }
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }

            
            count = 0;
            for (Boolean s : sideCheck) {
                if (s) {
                    count++;
                }
            }
            if (count >= 4) {
                layerManagers.pop();
                return generateLayer(layers, scount);
            }

        } while (occupied);

        prevSection.setSide(index, false, NullSection.WallType.PIT_HIGHER);
        gridCoords.add(nullCoord);

        switch (index) {
            case 0:
                currentLayer.layerSections.add(new NullSection(
                        prevSection.getPos().cpy().add(new Vector2(0, sectionHeight)),
                        sectionWidth,
                        sectionHeight,
                        this,
                        nullCoord,
                        currentLayer.depth));

                currentLayer.layerSections.peek().setSide(2, false, NullSection.WallType.PIT_LOWER);
                break;
            case 1:
                currentLayer.layerSections.add(new NullSection(
                        prevSection.getPos().cpy().add(new Vector2(sectionWidth, 0)),
                        sectionWidth,
                        sectionHeight,
                        this,
                        nullCoord,
                        currentLayer.depth));

                currentLayer.layerSections.peek().setSide(3, false, NullSection.WallType.PIT_LOWER);
                break;
            case 2:
                currentLayer.layerSections.add(new NullSection(
                        prevSection.getPos().cpy().add(new Vector2(0, -sectionHeight)),
                        sectionWidth,
                        sectionHeight,
                        this,
                        nullCoord,
                        currentLayer.depth));

                currentLayer.layerSections.peek().setSide(0, false, NullSection.WallType.PIT_LOWER);
                break;
            case 3:
                currentLayer.layerSections.add(new NullSection(
                        prevSection.getPos().cpy().add(new Vector2(-sectionWidth, 0)),
                        sectionWidth,
                        sectionHeight,
                        this,
                        nullCoord,
                        currentLayer.depth));

                currentLayer.layerSections.peek().setSide(1, false, NullSection.WallType.PIT_LOWER);
                break;
            default:
                break;
        }
        //set pit section child of higher pit section
        prevSection.childSection = currentLayer.layerSections.peek();
        currentLayer.layerSections.peek().parentSection = prevSection;

        fillLayer(currentLayer, scount, false);

        return generateLayer(layers - 1, scount-1);
    }
    
    
    /***********************************************************************
        FILL IN NEW PIT SECTION/LAYER WITH MORE SECTIONS
    ***********************************************************************/
    
    private void fillLayer(LayerManager lm, int scount, boolean reset){
        
        for (int i = 0; i < scount; i++) {

            //create ajoined null sections
            NullSection prevSection = !reset ? lm.layerSections.peek() : lm.layerSections.random();
            boolean[] prevSides = prevSection.getAvailableSides();
            int index;

            Vector2 secPosition;
            
            Array<Boolean> sideCheck = new Array<Boolean>();
            sideCheck.add(false);
            sideCheck.add(false);
            sideCheck.add(false);
            sideCheck.add(false);
            boolean occupied;
            int count;

            do {
                index = rng.nextInt(prevSides.length);
                
                switch (index) {
                    case 0:
                        nullCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY() + 1);
                        secPosition = new Vector2(0, sectionHeight);
                        break;
                    case 1:
                        nullCoord = new Coordinate(prevSection.getCoord().getX() + 1, prevSection.getCoord().getY());
                        secPosition = new Vector2(sectionWidth, 0);
                        break;
                    case 2:
                        nullCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY() - 1);
                        secPosition = new Vector2(0, -sectionHeight);
                        break;
                    default: //3
                        nullCoord = new Coordinate(prevSection.getCoord().getX() - 1, prevSection.getCoord().getY());
                        secPosition = new Vector2(-sectionWidth, 0);
                        break;
                }

                //if coord is already taken
                occupied = false;
                try {
                    for (Coordinate coord : gridCoords) {
                        if (coord.compareTo(nullCoord)) {
                            sideCheck.set(index, true);
                            occupied = true;
                        }
                    }
                } catch (IndexOutOfBoundsException ex) {
                    ex.printStackTrace();
                }

                //todo: fix the "spiral problem" w/ section generation
                //perhaps just choose random coord??
                //use array[s,s,s,s] to check all four sides
                //if(count == 4) return;
                count = 0;
                for (Boolean s : sideCheck) {
                    if (s) {
                        count++;
                    }
                }
                if (count >= 4) {
                    fillLayer(lm, scount - i, true);
                    return;
                }

            } while (occupied);

            prevSection.setSide(index, false, NullSection.WallType.CONNECTED);
            gridCoords.add(nullCoord);

            //create section at specifies coord(x,y)
            lm.layerSections.add(new NullSection(
                    prevSection.getPos().cpy().add(secPosition),
                    sectionWidth,
                    sectionHeight,
                    this,
                    nullCoord,
                    lm.depth)); //layerDepth

            switch (index) {
                case 0:
                    lm.layerSections.peek().setSide(2, false, NullSection.WallType.CONNECTED);
                    break;
                case 1:
                    lm.layerSections.peek().setSide(3, false, NullSection.WallType.CONNECTED);

                    break;
                case 2:
                    lm.layerSections.peek().setSide(0, false, NullSection.WallType.CONNECTED);

                    break;
                case 3:
                    lm.layerSections.peek().setSide(1, false, NullSection.WallType.CONNECTED);
                    break;
                default:
                    break;
            }

        }
    }
    
    
    protected class LayerManager{
        
        public Array<NullSection> layerSections = new Array<NullSection>();
        public ArrayList<Entity> layerEntities = new ArrayList<Entity>();
        private ArrayList<Entity> layerEntToRemove = new ArrayList<Entity>();
        
        public int depth = 0;
        private final float BASE_ZOOM = 1.0f;
        private final float BEGIN_ZOOM_BASE = 10.f;
        public float beginZoom;
        public float maxZoom;
        public float zoom = 1.0f;
        private float beginZoomRate = (9 * MainGame.STEP) / (2000 / 1000);
        private float pitZoomRate = 0.0115f;
        private double scaleFactor = 2.0;   //needed for adjusting zoom of pits during falls. see adJustZoom(int)
        
        public LayerManager(int depth){
            this.depth = depth;
            
            zoom = BASE_ZOOM * (float)Math.pow(scaleFactor, (double)depth);
            maxZoom = zoom;
            beginZoom = BEGIN_ZOOM_BASE * (depth+1);
            beginZoomRate *= (depth+1);
            pitZoomRate *= (depth+1);
        }
        
        public void init(){
            for(NullSection s: layerSections){
                s.init();
            }
        }
        
        public void update(){
            for(Entity e : layerEntities){
                e.active = currentDepth == depth;
            }
        }
        
        public boolean isComplete(){
            return layerEntities.isEmpty();
        }
        
        public void render(SpriteBatch sb){
            //render entities and sections here
            
            for (NullSection s : layerSections) {
                s.render(sb);
            }

            Collections.sort(layerEntities, new Entity.EntityComp());

            for (Entity e : layerEntities) {
                if (e.equals(GameScreen.player) 
                        && sm.getState() == StateManager.State.FALLING) {
                    continue;
                }
                e.render(sb);
            }
            
            //check for entity or sprite removal
            removeUpdate();


        }
        
        private void removeUpdate(){
            for(Entity e : layerEntities){
                if(!entities.contains(e) && !entToAdd.contains(e, false)){
                    layerEntToRemove.add(e);
                }
            }
            
            for(Entity e : layerEntToRemove){
                layerEntities.remove(e);
            }
            layerEntToRemove.clear();
            
        }
        
        public void setBeginZoom(){
            zoom = beginZoom;
        }
        
        public void updateBeginZoom(){
            zoom = zoom <= maxZoom ? maxZoom : zoom - beginZoomRate;
        }
        
        public void updatePitZoom(boolean fallDown){
            if(fallDown){
                zoom = zoom <= maxZoom ? maxZoom : zoom - pitZoomRate;
            }else{
                zoom = zoom >= maxZoom ? maxZoom : zoom + pitZoomRate;
            }
            
        }
        
        public void adjustZoom(int direction){
            if(direction < 0){
                maxZoom /= scaleFactor;
            }else if(direction > 0){
                maxZoom *= scaleFactor;
            }
        }
        
        
    }
    
    
    //**************************************
        
        //  ENEMIES
        
    public void spawnEnemyGroup(NullSection sec){
        //TODO: temp difficulty until enemy rewrite
        Array<Entity> ent = EnemyManager.getGroup(DIFFICULTY, sec);
        for(Entity e : ent){
            this.toAddEntity(e);
            
            //todo: get rid of this enemy count
            //use some sort of check in layer managers
            this.addEnemyCount();
        }
    }
    
    
    //TODO: transfer this to Environment class, not needed here
    public void spawnEnemy(){
        currentEnemies++;
    }
    
    //todo: remove, check layerManager.complete
    @Override
    public void addKillCount(){
        super.addKillCount();
        
        if(killCount >= enemyCount){
            spawnEndWarp();
        }
    }
    
    
    public void spawnEndWarp(){
        
        Vector2 secPos = layerManagers.get(0).layerSections.get(0).getPos();
        
        //loop through current layer
        for (LayerManager lm : layerManagers) {
            for (NullSection ns : lm.layerSections) {
                //if player in section, spawn null warp here
                PlayerEntity ply = GameScreen.player;
                if (ply.getPos().x > ns.getPos().x
                        && ply.getPos().x  <= ns.getPos().x + ns.getWidth()
                        && ply.getPos().y  > ns.getPos().y
                        && ply.getPos().y  <= ns.getPos().y + ns.getHeight()) {

                    secPos = ns.getPos();
                }
            }
        }
        
        endNullArm = new En_EndNullArm(new Vector2(
                secPos.x + 400*RATIO, 
                secPos.y + 400*RATIO),
                linkid);
        
        spawnEntity(endNullArm);
        
        //sound
        bgm1.stop();
        bgm1 = bgm_end;
        bgm1.play();
    }
    
    
    
    
    
    
}

