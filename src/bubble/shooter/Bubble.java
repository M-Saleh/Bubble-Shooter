package bubble.shooter;

import java.util.Random;

import android.graphics.Bitmap;
import game.engine.Sprite;
import game.engine.SurfaceViewHandler;

public class Bubble {
	Sprite sprite;
	boolean alive;
	
	int row ;
	int column;
	int timer;
	final static Bitmap BITMAP = GameScene.bubbleMap;
	int color;
	int velocity;
	double theta;
	double deltaX ;
	double deltaY ;
	boolean checked ;
	static final private float inwordFactor = 0.15f;
	static final int VELOCITY = 10;
	public Bubble(int x,int y ,int color) {
		sprite = new Sprite(BITMAP, GameScene.bubbleHight, GameScene.bubbleWight);
		reIntialize(x, y, color);
	}
	
	public Bubble reIntialize (int x,int y ,int color) {
		sprite.moveTo(x, y);
		column = (int) ( (sprite.x+ GameScene.bubbleSize/2) /GameScene.bubbleSize) ;
		row = (int) ( (sprite.y+ GameScene.bubbleSize/2) /GameScene.bubbleSize);
		alive = true;
		this.color = color;
		sprite.setCurrentVerFrame(color);
		timer = 0;
		checked = false ;
		sprite.setCurrentHorFrame(0);
		return this ;
	}
	
	public void updateFrame()
	{
		timer ++ ;
		if (sprite.currentHorFrame == 3)
			{
				GameScene.layer.removeSprite(sprite);
				GameScene.falling.remove(this);
				GameScene.pool.add(this);
			}
		if (timer%5 == 0)
			sprite.nextHorFrame();
	}
	
	private void calculateTheta(double x,double y)
	{
		System.out.println("x = " + x);
		System.out.println("y = " + y);
		
		theta =  Math.toDegrees(Math.atan(y/x));
		
		if (theta <0)
			theta += 180 ;
		
		System.out.println("Theta = " + theta);
		deltaX = velocity*Math.cos(Math.toRadians(theta)) ;
		deltaY = velocity*Math.sin(Math.toRadians(theta)) * -1;
	}
	
	public void fire(int x,int y)
	{
		velocity = VELOCITY ;
		calculateTheta(x-GameScene.originalPoint.x, GameScene.originalPoint.y-y);
	}
	
