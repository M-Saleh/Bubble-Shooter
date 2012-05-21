package game.engine;

import java.util.ArrayList;

import android.graphics.Canvas;

public  class LayerManager {
	ArrayList<Layer> layers;
//	Layer []layers;
//	private int count;
	CanvasThread canvasThread;
	public LayerManager(SurfaceViewHandler surfaceViewHandler) {
		layers = new ArrayList<Layer>();
//		layers = new Layer[10];
		canvasThread = new CanvasThread(surfaceViewHandler);
	}

	public void start(){

			canvasThread.start();
	}
	public void run(){
//		if(!canvasThread.isAlive())
//		canvasThread.stop();
		canvasThread.run();
	}
	
	public synchronized void addLayer(Layer layer) {
		synchronized (this) {
			layers.add(layer);
		}
		
//		layers[count++] = layer;
	}

	public synchronized void removeLayer(Layer layer) {
	
			layers.remove(layer);
		
	
	}

	public synchronized void render(Canvas canvas) {

			for (int i=0;i<layers.size();i++) {
				layers.get(i).render(canvas);
			}
		
	
//		for(int i=0;i<count;i++)
//			layers[i].render(canvas);
	}
}
