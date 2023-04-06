package project;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.PriorityQueue;

import control.Controller;

public abstract class Area {
	//static variables
	public final static int BLOCK_SIZE = 40;
	public static ArrayList<Area> list = new ArrayList<>();
	
	//instance variables
	private int x, y;
	protected Tile[][] grid;
	protected Tile border;
	protected Tile inner;
	private int width;
	private int height;
	protected int[] exits;
	private PriorityQueue<Enemy> enemies = new PriorityQueue<Enemy>(Controller.strengthCompare);
	
	//constructor given a box
	public Area(int x, int y, int width, int height, Tile border, Tile inner) {
		this.x = x;
		this.y = y;
		this.setWidth(width);
		this.setHeight(height);
		this.border = border;
		this.inner = inner;
		Area.list.add(this);
	}
	
	//constructor given a radius
	public Area(int x, int y, int radius, Tile border, Tile inner) {
		this.x = x;
		this.y = y;
		this.border = border;
		this.inner = inner;
		this.setWidth(2 * radius);
		this.setHeight(2 * radius);
		Area.list.add(this);
	}
	
	//contstructor given a set grid
	public Area(int x, int y, Tile[][] grid, Tile border, Tile inner) {
		this.x = x;
		this.y = y;
		this.grid = grid;
		this.border = border;
		this.inner = inner;
		this.setWidth(grid[0].length);
		this.setHeight(grid.length);
		Area.list.add(this);
	}
	
	//checks if this area is in sight range
	public boolean drawable() {
		if(Controller.chr.getX() > this.x - 500 && Controller.chr.getX() < this.x + this.getWidth() * BLOCK_SIZE + 500 &&
				Controller.chr.getY() > this.y - 500 && Controller.chr.getY() < this.y + this.getHeight() * BLOCK_SIZE + 500) {
			return true;
		}
		return false;
	}
	
	//gets the value at a tile
	public Tile get(int x, int y) {
		if(x < 0 || x > grid[0].length-1 || y < 0 || y > grid.length-1) {
			//System.out.println("getting out of bounds");
			return null;
		}
		return grid[y][x];
	}
	 
	//gets the value at a tile for doubles
	public Tile get(double x, double y) {
		if(x < 0 || x > grid[0].length-1 || y < 0 || y > grid.length-1) {
			//System.out.println("getting out of bounds");
			return null;
		}
		return grid[(int) y][(int) x];
	}
	 
	//sets the value at a tile
	public void set(int x, int y, Tile value) {
		if(x < 0 || x > grid[0].length-1 || y < 0 || y > grid.length-1) {
			//System.out.println("setting out of bounds");
			return;
		}
		grid[y][x] = value;
	}
	
	//copies the grid at a coordinate
	public void copySet(int x, int y, Tile[][] grid) {
		if(x < 0 || x > grid[0].length-1 || y < 0 || y > grid.length-1) {
			//System.out.println("setting out of bounds");
			return;
		}
		this.grid[y][x] = grid[y][x];
	}
	
	//sets a line of values
	public void setLine(int dir, int size, int x, int y, Tile value) {
		if(dir == 0) {
			for(int i = 0; i < size; i++) {
				this.set(x + i, y, value);
			}
		}
		else if(dir == 1) {
			for(int i = 0; i < size; i++) {
				this.set(x, y+i, value);
			}
		}
	}
	
	//sets a box of values, null in the middle
	public void setBox(int width, int height, int x, int y, Tile value) {
		for(int i = x; i < x + width; i++) {
			for(int j = y; j < y + height; j++) {
				this.set(i, j, value);
			}
		}
		for(int i = x+1; i < x + width-1; i++) {
			for(int j = y+1; j < y + height-1; j++) {
				this.set(i, j, null);
			}
		}			
	}
	
	//sets a box of values, inside is specified
	public void setBox(int width, int height, int x, int y, Tile value, Tile inner) {
		for(int i = x; i < x + width; i++) {
			for(int j = y; j < y + height; j++) {
				this.set(i, j, value);
			}
		}
		for(int i = x+1; i < x + width-1; i++) {
			for(int j = y+1; j < y + height-1; j++) {
				this.set(i, j, inner);
			}
		}
	}
	
