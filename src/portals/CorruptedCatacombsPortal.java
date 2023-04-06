package portals;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import dungeons.CorruptedCatacombs;
import project.Portal;

public class CorruptedCatacombsPortal extends Portal{
	final static int ANIMATE_FRAMES = 12;
	private int frames;
	
	public CorruptedCatacombsPortal(int x, int y, boolean restart) {
		super(x, y);
		this.destination = new CorruptedCatacombs();
		this.img = Portal.CORRUPTEDCATACOMBS_PORTAL.get(0);
		this.title = "Corrupted Catacombs";
		if(restart) {
			this.title = "Replay Level";
		}
		this.range = 50;
	}
	
	@Override
	public BufferedImage getImg() {
		for(int i = 1; i <= 10; i++) {
			if(frames < ANIMATE_FRAMES * i) {
				return Portal.CORRUPTEDCATACOMBS_PORTAL.get(i-1);
			}
		}
		return Portal.CORRUPTEDCATACOMBS_PORTAL.get(0);
	}
	
	@Override
	public void draw(Graphics g, ImageObserver i) {
		if(frames < ANIMATE_FRAMES * 10) {
			frames++;
		}
		else {
			frames = 0;
		}
		this.img = this.getImg();
		super.draw(g, i);
	}
}
