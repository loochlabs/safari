/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.camera.ParallaxCamera;
import com.mygdx.entities.DynamicEntities.player.PlayerEntity;
import com.mygdx.environments.Environment;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.game.MainGame;
import com.mygdx.gui.Overlay;
import com.mygdx.gui.pause.PauseOverlay;
import com.mygdx.managers.GameStats;
import com.mygdx.managers.ItemManager;
import com.mygdx.managers.PlayerInputManager;
import com.mygdx.managers.StateManager;
import com.mygdx.managers.StateManager.State;
import com.mygdx.utilities.FrameCounter;
import com.mygdx.utilities.ShaderUtil;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class GameScreen extends Screen{

    public static ParallaxCamera camera;
    
    //envs and entities
    
    private Environment currentEnv;
    private static int startEnvNum = 0; 
    
    //private final int START_NUM;
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
    
    @Override
    public void create() {
        
        camera = new ParallaxCamera();
        camera.viewportHeight = MainGame.HEIGHT;
        camera.viewportWidth = MainGame.WIDTH;
        
        
        
        //GameStats
        GameStats.init();
        
        
        //************************
        //      ENVIRONMNETS
        //*************************
        EnvironmentManager.createStart(startEnvNum);
        
        if (currentEnv != EnvironmentManager.currentEnv) {
            currentEnv = EnvironmentManager.currentEnv;
        }
        
        
        //itempool
        ItemManager.load();
        
        //input
        pim = new PlayerInputManager();
        
        
        //gui
        overlay = new Overlay(MainGame.WIDTH, MainGame.HEIGHT);
        overlayCam = new OrthoCamera();
        overlayCam.viewportHeight = MainGame.HEIGHT;
        overlayCam.viewportWidth = MainGame.WIDTH;
        overlayCam.setPosition(MainGame.WIDTH/2,MainGame.HEIGHT/2);
        
        //box2d
        b2dr = new Box2DDebugRenderer();
        b2dcam = new OrthoCamera();
        b2dcam.setToOrtho(false, ((float)MainGame.WIDTH)/PPM, ((float)MainGame.HEIGHT)/PPM);
        
        fpsfont.setColor(Color.PURPLE);
        
        //init blur shader
        //this.initBlurs();
        
        
    }
    
    @Override
    public void update(float dt) {}

    @Override
    public void render(SpriteBatch sb) {
        
        if (currentEnv != EnvironmentManager.currentEnv) {
            currentEnv = EnvironmentManager.currentEnv;
        }
        
        //*************************
        //      UPDATING
        //**********************
        
        if(sm.getState() == State.PLAYING){
         
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
        
        //****************************************************
        
        //input
        pim.update();
        
        
        //*************************
        //      RENDERING
        //**********************
        
        if(!activeBlur){
            sb.begin();
            renderEnv(sb);
            sb.end();
        }else    
            renderBlur(sb);
        
        
        //gui
        sb.setProjectionMatrix(overlayCam.combined);
        sb.begin();
        overlay.render(sb);
        
        
        //**********
        // PAUSE MENU
        if(sm.getState() == State.PAUSED){
            //pauseOverlay.render(sb);
        }
        
        
        if(MainGame.debugmode){
            fpsfont.draw(sb,"fps:" + Gdx.graphics.getFramesPerSecond() + "", MainGame.WIDTH-60, MainGame.HEIGHT);
        }
        
        sb.end();
        
        
        //box2d
        if(MainGame.debugmode){
            b2dr.render(currentEnv.getWorld(), b2dcam.combined);
        }
        
        
        
    }
    
    public void renderEnv(SpriteBatch sb){
        
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
    
    
    private ShaderProgram blurShader;
    private FrameBuffer blurTargetA, blurTargetB;
    private float radius = 3f;
    private final static float MAX_BLUR = 3f;
    public static final int FBO_SIZE = 1024;
    
    public void initBlurs(){
        
        //load our shader program and sprite batch
	try {
            //create our FBOs
            blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
            blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);

            //our basic pass-through vertex shader
            final String VERT = ShaderUtil.readFile(ShaderUtil.getResourceAsStream("effects/shader/lesson5.vert"));

            //our fragment shader, which does the blur in one direction at a time
            final String FRAG = ShaderUtil.readFile(ShaderUtil.getResourceAsStream("effects/shader/lesson5.frag"));

            //create our shader program
            blurShader = new ShaderProgram(VERT, FRAG);
            
            //Good idea to log any warnings if they exist
            if (blurShader.getLog().length() != 0) {
                System.out.println(blurShader.getLog());
            }

            //always a good idea to set up default uniforms...
            //blurShader..use();
            blurShader.setUniformf("dir", 0f, 0f); //direction of blur; nil for now
            blurShader.setUniformf("resolution", FBO_SIZE); //size of FBO texture
            blurShader.setUniformf("radius", radius); //radius of blur

            //blurBatch = new SpriteBatch(); 
        } catch (Exception e) {
            //simple exception handling...
            e.printStackTrace();
            System.exit(0);
        }
        
        
    }
    
    public static void blurTransition(){
        //activeBlur = true;
    }
    
    public void renderBlur(SpriteBatch sb){
        renderSceneBlur(sb);
        horizontalBlur(sb);
        verticalBlur(sb);
    }
    
    public void renderSceneBlur(SpriteBatch sb){
        //Bind FBO target A
        blurTargetA.begin();

        //Clear FBO A with an opaque colour to minimize blending issues
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Reset batch to default shader (without blur)
        sb.setShader(null);

        //send the new projection matrix (FBO size) to the default shader
        sb.setProjectionMatrix(camera.calculateParallaxMatrix(1.0f, 1.0f));

        //now we can start our batch
        sb.begin();

        //render our scene fully to FBO A
        //drawEntities(sb);
        renderEnv(sb);

        //flush the batch, i.e. render entities to GPU
        sb.flush();

        //After flushing, we can finish rendering to FBO target A
        blurTargetA.end();
    }
    
    public void horizontalBlur(SpriteBatch sb){
        //swap the shaders
        //this will send the batch's (FBO-sized) projection matrix to our blur shader
        sb.setShader(blurShader);

        //ensure the direction is along the X-axis only
        blurShader.setUniformf("dir", 1f, 0f);

        //determine radius of blur based on mouse position
        //float mouseXAmt = Mouse.getX() / (float) Display.getWidth();
        blurShader.setUniformf("radius", 0.5f * MAX_BLUR);

        //start rendering to target B
        blurTargetB.begin();

	//no need to clear since targetA has an opaque background
        //render target A (the scene) using our horizontal blur shader
        //it will be placed into target B
        sb.draw(blurTargetA.getColorBufferTexture(), 0, 0);

        //flush the batch before ending target B
        sb.flush();

        //finish rendering target B
        blurTargetB.end();
    }
    
    public void verticalBlur(SpriteBatch sb){
    //now we can render to the screen using the vertical blur shader

        //send the screen-size projection matrix to the blurShader
        sb.setProjectionMatrix(camera.calculateParallaxMatrix(1.0f, 1.0f));

        //apply the blur only along Y-axis
        blurShader.setUniformf("dir", 0f, 1f);

        //update Y-axis blur radius based on mouse
        //float mouseYAmt = (Display.getHeight() - Mouse.getY() - 1) / (float) Display.getHeight();
        blurShader.setUniformf("radius", 0.5f * MAX_BLUR);

        //draw the horizontally-blurred FBO B to the screen, applying the vertical blur as we go
        sb.draw(blurTargetB.getColorBufferTexture(), 0, 0);

        sb.end();
    }
    

    @Override
    public void resize(int width, int height) {
        camera.resize();
        overlayCam.resize();
        
        //resize environment w/ all entities
        //EnvironmentManager.resize()
    }

    @Override
    public void dispose() {
        //clear all variables
        GameStats.dispose();
        
    }

    //************************
    //All input comes in through pause(), 
    //  will go to resume() if at end of pauseOverlay
    @Override
    public void pause() {
        //on ESC/Gamepad.start
        if(sm.getState() != State.PAUSED){
            sm.setState(2);
            
            //pauseOverlay = new PauseOverlay();
        }else{
            //pauseOverlay.back();
        }
    }

    @Override
    public void resume() {
        if(sm.getState() == State.PAUSED){
            sm.setState(1);
        }
    }
    //******************************

    
    
    
    
}
