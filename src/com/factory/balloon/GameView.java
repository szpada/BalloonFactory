package com.factory.balloon;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.BitmapFactory.Options;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView{
	
	//GameView thread
	private GameLoopThread thread;

	private Paint paint;

	//factors for screen resolution
	private float w_factor;
	private float h_factor;

	//game finished animation
	private BalloonAnimation animation = null;
	
	public GameView(Context context, float w_factor, float h_factor) {
		super(context);

		this.w_factor = w_factor;
		this.h_factor = h_factor;
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		setLongClickable(true);
		thread = new GameLoopThread(this);
		getHolder().addCallback(new SurfaceHolder.Callback() {
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				thread.setRunning(false);
				while (retry) {
					try {
						thread.join();
						retry = false;
					} catch (InterruptedException e) {}
				}
			}
			public void surfaceCreated(SurfaceHolder holder) {
				prepareUI();
				thread.setRunning(true);
				thread.start();
			}
			public void surfaceChanged(SurfaceHolder holder, int format,int width, int height) {

			}
		}); 
	}
	
	public void prepareUI(){
		Options options = new Options();
		options.inPreferredConfig = Bitmap.Config.RGB_565;

		animation = new BalloonAnimation(480, 800);
//		animation.setBallonBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.balloon, options));
//		animation.setBallonDestroyedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.balloonexplosion, options));
		animation.setBallonBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.balloonnoalpha, options));
		animation.setBallonDestroyedBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.balloonexplosionnoalpha, options));
		animation.setBitmapProperties(0, 1, 1);
		animation.setBitmapProperties(1, 2, 4);
		animation.generateBallons();
		
		paint = new Paint();
	}
	
	@Override
	public void onDraw(Canvas canvas){
		paint.setColor(Color.LTGRAY);

		canvas.scale(w_factor, h_factor);
		
		//draw rect with color as background
		canvas.drawRect(0, 0, 480, 800, paint);

		//draw balloons
		animation.onDraw(canvas, paint);
		//move balloons
		animation.moveBallons();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//rozbudowac? pekanie tylko na pojedynczym dotknieciu a nie na przejechaniu palcem (albo na odwrot - tzn tak jak we frut nindzy?)
		
		int touchInd = event.getActionIndex();
		float x, y;
		x = event.getX(touchInd)/ this.w_factor;
		y = event.getY(touchInd) / this.h_factor;
		
		//check balloon collision
		if(event.getAction() == MotionEvent.ACTION_DOWN)
				animation.chechCollision((int)x, (int)y);
		
		return true;
	}
}

