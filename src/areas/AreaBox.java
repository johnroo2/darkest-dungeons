package areas;

import project.Area;
import project.Tile;

public class AreaBox extends Area{
	public AreaBox(int x, int y, int width, int height, Tile border, Tile inner, int[] exits) {
		super(x, y, width, height, border, inner);
		this.grid = rectBox(width, height, border, inner);
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
}
