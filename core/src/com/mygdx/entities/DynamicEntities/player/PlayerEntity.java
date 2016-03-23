/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.DynamicEntities.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.mygdx.combat.NormAttackSensor;
import com.mygdx.combat.Buff;
import com.mygdx.combat.skills.Skill;
import com.mygdx.combat.skills.Skill.SkillType;
import com.mygdx.entities.DynamicEntities.SteerableEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameInputProcessor;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import com.mygdx.utilities.Direction;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.FrameCounter_Attack;
import com.mygdx.utilities.FrameCounter_Combo;
import com.mygdx.utilities.SoundObject_Sfx;
import com.mygdx.utilities.UtilityVars.AttackState;
import static com.mygdx.utilities.UtilityVars.BIT_EN;
import static com.mygdx.utilities.UtilityVars.BIT_PICKUP;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.ArrayList;

/**
 *
 * @author looch
 */
public class PlayerEntity extends SteerableEntity{
    
    //graphics
    
    protected ImageSprite frontSprite,backSprite, rightSprite, leftSprite;
    protected ImageSprite idleSprite, diveSprite, warpSprite, recovSprite;
    protected ImageSprite attackSprite, attackHeavySprite, playerBuffSprite;//player body animations
    protected ImageSprite beginSpectralSprite, deathSprite;
    
    protected float spriteScale = 1.0f;
    protected boolean warping = false;
    protected float endTime = 2.0f;
    
    
    //***********************************************
    //PLAYER STATS
    
    protected String playerName = "player";
    
    //base values for each stat (count == 1 will equal base value)
    protected final float BASE_LIFE = 30f;
    protected final float BASE_ENERGY = 60f;
    protected final float BASE_DAMAGE = 1.0f;
    protected final float BASE_SPEED = 30.0f * RATIO;
    protected final float BASE_SPECIAL = 1.0f;
    
    //stat points
    protected int LIFE_STAT_COUNT = 1;
    protected int ENERGY_STAT_COUNT = 1;
    protected int DAMAGE_STAT_COUNT = 1;
    protected int SPEED_STAT_COUNT = 1;
    protected int SPECIAL_STAT_COUNT = 1;
    
    //values for each stat type, (COUNT * VALUE = ingame number)
    protected final float LIFE_STAT_VALUE = 10f;
    protected final float ENERGY_STAT_VALUE = 15f;
    protected final float DAMAGE_STAT_VALUE = 1.0f;
    protected final float SPEED_STAT_VALUE = 3.5f;
    protected final float SPECIAL_STAT_VALUE = 0.2f;
    
    protected float CURRENT_LIFE;
    protected float CURRENT_ENERGY;
    protected float CURRENT_DAMAGE;
    protected float CURRENT_SPEED;
    protected float CURRENT_SPECIAL;
    
    //ingame stats
    protected float life; //current life, <= CURRENT_LIFE
    protected float energy; //current energy, <= CURRENT_ENERGY
    protected float ENERGY_REGEN = 0.01f;
    protected float soulCount = 0;
    protected final float SOUL_MAX = 100f;
    
    public String getPlayerName() { return playerName; }
    public float getLife() { return life; }
    public float getEnergy() { return energy; }
    public float getEnergyRegen() { return ENERGY_REGEN; }  
    public float getCurrentLife() { return CURRENT_LIFE; }
    public float getCurrentEnergy() { return CURRENT_ENERGY; }
    public float getCurrentDamage() { return CURRENT_DAMAGE; }
    public float getCurrentSpeed() { return CURRENT_SPEED; }
    public float getCurrentSpecial() { return CURRENT_SPECIAL; }
    public int getLifeStatCount() { return LIFE_STAT_COUNT; }
    public int getEnergyStatCount() { return ENERGY_STAT_COUNT; }
    public int getDamagerStatCount() { return DAMAGE_STAT_COUNT; }
    public int getSpeedStatCount() { return SPEED_STAT_COUNT; }
    public int getSpecialStatCount() { return SPECIAL_STAT_COUNT; }
    public float getBaseSpeed() { return BASE_SPEED; }
    public float getSoulCount() { return soulCount; }
    
    
    public void setCurrentDamage(float dmg) { this.CURRENT_DAMAGE = dmg; }
    public void setCurrentSpeed(float speed) { this.CURRENT_SPEED = speed; }
    public void setEnergyRegen(float reg) { this.ENERGY_REGEN = reg; }
    
   
    //***********************************************
    //damage to player
    private FrameCounter dmgFC = new FrameCounter(0.25f);
    //private boolean dead = false;
    