	//sets a circle of values, does not touch the insides
	public void setCircle(int x, int y, int radius, Tile value) {
		Tile[][] prev = new Tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			prev[i] = grid[i].clone();
		}
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, prev[j+y][i+x]);
					}
				}
				else {
					this.set(i+x, j+y, prev[j+y][i+x]);
				}
			}
		}	
		for(int i = radius; i < radius * 2; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (i-radius)*(i-radius) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, prev[j+y][i+x]);
					}
				}
				else {
					this.set(i+x, j+y, prev[j+y][i+x]);
				}
			}
		}	
		for(int i = 0; i < radius; i++) {
			for(int j = radius; j < radius * 2; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (j-radius)*(j-radius);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, prev[j+y][i+x]);
					}
				}
				else {
					this.set(i+x, j+y, prev[j+y][i+x]);
				}
			}
		}	
		for(int i = radius; i < radius * 2; i++) {
			for(int j = radius; j < radius * 2; j++) {
				int dist = (i-radius)*(i-radius) + (j-radius)*(j-radius);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, prev[j+y][i+x]);
					}
				}
				else {
					this.set(i+x, j+y, prev[j+y][i+x]);
				}
			}
		}
		this.fillExits();
	}
	
	//sets a circle of values, inside is specified
	public void setCircle(int x, int y, int radius, Tile value, Tile inner) {
		Tile[][] prev = new Tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			prev[i] = grid[i].clone();
		}
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, inner);
					}
				}
				else {
					this.copySet(i+x, j+y, prev);
				}
			}
		}	
		for(int i = radius; i < radius * 2; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (i-radius)*(i-radius) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, inner);
					}
				}
				else {
					this.copySet(i+x, j+y, prev);
				}
			}
		}	
		for(int i = 0; i < radius; i++) {
			for(int j = radius; j < radius * 2; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (j-radius)*(j-radius);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, inner);
					}
				}
				else {
					this.copySet(i+x, j+y, prev);
				}
			}
		}	
		for(int i = radius; i < radius * 2; i++) {
			for(int j = radius; j < radius * 2; j++) {
				int dist = (i-radius)*(i-radius) + (j-radius)*(j-radius);
				if(dist < radius * radius) {
					this.set(i+x, j+y, value);
					if(dist < (radius-1)*(radius-1)) {
						this.set(i+x, j+y, inner);
					}
				}
				else {
					this.copySet(i+x, j+y, prev);
				}
			}
		}
		this.fillExits();
	}
	
	//gets an arraylist of jump points (streaking)
	public ArrayList<Integer> getPoints(int max, int points){
		ArrayList<Integer> out = new ArrayList<>();
		LinkedList<Integer> li = new LinkedList<>();
		for(int i = 0; i < max; i++) {
			li.push(i);
		}
		Collections.shuffle(li);
		for(int i = 0; i < points; i++) {
			if(li.peek()!= null) {
				out.add(li.poll());
			}
		}
		Collections.sort(out);
		return out;
	}
	
	//sets a downwards streak with repair
	public void setStreakDown(int x, int y, int xLength, int yLength, int thickness, Tile value) {
		setStreakDown(x, y, xLength, yLength, thickness, value, true);
	}
	
	//sets an upwards streak with repair
	public void setStreakUp(int x, int y, int xLength, int yLength, int thickness, Tile value) {
		setStreakUp(x, y, xLength, yLength, thickness, value, true);
	}

	//sets a downward streak 
	public void setStreakDown(int x, int y, int xLength, int yLength, int thickness, Tile value, boolean repair) {
		if(xLength > yLength) { //flat
			ArrayList<Integer> breaks = getPoints(xLength, yLength);
			ArrayList<Integer> jumps = getPoints(xLength, 2*(thickness-1));
			int height = y;
			int size = 1;
			int steps = 0;
			for(int i = 0; i < xLength; i++) {
				int start = -size/2;
				for(int j = start; j < start + size; j++) {
					this.set(i + x, height + j, value);
				}
				if(breaks.contains(i)) {
					height++;
				}
				if(jumps.contains(i)) {
					if(steps < thickness-1) {
						size++;
						steps++;
					}
					else {
						size--;
						steps++;
					}
				}
			}
		}
		else if(xLength < yLength) { //steep
			ArrayList<Integer> breaks = getPoints(yLength, xLength);
			ArrayList<Integer> jumps = getPoints(yLength, 2*(thickness-1));
			int width = x;
			int size = 1;
			int steps = 0;
			for(int i = 0; i < yLength; i++) {
				int start = -size/2;
				for(int j = start; j < start + size; j++) {
					this.set(width + j, i + y, value);
				}
				if(breaks.contains(i)) {
					width++;
				}
				if(jumps.contains(i)) {
					if(steps < thickness-1) {
						size++;
						steps++;
					}
					else {
						size--;
						steps++;
					}
				}
			}
		}
		else { //45 degrees
			ArrayList<Integer> jumps = getPoints(xLength, 2 * (thickness-1));
			int size = 1;
			int steps = 0;
			for(int i = 0; i < xLength; i++) {
				int start = -size/2;
				for(int j = start; j < start + size; j++) {
					this.set(i + x, i + y + j, value);
				}
				if(jumps.contains(i)) {
					if(steps < thickness-1) {
						size++;
						steps++;
					}
					else {
						size--;
						steps++;
					}
				}
			}
		}
		if(repair) {
			this.repair();
		}
	}
	
	//sets an upward streak
	public void setStreakUp(int x, int y, int xLength, int yLength, int thickness, Tile value, boolean repair) {
		if(xLength > yLength) { //flat
			ArrayList<Integer> breaks = getPoints(xLength, yLength);
			ArrayList<Integer> jumps = getPoints(xLength, 2*(thickness-1));
			int height = y;
			int size = 1;
			int steps = 0;
			for(int i = 0; i > -xLength; i--) {
				int start = -size/2;
				for(int j = start; j < start + size; j++) {
					this.set(i + x, height - j, value);
				}
				if(breaks.contains(-i)) {
					height++;
				}
				if(jumps.contains(-i)) {
					if(steps < thickness-1) {
						size++;
						steps++;
					}
					else {
						size--;
						steps++;
					}
				}
			}
		}
		else if(xLength < yLength) { //steep
			ArrayList<Integer> breaks = getPoints(yLength, xLength);
			ArrayList<Integer> jumps = getPoints(yLength, 2*(thickness-1));
			int width = x;
			int size = 1;
			int steps = 0;
			for(int i = 0; i > -yLength; i--) {
				int start = -size/2;
				for(int j = start; j < start + size; j++) {
					this.set(width - j, i + y, value);
				}
				if(breaks.contains(-i)) {
					width++;
				}
				if(jumps.contains(-i)) {
					if(steps < thickness-1) {
						size++;
						steps++;
					}
					else {
						size--;
						steps++;
					}
				}
			}
		}
		else { //45 degrees
			ArrayList<Integer> jumps = getPoints(xLength, 2 * (thickness-1));
			int size = 1;
			int steps = 0;
			for(int i = 0; i > -xLength; i--) {
				int start = -size/2;
				for(int j = start; j < start + size; j++) {
					this.set(x-i, i + y + j, value);
				}
				if(jumps.contains(-i)) {
					if(steps < thickness-1) {
						size++;
						steps++;
					}
					else {
						size--;
						steps++;
					}
				}
			}
		}
		if(repair) {
			this.repair();
		}
	}
	
	//gets a rectangular box given a border and filling
	public static Tile[][] rectBox(int width, int height, Tile border, Tile inner){
		Tile[][] box = new Tile[height][width];
		for(int i = 0; i < width; i++) {
			box[0][i] = border;
			box[height-1][i] = border;
		}
		for(int j = 1; j < height-1; j++) {
			box[j][0] = border;
			box[j][width-1] = border;
			for(int k = 1; k < width-1; k++) {
				box[j][k] = inner;
			}
		}
		return box;
	}
	
	//checks if something is in this area
	public boolean inArea(int x, int y) {
		if(x >= this.x && x <= this.x + BLOCK_SIZE * this.getWidth() &&
				y >= this.y && y <= this.y + BLOCK_SIZE * this.getHeight()) {
			return true;
		}
		return false;
	}
	
	//adds exits
	public void addExits(int[] exits) {
		int[] out = new int[exits.length + this.exits.length];
		for(int i = 0; i < exits.length; i++) {
			out[i] = exits[i];
		}
		for(int i = exits.length; i < out.length; i++) {
			out[i] = this.exits[i-exits.length];
		}
		this.exits = out;
	}
	
	//fills out the exits
	public void fillExits() {
		for(int i = 0; i < exits.length; i += 4) {
			for(int j = 0; j < exits[i+1]; j++) {
				if(exits[i] == 0) { //move by x
					if(get(exits[i+2]+j, exits[i+3]) != null) {
						set(exits[i+2]+j, exits[i+3], inner);
					}
				}
				else {
					if(get(exits[i+2], exits[i+3]+j) != null) {
						set(exits[i+2], exits[i+3]+j, inner);
					}
				}
			}
		}
	}
	
	//draws the area
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		for(int x = 0; x < grid[0].length; x++) {
			for(int y = 0; y < grid.length; y++) {
				if(this.get(x, y) != null) {
					g.drawImage(this.get(x, y).getImage(), this.x + x * BLOCK_SIZE - shiftX + 400, this.y + y * BLOCK_SIZE - shiftY + 400, 
							BLOCK_SIZE, BLOCK_SIZE, i);
				}
			}
		}
	}
	
	public abstract void repair();
	
	public boolean overlap(Area a1, Area a2) {
		return false;
	}
	
	//Desc: various getters and setters
	//Param: value for setters
	//Return: value for getters
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public Tile getBorder() {
		return this.border;
	}
	
	public Tile getInner() {
		return this.inner;
	}
	
	public int[] getExits() {
		return this.exits;
	}
	
	public PriorityQueue<Enemy> getEnemies(){
		return this.enemies;
	}
	
	public Tile[][] getGrid(){
		return this.grid;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
