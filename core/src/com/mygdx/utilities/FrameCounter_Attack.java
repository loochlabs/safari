/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.mygdx.game.MainGame;
import com.mygdx.utilities.UtilityVars.AttackState;

/**
 *
 * @author looch
 */
public class FrameCounter_Attack extends FrameCounter{

    private int prepTime, attTime, recovTime;
    public AttackState state;
    
    
    public void setTime(float prep, float att, float recov){
        prepTime = (int)(prep * Math.pow(MainGame.STEP,-1));
        attTime = prepTime + (int)(att * Math.pow(MainGame.STEP,-1));
        recovTime = attTime + (int)(recov * Math.pow(MainGame.STEP,-1));
        
        MAX_FRAME = recovTime;
    }
    
    public FrameCounter_Attack(float time) {
        super(time);
    }
    
    public FrameCounter_Attack(float prep, float att, float recov){
        super(prep+att+recov);
        
        setTime(prep, att, recov);
        
        state = AttackState.NONE;
    }
    
    @Override 
    public void step(){
        if(complete) return;
        
        CURRENT_FRAME++;
        running = true;
        
        if(CURRENT_FRAME <= prepTime && CURRENT_FRAME > 0){
            state = state != AttackState.RECOVERING ? AttackState.PREPPING : AttackState.RECOVERING;
        }else if(CURRENT_FRAME <= attTime) {
            state = state != AttackState.RECOVERING ? AttackState.ATTACKING : AttackState.RECOVERING;
        }else if(CURRENT_FRAME <= recovTime){
            state = AttackState.RECOVERING;
        }
        
        
        if(CURRENT_FRAME >= MAX_FRAME){
            complete();
            state = AttackState.NONE;
        }
    }
    
}
