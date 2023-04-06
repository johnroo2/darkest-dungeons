package areas;

import project.Area;
import project.Tile;

public class AreaCrescent extends Area{
	int dir;
	
	public AreaCrescent(int x, int y, int dir, Tile border, Tile inner) {
		super(x, y, getCrescent(dir, border, inner), border, inner);
		this.dir = dir;
		this.exits = new int[] {};
	}
	
	public AreaCrescent(int x, int y, int dir, Tile border, Tile inner, int[] exits) {
		super(x, y, getCrescent(dir, border, inner), border, inner);
		this.dir = dir;
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
	
	public static Tile[][] getCrescent(int dir, Tile border, Tile inner){
		Tile[][] out = new Tile[28][28];
		for(int i = 0; i < out[0].length; i++) {
			for(int j = 0; j < out.length; j++) {
				out[j][i] = inner;
			}
		}
		return repairGrid(out, dir, border, inner);
	}
	
	public static Tile[][] repairGrid(Tile[][] out, int dir, Tile border, Tile inner){
		if(dir == 0) { //up
			for(int i = 0; i < 28; i++) {
				out[i][0] = border;
				out[0][i] = border;
				out[i][27] = border;
			}
			for(int i = 20; i < 28; i++) {
				out[i][7] = border;
				out[i][20] = border;
			}
			for(int i = 7; i < 21; i++) {
				out[20][i] = border;
			}
			for(int i = 21; i < 28; i++) {
				for(int j = 8; j < 20; j++) {
					out[i][j] = null;
				}
			}
		}
		else if(dir == 1) {
			for(int i = 0; i < 28; i++) {
				out[i][0] = border;
				out[0][i] = border;
				out[27][i] = border;
			}
			for(int i = 20; i < 28; i++) {
				out[7][i] = border;
				out[20][i] = border;
			}
			for(int i = 7; i < 21; i++) {
				out[i][20] = border;
			}
			for(int i = 8; i < 20; i++) {
				for(int j = 21; j < 28; j++) {
					out[i][j] = null;
				}
			}
		}
		else if(dir == 2) {
			for(int i = 0; i < 28; i++) {
				out[i][0] = border;
				out[27][i] = border;
				out[i][27] = border;
			}
			for(int i = 0; i < 8; i++) {
				out[i][7] = border;
				out[i][20] = border;
			}
			for(int i = 7; i < 21; i++) {
				out[7][i] = border;
			}
			for(int i = 0; i < 7; i++) {
				for(int j = 8; j < 20; j++) {
					out[i][j] = null;
				}
			}
		}
		else if(dir == 3) {
			for(int i = 0; i < 28; i++) {
				out[i][27] = border;
				out[0][i] = border;
				out[27][i] = border;
			}
			for(int i = 0; i < 8; i++) {
				out[7][i] = border;
				out[20][i] = border;
			}
			for(int i = 7; i < 21; i++) {
				out[i][7] = border;
			}
			for(int i = 8; i < 20; i++) {
				for(int j = 0; j < 7; j++) {
					out[i][j] = null;
				}
			}
		}
		return out;
	}
	
	@Override
	public void repair() {
		this.grid = repairGrid(this.grid, this.dir, this.border, this.inner);
		this.fillExits();
	}
}
