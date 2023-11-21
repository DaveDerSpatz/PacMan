package main;

import java.awt.event.KeyEvent;

import java.awt.event.KeyListener;


public class Input implements KeyListener{
	
	private KeyEvent lastKey;
	public KeyEvent key;
	
	public Input(GameContainer gc) {
		
		gc.getGUI().getCanvas().addKeyListener(this);		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
		}
		
		
		
		this.lastKey = e;
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.lastKey = e;
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		this.lastKey = e;
		
	}
	
	public void update() {

		this.key = this.lastKey;
		
	}
	
	
}
