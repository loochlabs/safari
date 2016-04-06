/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ItemManager;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_ATT;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class En_EndNullArm extends StaticEntity{

    private final ImageSprite spawnSprite, idleSprite, deathSprite;
    private final int expYield_MIN, expYield_MAX;
    private final Array<Pickup> itemRewardPool;
    private final int linkid;
    
    //end falling fd
    private final FixtureDef sensFd = new FixtureDef();
    private final Object sensData;
    private boolean endActive = false;
    
    //sound 
    private SoundObject_Sfx SFX_DEATH;//SFX_IDLE, 
    private SoundObject_Sfx warpSound;
    
    public En_EndNullArm(Vector2 pos, int linkid) {
        super(pos, 25f*RATIO, 25f*RATIO);
        
        userdata = "en_" + id;
        bd.position.set(pos.x/PPM, pos.y/PPM);
        cshape.setRadius(width/PPM);
        fd.filter.categoryBits = BIT_EN;
        fd.filter.maskBits = BIT_PLAYER | BIT_ATT | BIT_EN;
        fd.shape = cshape;
        
        sensData = "action_" + id;
        sensFd.isSensor = true;
        sensFd.filter.categoryBits = BIT_WALL;
        sensFd.filter.maskBits = BIT_PLAYER;
        CircleShape endShape = new CircleShape();
        endShape.setRadius(150f/PPM);
        sensFd.shape = endShape;
        
        MAX_HP = 20;
        CURRENT_HP = MAX_HP;
        
        this.linkid = linkid;
        
        spawnSprite = new ImageSprite("null_end_spawn", false);
        spawnSprite.sprite.setScale(RATIO);
        idleSprite = new ImageSprite("null_end_idle", true);
        idleSprite.sprite.setScale(RATIO);
        deathSprite = new ImageSprite("null_end_death", false);
        deathSprite.sprite.setScale(RATIO);
        
        isprite = spawnSprite;
        
        expYield_MIN = 3;
        expYield_MAX = 6;
        
        int itemCount = rng.nextInt(expYield_MIN) + expYield_MIN;
        itemRewardPool = ItemManager.generateItems(itemCount);
        
        for(Pickup item: EnvironmentManager.currentEnv.getRewardItems()){
            itemRewardPool.add(item);
        }
        
        
        //sound
        //SFX_IDLE = new SoundObject_Sfx(ResourceManager.SFX_NULL_END_IDLE);
        SFX_DEATH = new SoundObject_Sfx(ResourceManager.SFX_NULL_END_DEATH);
        warpSound = new SoundObject_Sfx(ResourceManager.SFX_WARP_IN);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        body.createFixture(sensFd).setUserData(sensData);
        
        //SFX_IDLE.play(true, body.getPosition());
    }
    
    
    @Override
    public void update(){
        super.update();
        
        if(spawnSprite.isComplete() && !isprite.equals(idleSprite) && !dead)
            isprite = idleSprite;
        
        if(deathSprite.isComplete() && !endActive){
            createEndSensor();
        }
        
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(isprite != null){
            isprite.sprite.setPosition(
                    //body.getPosition().x*PPM - esprite.sprite.getWidth()/2, 
                    //body.getPosition().y*PPM - esprite.sprite.getHeight()*0.13f);
                    pos.x - isprite.sprite.getWidth()/2,
                    pos.y - isprite.sprite.getHeight()*0.13f);
            
            isprite.render(sb);
        }
        
    }
    
    
    
    @Override
    public void death(){
        super.death();
        isprite = deathSprite;
        
        //spawn reward items
        for(Pickup item: itemRewardPool){
            Vector2 iv = new Vector2(
                    //body.getPosition().x*PPM + 25*rng.nextFloat()*rngNegSet.random(),
                    //body.getPosition().y*PPM + 25*rng.nextFloat()*rngNegSet.random());
                    pos.x + 25*rng.nextFloat()*rngNegSet.random(),
                    pos.y + 25*rng.nextFloat()*rngNegSet.random());
            item.setPosition(iv);
            Pickup p = (Pickup)EnvironmentManager.currentEnv.spawnEntity(item);
            p.spawnForce();
        }
        
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
        
        //sound
        //SFX_IDLE.stop();
        SFX_DEATH.play(false);
        
    }
    
    @Override 
    public void dispose(){
        EnvironmentManager.currentEnv.removeEntity(this);
        
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
    }
    
    private void createEndSensor(){
        endActive = true;
        userdata = sensData;
    }
    
    @Override
    public void actionEvent(){
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
        
        //player warp anim
        GameScreen.player.clearActionEvents();
        GameScreen.player.warp(body.getPosition());
        
        warpSound.play(false);
        
        EnvNull env = (EnvNull) EnvironmentManager.currentEnv;
        env.setComplete(true);
        env.end();
    }
    
    @Override
    public void alert(String []str){
        try {
            if (str[0].equals("begin") && str[1].contains("action_")) {
                if (dead) {
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
    
}
