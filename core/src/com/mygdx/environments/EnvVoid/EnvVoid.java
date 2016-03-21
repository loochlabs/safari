/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvVoid;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.mygdx.camera.OrthoCamera;
import com.mygdx.combat.NormAttackSensor;
import com.mygdx.entities.DynamicEntities.DogEntities.MurphyEntity;
import com.mygdx.entities.Entity;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.DynamicEntities.DogEntities.StellaEntity;
import com.mygdx.environments.EnvNull.Tear_R_One;
import com.mygdx.environments.tears.TearPortal;
import com.mygdx.environments.tears.Tear_Room_DMLock;
import com.mygdx.environments.tears.Tear_Room_EndPiece;
import com.mygdx.environments.tears.Tear_Room_Glyph1;
import com.mygdx.environments.tears.Tear_Room_Simple;
import com.mygdx.environments.tears.Tear_Room_Statup;
import com.mygdx.entities.esprites.BgSprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.text.TextEntity;
import com.mygdx.environments.EnvSub.EnvSub;
import com.mygdx.environments.EnvSub.pads.EndPiece;
import com.mygdx.environments.EnvSub.pads.EndWarp;
import com.mygdx.environments.EnvSub.pads.test.EndWarp_Test;
import com.mygdx.environments.Environment;
import com.mygdx.environments.RngRoomManager;
import com.mygdx.environments.tears.Tear_Room_Glyph1.EnvRoom_Glyph1_TEST;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.StateManager.State;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.SoundObject_Bgm;
import static com.mygdx.utilities.UtilityVars.PPM;
import java.util.Collections;

/**
 *
 * @author looch
 */
public class EnvVoid extends Environment{
    
    protected final VoidGrid grid, igrid;
    protected int spawnCount;
    protected final VoidMap map;
    protected Texture fgFilter;
    
    private final Array<Body> playerBodies = new Array<Body>();
    private Body playerBodyNorth, playerBodySouth, playerBodyEast, playerBodyWest;
    private Body playerBodyNE, playerBodyNW, playerBodySE, playerBodySW;
    
    public Array<Body> getPlayerBodies() { return playerBodies; }
    public VoidMap getMap() { return map; }
    
    //**************
    //  SOUND
    //**************
    private SoundObject_Bgm bgm1;
    
    public EnvVoid(int id, float w, float h, int spawncount, String type){
        super(id);
        
        this.width = w;
        this.height = h;
        this.renderLayers = 0;
        
        //Intro description 
        introDescription = "The Void";
        
        renderLayers = 2;
        
        beginFC.setTime(0);
        
        //camera settings
        bgParallaxX = 1.0f;
        bgParallaxY = 1.0f;
        
        grid = new VoidGrid(0,0,width, height,3,3, "");
        igrid = new VoidGrid(
                -2*width/3,
                -2*height/3,
                (width+(4*RATIO)*width/(3*RATIO)) , 
                (height+(4*RATIO)*height/(3*RATIO)) , 
                7,7,
                type);
        
        //Void map
        map = new VoidMap(grid.getWidth()/2 - 400*RATIO, grid.getHeight()/2 - 400*RATIO, 550f*RATIO, 550f*RATIO, grid);
        this.spawnCount = spawncount;
        
        
        startPos = new Vector2((grid.getWidth()/2)/PPM, (grid.getHeight()/2)/PPM);
        this.setPlayerToStart();
        
        
        
        
        fgFilter = MainGame.am.get(ResourceManager.VOID_BG_FILTER);
        
        
        
        
        //**********
        //  SOUND
        //**********
        bgm1 = new SoundObject_Bgm(ResourceManager.BGM_VOID_1);
         
    }
    
    
    //Description:
    //      -Initialize Settings and objects in this Environment
    //      -Randomly generate tears in GridCells of this.grid
    
    
    
