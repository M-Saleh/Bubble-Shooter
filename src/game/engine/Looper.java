package game.engine;

public class Looper {
	private Scene scene;
	private long sleepTime;
	private boolean running;
	private Thread looperThread;
	public Looper(Scene scene , long sleepTime) {
		this.scene = scene;
		this.sleepTime = sleepTime;
	}
	public void start(){
		this.running = true;
		this.scene.getLayerManager().start();
	looperThread =new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(sleepTime);
					loop();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	looperThread.start();
		
	}
	
	public void stop(){
		this.running = false;
	}
	
	long t1 = 0;
	long t2 = 0;
	long fps = 0;
	int nsec = 5;
	boolean paused;
	private void loop() throws InterruptedException{
		t1 = System.currentTimeMillis();
		while(running){
			while(paused){
				Thread.sleep(2000);
			}
			scene.run();
			fps++;
			t2 = System.currentTimeMillis();
			
			if(t2 - t1 >= nsec*1000){
//				System.out.println("t: "+(t2-t1)+" FPS: "+(fps/nsec));
				fps = 0;
				t1 = t2;
//				System.out.println("running");
			}
			if(!running)
				break;
			else{
//			    scene.getLayerManager().canvasThread.join();
				Thread.sleep(sleepTime);
				
			}
				
		}
	}
	public void pause() {
		// TODO Auto-generated method stub
//		running = false;
		paused = true;
		System.out.println("paused");
	}
	public void resume(){
//		running = true;
		paused = false;
		System.out.println("looper resumed");
//		looperThread.run();
	}

}
