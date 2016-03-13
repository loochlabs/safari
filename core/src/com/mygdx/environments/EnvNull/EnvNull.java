/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.DynamicEntities.enemies.EnemyManager;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.entities.text.TextEntity;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.Coordinate;
import com.mygdx.utilities.SoundObject_Bgm;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Collections;

/**
 *
 * @author looch
 */
public abstract class EnvNull extends Environment {
    
    
    
    protected EntitySprite playerDive, impactSprite, introTextSprite, bgRockSprite;
    protected long diveTime;
    protected final float moveSpeed = 10.0f;
    protected float diveMovement = 1;
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
    //protected Array<NullSection> envSections = new Array<NullSection>(); //old
    //protected Array<NullSection> pitSections = new Array<NullSection>(); //old
    //protected Array<NullSection> sectionToAdd = new Array<NullSection>(); //old
    //protected Array<NullSection> sectionsToAdd = new Array<NullSection>();//old
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
    
    //public Array<NullSection> getEnvSections() { return envSections; }
    //public Array<NullSection> getPitSection() { return pitSections; }
    
    public EnvNull(int id, int linkid, int dif){
        super(id);
        
        this.linkid = linkid;
        this.width = MainGame.WIDTH;
        this.height = MainGame.HEIGHT;
        this.DIFFICULTY = dif;
        
        introDescription = "The Null";
        
        playTexture = MainGame.am.get(ResourceManager.NULL_BG1);
        introTexture = MainGame.am.get(ResourceManager.NULL_BG1);
        outroTexture = MainGame.am.get(ResourceManager.NULL_PH);
        
        fgx = 0;
        fgy = 0;
        fgw = width;
        fgh = height;
        
        renderLayers = 7;
        cameraZoom = 1.0f;
        
        startPos = new Vector2(MainGame.WIDTH*0.55f/PPM,MainGame.HEIGHT*0.65f/PPM);
        this.setPlayerToStart();
        
        playerDive = GameScreen.player.getDiveSprite();
        playerDive.sprite.setPosition(width * 0.5f, height * 0.15f);
        diveTime = (long)(2000 * 0.6);
        
        impactSprite = new EntitySprite("player-impact", false, false, false, false);
        impactSprite.sprite.setPosition(playerPos.x*PPM - impactSprite.sprite.getWidth()/2, 
                playerPos.y*PPM - impactSprite.sprite.getHeight()*0.175f);
        
        introTextSprite = new EntitySprite("kill-text", false);
        
        bgRockSprite = new EntitySprite("null-bg-rocks", false);
        bgRockSprite.sprite.setScale(1.0f * RATIO);
        bgRockSprite.sprite.setPosition(
                 - width/2 + 150f*RATIO, 
                 - height/2 + 75*RATIO);
        
        //end
        endFC.setTime(4.0f);
        
        //sound
        //bgm.add(new SoundObject_Bgm(ResourceManager.BGM_NULL_1));
        //bgm.add(new SoundObject_Bgm(ResourceManager.BGM_NULL_2));
        bgm.add(new SoundObject_Bgm(ResourceManager.BGM_NULL_3));
        bgm_end = new SoundObject_Bgm(ResourceManager.BGM_NULL_END);
        
        impactSound = new SoundObject_Sfx(ResourceManager.SFX_NULL_IMPACT);
            
    }
    
    
    @Override
    public void init(){
        super.init();
        this.initSections();
    }
    
    @Override
    public void update(){
        
        //TODO: remove
        /*
        if(sm.getState() == State.PLAYING){
            for(NullSection section: sectionToAdd){
                section.init();
                
                envSections.add(section);
            }
            
            if(sectionToAdd.size > 0) {
                sectionToAdd.clear();
            }
        }
        */
        //******************************************
        
        if(sm.getState() == StateManager.State.BEGIN)
            fallingBeginUpdate();
        
        super.update();
        
        if(sm.getState() == StateManager.State.FALLING )
            fallingUpdate();
            
        
        if(sm.getState() == State.END)
            fallingEndUpdate();
        
        bgZoomUpdate();
        
        
    }
    
    boolean beginCheck = false;     //used for initial beginning camera zoom
    float topRate = (9 * MainGame.STEP) / (2000 / 1000);
    
    //fall camera zoom during State.BEGIN
    