    @Override
    public void init(){
        super.init();
        
        generateTears();
        generateMisc();
        
        //create player ghost bodies
        playerBodyNorth = world.createBody(GameScreen.player.getBodyDef());
        playerBodySouth = world.createBody(GameScreen.player.getBodyDef());
        playerBodyEast = world.createBody(GameScreen.player.getBodyDef());
        playerBodyWest = world.createBody(GameScreen.player.getBodyDef());
        playerBodyNE = world.createBody(GameScreen.player.getBodyDef());
        playerBodyNW = world.createBody(GameScreen.player.getBodyDef());
        playerBodySE = world.createBody(GameScreen.player.getBodyDef());
        playerBodySW = world.createBody(GameScreen.player.getBodyDef());
        
        NormAttackSensor sensor = new NormAttackSensor(GameScreen.player);
        
        playerBodyNorth.createFixture(sensor).setUserData(sensor.getData());
        playerBodySouth.createFixture(sensor).setUserData(sensor.getData());
        playerBodyEast.createFixture(sensor).setUserData(sensor.getData());
        playerBodyWest.createFixture(sensor).setUserData(sensor.getData());
        playerBodyNE.createFixture(sensor).setUserData(sensor.getData());
        playerBodyNW.createFixture(sensor).setUserData(sensor.getData());
        playerBodySE.createFixture(sensor).setUserData(sensor.getData());
        playerBodySW.createFixture(sensor).setUserData(sensor.getData());
        
                
        playerBodies.add(playerBodyNorth);
        playerBodies.add(playerBodySouth);
        playerBodies.add(playerBodyEast);
        playerBodies.add(playerBodyWest);
        playerBodies.add(playerBodyNE);
        playerBodies.add(playerBodyNW);
        playerBodies.add(playerBodySE);
        playerBodies.add(playerBodySW);
        
        
        //bg sprites
        int spritecount = 20;
        for(int i = 0; i < spritecount; i++){
            spawnEntity(new BgSprite(grid.getWidth() * rng.nextFloat(), grid.getHeight()* rng.nextFloat(), rng.nextFloat()+1));
        }
        
    }
    
    
    @Override
    public void render(SpriteBatch sb){
        
        
        if(sm.getState() == State.BEGIN 
                || sm.getState() == State.PLAYING 
                || sm.getState() == State.END){
            
            
            
            //background + ghost images
            for (int i = 0; i < igrid.getRows(); i++) {
                for (int j = 0; j < igrid.getCols(); j++) {
                    GridCell[][] g = igrid.getGrid();
                    g[i][j].render(sb);
                }
            }
            
            map.render(sb);
            
            
            //entities.sort();
            Collections.sort(entities, new Entity.EntityComp());
            
            for (Entity e : entities) {
                e.render(sb);
                
                drawGhostImage(sb, e);
            }
            
            
            //sprite ghost images
            /*
            for(ImageSprite sprite: sprites){
                sprite.drawOffset(sb, grid.getWidth(), 0);
                sprite.drawOffset(sb, -grid.getWidth(), 0);
                sprite.drawOffset(sb, 0, grid.getHeight());
                sprite.drawOffset(sb, 0, -grid.getHeight());
                sprite.drawOffset(sb, grid.getWidth(), grid.getHeight());
                sprite.drawOffset(sb, -grid.getWidth(), grid.getHeight());
                sprite.drawOffset(sb, grid.getWidth(), -grid.getHeight());
                sprite.drawOffset(sb, -grid.getWidth(), -grid.getHeight());
            }*/
            
            
            
            for(TextEntity text: dmgTexts){
                text.render(dmgFont, sb);

                if (text.flagForDelete) {
                    dmgTextToRemove.add(text);
                }
            }

            for (TextEntity text : dmgTextToRemove) {
                dmgTexts.removeValue(text, false);
            }

            dmgTextToRemove.clear();
            
        
            sb.draw(fgFilter, 
                    GameScreen.player.getBody().getPosition().x*PPM - MainGame.WIDTH/2,
                    GameScreen.player.getBody().getPosition().y*PPM - MainGame.HEIGHT/2,
                    MainGame.WIDTH,
                    MainGame.HEIGHT);
        
            
        }
        
        //todo: needed?
        if(sm.getState() == State.END){
            endSpectralAnim(sb);
        }
        
    }
    
    
    private void drawGhostImage(SpriteBatch sb, Entity e) {
        e.offsetRender(sb, grid.getWidth(), 0, 0);
        e.offsetRender(sb, -grid.getWidth(), 0, 0);
        e.offsetRender(sb, 0, grid.getHeight(), 0);
        e.offsetRender(sb, 0, -grid.getHeight(), 0);
        e.offsetRender(sb, grid.getWidth(), grid.getHeight(), 0);
        e.offsetRender(sb, -grid.getWidth(), grid.getHeight(), 0);
        e.offsetRender(sb, grid.getWidth(), -grid.getHeight(), 0);
        e.offsetRender(sb, -grid.getWidth(), -grid.getHeight(), 0);
    }
    
    
    
