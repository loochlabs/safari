/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.StaticEntities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.combat.skills.Skill;
import com.mygdx.combat.skills.Skill.SkillType;
import com.mygdx.combat.skills.Skill_GhostJab;
import com.mygdx.entities.ImageSprite;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameStats;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Random;

/**
 *
 * @author looch
 */
public class SkillPad extends StaticEntity{

    //private String name;
    protected Skill SKILL;
    protected final Random rng = new Random();
    
    private Texture skillTexture, blankTexture;
    private ImageSprite lightSprite, heavySprite, specialSprite, passiveSprite, emptySprite;
    
    
    //sound
    private SoundObject_Sfx pickupSound;
    
    //particles
    private ParticleEffect effect;
    private ParticleEffectPool effectPool;
    private PooledEffect peffect;
    
    public SkillPad(Vector2 pos) {
        super(pos, 75f, 75f);
        
        userdata = "action_" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.StaticBody;
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        lightSprite = new ImageSprite("light-rotSlow", true);
        lightSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        heavySprite = new ImageSprite("heavy-rotSlow", true);
        heavySprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        specialSprite = new ImageSprite("special-rotSlow", true);
        specialSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        passiveSprite = new ImageSprite("passive-rotSlow", true);
        passiveSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        emptySprite = new ImageSprite("skill-empty", false);
        emptySprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        
        
        blankTexture = MainGame.am.get(ResourceManager.SKILL_BLANK);
        
        pickupSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP_SKILL);
        
        //particle effect
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/white-buff.p"), Gdx.files.internal("effects"));
        effectPool = new ParticleEffectPool(effect, 0, 200);
        
        setRandomSkill();
        
    }
    
    public SkillPad(Vector2 pos, Skill s){
        super(pos, 75f*RATIO, 75f*RATIO);
        
        userdata = "action_" + id;
        bd.position.set(pos.x/PPM,pos.y/PPM);
        bd.type = BodyDef.BodyType.StaticBody;
        shape.setAsBox(width/PPM, height/PPM);
        fd.shape = shape;
        fd.filter.categoryBits = BIT_WALL;
        fd.filter.maskBits = BIT_PLAYER;
        fd.isSensor = true;
        
        this.flaggedForRenderSort = false;
        
        lightSprite = new ImageSprite("light-rotSlow", true);
        lightSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        heavySprite = new ImageSprite("heavy-rotSlow", true);
        heavySprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        specialSprite = new ImageSprite("special-rotSlow", true);
        specialSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        passiveSprite = new ImageSprite("passive-rotSlow", true);
        passiveSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        emptySprite = new ImageSprite("skill-empty", false);
        emptySprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        
        
        blankTexture = MainGame.am.get(ResourceManager.SKILL_BLANK);
        
        pickupSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP_SKILL);
        
        //particle effect
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/white-buff.p"), Gdx.files.internal("effects"));
        effectPool = new ParticleEffectPool(effect, 0, 200);
        
        setSkillByName(s);
    }
    
    @Override
    public void init(World world){
        super.init(world);
        
        confirmSkill();
        
        //effect
        peffect = effectPool.obtain();
        this.addEffect(peffect);
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        sb.draw(blankTexture, body.getPosition().x*PPM - width*0.8f, body.getPosition().y*PPM - height*0.8f, iw*0.8f, ih*0.8f);
        
        if(skillTexture != null)
            sb.draw(skillTexture, body.getPosition().x*PPM - width*0.8f, body.getPosition().y*PPM - height*0.8f, iw*0.8f, ih*0.8f);
        
        super.render(sb);
    }
    
    //Description - set and confirm this.SKILL
    public void setRandomSkill(){
        if(GameStats.skillPool.size != 0){
            SKILL = GameStats.skillPool.get(rng.nextInt(GameStats.skillPool.size));
            
        }else{
            SKILL = new Skill_GhostJab();
        }
        
    }
    
    private void setSkillByName(Skill s){
        
        SKILL = s;
        
        if(GameStats.skillPool.contains(SKILL, true)){
            GameStats.skillPool.removeValue(SKILL, true);
        }
        
        /*
        SKILL = GameStats.skillPool.peek();
        for(Skill s: GameStats.skillPool){
            if(s.getName().equals(name)){
                SKILL = s;
            }
        }
        if(SKILL == null){
            SKILL = new Skill_GhostJab();
        }*/
        
    }
    
    public void confirmSkill(){
        //set skill
        //this.name = SKILL.getName();
        //skillTexture = SKILL.getItemTexture() != null ? SKILL.getItemTexture() : SKILL.getSkillIcon();
        skillTexture = SKILL.getSkillIcon();
        GameStats.skillPool.removeValue(SKILL, false);
        
        isprite = SKILL.getType() == SkillType.LIGHT ? lightSprite :
                SKILL.getType() == SkillType.HEAVY ? heavySprite :
                SKILL.getType() == SkillType.SPECIAL ? specialSprite :
                passiveSprite;
        
    }
    
    //Description - Swap skills on player contact
    public void swapSkills(){
        if(SKILL != null){
            SkillType type = SKILL.getType();
            Skill ts = SKILL;
            SKILL = GameScreen.player.getCurrentSkill(type);
            GameScreen.player.setCurrentSkill(ts);
            GameScreen.overlay.addDescAlert(ts);
            
            renew();
            
            pickupSound.play(false);
        }
    }
    
    //Description - renew current SKILL texture after swap
    public void renew(){
        if(SKILL != null)
            skillTexture = SKILL.getSkillIcon();
        else{
            skillTexture = null;
            isprite = emptySprite;
        }
    }
    
    public void reset(){
        setRandomSkill();
        confirmSkill();
    }
    
    @Override
    public void alert(String [] str){
        try {
            if (!str[1].contains("action_")) {
                return;
            }

            super.alert(str);
            if (str[0].equals("begin")) {
                if (SKILL != null) {
                    GameScreen.player.inRangeForAction(this);
                }
            }
            if (str[0].equals("end")) {
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void actionEvent(){
        //swap skills
        swapSkills();
        GameScreen.player.outRangeForAction(this);
    }
    
}
