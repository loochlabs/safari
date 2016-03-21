/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.StaticEntities.NullWall;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.pickups.Pickup_Key;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.Tear_Room_Glyph1.EnvRoom_Glyph1_TEST;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameStats;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;

/**
 *
 * @author looch
 * 
 * Description: serves as a lock the uses one (or more) corresponding keys
 */
public class Wall_Lock extends NullWall{

    protected FixtureDef sens = new FixtureDef();
    public boolean locked = true;
    protected Pickup_Key key;
    protected ImageSprite openSprite;
    protected Object wallData;
    
    protected SoundObject_Sfx openSound; 
    
    public boolean isLocked() { return locked; }
    public Pickup_Key getKey() { return key; }
    
    public Wall_Lock(Vector2 pos, float w, float h) {
        super(pos, w, h);
        
        wallData = "action_" + id;
        sens.filter.categoryBits = BIT_WALL;
        sens.filter.maskBits = BIT_PLAYER;
        CircleShape c = new CircleShape();
        c.setRadius(4.0f*RATIO);
        sens.shape = c;
        sens.isSensor = true;
        
        //sound
        openSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP);
        
    }
    
    
    @Override
    public void init(World world){
        super.init(world);
        body.createFixture(sens).setUserData(wallData);
        userdata = wallData;
    }
    
    
    
    @Override
    public void update(){
        super.update();
        
        if(!locked){
            isprite = openSprite;
            
            if(openSprite.isComplete()){
                destroy();
            }
        }
    
    }
    
    
    @Override
    public void alert(String str){
        if (str.equals("active")) {
            if (GameStats.inventory.hasItem(key)) {
                GameScreen.player.inRangeForAction(this);
            }
        }
        if(str.equals("inactive")){
            GameScreen.player.outRangeForAction(this);
        }
    }
    
    @Override
    public void actionEvent() {
        //use item
        this.openDoor();
        EnvRoom_Glyph1_TEST room = (EnvRoom_Glyph1_TEST) EnvironmentManager.currentEnv;
        room.unlockWall();

        GameStats.inventory.subItem(key, 1);

        GameScreen.player.outRangeForAction(this);

        openSound.play(false);
    }

    //called when key is used within range of lock
    public void openDoor(){
        
        if(locked){//temp code, needs animation
            locked = false;
            //EnvironmentManager.currentEnv.removeEntity(this);
            //GameStats.inventory.subItem(key, 1);
        }
        
    }
    
    public void destroy(){
        EnvironmentManager.currentEnv.removeEntity(this);
    }
    
}