    @Override
    public void render(SpriteBatch sb, int layer){
        
        switch (layer) {
            case 0:
                
                if (sm.getState() == State.PLAYING || sm.getState() == State.END) {
                    sb.draw(fgFilter,
                            GameScreen.player.getBody().getPosition().x * PPM - MainGame.WIDTH / 2,
                            GameScreen.player.getBody().getPosition().y * PPM - MainGame.HEIGHT / 2,
                            MainGame.WIDTH,
                            MainGame.HEIGHT);
                }

                
                break;
            case 1:

                if (sm.getState() == State.BEGIN 
                        || sm.getState() == State.PLAYING 
                        || sm.getState() == State.END) {
                    //background + ghost images
                    for (int i = 0; i < igrid.getRows(); i++) {
                        for (int j = 0; j < igrid.getCols(); j++) {
                            GridCell[][] g = igrid.getGrid();
                            g[i][j].render(sb);
                        }
                    }

                    map.render(sb);

                    
                    /*
                    for (ImageSprite e : sprites) {
                        e.render(sb);
                    }*/
                    
                    //entities.sort();
                    Collections.sort(entities, new Entity.EntityComp());

                    for (Entity e : entities) {
                        e.render(sb);
                    }

                    //sprite ghost images
                    /*
                    for (ImageSprite sprite : sprites) {
                        sprite.drawOffset(sb, grid.getWidth(), 0);
                        sprite.drawOffset(sb, -grid.getWidth(), 0);
                        sprite.drawOffset(sb, 0, grid.getHeight());
                        sprite.drawOffset(sb, 0, -grid.getHeight());
                        sprite.drawOffset(sb, grid.getWidth(), grid.getHeight());
                        sprite.drawOffset(sb, -grid.getWidth(), grid.getHeight());
                        sprite.drawOffset(sb, grid.getWidth(), -grid.getHeight());
                        sprite.drawOffset(sb, -grid.getWidth(), -grid.getHeight());
                    }*/

                    //entity ghost images
                    for (Entity e : entities) {
                        e.offsetRender(sb, grid.getWidth(), 0, 0);
                        e.offsetRender(sb, -grid.getWidth(), 0, 0);
                        e.offsetRender(sb, 0, grid.getHeight(), 0);
                        e.offsetRender(sb, 0, -grid.getHeight(), 0);
                        e.offsetRender(sb, grid.getWidth(), grid.getHeight(), 0);
                        e.offsetRender(sb, -grid.getWidth(), grid.getHeight(), 0);
                        e.offsetRender(sb, grid.getWidth(), -grid.getHeight(), 0);
                        e.offsetRender(sb, -grid.getWidth(), -grid.getHeight(), 0);
                    }
                    
                    

                    for (TextEntity text : dmgTexts) {
                        text.render(dmgFont, sb);

                        if (text.flagForDelete) {
                            dmgTextToRemove.add(text);
                        }
                    }

                    for (TextEntity text : dmgTextToRemove) {
                        dmgTexts.removeValue(text, false);
                    }

                    dmgTextToRemove.clear();

                }
                
                
                break;
                
            default:
                break;
        }
        
        
    }
    
