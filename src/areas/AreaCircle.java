package areas;

import java.util.Arrays;

import project.Area;
import project.Tile;

public class AreaCircle extends Area{
	public AreaCircle(int x, int y, int radius, Tile border, Tile inner, int[] exits) {
		super(x, y, radius, border, inner);
		this.grid = rectBox(2*radius, 2*radius, null, null);
		
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				if((radius-i-1)*(radius-i-1) + (radius-j-1)*(radius-j-1) < radius * radius) {
					this.grid[j][i] = border;
				}
			}
			for(int j = 0; j < radius; j++) {
				if((radius-i)*(radius-i) + (radius-j)*(radius-j) < radius * radius) {
					this.grid[j][i] = inner;
				}
			}
		}	
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				this.grid[2*radius-1-j][i] = this.grid[j][i];
				this.grid[j][2*radius-1-i] = this.grid[j][i];
				this.grid[2*radius-1-j][2*radius-1-i] = this.grid[j][i];
			}
		}
		
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
		this.exits = exits;
	}
	
	@Override
	public void repair() {
		Tile[][] prev = new Tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			prev[i] = grid[i].clone();
		}
		int radius = this.getWidth()/2;
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					this.grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						this.grid[j][i] = prev[j][i];
					}
				}
				else {
					this.grid[j][i] = null;
				}
			}
		}	
		for(int i = radius; i < radius * 2; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (i-radius)*(i-radius) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					this.grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						this.grid[j][i] = prev[j][i];
					}
				}
				else {
					this.grid[j][i] = null;
				}
			}
		}	
		for(int i = 0; i < radius; i++) {
			for(int j = radius; j < radius * 2; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (j-radius)*(j-radius);
				if(dist < radius * radius) {
					this.grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						this.grid[j][i] = prev[j][i];
					}
				}
				else {
					this.grid[j][i] = null;
				}
			}
		}	
		for(int i = radius; i < radius * 2; i++) {
			for(int j = radius; j < radius * 2; j++) {
				int dist = (i-radius)*(i-radius) + (j-radius)*(j-radius);
				if(dist < radius * radius) {
					this.grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						this.grid[j][i] = prev[j][i];
					}
				}
				else {
					this.grid[j][i] = null;
				}
			}
		}
		this.fillExits();
	}
}
