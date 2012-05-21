package game.engine;

import bubble.shoot.R;
import bubble.shooter.BubbleShootActivity;
import bubble.shooter.GameScene;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

public class SurfaceViewHandler extends SurfaceView implements
		SurfaceHolder.Callback ,OnTouchListener{
	
	Point touchPoint = new Point();
	Scene scene;
//	CanvasThread cthread;
    SurfaceHolder sh;
    public static int cWidth = 1;
    public static int cHeight = 1;
    public static boolean started = false;
    int lockCounter = 0 ; // ytshal we n3ml sleep ll thread
    
    
	public void setScene(Scene scene) {
		this.scene = scene;
	}

	public SurfaceViewHandler(Context context, AttributeSet attrbs) {
		super(context, attrbs);
		getHolder().addCallback(this);
		
		this.setOnTouchListener(this);
//		System.out.println("surface COnstr");
//		System.out.println("HELLO");
		
//		cthread = new CanvasThread(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		System.out.println("surface changed  holder:"+holder);
		cWidth = width;
		cHeight = height;
//		System.out.println("cv"+BubbleShootActivity.BubbleActivity.setc);
//		BubbleShootActivity.BubbleActivity.setContentView(R.layout.game_layout);
		System.out.println("format: "+ format);
		System.out.println("scene:"+ scene);
		if(scene != null){
			scene.setWidth(width);
			scene.setHeigth(height);
		}
	
		System.out.println("started:"+started);
		if(!started){
			
			scene.initialize();
			scene.start();
			started = true;
		}
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		sh = holder;
		System.out.println("SurfaceCreated holder:"+holder);

		setFocusable(true);

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		System.out.println("surface Destoryed holder:"+holder);
		boolean retry = true;
	
		while (retry) {
			try {
				scene.getLayerManager().canvasThread.join();
				retry = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doDraw(Canvas c) {
     this.scene.render(c);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event)
	{
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			touchPoint.x = (int) event.getX();
			touchPoint.y = (int) event.getY();
			
			GameScene.fireNewBubble(touchPoint);
			
			return true;
		}
		return false;
		
		
	}



}