	// test collied with 4 points
	private boolean collied () // return true if collision happened
	{
		double row1,row2,column1,column2;
		int rowI1,rowI2,columnI1,columnI2;
		int center ;
		
		row1 =  ((sprite.y+deltaY));
		
		row1+=inwordFactor*GameScene.bubbleSize;
		
		row1= (row1 + (row1/GameScene.bubbleSize)*GameScene.bubbleShitup)/GameScene.bubbleSize;
		
		row2 =  ((sprite.y+deltaY+GameScene.bubbleSize-(inwordFactor*GameScene.bubbleSize)));
		row2= (row2 + row2/GameScene.bubbleSize*GameScene.bubbleShitup)/GameScene.bubbleSize;
		
		rowI1 = (int)row1;
		rowI2 = (int)row2;
			column1 = ((sprite.x+deltaX+inwordFactor*GameScene.bubbleSize)/GameScene.bubbleSize);
			column2 = ((sprite.x+deltaX+GameScene.bubbleSize-inwordFactor*GameScene.bubbleSize)/GameScene.bubbleSize);
		
		columnI1 = (int) column1;
		columnI2 = (int) column2;
		
		center = (int) ((column1+column2)/2);
		
		if (columnI1 >= GameScene.array[0].length)
			columnI1 = GameScene.array[0].length-1;
		if (columnI2 >= GameScene.array[0].length)
			columnI2 = GameScene.array[0].length-1;
		
		if (columnI1 < 0)
			columnI1=0;
		if (columnI2 >= GameScene.array[0].length)
			columnI2=GameScene.array[0].length-1;
		
		if (rowI1 >= GameScene.array.length)
			rowI1 = GameScene.array.length-1;
		if (rowI2 >= GameScene.array.length)
			rowI2 = GameScene.array.length-1;
		
		boolean upLeft = GameScene.array[rowI1][columnI1] != null;
		boolean upRight = GameScene.array[rowI1][columnI2] != null ;
		boolean downLeft =   GameScene.array[rowI2][columnI1] != null ;
		boolean downRight =  GameScene.array[rowI2][columnI2] != null;
		if(upRight || upLeft || downRight || downLeft){
			
			System.out.println("rowI1 = " + rowI1);
			System.out.println("rowI2 = " + rowI2);
			System.out.println("columnI1 = " + columnI1);
			System.out.println("columnI2 = " + columnI2);
			System.out.println(upLeft+" "+upRight);
			System.out.println(downLeft+" "+downRight);
			
			if(color == GameScene.superBubbleColor){
				if(upLeft)
					color =  GameScene.array[rowI1][columnI1].color;
				else if(upRight)
					color =  GameScene.array[rowI1][columnI2].color;
				else if(downRight)
					color = GameScene.array[rowI2][columnI2].color;
				else
					color = GameScene.array[rowI2][columnI1].color;
				
				sprite.setCurrentVerFrame(color);
			}
			
			// row
			if((upRight || upLeft ) && !(downLeft || downRight)){ // up coll
				System.out.println("upColl");
				row = rowI1+1;
			}
			else if( (upRight || upLeft ) && !(downLeft || downRight)){ //down coll
				System.out.println("DownColl");
				row = rowI2-1;
			}
			else if ((upRight&& downRight) || (upLeft && downLeft)){
				System.out.println("sameRow");
				row = (int) ((row1+row2)/2);
			}
			else if ((upLeft && downRight) ||(upRight && downLeft)){
				row = rowI1+1;
			}
			else{
				System.out.println("ROW ERROR SETTING R+1");
				row = rowI1+1;
			}
			
			System.out.println("row is "+row);
			//column
			if( upLeft && downRight ){
				System.out.println("upLeft && downRight");
				if(row%2 == 0){
					System.out.println("even col");
					column = columnI1;
				}
				
				else{
					System.out.println("odd col");
					column = columnI1;
				}
				
			}
			else if (upRight && downLeft){
				System.out.println("upRight && downLeft");
				if(row%2 == 0){
					System.out.println("even col+1");
					column = columnI1+1;
				}
				
				else{
					System.out.println("odd col+1");
					column = columnI1+1;
				}
				
			}
			else if(upLeft || downLeft){
				System.out.println("upLeft || downLeft");
				if(row%2 == 0){
					System.out.println("even col+1");
					column = columnI1 +1;
				}
				
				else{
					System.out.println("odd col");
					column = columnI1;
				}
					
			}
			else if(upRight || downRight){
				System.out.println("upRight || downRight");
				if(row%2 == 0){
					System.out.println("even col+1");
					column = columnI1+1 ;
				}
					
				else{
					column = columnI1;//c
					System.out.println("odd col");
				}
				
			}
			
			if(upLeft && downLeft){
				column = columnI1+1;
			}
			else if(upRight && downRight){
				column = columnI1 ;
			}
		if(columnI1 == columnI2){
			column = columnI1;
		}
			System.out.println("row:"+row+" column:"+column);
			if(row < 0)
				row = 0;
			if(row >= 	GameScene.array.length)
				row = GameScene.array.length-1;
			if(column < 0)
				column = 0;
			if(column > GameScene.array[0].length)
				column = GameScene.array[0].length-1;
			if(GameScene.array[row][column] != null){
				panicRecover();
			}
			GameScene.array[row][column] = this;
			if (row%2 == 1){
				sprite.animateTo((column)*GameScene.bubbleSize + GameScene.bubbleSize/2, (row)*GameScene.bubbleSize  - row*GameScene.bubbleShitup ,velocity );
			}
			else{
				sprite.animateTo((column)*GameScene.bubbleSize, (row)*GameScene.bubbleSize  - row*GameScene.bubbleShitup , velocity );
			}
			
			return true;
		}
		
		else if(rowI2 == 0) // if reach to upper wall (wsl ll 7eta elly fo2 :D)
		{
			System.out.println("reach to upper wall (wsl ll 7eta elly fo2 :D)");
			row = (int) ((row1+row2)/(2));
			column = (int) ((column1+column2)/(2));
			
			if(row < 0)
				row = 0;
			if(row >= 	GameScene.array.length)
				row = GameScene.array.length-1;
			if(column < 0)
				column = 0;
			if(column >= GameScene.array[0].length)
				column = GameScene.array[0].length-1;
			
			System.out.println("Put in " + row + " , : " + column);
			if(GameScene.array[row][column] != null){
				panicRecover();
			}
			GameScene.array[row][column] = this ;
		
			//animateTo instead of moveTo
			if(color == GameScene.superBubbleColor){
				int bc = new Random().nextInt(4);
				color = bc;
				sprite.setCurrentVerFrame(bc);
				}
			sprite.animateTo((column)*GameScene.bubbleSize, (row)*GameScene.bubbleSize , velocity/2);
			
			return true;
		}
		else
			return false ;
	}
	
