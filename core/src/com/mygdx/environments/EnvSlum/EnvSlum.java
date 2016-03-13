/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSlum;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.environments.EnvSection;
import com.mygdx.environments.Environment;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.utilities.Coordinate;
import com.mygdx.utilities.SoundObject_Bgm;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EnvSlum extends Environment{

    private final int sectionCount;
    private float sectionWidth = 750 * RATIO;
    private float sectionHeight = 750 * RATIO;
    Coordinate slumCoord = new Coordinate(0, 0);
    private final Array<Coordinate> gridCoords = new Array<Coordinate>();
    private final Array<EnvSection> secToAdd = new Array<EnvSection>();
    
    //SOUND
    private SoundObject_Bgm bgm1;
    
    public EnvSlum(int id, int linkid, int sectionCount) {
        super(id);
        
        this.linkid = linkid;
        
        //todo: not needed anymore, done in init
        startPos = new Vector2(MainGame.WIDTH*0.55f/PPM,MainGame.HEIGHT*0.65f/PPM);
        this.setPlayerToStart();
        
        this.sectionCount = sectionCount;
        
        bgm1 = new SoundObject_Bgm(ResourceManager.BGM_SLUM_1);
    }
    
    /*
    @Override
    public void init(){
        super.init();
        
        createSections();
        
        for(EnvSection sec: envSections){
            sec.init();
        }
        
        spawnEntity(new NullWarp(new Vector2(
                envSections.get(0).getPos().x + 50*RATIO, 
                envSections.get(0).getPos().y + 100*RATIO),
                linkid));
        
        EnvSection s = envSections.get(0);
        Vector2 p = new Vector2((s.getPos().x + s.getWidth()/2)/PPM, (s.getPos().y + s.getHeight()/2)/PPM);
        startPos = p;
        this.setPlayerToStart();
        
        System.out.println("@EnvSlum init");
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
            
            
            
            for(TextEntity text: dmgTexts){
                text.render(dmgFont, sb);

                if (text.flagForDelete) {
                    dmgTextToRemove.add(text);
                }
            }
            
            for(TextEntity text: dmgTextToRemove){
                dmgTexts.removeValue(text, false);
            }
            
            dmgTextToRemove.clear();

        }
        
        
    }
    
    @Override
    public void render(SpriteBatch sb, int layer){
        switch (layer){
            case 0:
                //todo: slum layers
                break;
             
            default:
                this.render(sb);
                break;
        }
    }
    
    @Override
    public void play(){
        super.play();
        
        bgm1.play();
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        
        super.end(id, time);
    }
    
    
    public void createSections(){
        
        for(int i = 0; i < sectionCount; i++){
            if(envSections.size == 0){
                envSections.add(new SlumSection(
                        new Vector2(0,0), 
                        sectionWidth, 
                        sectionHeight, 
                        this,
                        slumCoord));
                gridCoords.add(slumCoord);
            }else{
                
                if(i%2 == 0) createShop();
                
                SlumSection prevSection = (SlumSection)envSections.peek();
                int index;
                
                boolean occupied;
                int count = 0;
                
                do{
                    index = 0;//only north side
                    occupied = false;
                    count++;
                    
                    slumCoord = new Coordinate(prevSection.getCoord().getX(), prevSection.getCoord().getY()+1);
                    
                    
                    for(Coordinate coord: gridCoords){
                        if (coord.compareTo(slumCoord))
                            occupied = true;
                    }
                    
                    if(count == 4) return;
                }while(occupied);
                    
                prevSection.setSide(index, false, WallType.CONNECTED);
                gridCoords.add(slumCoord);
                
                switch(index){
                    case 0:
                        envSections.add(new SlumSection(
                                prevSection.getPos().cpy().add(new Vector2(0, sectionHeight)), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                slumCoord));
                        
                        envSections.peek().setSide(2, false, WallType.CONNECTED);
                        
                                
                        break;
                        
                    default:
                        break;
                }
                
            }
        }
        
        for(EnvSection s : secToAdd){
            envSections.add(s);
        }
        
        createWallSections();
        
    }
    
    //create new shop
    private void createShop(){
        
        //add shop to left/right of main path
        EnvSection section = envSections.peek();
        
        if(rng.nextFloat() < 0.5f){
            //add shop to left
            slumCoord = new Coordinate(section.getCoord().getX() -1, section.getCoord().getY());
            
            secToAdd.add(new SlumSec_Shop(
                                section.getPos().cpy().add(new Vector2(-sectionWidth, 0)), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                slumCoord));
            secToAdd.peek().setSide(1, false, WallType.CONNECTED);
            section.setSide(3, false, WallType.CONNECTED);
            
            gridCoords.add(slumCoord);
            
        }else{
            //add shop to right
            
            slumCoord = new Coordinate(section.getCoord().getX() +1, section.getCoord().getY());
            
            secToAdd.add(new SlumSec_Shop(
                                section.getPos().cpy().add(new Vector2(sectionWidth, 0)), 
                                sectionWidth,
                                sectionHeight,
                                this,
                                slumCoord));
            secToAdd.peek().setSide(3, false, WallType.CONNECTED);
            section.setSide(1, false, WallType.CONNECTED);
            
            gridCoords.add(slumCoord);
        }
        
    }
    
    private void createWallSections(){
        secToAdd.clear();
        EnvSection sec;
        Coordinate fc;
        boolean occupied = false;
        
        for(EnvSection s : envSections){
            WallType [] sides = s.getSideTypes();
            Coordinate c;
            for(int i = 0; i < sides.length; i++){
                if(sides[i] == WallType.WALL){
                    switch (i){
                        case 0:
                            c = new Coordinate(s.getCoord().getX(), s.getCoord().getY()+1);
                            secToAdd.add(new SlumSec_Wall(
                                    s.getPos().cpy().add(new Vector2(0,sectionHeight)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    c));
                            gridCoords.add(c);
                            
                            //*************************
                            //new
                            sec = secToAdd.peek();
                            
                            //fill right if open
                            fc = new Coordinate(c.getX()+1, c.getY());
                            occupied = false;
                            for(Coordinate gc : gridCoords){
                                if(gc.compareTo(fc))
                                    occupied = true;
                            }
                            
                            if(!occupied){
                                secToAdd.add(new SlumSec_Wall(
                                    sec.getPos().cpy().add(new Vector2(sectionWidth, 0)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    fc));
                                
                                gridCoords.add(fc);
                            }
                            
                            //fill in left if open
                            fc = new Coordinate(c.getX()-1, c.getY());
                            occupied = false;
                            for(Coordinate gc : gridCoords){
                                if(gc.compareTo(fc))
                                    occupied = true;
                            }
                            
                            if(!occupied){
                                secToAdd.add(new SlumSec_Wall(
                                    sec.getPos().cpy().add(new Vector2(-sectionWidth, 0)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    fc));
                                
                                gridCoords.add(fc);
                            }
                             //*************************
                            break;
                        case 1:
                            c = new Coordinate(s.getCoord().getX()+1, s.getCoord().getY());
                            secToAdd.add(new SlumSec_Wall(
                                    s.getPos().cpy().add(new Vector2(sectionWidth,0)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    c));
                            
                            gridCoords.add(c);
                            
                            break;
                        case 2:
                            c = new Coordinate(s.getCoord().getX(), s.getCoord().getY()-1);
                            secToAdd.add(new SlumSec_Wall(
                                    s.getPos().cpy().add(new Vector2(0,-sectionHeight)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    c));
                            gridCoords.add(c);
                            
                            //*************************
                            //new
                            sec = secToAdd.peek();
                            
                            //fill right if open
                            fc = new Coordinate(c.getX()+1, c.getY());
                            occupied = false;
                            for(Coordinate gc : gridCoords){
                                if(gc.compareTo(fc))
                                    occupied = true;
                            }
                            
                            if(!occupied){
                                secToAdd.add(new SlumSec_Wall(
                                    sec.getPos().cpy().add(new Vector2(sectionWidth, 0)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    fc));
                                
                                gridCoords.add(fc);
                            }
                            
                            //fill in left if open
                            fc = new Coordinate(c.getX()-1, c.getY());
                            occupied = false;
                            for(Coordinate gc : gridCoords){
                                if(gc.compareTo(fc))
                                    occupied = true;
                            }
                            
                            if(!occupied){
                                secToAdd.add(new SlumSec_Wall(
                                    sec.getPos().cpy().add(new Vector2(-sectionWidth, 0)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    fc));
                                
                                gridCoords.add(fc);
                            }
                             //*************************
                            
                            break;
                        case 3:
                            c = new Coordinate(s.getCoord().getX()-1, s.getCoord().getY());
                            secToAdd.add(new SlumSec_Wall(
                                    s.getPos().cpy().add(new Vector2(-sectionWidth,0)),
                                    sectionWidth,
                                    sectionHeight,
                                    this,
                                    c));
                            
                            gridCoords.add(c);
                            break;
                        default:
                            break;
                    }
                }
            }
        }
        
        for(EnvSection s: secToAdd){
            envSections.add(s);
        }
                
        
    }
    */
}
