/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.entities.StaticEntities.BlankWall;
import com.mygdx.entities.ImageSprite;
import com.mygdx.entities.esprites.DMCostSprite;
import com.mygdx.entities.pickups.Pickup;
import com.mygdx.entities.pickups.Item_DarkMatter;
import com.mygdx.environments.EnvironmentManager;
import com.mygdx.environments.tears.Tear_Room_DMLock.EnvRoom_DMLock;
import static com.mygdx.game.MainGame.RATIO;
import com.mygdx.managers.GameStats;
import com.mygdx.screen.GameScreen;
import static com.mygdx.utilities.UtilityVars.BIT_PLAYER;
import static com.mygdx.utilities.UtilityVars.BIT_WALL;

/**
 *
 * @author saynt
 */
public class Wall_DMLock extends BlankWall{

    protected FixtureDef sens = new FixtureDef();
    public boolean locked = true;
    protected ImageSprite openSprite, closedSprite;
    private DMCostSprite dmCostSprite;
    private boolean costSpriteOpenAnim = false;
    protected Object wallData;
    
    private final Pickup itemLock = new Item_DarkMatter(new Vector2(0,0));
    private int dmcost;
    
    public boolean isLocked() { return locked; }
    
    public Wall_DMLock(Vector2 pos, float w, float h, int dmcost) {
        super(pos, w, h);
        
        wallData = "action_" + id;
        sens.filter.categoryBits = BIT_WALL;
        sens.filter.maskBits = BIT_PLAYER;
        CircleShape c = new CircleShape();
        c.setRadius(4.0f*RATIO);
        sens.shape = c;
        sens.isSensor = true;
        
        this.dmcost = dmcost;
        dmCostSprite = new DMCostSprite(pos.x, pos.y, 100f*RATIO, 100f*RATIO, dmcost);
        costSpriteOpenAnim = false;
        
        closedSprite = new ImageSprite("binWall-closed", false);
        closedSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        
        openSprite = new ImageSprite("binWall-open", false);
        openSprite.sprite.setBounds(pos.x, pos.y, width*2, height*2);
        
        isprite = closedSprite;
        
        
        System.out.println("@Wall_DMLock: dmCost " + dmcost );
        
    }
    
    
    @Override
    public void init(World world){
        super.init(world);
        body.createFixture(sens).setUserData(wallData);
        userdata = wallData;
    }
    
    
    
    @Override
    public void update(){
        super.update();
        
        if(!locked){
            isprite = openSprite;
            
            if(openSprite.isComplete()){
                destroy();
            }
        }
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        if(dmCostSprite != null){
            dmCostSprite.render(sb, costSpriteOpenAnim);
        }
    }
    
    
    @Override
    public void alert(String [] str){
        try {
            if (str[0].equals("begin") && str[1].contains("action_")) {
                if (GameStats.inventory.hasItemAmmount(itemLock, dmcost)) {
                    GameScreen.player.inRangeForAction(this);
                }
            }
            if (str[0].equals("end") && str[1].contains("action_")) {
                GameScreen.player.outRangeForAction(this);
            }
        } catch (IndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void actionEvent(){
        //use item
        this.openDoor();
        EnvRoom_DMLock room = (EnvRoom_DMLock) EnvironmentManager.currentEnv;
        room.unlockWall();
        GameStats.inventory.subItem(itemLock, dmcost);

        GameScreen.player.outRangeForAction(this);
        
    }
    
    //called when key is used within range of lock
    public void openDoor(){
        
        if(locked){//temp code, needs animation
            locked = false;
            
            //TODO: make call to dmCostSprite anim
            costSpriteOpenAnim = true;
            //dmCostSprite = null;
        }
    }
    
    public void destroy(){
        EnvironmentManager.currentEnv.removeEntity(this);
    }
    
    
    
    
}
