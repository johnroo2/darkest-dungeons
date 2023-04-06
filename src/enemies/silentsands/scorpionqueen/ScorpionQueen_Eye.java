package enemies.silentsands.scorpionqueen;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import control.Controller;
import project.Area;
import project.Projectile;
import project.Rotate;

import statuseffects.*;

public class ScorpionQueen_Eye extends Projectile{
	final int SIZE = 24;
	
	int size, dmg;
	BufferedImage img;

	public ScorpionQueen_Eye(int x, int y, int ori) {
		super(x, y, (double)ori, ScorpionQueen.EYE_RANGE, ScorpionQueen.EYE_SPEED);
		this.dmg = ScorpionQueen.EYE_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(IMGS_SCORPIONQUEEN_SHOTS.get(0), rotation+45);
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
			new AllyArmourBreak(333);
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
