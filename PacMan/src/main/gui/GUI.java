package main.gui;

import java.awt.BorderLayout;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import main.GameContainer;
import main.MainGame;
import main.gui.gfx.Image;


public class GUI{
	
	private JFrame frame;
	private BufferedImage image;
	private Canvas canvas;
	private BufferStrategy bs;
	private Graphics g;
	
	
	public Graphics getG() {
		return g;
	}
	

	private static Image pacImage;
	
	
	
	public GUI(MainGame gc) {
		image = new BufferedImage(gc.WIDTH, gc.HEIGHT, BufferedImage.TYPE_INT_RGB);
		canvas = new Canvas();
		Dimension s = new Dimension((int) (gc.WIDTH * gc.scale), (int) (gc.HEIGHT * gc.scale));
		canvas.setPreferredSize(s);
		canvas.setMaximumSize(s);
		canvas.setMinimumSize(s);
		canvas.setFocusable(true);
		canvas.requestFocus();
		
		JFrame frame = new JFrame("Pac-Man");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(canvas, BorderLayout.CENTER);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setName("PacMan");
		
		canvas.createBufferStrategy(2);
		bs = canvas.getBufferStrategy();
		g = bs.getDrawGraphics();
		
		
		
		pacImage = new Image("/PacMan01.png");
	}
	
	
	public void update(MainGame game, GameContainer gc) {
		gc.getRenderer().clear();
		
		
		gc.getRenderer().drawCoins(game.getGameWorld());
		
		gc.getRenderer().drawImmage(pacImage, game.pacMan.getPosition().toPixel(1) - 5, game.pacMan.getPosition().toPixel(0) - 5);
		
		//redGhost
		gc.getRenderer().drawImmage(game.redG.getAnimation(), game.redG.getPosition().toPixel(1) - 5, game.redG.getPosition().toPixel(0) - 5);
		//pink Ghost
		gc.getRenderer().drawImmage(game.pinkG.getAnimation(), game.pinkG.getPosition().toPixel(1) - 5, game.pinkG.getPosition().toPixel(0) - 5);
		//blue Ghost
		gc.getRenderer().drawImmage(game.blueG.getAnimation(), game.blueG.getPosition().toPixel(1) - 5, game.blueG.getPosition().toPixel(0) - 5);
		//orange Ghost
		gc.getRenderer().drawImmage(game.orangeG.getAnimation(), game.orangeG.getPosition().toPixel(1) - 5, game.orangeG.getPosition().toPixel(0) - 5);
		
		g.drawImage(image, 0, 0, canvas.getWidth(), canvas.getHeight(), null);
		bs.show();
	}

	public Canvas getCanvas() {
		return this.canvas;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	
	public void render() {
		int p[] = ((DataBufferInt) this.getImage().getRaster().getDataBuffer()).getData();
		
		for(int i = 0; i < p.length; i++) {
			
			((DataBufferInt) this.getImage().getRaster().getDataBuffer()).getData()[i] += i;
			//p[i] += i;
		}
	}

	
	
}
