/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.pads;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.Coordinate;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EndPad extends StaticEntity{

    protected Environment env;
    protected Array<EndPad_Section> sections = new Array<EndPad_Section>();
    protected Array<EndPiece> pieces_total = new Array<EndPiece>();//todo:remove
    protected int PIECES_TOTAL, PIECES_HELD;
    protected final int SCOUNT;
    //protected final float endTime = 1.5f;
    protected int idwarp;
   
    //protected Texture completeTexture;
    protected ImageSprite completeSprite;
    private boolean complete = false;
    
    //sound 
    private SoundObject_Sfx completeSound, warpSound;
    
    public Array<EndPiece> getPieces() { return pieces_total; }
    public int getPiecesTotal() { return PIECES_TOTAL; }
    public int getPiecesHeld() { return PIECES_HELD; }
    
    public EndPad(Vector2 pos, int scount, int idwarp) {
        super(pos, 100f*RATIO,100f*RATIO);
        
        SCOUNT = scount;
        this.idwarp = idwarp;
        
        //completeTexture = MainGame.am.get(ResourceManager.ENDPAD_COMPLETE);
        completeSprite = new ImageSprite("end-void-trans", false, true, false, false, 0,0,1.0f, false, true);
        //completeSprite = new EntitySprite("end-void-trans", true);
        completeSprite.sprite.setBounds(pos.x,pos.y, width*2, height*2);
        //completeSprite.setComplete(true);
        
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "action_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER ;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        sections = EndPadManager.createSections(SCOUNT);
        for(EndPad_Section s : sections){
            pieces_total.add(s.getPiece());
        }
        
        //sound
        completeSound = new SoundObject_Sfx(ResourceManager.SFX_COMPLETE_ENDSECTIONS);
        warpSound = new SoundObject_Sfx(ResourceManager.SFX_WARP_IN);
        
    }
    
    private int pcount = 0;
    
    
    @Override
    public void update(){
        super.update();
        
        if(!complete){
            pcount = 0;
            for (EndPad_Section s : sections) {
                if (s.isComplete()) {
                    pcount++;
                }
            }

            if (pcount == PIECES_TOTAL) {
                complete();
            }
        }
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        //if(complete && completeSprite.isComplete()){
            //warpToNextEnv();
        //
        
    }
    
    public void createSections(Environment env){
        
        this.env = env;
        for(int i = 0; i < sections.size; i++){
            env.spawnEntity(sections.get(i));
            sections.get(i).setPos(createPos());
            sections.get(i).setSize(width, height);
        }
        PIECES_TOTAL = sections.size;
    }
    
    public void complete(){
        complete = true;
        //texture = completeTexture;
        isprite = completeSprite;
        completeSprite.reset();
        
        for(EndPad_Section s : sections){
            env.removeEntity(s);
        }
        
        //play complete sound
        completeSound.play(false);
        
    }
    
    //public void warpToNextEnv(){}
    
    @Override
    public void alert(String str){
        if (str.equals("active")) {
            if (complete) {
                GameScreen.player.inRangeForAction(this);
            }
        }
        if(str.equals("inactive")){
            GameScreen.player.outRangeForAction(this);
        }
    }
    
    @Override
    public void actionEvent(){
        //warp to next level
        GameScreen.player.clearActionEvents();
        GameScreen.player.warp(body.getPosition());
        EnvironmentManager.currentEnv.end(idwarp, 1.5f);
        
        completeSprite.setPause(false);
        completeSprite.reset();
        
        warpSound.play(false);
    }

    Array<Coordinate> gridCoords = new Array<Coordinate>();
    Coordinate startCoord = new Coordinate(0,0);
    int currentIndex = 0;
    
    private Vector2 createPos() {
        if(gridCoords.size == 0)
            gridCoords.add(startCoord);
        
        Coordinate c = gridCoords.random();
        Coordinate nc = c;
        boolean occupied = false;
        
        do{
            currentIndex++;
            occupied = false;
            
            switch(currentIndex){
                case 0:
                    nc = new Coordinate(c.getX(), c.getY() + 1);
                    break;
                case 1:
                    nc = new Coordinate(c.getX()+1, c.getY());
                    break;
                case 2:
                    nc = new Coordinate(c.getX(), c.getY() - 1);
                    break;
                case 3:
                    nc = new Coordinate(c.getX() - 1, c.getY());
                    break;
                default:
                    break;
            }
            
            for(Coordinate gc : gridCoords){
                if(gc.compareTo(nc))
                    occupied = true;
            }
            
            if(occupied && currentIndex > 3){
                c = gridCoords.random();
                currentIndex = 0;
            }
            
        }while(occupied);
        
        Vector2 v = pos.cpy();
        v.add(new Vector2(width*2 * nc.getX(), height*2 * nc.getY()));
        gridCoords.add(nc);
        
        return v;
    }
    
}
