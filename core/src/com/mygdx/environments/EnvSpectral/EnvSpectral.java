/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvSection;
import com.mygdx.environments.Environment;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Bgm;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Collections;

/**
 *
 * @author looch
 * 
 * Description:  On player death, enter spectral environment
 *      -on exit, respawn at player
 */
public class EnvSpectral extends Environment{
    
    int sectionCount = 2;
    //float sectionWidth = 500 * RATIO;
    //float sectionHeight = 500 * RATIO;
    //Coordinate sectionCoord = new Coordinate(0, 0);
    //int sideIndex = 0;
    //private Array<Coordinate> gridCoords = new Array<Coordinate>();
    
    private ImageSprite introTextSprite, playerSprite, playerSpectralSprite, deathSprite;
    private float playerSpriteScale = 1.0f;
    
    
    //needed for getting coords of prev SpSections
    private Array<SpectralSection_Main> spSections = new Array<SpectralSection_Main>();
    
    //sound
    private SoundObject_Bgm bgm1;

    public EnvSpectral(int id, int linkid) {
        super(id);
        
        this.linkid = linkid;
        
        //todo: make pos more precise
        startPos = new Vector2(300*RATIO/PPM,300*RATIO/PPM);
        this.setPlayerToStart();
        
        /*
        beginSprite = new ImageSprite(GameScreen.player.getBuffSprite(),
                MainGame.WIDTH/2,
                MainGame.HEIGHT/2);
        
        introTextSprite = new ImageSprite("run-text", false);
        playerSprite = new ImageSprite(
                GameScreen.player.getBuffSprite(), 
                startPos.x*PPM - GameScreen.player.getBuffSprite().sprite.getWidth()/2, 
                startPos.y*PPM - GameScreen.player.getBuffSprite().sprite.getHeight()/2);
        */
        playerSpriteScale = playerSprite.sprite.getScaleX();
        
        playerSpectralSprite = GameScreen.player.getBeginSpectralSprite();
        playerSpectralSprite.sprite.setPosition(
                startPos.x*PPM - playerSpectralSprite.sprite.getWidth()/2,
                startPos.y*PPM - playerSpectralSprite.sprite.getHeight()*0.9f);
        
        deathSprite = GameScreen.player.getDeathSprite();
        deathSprite.sprite.setPosition(
                startPos.x*PPM - deathSprite.sprite.getWidth()/2,
                startPos.y*PPM - deathSprite.sprite.getHeight()/2);
        
        
        //sound
        bgm1 = new SoundObject_Bgm(ResourceManager.BGM_SPECTRAL_1);
        
    }
    
