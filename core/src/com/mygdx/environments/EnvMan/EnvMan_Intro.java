/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvMan;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.demo.demo2.DemoContScreen;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.entities.ImageSprite;
import com.mygdx.environments.EnvRoom.RoomArc;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.managers.StateManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.screen.ScreenManager;
import com.mygdx.utilities.SoundObject_Bgm;
import com.mygdx.utilities.SoundObject_Sfx;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author saynt
 */
public class EnvMan_Intro extends Environment{
    
    //sound
    protected SoundObject_Bgm bgm1;
    private SoundObject_Sfx activateSound;
     
    private RoomArc roomArc;
    private final Wall_StartLock lockWall;
    private boolean wallLocked = true;
    
    public EnvMan_Intro(int id, int linkid) {
        super(id);
        
        this.linkid = linkid;
        
        fg = MainGame.am.get(ResourceManager.ENVMAN_INTRO_BG);
        
        beginFC.setTime(0);
        
        setRoomSize();
        
        //sound
        bgm1 = new SoundObject_Bgm(ResourceManager.BGM_NULL_END);
        activateSound = new SoundObject_Sfx(ResourceManager.SFX_PICKUP);

        roomArc = new RoomArc(new Vector2((fgx) + width / 2, height * 0.88f), 670 * RATIO, 180 * RATIO);
        lockWall = new Wall_StartLock(new Vector2((fgx) + width /2, height * 0.855f), 520*RATIO, 125 * RATIO);
    }
    
    
    
    
    @Override
    public void init(){
        super.init();

        float border = 25f;
        
        spawnEntity(new BlankWall(new Vector2( (fgx) + width/2, height*0.0625f),       width/2,  border));//south
        
        spawnEntity(new BlankWall(new Vector2( (fgx) + width*0.92f, height/2),  border, height/2));//east
        spawnEntity(new BlankWall(new Vector2( (fgx) + width*0.08f, height/2),   border, height/2));//west
        
        //sensor for ContScreen
        spawnEntity(new ManIntroEndWall(new Vector2( (fgx) + width/2, height*0.95f),  width/2,  border));//north
        
        spawnEntity(new BlankWall(new Vector2( (fgx) + width*0.525f, height*0.325f),   border, height*0.31f));//inside right
        spawnEntity(new BlankWall(new Vector2( (fgx) + width*0.7555f, height*0.62f),   width*0.25f, border));//inside up
        
        if (wallLocked) {
            spawnEntity(lockWall);
        }
        spawnEntity(roomArc);
        //wall lock
        
        
    }
    
    @Override
    public void begin(){
        super.begin();
        
        bgm1.play();
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        super.end(id, time);
    }
    
    @Override
    public void envTransition() {
        if(sm.getState() == StateManager.State.BEGIN ){
            
            //call Overlay transition out
            
            if(GameScreen.overlay.transition(true) || beginFC.complete){
                System.out.println("@Environment begin trans " + this);
                GameScreen.overlay.endTransition();
                play();
            }
            
        }
        
        //TRANSITION SCENES
        if(sm.getState() == StateManager.State.END ){
            
            //call Overlay transition out
            if(GameScreen.overlay.transition(false)){
                System.out.println("@Environment end trans " + this);
                GameScreen.overlay.endTransition();
                ScreenManager.setScreen(new DemoContScreen());
            }
            
        }
    }
    
    public void setRoomSize(){
        width = 1300f*RATIO;
        height = 1745f*RATIO;
        
        
        fgx = 0;
        fgy = 0;
        fgw = width;
        fgh = height;
        
        startPos = new Vector2(width*0.3f/PPM,height*0.125f/PPM);
        this.setPlayerToStart();
    }
    
    public void unlockWall() {
        if (wallLocked) {
            wallLocked = false;
            activateSound.play(false);
        }

    }
    
    
    public class Wall_StartLock extends BlankWall {

        protected FixtureDef sens = new FixtureDef();
        public boolean locked = true;
        protected ImageSprite openSprite, closedSprite;
        protected Object wallData;


        public boolean isLocked() {
            return locked;
        }

        public Wall_StartLock(Vector2 pos, float w, float h) {
            super(pos, w, h);

            //TODO: adjust position of wall sensor
            wallData = "action_" + id;
            sens.filter.categoryBits = BIT_WALL;
            sens.filter.maskBits = BIT_PLAYER;
            CircleShape c = new CircleShape();
            c.setPosition(new Vector2(375f/PPM, -100f/PPM));
            c.setRadius(2f * RATIO);
            sens.shape = c;
            sens.isSensor = true;

            closedSprite = new ImageSprite("start-wall-closed", false);
            closedSprite.sprite.setBounds(pos.x, pos.y, width * 2, height * 2);

            openSprite = new ImageSprite("start-wall-lock", false);
            openSprite.sprite.setBounds(pos.x, pos.y, width * 2, height * 2);

            isprite = closedSprite;
            
            this.flaggedForRenderSort = false;


        }

        @Override
        public void init(World world) {
            super.init(world);
            body.createFixture(sens).setUserData(wallData);
            userdata = wallData;
        }

        @Override
        public void update() {
            super.update();

            if (!locked) {
                isprite = openSprite;

                if (openSprite.isComplete()) {
                    destroy();
                }
            }
        }


        @Override
        public void alert(String []str) {
            try {
                if (str[0].equals("begin") && str[1].contains("action_")) {
                    GameScreen.player.inRangeForAction(this);

                }
                if (str[0].equals("end") && str[1].contains("action_")) {
                    GameScreen.player.outRangeForAction(this);
                }
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void actionEvent() {
            //use item
            this.openDoor();
            EnvMan_Intro room = (EnvMan_Intro) EnvironmentManager.currentEnv;
            room.unlockWall();

            GameScreen.player.outRangeForAction(this);

        }

        //called when key is used within range of lock
        public void openDoor() {

            if (locked) {
                locked = false;
            }
        }

        public void destroy() {
            EnvironmentManager.currentEnv.removeEntity(this);
        }

    }
    
    private class ManIntroEndWall extends BlankWall{
        
        public ManIntroEndWall(Vector2 pos, float w, float h) {
            super(pos, w, h);
            
            userdata = "action_" + id;
            
        }
        
        @Override
        public void alert(String [] s){
            System.out.println("@EnvMan_Intro alert");
            //set to gameOverScreen
            EnvironmentManager.currentEnv.end(0, 3f);
        }
        
        
    }

}
