package main.game;

import main.MainGame;

public class Position {
	private int x;
	private int y;
	
	public Position() {
		
	}
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setPosition(Position pos) {
		this.x = pos.x;
		this.y = pos.y;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(final int x) {
		
		this.x = x%28;
		if(this.x < 0) this.x = 28 + this.x;
	}
	
	public void setY(final int y) {
		this.y = y%31;
		if(this.y < 0) this.y = 28 + this.y;
	}
	
	public int toPixel(int _1ElseY) {
		
		if(_1ElseY == 1) {
			return this.x * 30 + 30;
		}
		return this.y * 30 + 130;

		
	}
	
	public Position getNeighbor(Direction dir) {
		Position pos = new Position();
		pos.setPosition(this);
		pos.addVector(dir);
		return pos;
	}
	
	public void addVector(Direction dir) {
		
		
		int[] vec = dir.vector();
		this.setX(this.x + vec[0]);
		this.setY(this.y + vec[1]);
	}
	
	public int getField(MainGame game) {
		
		return game.getGameWorld()[y][x];
		
	}
	
	public boolean isWall(MainGame game) {
		int field = this.getField(game);
		return (field == 1);
	}
	
	public boolean isCoin(MainGame game) {
		int field = this.getField(game);
		return (field == 2);
	}
	
	public boolean isPowerCell(MainGame game) {
		int field = this.getField(game);
		return (field == 3);
	}
	
	public boolean isHouse(MainGame game) {
		int field = this.getField(game);
		return  (field == 5);
	}
	
	public int getDistance(Position dest) {
		
		double x  = (double) this.getX() - (double) dest.getX();
		if(x < 0) x = x * -1.0;
		
		double y = (double) this.getY() - (double) dest.getY(); 
		if(y < 0) y = y * -1.0;
		
		double dist;
		dist = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
		
		dist = dist * 1000;
		
		return (int) dist;
		
	}



	
}
