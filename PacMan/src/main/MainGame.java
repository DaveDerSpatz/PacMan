package main;

import java.awt.event.KeyEvent;



import main.game.*;
import main.game.Ghost.State;


public class MainGame {
	
	public final int WIDTH = 900;
	public final int HEIGHT = 1090;
	public final float scale = 0.9f;
	
	public final int pacStartX = 14, pacStartY = 23;
	
	private int gameTime = 0;
	private int level = 1;
	private int points = 0;
	private int highScore = 0;

	public int getHighScore() {
		return highScore;
	}


	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}


	public GameCharacter pacMan = new GameCharacter();
	
	public RedGhost redG = new RedGhost(this);
	public PinkGhost pinkG = new PinkGhost(this);
	public BlueGhost blueG = new BlueGhost(this);
	public OrangeGhost orangeG = new OrangeGhost(this);
	
	public int[][] gameWorld = copyWorld(START_WORLD);

	public int getPoints() {
		return points;
	}


	public void setPoints(int points) {
		this.points = points;
	}
	
	public void addPoints(int x) {
		this.points = this.points + x;
	}
	
	public void addGameTime() {
		this.gameTime = this.gameTime + 1;
	}


	/*fieldType
		0 = free,
		1 = wall,
		2 = coin
		3 = power cell
		4 = door
		5 = house
	 */
	private final static int[][] START_WORLD = {
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},	//Z 0
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},	//Z 1
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},	//Z 2
			{1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1},	//Z 3
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},	//Z 4
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},	//Z 5
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},	//Z 6
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},	//Z 7
			{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},	//Z 8
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},	//Z 9
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},	//Z10
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},	//Z11
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 5, 5, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},	//Z12
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 5, 5, 5, 5, 5, 5, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},	//Z13
			{0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 5, 5, 5, 5, 5, 5, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},	//Z14
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 5, 5, 5, 5, 5, 5, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},	//Z15
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},	//Z16
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},	//Z17
			{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},	//Z18
			{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},	//Z19
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},	//Z20
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},	//Z21
			{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},	//Z22
			{1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1},	//Z23
			{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},	//Z24
			{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},	//Z25
			{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},	//Z26
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},	//Z27
			{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},	//Z28
			{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},	//Z29
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},	//Z30
	};
	
	
	
	public MainGame () {
		

		pacMan.setDirection(Direction.up);
		pacMan.setPosition(pacStartX, pacStartY);
		

	}
	
	
	private Direction updatePacDir(KeyEvent key) {
		
		if(key == null) {
			return this.pacMan.getDir();
		}
		
		if(key.getKeyCode() == KeyEvent.VK_UP) {
			return Direction.up;
		} else
		
		if(key.getKeyCode() == KeyEvent.VK_RIGHT) {
			return Direction.right;
		} else
		
		if(key.getKeyCode() == KeyEvent.VK_DOWN) {
			return Direction.down;
		} else
		
		if(key.getKeyCode() == KeyEvent.VK_LEFT) {
			return Direction.left;
		}
		
		else {
			return this.pacMan.getDir();
		}
		
	}
	
	
	public void update(KeyEvent key) {
		
		System.out.println(this.highScore);
		
		this.redG.eatPacMan(this);
		this.pinkG.eatPacMan(this);
		this.blueG.eatPacMan(this);
		this.orangeG.eatPacMan(this);
		
		if(!pacMan.wallFront(this)) {
			pacMan.move();
		}
		
		Direction newDir = updatePacDir(key);
		if(!this.pacMan.getPosition().getNeighbor(newDir).isWall(this)) {
			pacMan.setDirection(newDir);
		}
		pacMan.removeCoin(this);
		pacMan.removePowerCell(this);
		
		redG.move();
		pinkG.move();
		blueG.move();
		orangeG.move();
		
		if(!this.coinLeft()) {
			this.restart();
			
			this.level = this.level + 1;
		}

		//TODO
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public int[][] getGameWorld() {
		return gameWorld;
	}


	public void setGameWorld(int[][] gameWorld) {
		this.gameWorld = gameWorld;
	}
	
	public void setStateAll(State state) {
		this.redG.setState(state);
		this.pinkG.setState(state);
	}
	
	public State getState() {
		/*
		switch (this.level) {
		case 1:
			if(this.gameTime < 10) {
				return State.hunt;
			} else if(this.gameTime < 15) {
				return State.scater;
			}
		}
		*/
		return State.hunt;
	}
	
	public void restart() {
		this.pacMan.setPosition(pacStartX, pacStartY);
		this.redG.toStart();
		this.redG.setState(State.house);
		this.pinkG.toStart();
		this.pinkG.setState(State.house);
		this.blueG.toStart();
		this.blueG.setState(State.house);
		this.orangeG.toStart();
		this.orangeG.setState(State.house);
		
		this.gameWorld = copyWorld(START_WORLD);
		this.pacMan.setDirection(Direction.left);
		
		if(this.points > this.highScore) {
			this.setHighScore(this.getPoints());
		}
		
	}
	
	
	public static int[][] copyWorld(int[][] world){
		
		int lengthY = world.length;
		int lengthX = world[1].length;
		
		int[][] res = new int[lengthY][lengthX];
		
		for(int y = 0; y < lengthY; y++) {
			for(int x = 0; x < lengthX; x++) {
				res[y][x] = world[y][x];
			}
		}
		
		return res;
		
		
	}
	
	public boolean coinLeft() {
		
		for(int y = 0; y < this.gameWorld.length; ++y) {
			for(int x = 0; x < this.gameWorld[y].length; ++x) {
				if(this.gameWorld[y][x] == 2) {
					return true;
				}
			}
		}
		
		return false;

	}
	
	public void addPointsCoin() {
		this.addPoints(20  * this.level);
	}
	
	public void addPointsCell() {
		this.addPoints(100  * this.level);
	}
	
	public void addPointsGhost() {
		this.addPoints(200 * this.level);
	}
	
	public int fleeTime() {
		int res = 60 - (this.level - 1);
		
		return res;
	}
	
	public static void main (String[] arg) {
		MainGame game = new MainGame();
		
		Position p = new Position();
		p.setX(11);
		p.setY(22);
		game.redG.updateDestination();
		
		game.redG.getPosition().setX(11);
		game.redG.getPosition().setY(11);
		game.redG.setDirection(Direction.left);
		
		game.pacMan.setPosition(12, 20);
		
		game.gameWorld = copyWorld(START_WORLD);
		
		game.gameWorld[1][1] = 919;
		
		System.out.println(START_WORLD[1][1]);
		
		
		
		System.out.println(game.redG.getPosition().getX() + " " + game.redG.getPosition().getY());
		
		System.out.println(game.redG.getPosition().getDistance(game.pacMan.getPosition()));
		
	}
	
}
