package bubble.shooter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import game.engine.Layer;
import game.engine.Scene;
import game.engine.Sprite;
import game.engine.SurfaceViewHandler;
import game.engine.TextSprite;
import bubble.shoot.R;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

public class GameScene extends Scene {// implements SensorEventListener{
	
	static Point originalPoint; // l point elly bndrb mnha
	static LinkedList<Bubble> BubbleList = new LinkedList<Bubble>();
	static Bitmap bubbleMap ;
	static Layer layer;
	static Bubble [][] array ;
	static Queue<Bubble> BubblesGroup = new LinkedList<Bubble> ();
	static LinkedList<Bubble> falling = new LinkedList<Bubble>();
	static Queue<Bubble> temp = new LinkedList<Bubble> ();
	static LinkedList<Bubble> pool = new LinkedList<Bubble>();
	static LinkedList<Bubble> extra = new LinkedList<Bubble>();
	static int bubbleHight;
	static int bubbleWight;	
	public Resources resources;
	static  int bubbleSize;
	static double shiftDown ;
	static int numOfBubble = 0 ;
	private static int rowsDown = 0; // number of changed rows to modify original Point
	private static int timer = 0;
	static double bubbleShitup = 0;
	static Layer blayer;
	static Layer clayer;
	static int score;
	static int downPeriod=0;
	static int numOfBubbPerRow = 10+1; // +1 which will decrease laster in Generator 
	static int arrayLenght ;
	static int superBubbleColor = 4 ;
	static int minNumberOfGroup=3;
	
//	private SensorManager mSensorManager;
//	private Sensor mAccelerometer;
	
