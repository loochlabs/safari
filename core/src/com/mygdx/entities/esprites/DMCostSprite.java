/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.entities.esprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import static com.mygdx.game.MainGame.RATIO;

/**
 *
 * @author saynt
 */
public class DMCostSprite extends EntitySprite {

    private final BitmapFont countFont;
    private final int dmcost;

    public DMCostSprite(float x, float y, float width, float height, int dmcost) {
        super(new Vector2(x, y), width, height, "dmlock",
                false, false, false, false, 1f, false, false, true, false);

        this.dmcost = dmcost;

        countFont = new BitmapFont(Gdx.files.internal("fonts/nav-impact.fnt"));
        countFont.setColor(Color.WHITE);
        countFont.setScale(0.65f);

        isprite.setComplete(true);

    }

    public void render(SpriteBatch sb, boolean openAnim) {
        super.render(sb);

        if (openAnim) {
            isprite.sprite.setScale(isprite.sprite.getScaleX() * 0.95f);

            if (isprite.sprite.getWidth() < (35f * RATIO)) {
                dispose();
            }
        } else {
            //render font
            countFont.draw(sb,
                    "" + dmcost + "",
                    pos.x - isprite.sprite.getWidth()/2 - countFont.getBounds(""+dmcost+"").width/2,
                    pos.y - isprite.sprite.getHeight()/2 );
        }

    }

}
