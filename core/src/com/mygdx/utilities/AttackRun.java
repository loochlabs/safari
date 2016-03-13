/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.mygdx.utilities.UtilityVars.AttackState;

/**
 *
 * @author looch
 */
public class AttackRun extends UtilityRun{

    private long prepTime, attTime, recovTime;
    private UtilityVars.AttackState state = AttackState.NONE;
    
    public UtilityVars.AttackState getState() { return state; }
    
    public AttackRun(long time) {
        super(time);
    }
    
    public AttackRun(long prep, long att, long recov){
        super(prep);
        this.prepTime = prep;
        this.attTime = att;
        this.recovTime = recov;
    }
    
    @Override
    public void run() {
        try {
            running = true;
            state = AttackState.PREPPING;
            Thread.sleep(prepTime);
            state = AttackState.ATTACKING;
            Thread.sleep(attTime);
            state = AttackState.RECOVERING;
            Thread.sleep(recovTime);
            
            state = AttackState.NONE;
            
            running = false;
            complete = true;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