    /*
    @Override
    public void init(){
        super.init();
        
        //createEnvConstraints();
        createSections();
        
        
        
        System.out.println("@EnvSpectral init");
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(bg != null)
            sb.draw(bg, 0,0,width,height);
        
        
        if(sm.getState() == StateManager.State.PLAYING){
            
            if (fg != null) {
                sb.draw(fg, fgx, fgy, fgw, fgh);
            }
            
            for(EnvSection section: envSections){
                section.render(sb);
            }
            
            
            for(EntitySprite e: sprites){
                e.step();
                e.sprite.draw(sb);
            }
            
            Collections.sort(entities, new Entity.EntityComp());
            
            for (Entity e : entities) {
                e.render(sb);
            }

        }
        
        if(sm.getState() == State.END){
            //deathSprite.render(sb);
        }
        
    }
    
    @Override
    public void render(SpriteBatch sb, int layer){
        switch (layer){
            case 0:
                //todo: spectral layers
                break;
             
            default:
                this.render(sb);
                break;
        }
    }
    
    
    @Override
    public void updateCamera(OrthoCamera cam){
        
        if(sm.getState() == State.BEGIN){
            cam.setPosition(startPos.x*PPM, startPos.y*PPM);
            
        }else if(sm.getState() == State.PLAYING 
                || sm.getState() == State.FALLING){
            
            cam.setPosition(GameScreen.player.getBody().getPosition().x * PPM, GameScreen.player.getBody().getPosition().y * PPM);
            
        }
    }
    
    @Override
    public void updateB2DCamera(OrthoCamera cam){
        if(sm.getState() == State.BEGIN){
            cam.setPosition(startPos.x*PPM, startPos.y*PPM);
            
        }else if(sm.getState() == State.PLAYING 
                || sm.getState() == State.FALLING){
            
            cam.setPosition(GameScreen.player.getBody().getPosition().x, GameScreen.player.getBody().getPosition().y);
            
        }
    }
    
    private void createSections(){
        
        float sectionWidth = 1000f*RATIO, sectionHeight = 500f*RATIO;
        
        
        spSections.add(new SpectralSection_Main(
                new Vector2(0 * RATIO, 0 * RATIO),
                sectionWidth, sectionHeight,
                this));
        
        spSections.add(new SpectralSection_Connect(
                spSections.peek().getConnectionPos().cpy(), 
                sectionWidth, sectionHeight, 
                this));
        
        
        
        
        //for getting the end pos for SpSec_End
        //also fill envSections with spSecions
        
        for(SpectralSection_Main s : spSections){
            envSections.add(s);
        }
        
        Vector2 endPos = spSections.peek().getConnectionPos().cpy();
        
        SpectralSection_End sp_end = new SpectralSection_End(
                endPos, 
                sectionWidth, sectionHeight, 
                this);
        
        envSections.add(sp_end);
        
        for(EnvSection sec: envSections){
            sec.init();
        }

    }
    
    
    /*
    @Override 
    public void begin(){
        
        //begin animation for DM drain
        this.getDarkMatter();
        
        //sm.setState(0);
        
        //if(!sm.isPaused())
            //this.init();
        
        super.begin();
        
        playerSpectralSprite.reset();
        playerSprite.reset();
        playerSprite.sprite.setScale(playerSpriteScale);
        deathSprite.reset();
        
        //GameScreen.overlay.enable = false;
        
        
        //sound
        bgm1.play();
    }*/
    
    /*
    @Override
    public void play(){
        super.play();
        
        //envFC.start(fm);
                
        introTextSprite.sprite.setPosition(
                playerPos.x*PPM - introTextSprite.sprite.getWidth()/2, 
                playerPos.y*PPM - introTextSprite.sprite.getHeight()*2);
        
        spawnSprite(introTextSprite);
        
        //GameScreen.overlay.enable = true;
        
    }*/
    
    /*
    @Override
    public void end(int id, float time){
        //envFC.stop(fm);
        bgm1.stop();
        
        super.end(id, time);
    }*/
    
    
    
    /*
    public void getDarkMatter(){
        DM_COUNT = GameStats.inventory.subAll(new Item_DarkMatter());
        //DM_ANIM_COUNT = DM_COUNT;    
        
        System.out.println("EnvSpectral dm count " + DM_COUNT);
        
    }*/
    
    
    //private FrameCounter specAnimFC = new FrameCounter(1);
    
    /*
    public void playerSpectralAnim(SpriteBatch sb){
        
        if(!specAnimFC.running && !specAnimFC.complete){
            specAnimFC.start(fm);
        }else if(specAnimFC.complete){
            playerSpectralSprite.render(sb);
        }
        
    }*/
    
    //private int drainCount = 0;
    //private Vector2 dmPos;
    //private World dmWorld = new World(new Vector2(0.0f, -100f), true);
    //private Array<DmBody> dmBodies = new Array<DmBody>();
    //private int curFrameCount = 0, frameEndCount = 90;
    
    //handle dm drain animation
    /*
    public void dmDrainAnim(SpriteBatch sb){
        
        playerSprite.render(sb);
        dmFont.draw(sb,
                "" + DM_ANIM_COUNT + "",
                startPos.x * PPM + 5 * RATIO,
                startPos.y * PPM - 130 * RATIO);
        
        if(DM_ANIM_COUNT <= 0){
            
            playerSprite.sprite.scale(-0.0055f);
            curFrameCount++;
            
            if(dmWorld != null && curFrameCount >= frameEndCount){
                dmWorld.dispose();
                
                this.play();
                return;
            }
        }else if(drainCount >= 5){
            
            dmPos = startPos.cpy().scl(PPM).add(new Vector2(75*rng.nextFloat(), 5* rng.nextFloat()));
            dmBodies.add(new DmBody(dmPos.cpy()));
            dmBodies.peek().init(dmWorld);
            dmBodies.peek().getBody().applyForceToCenter(new Vector2(5, 2000), true);
      
            drainCount = 0;
            DM_ANIM_COUNT--;
        }
        
        dmWorld.step(UtilityVars.STEP, 6, 2);
        
        for(DmBody dm: dmBodies){
            dm.render(sb);
        }
        
        drainCount++;
        
        sb.draw(dmTexture, startPos.x*PPM - 55*RATIO, 
                    startPos.y*PPM - 190*RATIO,
                    50f, 
                    50f);
    }*/
    
    
    //private void createEnvConstraints(){
        //int dm_count = GameStats.inventory.subAll(new Item_DarkMatter());
        
