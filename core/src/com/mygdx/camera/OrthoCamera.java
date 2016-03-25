package com.mygdx.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.MainGame;
import com.mygdx.managers.FrameManager;
import com.mygdx.utilities.FrameCounter;
import java.util.Random;

public class OrthoCamera extends OrthographicCamera {

	Vector3 tmp = new Vector3();
	Vector2 origin = new Vector2();
	VirtualViewport virtualViewport;
	Vector2 pos = new Vector2();

	public OrthoCamera() {
		this(new VirtualViewport(MainGame.WIDTH, MainGame.HEIGHT));
	}
	
	public OrthoCamera(VirtualViewport virtualViewport) {
		this(virtualViewport, 0f, 0f);
	}

	public OrthoCamera(VirtualViewport virtualViewport, float cx, float cy) {
		this.virtualViewport = virtualViewport;
		this.origin.set(cx, cy);
	}

	public void setVirtualViewport(VirtualViewport virtualViewport) {
		this.virtualViewport = virtualViewport;
	}
	
	public void setPosition(float x, float y) {
		position.set(x - viewportWidth * origin.x, y - viewportHeight * origin.y, 0f);
		pos.set(x, y);
	}
	
	public Vector2 getPos() {
		return pos;
	}
        
        //screen shake
        private Random random = new Random();
        private float rumbleX;
        private float rumbleY;
        private float rumbleTime = 0;
        private float currentRumbleTime = 1;
        private float rumblePower = 0;
        private float currentRumblePower = 3.5f;
        private FrameManager fm = new FrameManager();
        private FrameCounter shakeFC = new FrameCounter(0.75f);

	@Override
	public void update() {
		float left = zoom * -viewportWidth / 2 + virtualViewport.getVirtualWidth() * origin.x;
		float right = zoom * viewportWidth / 2 + virtualViewport.getVirtualWidth() * origin.x;
		float top = zoom * viewportHeight / 2 + virtualViewport.getVirtualHeight() * origin.y;
		float bottom = zoom * -viewportHeight / 2 + virtualViewport.getVirtualHeight() * origin.y;

		projection.setToOrtho(left, right, bottom, top, Math.abs(near), Math.abs(far));
		view.setToLookAt(position, tmp.set(position).add(direction), up);
		combined.set(projection);
		Matrix4.mul(combined.val, view.val);
		invProjectionView.set(combined);
		Matrix4.inv(invProjectionView.val);
		frustum.update(invProjectionView);
                
                //screen shake
                fm.update();
                
                if(toShake){
                    currentRumblePower = rumblePower * ((rumbleTime - currentRumbleTime) / rumbleTime);

                    rumbleX = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;
                    rumbleY = (random.nextFloat() - 0.5f) * 2 * currentRumblePower;

                    this.translate(-rumbleX, -rumbleY);
                    
                    if(shakeFC.complete){
                        toShake = false;
                        shakeFC.stop();
                    }
                }
	}

	/**
	 * This must be called in ApplicationListener.resize() in order to correctly update the camera viewport. 
	 */
	public void updateViewport() {
		setToOrtho(false, virtualViewport.getWidth(), virtualViewport.getHeight());
	}
	
	public Vector2 unprojectCoordinates(float x, float y) {
        Vector3 rawtouch = new Vector3(x, y,0);
        unproject(rawtouch); 
        return new Vector2(rawtouch.x, rawtouch.y);
    }
	
	public void resize() {
		VirtualViewport virtualViewport = new VirtualViewport(MainGame.WIDTH, MainGame.HEIGHT);  
		setVirtualViewport(virtualViewport);  
		updateViewport();
	}
        
        private boolean toShake = false;
        
        public void shake(float power, float time){
            toShake = true;
            rumblePower = power;
            rumbleTime = time;
            currentRumbleTime = 0;
            shakeFC.setTime(time);
            shakeFC.start(fm);
        }
        
        public void stopShake(){
            toShake = false;
        }
}