    //TODO: add ability to render entities waiting in sections during zoom
    public void fallingBeginUpdate(){
        
        if(beginCheck){
            if(beginFC.complete){
                currentTopZoom = TOP_LAYER_ZOOM;
            }else{
            
                currentTopZoom = currentTopZoom <= TOP_LAYER_ZOOM ? TOP_LAYER_ZOOM : currentTopZoom - topRate;
            }
        }else{
            beginCheck = true;
            
            currentTopZoom = BEGIN_TOP_ZOOM;
        }
        
        
        
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
            //todo: may need to clean up
            currentPlayerZoom = currentPlayerZoom >= PLAYER_LAYER_ZOOM ? PLAYER_LAYER_ZOOM : currentPlayerZoom + 0.065f;

            //complete current fall, resume play
            if (currentPlayerZoom == PLAYER_LAYER_ZOOM) {

                sm.setState(1);
                
                //currentSectionZoom = SECTION_LAYER_ZOOM;
                currentTopZoom = TOP_LAYER_ZOOM;
                currentPlayerZoom = PLAYER_LAYER_ZOOM;

                diveIn = false;
                diveMovement = 1;


                this.spawnSprite(new EntitySprite(
                        impactSprite,
                        GameScreen.player.getBody().getPosition().x * PPM - impactSprite.sprite.getWidth() / 2,
                        GameScreen.player.getBody().getPosition().y * PPM - impactSprite.sprite.getHeight()*0.175f,
                        false));
                
                //play impact sound
                impactSound.play(false);
            }

        } else {
            //fall zoom into current section
            //currentSectionZoom = currentSectionZoom <= 1.0f ? 1.0f : currentSectionZoom - 0.0678f;
            for(LayerManager lm : layerManagers){
                lm.zoom = lm.zoom <= lm.MAX_ZOOM ? lm.MAX_ZOOM : lm.zoom - 0.0678f;
            }
            
            
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

            
            //move player towards center of pit section
            Vector2 sectionPos = new Vector2(
                    (currentSection.getPos().x + currentSection.getWidth() / 2) / PPM,
                    (currentSection.getPos().y + currentSection.getHeight() / 2) / PPM);
            
            //todo: not needed
            //Vector2 dv = sectionPos.cpy().scl(1 / PPM).sub(GameScreen.player.getBody().getPosition()).cpy().nor();
            float dist = sectionPos.dst(GameScreen.player.getBody().getPosition());

            GameScreen.player.getBody().setTransform(
                    sectionPos.sub(sectionPos.cpy().nor().scl(diveMovement * dist)), 0);
            diveMovement = diveMovement <= 0 ? 0 : diveMovement * 0.975f;
        }
    }
    
    
    //fall camera zoom during State.END
    public void fallingEndUpdate(){
        //update currentTopZoom
        //update currentSectionZoom (playerDive)
        //update currentPitZoom
        
        if(endFC.running){
            
            currentTopZoom = currentTopZoom < 0 ? 0.01f : currentTopZoom - 0.02f;
            currentPitZoom = currentPitZoom < 0 ? 0.01f : currentPitZoom - 0.02f;
            //currentSectionZoom = 1.0f;
            
        }
        
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
    public void fall(NullSection section){
        currentSection  = section;
        
        int tempDepth = currentDepth - section.LAYER_DEPTH;
        currentDepth = section.LAYER_DEPTH;
        
        for(LayerManager lm : layerManagers) { 
            //lm.MAX_ZOOM /= 2;
            lm.adjustZoom(tempDepth);
        }
        
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
                    moveDiveSprite(playerDive);
                }

                playerDive.render(sb);

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
            if (sm.getState() == StateManager.State.PLAYING
                    || sm.getState() == State.END) {
                for (EntitySprite e : sprites) {
                    e.step();
                    e.sprite.draw(sb);
                }

                Collections.sort(entities, new Entity.EntityComp());

                for (Entity e : entities) {
                    e.render(sb);
                }

            }

            if (sm.getState() == StateManager.State.FALLING) {
                for (EntitySprite e : sprites) {
                    e.step();
                    e.sprite.draw(sb);
                }

                Collections.sort(entities, new Entity.EntityComp());

                for (Entity e : entities) {
                    //move this conditional to above looping
                    if (!e.equals(GameScreen.player)) {
                        e.render(sb);
                    }
                }
            }

            if (sm.getState() == State.END) {
                endSpectralAnim(sb);
            }

            //set zoom for player
            GameScreen.camera.zoom = currentPlayerZoom;
        } 
        
        
        
        else if (layer > top1 && layer <= (layerManagers.size + top1)) {

            layerManagers.get(layer - top1 - 1).render(sb);
            
            //set zoom for next layer
            if(layer-top1-2 < 0){
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
        
        beginCheck = false;
        playerDive.sprite.setScale(1.0f);
        playerDive.sprite.setPosition(
                playerPos.x*PPM - playerDive.sprite.getWidth()/2, 
                playerPos.y*PPM - playerDive.sprite.getHeight()/2);
        
        
        //sound
        bgm1 = bgm.random();
        bgm1.play();
    }
    
    @Override
    public void play(){
        super.play();
        
        spawnSprite(impactSprite);
        
        introTextSprite.sprite.setPosition(
                playerPos.x*PPM - introTextSprite.sprite.getWidth()/2, 
                playerPos.y*PPM - introTextSprite.sprite.getHeight()*2);
        spawnSprite(introTextSprite);
        //begin null section
        
        //play impact sound
        impactSound.play(false);
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        sprites.clear();
        super.end(id, time);
    }
    
    //used for end arm warp
    //todo: remove, old code
    public void end(){
        playerDive.sprite.setPosition(
                GameScreen.player.getBody().getPosition().x*PPM - playerDive.sprite.getWidth()/2, 
                GameScreen.player.getBody().getPosition().y*PPM - playerDive.sprite.getHeight()/2);
        
        
        this.end(linkid, 0);
    }
    
    
    //init all sections in layerMap, layerManagers
    public void initSections(){
        
        //layers + top 2 and bottom 2
        renderLayers = layerManagers.size + 4;
        
        top0 = 0;
        top1 = 1;
        bottom0 = renderLayers - 2;
        bottom1 = renderLayers - 1;
    }
    
    //**************************************
        
        //  ENEMIES
        
    public void spawnEnemyGroup(NullSection sec){
        Array<Entity> ent = EnemyManager.getGroup(DIFFICULTY, sec);
        for(Entity e : ent){
            this.spawnEntity(e);
            this.addEnemyCount();
        }
    }
    
    
    //TODO: transfer this to Environment class, not needed here
    public void spawnEnemy(){
        currentEnemies++;
    }
    
    //Used during player dive
    public void moveDiveSprite(EntitySprite e){
        e.sprite.setPosition(e.sprite.getX() + moveSpeed, e.sprite.getY() + moveSpeed);
        e.sprite.setScale(e.sprite.getScaleX()*1.015f);
    }
    
    public void spawnEndWarp(){
        
        Vector2 secPos = layerManagers.get(0).layerSections.get(0).getPos();
        
        //loop through current layer
        for(NullSection sec: layerManagers.get(0).layerSections){
            //if player in section, spawn null warp here
            PlayerEntity ply = GameScreen.player;
            if(ply.getBody().getPosition().x*PPM > sec.getPos().x 
                    && ply.getBody().getPosition().x*PPM <= sec.getPos().x + sec.getWidth()
                    && ply.getBody().getPosition().y*PPM > sec.getPos().y
                    && ply.getBody().getPosition().y*PPM <= sec.getPos().y + sec.getHeight()){
             
                secPos = sec.getPos();
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
    
    
    //todo: remove, check layerManager.complete
    @Override
    public void addKillCount(){
        super.addKillCount();
        
        if(killCount >= enemyCount){
            spawnEndWarp();
        }
    }
    
    
    //add parameter for layer depth 
    public void generateSections(int scount){
        
        generateLayer0(scount);
        
        //generateLayer(1, sectionCount/2) -> layer 0
        //generateLAyer(2, sectionCount/2) -> layer 1
        //generateLayer(3, sectionCount/2) -> layer 2
        
        generateLayer(1, scount/2);
    }
    
    
    /*
        CREATES AN INITIAL PIT SECTION AND NEW LAYERMANAGER, based on piChance
    */
    
    private float pitChance = 0.5f;
    
    private int generateLayer(int depth, int scount){
        
        //recursive termination
        if(scount <= 1) return 0;
        
        System.out.println("@EnvNull generate layer : depth:" + depth);
        
        
        LayerManager prevLayer = layerManagers.peek();
        layerManagers.add(new LayerManager(depth));
        LayerManager currentLayer = layerManagers.peek();
        
        /*******************************************
         * 
         *          PIT SECTION GENERATION
         * 
         *******************************************/
        
        
        //go through sections of prevLayer
        for (NullSection prevSection : prevLayer.layerSections) {
            //check to generate pit at this section
            if (pitChance > rng.nextFloat()) {

                //check section for available adjecent sections
                boolean[] sides = prevSection.getAvailableSides();
                boolean occupied;
                int index;
                int count = 0;

                do {
                    index = rng.nextInt(sides.length);
                    occupied = false;
                    count++;

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

                    for (Coordinate coord : gridCoords) {
                        if (coord.compareTo(nullCoord)) {
                            occupied = true;
                        }
                    }

                    //todo: fix the "spiral problem" w/ section generation
                    if (count == 4) {
                        break;
                    }

                } while (occupied);

                //break if broken out of prev while loop
                if (occupied) {
                    break;
                }

                prevSection.setSide(index, false, NullSection.WallType.PIT_HIGHER);
                gridCoords.add(nullCoord);

                switch (index) {
                    case 0:
                        currentLayer.layerSections.add(new NullSection_R(
                                prevSection.getPos().cpy().add(new Vector2(0, sectionHeight)),
                                sectionWidth,
                                sectionHeight,
                                this,
                                nullCoord,
                                currentLayer.depth));
                        
                        currentLayer.layerSections.peek().setSide(2, false, NullSection.WallType.PIT_LOWER);
                        break;
                    case 1:
                        currentLayer.layerSections.add(new NullSection_R(
                                prevSection.getPos().cpy().add(new Vector2(sectionWidth, 0)),
                                sectionWidth,
                                sectionHeight,
                                this,
                                nullCoord,
                                currentLayer.depth));

                        currentLayer.layerSections.peek().setSide(3, false, NullSection.WallType.PIT_LOWER);
                        break;
                    case 2:
                        currentLayer.layerSections.add(new NullSection_R(
                                prevSection.getPos().cpy().add(new Vector2(0, -sectionHeight)),
                                sectionWidth,
                                sectionHeight,
                                this,
                                nullCoord,
                                currentLayer.depth));

                        currentLayer.layerSections.peek().setSide(0, false, NullSection.WallType.PIT_LOWER);
                        break;
                    case 3:
                        currentLayer.layerSections.add(new NullSection_R(
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
                prevSection.childSection = currentLayer.layerSections.peek();
                
                fillLayer(currentLayer, scount/2);

            }

        }

        return generateLayer(depth + 1, scount / 2);
    }
    
    
    /*
        FILL IN NEW PIT SECTION/LAYER WITH MORE SECTIONS
    */
    
    private void fillLayer(LayerManager lm, int scount){
        
        for (int i = 0; i < scount; i++) {

            //create ajoined null sections
            NullSection prevSection = lm.layerSections.peek();
            boolean[] prevSides = prevSection.getAvailableSides();
            int index;

            boolean occupied;
            int count = 0;
            Vector2 secPosition;

            do {
                count++;
                index = rng.nextInt(prevSides.length);
                occupied = false;

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
                for (Coordinate coord : gridCoords) {
                    if (coord.compareTo(nullCoord)) {
                        occupied = true;
                    }
                }

                //todo: fix the "spiral problem" w/ section generation
                //perhaps just choose random coord??
                //use array[s,s,s,s] to check all four sides
                if (count == 4) {
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
    
    
    /*
        GENERATE FIRST LAYER
    */
    
    private void generateLayer0(int scount){
        sectionCount = scount;
            
        if(layerManagers.size == 0){
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
                NullSection prevSection = mainLayer.layerSections.peek();
                boolean [] prevSides = prevSection.getAvailableSides();
                int index;
                
                boolean occupied;
                int count = 0;
                Vector2 secPosition;
                
                do{
                    count++;
                    index = rng.nextInt(prevSides.length);
                    occupied = false;
                    
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
                    for(Coordinate coord: gridCoords){
                        if (coord.compareTo(nullCoord))
                            occupied = true;
                    }
                    
                    
                    //todo: fix the "spiral problem" w/ section generation
                    //perhaps just choose random coord??
                    //use array[s,s,s,s] to check all four sides
                    if(count == 4) return;
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
    
    
    
    protected class LayerManager{
        
        public Array<NullSection> layerSections = new Array<NullSection>();
        public Array<Entity> layerEntities = new Array<Entity>();
        public int depth = 0;
        private final float BASE_ZOOM = 1.0f;
        public float MAX_ZOOM;
        public float zoom = 1.0f;
        
        public LayerManager(int depth){
            this.depth = depth;
            
            zoom = BASE_ZOOM * (depth+1);
            MAX_ZOOM = zoom;
        }
        
        public void init(){
            for(NullSection s: layerSections){
                s.init();
            }
            
            //todo: add entities.init()???
        }
        
        
        public boolean isComplete(){
            return layerEntities.size == 0;
        }
        
        public void render(SpriteBatch sb){
            //render entities and sections here
            
            for(NullSection s : layerSections){
                s.render(sb);
            }
        }
        
        public void adjustZoom(int direction){
            if(direction < 0){
                MAX_ZOOM /= 2;
            }else if(direction > 0){
                MAX_ZOOM *= 2;
            }
        }
    }
    
}

