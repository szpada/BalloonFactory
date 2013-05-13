package com.factory.balloon;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.DrawableContainer;
import android.util.Log;

//class showing randomly generated flying balloons which can be destroyed
public class BalloonAnimation {

	private Bitmap balloon;
	private Bitmap balloonDestroyed;
	
	private List<DestructibleBalloon> balloons;
	
	//area of effect
	private int width;
	private int height;
	
	//random for creating and moving balloons
	private Random random;
	
	//balloons quantity
	private static final int balloonQuota = 30;
	
	//balloon speed (1 - balloonSpeed)
	private static final int balloonSpeed = 8;
	
	//balloon sideways move (0 - balloonSidewayMove-1)
	private static final int balloonSidewayMove = 6;
	
	private int bitmapProperties[][] = {{1,1}, {1,1}};
	
	
	private static int bmpWidth;
	private static int bmpHeight;
	
	private static int destroyedBmpWidth;
	private static int destroyedBmpHeight;
	
	public BalloonAnimation(int width, int height){
		this.width = width;
		this.height = height;
		
		this.random = new Random();
	}
	
	public void generateBallons(){
		balloons = new ArrayList<DestructibleBalloon>();
		for(int i = 0; i < balloonQuota; i++){
			DestructibleBalloon blln = new DestructibleBalloon(random.nextInt(width), this.height + random.nextInt(height), random.nextInt(balloonSpeed) + 1);
			
			//change width with height because image is rotated
			//blln.setHeight(balloon.getWidth());
			//blln.setWidth(balloon.getHeight());
			
			blln.setHeight(balloon.getHeight());
			blln.setWidth(balloon.getWidth());
			
			balloons.add(blln);
		}
	}
	
	public void moveBallons(){		
		int size = balloons.size();
		for(int i = 0; i < size; i++){
			//move balloon only if it isn't dead
			if(!balloons.get(i).isDead()) balloons.get(i).move(random.nextInt(balloonSidewayMove) - balloonSidewayMove/2);
			if(balloonNotVissible(balloons.get(i).getX(), balloons.get(i).getY(), balloons.get(i).getWidth(), balloons.get(i).getHeight())){
				balloons.get(i).setX(random.nextInt(width));
				balloons.get(i).setY(this.height + random.nextInt(height));
			}
			//if balloon finished death animation
			if(balloons.get(i).update()){
				balloons.get(i).setX(random.nextInt(width));
				balloons.get(i).setY(this.height + random.nextInt(height));
				balloons.get(i).setDead(false);
				balloons.get(i).setFrames(bitmapProperties[0][0] * bitmapProperties[0][1] - 1);
				balloons.get(i).setCurrentFrame(0);
				//balloons.get(i).setWidth(balloon.getWidth());
				//balloons.get(i).setHeight(balloon.getHeight());
				
				//change width with heigh because image is rotated
				//balloons.get(i).setHeight(balloon.getWidth());
				//balloons.get(i).setWidth(balloon.getHeight());
				
			}
		}
	}
	
	public boolean chechCollision(int x, int y){
		int size = balloons.size();
		for(int i = 0; i < size; i++){
			//balloon touched
			if(balloons.get(i).checkCollision(x, y)){
				//balloon is dead
				balloons.get(i).setDead(true);
				balloons.get(i).setFrames(bitmapProperties[1][0] * bitmapProperties[1][1] - 1);
				balloons.get(i).setCurrentFrame(0);
				return true;
			}
		}
		return false;
	}
	
	public void onDraw(Canvas canvas, Paint paint){
		int size = balloons.size();
		for(int i = 0; i < size; i++){
			DestructibleBalloon currentballoon = balloons.get(i);
			drawSprite(canvas, paint, currentballoon.getX(), currentballoon.getY(), currentballoon.isDead(), currentballoon.getCurrentFrame(), 0, 1.0f, 255, currentballoon.getBalloonType());
			//paint.setColor(Color.GRAY);
			//canvas.drawRect(balloons.get(i).getX(), balloons.get(i).getY(), balloons.get(i).getX() + balloons.get(i).getWidth(), balloons.get(i).getY() + balloons.get(i).getHeight(), paint);
		}
		paint.reset();
	}
	
	public boolean balloonNotVissible(int x, int y, int widht, int height){
		if(x > this.width || y + height < 0 || x + width < 0) return true;
		return false;
	}
	
	public void drawSprite(Canvas canvas, Paint paint, int x, int y, boolean dead, int currentFrame, float angle, float scale, int alpha, int type){

		int columns;
		int rows;
		int height;
		int width;
		
		if(dead){
			columns = bitmapProperties[1][1];
			rows = bitmapProperties[1][0];	
			width  = destroyedBmpWidth /columns;
			height = destroyedBmpHeight /rows;
		}
		else{
			columns = bitmapProperties[0][1];
			rows = bitmapProperties[0][0];
			width  = bmpWidth /columns;
			height = bmpHeight /rows;
		}
		
		//set color filter on paint
		//paint.setColorFilter(colorFilter);
		
		//save current state of canvas
		canvas.save();
		//scale
		//canvas.scale(scale, scale, x, y);
		
		//rotation
		//canvas.rotate(angle + 90, x + width/2, y + height/2);
		//canvas.rotate(angle, x + width/2, y + height/2);
		canvas.rotate(angle, x, y);
		
		//y -= height;
		
		int srcX = 0;
		int srcY = 0;
		int row;
		srcX = (currentFrame % (columns)) * width;
		if(rows % 2 == 0){
			row = currentFrame / (rows);
		}
		else{
			row = currentFrame / (rows + 1);
		}
		srcY = row * height;

		Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
		//Rect dst = new Rect(x - width/2, y - height/2, x + width/2, y + height/2);

		Rect dst = new Rect(x, y, x + width, y + height);

//		paint.setAntiAlias(true); //spowalnia tak samo jak alpha?
//		paint.setAlpha(alpha); //spowalnia, ale niewiele, bez alfy z pelnym ekranem balonow tez jest spowolnione
		
		if(dead) canvas.drawBitmap(balloonDestroyed, src, dst, paint);
		else canvas.drawBitmap(balloon, src, dst, paint);

		paint.reset();

		//restore canvas to previous settings
		canvas.restore();
	}

	public void setBallonBitmap(Bitmap decodeResource) {
		this.balloon = decodeResource;
		bmpWidth = decodeResource.getWidth();
		bmpHeight = decodeResource.getHeight();
	}

	public void setBallonDestroyedBitmap(Bitmap decodeResource) {
		this.balloonDestroyed = decodeResource;
		destroyedBmpWidth = decodeResource.getWidth();
		destroyedBmpHeight = decodeResource.getHeight();
	}

	/**
	 * 
	 * @param bitmap propoerty - 0 - BALLOON, 1 - BALLOON DESTRUCTION
	 * @param rows number of rows
	 * @param columns number of columns
	 */
	public void setBitmapProperties(int bitmap, int rows, int columns) {
		this.bitmapProperties[bitmap][0] = rows;
		this.bitmapProperties[bitmap][1] = columns;
	}
}
