package project;

import java.util.ArrayList;
import java.util.Arrays;

import control.Controller;

public abstract class Dungeon {
	protected Tile border, inner;
	protected int spawnX, spawnY;
	protected String title;
	private int level;
	protected ArrayList<Area> areas = new ArrayList<>();
	
	public Dungeon(String title, int difficulty) {
		this.title = title;
		this.setLevel(difficulty);
	}
	
	public void init() {
		Area.list.clear();
		Controller.allyAoE.clear();
		Controller.enemyAoE.clear();
		Controller.allyShots.clear();
		Controller.enemyShots.clear();
		Controller.addList.clear();
		LootBag.list.clear();
		Controller.chr.setX(spawnX);
		Controller.chr.setY(spawnY);
		Portal.list.clear();
		generate();
	}
	
	public int getSeed(int min, int max, int exclude) {
		ArrayList<Integer> val = new ArrayList<>();
		for(int i = min; i < max; i++) {
			if(i != exclude) {
				val.add(i);
			}
		}
		return val.get(Controller.random.nextInt(0, val.size()));
	}
	
	public int getSeed(int min, int max, int[] exclude) {
		ArrayList<Integer> val = new ArrayList<>();
		for(int i = min; i < max; i++) {
			if(Arrays.binarySearch(exclude, i) < 0) {
				val.add(i);
			}
		}
		return val.get(Controller.random.nextInt(0, val.size()));
	}
	
	public abstract void add(Area a);
	
	public abstract void generate();
	
	public ArrayList<Area> getAreas(){return this.areas;}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

