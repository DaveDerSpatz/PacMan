package main.game;

import main.MainGame;
import main.game.Ghost.State;
import main.gui.gfx.Image;

public class OrangeGhost extends Ghost {
	
	public final Kind color = Kind.red;
	
	private final int START_X = 16;
	private final int START_Y = 14;
	
	private final int SCATTER_X = 1, SCATTER_Y = 30;
	
	private final MainGame game;
	
	private Image aniUp01 = new Image("/OrangeGhost01.png");
	private Image aniUp02 = new Image("/OrangeGhost02.png");
	private Image aniRight01 = new Image("/OrangeGhostRight01.png");
	private Image aniRight02 = new Image("/OrangeGhostRight02.png");
	private Image aniDown01 = new Image("/OrangeGhostDown01.png");
	private Image aniDown02 = new Image("/OrangeGhostDown02.png");
	private Image aniLeft01 = new Image("/OrangeGhostLeft01.png");
	private Image aniLeft02 = new Image("/OrangeGhostLeft02.png");
	
	private Image[][] animations = {{aniUp01, aniRight01, aniDown01, aniLeft01},
										{aniUp02, aniRight02, aniDown02, aniLeft02}
									};
	
	public OrangeGhost(MainGame game) {
		this.game = game;
		this.setPosition(START_X, START_Y);
		this.setScatterPosition(SCATTER_X, SCATTER_Y);
		this.setDirection(Direction.left);
		this.setState(State.hunt);
	}
	
	@Override
	public void toStart() {
		this.setPosition(START_X, START_Y);
	}
	

	@Override
	public void updateDestination() {
		switch(this.getState()) {
		case hunt:
			if(this.getPosition().getDistance(game.pacMan.getPosition()) > 8) {
				this.setDestination(game.pacMan.getPosition());
				}
			else {
				this.setDestination(this.getScatterPosition());
			}
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
