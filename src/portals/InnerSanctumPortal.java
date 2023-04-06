package portals;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import dungeons.InnerSanctum;
import project.Portal;

public class InnerSanctumPortal extends Portal{
	int SIZE = 60;
	final static int ANIMATE_FRAMES = 9;
	private int frames;
	
	public InnerSanctumPortal(int x, int y, boolean restart) {
		super(x, y);
		this.destination = new InnerSanctum();
		this.img = Portal.INNERSANCTUM_PORTAL.get(0);
		this.title = "Inner Sanctum";
		this.range = 50;
		
		if(restart) {
			this.title = "Replay Level";
		}
	}
	
	@Override
	public BufferedImage getImg() {
		for(int i = 1; i <= 10; i++) {
			if(frames < ANIMATE_FRAMES * i) {
				return Portal.INNERSANCTUM_PORTAL.get(i-1);
			}
		}
		return Portal.INNERSANCTUM_PORTAL.get(0);
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
		g.drawImage(this.img, (int)this.x-this.SIZE/2 - Controller.chr.getX() + 400, (int)this.y-this.SIZE/2 - Controller.chr.getY() + 400, 
				SIZE, SIZE, i);
	}
}
