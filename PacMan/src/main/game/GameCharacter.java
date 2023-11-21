package main.game;

import main.MainGame;

public class GameCharacter {
	
	private final Position pos = new Position();
	private Direction dir = Direction.up;

	
	public Position getPosition() {
		return pos;
	}
	
	public Direction getDir() {
		return dir;
	}
	
	
	public void setPosition(int x, int y) {
		this.pos.setX(x);
		this.pos.setY(y);
	}
	
	public void setDirection(Direction dir) {
		this.dir = dir;
	}
	
	

	public boolean wallFront(MainGame game) {
		
		Position front = this.getPosition().getNeighbor(this.dir);
		
		return front.isWall(game);
		
	}
	
	public void removeCoin(MainGame game) {
		if(this.getPosition().isCoin(game)) {
			game.gameWorld[this.pos.getY()][this.pos.getX()] = 0;
			game.addPointsCoin();
		}

		
	}
	
	public void removePowerCell(MainGame game) {
		if(this.getPosition().isPowerCell(game)) {
			game.gameWorld[this.pos.getY()][this.pos.getX()] = 0;
			game.addPointsCell();
			
			game.redG.setFleeTimer(game.fleeTime());
			game.pinkG.setFleeTimer(game.fleeTime());
			game.blueG.setFleeTimer(game.fleeTime());
			game.orangeG.setFleeTimer(game.fleeTime());
			
			System.out.println(this.getPosition().isPowerCell(game));
			
		}
	}
	
	
	public void move() {

		this.pos.addVector(this.dir);

	}
	
	
	

	
}
