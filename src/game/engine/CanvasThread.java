package game.engine;

import android.graphics.Canvas;

public class CanvasThread extends Thread {
	SurfaceViewHandler surfaceViewHandler;
	 Canvas c = null;
	public CanvasThread(SurfaceViewHandler surfaceViewHandler) {
		this.surfaceViewHandler = surfaceViewHandler;
	}
	long t1 = 0;
	long t2 = 0;
	long fps = 0;
	int nsec = 5;
	@Override
	public void run() {
//		t1 = System.currentTimeMillis();
//		while(true){

            try {
            	if(surfaceViewHandler.sh.getSurface().isValid()){
            		  c = surfaceViewHandler.sh.lockCanvas(null);
                      synchronized (surfaceViewHandler) {
                          surfaceViewHandler.doDraw(c);
                      }
            	}
              
            } finally {
             
                if (c != null) {
                	surfaceViewHandler.sh.unlockCanvasAndPost(c);
                }
            }
//	t2 = System.currentTimeMillis();
//			fps++;
//			if(t2 - t1 >= nsec*1000){
//				System.out.println("t: "+(t2-t1)+" FPSCanvas: "+(fps/nsec));
//				fps = 0;
//				t1 = t2;
//			}
//            try {
//				Thread.sleep(1);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}


	}
}