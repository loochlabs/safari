/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.skills.Skill_GhostJab;
import com.mygdx.combat.skills.Skill_PowerPlease;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.tears.Tear_Room_Glyph1;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.Pickup_Life;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.environments.tears.Tear_A;
import com.mygdx.environments.tears.Tear_Room_Binary;
import com.mygdx.entities.esprites.ManSprite;
import com.mygdx.entities.pickups.Item_DarkMatter;
import com.mygdx.environments.EnvRoom.EnvRoom_Glyph1;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 * 
 * Description: for development
 */
public class EnvVoid_Dev extends EnvVoid{

    public EnvVoid_Dev(int id, float w, float h, int spawncount) {
        super(id, w, h, spawncount, "");
        
        
    }
    
    
    
    @Override
    public void generateTears(){
        super.generateTears();
        
        
        //*********************************************************
        
        //              TESTING
        
        //**************************************
        //room tear
        Array<Integer> usedCells = new Array<Integer>();
        int tempId; //grid id to spawn in
        do{
                tempId = rng.nextInt(9) + 1;
        }while (tempId == 5 || usedCells.contains(tempId, true));
        GridCell gcell = grid.getCellById(tempId);
        
       Tear_Room_Glyph1 t1 = (Tear_Room_Glyph1)spawnEntity(new Tear_Room_Glyph1(new Vector2(
                    2200*RATIO,
                    1500*RATIO),
                    this.id));
       
       map.getTears().add(t1);
       
       //assign key from Tear_Room_Glyph1 to random null
       EnvRoom_Glyph1 skillRoom1 = (EnvRoom_Glyph1)t1.getWarpEnv();//this needs to be a key pool (Array<Keys>) to pull from,, not a specific room
       Pickup key = skillRoom1.getKey();
        TearPortal keyTear;
        int scount = 0;
        do {
            keyTear = map.getTears().random();
            scount++;
        } while (!keyTear.getUserData().toString().contains("_null") || scount <= spawnCount*2);
        
        keyTear.addReward(key);
        
        //**************************************
        
        
        //****************
        //  SLUMS
        //***************
        /*
         map.getTears().add(
                (TearPortal)spawnEntity(new TearSlum(new Vector2(
                    2500*RATIO,
                    1500*RATIO),
                    this.id,
                    10)));
        */
         
         //***************
        
        //TESTING 
        map.getTears().add((TearPortal)spawnEntity(new Tear_Room_Binary(new Vector2(1700f,1500f), this.id)));
        
        //TESTING
        map.getTears().add((TearPortal)spawnEntity(new Tear_A(new Vector2(1400f,1500f), this.id, 1)));
        map.getTears().add((TearPortal)spawnEntity(new Tear_A(new Vector2(1200f,1500f), this.id, 2)));
        map.getTears().add((TearPortal)spawnEntity(new Tear_A(new Vector2(1000f,1500f), this.id, 4)));
    }
    
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        
        spawnEntity(new Pickup_Life(new Vector2( 2300*RATIO, 1200*RATIO)));
        spawnEntity(new Pickup_Life(new Vector2( 2200*RATIO, 1200*RATIO)));
        spawnEntity(new Pickup_Life(new Vector2( 2100*RATIO, 1200*RATIO)));
        spawnEntity(new Pickup_Life(new Vector2( 2050*RATIO, 1200*RATIO)));
        
        
        spawnEntity(new SkillPad(new Vector2(500,1000), new Skill_GhostJab()));
        spawnEntity(new SkillPad(new Vector2(800,1000), new Skill_PowerPlease()));
        //spawnEntity(new SkillPad(new Vector2(1100,1000), "N.R.G."));
        //spawnEntity(new SkillPad(new Vector2(1400,1000), "Haymaker"));
        //spawnEntity(new SkillPad(new Vector2(1700,1000), "Warp It"));
        
        
        int dmcount = 10;
        for(int i = 0; i < dmcount; i++){
            spawnEntity(new Item_DarkMatter(new Vector2((1600)*RATIO, (2000+i)*RATIO)));
        }
        
        spawnEntity(new Cyst_Blue(new Vector2(2000*RATIO, 2400*RATIO)));
        spawnEntity(new Cyst_Blue(new Vector2(1800*RATIO, 2600*RATIO)));
        spawnEntity(new Cyst_Blue(new Vector2(2000*RATIO, 2600*RATIO)));
        spawnEntity(new Cyst_Blue(new Vector2(1800*RATIO, 2400*RATIO)));
        
        spawnEntity(new ManSprite(new Vector2(0,0)));
        
    }
    
    
    
    @Override
    public String toString(){
        return "dev";
    }
}
