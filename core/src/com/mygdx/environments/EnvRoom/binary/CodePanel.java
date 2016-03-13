/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom.binary;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.environments.EnvironmentManager;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class CodePanel extends StaticEntity{
    
    private boolean on = false;
    private final int CODE_VALUE;
    
    private final PanelNumber panNumber;
    private final PanelSwitch panSwitch;
    
    public int getValue() { return CODE_VALUE; }
    public boolean isOn() { return on; }
    
    public CodePanel(Vector2 pos, float width, float height, int value){
        super(pos,width,height);
        
        bd.position.set(pos.x/PPM,pos.y/PPM);
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        userdata = "nwall_" + id;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        this.CODE_VALUE = value;
        
        float pwidth = width*0.75f;
        
        panNumber = new PanelNumber(new Vector2(pos.x, pos.y + height - pwidth), pwidth, pwidth, this);
        panSwitch = new PanelSwitch(new Vector2(pos.x, pos.y - height + pwidth), pwidth, pwidth, this);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        EnvironmentManager.currentEnv.spawnEntity(panNumber);
        EnvironmentManager.currentEnv.spawnEntity(panSwitch);
    }
    
    public void switchState(){
        on = !on;
        
        panNumber.setOn(on);
    }
    
}
