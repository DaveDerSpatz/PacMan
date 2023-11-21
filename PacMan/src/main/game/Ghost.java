package main.game;

import main.MainGame;

import main.gui.gfx.Image;

public abstract class Ghost extends GameCharacter {
	
	private Position destination;
	private State state;
	private Position scatterPosition = new Position();
	public final Position eatenPosition = new Position(14, 13);
	public final Position leaveHousePosition = new Position(14, 10);
	
	private int fleeTimer;
	
	private Image aniUp01 = new Image("/FleeGhost01.png");
	private Image aniUp02 = new Image("/FleeGhost02.png");
	
	private Image fleeAniUp01 = new Image("/EatenGhost01.png");
	private Image fleeAniUp02 = new Image("/EatenGhost02.png");
	private Image fleeAniRight01 = new Image("/EatenGhostRight01.png");
	private Image fleeAniRight02 = new Image("/EatenGhostRight02.png");
	private Image fleeAniDown01 = new Image("/EatenGhostDown01.png");
	private Image fleeAniDown02 = new Image("/EatenGhostDown02.png");
	private Image fleeAniLeft01 = new Image("/EatenGhostLeft01.png");
	private Image fleeAniLeft02 = new Image("/EatenGhostLeft02.png");

	
	public Image[][] animations = {{aniUp01, aniUp01, aniUp01, aniUp01},
										{aniUp02, aniUp02, aniUp02, aniUp02}
										};
	
	public Image[][] fleeAnimations = {{fleeAniUp01, fleeAniRight01, fleeAniDown01, fleeAniLeft01},
			{fleeAniUp02, fleeAniRight02, fleeAniDown02, fleeAniLeft02}
			};
	
	private int animNum = 0;
	private final int ANIMS = 2;

	
	public Position getScatterPosition() {
		return scatterPosition;
	}

	public void setScatterPosition(int x, int y) {
		this.scatterPosition.setX(x);
		this.scatterPosition.setY(y);
	}

	public enum Kind{
		red, pink, blue, orange
	}
	
	public enum State {
		hunt, scater, flee, eaten, house
	}
	
	public State getState() {
		return this.state;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public Position getDestination() {
		return this.destination;
	}
	
	public void setDestination(Position newDest) {
		this.destination =  newDest;
	}
	
	
	public abstract void updateDestination();
	
	@Override
	public abstract void move();

	public void moveAbsract(MainGame game) {
		this.updateDestination();
		this.moveToDestination(this.getDestination(), game);
	}
	
	
	public void moveToDestination(Position dest, MainGame game) {
		
		updateState(game);

		
		Position left = this.getPosition().getNeighbor(this.getDir().left());
		Position right = this.getPosition().getNeighbor(this.getDir().right());
		Position front = this.getPosition().getNeighbor(this.getDir());
	
		
		int leftD;
		int rightD;
		int frontD;
		
		leftD = left.getDistance(dest);
		rightD = right.getDistance(dest);
		frontD = front.getDistance(dest);
		
		if(left.isWall(game)) {
			leftD = 999999999;
		}
		
		if(left.isHouse(game)) {
			leftD = leftD + this.houseValue();
		}
		
		if(right.isWall(game)) {
			rightD = 999999999;
		}
		
		if(right.isHouse(game)) {
			rightD = rightD + this.houseValue();
		}
		
		if(front.isWall(game)) {
			frontD = 999999999;
		}
		
		if(front.isHouse(game)) {
			frontD = frontD + this.houseValue();
		}
		
		if(frontD <= rightD && frontD < leftD) {
			
		} else if (leftD <= frontD && leftD <= rightD){
			this.setDirection(this.getDir().left());
		} else {
			this.setDirection(this.getDir().right());
		}
		

		super.move();
	}
		
	public boolean onPacMan(MainGame game) {
		
		if(this.getPosition().getX() == game.pacMan.getPosition().getX() && this.getPosition().getY() == game.pacMan.getPosition().getY()){
			return true;
		}
		
		return this.getPosition() == game.pacMan.getPosition();
	}
	
	public void eatPacMan(MainGame game) {
		if(onPacMan(game)) {
			if(!(this.state == State.flee) && !(this.state == State.eaten)) {
				game.restart();
				game.setLevel(1);
				game.setPoints(0);
				
			} else if(this.state == State.flee){
				this.setState(State.eaten);
				this.setFleeTimer(0);
				game.addPoints(200);
			}
		}
	}
	
	
	public void addAnimNum() {
		this.animNum = this.animNum + 1;
		this.animNum = this.animNum%ANIMS;
	}
	
	public  Image getAnimation() {
		
		switch(this.getDir()) {
		case up:
			return this.animations[this.animNum][Direction.up.ordinal()];
		case right:
			return this.animations[this.animNum][Direction.right.ordinal()];
		case down:
			return this.animations[this.animNum][Direction.down.ordinal()];
		default:
			return this.animations[this.animNum][Direction.left.ordinal()];
			
		}

	}
	
	public int getAnimNum() {
		return animNum;
	}

	public abstract void toStart();
	
	
	public void updateState(MainGame game) {
		if(this.fleeTimer > 0) {
			this.setState(State.flee);
			this.setFleeTimer(this.fleeTimer - 1);
		}
		else if(this.state == State.eaten) {
			
			if(this.getPosition().isHouse(game)) {
				this.setState(State.house);
			}
			else {
				this.setState(State.eaten);
			}
			
		} else if(this.state == State.house) {
			if(this.getPosition().isHouse(game)) {
				this.setState(State.house);
			} else {
				this.setState(State.hunt);
			}
		}
		
		else {
			this.setState(game.getState());
		}
	}
	
	
	public int houseValue() {
		if(this.state == State.eaten) {
			return 0;
		}
		else {
			return 2000000;
		}
	}

	public int getFleeTimer() {
		return fleeTimer;
	}

	public void setFleeTimer(int fleeTimer) {
		this.fleeTimer = fleeTimer;
	}
	
	
}
