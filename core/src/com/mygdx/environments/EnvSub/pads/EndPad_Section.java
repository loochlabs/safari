/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.pads;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.managers.GameStats;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class EndPad_Section extends StaticEntity{

    protected EndPiece piece;
    protected Texture emptyTexture, fillTexture;
    private boolean complete;
    
    //sound 
    private final SoundObject_Sfx fillSound;
    
    public EndPiece getPiece() { return piece; }
    public boolean isComplete() { return complete; }
    
    public void setPos(Vector2 pos){
        this.pos = pos;
        bd.position.set(pos.x/PPM, pos.y/PPM);
    }
    
    public void setSize(float width, float height){
        this.width = width;
        this.height = height;
        shape.setAsBox(width/PPM, height/PPM);
        this.iw = width*2;
        this.ih = height*2;
    }
    
    public EndPad_Section(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "action_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER ;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        //sound
        fillSound = new SoundObject_Sfx(ResourceManager.SFX_ENDPIECE_FILL);
    }
    
    @Override
    public void alert(String []str){
        try {
            if (str[0].equals("begin") && str[1].contains("action_")) {
                if (GameStats.inventory.hasItem(piece)) {
                    GameScreen.player.inRangeForAction(this);
                }
            }
            if (str[0].equals("end") && str[1].contains("action_")) {
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void actionEvent(){
        //use item
        GameStats.inventory.subItem(piece, 1);
        GameScreen.player.outRangeForAction(this);
        
        fillSection();
        complete = true;
        
        //sound
        fillSound.play(false);
    }
    
    //play animation for section fill
    private void fillSection(){
        texture = fillTexture;
    }
    
    public EndPad_Section copy(){
        return new EndPad_Section(pos, width, height);
    }
}
