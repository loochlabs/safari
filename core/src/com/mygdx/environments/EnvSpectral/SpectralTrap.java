/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSpectral;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.Item_DarkMatter;
import com.mygdx.managers.GameStats;
import com.mygdx.screen.GameOver.GameOverScreen;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import com.mygdx.utilities.FrameCounter;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class SpectralTrap extends StaticEntity{

    private boolean trapActive = false;
    private final int TRAP_COST = 1;
    private final Pickup COST_ITEM  = new Item_DarkMatter();
    private final FrameCounter trapFC;
    private final float TRAP_TIME = 5f;
    
    public SpectralTrap(Vector2 pos, float w) {
        super(pos, w, 25f);
        
        userdata = "action_" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.StaticBody;
        cshape.setRadius(width/PPM);
        fd.shape = cshape;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        trapFC = new FrameCounter(TRAP_TIME);
        
    }
    
    @Override
    public void update(){
        super.update();
        
        fm.update();
        
        
        if(trapActive){
            applyTrap();
        }
        
        if(trapFC.complete){
            gameOver();
        }
    }
    
    @Override
    public void alert(String []str){
        /*
        if (str.equals("active")) {
            //create joint on player
            //enable action event
            activateTrap();
            GameScreen.player.inRangeForAction(this);
            
        }
        if(str.equals("inactive")){
            GameScreen.player.outRangeForAction(this);
        }*/
    }
    
    @Override
    public void actionEvent(){
        //sub TRAP_COST from inventory
        //break joint
        
        if(GameStats.inventory.hasItemAmmount(COST_ITEM, TRAP_COST)){
            GameStats.inventory.subItem(COST_ITEM, TRAP_COST);
            deactivateTrap();
        }
    }
    
    private void activateTrap(){
        trapActive = true;
        
        trapFC.start(fm);
    }
    
    private void deactivateTrap(){
        trapActive = false;
        
        trapFC.stop(fm);
    }
    
    private void applyTrap(){
        //knock player towards center of trap
        Vector2 dv = body.getPosition().cpy().sub(GameScreen.player.getBody().getPosition()).nor();
        dv.scl(500f);
        GameScreen.player.getBody().applyForceToCenter(dv, true);
    }

    private void gameOver() {
        ScreenManager.setScreen(new GameOverScreen());
    }
    
}
