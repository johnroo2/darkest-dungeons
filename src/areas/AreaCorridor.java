package areas;

import control.Controller;
import project.Area;
import project.Tile;

public class AreaCorridor extends Area{
	private int dir;
	
	public AreaCorridor(int x, int y, int width, int height, int dir, Tile border, Tile inner) {
		super(x, y, width, height, border, inner);
		this.grid = rectBox(width, height, border, inner);
		this.setDir(dir);
		
		exits = new int[8];
		if(dir % 2 == 0) {
			exits = new int[] {0, width-2, 1, 0, 0, width-2, 1, height-1};
		}
		else {
			exits = new int[] {1, height-2, 0, 1, 1, height-2, width-1, 1};
		}
		for(int i = 0; i < exits.length; i += 4) {
			for(int j = 0; j < exits[i+1]; j++) {
				if(exits[i] == 0) { //move by x
					set(exits[i+2]+j, exits[i+3], inner);
				}
				else {
					set(exits[i+2], exits[i+3]+j, inner);
				}
			}
		}
	}
	
	public void randomStreak(Tile streak) {
		if(this.getDir() % 2 == 1) {
			if(Controller.random.nextInt(0, 2) == 0) { //up
				int start = 0;
				int cutoff = getWidth();
				for(int i = getHeight()-2; i >= 2; i--) {
					this.setLine(0, cutoff-start, start, i, streak);
					start += Controller.random.nextInt(1, 3);
					cutoff -= Controller.random.nextInt(1, 3);
					if(cutoff <= 0) {
						break;
					}
				}
			}
			else { //down
				int start = 0;
				int cutoff = getWidth();
				for(int i = 1; i <= getHeight()-3; i++) {
					this.setLine(0, cutoff-start, start, i, streak);
					start += Controller.random.nextInt(1, 3);
					cutoff -= Controller.random.nextInt(1, 3);
					if(cutoff <= 0) {
						break;
					}
				}
			}
		}
		else {
			if(Controller.random.nextInt(0, 2) == 0) { //left
				int start = 0;
				int cutoff = getHeight();
				for(int i = getWidth()-2; i >= 2; i--) {
					this.setLine(1, cutoff-start, i, start, streak);
					start += Controller.random.nextInt(1, 3);
					cutoff -= Controller.random.nextInt(1, 3);
					if(cutoff <= 0) {
						break;
					}
				}
			}
			else { //right
				int start = 0;
				int cutoff = getHeight();
				for(int i = 1; i <= getWidth()-3; i++) {
					this.setLine(1, cutoff-start, i, start, streak);
					start += Controller.random.nextInt(1, 3);
					cutoff -= Controller.random.nextInt(1, 3);
					if(cutoff <= 0) {
						break;
					}
				}
			}
		}
		
	}
	
	public AreaCorridor(int x, int y, int width, int height, int dir, Tile border, Tile inner, int[] exits) {
		super(x, y, width, height, border, inner);
		this.grid = rectBox(width, height, border, inner);
		int[] newExit = new int[exits.length + 8];
		for(int i = 0; i < exits.length; i++) {
			newExit[i] = exits[i];
		}
		int[] addArray;
		if(dir % 2 == 0) {
			addArray = new int[] {0, width-2, 1, 0, 0, width-2, 1, height-1};
		}
		else {
			addArray = new int[] {1, height-2, 0, 1, 1, height-2, width-1, 1};
		}
		for(int i = 0; i < 8; i++) {
			newExit[exits.length + i] = addArray[i];
		}
		this.exits = newExit;
		for(int i = 0; i < exits.length; i += 4) {
			for(int j = 0; j < exits[i+1]; j++) {
				if(exits[i] == 0) { //move by x
					set(exits[i+2]+j, exits[i+3], inner);
				}
				else {
					set(exits[i+2], exits[i+3]+j, inner);
				}
			}
		}
		for(int i = 0; i < exits.length; i += 4) {
			for(int j = 0; j < exits[i+1]; j++) {
				if(exits[i] == 0) { //move by x
					set(exits[i+2]+j, exits[i+3], inner);
				}
				else {
					set(exits[i+2], exits[i+3]+j, inner);
				}
			}
		}
		this.exits = exits;
	}
	
	@Override
	public void repair() {
		this.setLine(0, getWidth(), 0, 0, border);
		this.setLine(1, getHeight(), getWidth()-1, 0, border);
		this.setLine(0, getWidth(), 0, getHeight()-1, border);
		this.setLine(1, getHeight(), 0, 0, border);
		this.fillExits();
	}

	public int getDir() {
		return dir;
	}

	public void setDir(int dir) {
		this.dir = dir;
	}
}