	public GameScene(SurfaceViewHandler surfaceViewHandler) {
		super(surfaceViewHandler,1);
		
//		mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
//		mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}
	
	public void initialize() 
	{
		timer =0;
//		System.out.println("LevelSelectMenu.selected = " + LevelSelectMenu.selected);
		
		// period bet. downs
		downPeriod = 600 - LevelSelectMenu.selected*50 ;
		
		// reInitialize all static variable each level
		rowsDown=0;
		score = 0 ;
		numOfBubble=0;
		BubbleList.clear();
		BubblesGroup.clear();
		temp.clear();
		falling.clear();
		pool.clear();
		extra.clear();
		bubbleSize = getWidth()/numOfBubbPerRow;
		shiftDown = (bubbleSize *2)/3;
	    bubbleMap = BitmapFactory.decodeResource(resources, R.drawable.b16);
	    
	    bubbleHight =bubbleMap.getHeight()/5;
	    bubbleWight =bubbleMap.getWidth()/4;
	    
	    System.out.println("sizes = " + bubbleHight +" , " + bubbleWight);
	    
	    // create Objects
	    layer = new Layer();
	    blayer = new Layer();
	    Bitmap background;
	    if(false){
	    	background= BitmapFactory.decodeResource(resources, R.drawable.fotouh_pic);
	    }else{
	        background= BitmapFactory.decodeResource(resources, R.drawable.background);
	    }
	
	    Bitmap shootSpotBitmap = BitmapFactory.decodeResource(resources, R.drawable.shoot_point);
	    Sprite shootspotSprite = new Sprite(shootSpotBitmap, shootSpotBitmap.getWidth(), shootSpotBitmap.getHeight());
	    Sprite bsprite = new Sprite(background, background.getWidth(),background.getHeight());
	    
	    Bitmap wallbitmap = BitmapFactory.decodeResource(resources, R.drawable.wall);
	    Sprite wsprite = new Sprite(wallbitmap, wallbitmap.getWidth(), wallbitmap.getHeight());
	    wsprite.dispWidth = getWidth();
	    wsprite.dispHeight = getHeigth();
	    wsprite.moveTo(-bubbleSize/2, -getHeigth());
	    layer.addSprite(wsprite);
	    
	    bsprite.dispWidth = getWidth();
	    bsprite.dispHeight = getHeigth();
	    blayer.addSprite(bsprite);
	    blayer.addSprite(shootspotSprite);
	    
	    // set variables
	    arrayLenght = getHeigth()/bubbleSize +5;
//	    System.out.println("arrayLenght = " + arrayLenght );
	    
//	    Load the Array
		array = BubbleShootActivity.generator.load(LevelSelectMenu.levelSelected) ;
//		Calculate ShiftUp	
	    bubbleShitup = 2*bubbleSize - Math.sqrt(3*Math.pow(bubbleSize, 2));
	    
	    // Draw the initial Bubbles
	    drawGrid();
	    
	    int factor =(int)( bubbleSize * 2);
	    // Calculate Original Point (Shoteer Point)
	    originalPoint = new Point(((getWidth()-bubbleSize)/2)-bubbleSize/2, getHeigth()-bubbleSize - factor/2);
	    
	    /////////////
	    shootspotSprite.dispWidth = bubbleSize +factor;
	    shootspotSprite.dispHeight = bubbleSize + factor;
	    shootspotSprite.x = originalPoint.x - factor/2 + bubbleSize/2;
	    shootspotSprite.y = originalPoint.y - factor/2;
	    /////////////
	    
	    layer.x = bubbleSize/2;
	    getLayerManager().addLayer(blayer);
	    getLayerManager().addLayer(layer);
//	    b = getRandomBubble();
	    bubbles = new ArrayList<Bubble>();
	   for(int i=0;i< 5;i++)
		   getRandomBubble();
	   clayer = new Layer();
	   getLayerManager().addLayer(clayer);
	   ts = new TextSprite(null, "LEVEL :"+LevelSelectMenu.levelSelected+"         SCORE: "+score, 0, 10,Color.GREEN);
	   clayer.addSprite(ts);
//	   layer.addSprite(ts);
	}
	
	TextSprite ts ;
	Sprite s;
	Bitmap winLose;
	boolean gameEnded = false;
	
//	Handle Win/Lose Action
	public void winlose(boolean win){
		
		if(win){
			 winLose = BitmapFactory.decodeResource(resources, R.drawable.win);
			 if (Options.soundBoolean)
				 BubbleShootActivity.winPlayer.start();
		}
		else{
			 winLose = BitmapFactory.decodeResource(resources, R.drawable.lose);
			 if (Options.soundBoolean)
			 	BubbleShootActivity.losePlayer.start();
		}
		 s = new Sprite(winLose, winLose.getWidth(),winLose.getHeight());
	
		s.dispWidth = getWidth()/2;
		s.dispHeight = (int) ((((double)winLose.getHeight())/((double)winLose.getWidth())) * s.dispWidth);
//		System.out.println(s.dispHeight+" "+s.dispWidth);
		s.x = getWidth()/4;
		s.y = -s.dispHeight;
		clayer.addSprite(s);
		s.animateTo( getWidth()/4, (getHeigth()/2)-s.dispHeight, 20);
		gameEnded = true;
		timer = 0;
	}
	
	// Game Looper
	@Override
	public void run() 
	{
		// Check End to back
		if(gameEnded){
			if(timer++ == 200){
				timer = 0;
				BubbleShootActivity.BubbleActivity.finish();
			}
			super.run();
			return;
		}	
		
		// For all moving bubbles check Collision
		checkCollision();
		
		//update falling Bubbles Frame
		for (int i=0;i<falling.size();i++)
			falling.get(i).updateFrame();
				
		// Check Winning
		if (numOfBubble == 0){
			winlose(true);
		}
		
		timer ++;
		//  Check goDown
		if (timer %downPeriod==0)
		{
			System.out.println("SHIFT");
			rowsDown++;
			
			// change point position of layer and firing point
			layer.y+=shiftDown;
			originalPoint.y-=shiftDown;
			// Shift all Bubbles down by shiftDown
			for(int i=0;i<bubbles.size();i++){
				bubbles.get(i).sprite.moveBy(0, -shiftDown);
			}				
			// check losing
			if (checkLose(getLastFillRow(array)))
				winlose(false);
		}
			
		super.run();
	}
	
	// Check Collision
	private void checkCollision()
	{
		for (int i =0;i<BubbleList.size();i++)
		{
			if (BubbleList.get(i).updatePos()) // Collied (stop it)
				{
					temp.clear();
					BubblesGroup.clear();
					BubbleList.get(i).checkFalls();
					if ((BubblesGroup.size()) >= minNumberOfGroup)
					{
//						if (Options.soundBoolean)
//							BubbleShootActivity.fallPlayer.start();
						
						if (Options.vibrateBoolean)
							BubbleShootActivity.vibrator.vibrate(150);
						
						while (!BubblesGroup.isEmpty())
							{
								array[BubblesGroup.peek().row][BubblesGroup.peek().column]=null;
								// add to Falling
								falling.add(BubblesGroup.poll());
								numOfBubble -- ;
								score+=10;
							}
						
							// Get NotConnected Bubbles
							extra.clear();
							Bubble.getNotConnected();
							System.out.println("Num Of Extra = " + extra.size());
							for (int k =0;k<extra.size();k++)
								{
									falling.add(extra.get(k));
									numOfBubble -- ;
									score+=10;
									array[extra.get(k).row][extra.get(k).column]=null;
								}
							
							// Update Score
							ts.text ="Level :"+LevelSelectMenu.levelSelected+"           Score = "+score;
					}
					else // turn all bubble alive again
					{
						while (!BubblesGroup.isEmpty())
							BubblesGroup.poll().alive = true ;
					}
					
					// Collied So Remove From BubbleList
					BubbleList.remove(i);
					
					// check losing
					if (checkLose(getLastFillRow(array)))
						winlose(false);
				}
		}
	}
	
	static ArrayList<Bubble> bubbles;
	/*
	 * When Fire call this function
	 * Check the poll if has Bubbles take one else create new one 
	 */
	
	public static void getRandomBubble(){
		Bubble b = null ;
		if (pool.size()>0)
			b =pool.remove(0).reIntialize((int) (originalPoint.x+((bubbleSize*1.5)*bubbles.size())),originalPoint.y,
					Generate.rand.nextInt(5));
		else
			b = new Bubble((int) (originalPoint.x+((bubbleSize*1.5)*bubbles.size())),originalPoint.y,
					 Generate.rand.nextInt(5));
		
		layer.addSprite(b.sprite);
		b.sprite.dispHeight = bubbleSize;
		b.sprite.dispWidth = bubbleSize;
		bubbles.add(b);
	}
	
	// fire new Bubble to Point P
	static double theta;
	static double x;
	static double y;
	public static void fireNewBubble (Point p)
	{
		p.y-=shiftDown*rowsDown;
		p.x-=bubbleSize;
		
		x = p.x-GameScene.originalPoint.x;
		y = GameScene.originalPoint.y-p.y;
		
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		
		theta =  Math.toDegrees(Math.atan (y/x));
		if (theta <0)
			theta += 180 ;
		
		System.out.println("THEATA NOE = " + theta);
		if (theta < 5 || theta > 175)
			return ;
		
		Bubble b = bubbles.remove(0);
		for(int i=0;i<bubbles.size();i++)
			bubbles.get(i).sprite.animateTo(originalPoint.x+((bubbleSize*1.5)*i), originalPoint.y,8);

		getRandomBubble();
		
		if (p.y > originalPoint.y) // if press under the Shooter
			{
				layer.removeSprite(b.sprite);
				pool.add(b);
				return ;
			}
		
		if (Options.soundBoolean)
			BubbleShootActivity.hitPlayer.start();
		
		b.fire(p.x, p.y);
		BubbleList.add(b);
		numOfBubble ++ ;
	}
	
	
	// draw initial Bubbles
	private void drawGrid ()
	{
		for (int i =0;i<array.length;i++)
		{
			for (int j =0;j<array[0].length;j++)
			{
				if (array[i][j] != null)
				{
					numOfBubble ++ ;
					layer.addSprite(array[i][j].sprite);
					array[i][j].sprite.dispWidth = GameScene.bubbleSize;
					array[i][j].sprite.dispHeight = GameScene.bubbleSize;	
					array[i][j].sprite.moveBy(0, i* -bubbleShitup);
					if(i%2 == 1)
						array[i][j].sprite.moveBy(bubbleSize/2, 0);
				}
			}
		}
	}
	
	private double position;
	// Check Losing
	private boolean checkLose (int lastRow)
	{
		position = (lastRow+2) * bubbleSize - (lastRow)* bubbleShitup ;
		if (position >= originalPoint.y)
			return true ;
		else
			return false ;
	}
	
	private int getLastFillRow(Bubble [][] array)
	{
		for (int i =array.length-1;i>=0;i--)
		{
			for (int j =array[0].length-1;j>=0;j--)
				if (array[i][j] != null)
					return i ;
		}
		return -1 ;
	}

//	@Override
//	public void onAccuracyChanged(Sensor sensor, int accuracy) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void onSensorChanged(SensorEvent event) {
//		// TODO Auto-generated method stub
//		
//	}
}
