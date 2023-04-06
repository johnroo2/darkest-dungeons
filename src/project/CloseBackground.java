package project;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

import control.Controller;

public class CloseBackground {
	public final static CloseBackground SSCloseBackground = new CloseBackground(new Tile[] {Tile.SAND});
	public final static CloseBackground WWCloseBackground = new CloseBackground(new Tile[] {Tile.MEADOWGRASS, Tile.MEADOWGRASS, Tile.MEADOWGRASS, Tile.LIGHTWATER});
	public final static CloseBackground VVCloseBackground = new CloseBackground(new Tile[] {Tile.LAVAFLOOR, Tile.LAVAFLOOR, Tile.LAVAFLOOR, Tile.LAVAFLOOR, Tile.VOLCANOLAVA});
	public final static CloseBackground CCCloseBackground = new CloseBackground(new Tile[] {Tile.ICEFLOOR, Tile.ICEFLOOR, Tile.ICEFLOOR, Tile.ICEFLOOR, Tile.CORRUPTEDICE});
	
	private Tile[][] grid = new Tile[20][28];
	
	public CloseBackground(Tile[] tiles) {
		for(int x = 0; x < 28; x++) {
			for(int y = 0; y < 20; y++) {
				grid[y][x] = tiles[Controller.random.nextInt(0, tiles.length)];
			}
		}
	}
	
	public void draw(Graphics g, ImageObserver i) {
		for(int x = 0; x < 28; x++) {
			for(int y = 0; y < 20; y++) {
				g.drawImage(grid[y][x].getImage(), Area.BLOCK_SIZE * x, Area.BLOCK_SIZE * y, Area.BLOCK_SIZE, Area.BLOCK_SIZE, i);
			}
		}
	}
}
