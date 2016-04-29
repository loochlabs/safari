/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.dev;

import com.mygdx.environments.EnvNull.random.Tear_R;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.combat.skills.Skill_CrackOfThunder;
import com.mygdx.combat.skills.Skill_GhostJab;
import com.mygdx.combat.skills.Skill_HauntHaste;
import com.mygdx.combat.skills.Skill_LightningRod;
import com.mygdx.combat.skills.Skill_Prognosis;
import com.mygdx.demo.demo2.EnvVoid_D2_1;
import com.mygdx.entities.DynamicEntities.DogEntities.StellaEntity;
import com.mygdx.entities.StaticEntities.SkillPad;
import com.mygdx.entities.StaticEntities.SkillPad_Defense;
import com.mygdx.entities.StaticEntities.SkillPad_DmLock;
import com.mygdx.entities.StaticEntities.breakable.Cyst_Blue;
import com.mygdx.entities.esprites.DecomSprite;
import com.mygdx.environments.EnvSub.EndWarp_Dev;
import com.mygdx.environments.EnvVoid.pads.EndPad;
import com.mygdx.environments.EnvVoid.EnvVoid;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.Tear_Room_Simple;
import static com.mygdx.game.MainGame.RATIO;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class EnvVoid_Dev0 extends EnvVoid{
    
    protected EndPad pad;
    protected int cyst_count = 4;
    
    public EnvVoid_Dev0(){
        super(-999, 3000f*RATIO, 3000f*RATIO, 1, "B");
        
        startPos = new Vector2(width*0.5f/PPM, height*0.5f/PPM);
        this.setPlayerToStart();
        
        introDescription = "Dev Hell";
        cyst_count = 2;
    }
    
    public EnvVoid_Dev0(int id){
        super(id, 2000f*RATIO, 2000f*RATIO, 1, "B");
        
        startPos = new Vector2(width*0.5f/PPM, height*0.5f/PPM);
        this.setPlayerToStart();
        
        introDescription = "Dev Hell";
        cyst_count = 2;
    }
    
    @Override
    public void init(){
        super.init();
        
        spawnPad();
    }
    
    public void spawnPad(){
    }
    
    
    @Override
    public void generateMisc(){
        super.generateMisc();
        
        //***************************************
        //      SPAWN CYSTS
        //************************************

        for (int i = 0; i < cyst_count; i++) {

            spawnEntity( new Cyst_Blue( new Vector2(createSpawnLocation())));

        }
        //***************************************************
        
        //bg sprites
        spawnEntity(new DecomSprite(new Vector2(25f*RATIO, grid.getHeight()*0.9f)));
        
        spawnEntity(new SkillPad(new Vector2(900f, 1200f), new Skill_LightningRod()));
        spawnEntity(new SkillPad(new Vector2(1200f, 1200f), new Skill_CrackOfThunder()));
        spawnEntity(new SkillPad_Defense(new Vector2(600f, 1200f)));
        spawnEntity(new SkillPad(new Vector2(1500f, 1200f), new Skill_Prognosis()));
        
        
        //void trap
        //spawnEntity(new En_VoidTrap(new Vector2(800f*RATIO, 1100f*RATIO)));
        
        //dev soul
        spawnEntity(new SkillPad_DmLock(new Vector2(1000f*RATIO, 1400f*RATIO), new Skill_GhostJab(), 1));
        spawnEntity(new SkillPad_DmLock(new Vector2(750f*RATIO, 1400f*RATIO), new Skill_HauntHaste(), 1));
        
        
        
        //dev EnvSub
        
        spawnEntity(new EndWarp_Dev(new Vector2(2000f*RATIO, 2000f*RATIO),  this.id));
    }
    
    
    
    @Override
    public void generateEndPad(){
        
    }
    
    @Override
    public void generateDogs(){
        //stella & murphy
        
        
        StellaEntity stella = 
                new StellaEntity(
                        new Vector2(150f*RATIO, 150f*RATIO));
        
        spawnEntity(stella);
        
        /*
        MurphyEntity murph = 
                new MurphyEntity(
                        new Vector2(150f*RATIO, 150f*RATIO));
        
        spawnEntity(murph);
        */
    }
    
    
    @Override
    public void generateTears(){
        
        spawnEntity(new Tear_R(new Vector2(1300f*RATIO, 800f*RATIO), this.id, 0));
        spawnEntity(new Tear_R(new Vector2(1000f*RATIO,800f*RATIO), this.id, 1));
        spawnEntity(new Tear_R(new Vector2(800f*RATIO,800f*RATIO), this.id, 2));
        spawnEntity(new Tear_R(new Vector2(550f*RATIO,800f*RATIO), this.id, 3));
        spawnEntity(new Tear_R(new Vector2(300f*RATIO,800f*RATIO), this.id, 4));
        
        spawnEntity(new Tear_Room_Simple(new Vector2(1000f*RATIO, 650f*RATIO), this.id));
    }
    
    
    
    @Override
    public String toString(){
        return "DEMO2 EnvVoid 0";
    }
    
    private class EndPad_D2_0 extends EndPad {

        public EndPad_D2_0(Vector2 pos, int scount, int idwarp) {
            super(pos, scount, idwarp);
            
        }

        @Override
        public void complete(){
            super.complete();
            
            EnvironmentManager.add(new EnvVoid_D2_1(idwarp));
        }
    }
}
