package project;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import control.Controller;

public abstract class Tile {
	protected boolean wall = false;
	protected boolean water = false;
	protected double slow = 0.5;
	protected int damage = 0;
	private String name = "";
	protected BufferedImage image;

	protected static BufferedImage TILE_DIRTWALL;
	protected static BufferedImage TILE_SAND;
	protected static BufferedImage TILE_MEADOWGRASS;
	protected static BufferedImage TILE_LIGHTDIRTWALL;
	protected static BufferedImage TILE_VOLCANOWALL;
	protected static BufferedImage TILE_LAVAFLOOR;
	protected static BufferedImage TILE_LAVAFLOORSQUARE;
	protected static BufferedImage TILE_ICEFLOOR;
	protected static BufferedImage TILE_ICEWALL;
	protected static ArrayList<BufferedImage> TILE_VOLCANOLAVA = new ArrayList<>();
	protected static ArrayList<BufferedImage> TILE_LIGHTWATER = new ArrayList<>();
	protected static ArrayList<BufferedImage> TILE_CORRUPTEDICE = new ArrayList<>();
	
	public static Tile DIRTWALL;
	public static Tile SAND;
	public static Tile LIGHTWATER;
	public static Tile MEADOWGRASS;
	public static Tile LIGHTDIRTWALL;
	public static Tile VOLCANOWALL;
	public static Tile LAVAFLOOR;
	public static Tile VOLCANOLAVA;
	public static Tile ICEFLOOR;
	public static Tile ICEWALL;
	public static Tile CORRUPTEDICE;
	public static Tile[] tileList;
	
	public static void upload() throws IOException{
		TILE_DIRTWALL = ImageIO.read(new File("./tiles/dirtwall.png"));
		TILE_SAND = ImageIO.read(new File("./tiles/sand.png"));
		TILE_MEADOWGRASS = ImageIO.read(new File("./tiles/meadowgrass.png"));
		TILE_LIGHTDIRTWALL = ImageIO.read(new File("./tiles/lightdirtwall.png"));
		TILE_VOLCANOWALL = ImageIO.read(new File("./tiles/volcanowall.png"));
		TILE_LAVAFLOOR = ImageIO.read(new File("./tiles/lavafloor.png"));
		TILE_LAVAFLOORSQUARE = ImageIO.read(new File("./tiles/lavafloorsquare.png"));
		TILE_ICEWALL = ImageIO.read(new File("./tiles/icewall.png"));
		TILE_ICEFLOOR = ImageIO.read(new File("./tiles/icefloor.png"));
		
		for(int i = 0; i <= 3; i++) {TILE_LIGHTWATER.add(ImageIO.read(new File("./tiles/lightwater000"+i+".png")));}
		for(int i = 0; i <= 3; i++) {TILE_CORRUPTEDICE.add(ImageIO.read(new File("./tiles/corruptedice000"+i+".png")));}
		for(int i = 3; i >= 0; i--) {TILE_VOLCANOLAVA.add(ImageIO.read(new File("./tiles/volcanolava000"+i+".png")));}
		
		DIRTWALL = new DirtWall();
		SAND = new Sand();
		LIGHTWATER = new LightWater();
		MEADOWGRASS = new MeadowGrass();
		LIGHTDIRTWALL = new LightDirtWall();
		LAVAFLOOR = new LavaFloor();
		VOLCANOLAVA = new VolcanoLava();
		VOLCANOWALL = new VolcanoWall();
		ICEWALL = new IceWall();
		ICEFLOOR = new IceFloor();
		CORRUPTEDICE = new CorruptedIce();
		tileList = new Tile[] {LIGHTWATER, VOLCANOLAVA, CORRUPTEDICE};
	}
	
	public BufferedImage getImage() {return image;}
	public void runTiles() {}
	public int getDamage() {return damage;}
	public boolean isWall() {return wall;}
	public boolean isWater() {return water;}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}

class DirtWall extends Tile{
	public DirtWall() {
		this.image = TILE_DIRTWALL;
		this.wall = true;
	}
}

class Sand extends Tile{
	public Sand() {
		this.image = TILE_SAND;
	}
}

class LightWater extends Tile{
	private static int frames;
	private static final int CYCLE = 25;
	
	public LightWater() {
		this.water = true;
		frames = CYCLE * 4;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(frames < CYCLE * i) {
				return TILE_LIGHTWATER.get(i-1);
			}
		}
		return TILE_LIGHTWATER.get(0);
	}
	
	@Override
	public void runTiles() {
		frames -= 1;
		if(frames <= 0) {
			frames = CYCLE * 4;
		}
	}
}

class MeadowGrass extends Tile{
	public MeadowGrass() {
		this.image = TILE_MEADOWGRASS;
	}
}

class LightDirtWall extends Tile{
	public LightDirtWall() {
		this.image = TILE_LIGHTDIRTWALL;
		this.wall = true;
	}
}

class VolcanoWall extends Tile{
	public VolcanoWall() {
		this.image = TILE_VOLCANOWALL;
		this.wall = true;
	}
}

class LavaFloor extends Tile{
	public LavaFloor() {
		this.image = TILE_LAVAFLOOR;
		//this.image = TILE_LAVAFLOORSQUARE;
	}
}

class VolcanoLava extends Tile{
	private static int frames;
	private static final int CYCLE = 20;
	
	public VolcanoLava() {
		this.water = true;
		this.damage = 45;
		this.setName("Lava");
		frames = CYCLE * 4;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(frames < CYCLE * i) {
				return TILE_VOLCANOLAVA.get(i-1);
			}
		}
		return TILE_VOLCANOLAVA.get(0);
	}
	
	@Override
	public void runTiles() {
		frames -= 1;
		if(frames <= 0) {
			frames = CYCLE * 4;
		}
	}
}

class IceWall extends Tile{
	public IceWall() {
		this.wall = true;
		this.image = TILE_ICEWALL;
	}
}

class IceFloor extends Tile{
	public IceFloor() {
		this.image = TILE_ICEFLOOR;
	}
}

class CorruptedIce extends Tile{
	private static int frames;
	private static final int CYCLE = 50;
	
	public CorruptedIce() {
		this.water = true;
		this.damage = 60;
		this.setName("Corrupted Ice");
		frames = CYCLE * 4;
	}
	
	@Override
	public BufferedImage getImage() {
		for(int i = 1; i < 5; i++) {
			if(frames < CYCLE * i) {
				return TILE_CORRUPTEDICE.get(i-1);
			}
		}
		return TILE_CORRUPTEDICE.get(0);
	}
	
	@Override
	public void runTiles() {
		frames -= 1;
		if(frames <= 0) {
			frames = CYCLE * 4;
		}
	}
}