    @Override
    public void update(){
        
        if(sm.getState() == State.PLAYING){
            //entity wrap
            for (Entity e : entities) {
                //if (e.getBody().getPosition().x < grid.getX() / PPM) {
                if(e.getPos().x < grid.getX()){
                    //e.getBody().setTransform(new Vector2(e.getBody().getPosition().x + grid.getWidth() / PPM, e.getBody().getPosition().y), 0);
                    e.setPosition(new Vector2(e.getPos().x + grid.getWidth(), e.getPos().y));
                    
                    if(e.equals(GameScreen.player)){
                        wrapGhostBodies();
                    }
                }
                //if (e.getBody().getPosition().x > (grid.getX() + grid.getWidth()) / PPM) {
                if(e.getPos().x > (grid.getX() + grid.getWidth())){
                    //e.getBody().setTransform(new Vector2(e.getBody().getPosition().x - grid.getWidth() / PPM, e.getBody().getPosition().y), 0);
                    e.setPosition(new Vector2(e.getPos().x - grid.getWidth(), e.getPos().y));
                    
                    if(e.equals(GameScreen.player)){
                        wrapGhostBodies();
                    }
                }
                //if (e.getBody().getPosition().y < grid.getY() / PPM) {
                if(e.getPos().y < grid.getY()){
                    //e.getBody().setTransform(new Vector2(e.getBody().getPosition().x, e.getBody().getPosition().y + grid.getHeight() / PPM), 0);
                    e.setPosition(new Vector2(e.getPos().x, e.getPos().y + grid.getHeight()));
                    
                    if(e.equals(GameScreen.player)){
                        wrapGhostBodies();
                    }
                }
                //if (e.getBody().getPosition().y > (grid.getY() + grid.getHeight()) / PPM) {
                if(e.getPos().y > (grid.getY() + grid.getHeight())){
                    //e.getBody().setTransform(new Vector2(e.getBody().getPosition().x, e.getBody().getPosition().y - grid.getHeight() / PPM), 0);
                    e.setPosition(new Vector2(e.getPos().x, e.getPos().y - grid.getHeight()));
                    
                    if(e.equals(GameScreen.player)){
                        wrapGhostBodies();
                    }
                }
            }

            
            //sprite wrap
            /*
            for (ImageSprite es : sprites) {
                if (es.x < grid.getX()) {
                    es.x += grid.getWidth();
                    es.sprite.setX(es.sprite.getX() + grid.getWidth());
                }
                if (es.x > (grid.getX() + grid.getWidth())) {
                    es.x -= grid.getWidth();
                    es.sprite.setX(es.sprite.getX() - grid.getWidth());
                }
                if (es.y < grid.getY()) {
                    es.y += grid.getHeight();
                    es.sprite.setY(es.sprite.getY() + grid.getHeight());
                }
                if (es.y > (grid.getY() + grid.getHeight())) {
                    es.y -= grid.getHeight();
                    es.sprite.setY(es.sprite.getY() + grid.getHeight());
                }
            }*/
            
            followGhostBodies();
            
        }
        
        super.update();
    }
    
    @Override
    public void updateCamera(OrthoCamera cam){
        
        if(sm.getState() != State.END){
            cam.setPosition(GameScreen.player.getBody().getPosition().x * PPM, GameScreen.player.getBody().getPosition().y * PPM);
        }
        
    }
    
    @Override
    public void begin(){
        super.begin();
        
        bgm1.play();
        
    }
    
    @Override
    public void play(){
        super.play();
        beginFC.setTime(2);
    }
    
    @Override
    public void end(int id, float time){
        bgm1.stop();
        
        super.end(id, time);
    }
    
    @Override
    public void resume(){
        super.resume();
        //create att sensors for ghosts
        for(Body pbody: playerBodies){
            pbody.createFixture(GameScreen.player.getNormAttSensor()).setUserData(GameScreen.player.getNormAttSensor().getData());
        }
        
        //create player ghost bodies
        playerBodies.add(GameScreen.player.getBody());
        
        wrapGhostBodies();
        
        
    }
    