        /*
        if(dm_count == 0){
            ScreenManager.setScreen(new GameOverScreen());
        }else{
            sectionCount = dm_count;
        }*/
        
    //}
    
    //boolean prevConnector = false;
    //float deadEndChance = 0.50f;
    
    /*
    public void createSections(){
        
        for(int i = 0; i < sectionCount; i++){
            if(envSections.size == 0){
                envSections.add(new SpectralSection(
                        new Vector2(0*RATIO , 0*RATIO), 
                        sectionWidth, 
                        sectionHeight, 
                        this,
                        sectionCoord));
                gridCoords.add(sectionCoord);
            }else{
                
                //if (prevConnector && rng.nextFloat() < deadEndChance)
                    //createDeadEnd();    
                    
                SpectralSection prevSection = (SpectralSection)envSections.peek();
                boolean [] sides = prevSection.getAvailableSides();
                //int index;
                
                boolean occupied;
                //int count = 0;
                Array<Integer> sideCheck = new Array<Integer>();
                
                do{
                    //count++;
                    if(!prevConnector){
                        do{
                            sideIndex = rng.nextInt(sides.length);
                        }while(sideIndex == 2);
                    }
                    occupied = false;
                    
                    
                    switch (sideIndex){
                        case 0:
                            sectionCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY()+1);
                            break;
                        case 1:
                            sectionCoord = new Coordinate(prevSection.getCoord().getX()+1, prevSection.getCoord().getY());
                            break;
                        case 2:
                            sectionCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY()-1);
                            break;
                        case 3:
                            sectionCoord = new Coordinate(prevSection.getCoord().getX()-1, prevSection.getCoord().getY());
                            break;
                        default:
                            sectionCoord = new Coordinate(prevSection.getCoord().getX()-1, prevSection.getCoord().getY());
                            break;
                    }
                    
                    
                    for(Coordinate coord: gridCoords){
                        if (coord.compareTo(sectionCoord)){
                            occupied = true;
                            if(!sideCheck.contains(sideIndex, true))
                                sideCheck.add(sideIndex);
                        }
                    }
                    
                    if(sideCheck.size == 4){
                        prevSection = (SpectralSection)envSections.get(envSections.size-2);
                        sides = prevSection.getAvailableSides();
                        prevConnector = false;
                        sideCheck.clear();
                    }
                    
                }while(occupied);
                
                prevConnector = !prevConnector;
                    
                prevSection.setSide(sideIndex, false, EnvSection.WallType.CONNECTED);
                gridCoords.add(sectionCoord);
                
                switch(sideIndex){
                    case 0:
                        envSections.add(new SpectralSection(
                                prevSection.getPos().cpy().add(new Vector2(0, sectionHeight)), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                sectionCoord));
                        
                        envSections.peek().setSide(2, false, EnvSection.WallType.CONNECTED);
                          
                        break;
                    case 1:
                        envSections.add(new SpectralSection(
                                prevSection.getPos().cpy().add(new Vector2(sectionWidth, 0)), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                sectionCoord));
                        envSections.peek().setSide(3, false, EnvSection.WallType.CONNECTED);
                        
                        break;
                    case 2:
                        envSections.add(new SpectralSection(
                                prevSection.getPos().cpy().add(new Vector2(0, -(sectionHeight))), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                sectionCoord));
                        envSections.peek().setSide(0, false, EnvSection.WallType.CONNECTED);
                         
                        break;
                    case 3:
                        envSections.add(new SpectralSection(
                                prevSection.getPos().cpy().add(new Vector2(-(sectionWidth), 0)), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                sectionCoord));
                        envSections.peek().setSide(1, false, EnvSection.WallType.CONNECTED);
                        
                        break;
                    default:
                        break;
                }
                
            }
        }
        
        if(envSections.size > 0){
            spawnEntity(new TearSpectral(new Vector2(
                    envSections.peek().getPos().x + (envSections.peek().getWidth() / 2) * RATIO,
                    envSections.peek().getPos().y + (envSections.peek().getHeight() / 2) * RATIO),
                    linkid));
        }
        
        for(EnvSection section: deadEndSections){
            envSections.add(section);
        }
        
        deadEndSections.clear();
        
        System.out.println("@EnvSpectral sections " + envSections.size);
    }*/
    
