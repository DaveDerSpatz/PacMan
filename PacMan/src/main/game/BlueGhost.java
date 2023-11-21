package main.game;

import main.MainGame;
import main.gui.gfx.Image;

public class BlueGhost extends Ghost {
	
	public final Kind color = Kind.red;
	
	private final int START_X = 12;
	private final int START_Y = 14;
	
	
	
	private final int SCATTER_X = 1, SCATTER_Y = 1;
	
	private final MainGame game;
	
	private Image aniUp01 = new Image("/BlueGhost01.png");
	private Image aniUp02 = new Image("/BlueGhost02.png");
	private Image aniRight01 = new Image("/BlueGhostRight01.png");
	private Image aniRight02 = new Image("/BlueGhostRight02.png");
	private Image aniDown01 = new Image("/BlueGhostDown01.png");
	private Image aniDown02 = new Image("/BlueGhostDown02.png");
	private Image aniLeft01 = new Image("/BlueGhostLeft01.png");
	private Image aniLeft02 = new Image("/BlueGhostLeft02.png");
	
	private Image[][] animations = {{aniUp01, aniRight01, aniDown01, aniLeft01},
										{aniUp02, aniRight02, aniDown02, aniLeft02}
									};
	
	public BlueGhost(MainGame game) {
		this.game = game;
		this.setPosition(START_X, START_Y);
		this.setScatterPosition(SCATTER_X, SCATTER_Y);
		this.setDirection(Direction.up);
		this.setState(State.house);
	}
	
	@Override
	public void toStart() {
		this.setPosition(START_X, START_Y);
	}
	

	@Override
	public void updateDestination() {
		
		switch(this.getState()) {
		case hunt:
			this.setDestination(this.newDest());
			break;
		case scater:
			this.setDestination(this.getScatterPosition());
			break;
		case flee:
			this.setDestination(this.getPosition());
		case eaten:
			this.setDestination(super.eatenPosition);
			break;
		case house:
			this.setDestination(super.leaveHousePosition);
			
		}

		
	}
	
	private Position newDest() {
		
		int newX = this.getPosition().getX() + (game.pacMan.getPosition().getX() - game.redG.getPosition().getX());
		
		int newY = this.getPosition().getY() + (game.pacMan.getPosition().getY() - game.redG.getPosition().getY());
		
		return new Position(newX, newY);
	}


	@Override
	public void move() {
		this.moveAbsract(game);
	}


	@Override
	public  Image getAnimation() {
		
		this.addAnimNum();
		
		if(this.getState() == State.flee) {
			switch(this.getDir()) {
			case up:
				return super.animations[this.getAnimNum()][Direction.up.ordinal()];
			case right:
				return super.animations[this.getAnimNum()][Direction.right.ordinal()];
			case down:
				return super.animations[this.getAnimNum()][Direction.down.ordinal()];
			default:
				return super.animations[this.getAnimNum()][Direction.left.ordinal()];
				
			}
		}
		
		if(this.getState() == State.eaten) {
			switch(this.getDir()) {
			case up:
				return super.fleeAnimations[this.getAnimNum()][Direction.up.ordinal()];
			case right:
				return super.fleeAnimations[this.getAnimNum()][Direction.right.ordinal()];
			case down:
				return super.fleeAnimations[this.getAnimNum()][Direction.down.ordinal()];
			default:
				return super.fleeAnimations[this.getAnimNum()][Direction.left.ordinal()];
			}
		}
		
		switch(this.getDir()) {
		case up:
			return this.animations[this.getAnimNum()][Direction.up.ordinal()];
		case right:
			return this.animations[this.getAnimNum()][Direction.right.ordinal()];
		case down:
			return this.animations[this.getAnimNum()][Direction.down.ordinal()];
		default:
			return this.animations[this.getAnimNum()][Direction.left.ordinal()];
			
		}
	}

}