    public void wrapGhostBodies(){
        playerBodyNorth.setTransform(GameScreen.player.getBody().getPosition().x, GameScreen.player.getBody().getPosition().y + grid.getHeight() / PPM, 0);
        playerBodySouth.setTransform(GameScreen.player.getBody().getPosition().x, GameScreen.player.getBody().getPosition().y - grid.getHeight() / PPM, 0);
        playerBodyEast.setTransform(GameScreen.player.getBody().getPosition().x + grid.getWidth() / PPM, GameScreen.player.getBody().getPosition().y, 0);
        playerBodyWest.setTransform(GameScreen.player.getBody().getPosition().x - grid.getWidth() / PPM, GameScreen.player.getBody().getPosition().y, 0);
        playerBodyNE.setTransform(GameScreen.player.getBody().getPosition().x + grid.getWidth() / PPM, GameScreen.player.getBody().getPosition().y + grid.getHeight() / PPM, 0);
        playerBodyNW.setTransform(GameScreen.player.getBody().getPosition().x - grid.getWidth() / PPM, GameScreen.player.getBody().getPosition().y + grid.getHeight() / PPM, 0);
        playerBodySE.setTransform(GameScreen.player.getBody().getPosition().x + grid.getWidth() / PPM, GameScreen.player.getBody().getPosition().y - grid.getHeight() / PPM, 0);
        playerBodySW.setTransform(GameScreen.player.getBody().getPosition().x - grid.getWidth() / PPM, GameScreen.player.getBody().getPosition().y - grid.getHeight() / PPM, 0);
    }
    
    public void followGhostBodies(){
        //player ghost body wrap
            playerBodyNorth.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodySouth.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodyEast.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodyWest.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodyNW.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodyNE.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodySE.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
            playerBodySW.setLinearVelocity(GameScreen.player.getBody().getLinearVelocity());
    }
    
    //used for adjusting playerPos with endPad
    @Override
    public void setPlayerPos(Vector2 pos){
        playerPos = pos;
    }
    
    protected Array<Integer> unavailableGridCells = new Array<Integer>();
    protected Array<Integer> usedGridCells = new Array<Integer>();
    protected Array<EndPiece> endPieces;
    
    public void generateTears(){
        
        unavailableGridCells.add(5);//center cell;
        
        //spawn end pad
        //NOTE: EndPad needs to be created before tears for end-piece creation
        generateEndPad();
        
        
        //*************************************************************
        //spawn tears in random grid cell (not 5, center)
        //2,4,6,8,1,3,7,9
        
        for(int i = 0; i < spawnCount; i++){
            
            //spawn tear in given GridCell
            TearPortal tear = (TearPortal) spawnEntity(new Tear_R_One(createSpawnLocation(), this.id));

            map.getTears().add(tear);
        }
        
        
        //add endPieces to Tear envs
        Array<TearPortal> usedTears = new Array<TearPortal>();
        TearPortal tear;
        int pcount = endPieces.size;
        
        do {
            do {
                tear = map.getTears().random();
            } while (!tear.getUserData().toString().contains("_null")
                    || usedTears.contains(tear, false));

            //TODO: chage this to endPieces.pop(). get rid of pcount
            EndPiece piece = endPieces.get(--pcount);
            tear.addReward(piece);
            usedTears.add(tear);

        } while (pcount > 0);
        
        //*********************************************************
        
    }
    
    public void generateMisc(){
        
        
        
        //*********************************************************
        
        generateDogs();
    }
    
    
    public void generateDogs(){
        //stella & murphy
        
        StellaEntity stella = new StellaEntity(new Vector2(800,500));
        spawnEntity(stella);
        
        MurphyEntity murphy = new MurphyEntity(new Vector2(900,500));
        spawnEntity(murphy);
    }
    
