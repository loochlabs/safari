/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.tears.Tear_Room_DMLock;
import com.mygdx.environments.tears.Tear_Room_EndPiece;
import com.mygdx.environments.tears.Tear_Room_Glyph1;
import com.mygdx.environments.tears.Tear_Room_Simple;
import com.mygdx.environments.tears.Tear_Room_Statup;
import java.util.Random;

/**
 *
 * @author saynt
 */
public class RngRoomManager {
    
    
    //simple reward rooms(2)
    //1 lock reward rooms(3)
    
    private static Random rng = new Random();
    
    public static TearPortal generateRngReward_Simple(Environment env, Vector2 pos){
        
        switch (rng.nextInt(2)) {
         
            case 0:
                return generateSimpleTear(env, pos);
            
            case 1:
                return generateStatupTear(env, pos);
                
            default:
                return generateStatupTear(env, pos);
                
        }
    }
    
    //TODO: need to find cleaner work around EnvVoid glyph room create
    
    public static void generateRngReward_1Lock(EnvVoid env){
        
        switch (rng.nextInt(2)) {
         
            case 0:
                env.generateDMLockRoom(((int)rng.nextFloat()*5) + 10);
                break;
            case 1:
                env.generateGlyphRoom();
                break;
            default:
                env.generateDMLockRoom(5);
                break;
        }
    }
    
    
    
    
    public static TearPortal generateSimpleTear(Environment env, Vector2 pos){
        return new Tear_Room_Simple(pos,env.getId());  
    }
    
    public static TearPortal generateStatupTear(Environment env, Vector2 pos){
        return new Tear_Room_Statup(pos,env.getId());  
    }
    
    public static TearPortal generateDMLockTear(Environment env, Vector2 pos){
        return new Tear_Room_DMLock(pos,env.getId());  
    }
    
    public static TearPortal generateDMLockTear(Environment env, Vector2 pos, int cost){
        return new Tear_Room_DMLock(pos,env.getId(), cost);  
    }
    
    public static TearPortal generateEndPieceTear(Environment env, Vector2 pos){
        return new Tear_Room_EndPiece(pos,env.getId());  
    }
    
    public static TearPortal generateGlyphTear(Environment env, Vector2 pos){
        return new Tear_Room_Glyph1(pos,env.getId());  
    }
}
