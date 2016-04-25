/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

/**
 *
 * @author looch
 */
public class UtilityVars {
    
    public static enum AttackState { NONE, PREPPING, ATTACKING, RECOVERING, COMBO };
    public static enum EnvType { DELTA, PSI, OMEGA };
    
    public static final float STEP = 1 / 60f;
    public static int PPM = 100;
    
    public static final short BIT_WALL = 2;
    public static final short BIT_PLAYER = 4;
    public static final short BIT_EN = 8;
    public static final short BIT_ATT = 16;
    public static final short BIT_TEAR = 32;
    public static final short BIT_PICKUP = 64;
    public static final short BIT_MISC = 128;
    
    public static float radiansToDegrees(float rad){
        return rad * (float)(180/Math.PI);
    }
    
    public static float degreesToRadians(float deg){
        return deg * (float)(Math.PI/180);
    }
}
