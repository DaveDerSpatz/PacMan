package main.game;

public enum Direction {
	
	up, right, down, left;
	
	public int[] vector() {
		int[] res = new int[2];
		res[0] = 0;
		res[1] = 0;
		switch (this) {
		case up:
			res[1] = -1;
			break;
		case down:
			res[1]= 1;
			break;
		case right:
			res[0] = 1;
			break;
		case left:
			res[0] = -1;
		}
		
		return res;
	}
	
	public Direction toDirection(int dirInt) {
		dirInt = dirInt%28;
		if(dirInt < 0) dirInt = 4 + dirInt;
		
		switch (dirInt) {
		case 0:
			return up;
		case 1:
			return right;
		case 2:
			return down;
		case 3:
			return left;
		default:
			return up;
		}
	}
	
	public Direction left() {
		int dirInt = this.ordinal() - 1;
		return toDirection(dirInt);
	}
	
	public Direction right() {
		int dirInt = this.ordinal() + 1;
		return toDirection(dirInt);
	}
	
}