    //Array<EnvSection> deadEndSections = new Array<EnvSection>();
    
    /*
    public void createDeadEnd(){
        
        int deadEndCount = (int)(sectionCount * 0.2f);
        System.out.println("@EnvSpectral deadEnds " + deadEndCount);
        boolean deadEndConnect = false;
        int index = 0;
        
        for(int i = 0; i < deadEndCount; i++){
            SpectralSection prevSection;
            if(i == 0){
                prevSection = (SpectralSection)envSections.peek();
            }else{
                prevSection = (SpectralSection)deadEndSections.peek();
            }
                
                
            boolean[] sides = prevSection.getAvailableSides();

            boolean occupied;
            int count = 0;

            do {
                count++;
                if (!deadEndConnect) {
                    index = rng.nextInt(sides.length);
                }
                occupied = false;

                System.out.println("@EnvSpectral index " + index + " " + prevConnector + " " + count);

                switch (index) {
                    case 0:
                        sectionCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY() + 1);
                        break;
                    case 1:
                        sectionCoord = new Coordinate(prevSection.getCoord().getX() + 1, prevSection.getCoord().getY());
                        break;
                    case 2:
                        sectionCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY() - 1);
                        break;
                    case 3:
                        sectionCoord = new Coordinate(prevSection.getCoord().getX() - 1, prevSection.getCoord().getY());
                        break;
                    default:
                        sectionCoord = new Coordinate(prevSection.getCoord().getX() - 1, prevSection.getCoord().getY());
                        break;
                }

                for (Coordinate coord : gridCoords) {
                    if (coord.compareTo(sectionCoord)) {
                        occupied = true;
                    }
                }

                if (count >= 4) {
                    return;
                }

            } while (occupied);

            deadEndConnect = !deadEndConnect;

            prevSection.setSide(index, false, EnvSection.WallType.CONNECTED);
            gridCoords.add(sectionCoord);

            switch (index) {
                case 0:
                    deadEndSections.add(new SpectralSection(
                            prevSection.getPos().cpy().add(new Vector2(0, sectionHeight)),
                            sectionWidth,
                            sectionHeight,
                            this,
                            sectionCoord));

                    deadEndSections.peek().setSide(2, false, EnvSection.WallType.CONNECTED);

                    break;
                case 1:
                    deadEndSections.add(new SpectralSection(
                            prevSection.getPos().cpy().add(new Vector2(sectionWidth, 0)),
                            sectionWidth,
                            sectionHeight,
                            this,
                            sectionCoord));
                    deadEndSections.peek().setSide(3, false, EnvSection.WallType.CONNECTED);

                    break;
                case 2:
                    deadEndSections.add(new SpectralSection(
                            prevSection.getPos().cpy().add(new Vector2(0, -(sectionHeight))),
                            sectionWidth,
                            sectionHeight,
                            this,
                            sectionCoord));
                    deadEndSections.peek().setSide(0, false, EnvSection.WallType.CONNECTED);

                    break;
                case 3:
                    deadEndSections.add(new SpectralSection(
                            prevSection.getPos().cpy().add(new Vector2(-(sectionWidth), 0)),
                            sectionWidth,
                            sectionHeight,
                            this,
                            sectionCoord));
                    deadEndSections.peek().setSide(1, false, EnvSection.WallType.CONNECTED);

                    break;
                default:
                    break;
            }
        }
        
        
        
    }*/
    
    
}