	public boolean updatePos(){
		if(velocity <= 0){
			return false;
		}
		
//		if (sprite.x < 0) ??? bta3t a dy :D
//			return true ;
		
		if (collied())
			return true ;
		
		if (sprite.x+GameScene.bubbleSize/2 <0 
			|| (sprite.x + sprite.dispWidth+GameScene.bubbleSize/2) >= SurfaceViewHandler.cWidth)
			deltaX*= -1 ;
		
		sprite.moveBy(deltaX,deltaY);
		
		return false ;
	}
	
	public void panicRecover(){
		System.err.println("________PANIC_RECOVERY___________");
		if(column+1 < GameScene.array[0].length && (GameScene.array[row][column+1] == null)){
			column++;
		}
		else if(row-1 > 0 && (GameScene.array[row-1][column] != null)){
			row--;
		}
		else if(row+1 < GameScene.array.length &&  (GameScene.array[row+1][column] == null) ){
			row++;
		}
		else if(column-1 > 0 &&  (GameScene.array[row][column-1] == null)){
			column--;
		}
		else{
			GameScene.layer.removeSprite(GameScene.array[row][column].sprite);
			GameScene.array[row][column]=null;
			System.out.println("_____________RECOVERY_FAILED______________");
			
		}
			
	}
	public void checkFalls ()
	{
		alive = false ;
		GameScene.temp.add(this);
		while (!GameScene.temp.isEmpty())
		{
//			System.out.println("IN");
//			 remove first bubble and get it's Neighbor 
			GameScene.BubblesGroup.add(GameScene.temp.peek());
			GameScene.temp.poll().getNeighbor();
		}
		
		/*NOW
		 * we have bubble loksha
		 * we need to get Bubbles which Not Connected
		 * get it if there is falling So call in GameScene looper 
		 */
	}
	
	public static void getNotConnected()
	{
		/*
		 *  visit Bubble which in First Row
		 *  That will visit all Bubble connected with them
		 *  So any Bubble to connect to them Will fall :)   
		 */
		
		for (int i=0;i<GameScene.array[0].length;i++)
			if (GameScene.array[0][i] != null && GameScene.array[0][i].alive
			&& !GameScene.array[0][i].checked)
				{
//					System.out.println("Visit " + i);
					GameScene.array[0][i].visit();
				}

		for (int i=0;i<GameScene.array.length;i++)
		{
			for (int j=0;j<GameScene.array[0].length;j++)
				if (GameScene.array[i][j] != null && !GameScene.array[i][j].checked)
					GameScene.extra.add(GameScene.array[i][j]);
		}
	
		/*
		 * return checked attribute to false
		 * I must find another way to solve this 
		 */
		
		for (int i=0;i<GameScene.array.length;i++)
		{
			for (int j=0;j<GameScene.array[0].length;j++)
				if (GameScene.array[i][j] != null)
					GameScene.array[i][j].checked = false ;
		}
	}
	
	private void visit ()
	{
//		System.out.println("Visit : row = " + row +" , Column = " + column);
		checked = true ;
		// Up
		if (row > 0)
			if (GameScene.array[row-1][column] != null && GameScene.array[row-1][column].alive
			&& !GameScene.array[row-1][column].checked)
				{
//					System.out.println("Go UP");
					GameScene.array[row-1][column].visit();
				}

		// Down
		if (row < GameScene.array.length-1)
			if (GameScene.array[row+1][column] != null && GameScene.array[row+1][column].alive
			&& !GameScene.array[row+1][column].checked)
				{
//					System.out.println("Go Down");	
					GameScene.array[row+1][column].visit();
				}
		
		// Left
		if (column > 0)
			if (GameScene.array[row][column-1] != null && GameScene.array[row][column-1].alive
			&& !GameScene.array[row][column-1].checked)
				{
//					System.out.println("Go Left");	
					GameScene.array[row][column-1].visit();
				}
		
		// Right
		if (column < GameScene.array[0].length-1)
		{
			if (GameScene.array[row][column+1] != null && GameScene.array[row][column+1].alive
					&& !GameScene.array[row][column+1].checked)
					{
//					System.out.println("Go Right");	
					GameScene.array[row][column+1].visit();
					}
		}

		// Upper_Left and Down_Left
		if (row %2 ==0 && column >0)
		{
			//Upper_Left
			if(row >0 && GameScene.array[row-1][column-1] != null 
			&& GameScene.array[row-1][column-1].alive && !GameScene.array[row-1][column-1].checked)
				{
//					System.out.println("Go UP_Left");
					GameScene.array[row-1][column-1].visit();
				}
			
			//Down_Left
			if(row < GameScene.array.length-1 && GameScene.array[row+1][column-1] != null 
			&& GameScene.array[row+1][column-1].alive && !GameScene.array[row+1][column-1].checked)
				{
//					System.out.println("Go Down_Left");
					GameScene.array[row+1][column-1].visit();
				}
		}
		
		// Upper_Right and Down_Right
		if (row %2 ==1 && column < GameScene.array[0].length-1)
		{
			//Upper_Right
			if(row >0 && GameScene.array[row-1][column+1] != null 
			&& GameScene.array[row-1][column+1].alive && !GameScene.array[row-1][column+1].checked)
				{
//					System.out.println("Go UP_Right");
					GameScene.array[row-1][column+1].visit();
				}
			
			//Down_Right
			if(row < GameScene.array.length-1 && GameScene.array[row+1][column+1] != null 
			&& GameScene.array[row+1][column+1].alive && !GameScene.array[row+1][column+1].checked)
				{
//					System.out.println("Go Down_Right");
					GameScene.array[row+1][column+1].visit();
				}
		}
	}
	
