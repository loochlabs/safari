/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.mygdx.game.MainGame;

/**
 *
 * @author saynt
 */
public class FrameCounter_Combo extends FrameCounter{

    public int attTime, comboTime, recovTime;
    public UtilityVars.AttackState state;
    
    
    public void setTime(float attack, float combo, float recov){
        attTime = (int)(attack * Math.pow(MainGame.STEP,-1));
        comboTime = attTime + (int)(combo * Math.pow(MainGame.STEP,-1));
        recovTime = comboTime + (int)(recov * Math.pow(MainGame.STEP,-1));
        
        MAX_FRAME = recovTime;
    }
    
    
    
    public FrameCounter_Combo(float time) {
        super(time);
    }
    
    public FrameCounter_Combo(float attack, float combo, float recov){
        super(attack+combo+recov);
        
        setTime(attack, combo, recov);
        
        state = UtilityVars.AttackState.NONE;
    }
    
    @Override 
    public void step(){
        if(complete) return;
        
        CURRENT_FRAME++;
        running = true;
        
        if(CURRENT_FRAME <= attTime && CURRENT_FRAME > 0){
            state = state != UtilityVars.AttackState.RECOVERING ? UtilityVars.AttackState.ATTACKING : UtilityVars.AttackState.RECOVERING;
        }else if(CURRENT_FRAME <= comboTime) {
            state = state != UtilityVars.AttackState.RECOVERING ? UtilityVars.AttackState.COMBO : UtilityVars.AttackState.RECOVERING;
        }else if(CURRENT_FRAME <= recovTime){
            state = UtilityVars.AttackState.RECOVERING;
        }
        
        
        if(CURRENT_FRAME >= MAX_FRAME){
            complete();
            state = UtilityVars.AttackState.NONE;
        }
    }
    
}