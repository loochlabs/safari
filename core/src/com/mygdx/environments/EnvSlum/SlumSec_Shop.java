/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvSlum;

/**
 *
 * @author looch
 */
public class SlumSec_Shop{ /*extends SlumSection{

    private NpcEntity shopNpc;
    private Array<ShopPair> items = new Array<ShopPair>();
    //private ShopPair itemPair;
    
    public SlumSec_Shop(Vector2 pos, float width, float height, EnvSlum env,Coordinate coord) {
        super(pos, width, height, env,coord);
        
        shopNpc = new Npc_SlumGuy1(new Vector2(pos.x + width * 0.5f, pos.y + height*0.85f), 40f, 55f);
        
        items.add(new ShopPair(
                pos.cpy().add(new Vector2(width*0.25f, height*.6f)), 
                50f, 50f, 
                new ShopItem(ItemManager.shopPool.random())));
        items.add(new ShopPair(
                pos.cpy().add(new Vector2(width*0.5f, height*.6f)), 
                50f, 50f, 
                new ShopItem(ItemManager.shopPool.random())));
        items.add(new ShopPair(
                pos.cpy().add(new Vector2(width*0.75f, height*.6f)), 
                50f, 50f, 
                new ShopItem(ItemManager.shopPool.random())));
    }
    
    @Override
    public void init(){
        super.init();
        
        EnvironmentManager.currentEnv.spawnEntity(shopNpc);
        
        for(ShopPair item: items){
            EnvironmentManager.currentEnv.spawnEntity(item);
        }
        
    }
    
    */
}
