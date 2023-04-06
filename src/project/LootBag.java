package project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

public class LootBag {
	final static int SIZE = 32;
	public final static ArrayList<LootBag> list = new ArrayList<>();
	
	private int x, y;
	private int lifetime = 10000;
	private Equipment[] inventory = new Equipment[8];
	private BufferedImage img_blue;
	private BufferedImage img_orange;
	
	public LootBag(int x, int y, Equipment[] inventory) {
		this.x = x;
		this.y = y;
		for(int i = 0; i < inventory.length; i++) {
			this.inventory[i] = inventory[i];
		}
		this.img_blue = Equipment.IMGS_LOOTBAG_BLUE;
		this.img_orange = Equipment.IMGS_LOOTBAG_ORANGE;
		
		LootBag.list.add(this);
	}
	
	public LootBag(double x, double y, Equipment[] inventory) {
		this.x = (int)x;
		this.y = (int)y;
		for(int i = 0; i < inventory.length; i++) {
			this.inventory[i] = inventory[i];
		}
		this.img_blue = Equipment.IMGS_LOOTBAG_BLUE;
		this.img_orange = Equipment.IMGS_LOOTBAG_ORANGE;
		
		LootBag.list.add(this);
	}
	
	public static Equipment[] add(Equipment[] e, Equipment item) {
		for(int i = 0; i < 8; i++) {
			if(e[i] == null) {
				e[i] = item;
				break;
			}
		}
		return e;
	}
	
	public static void lootTable(int x, int y, LootRoll[][] l) {
		Equipment[] out = new Equipment[8];
		for(int i = 0; i < l.length; i++) {
			int culm = 0;
			for(int j = 0; j < l[i].length; j++) {
				if(l[i][j].run(culm)) {
					add(out, l[i][j].getItem());
					break;
				}
				culm += l[i][j].getProb();
			}
		}
		new LootBag(x, y, out);
	}
	
	public boolean empty() {
		for(int i = 0; i < 8; i++) {
			if(this.inventory[i] != null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean full() {
		for(int i = 0; i < 8; i++) {
			if(this.inventory[i] == null) {
				return false;
			}
		}
		return true;
	}
	
	public boolean ranged() {
		this.lifetime -= 1;
		if(this.lifetime <= 0) {
			return true;
		}
		if(this.empty()) {
			return true;
		}
		return false;
	}
	
	public BufferedImage getImage() {
		for(int i = 0; i < 8; i++) {
			if(this.inventory[i] != null && this.inventory[i].tierDisplay.equals("ST")) {
				return this.img_orange;
			}
		}
		return this.img_blue;
	}
	
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.getImage(), (int)this.x-this.SIZE/2 - shiftX + 400, (int)this.y-this.SIZE/2 - shiftY + 400, SIZE, SIZE, i);
	}
	
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public int getLifetime() {return this.lifetime;}
	public Equipment[] getInventory() {
		this.lifetime = 9990;
		return this.inventory;
	}
}
