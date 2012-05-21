package game.engine;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

public class TextSprite extends Sprite{
	public String text;
	public TextSprite(Bitmap image,String text, int width, int height,int color) {
		super(image, width, height);
		this.text = text;
		paint.setColor(color);
		paint.setShadowLayer(6, 0, 0, Color.WHITE);
		// TODO Auto-generated constructor stub
	}
	@Override
	public void render(Canvas c) {
//		c.drawText(text,(int) x, (int)y, (float)x+dispWidth, (float)y+dispHeight,paint);
//		paint.setColor(Color.MAGENTA);
//		System.out.println("gere");
//		System.out.println(text+" "+x+" "+y+" "+paint);
		c.drawText(text,(float)x+width,(float)y+height , paint);
		
	}
	
}
