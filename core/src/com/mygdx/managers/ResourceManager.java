/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mygdx.managers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.mygdx.game.MainGame;
import java.util.HashMap;

/**
 *
 * @author looch
 */
public class ResourceManager {
    
    private final HashMap<String, TextureAtlas> atlases;
    private final HashMap<String, Integer> atlasLengths;
    private AssetManager asm;
    
    public ResourceManager(){
        atlases = new HashMap<String,TextureAtlas>();
        atlasLengths = new HashMap<String, Integer>();
    }
    
    
    /*
    
    Description:
        -Called during LoadingScreen on initial launch.
        -Only contains the vital resources for starting first game.
        
        -Call secondaryLoad() for all other resources.
    */
    
    public void primaryLoad(){
        
        //poe
        this.loadAtlas("entities/player/poe/poe-dive.atlas", "poe-dive", 36);
        this.loadAtlas("entities/player/poe/poe-attack.atlas", "poe-attack", 12);
        this.loadAtlas("entities/player/poe/poe-attack-heavy.atlas", "poe-attack-heavy", 27);
        this.loadAtlas("entities/player/poe/poe-attack-light.atlas", "poe-attack-light", 27);
        this.loadAtlas("entities/player/poe/poe-body-light-att.atlas", "poe-body-light-att", 10);
        this.loadAtlas("entities/player/poe/poe-body-heavy-att.atlas", "poe-body-heavy-att", 21);
        this.loadAtlas("entities/player/poe/poe-warp.atlas", "poe-warp", 19);
        this.loadAtlas("entities/player/poe/poe-recov.atlas", "poe-recov", 1);
        this.loadAtlas("entities/player/poe/poe-buff.atlas", "poe-buff", 6);
        this.loadAtlas("entities/player/poe/poe-front.atlas", "poe-front", 40);
        this.loadAtlas("entities/player/poe/poe-back.atlas", "poe-back", 36);
        this.loadAtlas("entities/player/poe/poe-side.atlas", "poe-side", 40);
        this.loadAtlas("entities/player/poe/poe-idle.atlas", "poe-idle", 1);
        this.loadAtlas("entities/player/poe/poeSpectral.atlas", "poeSpectral", 207);
        this.loadAtlas("entities/player/poe/poe-death.atlas", "poe-death", 324);
        this.loadAtlas("entities/player/poe/poe-attack3.atlas", "poe-attack3",1);
        this.loadAtlas("entities/player/poe/poe-attack4.atlas", "poe-attack4",1);
        this.loadAtlas("entities/player/poe/poe-attack-side.atlas", "poe-attack-side",10);
        
        
        //woogie
        this.loadAtlas("entities/player/woogie/woogie-back.atlas", "woogie-back", 40);
        this.loadAtlas("entities/player/woogie/woogie-buff.atlas", "woogie-buff", 20);
        this.loadAtlas("entities/player/woogie/woogie-dive.atlas", "woogie-dive", 32);
        this.loadAtlas("entities/player/woogie/woogie-front.atlas", "woogie-front", 40);
        this.loadAtlas("entities/player/woogie/woogie-heavy.atlas", "woogie-heavy", 10);
        this.loadAtlas("entities/player/woogie/woogie-idle.atlas", "woogie-idle", 1);
        this.loadAtlas("entities/player/woogie/woogie-light.atlas", "woogie-light", 10);
        this.loadAtlas("entities/player/woogie/woogie-recov.atlas", "woogie-recov", 1);
        this.loadAtlas("entities/player/woogie/woogie-side.atlas", "woogie-side", 40);
        this.loadAtlas("entities/player/woogie/woogie-warp.atlas", "woogie-warp", 14);
        
        //lumen
        this.loadAtlas("entities/player/lumen/lumen-back.atlas", "lumen-back", 48);
        this.loadAtlas("entities/player/lumen/lumen-buff.atlas", "lumen-buff", 18);
        this.loadAtlas("entities/player/lumen/lumen-dive.atlas", "lumen-dive", 32);
        this.loadAtlas("entities/player/lumen/lumen-front.atlas", "lumen-front", 48);
        this.loadAtlas("entities/player/lumen/lumen-heavy.atlas", "lumen-heavy", 10);
        this.loadAtlas("entities/player/lumen/lumen-idle.atlas", "lumen-idle", 1);
        this.loadAtlas("entities/player/lumen/lumen-light.atlas", "lumen-light", 10);
        this.loadAtlas("entities/player/lumen/lumen-recov.atlas", "lumen-recov", 1);
        this.loadAtlas("entities/player/lumen/lumen-side.atlas", "lumen-side", 36);
        this.loadAtlas("entities/player/lumen/lumen-warp.atlas", "lumen-warp", 19);
        
        //character starts
        this.loadAtlas("environments/env-start/lumen-start.atlas", "lumen-start", 40);
        this.loadAtlas("environments/env-start/poe-start.atlas", "poe-start", 40);
        this.loadAtlas("environments/env-start/woogie-start.atlas", "woogie-start", 40);
        
        //perm sprites
        this.loadAtlas("combat/perm-sprites/perm1.atlas", "perm1", 10);
        this.loadAtlas("combat/perm-sprites/perm2.atlas", "perm2", 10);
        
        
        //skills
        this.loadAtlas("combat/player-att/impact1.atlas", "impact1",1);
        this.loadAtlas("combat/player-att/impact2.atlas", "impact2",1);
        this.loadAtlas("combat/aoe-circles/aoe-heal.atlas", "aoe-heal",1);
        this.loadAtlas("combat/aoe-circles/aoe-dmg.atlas", "aoe-dmg",10);
        this.loadAtlas("combat/skill-attacks/light-attack-green.atlas", "light-attack-green", 27);
        this.loadAtlas("combat/skill-attacks/heavy-att-green.atlas", "heavy-att-green", 27);
        this.loadAtlas("combat/skill-attacks/light-att-red.atlas", "light-att-red", 27);
        this.loadAtlas("combat/skill-attacks/heavy-att-red.atlas", "heavy-att-red", 27);
        this.loadAtlas("combat/skill-attacks/light-att-yellow.atlas", "light-att-yellow", 28);
        this.loadAtlas("combat/skill-attacks/heavy-att-yellow.atlas", "heavy-att-yellow", 27);
        
        //combos
        this.loadAtlas("combat/combo/ghostjab-combo.atlas", "ghostjab-combo", 14);
        this.loadAtlas("combat/combo/haunt-combo.atlas", "haunt-combo", 17);
        this.loadAtlas("combat/combo/lightningrod-combo.atlas", "lightningrod-combo", 59);
        this.loadAtlas("combat/combo/lightningbolt.atlas", "lightningbolt", 15);
        
        //EnvNull
        this.loadAtlas("entities/enemies/EndNull/null_end_spawn.atlas", "null_end_spawn",21);
        this.loadAtlas("entities/enemies/EndNull/null_end_idle.atlas", "null_end_idle",23);
        this.loadAtlas("entities/enemies/EndNull/null_end_death.atlas", "null_end_death",28);
        this.loadAtlas("environments/NullEnv/sectionPad.atlas", "sectionPad",15);
        
        //enemies
        this.loadAtlas("entities/enemies/cyst-idle.atlas", "cyst-idle",20);
        this.loadAtlas("entities/enemies/peeker-idle.atlas", "peeker-idle",1);
        this.loadAtlas("entities/enemies/peeker-prep.atlas", "peeker-prep",22);
        this.loadAtlas("entities/enemies/sloober-prep.atlas", "sloober-prep",18);
        this.loadAtlas("entities/enemies/sloober-move.atlas", "sloober-move",28);
        this.loadAtlas("entities/enemies/sloober-att.atlas", "sloober-att",1);
        this.loadAtlas("entities/enemies/sloober-dmg.atlas", "sloober-dmg",1);
        
        //traps
        this.loadAtlas("entities/enemies/void-trap/void-trap-idle.atlas", "void-trap-idle",21);
        this.loadAtlas("entities/enemies/void-trap/void-trap-prep.atlas", "void-trap-prep",1);
        this.loadAtlas("entities/enemies/void-trap/void-trap-arm.atlas", "void-trap-arm",7);
        this.loadAtlas("entities/enemies/void-trap/void-trap-spawn.atlas", "void-trap-spawn",12);
        
        //goober
        this.loadAtlas("entities/enemies/goober/goober_move.atlas", "goober_move", 26);
        this.loadAtlas("entities/enemies/goober/goober_prep.atlas", "goober_prep", 16);
        this.loadAtlas("entities/enemies/goober/goober-prep2.atlas", "goober-prep2", 12);
        this.loadAtlas("entities/enemies/goober/goober-attack2.atlas", "goober-attack2", 1);
        this.loadAtlas("entities/enemies/goober/goober-dmg.atlas", "goober-dmg", 1);
        
        
        //spray
        this.loadAtlas("combat/spray/spray1.atlas", "spray1",9);
        
        //proj death
        this.loadAtlas("combat/proj-death.atlas", "proj-death",12);
        
        //environments
        //start
        this.loadAtlas("environments/env-start/start-player.atlas", "start-player",1);
        //null
        this.loadAtlas("environments/NullEnv/impact/player-impact.atlas", "player-impact",14);
        this.loadAtlas("entities/sprites/kill-text.atlas", "kill-text",94);
        this.loadAtlas("environments/NullEnv/null-bg-rocks.atlas", "null-bg-rocks",1);
        //room
        this.loadAtlas("environments/RoomEnv/wall1/wall1_open.atlas", "wall1_open",214);
        this.loadAtlas("environments/RoomEnv/wall1/wall1_closed.atlas", "wall1_closed",1);
        this.loadAtlas("environments/RoomEnv/binary/binWall-closed.atlas", "binWall-closed",1);
        this.loadAtlas("environments/RoomEnv/binary/binWall-open.atlas", "binWall-open",56);
        this.loadAtlas("environments/RoomEnv/dmlock.atlas", "dmlock",1);
        //EnvMan
        this.loadAtlas("environments/EnvMan/start-wall-lock.atlas", "start-wall-lock",107);
        this.loadAtlas("environments/EnvMan/start-wall-closed.atlas", "start-wall-closed",1);
        this.loadAtlas("environments/EnvMan/end-cont.atlas", "end-cont",217);
        //void
        this.loadAtlas("environments/EnvVoid/end-void-trans.atlas", "end-void-trans",29);
        //tear
        this.loadAtlas("entities/tears/tear-open2.atlas", "tear-open2",39);
        this.loadAtlas("entities/tears/damaged2.atlas", "damaged2",1);
        //EnvSub
        this.loadAtlas("environments/EnvSub/endPad-mist.atlas", "endPad-mist",1);
        this.loadAtlas("environments/EnvSub/bg-sub-piece1.atlas", "bg-sub-piece1",1);
        this.loadAtlas("environments/EnvSub/sub-center-piece1.atlas", "sub-center-piece1",1);
        this.loadAtlas("environments/EnvSub/web.atlas", "web",28);
        
        //dogs
        this.loadAtlas("entities/dogs/stella-move.atlas", "stella-move",1);
        this.loadAtlas("entities/dogs/red-alert.atlas", "red-alert",1);
        this.loadAtlas("entities/dogs/murphy-move.atlas", "murphy-move",1);
        this.loadAtlas("entities/dogs/murphy-alert.atlas", "murphy-alert",1);
        this.loadAtlas("entities/dogs/stella-idle.atlas", "stella-idle", 30);
        this.loadAtlas("entities/dogs/murphy-idle.atlas", "murphy-idle", 90);
        this.loadAtlas("entities/dogs/stella-init.atlas", "stella-init", 9);
        this.loadAtlas("entities/dogs/murphy-init.atlas", "murphy-init", 9);
        
        
        
        //sprites
        this.loadAtlas("entities/sprites/decom.atlas", "decom",1);
        this.loadAtlas("entities/sprites/decom2.atlas", "decom2",30);
        this.loadAtlas("entities/sprites/leave1.atlas", "leave1",20);
        this.loadAtlas("entities/sprites/leave2.atlas", "leave2",20);
        this.loadAtlas("entities/sprites/decom3.atlas", "decom3",20);
        
        //bg sprites
        this.loadAtlas("environments/EnvVoid/bg-sprites/bg-piece8.atlas", "bg-piece8",1);
        
        //gui
        this.loadAtlas("gui/hud1/hud-skills/light-rot.atlas", "light-rot",15);
        this.loadAtlas("gui/hud1/hud-skills/light-rotSlow.atlas", "light-rotSlow",30);
        this.loadAtlas("gui/hud1/hud-skills/heavy-rot.atlas", "heavy-rot",15);
        this.loadAtlas("gui/hud1/hud-skills/heavy-rotSlow.atlas", "heavy-rotSlow",30);
        this.loadAtlas("gui/hud1/hud-skills/special-rot.atlas", "special-rot",15);
        this.loadAtlas("gui/hud1/hud-skills/special-rotSlow.atlas", "special-rotSlow",30);
        this.loadAtlas("gui/hud1/hud-skills/passive-rot.atlas", "passive-rot",15);
        this.loadAtlas("gui/hud1/hud-skills/passive-rotSlow.atlas", "passive-rotSlow",30);
        this.loadAtlas("gui/hud1/hud-skills/skill-empty.atlas", "skill-empty",1);
        this.loadAtlas("gui/hud1/hud-dm-idle.atlas", "hud-dm-idle",20);
        
        //skill-overlay
        this.loadAtlas("gui/hud2/skill-overlay-cursor.atlas", "skill-overlay-cursor",1);
        this.loadAtlas("gui/hud2/hud-skill-light.atlas", "hud-skill-light",1);
        this.loadAtlas("gui/hud2/hud-skill-heavy.atlas", "hud-skill-heavy",1);
        this.loadAtlas("gui/hud2/hud-skill-special.atlas", "hud-skill-special",1);
        this.loadAtlas("gui/hud2/hud-skill-passive.atlas", "hud-skill-passive",1);
        this.loadAtlas("gui/hud2/hud-skill-defense.atlas", "hud-skill-defense",1);
        
        
        //**********************************
        //ASSET MANAGER
        asm = MainGame.am;
        
        
        //menus
        asm.load(MENU_NEW_GAME, Texture.class);
        asm.load(MENU_RESUME, Texture.class);
        asm.load(MENU_OPTIONS, Texture.class);
        asm.load(MENU_HIGHLIGHT, Texture.class);
        asm.load(MENU_BG,  Texture.class);
        asm.load(MENU_LOGO,  Texture.class);
        
        //poe
        asm.load(POE_IDLE, Texture.class);
        
        //enemies
        asm.load(GOOBER_IDLE, Texture.class);
        asm.load(GOOBER_ATTACK, Texture.class);
        asm.load(PEEKER_MAIN, Texture.class);
        
        //projectiles
        asm.load(PROJ_EN_1, Texture.class);
        
        //breakable objects
        asm.load(CYST_BLUE, Texture.class);
        
        //npcs
        asm.load(NPC_SLUMGUY1, Texture.class);
        asm.load(NPC_SLUMGUY2, Texture.class);
        
        asm.load(STELLA_PH, Texture.class);
        asm.load(STELLA_ALERT, Texture.class);
        asm.load(MURPHY_IDLE, Texture.class);
        asm.load(MURPHY_ALERT, Texture.class);
        
        //env-start
        asm.load(START_BG_PH, Texture.class);
        asm.load(START_INTRO_WHITE_BG, Texture.class);
        asm.load(START_BLACK_BG, Texture.class);
        
        //null
        asm.load(NULL_BG1, Texture.class);
        asm.load(NULL_PH, Texture.class);
        asm.load(NULLWALL_PH, Texture.class);
        asm.load(NULL_WALL1, Texture.class);
        asm.load(WALL_START, Texture.class);
        
        //blue sections
        asm.load(SECTION_B_0000, Texture.class);
        asm.load(SECTION_B_0001, Texture.class);
        asm.load(SECTION_B_0010, Texture.class);
        asm.load(SECTION_B_0011, Texture.class);
        asm.load(SECTION_B_0012, Texture.class);
        
        asm.load(SECTION_B_0021, Texture.class);
        asm.load(SECTION_B_0100, Texture.class);
        asm.load(SECTION_B_0101, Texture.class);
        asm.load(SECTION_B_0102, Texture.class);
        asm.load(SECTION_B_0110, Texture.class);
        
        asm.load(SECTION_B_0111, Texture.class);
        asm.load(SECTION_B_0112, Texture.class);
        asm.load(SECTION_B_0120, Texture.class);
        asm.load(SECTION_B_0121, Texture.class);
        asm.load(SECTION_B_0201, Texture.class);
        
        asm.load(SECTION_B_0210, Texture.class);
        asm.load(SECTION_B_0211, Texture.class);
        asm.load(SECTION_B_1000, Texture.class);
        asm.load(SECTION_B_1001, Texture.class);
        asm.load(SECTION_B_1002, Texture.class);
        
        asm.load(SECTION_B_1010, Texture.class);
        asm.load(SECTION_B_1011, Texture.class);
        asm.load(SECTION_B_1012, Texture.class);
        asm.load(SECTION_B_1020, Texture.class);
        asm.load(SECTION_B_1021, Texture.class);
        
        asm.load(SECTION_B_1100, Texture.class);
        asm.load(SECTION_B_1101, Texture.class);
        asm.load(SECTION_B_1102, Texture.class);
        asm.load(SECTION_B_1110, Texture.class);
        asm.load(SECTION_B_1111, Texture.class);

        asm.load(SECTION_B_1112, Texture.class);
        asm.load(SECTION_B_1120, Texture.class);
        asm.load(SECTION_B_1121, Texture.class);
        asm.load(SECTION_B_1200, Texture.class);
        asm.load(SECTION_B_1201, Texture.class);

        asm.load(SECTION_B_1210, Texture.class);
        asm.load(SECTION_B_1211, Texture.class);
        asm.load(SECTION_B_2010, Texture.class);
        asm.load(SECTION_B_2001, Texture.class);
        asm.load(SECTION_B_2011, Texture.class);

        asm.load(SECTION_B_2100, Texture.class);
        asm.load(SECTION_B_2101, Texture.class);
        asm.load(SECTION_B_2110, Texture.class);
        asm.load(SECTION_B_2111, Texture.class);
        
        //orange sections
        asm.load(NULL_SECTION_1000, Texture.class);
        asm.load(NULL_SECTION_0100, Texture.class);
        asm.load(NULL_SECTION_0010, Texture.class);
        asm.load(NULL_SECTION_0001, Texture.class);
        asm.load(NULL_SECTION_1200, Texture.class);
        asm.load(NULL_SECTION_1020, Texture.class);
        asm.load(NULL_SECTION_1002, Texture.class);
        asm.load(NULL_SECTION_2100, Texture.class);
        asm.load(NULL_SECTION_0120, Texture.class);
        asm.load(NULL_SECTION_0102, Texture.class);
        asm.load(NULL_SECTION_2010, Texture.class);
        asm.load(NULL_SECTION_0210, Texture.class);
        asm.load(NULL_SECTION_0012, Texture.class);
        asm.load(NULL_SECTION_2001, Texture.class);
        asm.load(NULL_SECTION_0201, Texture.class);
        asm.load(NULL_SECTION_0021, Texture.class);
        asm.load(NULL_SECTION_1100, Texture.class);
        asm.load(NULL_SECTION_1010, Texture.class);
        asm.load(NULL_SECTION_1001, Texture.class);
        asm.load(NULL_SECTION_0110, Texture.class);
        asm.load(NULL_SECTION_0101, Texture.class);
        asm.load(NULL_SECTION_0011, Texture.class);
        asm.load(NULL_SECTION_1120, Texture.class);
        asm.load(NULL_SECTION_1102, Texture.class);
        asm.load(NULL_SECTION_1210, Texture.class);
        asm.load(NULL_SECTION_1012, Texture.class);
        asm.load(NULL_SECTION_1201, Texture.class);
        asm.load(NULL_SECTION_1021, Texture.class);
        asm.load(NULL_SECTION_2110, Texture.class);
        asm.load(NULL_SECTION_0112, Texture.class);
        asm.load(NULL_SECTION_2101, Texture.class);
        asm.load(NULL_SECTION_0121, Texture.class);
        asm.load(NULL_SECTION_2011, Texture.class);
        asm.load(NULL_SECTION_0211, Texture.class);
        asm.load(NULL_SECTION_0111, Texture.class);
        asm.load(NULL_SECTION_1011, Texture.class);
        asm.load(NULL_SECTION_1101, Texture.class);
        asm.load(NULL_SECTION_1110, Texture.class);
        
        //void
        asm.load(VOID_BG, Texture.class);//todo:remove one of these VOID_BG/PH
        asm.load(VOID_BG_PH, Texture.class);
        asm.load(VOID_BG_B, Texture.class);
        asm.load(VOID_BG_A, Texture.class);
        asm.load(VOID_BG_FILTER, Texture.class);
        asm.load(VOID_MAP, Texture.class);
        asm.load(MAP_MARKER1, Texture.class);
        asm.load(MAP_MARKER_YELLOW, Texture.class);
        asm.load(MAP_STARTER, Texture.class);
        asm.load(MAP_END, Texture.class);
        //EnvSub-end
        asm.load(ENDPIECE_A_1, Texture.class);
        asm.load(ENDPIECE_A_2, Texture.class);
        asm.load(ENDPIECE_A_3, Texture.class);
        asm.load(ENDPIECE_A_4, Texture.class);
        asm.load(ENDSECTION_A_1, Texture.class);
        asm.load(ENDSECTION_A_1_FILL, Texture.class);
        asm.load(ENDSECTION_A_2, Texture.class);
        asm.load(ENDSECTION_A_2_FILL, Texture.class);
        asm.load(ENDSECTION_A_3, Texture.class);
        asm.load(ENDSECTION_A_3_FILL, Texture.class);
        asm.load(ENDSECTION_A_4, Texture.class);
        asm.load(ENDSECTION_A_4_FILL, Texture.class);
        asm.load(ENDSECTION_C1, Texture.class);
        asm.load(ENDSECTION_C1_FILL, Texture.class);
        asm.load(ENDPIECE_C1, Texture.class);
        asm.load(ENDPAD_COMPLETE, Texture.class);
        
         //EnvSub-end
        asm.load(ENVSUB_END_BG, Texture.class);
        asm.load(ENVSUB_END_FG, Texture.class);
        
        //EnvMan
        asm.load(ENVMAN_INTRO_BG, Texture.class);
        
        //EnvRoom
        asm.load(ROOM_BG1, Texture.class);
        asm.load(ROOM_SIMPLE_BG1, Texture.class);
        asm.load(ROOM_GLYPHWALL_1, Texture.class);
        asm.load(ROOM_BIN_BG, Texture.class);
        asm.load(ROOM_ARC, Texture.class);
        asm.load(ROOM_BIN_WALL, Texture.class);
        //items
        
        asm.load(STAT_ITEM_LIFE, Texture.class);
        asm.load(STAT_ITEM_DMG, Texture.class);
        asm.load(STAT_ITEM_ENERGY, Texture.class);
        asm.load(STAT_ITEM_SPEED, Texture.class);
        
        //soul
        asm.load(ITEM_SOUL_POE, Texture.class);
        
        asm.load(GLYPH_ONE, Texture.class);
        asm.load(ITEM_DM1, Texture.class);
        asm.load(ITEM_MATTER_GREEN, Texture.class);
        asm.load(ITEM_PED, Texture.class);
        asm.load(ITEM_LIFE, Texture.class);
        
        
        //gui
        asm.load(HUD1_HP_BG, Texture.class);
        asm.load(HUD1_HP_FG, Texture.class);
        asm.load(HUD1_EXP_FG, Texture.class);
        asm.load(OVERLAY_GRID, Texture.class);
        asm.load(ICON_HP, Texture.class);
        asm.load(ICON_ENERGY, Texture.class);
        asm.load(HUD_ENERGY_FILL, Texture.class);
        asm.load(HUD_ENERGY_EMPTY, Texture.class);
        
        //skill hud
        asm.load(HUD_SKILL_OVERLAY, Texture.class);
        
        //soul hud
        asm.load(SOUL_LOGO_POE, Texture.class);
        asm.load(HUD_SOUL_EMPTY, Texture.class);
        
        
        //pad icons
        asm.load(GUI_PAD_A, Texture.class);
        asm.load(GUI_PAD_B, Texture.class);
        asm.load(GUI_PAD_X, Texture.class);
        asm.load(GUI_PAD_Y, Texture.class);
        asm.load(GUI_PAD_RB, Texture.class);
        asm.load(GUI_DASH, Texture.class);
        //pause menu
        asm.load(PAUSE_BG, Texture.class);
        asm.load(PAUSE_CURSOR, Texture.class);
        asm.load(PAUSE_OPTIONS, Texture.class);
        asm.load(PAUSE_SOUND, Texture.class);
        //desc
        asm.load(DESC_BG, Texture.class);
        
        //combo
        /*
        asm.load(COMBO_BASE, Texture.class);
        asm.load(COMBO_CENTER, Texture.class);
        asm.load(COMBO_CURSOR, Texture.class);
        asm.load(COMBO_YELLOW, Texture.class);
        asm.load(COMBO_ORANGE, Texture.class);
        asm.load(COMBO_RED, Texture.class);
        */
        
        //COMBO
        asm.load(COMBO_ICON_LIGHT, Texture.class);
        asm.load(COMBO_ICON_HEAVY, Texture.class);
        asm.load(COMBO_ICON_SPECIAL, Texture.class);
        
        
        //skills
        asm.load(SKILL_PAD_LIGHT, Texture.class);
        asm.load(SKILL_PAD_HEAVY, Texture.class);
        asm.load(SKILL_PAD_SPECIAL, Texture.class);
        asm.load(SKILL_PAD_PASSIVE, Texture.class);
        
        
        asm.load(SKILL_RED, Texture.class);
        asm.load(SKILL_BLANK, Texture.class);
        asm.load(SKILL_DASH, Texture.class);
        asm.load(SKILL_GHOSTJAB, Texture.class);
        asm.load(SKILL_POWERPLEASE, Texture.class);
        asm.load(SKILL_NRG, Texture.class);
        asm.load(SKILL_MOMMASTOUCH, Texture.class);
        asm.load(SKILL_MOMMASFURY, Texture.class);
        asm.load(SKILL_DASHBOLT, Texture.class);
        asm.load(SKILL_ONETWO, Texture.class);
        asm.load(SKILL_CANTTOUCH, Texture.class);
        asm.load(SKILL_HAUNTHASTE, Texture.class);
        asm.load(SKILL_MUCHHASTE, Texture.class);
        asm.load(SKILL_WARPIT, Texture.class);
        asm.load(SKILL_PROGNOSIS, Texture.class);
        asm.load(SKILL_HEAVYHANDED, Texture.class);
        asm.load(SKILL_TAINTEDTORTURE, Texture.class);
        asm.load(SKILL_CRACKOFLIGHTNING, Texture.class);
        asm.load(SKILL_CRACKOFTHUNDER, Texture.class);
        
        //screens
        //game over
        asm.load(GO_TEXT, Texture.class);
        
        
        //**************************
        //  SOUND
        //************************
        
        //void bgm
        asm.load(BGM_VOID_1, Music.class);
        
        //void sfx
        asm.load(SFX_WARP_IN, Sound.class);
        
        //null bgm
        //TODO: Only keep one here, move other 2 o secondaryLoad()
        asm.load(BGM_NULL_1, Music.class);
        asm.load(BGM_NULL_2, Music.class);
        asm.load(BGM_NULL_3, Music.class);
        asm.load(BGM_NULL_END, Music.class);
        
        //room bgm
        //TODO: move one to secondaryLoad
        asm.load(BGM_ROOM_1, Music.class);
        asm.load(BGM_ROOM_2, Music.class);
        
       
        //SFX
        //void
        asm.load(SFX_COMPLETE_ENDSECTIONS, Sound.class);
        asm.load(SFX_ENDPIECE_FILL, Sound.class);
        
        //null
        asm.load(SFX_NULL_IMPACT, Sound.class);
        
        //player
        asm.load(SFX_DASH, Sound.class);
        //poe
        asm.load(POE_YELL_1, Sound.class);
        asm.load(POE_YELL_2, Sound.class);
        asm.load(POE_YELL_3, Sound.class);
        asm.load(SFX_POE_DMG, Sound.class);
        
        //enemies
        asm.load(SFX_GOOBER_MOVE, Sound.class);
        
        //null
        asm.load(SFX_NULL_END_IDLE, Sound.class);
        asm.load(SFX_NULL_END_DEATH, Sound.class);
        
        
        //pickups
        asm.load(SFX_PICKUP, Sound.class);
        asm.load(SFX_PICKUP_SKILL, Sound.class);
        asm.load(SFX_PICKUP_USE, Sound.class);
        
        //impacts
        asm.load(SFX_IMPACT_1, Sound.class);
        asm.load(SFX_IMPACT_2, Sound.class);
        asm.load(SFX_DEATH_1, Sound.class);
        
        //powerups
        asm.load(SFX_POWER_1, Sound.class);
    }
    
