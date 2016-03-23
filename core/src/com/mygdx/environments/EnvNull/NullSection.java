/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvNull;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.entities.DynamicEntities.enemies.EnemyEntity;
import com.mygdx.entities.StaticEntities.StaticEntity;
import com.mygdx.entities.StaticEntities.NullWall;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.environments.EnvNull.NullSection.WallType.WALL;
import com.mygdx.game.MainGame;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.ResourceManager;
import com.mygdx.screen.GameScreen;
import com.mygdx.utilities.Coordinate;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;
import static com.mygdx.utilities.UtilityVars.PPM;

/**
 *
 * @author looch
 */
public class NullSection {
    
    protected Texture bg;
    protected ImageSprite bgSprite;
    protected Vector2 pos;
    protected final float width, height;
    protected final EnvNull env;
    protected final Coordinate coord;
    protected final boolean [] availableSides = { true, true, true, true };
    protected final WallType [] sideTypes = { WALL, WALL, WALL, WALL };
    protected String sideNumber;
    protected Array<EnemyEntity> enemyGroup;
    protected FallSensor fallSensor;
    protected int LAYER_DEPTH = 0;
    public NullSection childSection;
    public NullSection parentSection;
    
    public int getLayerDepth() { return LAYER_DEPTH; }
    public Vector2 getPos() { return pos; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    public Coordinate getCoord() { return coord; }
    public boolean[] getAvailableSides() { return availableSides; }
    public WallType[] getSideTypes() { return sideTypes; }
    
    
    public void setSide(int index, boolean value, WallType type) { 
        availableSides[index] = value; 
        sideTypes[index] = type;
    }
    
    
    
    //TODO: get rid of PIT, PITCONNECTED
    public enum WallType { WALL, CONNECTED, PIT_LOWER, PIT_HIGHER};
    
    
    public NullSection(Vector2 pos, float width, float height, EnvNull env, Coordinate coord, int depth){
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.env = env;
        this.coord = coord;
        this.LAYER_DEPTH = depth;
        
        bg = MainGame.am.get(ResourceManager.NULL_PH);
    }
    
    public void render(SpriteBatch sb){
        if(bgSprite != null){
            bgSprite.sprite.setPosition(bgSprite.x, bgSprite.y);//todo: not needed here, just set in constructor/init()
            bgSprite.step();
            bgSprite.sprite.draw(sb);
        }
        else if(bg != null){
            sb.draw(bg, pos.x, pos.y, width+(10*RATIO), height+(10*RATIO));
        }
    }
    
    
    
    public void init(){
        
        //north wall
        switch(sideTypes[0]){
            case WALL:
                env.toAddEntity(new NullWall(new Vector2(pos.x + width/2, pos.y + height - height*0.035f ),  width/2,  height*0.035f));
                break;
            case PIT_HIGHER:
                
                fallSensor = 
                    new FallSensor(
                            new Vector2(pos.x + width/2, pos.y + height ), 
                            width/2,  
                            15f*RATIO, 
                            childSection, 
                            this);
                env.toAddEntity(fallSensor);
                
                break;
            case PIT_LOWER:
                
                //spawn SectionPad
                env.toAddEntity(new SectionPad(
                        new Vector2(pos.x + width/2, pos.y + height - height*0.15f), 
                        parentSection));
            
                env.toAddEntity(new NullWall(new Vector2(pos.x + width/2, pos.y + height - height*0.035f ),  width/2,  height*0.035f));
                
                sideTypes[0] = WallType.WALL;
                
                break;
            default:
                break;
        }
       
        switch(sideTypes[1]){
            case WALL:
                env.toAddEntity(new NullWall(new Vector2(pos.x + width - width*0.05f, pos.y + height/2), width*0.05f, height/2));
                break;
            case PIT_HIGHER:
                
                fallSensor = 
                    new FallSensor(
                            new Vector2(pos.x + width , pos.y + height/2),
                            15f*RATIO, 
                            height/2, 
                            childSection, 
                            this);
                env.toAddEntity(fallSensor);
                
                break;
            case PIT_LOWER:
                
                //spawn SectionPad
                //spawn SectionPad
                env.toAddEntity(new SectionPad(
                        new Vector2(pos.x + width - width*0.2f, pos.y + height*0.55f), 
                        parentSection));
                
                env.toAddEntity(new NullWall(new Vector2(pos.x + width - width*0.05f, pos.y + height/2), width*0.05f, height/2));
                
                sideTypes[1] = WallType.WALL;
                
                break;
            default:
                break;
        }
        
        
        switch (sideTypes[2]) {
            case WALL:
                env.toAddEntity(new NullWall(new Vector2(pos.x + width / 2, pos.y + height * 0.1f), width / 2, height * 0.1f));
                break;
            case PIT_HIGHER:

                fallSensor = 
                        new FallSensor(
                                new Vector2(pos.x + width / 2, pos.y + height * 0.085f),
                                width * 0.425f,
                                25f * RATIO,
                                childSection,
                                this);
                env.toAddEntity(fallSensor);

                break;
            case PIT_LOWER:

                //spawn SectionPad
                env.toAddEntity(new SectionPad(
                        new Vector2(pos.x + width / 2, pos.y + height * 0.25f), 
                        parentSection));
                
                //env.spawnEntity(new NullWall(new Vector2(pos.x + width / 2, pos.y + height * 0.1f), width / 2, height * 0.1f));
                env.toAddEntity(new NullWall(new Vector2(pos.x + width / 2, pos.y + height * 0.1f), width / 2, height * 0.1f));
                
                sideTypes[2] = WallType.WALL;

                break;
            default:
                break;
        }
        
        
        switch (sideTypes[3]) {
            case WALL:
                env.toAddEntity(new NullWall(new Vector2(pos.x + width * 0.05f, pos.y + height / 2), width * 0.05f, height / 2));
                break;
            case PIT_HIGHER:

                fallSensor = 
                        new FallSensor(
                                new Vector2(pos.x, pos.y + height / 2),
                                15f * RATIO,
                                height / 2,
                                childSection,
                                this);
                env.toAddEntity(fallSensor);

                break;
            case PIT_LOWER:

                //spawn SectionPad
                env.toAddEntity(new SectionPad(
                        new Vector2(pos.x + width * 0.2f, pos.y + height*0.55f), 
                        parentSection));
                
                env.toAddEntity(new NullWall(new Vector2(pos.x + width * 0.05f, pos.y + height / 2), width * 0.05f, height / 2));
                
                sideTypes[3] = WallType.WALL;

                break;
            default:
                break;
        }
        
        setSideNumber();
        
        setTexture();
        
    }
    
    
    private class SectionPad extends StaticEntity {

        public NullSection parentSection;

        public SectionPad(Vector2 pos, NullSection parentSection) {
            super(pos, 40f * RATIO, 40f * RATIO);

            userdata = "action_" + id;
            bd.position.set(pos.x/PPM,pos.y/PPM);
            fd.isSensor = true;
            fd.filter.categoryBits = BIT_WALL;
            fd.filter.maskBits = BIT_PLAYER;
            cshape.setRadius(width / PPM);
            fd.shape = cshape;
            
            this.parentSection = parentSection;

            isprite = new ImageSprite("sectionPad", true);
            isprite.sprite.setBounds(pos.x - width / 2, pos.y - height / 2, width*2, height*2);
            this.flaggedForRenderSort = false;
        }

        @Override
        public void actionEvent() {
            //send player to new section
            System.out.println("@SectionPad warp to section");
            GameScreen.player.clearActionEvents();
            env.fall(parentSection, false);
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

    }
    
    //Generate number corresponding to section id format (ex: 2001, 0110)
    private void setSideNumber(){
        
        sideNumber = "";
        char [] sideChars = new char[4];
        
        for(int i = 0; i < sideTypes.length; i++){
            
            switch (sideTypes[i]){
                case PIT_HIGHER:
                    sideChars[i] = '2';
                    //tempNum += "" + (int)(2*(Math.pow(10.0, 3-i))) + "";
                    break;
                case CONNECTED:
                    sideChars[i] = '1';
                    //tempNum += (int)Math.pow(10.0, 3-i);
                    break;
                default:
                    sideChars[i] = '0';
                    //tempNum += "0";
                    break;
            }
        }
        
        for(char c : sideChars){
            sideNumber += c;
        }
        
        System.out.println("@NullSEction sideNumer " + sideNumber );
    }
    
    public void setTexture(){
        /*
        Sides
        0 - Wall
        1 - Connected
        2 - Pit
        */
        
        //0000
        if(sideNumber.equals("0000")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0000);
        }
        //0001
        else if(sideNumber.equals("0001")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0001);
        }
        //0010
        else if(sideNumber.equals("0010")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0010);
        }
        //0011
        else if(sideNumber.equals("0011")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0011);
        }
        //0012
        else if(sideNumber.equals("0012")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0012);
        }
        //0021
        else if(sideNumber.equals("0021")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0021);
        }
        //0100
        else if(sideNumber.equals("0100")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0100);
        }
        //0101
        else if(sideNumber.equals("0101")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0101);
        }
        //0102
        else if(sideNumber.equals("0102")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0102);
        }
        //0110
        else if(sideNumber.equals("0110")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0110);
        }
        
        //0111
        else if(sideNumber.equals("0111")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0111);
        }
        //0112
        else if(sideNumber.equals("0112")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0112);
        }
        //0120
        else if(sideNumber.equals("0120")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0120);
        }
        //0121
        else if(sideNumber.equals("0121")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0121);
        }
        //0201
        else if(sideNumber.equals("0201")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0201);
        }
        //0210
        else if(sideNumber.equals("0210")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0210);
        }
        //0211
        else if(sideNumber.equals("0211")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_0211);
        }
        //1000
        else if(sideNumber.equals("1000")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1000);
        }
        //1001
        else if(sideNumber.equals("1001")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1001);
        }
        //1002
        else if(sideNumber.equals("1002")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1002);
        }
        
        //1010
        else if(sideNumber.equals("1010")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1010);
        }
        //1011
        else if(sideNumber.equals("1011")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1011);
        }
        //1012
        else if(sideNumber.equals("1012")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1012);
        }
        //1020
        else if(sideNumber.equals("1020")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1020);
        }
        //1021
        else if(sideNumber.equals("1021")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1021);
        }
        //1100
        else if(sideNumber.equals("1100")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1100);
        }
        //1101
        else if(sideNumber.equals("1101")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1101);
        }
        //1102
        else if(sideNumber.equals("1102")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1102);
        }
        //1110
        else if(sideNumber.equals("1110")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1110);
        }
        //1111
        else if(sideNumber.equals("1111")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1111);
        }
        
        //1112
        else if(sideNumber.equals("1112")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1112);
        }
        //1120
        else if(sideNumber.equals("1120")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1120);
        }
        //1121
        else if(sideNumber.equals("1121")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1121);
        }
        //1200
        else if(sideNumber.equals("1200")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1200);
        }
        //1201
        else if(sideNumber.equals("1201")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1201);
        }
        //1210
        else if(sideNumber.equals("1210")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1210);
        }
        //1211
        else if(sideNumber.equals("1211")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_1211);
        }
        //2001
        else if(sideNumber.equals("2001")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2001);
        }
        //2010
        else if(sideNumber.equals("2010")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2010);
        }
        //2011
        else if(sideNumber.equals("2011")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2011);
        }
        
        //2100
        else if(sideNumber.equals("2100")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2100);
        }
        //2101
        else if(sideNumber.equals("2101")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2101);
        }
        //2110
        else if(sideNumber.equals("2110")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2110);
        }
        //2111
        else if(sideNumber.equals("2111")){
            bg = MainGame.am.get(ResourceManager.SECTION_B_2111);
        }
        
    }
    
    
    
    
}
