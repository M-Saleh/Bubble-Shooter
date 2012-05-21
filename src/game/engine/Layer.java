package game.engine;

import java.util.ArrayList;

import android.graphics.Canvas;

public class Layer {
    public ArrayList<Sprite> sprites;
//    Sprite [] sprites;
//    private int count;
   public float x;
   public float y;
    public Layer(){
    	sprites = new ArrayList<Sprite>();
//    	sprites = new Sprite[200];
    }
    
    
    public synchronized void addSprite(Sprite newSprite){
    	sprites.add(newSprite);
//    	sprites[count++] = newSprite;
    }
    public synchronized void removeSprite (Sprite sprite){
    	sprites.remove(sprite);
    }
    public synchronized void render(Canvas canvas){
   
    	canvas.translate(x, y);
    	for (int i=0;i<sprites.size();i++) {
			sprites.get(i).render(canvas);
		}
    	canvas.translate(-x, -y);
    }
}
