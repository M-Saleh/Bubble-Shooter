package game.engine;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
public class Sprite {
	
   Bitmap image;
   public int currentHorFrame;
   int currentVerFrame;
   int noOfHorFrames;
   int noOfVerFrames;
   int height;
   public int width;
   public double x;
   public double y;
   double rotation;
   Paint paint;
   public int dispWidth;
   public int dispHeight;
   Rect src;
   Rect dist;
   private double deltaX;
   private double deltaY;
   private int steps;
   public Sprite( Bitmap image,int width, int height){
	   this.image = image;
	   this.height = height;
	   this.width = width;
	   this.dispWidth = width;
	   this.dispHeight = height;
	   if(image != null){
		   this.noOfHorFrames = image.getWidth()/this.width;
		   this.noOfVerFrames = image.getHeight()/this.height;  
	   }
	  
	   paint = new Paint();
	   paint.setFlags(Paint.ANTI_ALIAS_FLAG);
	   src = new Rect();
	   dist = new Rect();
   }
   public void animateTo(double x,double y,int steps){

	    deltaX = ( x - this.x) /steps;
	    deltaY = ( y - this.y) /steps;
	    this.steps = steps;
   }
   public void moveTo(double x,double y){
	   this.x = x;
	   this.y = y;
   }
   public void moveBy(double d,double e){
	   this.x+=d;
	   this.y+=e;
   }
   public void setCurrentFrame(int currentX , int currentY){
	   this.currentVerFrame =  currentX;
	   this.currentVerFrame =  currentY;
   }
   
   public void setCurrentHorFrame(int currentHorFrame) {
	this.currentHorFrame = currentHorFrame;
   }
   public void nextHorFrame(){
	   this.currentHorFrame++;
	   if(this.currentHorFrame >= this.noOfHorFrames)
		   this.currentHorFrame = 0;
   }
   public void nextVerFrame(){
	   this.currentVerFrame++;
	   if(this.currentVerFrame >= this.noOfVerFrames)
		   this.currentVerFrame =  0;
   }
   public void setCurrentVerFrame(int currentVerFrame) {
	this.currentVerFrame = currentVerFrame;
   }
   
   public void rotateTo(double degrees){
	   this.rotation = degrees;
   }
   public void rotateBy(double degrees){
	   this.rotation+=degrees;
   }
   public void render(Canvas c){
	   if(steps-- > 0){
//		   System.out.println("x: "+x+" y: "+y+" deltaX: "+deltaX+" deltaY: "+deltaY);
		   x+=deltaX;
		   y+=deltaY;
	   }
	   src.left = currentHorFrame*width;
	   src.top = (int) (currentVerFrame*height);
	   src.right = currentHorFrame*width + width;
	   src.bottom = (int) (currentVerFrame*height +height);
	   dist.left = (int)x;
	   dist.top = (int) y;
	   dist.bottom = (int)y+ dispHeight;
	   dist.right =  (int)x+dispWidth ;
	   c.drawBitmap(image,src,dist, paint);

   }
	
}