    //movement
    private boolean moveUp, moveDown,moveLeft,moveRight = false;
    private Vector2 currentDirection = new Vector2(0,0);
    private Vector2 prevLocation = new Vector2(0,0);
    private float currentAngle = 0;

    //dash
    private final FrameCounter_Attack dashFC = new FrameCounter_Attack(0,0.25f,0.75f);
    private final float DASH_COST;
    private float dashSpeed;
    private boolean canDash = true;
    private float DASHMOD = 1;
    private final Array<Sprite> dashSprites = new Array<Sprite>();
    private final Array<Float> dashAlphas = new Array<Float>();
    private boolean isDashSkill = false; //todo: move to Skill(), not here
    
    //player state
    private final StateManager sm;
    
    //Combat
    //private FrameCounter_Attack attackFC;
    private FrameCounter_Combo attackFC = new FrameCounter_Combo(0,0,0);
    
    protected final Skill[] skillSet = {null,null,null,null};
    private Skill currentSkill; //SKILL_LIGHT, SKILL_HEAVY, SKILL_SPEC, SKILL_PASSIVE;
    private Skill previousSkill;
    private float LIGHT_MOD, HEAVY_MOD, SPECIAL_MOD;
    private boolean isCombo = false;  //todo: remove canAttack?
    private boolean currentAttackFail = false;
    private final ArrayList<Entity> attTargets = new ArrayList<Entity>();
    private NormAttackSensor normAttSensor;
    
    private Array<ImageSprite> skillSprites = new Array<ImageSprite>();//collection of skillSprite, skillHeavySprite
    private Array<ImageSprite> skillsToRemove = new Array<ImageSprite>();
    private Array<ImageSprite> impactSprites = new Array<ImageSprite>();//todo: place in EntityEnemy, not here
    private Array<Float> impactAlphas = new Array<Float>();
    
    
    //protected EntitySprite skillSprite, skillHeavySprite;//AoE attack animations
    //Skills
    
    
    //buffs
    private final Array<Buff> buffs = new Array<Buff>();
    private final Array<Buff> buffsToAdd = new Array<Buff>();
    private final Array<Buff> buffsToRemove = new Array<Buff>();
    
    //items
    private BitmapFont font;
    private Texture actionTexture;
    
    //***************
    //SOUND
    private SoundObject_Sfx SFX_YELL1, SFX_YELL2, SFX_YELL3, SFX_DASH, SFX_DMG;
    private Array<SoundObject_Sfx> SFX_YELLS = new Array<SoundObject_Sfx>();
    private SoundObject_Sfx soulSound;
    
    //***************
    
    public boolean isDead() { return dead; }
    public ImageSprite getBeginSpectralSprite() { return beginSpectralSprite; }
    public ImageSprite getDeathSprite() { return deathSprite; }
    public ImageSprite getRecovSprite() { return recovSprite; }
    public ImageSprite getDiveSprite() { return diveSprite; }
    public ImageSprite getBuffSprite() { return playerBuffSprite; }
    public ArrayList<Entity> getAttTargets() { return attTargets; }
    public Skill[] getSkillSet() { return skillSet; }
    public Skill getPreviousSkill() { return previousSkill; }
    public StateManager getStateManager() { return sm; }
    public float getSpriteScale() { return spriteScale; }
    public float getLightMod() { return LIGHT_MOD; }
    public float getHeavyMod() { return HEAVY_MOD; }
    public float getSpecialMod() { return SPECIAL_MOD; }
    public Array<Buff> getBuffs() { return buffs; }
    public boolean isDashSkill() { return isDashSkill; }
    public NormAttackSensor getNormAttSensor() { return normAttSensor; }
    public float getCurrentAngle() { return currentAngle; } 
    public Vector2 getCurrentDirection() { return currentDirection; }
    
    public void setLightMod(float light) { this.LIGHT_MOD = light; }
    public void setHeavyMod(float heavy) { this.HEAVY_MOD = heavy; }
    public void setSpecialMod(float special) { this.SPECIAL_MOD = special; }
    public void setCurrentEnergy(float energy) { this.energy = energy; }
    public void setDashSkill(boolean dashSkill) { this.isDashSkill = dashSkill; }
    
