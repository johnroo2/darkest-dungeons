package enemies.volatilevolcano.daimanwitch;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import enemies.wildwetlands.forbiddenfountain.ForbiddenFountain;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySilenced;

public class DaimanWitchShot extends Projectile{
	final int SIZE = 28;
	
	int size, dmg, initX, initY;
	BufferedImage img;

	public DaimanWitchShot(int x, int y, int targetX, int targetY, int alt) {
		super(x, y, targetX, targetY, DaimanWitch.BLAST_RANGE, DaimanWitch.BLAST_SPEED);
		this.dmg = DaimanWitch.BLAST_DMG;
		double angle = getAngle(x, y, targetX, targetY);
		double rotation = angle;
		
		//double opposite = speed * range * Math.tan(Math.toRadians(Math.abs(alt)));
		double oppositeX = 12 * Math.cos(Math.toRadians(angle+90));
		double oppositeY = 12 * Math.sin(Math.toRadians(angle+90));
		if(alt == 0) {
			this.initX += oppositeX;
			this.initY += oppositeY;
			this.x += oppositeX;
			this.y += oppositeY;
		}
		else {
			this.initX -= oppositeX;
			this.initY -= oppositeY;
			this.x -= oppositeX;
			this.y -= oppositeY;
		}
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));
		this.img = Rotate.rotateImage(Projectile.IMGS_DAIMANWITCH_SHOTS.get(0), rotation+45);
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
			Controller.chr.damage("Daiman Witch", this.dmg);
			new AllySilenced(125);
			return true;
		}
		return false;
	}

	@Override
	public void move() {
		double multiplier = 1;
		if(this.range < DaimanWitch.BLAST_RANGE) {
			multiplier = DaimanWitch.BLAST_ACCEL * (DaimanWitch.BLAST_RANGE - range);
		}
		this.x += this.xVel * multiplier;
		this.y += this.yVel * multiplier;
		this.range--;	
	}

	@Override
	public void draw(Graphics g, int shiftX, int shiftY, ImageObserver i) {
		g.drawImage(this.img, (int)this.x-this.size/2 - shiftX + 400, (int)this.y-this.size/2 - shiftY + 400, size, size, i);	
	}
}