    public void secondaryLoad(){
        
        //perm sprites
        this.loadAtlas("combat/perm-sprites/perm-warpit.atlas", "perm-warpit", 1);
        
        //enemies
        this.loadAtlas("entities/en-death2.atlas", "en-death2", 27);
        this.loadAtlas("entities/enemies/krak-baby.atlas", "krak-baby",42);
        this.loadAtlas("entities/enemies/murgle-front.atlas", "murgle-front",35);
        this.loadAtlas("entities/enemies/cyst-idle.atlas", "cyst-idle",20);
        this.loadAtlas("entities/enemies/knowit-move.atlas", "knowit-move",40);
        this.loadAtlas("entities/enemies/knowit-prep.atlas", "knowit-prep",20);
        
        
        //darkling
        this.loadAtlas("entities/enemies/darkling/darkling-move.atlas", "darkling-move", 28);
        this.loadAtlas("entities/enemies/darkling/darkling-prep.atlas", "darkling-prep", 18);
        this.loadAtlas("entities/enemies/darkling/darkling-att.atlas", "darkling-att", 1);
        
        //worm
        this.loadAtlas("entities/enemies/worm/worm-move.atlas", "worm-move", 30);
        this.loadAtlas("entities/enemies/worm/worm-prep.atlas", "worm-prep", 10);
        this.loadAtlas("entities/enemies/worm/worm-att.atlas", "worm-att", 1);
        
        //spectral
        this.loadAtlas("environments/envSpectral/run-text.atlas", "run-text",77);
        this.loadAtlas("environments/envSpectral/spec-dm.atlas", "spec-dm",1);
        this.loadAtlas("environments/envSpectral/dm_sprite2.atlas", "dm_sprite2",24);
        this.loadAtlas("environments/envSpectral/endSpectralSprite.atlas", "endSpectralSprite",119);
        
        //env room
        this.loadAtlas("environments/RoomEnv/binary/hintSprite.atlas", "hintSprite",30);
        
        
        
        //boss null
        this.loadAtlas("environments/boss-null/section1.atlas", "section1", 1);
        
        //sprites
        this.loadAtlas("entities/sprites/mitb-shadow.atlas", "mitb-shadow",1);
        this.loadAtlas("entities/sprites/man-crouch.atlas", "man-crouch",1);
        this.loadAtlas("entities/sprites/man-warp.atlas", "man-warp",12);
        
        
        
        
        
        //enemies
        asm.load(ENEMY_PH, Texture.class);
        asm.load(EN_REDMATTER, Texture.class);
        asm.load(MURGLE_MAIN, Texture.class);
        asm.load(MURGIE_MAIN, Texture.class);
        
        
        //breakable objects
        asm.load(CYST_PURPLE, Texture.class);
        asm.load(CYST_GREEN, Texture.class);
        asm.load(CYST_BIG, Texture.class);
        
        
        
 
    
        asm.load(NULL_SECTION_A_1000, Texture.class); 
        asm.load(NULL_SECTION_A_0100, Texture.class); 
        asm.load(NULL_SECTION_A_0010, Texture.class); 
        asm.load(NULL_SECTION_A_0001, Texture.class); 
        asm.load(NULL_SECTION_A_1100, Texture.class); 
        asm.load(NULL_SECTION_A_1010, Texture.class); 
        asm.load(NULL_SECTION_A_1001, Texture.class); 
        asm.load(NULL_SECTION_A_0101, Texture.class); 
        asm.load(NULL_SECTION_A_0011, Texture.class); 
        asm.load(NULL_SECTION_A_1110, Texture.class); 
        asm.load(NULL_SECTION_A_1101, Texture.class); 
        asm.load(NULL_SECTION_A_1011, Texture.class); 
        asm.load(NULL_SECTION_A_0111, Texture.class); 
        asm.load(NULL_SECTION_A_1111, Texture.class); 
        asm.load(NULL_SECTION_A_0110, Texture.class);
        
        
        asm.load(KRAKEN_FG, Texture.class);
        
        //spectral 
        
        asm.load(SP_SECTION_PH, Texture.class);
        asm.load(SP_SECTION_0001, Texture.class);
        asm.load(SP_SECTION_0010, Texture.class);
        asm.load(SP_SECTION_0011, Texture.class); 
        asm.load(SP_SECTION_0100, Texture.class);
        asm.load(SP_SECTION_0101, Texture.class);
        asm.load(SP_SECTION_0110, Texture.class); 
        asm.load(SP_SECTION_0111, Texture.class); 
        asm.load(SP_SECTION_1000, Texture.class);
        asm.load(SP_SECTION_1001, Texture.class); 
        asm.load(SP_SECTION_1010, Texture.class); 
        asm.load(SP_SECTION_1011, Texture.class);
        asm.load(SP_SECTION_1100, Texture.class);
        asm.load(SP_SECTION_1101, Texture.class); 
        asm.load(SP_SECTION_1110, Texture.class);
        
        
       
        
        
        asm.load(ROOM_CODEPANEL0, Texture.class);
        asm.load(ROOM_CODEPANEL1, Texture.class);
        asm.load(ROOM_CODEPANEL_SWITCH, Texture.class);
        asm.load(ROOM_CODESWITCH_0, Texture.class);
        asm.load(ROOM_CODESWITCH_1, Texture.class);
        asm.load(ROOM_CODEMON, Texture.class);
        asm.load(ROOM_CODENUM_BLANK, Texture.class);
        asm.load(ROOM_CODENUM_0, Texture.class);
        asm.load(ROOM_CODENUM_1, Texture.class);
        asm.load(ROOM_CODENUM_2, Texture.class);
        asm.load(ROOM_CODENUM_3, Texture.class);
        asm.load(ROOM_CODENUM_4, Texture.class);
        asm.load(ROOM_CODENUM_5, Texture.class);
        asm.load(ROOM_CODENUM_6, Texture.class);
        asm.load(ROOM_CODENUM_7, Texture.class);
        asm.load(ROOM_CODENUM_8, Texture.class);
        asm.load(ROOM_CODENUM_9, Texture.class);
        
        
        //evSlum
        
        asm.load(SLUM_SECTION_BG, Texture.class);
        asm.load(SLUM_SECTION_WALL, Texture.class);
        asm.load(SLUM_SECTION_WALL_2, Texture.class);
        
        asm.load(BOSS_ICON, Texture.class);
        
        

     
        //**************************
        //  SOUND
        //************************
        
        
        //spectral bgm
        asm.load(BGM_SPECTRAL_1, Music.class);
        
        
        //slums bgm
        asm.load(BGM_SLUM_1, Music.class);
        
        
    }
    