    public PlayerEntity(Vector2 pos, float w, float h){
        super(pos,w,h);
        
        userdata = "player_" + id;
        fd.filter.categoryBits = BIT_PLAYER;
        fd.filter.maskBits = BIT_WALL | BIT_EN | BIT_PICKUP;
        fd.restitution = 0.2f;
        
        sm = new StateManager();
        
        //*************  STATS ********************
        DASH_COST = 20.0f;
        
        //DAMAGE = 5;
        LIGHT_MOD = 1.0f;
        HEAVY_MOD = 1.0f;
        SPECIAL_MOD = 1.0f;
        //ATTSPEED = 200;
        RANGE = 1.3f*RATIO;     //needed for NormalAttackSensor size, radius
        
        life = CURRENT_LIFE;
        
        //************************************
        
        
        beginSpectralSprite = new ImageSprite("poeSpectral", false);
        beginSpectralSprite.sprite.setScale(1.06f*RATIO);
        deathSprite = new ImageSprite("poe-death", false);
        
        
        //SOUND
        SFX_YELL1 = new SoundObject_Sfx(ResourceManager.POE_YELL_1);
        SFX_YELL2 = new SoundObject_Sfx(ResourceManager.POE_YELL_2);
        SFX_YELL3 = new SoundObject_Sfx(ResourceManager.POE_YELL_3);
        
        SFX_YELLS.add(SFX_YELL1);
        SFX_YELLS.add(SFX_YELL2);
        SFX_YELLS.add(SFX_YELL3);
        
        SFX_DASH = new SoundObject_Sfx(ResourceManager.SFX_DASH);
        SFX_DMG = new SoundObject_Sfx(ResourceManager.SFX_POE_DMG);
        
        soulSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP_SKILL);
        
        //font
        font = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        font.setScale(RATIO);
        
