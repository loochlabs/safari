/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.utilities;

import com.badlogic.gdx.audio.Music;
import com.mygdx.game.MainGame;
import com.mygdx.managers.SoundManager;

/**
 *
 * @author looch
 */
public class SoundObject_Bgm{

    public Music music;
    public float vol;
    
    
    public SoundObject_Bgm(String path){
        music = MainGame.am.get(path);
    }
    
    public void setVolume(float vol){
        this.vol = vol;
        if(!SoundManager.muted){
            music.setVolume(vol);
        }else
            music.setVolume(0);
    }
    
    public void play(){
        SoundManager.addBgmSound(this);
    }
    
    public void pause(){
        music.pause();
    }
    public void resume(){
        music.play();
    }
    public void stop(){
        music.stop();
        SoundManager.removeBgmSound(this);
    }
    
    public void mute(){
        if(SoundManager.muted){
            music.setVolume(0);
        }else{
            music.setVolume(vol);
        }
    }
    
    
    
}