    public void loadAtlas(String path, String key){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path));
        atlases.put(key, atlas);
    }
    
    public void loadAtlas(String path, String key, int length){
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal(path));
        atlases.put(key,atlas);
        atlasLengths.put(key, length);
    }
    
    public TextureAtlas getAtlas(String key){
        return atlases.get(key);
    }
    
    public int getAtlasLength(String key){
        return atlasLengths.get(key);
    }
    
    public void disposeAtlas(String key){
        TextureAtlas atlas = atlases.get(key);
        if(atlas != null) atlas.dispose();
    }
    
    
    //*********************************
    //      TEXTURE PATHS
    //*********************************
    //menus
    public static String MENU_NEW_GAME = "menus/button-newGame.png";
    public static String MENU_RESUME = "menus/button-resume.png";
    public static String MENU_OPTIONS = "menus/button-options.png";
    public static String MENU_HIGHLIGHT = "menus/menu-highlight.png";
    public static String MENU_BG = "menus/button-bg.png";
    public static String MENU_LOGO = "menus/nav-alpha.png";
    
    //player textures
    //poe
    public static String POE_IDLE = "entities/player/poe/poe-idle.png";
    
    //enemy textures
    public static String ENEMY_PH = "entities/enemies/enemy1.png";
    public static String EN_REDMATTER = "entities/enemies/en-RedMatter1.png";
    public static String GOOBER_IDLE = "entities/enemies/goober_1.png";
    public static String GOOBER_ATTACK = "entities/enemies/goober-attack_1.png";
    public static String KRAK_EYE_OPEN = "entities/enemies/eye-open.png";
    public static String KRAK_EYE_CLOSED = "entities/enemies/eye-closed.png";
    public static String POLLOP_CLOSED = "entities/enemies/pollop-closed_1.png";
    public static String POLLOP_OPEN = "entities/enemies/pollop-open_1.png";
    public static String MURGLE_MAIN = "entities/enemies/murgle-main.png";
    public static String MURGIE_MAIN = "entities/enemies/murgie-main.png";
    public static String PEEKER_MAIN = "entities/enemies/peeker-main.png";
    
    //projectiles
    public static String PROJ_EN_1 = "entities/projectiles/en-bullet.png";
    
    //cysts
    public static String CYST_BLUE = "entities/enemies/en-cyst1.png";
    public static String CYST_PURPLE = "entities/enemies/en-cyst-purple.png";
    public static String CYST_GREEN = "entities/enemies/en-cyst-green.png";
    public static String CYST_BIG = "entities/enemies/en-bigcyst.png";
    
    //npcs
    public static String NPC_SLUMGUY1 = "entities/npcs/slum-guy1.png";
    public static String NPC_SLUMGUY2 = "entities/npcs/slum-guy2.png";
    
    //stella & murphy
    public static String STELLA_PH = "entities/stella1.png";
    public static String STELLA_ALERT = "entities/dogs/stella-alert2.png";
    public static String MURPHY_IDLE = "entities/muphy.png";
    public static String MURPHY_ALERT = "entities/dogs/murphy-alert.png";
    
    //Environments
    //null
    public static String NULL_BG1 = "environments/NullEnv/null-bg2.png";
    public static String NULL_PH = "environments/nullPH.png";//todo:old
    public static String NULLWALL_PH = "environments/NullEnv/northWallPH.png";
    public static String NULL_WALL1 = "environments/NullEnv/null-wall.png";
    public static String WALL_START = "environments/RoomEnv/wall-start.png";
    
    //orange sections
    public static String NULL_SECTION_1000 = "environments/NullEnv/sections/null-section-1000.png";
    public static String NULL_SECTION_0100 = "environments/NullEnv/sections/null-section-0100.png";
    public static String NULL_SECTION_0010 = "environments/NullEnv/sections/null-section-0010.png";
    public static String NULL_SECTION_0001 = "environments/NullEnv/sections/null-section-0001.png";
    public static String NULL_SECTION_1200 = "environments/NullEnv/sections/null-section-1200.png";
    public static String NULL_SECTION_1020 = "environments/NullEnv/sections/null-section-1020.png";
    public static String NULL_SECTION_1002 = "environments/NullEnv/sections/null-section-1002.png";
    public static String NULL_SECTION_2100 = "environments/NullEnv/sections/null-section-2100.png";
    public static String NULL_SECTION_0120 = "environments/NullEnv/sections/null-section-0120.png";
    public static String NULL_SECTION_0102 = "environments/NullEnv/sections/null-section-0102.png";
    public static String NULL_SECTION_2010 = "environments/NullEnv/sections/null-section-2010.png";
    public static String NULL_SECTION_0210 = "environments/NullEnv/sections/null-section-0210.png";
    public static String NULL_SECTION_0012 = "environments/NullEnv/sections/null-section-0012.png";
    public static String NULL_SECTION_2001 = "environments/NullEnv/sections/null-section-2001.png";
    public static String NULL_SECTION_0201 = "environments/NullEnv/sections/null-section-0201.png";
    public static String NULL_SECTION_0021 = "environments/NullEnv/sections/null-section-0021.png";
    public static String NULL_SECTION_1100 = "environments/NullEnv/sections/null-section-1100.png";
    public static String NULL_SECTION_1010 = "environments/NullEnv/sections/null-section-1010.png";
    public static String NULL_SECTION_1001 = "environments/NullEnv/sections/null-section-1001.png";
    public static String NULL_SECTION_0110 = "environments/NullEnv/sections/null-section-0110.png";
    public static String NULL_SECTION_0101 = "environments/NullEnv/sections/null-section-0101.png";
    public static String NULL_SECTION_0011 = "environments/NullEnv/sections/null-section-0011.png";
    public static String NULL_SECTION_1120 = "environments/NullEnv/sections/null-section-1120.png";
    public static String NULL_SECTION_1102 = "environments/NullEnv/sections/null-section-1102.png";
    public static String NULL_SECTION_1210 = "environments/NullEnv/sections/null-section-1210.png";
    public static String NULL_SECTION_1012 = "environments/NullEnv/sections/null-section-1012.png";
    public static String NULL_SECTION_1201 = "environments/NullEnv/sections/null-section-1201.png";
    public static String NULL_SECTION_1021 = "environments/NullEnv/sections/null-section-1021.png";
    public static String NULL_SECTION_2110 = "environments/NullEnv/sections/null-section-2110.png";
    public static String NULL_SECTION_0112 = "environments/NullEnv/sections/null-section-0112.png";
    public static String NULL_SECTION_2101 = "environments/NullEnv/sections/null-section-2101.png";
    public static String NULL_SECTION_0121 = "environments/NullEnv/sections/null-section-0121.png";
    public static String NULL_SECTION_2011 = "environments/NullEnv/sections/null-section-2011.png";
    public static String NULL_SECTION_0211 = "environments/NullEnv/sections/null-section-0211.png";
    public static String NULL_SECTION_0111 = "environments/NullEnv/sections/null-section-0111.png";
    public static String NULL_SECTION_1011 = "environments/NullEnv/sections/null-section-1011.png";
    public static String NULL_SECTION_1101 = "environments/NullEnv/sections/null-section-1101.png";
    public static String NULL_SECTION_1110 = "environments/NullEnv/sections/null-section-1110.png";
    
    //purple sections
    public static String NULL_SECTION_A_1000 = "environments/NullEnv/sections/A/section2-1000.png";
    public static String NULL_SECTION_A_0100 = "environments/NullEnv/sections/A/section2-0100.png";
    public static String NULL_SECTION_A_0010 = "environments/NullEnv/sections/A/section2-0010.png";
    public static String NULL_SECTION_A_0001 = "environments/NullEnv/sections/A/section2-0001.png";
    public static String NULL_SECTION_A_1100 = "environments/NullEnv/sections/A/section2-1100.png";
    public static String NULL_SECTION_A_1010 = "environments/NullEnv/sections/A/section2-1010.png";
    public static String NULL_SECTION_A_1001 = "environments/NullEnv/sections/A/section2-1001.png";
    public static String NULL_SECTION_A_0101 = "environments/NullEnv/sections/A/section2-0101.png";
    public static String NULL_SECTION_A_0110 = "environments/NullEnv/sections/A/section2-0110.png";
    public static String NULL_SECTION_A_0011 = "environments/NullEnv/sections/A/section2-0011.png";
    public static String NULL_SECTION_A_1110 = "environments/NullEnv/sections/A/section2-1110.png";
    public static String NULL_SECTION_A_1101 = "environments/NullEnv/sections/A/section2-1101.png";
    public static String NULL_SECTION_A_1011 = "environments/NullEnv/sections/A/section2-1011.png";
    public static String NULL_SECTION_A_0111 = "environments/NullEnv/sections/A/section2-0111.png";
    public static String NULL_SECTION_A_1111 = "environments/NullEnv/sections/A/section2-1111.png";
    
    //blue sections
    public static String SECTION_B_0000 = "environments/NullEnv/sections/b/section-0000.png";
    public static String SECTION_B_0001 = "environments/NullEnv/sections/b/section-0001.png";
    public static String SECTION_B_0010 = "environments/NullEnv/sections/b/section-0010.png";
    public static String SECTION_B_0011 = "environments/NullEnv/sections/b/section-0011.png";
    public static String SECTION_B_0012 = "environments/NullEnv/sections/b/section-0012.png";
    public static String SECTION_B_0021 = "environments/NullEnv/sections/b/section-0021.png";
    public static String SECTION_B_0100 = "environments/NullEnv/sections/b/section-0100.png";
    public static String SECTION_B_0101 = "environments/NullEnv/sections/b/section-0101.png";
    public static String SECTION_B_0102 = "environments/NullEnv/sections/b/section-0102.png";
    public static String SECTION_B_0110 = "environments/NullEnv/sections/b/section-0110.png";
    public static String SECTION_B_0111 = "environments/NullEnv/sections/b/section-0111.png";
    public static String SECTION_B_0112 = "environments/NullEnv/sections/b/section-0112.png";
    public static String SECTION_B_0120 = "environments/NullEnv/sections/b/section-0120.png";
    public static String SECTION_B_0121 = "environments/NullEnv/sections/b/section-0121.png";
    public static String SECTION_B_0201 = "environments/NullEnv/sections/b/section-0201.png";
    public static String SECTION_B_0210 = "environments/NullEnv/sections/b/section-0210.png";
    public static String SECTION_B_0211 = "environments/NullEnv/sections/b/section-0211.png";
    public static String SECTION_B_1000 = "environments/NullEnv/sections/b/section-1000.png";
    public static String SECTION_B_1001 = "environments/NullEnv/sections/b/section-1001.png";
    public static String SECTION_B_1002 = "environments/NullEnv/sections/b/section-1002.png";
    public static String SECTION_B_1010 = "environments/NullEnv/sections/b/section-1010.png";
    public static String SECTION_B_1011 = "environments/NullEnv/sections/b/section-1011.png";
    public static String SECTION_B_1012 = "environments/NullEnv/sections/b/section-1012.png";
    public static String SECTION_B_1020 = "environments/NullEnv/sections/b/section-1020.png";
    public static String SECTION_B_1021 = "environments/NullEnv/sections/b/section-1021.png";
    public static String SECTION_B_1100 = "environments/NullEnv/sections/b/section-1100.png";
    public static String SECTION_B_1101 = "environments/NullEnv/sections/b/section-1101.png";
    public static String SECTION_B_1102 = "environments/NullEnv/sections/b/section-1102.png";
    public static String SECTION_B_1110 = "environments/NullEnv/sections/b/section-1110.png";
    public static String SECTION_B_1111 = "environments/NullEnv/sections/b/section-1111.png";
    public static String SECTION_B_1112 = "environments/NullEnv/sections/b/section-1112.png";
    public static String SECTION_B_1120 = "environments/NullEnv/sections/b/section-1120.png";
    public static String SECTION_B_1121 = "environments/NullEnv/sections/b/section-1121.png";
    public static String SECTION_B_1200 = "environments/NullEnv/sections/b/section-1200.png";
    public static String SECTION_B_1201 = "environments/NullEnv/sections/b/section-1201.png";
    public static String SECTION_B_1210 = "environments/NullEnv/sections/b/section-1210.png";
    public static String SECTION_B_1211 = "environments/NullEnv/sections/b/section-1211.png";
    public static String SECTION_B_2001 = "environments/NullEnv/sections/b/section-2001.png";
    public static String SECTION_B_2010 = "environments/NullEnv/sections/b/section-2010.png";
    public static String SECTION_B_2011 = "environments/NullEnv/sections/b/section-2011.png";
    public static String SECTION_B_2100 = "environments/NullEnv/sections/b/section-2100.png";
    public static String SECTION_B_2101 = "environments/NullEnv/sections/b/section-2101.png";
    public static String SECTION_B_2110 = "environments/NullEnv/sections/b/section-2110.png";
    public static String SECTION_B_2111 = "environments/NullEnv/sections/b/section-2111.png";
    
    //boss nulls
    public static String KRAKEN_FG = "environments/boss-nulls/kraken-bg1.png";
    
    //spectral
    public static String SP_SECTION_PH = "environments/envSpectral/section-ph.png";
    public static String SP_SECTION_0001 = "environments/envSpectral/section-0001.png";
    public static String SP_SECTION_0010 = "environments/envSpectral/section-0010.png";
    public static String SP_SECTION_0011 = "environments/envSpectral/section-0011.png";
    public static String SP_SECTION_0100 = "environments/envSpectral/section-0100.png";
    public static String SP_SECTION_0101 = "environments/envSpectral/section-0101.png";
    public static String SP_SECTION_0110 = "environments/envSpectral/section-0110.png";
    public static String SP_SECTION_0111 = "environments/envSpectral/section-0111.png";
    public static String SP_SECTION_1000 = "environments/envSpectral/section-1000.png";
    public static String SP_SECTION_1001 = "environments/envSpectral/section-1001.png";
    public static String SP_SECTION_1010 = "environments/envSpectral/section-1010.png";
    public static String SP_SECTION_1011 = "environments/envSpectral/section-1011.png";
    public static String SP_SECTION_1100 = "environments/envSpectral/section-1100.png";
    public static String SP_SECTION_1101 = "environments/envSpectral/section-1101.png";
    public static String SP_SECTION_1110 = "environments/envSpectral/section-1110.png";
    
    
    //void
    public static String VOID_BG = "environments/EnvVoid/void-bg4.png";
    public static String VOID_BG_PH = "environments/EnvVoid/void-bg5b.png";
    public static String VOID_BG_A = "environments/EnvVoid/void-bgA.png";
    public static String VOID_BG_B = "environments/EnvVoid/void-bgB.png";
    public static String VOID_BG_FILTER = "environments/EnvVoid/void-bg-filter3.png";
    public static String VOID_MAP = "environments/EnvVoid/map/void-map2.png";
    public static String MAP_MARKER1 = "environments/EnvVoid/map/marker1.png";
    public static String MAP_MARKER_YELLOW = "environments/EnvVoid/map/markerYellow.png";
    public static String MAP_STARTER = "environments/EnvVoid/map/mark-start1.png";
    public static String MAP_END = "environments/EnvVoid/map/mark-end1.png";
    //EnvSub-end
    public static String ENVSUB_END_FG = "environments/EnvSub/sub-bg1.png";
    public static String ENVSUB_END_BG = "environments/EnvSub/EnvSub-end-bg.png";
    public static String ENDPIECE_A_1 = "entities/pickups/end pieces/pieceB-1.png";
    public static String ENDPIECE_A_2 = "entities/pickups/end pieces/pieceB-2.png";
    public static String ENDPIECE_A_3 = "entities/pickups/end pieces/pieceB-3.png";
    public static String ENDPIECE_A_4 = "entities/pickups/end pieces/pieceB-4.png";
    public static String ENDSECTION_A_1 = "entities/pickups/end pieces/sectionB-1.png";
    public static String ENDSECTION_A_1_FILL = "entities/pickups/end pieces/sectionB-1-fill.png";
    public static String ENDSECTION_A_2 = "entities/pickups/end pieces/sectionB-2.png";
    public static String ENDSECTION_A_2_FILL = "entities/pickups/end pieces/sectionB-2-fill.png";
    public static String ENDSECTION_A_3 = "entities/pickups/end pieces/sectionB-3.png";
    public static String ENDSECTION_A_3_FILL = "entities/pickups/end pieces/sectionB-3-fill.png";
    public static String ENDSECTION_A_4 = "entities/pickups/end pieces/sectionB-4.png";
    public static String ENDSECTION_A_4_FILL = "entities/pickups/end pieces/sectionB-4-fill.png";
    
                
    public static String ENDPIECE_C1 = "entities/pickups/end pieces/pieceC-1.png";            
    public static String ENDSECTION_C1 = "entities/pickups/end pieces/end-sectionC-1.png";
    public static String ENDSECTION_C1_FILL = "entities/pickups/end pieces/end-sectionC-1-fill.png";
    
    public static String ENDPAD_COMPLETE = "environments/EnvSub/endPad-complete1.png";
    
    
    //env-start 
    public static String START_BG_PH = "environments/env-start/start-bg.png";
    public static String START_INTRO_WHITE_BG = "environments/env-start/env-start-white.png";
    public static String START_BLACK_BG = "environments/env-start/env-start-black.png";
    
    
    
    //rooms
    public static String ROOM_BG1 = "environments/RoomEnv/room-bg3.png";
    public static String ROOM_BIN_BG = "environments/RoomEnv/binary/room-bin-bg.png";
    public static String ROOM_SIMPLE_BG1 = "environments/RoomEnv/room-simple-bg.png";
    public static String ROOM_GLYPHWALL_1 = "environments/RoomEnv/wall-glyph1.png";
    public static String ROOM_ARC = "environments/RoomEnv/room-arc.png";
    public static String ROOM_BIN_WALL = "environments/RoomEnv/binary/bin-wallPH.png";
    public static String ROOM_CODEPANEL0 = "environments/RoomEnv/binary/codePanelA0.png";
    public static String ROOM_CODEPANEL1 = "environments/RoomEnv/binary/codePanelA1.png";
    public static String ROOM_CODEPANEL_SWITCH = "environments/RoomEnv/binary/codePanel-switch2.png";
    public static String ROOM_CODESWITCH_0 = "environments/RoomEnv/binary/codeSwitch0.png";
    public static String ROOM_CODESWITCH_1 = "environments/RoomEnv/binary/codeSwitch1.png";
    public static String ROOM_CODEMON = "environments/RoomEnv/binary/codeMonitor1.png";
    public static String ROOM_CODENUM_BLANK = "environments/RoomEnv/binary/codeNumBlank.png";
    public static String ROOM_CODENUM_0 = "environments/RoomEnv/binary/codeNum0.png";
    public static String ROOM_CODENUM_1 = "environments/RoomEnv/binary/codeNum1.png";
    public static String ROOM_CODENUM_2 = "environments/RoomEnv/binary/codeNum2.png";
    public static String ROOM_CODENUM_3 = "environments/RoomEnv/binary/codeNum3.png";
    public static String ROOM_CODENUM_4 = "environments/RoomEnv/binary/codeNum4.png";
    public static String ROOM_CODENUM_5 = "environments/RoomEnv/binary/codeNum5.png";
    public static String ROOM_CODENUM_6 = "environments/RoomEnv/binary/codeNum6.png";
    public static String ROOM_CODENUM_7 = "environments/RoomEnv/binary/codeNum7.png";
    public static String ROOM_CODENUM_8 = "environments/RoomEnv/binary/codeNum8.png";
    public static String ROOM_CODENUM_9 = "environments/RoomEnv/binary/codeNum9.png";
    
    //EnvMan
    public static String ENVMAN_INTRO_BG = "environments/EnvMan/room-man-introbg1.png";
    
    
    //slums
    public static String SLUM_SECTION_BG = "environments/EnvSlum/slum-bg2.png";
    public static String SLUM_SECTION_WALL = "environments/EnvSlum/slum-wall1.png";
    public static String SLUM_SECTION_WALL_2 = "environments/EnvSlum/slum-wall2.png";
    
    //tears
    public static String BOSS_ICON = "entities/tears/boss-icon1.png";
    
    //pickups
    public static String FRAGMENT_PH = "entities/fragment-ph.png";
    public static String FRAG_HP = "entities/pickups/frag-hp.png";
    
    
    //items
    public static String GLYPH_ONE= "entities/pickups/items/glyph-one.png";
    public static String ITEM_DM1= "entities/pickups/items/item-dm2.png";
    public static String ITEM_MATTER_GREEN = "entities/pickups/matter-green1.png";
    public static String ITEM_MATTER_RED = "entities/pickups/matter-red1.png";
    public static String ITEM_MATTER_WHITE = "entities/pickups/matter-white1.png";
    public static String ITEM_MATTER_YELLOW = "entities/pickups/matter-yellow1.png";
    public static String ITEM_PED = "entities/pickups/item-ped1.png";
    public static String ITEM_LIFE = "entities/pickups/life-pickup1.png";
    
    
    public static String STAT_ITEM_LIFE = "entities/pickups/items/orb-life1.png";
    public static String STAT_ITEM_ENERGY = "entities/pickups/items/orb-energy1.png";
    public static String STAT_ITEM_DMG = "entities/pickups/items/orb-dmg1.png";
    public static String STAT_ITEM_SPEED = "entities/pickups/items/orb-speed1.png";
    
    //GUI
    public static String HUD1_HP_BG = "gui/hud1/hud-hp-bg.png";
    public static String HUD1_HP_FG = "gui/hud1/hud-hp-fg.png";
    public static String HUD1_EXP_FG = "gui/hud1/hud-exp-fg.png";
    public static String OVERLAY_GRID = "gui/hud1/overlay-grid.png"; 
    public static String ICON_HP = "gui/hud1/hp-icon2.png"; 
    public static String ICON_ENERGY = "gui/hud1/energy-icon.png"; 
    public static String HUD_ENERGY_FILL = "gui/hud2/energy-slot-fill.png"; 
    public static String HUD_ENERGY_EMPTY = "gui/hud2/energy-slot-empty.png"; 
    
    //skill overlay
    public static String HUD_SOUL_EMPTY = "gui/hud2/soul-slot-empty.png";
    public static String HUD_SKILL_OVERLAY = "gui/hud2/skill-overlay.png";
    public static String ITEM_SOUL_POE = "entities/pickups/soul/soul-poe.png";
    
    
    //soul hud
    public static String SOUL_LOGO_POE = "gui/hud2/poe-name.png"; 
    
    //pad icons
    public static String GUI_PAD_A = "gui/padIcon-A2.png";
    public static String GUI_PAD_B = "gui/padIcon-B2.png";
    public static String GUI_PAD_X = "gui/padIcon-X2.png";
    public static String GUI_PAD_Y = "gui/padIcon-Y2.png";
    public static String GUI_PAD_RB = "gui/padIcon-RB.png";
    public static String GUI_DASH = "gui/skill-dash.png";
    //pause menu
    public static String PAUSE_BG= "gui/pause/pause-menu-bg1.png";
    public static String PAUSE_CURSOR= "gui/pause/pause-cursor1.png";
    public static String PAUSE_OPTIONS= "gui/pause/pause-options1.png";
    public static String PAUSE_SOUND= "gui/pause/pause-sound1.png";
    //descriptions
    public static String DESC_BG = "gui/descriptions/desc-bg3.png";
    
    
    //GAME OVER SCREEN
    public static String GO_TEXT = "screens/GameOver/gameOver-text.png";
    
    //TODO: old comob circle stuff
    /*
    public static String COMBO_BASE = "combat/combo/combo-base.png";
    public static String COMBO_CENTER = "combat/combo/combo-center.png";
    public static String COMBO_CURSOR = "combat/combo/combo-cursor.png";
    public static String COMBO_YELLOW = "combat/combo/combo2-yellow.png";
    public static String COMBO_ORANGE = "combat/combo/combo2-orange.png";
    public static String COMBO_RED = "combat/combo/combo2-red.png";
    */
    
    
    //combo icons
    public static String COMBO_ICON_LIGHT = "gui/hud2/combo-icon-light.png";
    public static String COMBO_ICON_HEAVY = "gui/hud2/combo-icon-heavy.png";
    public static String COMBO_ICON_SPECIAL = "gui/hud2/combo-icon-special.png";
    
    
    //SKILLS
    public static String SKILL_PAD_LIGHT = "entities/pickups/pad-light.png";
    public static String SKILL_PAD_HEAVY = "entities/pickups/pad-heavy.png";
    public static String SKILL_PAD_SPECIAL = "entities/pickups/pad-special.png";
    public static String SKILL_PAD_PASSIVE = "entities/pickups/pad-passive.png";
    
    public static String SKILL_BLANK = "gui/skills/2/skill-blank.png";
    public static String SKILL_RED = "gui/skills/2/skill-red2.png";
    public static String SKILL_DASH = "gui/skills/2/skill-Dash.png";
    public static String SKILL_GHOSTJAB = "gui/skills/2/skill-GhostJab2.png";
    public static String SKILL_POWERPLEASE = "gui/skills/2/skill-PowerPlease2.png";
    public static String SKILL_NRG = "gui/skills/2/skill-Nrg2.png";
    public static String SKILL_MOMMASTOUCH = "gui/skills/2/skill-MommasTouch.png";
    public static String SKILL_MOMMASFURY = "gui/skills/2/skill-MommasFury.png";
    public static String SKILL_DASHBOLT = "gui/skills/2/skill-Dashbolt2.png";
    public static String SKILL_ONETWO = "gui/skills/2/skill-OneTwo2.png";
    public static String SKILL_CANTTOUCH = "gui/skills/2/skill-CantTouchThis2.png";
    public static String SKILL_HAUNTHASTE = "gui/skills/2/skill-HauntHaste2.png";
    public static String SKILL_MUCHHASTE = "gui/skills/2/skill-MuchHaste2.png";
    public static String SKILL_WARPIT = "gui/skills/2/skill-WarpIt2.png";
    public static String SKILL_PROGNOSIS = "gui/skills/2/skill-prognosis.png";
    public static String SKILL_HEAVYHANDED = "gui/skills/2/skill-heavyhanded.png";
    public static String SKILL_TAINTEDTORTURE = "gui/skills/2/skill-TaintedTouch.png";
    public static String SKILL_CRACKOFLIGHTNING = "gui/skills/2/skill-CrackOfLightning.png";
    public static String SKILL_CRACKOFTHUNDER = "gui/skills/2/skill-CrackOfThunder.png"; 
    
    //***********************
    //      SOUND
    //***********************
    
    //void music
    public static String BGM_VOID_1 = "sound/music/void/no-place-like-this.mp3";
    
    //null bgm
    public static String BGM_NULL_1 = "sound/music/null/frustration.mp3";
    public static String BGM_NULL_2 = "sound/music/null/head-banger.mp3";
    public static String BGM_NULL_3 = "sound/music/null/midnite-murders.mp3";
    public static String BGM_NULL_END = "sound/music/null/grave-walk.mp3";
    
    //spectral bgm
    public static String BGM_SPECTRAL_1 = "sound/music/spectral/when-bats-fly.mp3"; 
    
    //glyph room bgm
    public static String BGM_ROOM_1 = "sound/music/room/numbing-the-brain.mp3";
    public static String BGM_ROOM_2 = "sound/music/room/places-of-mystery.mp3";
    
    //slums
    public static String BGM_SLUM_1 = "sound/music/slums/amniotic-gravity.mp3";
    
    //***********************
    //SFX
    
    //void
    public static String SFX_WARP_IN = "sound/sfx/environment/warp-in.mp3";
    public static String SFX_COMPLETE_ENDSECTIONS = "sound/sfx/environment/click-shimmer.mp3";
    public static String SFX_ENDPIECE_FILL = "sound/sfx/environment/button-1.mp3";
    
    //null
    public static String SFX_NULL_IMPACT= "sound/sfx/impacts/impact-null.mp3";
    
    //player
    public static String SFX_DASH = "sound/sfx/impacts/dash7.mp3";
    //poe
    public static String POE_YELL_1 = "sound/sfx/poe/attack1.mp3";
    public static String POE_YELL_2 = "sound/sfx/poe/attack3.mp3";
    public static String POE_YELL_3 = "sound/sfx/poe/attack4.mp3";
    public static String SFX_POE_DMG = "sound/sfx/poe/male-gasp.mp3";
    
    //enemies
    public static String SFX_GOOBER_MOVE = "sound/sfx/enemies/squish-move.mp3";
    
    //null
    public static String SFX_NULL_END_IDLE = "sound/sfx/impacts/squish-2.mp3";
    public static String SFX_NULL_END_DEATH = "sound/sfx/impacts/squish-death.mp3";
    
    //ui
    public static String SFX_UI_MOVE = "sound/sfx/ui/switch-click.mp3";
    public static String SFX_UI_EQUIP = "sound/sfx/ui/equip.mp3";
    public static String SFX_INV_OPEN = "sound/sfx/ui/open-inv.mp3";
    public static String SFX_INV_CLOSE = "sound/sfx/ui/close-inv.mp3";
    
    //pickups
    public static String SFX_PICKUP = "sound/sfx/pickups/pickup2.mp3";
    public static String SFX_PICKUP_SKILL = "sound/sfx/pickups/new-skill3.mp3";
    public static String SFX_PICKUP_USE = "sound/sfx/pickups/click-digital.mp3";
    
    //impacts
    public static String SFX_IMPACT_1 = "sound/sfx/impacts/impact2.mp3";
    public static String SFX_IMPACT_2 = "sound/sfx/impacts/impact-heavy.mp3";
    public static String SFX_DEATH_1 = "sound/sfx/impacts/death1.mp3";
    
    //powerups
    public static String SFX_POWER_1= "sound/sfx/powerups/power1.mp3";
    
    
}

    