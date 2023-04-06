package enemies.corruptedcatacombs.lostsentineltower;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Target;

public class LostSentinelTower extends Target{
	final static int SIZE = 64;
	final static int SHOT_RANGE = 1500;
	final static double SHOT_SPEED = 2.8;
	final static int DMG = 150;
	
	public LostSentinelTower(int x, int y) {
		super(x, y, 1, 0);
	}
	
	public void shoot(int center) {
		int offset = Controller.random.nextInt(-15, 16);
		for(int i = -1; i < 2; i++) {
			new LostSentinelTowerShot((int)this.x, (int)this.y, center + offset + 22 * i);
		}
	}

	public BufferedImage getImage() {
		return Target.IMGS_LOSTSENTINELTOWER;
	}
	
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(getImage(), (int)this.x-SIZE/2 - shiftX + 400, (int)this.y-this.SIZE/2 - shiftY + 400, SIZE, SIZE, i);
	}
}