        //action key
        actionTexture = MainGame.am.get(ResourceManager.GUI_PAD_Y);
        
    }
    
    @Override
    public void init(World world) {
        super.init(world);
        
        //combat
        //todo: might not need initial init
        attackFC = new FrameCounter_Combo(0,0,0);
        
        //combat
        normAttSensor = new NormAttackSensor(this);
        body.createFixture(normAttSensor).setUserData(normAttSensor.getData());
        
        //skill init
        for(Skill skill: skillSet){
            if(skill != null && skill.getType() == SkillType.PASSIVE && !skill.isActive()){
                skill.effect();
            }
        }
        
        warping = false;
    }
    
    @Override
    public void render(SpriteBatch sb){
        
        //attack sprite
        for(ImageSprite e: skillsToRemove){
            skillSprites.removeValue(e, false);
        }
        
        for(ImageSprite e: skillSprites){
            e.render(sb);
            
            if(e.isComplete()){
                skillsToRemove.add(e);
            }
        }
        
        //dive sprite
        if(EnvironmentManager.currentEnv.getStateManager().getState() == State.FALLING){
            isprite = diveSprite;
        }
        
        
        /*
        if(isprite != null && !warping){
            
            //damage animation
            if(dmgFC.running){
                isprite = recovSprite;
                isprite.sprite.setAlpha(0.65f);
            }else{
                isprite.sprite.setAlpha(1.0f);
            }
            
            if (isprite.getXFlip()) {
                isprite.sprite.setPosition(
                        (pos.x + (isprite.sprite.getWidth() * 0.15f)),
                        (pos.y - (isprite.sprite.getHeight() / 2)));
            } else {
                isprite.sprite.setPosition(
                        (pos.x - (isprite.sprite.getWidth() / 2)),
                        (pos.y - (isprite.sprite.getHeight() / 2)));
            }
            
            isprite.render(sb);
            
            //*******************
            //dash effect
            if(dashFC.running){
                dashSprites.add(new Sprite(isprite.sprite));
                dashAlphas.add(1.0f);
            }else if(dashFC.complete && dashSprites.size > 0){
                boolean clearAlphas = true;
                for (Float alpha : dashAlphas) {
                    if (alpha > 0) {
                        clearAlphas = false;
                    }
                }
            
            
                if(clearAlphas){
                    dashSprites.clear();
                    dashAlphas.clear();
                }
            //*******************
            }
            
        }else if(isprite != null && warping){
            isprite.render(sb);
        
        }else
            super.render(sb);
        */
        
        if(isprite != null){
            isprite.render(sb);
        
        }else
            super.render(sb);
        
        
        renderSprites(sb);
        
        
        
        //particle effects
        renderEffects(sb);
        
        renderActionKey(sb);
    }
    
    @Override 
    public void renderEffects(SpriteBatch sb){
        
        for(PooledEffect e : effects){
            e.setPosition(
                    body.getPosition().x*PPM - e.getEmitters().peek().getXOffsetValue().getLowMax(), 
                    body.getPosition().y*PPM - height);
        }
        super.renderEffects(sb);
    }
    
    public void renderActionKey(SpriteBatch sb){
        if(actionEntities.size > 0){
            if (!GameInputProcessor.controller) {
                font.draw(sb,
                        Keys.toString(GameInputProcessor.KEY_ACTION_4),
                        pos.x - width,
                        pos.y + height * 2.5f + font.getCapHeight());
            } else {
                sb.draw(
                        actionTexture,
                        pos.x - width,
                        pos.y + height * 2.5f,
                        50f * RATIO, 50f * RATIO);
            }
        }
    }
    private void updateSprites() {
         if(attackFC.complete){//TODO: clean up to only execute once
            boolean clearAlphas = true;
            for (Float alpha : impactAlphas) {
                if (alpha > 0) {
                    clearAlphas = false;
                }
            }
            
            if(clearAlphas){
                impactSprites.clear();
                impactAlphas.clear();
            }
            
            if(isprite != null)
                isprite.sprite.setAlpha(1.0f);//TODO clean up
        }
         
         
         //skillSprites
         for(ImageSprite i : skillSprites){
            i.step();
         }
        
        //impact effect
        for(int i = 0; i < impactSprites.size; i++){
            impactSprites.get(i).sprite.setAlpha(impactAlphas.get(i));
            
            if(impactAlphas.get(i) >0.75f)
                impactSprites.get(i).sprite.setScale(impactSprites.get(i).sprite.getScaleX()* impactAlphas.get(i));
            
            impactSprites.get(i).step();
            
            //flip sprite
            
            
            impactAlphas.set(i, impactAlphas.get(i) - 0.05f < 0 ? 0 : impactAlphas.get(i) - 0.05f);
        }
        
        if(isprite != null && !warping){
            
            //damage animation
            if(dmgFC.running){
                isprite = recovSprite;
                isprite.sprite.setAlpha(0.65f);
            }else{
                isprite.sprite.setAlpha(1.0f);
            }
            
            if (isprite.getXFlip()) {
                isprite.sprite.setPosition(
                        (pos.x + (isprite.sprite.getWidth() * 0.15f)),
                        (pos.y - (isprite.sprite.getHeight() / 2)));
            } else {
                isprite.sprite.setPosition(
                        (pos.x - (isprite.sprite.getWidth() / 2)),
                        (pos.y - (isprite.sprite.getHeight() / 2)));
            }
            
            //isprite.render(sb);
            
            //*******************
            //dash effect
            if(dashFC.running){
                dashSprites.add(new Sprite(isprite.sprite));
                dashAlphas.add(1.0f);
            }else if(dashFC.complete && dashSprites.size > 0){
                boolean clearAlphas = true;
                for (Float alpha : dashAlphas) {
                    if (alpha > 0) {
                        clearAlphas = false;
                    }
                }
            
            
                if(clearAlphas){
                    dashSprites.clear();
                    dashAlphas.clear();
                }
            //*******************
            }
            
        }
        //dash effect
        for(int i = 0; i < dashSprites.size; i++){
            dashSprites.get(i).setAlpha(dashAlphas.get(i));
            dashAlphas.set(i, dashAlphas.get(i) - 0.15f < 0 ? 0 : dashAlphas.get(i) - 0.15f);
            
        }
    }
    
    private void renderSprites(SpriteBatch sb){
        
        
        for(ImageSprite i : impactSprites){
            if(i.getXFlip())  
                i.sprite.flip(true, false);
            
            i.sprite.draw(sb);
            
            //unflip sprite
            if(i.getXFlip())  
                i.sprite.flip(true, false);
        }
        
        for(Sprite i : dashSprites){
            i.draw(sb);
        }
    }
    

    @Override
    public void update() {
        super.update();
        
        //todo: have this check in env.update(), not here
        if(EnvironmentManager.currentEnv.getStateManager().getState() == State.PLAYING){
        
            
            moveUpdate();
            

            //reset dash
            dashUpdate();

            //combat
            updateSkill();
            
            //impact
            updateSprites();

            
            //energy regen
            updateEnergy();
            

            //buffs
            updateBuffs();
            
            
            //direction
            updateDirection();

            //death
            if (!dead && life <= 0) {
                death();
            }
        
        }
    }
    
    /*
    EDIT FOR DEMO (2/10/16):
    REMOVE SPECTRAL MODE > SWITCH TO GameOverScreen
    */
    
    @Override
    public void death(){
        
        if (!dead) {
            
            //Edit: 2/10/16
            //ScreenManager.setScreen(new GameOverScreen());
            ScreenManager.gameOverScreen(1);        //DemoGameOverScreen
            
            /*
            int specid = ++GameStats.idcount;
            EnvironmentManager.add(
                    new EnvSpectral(
                            specid,
                            EnvironmentManager.currentEnv.getId()));

            EnvironmentManager.currentEnv.end(specid, endTime);
            */
            
        }
        super.death();
    }
    
    public void killSwitch(){
        life = 0;
    }
    
    public void revive(){
        //alive = true;
        dead = false;
        life = CURRENT_LIFE*0.5f;
    }
    
    private Vector2 dv = new Vector2(0,0);
    
    private void moveUpdate() {
        //applies movement and adjusts sprites
        dv.x = 0;
        dv.y = 0;
        if (!attackFC.running && !dmgFC.running && !dashFC.running) {
            if (moveDown && -CURRENT_SPEED * DASHMOD < body.getLinearVelocity().y) {
                //body.applyForce(new Vector2(0, -CURRENT_SPEED * DASHMOD), body.getPosition(), true);
                dv.y -= 1;
            }

            if (moveUp && CURRENT_SPEED * DASHMOD > body.getLinearVelocity().y) {
                //body.applyForce(new Vector2(0, CURRENT_SPEED * DASHMOD), body.getPosition(), true);
                dv.y += 1;
            }

            if (moveRight && CURRENT_SPEED * DASHMOD > body.getLinearVelocity().x) {
                //body.applyForce(new Vector2(CURRENT_SPEED * DASHMOD, 0), body.getPosition(), true);
                dv.x += 1;
            }

            if (moveLeft && -CURRENT_SPEED * DASHMOD < body.getLinearVelocity().x) {
                //body.applyForce(new Vector2(-CURRENT_SPEED * DASHMOD, 0), body.getPosition(), true);
                dv.x -= 1;
            }
            
            
            dv.nor().scl(CURRENT_SPEED * DASHMOD);
            body.applyForce(dv, body.getPosition(), true);

            if (!(moveUp || moveDown || moveRight || moveLeft)
                    || ( (moveUp && moveDown) || (moveLeft && moveRight) )) {
                isprite = idleSprite;
            }
            
            if(moveDown && !moveUp){
                isprite = frontSprite;
            }
            if(moveUp && !moveDown){
                isprite = backSprite;
            }
            if(moveRight && !moveLeft){
                isprite = rightSprite;
            }
            if(moveLeft && !moveRight){
                isprite = leftSprite;
            }
        }
    }
    
    //@param: Direction up,down,left,right (U,D,L,R)
    //responsible for getting input and setting direction states
    public void move(Direction d) {
        if (sm.getState() == State.PLAYING) {
            switch (d) {
                case UP:
                    moveUp = true;
                    //moveDown = false;
                    break;
                case DOWN:
                    moveDown = true;
                    //moveUp = false;
                    break;
                case LEFT:
                    moveLeft = true;
                    //moveRight = false;
                    break;
                case RIGHT:
                    moveRight = true;
                    //moveLeft = false;
                    break;
                default:
                    break;
            }
        }
    }
    
    //@param: Direction up,down,left,right (U,D,L,R)
    public  void moveStop(Direction d) {
        switch (d) {
            case UP:
                moveUp = false;
                break;
            case DOWN:
                moveDown = false;
                break;
            case LEFT:
                moveLeft = false;
                break;
            case RIGHT:
                moveRight = false;
                break;
            default:
                break;
        }
    }
    
    private void updateDirection(){
        currentDirection = body.getPosition().cpy().sub(prevLocation.cpy()).nor();
        prevLocation = body.getPosition().cpy();
        
        currentAngle = currentDirection.angleRad();
        
    }
    
    public boolean isUserMoving() { return moveUp || moveRight || moveLeft || moveDown; }
    
    public  void dash(){
        if(canDash && energy >= DASH_COST && !dead){
            if(attackFC.running){ //added to allow players to combo off a dash
                System.out.println("@PlayerEntity combo dash");
                attackFC.complete();
            }
            
            
            //remove ability to control player
            //maybe use attackFC? dash during prep && attack, stand still during recov
            //flat speed (Max_Speed * dash_mod) 
            
            energy -= DASH_COST;
            canDash = false;
            DASHMOD *= 2.6;
            dashSpeed = CURRENT_SPEED * DASHMOD;
            
            dashFC.start(fm);
            
            //dash skill effects
            for(Skill skill: skillSet){
                if(skill != null && skill.isDashSkill()){
                    skill.effect();
                }
            }
            
            //sound
            SFX_DASH.play(false);
        }
    }
    
    
    private void dashUpdate() {
        
        if (dashFC.running) {
            if (dashFC.state != AttackState.RECOVERING
                    && dashSpeed > body.getLinearVelocity().x) {
                //edit: (2/17/16) 
                //apply persistent force on player, based on current direction
                body.applyForce(currentDirection.cpy().scl(dashSpeed), body.getPosition(), true);

            } else {
                isprite = recovSprite;
            }
        }
        
        if (!dashFC.running && !canDash) {
            canDash = true;
            DASHMOD /= DASHMOD;
        }
    }

    @Override
    public void damage(float damage){
        if(!dmgFC.running){
            
            life -= damage;
            System.out.println("@PlayerEntity " + this.toString() + " damaged - hp: " + life);

            if (life <= 0) {
                //alive = false;
                dead = true;
            }

            EnvironmentManager.currentEnv.addDamageText(
                    "" + (int) (damage + 1) + "",
                    new Vector2(
                            body.getPosition().x * PPM - width * rng.nextFloat() / 2,
                            body.getPosition().y * PPM + height * 1.1f),
                            "red");
            
            
            isprite = recovSprite;
            isprite.reset();
            
            dmgFC.start(fm);
            
            //sound
            SFX_DMG.play(false);
            
            //screen shake
            GameScreen.camera.shake(2.5f, 0.3f);
        }
    }
    
    
    
    private void updateSkill() {

        if(attackFC.running) {
            if (attackFC.state == AttackState.ATTACKING) {

                if (currentSkill != null && currentSkill.isActive()) {
                    currentSkill.effect(isCombo, previousSkill);
                }
            }
            
            switch(currentSkill.getType()){
                case HEAVY:
                    isprite = attackHeavySprite;
                    break;
                case SPECIAL:
                    isprite = playerBuffSprite;
                    break;
                default:
                    isprite = attackSprite;
                    break;  
            }
            
        }
    }
    
    
    public void attack(int index) {
        if (sm.getState() == State.PLAYING
                && skillSet[index - 1] != null
                && !dmgFC.running                               //while not being damaged
                && energy >= skillSet[index - 1].getCost() ) {   

            
            
            if(!attackFC.running){
                initNewSkill(index);
               isCombo = false;
               currentAttackFail = false;
               //set attackFC to new skill.comboFC
               
               //todo: REDUNDANT, attackFC set in initNewSkill()
                //attackFC.setTime(currentSkill.getPrepTime(), currentSkill.getAttTime(), currentSkill.getRecovTime());
                //attackFC = currentSkill.getComboFC();
                //attackFC.start(fm);
            }else if (attackFC.state == AttackState.COMBO
                        && !currentAttackFail) {   //combo
                initNewSkill(index);
                isCombo = true;
                //attackFC = currentSkill.getComboFC();
                //attackFC.reset();
                //attackFC.setTime(0, currentSkill.getAttTime(), currentSkill.getRecovTime());
                //attackFC.start(fm);
            } else{
                attackFail();
            }
            
        }
    }
    
    
    
    private void initNewSkill(int index){
        previousSkill = currentSkill;
        currentSkill = skillSet[index - 1];
        float cost = currentSkill.getCost();
        energy -= cost;

        attackFC = currentSkill.getComboFC();
        //attackFC.setTime(currentSkill.getPrepTime(), currentSkill.getAttTime(), currentSkill.getRecovTime());
        attackFC.start(fm);

        //execute background effects of skills (buffs, etc)
        //not the actual damage attack on enemy
        currentSkill.active();

        //animation
        
        
        if(currentSkill.getSkillSprite() != null){
            ImageSprite ss = currentSkill.getSkillSprite();
            skillSprites.add(new ImageSprite(ss,
                    body.getPosition().x * PPM - ss.sprite.getWidth() / 2, 
                    body.getPosition().y * PPM - ss.sprite.getHeight() / 2,
                    0.75f));
            //skillSprites.peek().sprite.setScale(0.75f);
        }
        /*
        if (currentSkill.getType() == SkillType.HEAVY) {
            skillSprites.add(new EntitySprite(skillHeavySprite,
                    body.getPosition().x * PPM - skillHeavySprite.sprite.getWidth() / 2,
                    body.getPosition().y * PPM - skillHeavySprite.sprite.getHeight() / 2));
            skillSprites.peek().sprite.setScale(0.75f);
        } else if (currentSkill.getType() != SkillType.SPECIAL) {
            skillSprites.add(new EntitySprite(skillSprite,
                    body.getPosition().x * PPM - skillSprite.sprite.getWidth() / 2,
                    body.getPosition().y * PPM - skillSprite.sprite.getHeight() / 2));
            skillSprites.peek().sprite.setScale(0.75f);
        }*/

        
        //reset appropriate skill sprite
        switch(currentSkill.getType()){
            case HEAVY:
                attackHeavySprite.reset();
                break;
            case SPECIAL:
                playerBuffSprite.reset();
                break;
            default:
                attackSprite.reset();
                break;
        }
        
        //sound
        SFX_YELLS.random().play(false);

        //overlay
        switch (currentSkill.getType()) {
            case LIGHT:
                GameScreen.overlay.resetSkillSlot(0);
                break;
            case HEAVY:
                GameScreen.overlay.resetSkillSlot(1);
                break;
            case SPECIAL:
                GameScreen.overlay.resetSkillSlot(2);
                break;
            default:
                break;
        }
        
        /***********
         * 
        ADD COMBO BAR TO OVERLAY
        * 
        ***********/
        GameScreen.overlay.addComboBar(attackFC);
    }
    
    
    public void setCurrentSkill(Skill skill){
       
        switch (skill.getType()){
            case LIGHT:
                if(skillSet[0] != null) skillSet[0].deactivate();
                skillSet[0] = skill;
                break;
            case HEAVY:
                if(skillSet[1] != null) skillSet[1].deactivate();
                skillSet[1] = skill;
                break;
            case SPECIAL:
                if(skillSet[2] != null) skillSet[2].deactivate();
                skillSet[2] = skill;
                break;
            case PASSIVE:
                if(skillSet[3] != null) skillSet[3].deactivate();
                skillSet[3] = skill;
                break;
            default:
                return;
        }
        
        skill.activate();
        
    }
    
    //TODO: change to index value, cleaner than passing type
    public Skill getCurrentSkill(SkillType type){
        switch (type){
            case LIGHT:
                return skillSet[0];
            case HEAVY:
                return skillSet[1];
            case SPECIAL:
                return skillSet[2];
            case PASSIVE:
                return skillSet[3];
            default:
                return null;
        }
    }
    
    private void attackFail(){
        //fail comboBar on overlay
        currentAttackFail = true;
        GameScreen.overlay.removeComboBar();
    }
    
    
    private void updateEnergy(){
        if (energy < CURRENT_ENERGY && !attackFC.running && !dashFC.running) {
            regenEnergy();
        } else if (energy > CURRENT_ENERGY) {
            energy = CURRENT_ENERGY;
        }
    }
    
    public void regenEnergy(){
        energy += CURRENT_ENERGY * ENERGY_REGEN;
        if(energy > CURRENT_ENERGY){
            energy = CURRENT_ENERGY;
        }
    }
    
    public void addEnergy(float e){
        energy += e;
        if(energy > CURRENT_ENERGY){
            energy = CURRENT_ENERGY;
        }
    }
    
    public void useEnergy(float cost){
        if(energy >= cost){
            energy -= cost;
        }
    }
    
    public void restoreHp(float l){
        
        float lp = l * this.LIFE_STAT_COUNT;
        float templife = this.life;
        this.life = 
                this.life + lp > CURRENT_LIFE ? CURRENT_LIFE 
                : this.life + lp;
        
        int ammountRestored = (int)(this.life - templife);
        
        if (ammountRestored > 0) {
            EnvironmentManager.currentEnv.addHealingText(
                    "" + ammountRestored + "",
                    new Vector2(
                            body.getPosition().x * PPM - width * rng.nextFloat() / 2,
                            body.getPosition().y * PPM + height * 1.1f));
        }
    }
    
    public void restoreHp(){
        this.life = CURRENT_LIFE;
    }
    
    public Buff addBuff(Buff buff){
        buff.start();
        buff.applyBuff();
        buffsToAdd.add(buff);
        
        return buff;
    }
    
    public void removeBuff(Buff buff){
        buffsToRemove.add(buff);
    }
    
    public boolean hasBuff(Buff buff){
        return buffs.contains(buff, false);
    }
    
    private void updateBuffs(){
        for (Buff b : buffs) {
            b.update();
            if (b.isComplete()) {
                buffsToRemove.add(b);
            }
        }

        for (Buff b : buffsToRemove) {
            b.removeBuff();
            buffs.removeValue(b, false);
        }

        if (buffsToRemove.size > 0) {
            buffsToRemove.clear();
        }

        for (Buff b : buffsToAdd) {
            buffs.add(b);
        }
        if (buffsToAdd.size > 0) {
            buffsToAdd.clear();
        }
    }
    
    public void addTarget(Entity e){
        if(!attTargets.contains(e)){
            attTargets.add(e);
        }
    }
    
    public void removeTarget(Entity e){
        if(attTargets.contains(e)){
            attTargets.remove(e);
        }
    }
    
    //todo: needed or handled in individual skill??
    public void addImpactSprite(float x, float y, ImageSprite isprite){
        isprite.sprite.setPosition(x, y);
        impactSprites.add(isprite);
        
        impactAlphas.add(1.0f);
        for(ImageSprite impsp: impactSprites){
            impsp.sprite.setScale(spriteScale * 1.4f);
        }
    }
    
    //Description:  sets player warp animation during env end state
    //@param: pos - position of tear
    public void warp(Vector2 pos){
        
        warping = true;
        warpSprite.reset();
        warpSprite.sprite.setPosition(
                pos.x*PPM - warpSprite.sprite.getWidth()/2, 
                pos.y*PPM - warpSprite.sprite.getHeight()/2);
        isprite = warpSprite;
        
    }
    
    //*****************************
    //Desctiption: for action events involving actionEntity
    //private Entity actionEntity;
    private Array<Entity> actionEntities = new Array<Entity>();
    
    public void inRangeForAction(Entity e){
        //show hot key
        //enable hotkey
        //actionEntity = e;
        actionEntities.add(e);
    }
    
    public void outRangeForAction(Entity e){
        //actionEntity = null;
        actionEntities.removeValue(e, false);
    }
    
    public void beginAction(){
        //if(actionEntity != null)
            //actionEntity.actionEvent();
        
        if(actionEntities.size > 0)
            actionEntities.peek().actionEvent();
    }
    
    public void clearActionEvents(){
        actionEntities.clear();
    }
    //*****************************
    
    public void addSoulPiece(float s){
        soulCount += s;
        
        if(soulCount >= SOUL_MAX){
            soulCount -= SOUL_MAX;
            System.out.println("@PlayereEntity soul up!");
            soulUp();
            soulSound.play(false);
            EnvironmentManager.currentEnv.addDamageText("SOUL UP", 
                    new Vector2(body.getPosition().x*PPM, body.getPosition().y*PPM));
        }
        
        
    }
    
    public void soulUp(){
        addStatPoints(1,1,1,1,1);
    }
    
    //Adds to stat counter
    public void addStatPoints(int life, int energy, int dmg, int speed, int special){
        LIFE_STAT_COUNT += life;
        ENERGY_STAT_COUNT += energy;
        DAMAGE_STAT_COUNT += dmg;
        SPEED_STAT_COUNT += speed;
        SPECIAL_STAT_COUNT += special;
        
        updateCurrentStatValues(life, energy, dmg, speed, special);
    }
    
    public void refreshStats(){
        CURRENT_LIFE =      BASE_LIFE +    LIFE_STAT_COUNT *   LIFE_STAT_VALUE;
        CURRENT_ENERGY =    BASE_ENERGY +  ENERGY_STAT_COUNT * ENERGY_STAT_VALUE;
        CURRENT_DAMAGE =    BASE_DAMAGE +  DAMAGE_STAT_COUNT * DAMAGE_STAT_VALUE;
        CURRENT_SPEED =     BASE_SPEED +   SPEED_STAT_COUNT *  SPEED_STAT_VALUE;
        CURRENT_SPECIAL =   BASE_SPECIAL + SPECIAL_STAT_COUNT* SPECIAL_STAT_VALUE;
    }
    
    
    public void updateCurrentStatValues(int life, int energy, int dmg, int speed, int special){
        CURRENT_LIFE +=         LIFE_STAT_VALUE * life;
        CURRENT_ENERGY +=       ENERGY_STAT_VALUE * energy;
        CURRENT_DAMAGE +=       DAMAGE_STAT_VALUE * dmg;
        CURRENT_SPEED +=        SPEED_STAT_VALUE * speed;
        CURRENT_SPECIAL +=      SPECIAL_STAT_VALUE * special;
    }

    
}
