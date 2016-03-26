/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvStart.charstart;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.environments.EnvStart.EnvStart_0;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public abstract class CharacterStart extends StaticEntity{
    
    //gfx
    protected ImageSprite startSprite;
    
    //b2d
    private final FixtureDef actionfd = new FixtureDef();
    
    //selection
    private final int SELECT_POSITION;
    
    public int getSelection() { return SELECT_POSITION; }
    
    public CharacterStart(Vector2 pos, int select) {
        super(pos, 35f*RATIO, 35f*RATIO);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        cshape.setRadius(width/PPM);
        fd.shape = cshape;
        userdata = "nwall_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        
        sensordata = "action_" + id;
        actionfd.isSensor = true;
        actionfd.filter.categoryBits = BIT_WALL;
        actionfd.filter.maskBits = BIT_PLAYER;
        CircleShape ashape = new CircleShape();
        ashape.setRadius(100f*RATIO/PPM);
        actionfd.shape = ashape;
        
        this.SELECT_POSITION = select;
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        body.createFixture(actionfd).setUserData(sensordata);
        
        isprite = startSprite;
    }
    
    @Override
    public void actionEvent(){
        
        
        if(GameScreen.player.getAttTargets().contains(this)){
            GameScreen.player.getAttTargets().remove(this);
        }
        
        GameScreen.player.clearActionEvents();
        
        /**************************
            CREATE NEW PLAYER
        ***************************/
        
        EnvStart_0 env = (EnvStart_0)EnvironmentManager.currentEnv;
        env.characterSelect(SELECT_POSITION);
    }
    
    @Override
    public void alert(String []str){
        try {
            if (str[0].equals("begin") && str[1].contains("action_")) {
               
                GameScreen.player.inRangeForAction(this);
                
            }
            if (str[0].equals("end") && str[1].contains("action_")) {
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public abstract PlayerEntity createPlayer();
}
