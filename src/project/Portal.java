package project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import control.Controller;
import portals.EnterButton;

public class Portal {
	protected final int SIZE = 48;
	
	protected static BufferedImage SILENTSANDS_PORTAL;
	protected static BufferedImage WILDWETLANDS_PORTAL;
	protected final static ArrayList<BufferedImage> VOLATILEVOLCANO_PORTAL = new ArrayList<BufferedImage>();
	protected final static ArrayList<BufferedImage> CORRUPTEDCATACOMBS_PORTAL = new ArrayList<BufferedImage>();
	protected final static ArrayList<BufferedImage> INNERSANCTUM_PORTAL = new ArrayList<BufferedImage>();
	
	protected int x, y, range;
	protected Dungeon destination;
	protected String title;
	protected BufferedImage img;
	public static ArrayList<Portal> list = new ArrayList<>();
	
	private EnterButton eb = new EnterButton(this);
	
	public Portal(int x, int y) {
		this.x = x;
		this.y = y;
		list.add(this);
	}
	
	public static void upload() throws IOException{
		SILENTSANDS_PORTAL = ImageIO.read(new File("./portals/silentsands_portal.png"));
		WILDWETLANDS_PORTAL = ImageIO.read(new File("./portals/wildwetlands_portal.png"));
		for(int i = 0; i <= 9; i++) {VOLATILEVOLCANO_PORTAL.add(ImageIO.read(new File("./portals/volatilevolcano_portal000"+i+".png")));}
		for(int i = 0; i <= 9; i++) {CORRUPTEDCATACOMBS_PORTAL.add(ImageIO.read(new File("./portals/corruptedcatacombs_portal000"+i+".png")));}
		for(int i = 0; i <= 9; i++) {INNERSANCTUM_PORTAL.add(ImageIO.read(new File("./portals/innersanctum_portal000"+i+".png")));}
	}
	
	public boolean inRange() {
		if((this.x - Controller.chr.getX()) * (this.x - Controller.chr.getX()) + (this.y - Controller.chr.getY()) * (this.y - Controller.chr.getY()) < range*range) {
			return true;
		}
		return false;
	}
	
	public void enter() {
		Controller.chr.setDungeon(this.getDestination());
		if(Controller.chr.levelReached < Controller.chr.getDungeon().getLevel()) {
			Controller.chr.levelReached = Controller.chr.getDungeon().getLevel();
		}
		Controller.chr.getDungeon().init();
	}
	
	public BufferedImage getImg() {
		return this.img;
	}
	
	public void draw(Graphics g, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.SIZE/2 - Controller.chr.getX() + 400, (int)this.y-this.SIZE/2 - Controller.chr.getY() + 400, 
				SIZE, SIZE, i);
	}
	
	public Dungeon getDestination() {return this.destination;}
	public int getX() {return this.x;}
	public int getY() {return this.y;}
	public String getTitle() {return this.title;}

	public EnterButton getEb() {
		return eb;
	}

	public void setEb(EnterButton eb) {
		this.eb = eb;
	}
}
