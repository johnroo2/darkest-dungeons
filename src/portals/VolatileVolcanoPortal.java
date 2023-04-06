package portals;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import dungeons.CorruptedCatacombs;
import dungeons.VolatileVolcano;
import project.Portal;

public class VolatileVolcanoPortal extends Portal{
	final static int ANIMATE_FRAMES = 10;
	private int frames;
	
	public VolatileVolcanoPortal(int x, int y, boolean restart) {
		super(x, y);
		this.destination = new VolatileVolcano();
		this.img = Portal.VOLATILEVOLCANO_PORTAL.get(0);
		this.title = "Volatile Volcano";
		this.range = 50;
		
		if(restart) {
			this.title = "Replay Level";
		}
	}
	
	@Override
	public BufferedImage getImg() {
		for(int i = 1; i <= 10; i++) {
			if(frames < ANIMATE_FRAMES * i) {
				return Portal.VOLATILEVOLCANO_PORTAL.get(i-1);
			}
		}
		return Portal.VOLATILEVOLCANO_PORTAL.get(0);
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
