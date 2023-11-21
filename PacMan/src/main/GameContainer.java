package main;

import main.gui.GUI;

import main.gui.Renderer;

public class GameContainer implements Runnable {
	
	private Thread thread;
	private Boolean render = false;
	private Boolean running = false;
	private final double UPDATE_CAP = 1.0/8.0;
	
	
	public MainGame game = new MainGame();
	private GUI gui;
	private Renderer renderer;
	private Input input;
	
	public void start() {
		
		gui = new GUI(game);
		renderer = new Renderer(this);
		input = new Input(this);
		
		thread = new Thread(this);
		thread.run();
		
	}
	
	
	public void run() {
		
		this.running = true;
		
		double firstTime = .0;
		double lastTime = System.nanoTime() / 1000000000.0;
		double passedTime = .0;
		double unprosessedTime = .0;
		
		while(running) {
			
			firstTime = System.nanoTime() / 1000000000.0;
			passedTime = firstTime - lastTime;
			lastTime = firstTime;
			unprosessedTime += passedTime;
			
			
			while(unprosessedTime >= UPDATE_CAP) {
				
				unprosessedTime -= UPDATE_CAP;
				input.update();
				render = true;
				//TODO: UpdateGame
				
				this.game.addGameTime();
				
				game.update(this.input.key);

				
			}
			
			if(render) {
				
				//TODO:renderGame
				gui.update(this.game, this);
				
			} else {
				
				try {
					Thread.sleep(1);
				} catch(InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
		dispose();
		
	}
	
	private void dispose() {
		
	}
	
	
	public GUI getGUI() {
		return this.gui;
	}
	
	public Renderer getRenderer() {
		return this.renderer;
	}
	
	
	public static void main(String[] args) {
		
		GameContainer gc = new GameContainer();
		
		gc.start();	
	}
	
	
}