	private void getNeighbor ()
	{
		//1) in the same row 
		if (column > 0)
			if (GameScene.array[row][column-1] != null && GameScene.array[row][column-1].alive 
			&&(GameScene.array[row][column-1].color == color || GameScene.array[row][column-1].color == GameScene.superBubbleColor))
				{
					GameScene.array[row][column-1].alive = false ;
					GameScene.temp.add(GameScene.array[row][column-1]);
//					System.out.println("left , " + row);
				}
		
		if (column < GameScene.array[0].length-1)
			if (GameScene.array[row][column+1] != null && GameScene.array[row][column+1].alive
			&&(GameScene.array[row][column+1].color == color || GameScene.array[row][column+1].color == GameScene.superBubbleColor))
				{
					GameScene.array[row][column+1].alive = false ;
					GameScene.temp.add(GameScene.array[row][column+1]);
//					System.out.println("Right , " + row);
				}
		
		//2) prev. row
		if (row > 0)
		{
			if (GameScene.array[row-1][column] != null && GameScene.array[row-1][column].alive
					&&(GameScene.array[row-1][column].color == color || GameScene.array[row-1][column].color == GameScene.superBubbleColor))
				{
					GameScene.temp.add(GameScene.array[row-1][column]);
					GameScene.array[row-1][column].alive = false ;
//					System.out.println("Up , " + row);
				}
			
			if (column > 0)
			{
				if (row%2 == 0 && GameScene.array[row-1][column-1] != null && GameScene.array[row-1][column-1].alive
					&& (GameScene.array[row-1][column-1].color == color || GameScene.array[row-1][column-1].color == GameScene.superBubbleColor))
					{
						GameScene.array[row-1][column-1].alive = false ;
						GameScene.temp.add(GameScene.array[row-1][column-1]);
//								System.out.println("upper left , " + row);
					}
			}
			
			if (column < GameScene.array[0].length-1)
				if (row%2 == 1 && GameScene.array[row-1][column+1] != null && GameScene.array[row-1][column+1].alive
				&&(GameScene.array[row-1][column+1].color == color || GameScene.array[row-1][column+1].color == GameScene.superBubbleColor))
					{
						GameScene.array[row-1][column+1].alive = false ;
						GameScene.temp.add(GameScene.array[row-1][column+1]);
//						System.out.println("upper right , " + row);
					}
		}
		
		//3) next row
		if (row < GameScene.array.length-1)
		{
			if (GameScene.array[row+1][column] != null && GameScene.array[row+1][column].alive
					&&(GameScene.array[row+1][column].color == color || GameScene.array[row+1][column].color == GameScene.superBubbleColor))
				{
					GameScene.array[row+1][column].alive = false ;	
					GameScene.temp.add(GameScene.array[row+1][column]);
//					System.out.println("down , " + row);
				}
			if (column > 0)
				if (row%2 == 0 &&GameScene.array[row+1][column-1] != null &&GameScene.array[row+1][column-1].alive
				&& (GameScene.array[row+1][column-1].color == color || GameScene.array[row+1][column-1].color == GameScene.superBubbleColor))
					{
						GameScene.array[row+1][column-1].alive = false ;
						GameScene.temp.add(GameScene.array[row+1][column-1]);
//						System.out.println("down left , " + row);
					}
			
			if (column < GameScene.array[0].length-1)
				if (row%2 == 1 &&GameScene.array[row+1][column+1] != null && GameScene.array[row+1][column+1].alive
				&& (GameScene.array[row+1][column+1].color == color || GameScene.array[row+1][column+1].color == GameScene.superBubbleColor))
					{
						GameScene.array[row+1][column+1].alive = false ;
						GameScene.temp.add(GameScene.array[row+1][column+1]);
//						System.out.println("down right , " + row);
					}
		}
	}
	
}
