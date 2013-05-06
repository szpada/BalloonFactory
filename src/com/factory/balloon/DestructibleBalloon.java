package com.factory.balloon;

public class DestructibleBalloon {
	//balloon position
	private int x;
	private int y;
	
	//balloon size
	private int width;
	private int height;
	
	//balloon size
	private int speed;
	
	//balloon dead - variable defining if balloon is exploding
	private boolean dead;
	
	//current animation frame
	private int currentFrame;
	
	//max frames
	private int frames;
	
	//type of a balloon (i.e. good/bad, etc.)
	private int balloonType;
	
	public DestructibleBalloon(int x, int y, int speed){
		this.x = x;
		this.y = y;
		
		this.width = 64;
		this.height = 64;
		
		this.speed = speed;
		
		this.dead = false;
		
		this.balloonType = 0; //default?
	}
	
	public void move(int randomMove){
		this.x += randomMove;
		this.y -= speed;
	}
	
	public boolean checkCollision(int x, int y){
		if(dead) return false;
		if(this.x <= x && this.x + this.width >= x && this.y <= y && this.y + this.height >= y){
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @return true if balloon finished whole death animation (can be removed), false if balloon is not dead or animation didn't finish
	 */
	public boolean update(){
		currentFrame++;
		if(currentFrame > frames){
			currentFrame = 0;
			if(dead){
				return true;
			}
		}
		return false;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}

	public void setCurrentFrame(int currentFrame) {
		this.currentFrame = currentFrame;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	/**
	 * @return the balloonType
	 */
	public int getBalloonType() {
		return balloonType;
	}

	/**
	 * @param balloonType the balloonType to set
	 */
	public void setBalloonType(int balloonType) {
		this.balloonType = balloonType;
	}
}
