/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSub.pads;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.EntitySprite;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 * 
 * Description:  Serves as the door to the next EnvVoid
 * 
 */
public abstract class EndWarp extends Entity{

    //has a collection of end pieces in order to complete/open
    
    
    //starting pool for EnvNull generation to pull from
    //protected Array<Pickup_EndPiece> piecesNeeded = new Array<Pickup_EndPiece>();
    
    //pieces that have been placed in pad
    //protected Array<Pickup_EndPiece> piecesHeld = new Array<Pickup_EndPiece>();
    
    protected boolean complete = false;
    protected int requiredPieces;
    
    //EnvSub end
    protected EnvSub endEnvSub;
    protected EnvVoid envVoid;
    
    //esprite mist
    private EntitySprite mistSprite;
    private float mistScale;
    
    public EnvSub getEnv() { return endEnvSub; }
    //public Array<Pickup_EndPiece> getPiecesNeeded() { return piecesNeeded; }
    public int getRequiredPieces() { return requiredPieces; }
    
    public EndWarp(Vector2 pos){
        super(pos, 175*RATIO, 175*RATIO);
        
        bd.type = BodyType.StaticBody;
        fd.isSensor = true;
        userdata = "end_pad_" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        //shape.setAsBox(width/PPM, height/PPM);
        cshape.setRadius(width/PPM);
        fd.shape = cshape;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        
        this.flaggedForRenderSort = false;
        
        mistScale = 2.0f * RATIO;
        mistSprite = new EntitySprite(new Vector2(pos.x - 1000, pos.y - 1000), 1000,1000,"endPad-mist",true, false, false, false, mistScale + 1f, false, false);
        //mistSprite = new ImageSprite(
                //mistSprite, 
                //pos.x - mistSprite.sprite.getWidth()/2, 
                //pos.y - mistSprite.sprite.getHeight()/2);
        
        
        //mistSprite.sprite.setScale(mistScale + 1f);
        
        createEnvSub();
    }
    
    @Override
    public void init(World world) {
        
        body = world.createBody(bd);
        body.createFixture(fd).setUserData(userdata);
        body.setUserData(userdata);
        
        EnvironmentManager.currentEnv.spawnEntity(mistSprite);
        
        envVoid = (EnvVoid) EnvironmentManager.currentEnv;
        
    }
    
    
    @Override
    public void update(){
        super.update();
        
        //openCheck();
        
        //mist adjustment
        
        //if(player within 1000px, start shift for mist)
        if(playerInRange()){
            updateMist();
        }else{
            mistSprite.setPosition(new Vector2(
                    pos.x - mistSprite.getWidth()/2, 
                    pos.y - mistSprite.getHeight()/2));
            mistSprite.getSprite().sprite.setScale(1.0f);
        }
    }
    
    private final float PLAYER_RANGE = 5.0f;
    
    public abstract void createEnvSub();
    
    
    public boolean playerInRange(){
        boolean inRange = false;
        for(Body b : envVoid.getPlayerBodies()){
            if(b.getPosition().dst(body.getPosition()) < PLAYER_RANGE){
                inRange = true;
            }
        }
        
        return inRange;
    }
    
    public void updateMist(){
        /*
        Vector2 pv = GameScreen.player.getBody().getPosition().cpy().sub(body.getPosition());
        pv.nor().scl(0.7f * GameScreen.player.getBody().getPosition().dst(body.getPosition()));
        mistSprite.sprite.setPosition(
                pos.x - pv.x * PPM - mistSprite.sprite.getWidth()/2, 
                pos.y - pv.y * PPM - mistSprite.sprite.getHeight()/2);*/
        
        float dist = envVoid.getPlayerBodies().peek().getPosition().dst(body.getPosition());
        for(Body b : envVoid.getPlayerBodies()){
            float d = b.getPosition().dst(body.getPosition());
            if(d < dist)
                dist = d;
        }
        
        mistSprite.getSprite().sprite.setScale(1 + mistScale * (1 - dist/PLAYER_RANGE));
    }
    
    @Override
    public void alert(String []str){
        //warp to EnvSub-end
        
        //calculate point of entry
        Vector2 p = GameScreen.player.getBody().getPosition().cpy().sub(this.getBody().getPosition()).nor();
        
        endEnvSub.setStartPos(p);
        EnvironmentManager.currentEnv.end(endEnvSub.getId(), 0);
        
        //warp player to EnvSub relative point of entry
    }
    
    //todo: old
    /*
    public void addPieces(){
        for(Pickup_EndPiece piece: piecesNeeded){
            if(GameStats.inventory.hasItem(piece)){
                System.out.println("@EndPad piece added");
                piecesHeld.add(piece);
                GameStats.inventory.subItem(piece, 1);
            }else{
                System.out.println("@EndPad no pieces to added");
            }
        }
    }*/
    
    
}
