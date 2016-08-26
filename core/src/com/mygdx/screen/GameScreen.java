/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.camera.ParallaxCamera;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.environments.EnvRoom.EnvRoom;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.gui.Overlay;
import com.mygdx.gui.pause.PauseOverlay;
import com.mygdx.managers.GameStats;
import com.mygdx.managers.PlayerInputManager;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author looch
 */
public class GameScreen extends Screen{

    public static ParallaxCamera camera;
    
    private Environment currentEnv;
    private static int startEnvNum = 0; 
    public static PlayerEntity player;
    
    //input
    private PlayerInputManager pim;
    
    //state, pause menus
    public static StateManager sm = new StateManager();
    public static PauseOverlay pauseOverlay;
    
    //gui
    public static Overlay overlay;
    public OrthoCamera overlayCam;
    
    //shader efffects
    public static boolean activeBlur = false;
    
    private Box2DDebugRenderer b2dr;
    private OrthoCamera b2dcam;
    private BitmapFont fpsfont = new BitmapFont();
    
    
    
    public GameScreen(int n){
        startEnvNum = n;
    }
    
    public GameScreen(){
        this(0);
    }
    
    @Override
    public void create() {
        
        camera = new ParallaxCamera();
        camera.viewportHeight = MainGame.HEIGHT;
        camera.viewportWidth = MainGame.WIDTH;
        
        //GameStats
        GameStats.init();
        
        //player
        player = new PlayerEntity(new Vector2(0,0), 0,0);
        
        //ENVIRONMNETS
        EnvironmentManager.add(new EnvRoom(0,1));
        
        if (currentEnv != EnvironmentManager.currentEnv) {
            currentEnv = EnvironmentManager.currentEnv;
        }
        
        //input
        pim = new PlayerInputManager();
        
        //gui
        overlay = new Overlay();
        overlayCam = new OrthoCamera();
        overlayCam.viewportHeight = MainGame.HEIGHT;
        overlayCam.viewportWidth = MainGame.WIDTH;
        overlayCam.setPosition(MainGame.WIDTH/2,MainGame.HEIGHT/2);
        
        //box2d
        b2dr = new Box2DDebugRenderer();
        b2dcam = new OrthoCamera();
        b2dcam.setToOrtho(false, ((float)MainGame.WIDTH)/PPM, ((float)MainGame.HEIGHT)/PPM);
        
        fpsfont.setColor(Color.PURPLE);
        
    }
    
    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch sb) {
        
        if (currentEnv != EnvironmentManager.currentEnv) {
            currentEnv = EnvironmentManager.currentEnv;
        }
        
        //****************************
        //      FRAME UPDATING
        //****************************
        
        if(currentEnv != null && sm.getState() == State.PLAYING){
            currentEnv.update();
            currentEnv.updateCamera(camera);
            currentEnv.updateB2DCamera(b2dcam);
            
            //camera lerp
            camera.update();
            
            if (MainGame.debugmode) {
                b2dcam.update();
            }

            //gui
            overlay.update();
            overlayCam.update();
        }
        
        try {
            pim.update();
        } catch (InterruptedException ex) {
            Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        //*************************
        //      RENDERING
        //**********************
        
        sb.begin();
        renderEnv(sb);
        sb.end();
        
        //gui
        sb.setProjectionMatrix(overlayCam.combined);
        sb.begin();
        overlay.render(sb);
        
        if(MainGame.debugmode){
            fpsfont.draw(sb,"fps:" + Gdx.graphics.getFramesPerSecond() + "", MainGame.WIDTH-60, MainGame.HEIGHT);
        }
        
        sb.end();
        
        
        //box2d
        if(MainGame.debugmode && currentEnv != null){
            b2dr.render(currentEnv.getWorld(), b2dcam.combined);
        }
        
    }
    
    public void renderEnv(SpriteBatch sb){
        
        if(currentEnv == null) return;
        
        if (currentEnv.getRenderLayers() == 0) {
            GameScreen.this.renderEnvLayer(sb, camera.combined);

        } else {
            for (int i = currentEnv.getRenderLayers() - 1; i >= 0; i--) {

                //new - 3/23/16
                if (i == currentEnv.getRenderLayers() - 1) {
                    renderEnvLayer(sb, camera.calculateParallaxMatrix(currentEnv.getBgParallaxX(), currentEnv.getBgParallaxY()), i);
                } else {
                    renderEnvLayer(sb, camera.calculateParallaxMatrix(currentEnv.getFgParallaxX(), currentEnv.getFgParallaxY()), i);
                }
            }
        }
    }
    
    public void renderEnvLayer(SpriteBatch sb, Matrix4 mat){
        sb.setProjectionMatrix(mat);
        currentEnv.render(sb);
    }
    
    public void renderEnvLayer(SpriteBatch sb, Matrix4 mat, int layer){
        sb.setProjectionMatrix(mat);
        currentEnv.render(sb, layer);
    }
    
    
    
    //******************
    //  CAMERA LERP
    private final float CAM_LERP = 0.1f;
    
    public void cameraLerp(Vector2 target){
        // a + (b - a) * lerp
        Vector3 position = camera.position;
        position.x = camera.position.x + (target.x - camera.position.x) * CAM_LERP;
        position.y = camera.position.y + (target.y - camera.position.y) * CAM_LERP;
        camera.position.set(position);
        camera.update();
    }
    
    
    @Override
    public void resize(int width, int height) {
        camera.resize();
        overlayCam.resize();
    }

    @Override
    public void dispose() {
        GameStats.dispose();
    }

    @Override
    public void pause() {
        //on ESC/Gamepad.start
        if(sm.getState() != State.PAUSED){
            sm.setState(2);
        }
    }

    @Override
    public void resume() {
        if(sm.getState() == State.PAUSED){
            sm.setState(1);
        }
    }

}
