/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.pickups.Pickup_SoulPiece;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class Entity_VomitNpc extends StaticEntity{
    
    private ImageSprite idleSprite, actionSprite;
    
    private final FixtureDef sensFd = new FixtureDef();
    private final Object sensData;
    
    private Pickup_SoulPiece rewardItem;
    private final Vector2 spawnDirection = new Vector2(1,0);
    private final FrameCounter spawnDelayFC = new FrameCounter(1f);
    private boolean soulComplete = false;
    private int respawnCount = 0;
    private int rewardCount = 1;
    private final int soulCount_max = 3;
    
    private final SoundObject_Sfx vomitSound;
    
    public Entity_VomitNpc(Vector2 pos) {
        super(pos, 30f*RATIO, 30f*RATIO);
        
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.StaticBody;
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        
        sensData = "action_" + id;
        userdata = sensData;
        
        sensFd.isSensor = true;
        sensFd.filter.categoryBits = BIT_WALL;
        sensFd.filter.maskBits = BIT_PLAYER;
        CircleShape endShape = new CircleShape();
        endShape.setRadius(100f/PPM);
        sensFd.shape = endShape;
        
        
        idleSprite = new ImageSprite("vomit-npc-idle", true);
        idleSprite.sprite.setBounds(pos.x, pos.y, 100f*RATIO, 120f*RATIO);
        actionSprite = new ImageSprite("vomit-npc-action", false);
        actionSprite.sprite.setBounds(pos.x, pos.y, 100f*RATIO, 120f*RATIO);
        
        isprite = idleSprite;
        
        
        rewardItem = new Pickup_SoulPiece();
        
        //sound
        vomitSound = new SoundObject_Sfx(ResourceManager.SFX_INTRO_VOMIT);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        body.createFixture(sensFd).setUserData(sensData);
        
        //create initial parameters for rewardCount spawn
        //This seems like the cleanest place to do it
        respawn();
        
    }
    
    @Override
    public void update(){
        super.update();
        
        if(spawnDelayFC.complete && !soulComplete){
            spawnSoulPiece();
        }
    }
    
    @Override
    public void actionEvent(){
        
        this.active = false;
        
        //player warp anim
        GameScreen.player.clearActionEvents();
        
        actionSprite.reset();
        isprite = actionSprite;
        
        //delay to catch up with actionSprite animation
        spawnDelayFC.start(fm);
        
        System.out.println("@Entity_VomitNpc action");
    }
    
    @Override
    public void alert(String []str){
        try {
            if (str[0].equals("begin") && str[1].contains("action_")
                    && active) {
                GameScreen.player.inRangeForAction(this);
            }
            if (str[0].equals("end") && str[1].contains("action_")) {
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    private void spawnSoulPiece(){
        //dispnse soul pieces
         //spawn reward items
         
         for(int i = 0; i < rewardCount; i++){
            Pickup_SoulPiece item = rewardItem.cpy();
            Vector2 iv = new Vector2(
                    pos.x + 25*rng.nextFloat(),
                    pos.y );
            item.setPosition(iv);
            Pickup_SoulPiece p = (Pickup_SoulPiece)EnvironmentManager.currentEnv.spawnEntity(item);
            p.spawnForce(spawnDirection, 500f*RATIO);
         }
         
         soulComplete = true;
         
         //sound 
         vomitSound.play(false);
    }
    
    /*******************************************
    Reset sprite, action event
    Adjust reward count for this playthrough
    ********************************************/
    public void respawn(){
        active = true;
        
        idleSprite.reset();
        actionSprite.reset();
        
        isprite = idleSprite;
        
        respawnCount++;
        rewardCount = rng.nextInt(soulCount_max * respawnCount) + 1;
    }
    
}
