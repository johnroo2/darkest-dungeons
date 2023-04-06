package areas;

import project.Area;
import project.Tile;

public class AreaRoundBox extends Area{
	int radius, innerWidth, innerHeight;
	public AreaRoundBox(int x, int y, int radius, int width, int height, Tile border, Tile inner, int[] exits) {
		super(x, y, getRoundBox(radius, width, height, border, inner), border, inner);
		this.radius = radius;
		this.innerWidth = width;
		this.innerHeight = height;
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
	
	public static Tile[][] getRoundBox(int radius, int width, int height, Tile border, Tile inner){
		Tile[][] out = new Tile[radius * 2 + height][radius * 2 + width];
		//sector 1: top left
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					out[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						out[j][i] = inner;
					}
				}
				else {
					out[j][i] = null;
				}
			}
		}	
		//sector 2: top middle
		for(int i = radius; i < radius + width; i++) {
			out[0][i] = border;
			for(int j = 1; j < radius; j++) {
				out[j][i] = inner;
			}
		}
		//sector 3: top right
		for(int i = radius + width; i < radius * 2 + width; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (i-radius-width)*(i-radius-width) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					out[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						out[j][i] = inner;
					}
				}
				else {
					out[j][i] = null;
				}
			}
		}	
		//sector 4: middle
		for(int j = radius; j < radius + height; j++) {
			out[j][0] = border;
			out[j][radius * 2 + width-1] = border;
			for(int i = 1; i < radius * 2 + width-1; i++) {
				out[j][i] = inner;
			}
		}
		//sector 5: bottom left
		for(int i = 0; i < radius; i++) {
			for(int j = radius + height; j < radius * 2 + height; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (j-radius-height)*(j-radius-height);
				if(dist < radius * radius) {
					out[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						out[j][i] = inner;
					}
				}
				else {
					out[j][i] = null;
				}
			}
		}	
		//sector 6: bottom middle
		for(int i = radius; i < radius + width; i++) {
			out[radius * 2 + height - 1][i] = border;
			for(int j = radius + height; j < radius * 2 + height - 1; j++) {
				out[j][i] = inner;
			}
		}	
		//sector 7: bottom right
		for(int i = radius + width; i < radius * 2 + width; i++) {
			for(int j = radius + height; j < radius * 2 + height; j++) {
				int dist = (i-radius-width)*(i-radius-width) + (j-radius-height)*(j-radius-height);
				if(dist < radius * radius) {
					out[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						out[j][i] = inner;
					}
				}
				else {
					out[j][i] = null;
				}
			}
		}	
		
		return out;
	}

	@Override
	public void repair() {
		Tile[][] prev = new Tile[grid.length][grid[0].length];
		for(int i = 0; i < grid.length; i++) {
			prev[i] = grid[i].clone();
		}
		
		//sector 1: top left
		for(int i = 0; i < radius; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						grid[j][i] = prev[j][i];
					}
				}
				else {
					grid[j][i] = null;
				}
			}
		}	
		//sector 2: top middle
		for(int i = radius; i < radius + innerWidth; i++) {
			grid[0][i] = border;
			for(int j = 1; j < radius; j++) {
				grid[j][i] = prev[j][i];
			}
		}
		//sector 3: top right
		for(int i = radius + innerWidth; i < radius * 2 + innerWidth; i++) {
			for(int j = 0; j < radius; j++) {
				int dist = (i-radius-innerWidth)*(i-radius-innerWidth) + (radius-j-1)*(radius-j-1);
				if(dist < radius * radius) {
					grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						grid[j][i] = prev[j][i];
					}
				}
				else {
					grid[j][i] = null;
				}
			}
		}	
		//sector 4: middle
		for(int j = radius; j < radius + innerHeight; j++) {
			grid[j][0] = border;
			grid[j][radius * 2 + innerWidth-1] = border;
			for(int i = 1; i < radius * 2 + innerWidth-1; i++) {
				grid[j][i] = prev[j][i];
			}
		}
		//sector 5: bottom left
		for(int i = 0; i < radius; i++) {
			for(int j = radius + innerHeight; j < radius * 2 + innerHeight; j++) {
				int dist = (radius-i-1)*(radius-i-1) + (j-radius-innerHeight)*(j-radius-innerHeight);
				if(dist < radius * radius) {
					grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						grid[j][i] = prev[j][i];
					}
				}
				else {
					grid[j][i] = null;
				}
			}
		}	
		//sector 6: bottom middle
		for(int i = radius; i < radius + innerWidth; i++) {
			grid[radius * 2 + innerHeight - 1][i] = border;
			for(int j = radius + innerHeight; j < radius * 2 + innerHeight - 1; j++) {
				grid[j][i] = prev[j][i];
			}
		}	
		//sector 7: bottom right
		for(int i = radius + innerWidth; i < radius * 2 + innerWidth; i++) {
			for(int j = radius + innerHeight; j < radius * 2 + innerHeight; j++) {
				int dist = (i-radius-innerWidth)*(i-radius-innerWidth) + (j-radius-innerHeight)*(j-radius-innerHeight);
				if(dist < radius * radius) {
					grid[j][i] = border;
					if(dist < (radius-1)*(radius-1)) {
						grid[j][i] = prev[j][i];
					}
				}
				else {
					grid[j][i] = null;
				}
			}
		}		
	}

}
