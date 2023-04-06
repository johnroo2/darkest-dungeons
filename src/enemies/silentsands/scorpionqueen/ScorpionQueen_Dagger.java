package enemies.silentsands.scorpionqueen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;

public class ScorpionQueen_Dagger extends Projectile{
	final int SIZE = 30;
	
	int size, dmg;
	BufferedImage img;

	public ScorpionQueen_Dagger(int x, int y, int ori) {
		super(x, y, (double)ori, ScorpionQueen.DAGGER_RANGE, ScorpionQueen.DAGGER_SPEED);
		this.dmg = ScorpionQueen.DAGGER_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(IMGS_SCORPIONQUEEN_SHOTS.get(1), rotation+45);
		Controller.enemyShots.add(this);
	}

	@Override
	public boolean ranged() {
		if(this.range <= 0) {
			return true;
		}
		if(inWall()) {
			return true;
		}
		if(Controller.chr.collision(this)) {
			Controller.chr.damage("Scorpion Queen", this.dmg);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		this.x += this.xVel;
		this.y += this.yVel;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}
