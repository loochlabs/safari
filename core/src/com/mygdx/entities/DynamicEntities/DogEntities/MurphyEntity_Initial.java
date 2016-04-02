/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.DogEntities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.TearPortal;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class MurphyEntity_Initial extends MurphyEntity{
    
    //Condition whether this stella has been activated yet
    //true at the beginning
    private boolean initialInactive = true;
    private final ImageSprite initTextSprite;
    private FrameCounter initFC = new FrameCounter(3.5f);
    
    private final FixtureDef afd = new FixtureDef();
    private final CircleShape acshape = new CircleShape();
    private Object action_data, tempUserData;
    
    private SoundObject_Sfx initSound;
    
    public MurphyEntity_Initial(Vector2 pos) {
        super(pos);
        
        acshape.setRadius(width*6f / PPM);
        afd.shape = acshape;
        action_data = "action_" + id;
        afd.filter.categoryBits = BIT_EN;
        afd.filter.maskBits = BIT_PLAYER ;
        afd.isSensor = true;
        
        isprite = idleSprite;
        
        initTextSprite = new ImageSprite("murphy-init", false, true, false, false);
        initTextSprite.sprite.setScale(0.6f*RATIO);
        
        initSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP);
    }
    
    @Override
    public void init(World world) {
        super.init(world);

        tempUserData = userdata;
        userdata = action_data;
        body.createFixture(afd).setUserData(action_data);

        for (Entity e : EnvironmentManager.currentEnv.getEntities()) {
            if (e.getUserData().toString().contains("bosst_")) {
                TearPortal t = (TearPortal) e;
                t.disable();
            }
        }
    }
    
    
    @Override
    public void actionEvent(){
        
        //Add this entity to player's available actionEvents
        GameScreen.player.outRangeForAction(this);
        initialInactive = false;
        userdata = tempUserData;
        initFC.start(fm);
        isprite = initTextSprite;
        
        for(Entity e : EnvironmentManager.currentEnv.getEntities()){
            if(e.getUserData().toString().contains("bosst_")){
                TearPortal t = (TearPortal)e;
                t.enable();
            }
        }
        
        initSound.play(false);
    }
    
    @Override
    public void alert(String [] str){
        try {
            if (!str[1].contains("action_")) {
                return;
            }

            super.alert(str);
            if (str[0].equals("begin")) {
                if (initialInactive) {
                    GameScreen.player.inRangeForAction(this);
                }
            }
            if (str.equals("end")) {
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void dogUpdate(){
        if(!initialInactive && !initFC.running && initTextSprite.isComplete()){
            //initial text on actionEvent
            super.dogUpdate();
        }
    }
}
