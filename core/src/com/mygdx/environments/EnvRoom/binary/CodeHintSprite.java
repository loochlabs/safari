/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.environments.EnvRoom.binary;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.entities.ImageSprite;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author looch
 */
public class CodeHintSprite{/* extends ImageSprite{

    private final int CODE_VALUE;
    private BitmapFont font;
    
    public int getCodeValue() { return CODE_VALUE; } 
    
    public CodeHintSprite(int code, float x, float y) {
        super("hintSprite", true, true, false, false, x, y);
        
        this.CODE_VALUE = code;
        
        sprite.setScale(1.0f *RATIO);
        
        font = new BitmapFont(Gdx.files.internal("fonts/nav-font.fnt"));
        font.setColor(Color.WHITE);
        font.setScale(1.0f);
        
        
    }
    
    public CodeHintSprite(CodeHintSprite c, float x, float y){
        this(c.getCodeValue(), x, y);
    }
    
    @Override
    public void render(SpriteBatch sb){
        super.render(sb);
        
        font.draw(sb, "" + CODE_VALUE + "", x + sprite.getWidth()/2, y);
        
    }
    
    @Override
    public void drawOffset(SpriteBatch sb, float x, float y) {
        super.drawOffset(sb, x, y);
        float tempx = this.x + x;
        float tempy = this.y + y;
        font.draw(sb, "" + CODE_VALUE + "", tempx + sprite.getWidth()/2, tempy);
    }
    */
    
}
