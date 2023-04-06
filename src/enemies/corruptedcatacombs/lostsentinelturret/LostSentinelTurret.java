package enemies.corruptedcatacombs.lostsentinelturret;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import project.Target;

public class LostSentinelTurret extends Target{
	final static int SIZE = 36;
	final static int SHOT_RANGE = 1000;
	final static double SHOT_SPEED = 2;
	final static int DMG = 150;

	final static int ANIMATE_TICKS = 12;
	private int animate;
	
	public LostSentinelTurret(int x, int y) {
		super(x, y, 1, 0);
	}
	
	public void shoot() {
		new LostSentinelTurretShot((int)this.x, (int)this.y);
		this.animate = ANIMATE_TICKS;
	}

	public BufferedImage getImage() {
		if(this.animate > 0) {
			this.animate -= 1;
			return Target.IMGS_LOSTSENTINELTURRET.get(1);
		}
		return Target.IMGS_LOSTSENTINELTURRET.get(0);
	}
	
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(getImage(), (int)this.x-SIZE/2 - shiftX + 400, (int)this.y-this.SIZE/2 - shiftY + 400, SIZE, SIZE, i);
	}
}
