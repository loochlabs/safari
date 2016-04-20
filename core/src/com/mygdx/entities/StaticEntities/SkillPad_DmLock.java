/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.combat.skills.Skill;
import com.mygdx.entities.esprites.DMCostSprite;
import com.mygdx.entities.pickups.Item_DarkMatter;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.Tear_Room_DMLock;
import com.mygdx.managers.GameStats;
import com.mygdx.screen.GameScreen;

/**
 *
 * @author saynt
 */
public class SkillPad_DmLock extends SkillPad{
    
    private DMCostSprite dmcostSprite;
    private int dmcost;
    private boolean locked = true;
    private final Pickup dm = new Item_DarkMatter(new Vector2(0,0));
    
    public SkillPad_DmLock(Vector2 pos, int dmcost) {
        super(pos);
        
        this.dmcost = dmcost;
        dmcostSprite = new DMCostSprite(pos.x, pos.y, this.width, this.height, dmcost);
    }
    
    public SkillPad_DmLock(Vector2 pos, Skill skill, int dmcost){
        super(pos, skill);
        
        this.dmcost = dmcost;
        dmcostSprite = new DMCostSprite(pos.x, pos.y, this.width, this.height, dmcost);
    }
    
    @Override
    public void render(SpriteBatch sb){
        if(!locked){
            super.render(sb);
        }else{
            //render dmlock, cost
            dmcostSprite.render(sb, false);
        }
    }
    
    
    @Override
    public void alert(String [] str){
        try {
            if (!str[1].contains("action_")) {
                return;
            }
            
            int index = -1;
            if (SKILL != null) {
                switch (SKILL.getType()) {
                    case LIGHT:
                        index = 0;
                        break;
                    case HEAVY:
                        index = 1;
                        break;
                    case SPECIAL:
                        index = 2;
                        break;
                    case PASSIVE:
                        index = 3;
                        break;
                    case DEFENSE:
                        index = 4;
                        break;
                    default:
                        break;
                }
            }
            
            if (str[0].equals("begin")) {
                if ((locked && GameStats.inventory.hasItemAmmount(dm, dmcost))
                        || !locked) {
                    GameScreen.player.inRangeForAction(this);

                    if (!locked && SKILL != null) {
                        GameScreen.overlay.enableSkillHud(index);
                    }
                }
            }
            if (str[0].equals("end")) {
                GameScreen.overlay.disableSkillHud();
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void actionEvent(){
        //use item
        if(locked){     
            GameStats.inventory.subItem(dm, dmcost);
            locked = false;
            
            int index = -1;
            if (SKILL != null) {
                switch (SKILL.getType()) {
                    case LIGHT:
                        index = 0;
                        break;
                    case HEAVY:
                        index = 1;
                        break;
                    case SPECIAL:
                        index = 2;
                        break;
                    case PASSIVE:
                        index = 3;
                        break;
                    case DEFENSE:
                        index = 4;
                        break;
                    default:
                        break;
                }
            }
            GameScreen.overlay.enableSkillHud(index);
        }else{
            super.actionEvent();
        }
    }
    
}
