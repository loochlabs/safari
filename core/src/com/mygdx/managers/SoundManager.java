/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.mygdx.utilities.SoundObject_Sfx;
import com.mygdx.utilities.SoundObject_Bgm;

/**
 *
 * @author looch
 */
public class SoundManager {
    
    public static float MUSIC_VOL = 1.0f;
    public static float SFX_VOL = 1.0f;
    public static boolean muted = true;
    
    private static Array<SoundObject_Sfx> SFX_SOUNDS = new Array<SoundObject_Sfx>();
    private static Array<SoundObject_Bgm> BGM_SOUNDS = new Array<SoundObject_Bgm>();
    
    //for proximity volume of sfx
    public static void update(Vector2 pos){
        if(pos == null) return;
        
        for (SoundObject_Sfx s : SFX_SOUNDS) {
            s.update(pos);
        }
        
    }
    
    public static long addSfxSound(SoundObject_Sfx obj, boolean loop){
        long id = obj.sound.play();
        //obj.music.play();
        if(!SFX_SOUNDS.contains(obj, false))
            SFX_SOUNDS.add(obj);
        obj.sound.setLooping(id, loop);
        //obj.music.setLooping(loop);
        
        return id;
    }
    
    public static void removeSfxSound(SoundObject_Sfx obj){
        obj.sound.stop(obj.id);
        obj.sound.setVolume(obj.id, 0f);
        //obj.sound.dispose();
        //obj.music.stop();
        //obj.music.dispose();
        SFX_SOUNDS.removeValue(obj, false);
    }
    
    public static void addBgmSound(SoundObject_Bgm obj){
        obj.music.setLooping(true);
        obj.music.play();
        BGM_SOUNDS.add(obj);
        obj.setVolume(MUSIC_VOL);
    }
    
    public static void removeBgmSound(SoundObject_Bgm obj){
        obj.music.stop();
        //obj.music.dispose();
        BGM_SOUNDS.removeValue(obj, false);
    }
    
    public static void setMusicVolume(float vol){
        MUSIC_VOL = vol;
        
        for(SoundObject_Bgm obj: BGM_SOUNDS){
            obj.setVolume(MUSIC_VOL);
        }
    }
    
    public static void setSfxVolume(float vol){
        
        SFX_VOL = vol < 0 ? 0 : vol;
        
        for(SoundObject_Sfx obj: SFX_SOUNDS){
            obj.setVolume(SFX_VOL);
        }
    }
    
    public static void pause(){
        for(SoundObject_Sfx obj: SFX_SOUNDS){
            //obj.sound.pause();
            
        }
        
        for(SoundObject_Bgm obj: BGM_SOUNDS){
            obj.pause();
        }
    }
    
    public static void resume() { 
        for(SoundObject_Sfx obj: SFX_SOUNDS){
            //obj.sound.resume();
        }
        
        for(SoundObject_Bgm obj: BGM_SOUNDS){
            obj.resume();
        }
    }
    
    public static void mute(){
        muted = !muted;
        
        for(SoundObject_Bgm obj: BGM_SOUNDS){
            obj.mute();
        }
        
        for(SoundObject_Sfx obj: SFX_SOUNDS){
            obj.mute();
        }
    }
    public static void clear(){
        for(SoundObject_Bgm obj : BGM_SOUNDS){
            obj.music.stop();
            //obj.music.dispose();
            obj = null;
        }
        BGM_SOUNDS.clear();
        
        for(SoundObject_Sfx obj: SFX_SOUNDS){
            obj.sound.stop();
            //obj.sound.dispose();
            obj = null;
        }
        SFX_SOUNDS.clear();
    }
    
}