    public void generateEndPad(){
        
        GridCell g = createGridLocation();
        
        EndWarp warp = (EndWarp)spawnEntity(
                new EndWarp_Test(
                        new Vector2(
                                g.getX() + g.getWidth()/2,
                                g.getY() + g.getHeight()/2)));
        
        unavailableGridCells.add(g.getId());
        map.setEndPos(warp.getPos().x, warp.getPos().y);
        
        EnvSub env = warp.getEnv();
        endPieces = env.getEndPad().getPieces();
        spawnCount = endPieces.size;//todo: add to spawnCount, dont replace
        //take neededPieces and place them in seperate EnvNulls
    }
    
  
    
    public void generateSimpleRoom(){
        map.getTears().add(
                (Tear_Room_Simple) spawnEntity(
                        RngRoomManager.generateSimpleTear(this, createSpawnLocation())
                ));
    }
   
    public void generateStatupRoom(){
        
        map.getTears().add(
                (Tear_Room_Statup)spawnEntity(
                        RngRoomManager.generateStatupTear(this, createSpawnLocation())));
    }
    
    public void generateDMLockRoom(){
        map.getTears().add(
                (Tear_Room_DMLock) spawnEntity(
                        RngRoomManager.generateDMLockTear(this, createSpawnLocation())));
    }
    
    public void generateDMLockRoom(int cost){
        map.getTears().add(
                (Tear_Room_DMLock)spawnEntity(
                        RngRoomManager.generateDMLockTear(this, createSpawnLocation(), cost)));
    }
    
    public void generateEndPieceRoom(Pickup piece){
        Tear_Room_EndPiece t2 = (Tear_Room_EndPiece) spawnEntity(
                RngRoomManager.generateEndPieceTear(this, createSpawnLocation()));

        t2.addReward(piece);
        map.getTears().add(t2);
    }
    
    public void generateGlyphRoom(){
        
       Tear_Room_Glyph1 t1 = (Tear_Room_Glyph1)spawnEntity(
               RngRoomManager.generateGlyphTear(this, createSpawnLocation()));
       
       map.getTears().add(t1);
       
       //assign key from Tear_Room_Glyph1 to random null
        EnvRoom_Glyph1_TEST skillRoom1 = (EnvRoom_Glyph1_TEST) t1.getWarpEnv();//this needs to be a key pool (Array<Keys>) to pull from,, not a specific room

        Pickup key = skillRoom1.getKey();
        TearPortal keyTear;
        int scount = 0;

        do {
            keyTear = map.getTears().random();
            scount++;
        } while (!keyTear.getUserData().toString().contains("_null") || scount <= spawnCount * 2);

        keyTear.addReward(key);
    }
    
    
    public void generateRngSimpleReward(){
        map.getTears().add(
                (TearPortal) spawnEntity(
                        RngRoomManager.generateRngReward_Simple(this, createSpawnLocation())
                ));
    }
    
    public void generateRng1LockRoom(){
        RngRoomManager.generateRngReward_1Lock(this);
    }
    
    public GridCell createGridLocation(){
        int tempId;

        do {
            tempId = rng.nextInt(9) + 1;
        } while (unavailableGridCells.contains(tempId, true) || usedGridCells.contains(tempId, true));
        
        //add GridCellId to usedGridCells
        usedGridCells.add(tempId);
        if (usedGridCells.size >= 8) {
            usedGridCells.clear();
        }
            
        return grid.getCellById(tempId);
    }
    
    private final float SPAWN_PADDING = 100f;
    
    //TODO: incorporate createGridLocation() into this method call
    public Vector2 createSpawnLocation(){
        Vector2 v;
        boolean locClear = true;
        GridCell gc = createGridLocation();
        
        do{
            v = new Vector2(
                        gc.getX() + (float) (rng.nextFloat() * gc.getWidth())*0.9f,
                        gc.getY() + (float) (rng.nextFloat() * gc.getHeight()*0.9f));
            
            for(TearPortal t : map.getTears()){
                if(t.getPos().dst(v) < SPAWN_PADDING){
                    locClear = false;
                }
            }
        }while(!locClear);
        
        return v;
    }
}
