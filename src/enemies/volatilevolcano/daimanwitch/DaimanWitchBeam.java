package enemies.volatilevolcano.daimanwitch;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import control.Controller;
import project.Projectile;
import project.Rotate;
import statuseffects.AllySilenced;

public class DaimanWitchBeam extends Projectile{
	final int SIZE = 22;
	
	int size, dmg;
	BufferedImage img;

	public DaimanWitchBeam(int x, int y, int ori) {
		super(x, y, (double)ori, DaimanWitch.BEAM_RANGE, DaimanWitch.BEAM_SPEED);
		this.dmg = DaimanWitch.BEAM_DMG;
		double rotation = ori;
		
		this.size = (int) (SIZE * Rotate.getSizeConstant(rotation+45));		
		this.img = Rotate.rotateImage(Projectile.IMGS_DAIMANWITCH_SHOTS.get(1), rotation+45);
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
			new AllySilenced(125); //1.5 seconds;
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
