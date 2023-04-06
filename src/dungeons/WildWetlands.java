package dungeons;

import areas.*;
import control.Controller;
import enemies.wildwetlands.aquamage.AquaMage;
import enemies.wildwetlands.crocodile.Crocodile;
import enemies.wildwetlands.forbiddenfountain.ForbiddenFountain;
import enemies.wildwetlands.fountainfairy.FountainFairy;
import enemies.wildwetlands.fountainnymph.FountainNymph;
import enemies.wildwetlands.fountainspirit.FountainSpirit;
import enemies.wildwetlands.giantcrab.GiantCrab;
import enemies.wildwetlands.greatdragonfly.GreatDragonfly;
import enemies.wildwetlands.watersprite.*;
import project.Area;
import project.Dungeon;
import project.Tile;

public class WildWetlands extends Dungeon{
	private Pen p = new Pen(0, 0);
	
	public WildWetlands() {
		super("Wild Wetlands", 1);
		this.spawnX = 560;
		this.spawnY = 560;
		this.border = Tile.LIGHTDIRTWALL;
		this.inner = Tile.MEADOWGRASS;
	}

	@Override
	public void add(Area a) {
		this.areas.add(a);
	}

	@Override
	public void generate() {
		int v, h;
		if(Controller.coinflip()) {v = 0;}
		else {v = 2;}
		if(Controller.coinflip()) {h = 1;}
		else {h = 3;}
		if(Controller.coinflip()) {
			path(v, h, Controller.random.nextInt(6, 8), v, 28, -1);
		}
		else {
			path(v, h, Controller.random.nextInt(6, 8), h, 28, -1);
		}
	}
	
	public AreaCorridor makeHall(int dir, int length, int lastX, int lastY) {
		AreaCorridor a = null;
		if(dir == 0) {
			p.drawX += (lastX/2 - 4) * Area.BLOCK_SIZE;
			p.drawY -= length * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, 8, length, 0, border, inner);
			p.drawX -= (lastX/2 - 4) * Area.BLOCK_SIZE;
		}
		else if(dir == 1) {
			p.drawY += (lastY/2 - 4) * Area.BLOCK_SIZE;
			p.drawX -= length * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, length, 8, 1, border, inner);
			p.drawY -= (lastY/2 - 4) * Area.BLOCK_SIZE;
		}
		else if(dir == 2) {
			p.drawX += (lastX/2 - 4) * Area.BLOCK_SIZE;
			p.drawY += lastY * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, 8, length, 0, border, inner);
			p.drawY += length * Area.BLOCK_SIZE;
			p.drawX -= (lastX/2 - 4) * Area.BLOCK_SIZE;
		}
		else if(dir == 3) {
			p.drawX += lastX * Area.BLOCK_SIZE;
			p.drawY += (lastY/2 - 4) * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, length, 8, 1, border, inner);
			p.drawX += length * Area.BLOCK_SIZE;
			p.drawY -= (lastY/2 - 4) * Area.BLOCK_SIZE;
		}
		a.randomStreak(Tile.LIGHTWATER);
		return a;
	}
	
	public AreaUCorridor makeChainHall(int dir, boolean left, int length, int lastX, int lastY) {
		AreaUCorridor a = null;
		if(dir == 0) {
			if(left) { //up, left
				p.drawY -= 320;
				p.drawX -= length * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, length, 8, 0, border, inner);
			}
			else { //up, right
				p.drawY -= 320;
				p.drawX += lastX * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, length, 8, 0, border, inner);
				p.drawX += length * Area.BLOCK_SIZE;
				p.drawX -= 320;
			}
		}
		else if(dir == 1) {
			if(left) { //left, down
				p.drawX -= 320;
				p.drawY += lastX * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, 8, length, 1, border, inner);
				p.drawY += length * Area.BLOCK_SIZE;
				p.drawY -= 320;
			}
			else { //left, up
				p.drawX -= 320;
				p.drawY -= length * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, 8, length, 1, border, inner);
			}
		}
		else if(dir == 2) {
			if(left) { //down, right
				p.drawY += lastY * Area.BLOCK_SIZE;
				p.drawX += lastX * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, length, 8, 2, border, inner);
				p.drawX += length * Area.BLOCK_SIZE;
				p.drawX -= 320;
				
			}
			else { //down, left
				p.drawY += lastY * Area.BLOCK_SIZE;
				p.drawX -= length * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, length, 8, 2, border, inner);
			}
		}
		else if(dir == 3) {
			if(left) { //right, up
				p.drawX += lastY * Area.BLOCK_SIZE;
				p.drawY -= length * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, 8, length, 3, border, inner);
				
			}
			else { //right, down
				p.drawX += lastX * Area.BLOCK_SIZE;
				p.drawY += lastY * Area.BLOCK_SIZE - 320;
				a = new AreaUCorridor(p.drawX, p.drawY, 8, length, 3, border, inner);
				p.drawY += length * Area.BLOCK_SIZE;
				p.drawY -= 320;
			}
			
		}
		a.randomStreak(Tile.LIGHTWATER);
		return a;
	}
	
	public void shiftBox(int lastDir, int size) {
		if(lastDir == 0) {
			p.drawX -= (size/2 - 4) * Area.BLOCK_SIZE;
			p.drawY -= size * Area.BLOCK_SIZE;
		}
		else if(lastDir == 1) {
			p.drawY -= (size/2 - 4) * Area.BLOCK_SIZE;
			p.drawX -= size* Area.BLOCK_SIZE;
		}
		else if(lastDir == 2) {
			p.drawX -= (size/2 - 4) * Area.BLOCK_SIZE;
		}
		else if(lastDir == 3) {
			p.drawY -= (size/2 - 4) * Area.BLOCK_SIZE;
		}
	}
	
	public void crescentToBoxShift(int lastDir) {
		if(lastDir == 2) {
			p.drawY += 320;
		}
		else if(lastDir == 3) {
			p.drawX += 320;
		}
	}
	
	public void boxToBoxShift(int lastDir, int size) {
		if(lastDir == 0) {
			p.drawY -= size * Area.BLOCK_SIZE;
		}
		else if(lastDir == 1) {
			p.drawX -= size * Area.BLOCK_SIZE;
		}
	}
	
	public boolean shiftCrescent(int lastDir, int lastX, int lastY, int v, int h) {
		if(lastDir == 0) {
			p.drawX += (lastX/2 - 4) * Area.BLOCK_SIZE;
			if(h == 1) { //up, start left
				p.drawX -= 800;
				p.drawY -= 1120;
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 0, border, inner);
				add(a);
				a.setStreakDown(4, 4, 20, 20, 7, Tile.LIGHTWATER);
				return false;
			}
			else { //up, start right
				p.drawY -= 1120;
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 0, border, inner);
				add(a);
				a.setStreakUp(4, 24, 20, 20, 7, Tile.LIGHTWATER);			
				return true;
			}
		}
		else if(lastDir == 1) {
			p.drawY += (lastY/2 - 4) * Area.BLOCK_SIZE;
			if(v == 2) { //left, start down
				p.drawX -= 1120;
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 1, border, inner);
				add(a);
				a.setStreakDown(4, 4, 20, 20, 7, Tile.LIGHTWATER);			
				return false;
			}
			else { //left, start up
				p.drawY -= 800;
				p.drawX -= 1120;
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 1, border, inner);
				add(a);
				a.setStreakUp(4, 24, 20, 20, 7, Tile.LIGHTWATER);		
				return true;
			}
		}
		else if(lastDir == 2) {
			p.drawX += (lastX/2 - 4) * Area.BLOCK_SIZE;
			if(h == 3) { //down, start right
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 2, border, inner);
				add(a);
				a.setStreakDown(4, 4, 20, 20, 7, Tile.LIGHTWATER);			
				return false;
			}
			else { //down, start left
				p.drawX -= 800;
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 2, border, inner);
				add(a);
				a.setStreakUp(4, 24, 20, 20, 7, Tile.LIGHTWATER);			
				return true;
			}
		}
		else {
			p.drawY += (lastY/2 - 4) * Area.BLOCK_SIZE;
			if(v == 0) { //right, start up
				p.drawY -= 800;
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 3, border, inner);
				add(a);
				a.setStreakDown(4, 4, 20, 20, 7, Tile.LIGHTWATER);			
				return false;
			}
			else { //right, start down
				AreaCrescent a = new AreaCrescent(p.drawX, p.drawY, 3, border, inner);
				add(a);
				a.setStreakUp(4, 24, 20, 20, 7, Tile.LIGHTWATER);			
				return true;
			}
		}
	}
	
	public void newFae(int x, int y) {
		switch(Controller.random.nextInt(0, 7)) {
		case 0:
			new FountainFairy(x, y);
			break;
		case 1:
			new FountainFairy(x, y);
			break;
		case 2:
			new FountainNymph(x, y);
			break;
		case 3:
			new FountainNymph(x, y);
			break;
		case 4:
			new FountainSpirit(x, y);
			break;
		case 5:
			new FountainSpirit(x, y);
			break;
		case 6:
			new WaterSprite(x, y);
			break;
		}
	}
	
	public void faeHorde(int x, int y, int radius, int number) {
		for(int i = 0; i < number; i++) {
			double angle = Math.toRadians(Controller.random.nextInt(0, 360));
			int rad = Controller.random.nextInt(0, radius+1);
			newFae((int)(x + rad * Math.cos(angle)), (int)(y + rad * Math.sin(angle)));
		}
	}
	
	public void path(int v, int h, int space, int lastDir, int lastSize, int seed) {
		if(space == 0) {
			Area a = new AreaRoundBox(p.drawX, p.drawY, 9, 6, 6, border, inner, getLink(new int[] {(lastDir+2)%4}, 24));
			a.setCircle(3, 3, 9, Tile.LIGHTWATER);
			a.setCircle(4, 4, 8, Tile.LIGHTWATER);
			add(a);
			new ForbiddenFountain(p.drawX + 480, p.drawY + 480);
			return;
		}
		if(seed == -1) { //sprite room (starting platform)
			Area a = new AreaBox(p.drawX, p.drawY, 28, 28, border, inner, getLink(new int[] {lastDir}, 28));
			add(a);
			a.setBox(14, 14, 7, 7, Tile.LIGHTWATER, inner);
			a.setBox(12, 12, 8, 8, Tile.LIGHTWATER, inner);
			a.setBox(10, 10, 9, 9, Tile.LIGHTWATER, inner);
			a.set(7, 7, inner);
			a.set(20, 7, inner);
			a.set(7, 20, inner);
			a.set(20, 20, inner);
			
			new WaterSprite(200, 200);
			new WaterSprite(200, 920);
			new WaterSprite(920, 200);
			new WaterSprite(920, 920);
			
			int nextSeed = Controller.random.nextInt(5, 10);
			add(makeHall(lastDir, 20, 28, 28));
			path(v, h, space-1, lastDir, 28, nextSeed);
		}
		else if(seed == 0) { //crab room box
			boolean cf = Controller.coinflip();
			if(cf) {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, v}, 24));
				add(a);
				a.setCircle(7, 7, 5, Tile.LIGHTWATER);
				a.setCircle(6, 6, 6, Tile.LIGHTWATER);
			}
			else {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, h}, 24));
				add(a);
				a.setCircle(7, 7, 5, Tile.LIGHTWATER);
				a.setCircle(6, 6, 6, Tile.LIGHTWATER);
			}
			
			new GiantCrab(p.drawX+400, p.drawY+480);
			new GiantCrab(p.drawX+560, p.drawY+480);
			faeHorde(p.drawX + 480, p.drawY + 480, 400, 3);

			if(cf) {			
				add(makeHall(v, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(v, 24);
				}
				path(v, h, space-1, v, 24, next);
			}
			else {
				add(makeHall(h, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(h, 24);
				}
				path(v, h, space-1, h, 24, next);
			}
		}
		else if(seed == 1) { //croc room box
			boolean cf = Controller.coinflip();
			if(cf) {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, v}, 24));
				add(a);
				a.setBox(8, 2, 8, 5, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(8, 2, 8, 17, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(2, 8, 5, 8, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(2, 8, 17, 8, Tile.LIGHTWATER, Tile.LIGHTWATER);
			}
			else {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, h}, 24));
				add(a);
				a.setBox(8, 2, 8, 5, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(8, 2, 8, 17, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(2, 8, 5, 8, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(2, 8, 17, 8, Tile.LIGHTWATER, Tile.LIGHTWATER);
			}
			
			new Crocodile(p.drawX+480, p.drawY+480);
			faeHorde(p.drawX + 480, p.drawY + 480, 400, 3);

			if(cf) {			
				add(makeHall(v, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(v, 24);
				}
				path(v, h, space-1, v, 24, next);
			}
			else {
				add(makeHall(h, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(h, 24);
				}
				path(v, h, space-1, h, 24, next);
			}
		}
		else if(seed == 2) { //dragonfly room box
			boolean cf = Controller.coinflip();
			if(cf) {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, v}, 24));
				add(a);
				a.setBox(5, 5, 6, 6, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(5, 5, 6, 13, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(5, 5, 13, 6, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(5, 5, 13, 13, Tile.LIGHTWATER, Tile.LIGHTWATER);
			}
			else {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, h}, 24));
				add(a);
				a.setBox(5, 5, 6, 6, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(5, 5, 6, 13, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(5, 5, 13, 6, Tile.LIGHTWATER, Tile.LIGHTWATER);
				a.setBox(5, 5, 13, 13, Tile.LIGHTWATER, Tile.LIGHTWATER);
			}
			
			new GreatDragonfly(p.drawX+480, p.drawY+480);
			faeHorde(p.drawX + 480, p.drawY + 480, 400, 3);

			if(cf) {			
				add(makeHall(v, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(v, 24);
				}
				path(v, h, space-1, v, 24, next);
			}
			else {
				add(makeHall(h, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(h, 24);
				}
				path(v, h, space-1, h, 24, next);
			}
		}
		else if(seed == 3) { //aquamage room box
			boolean cf = Controller.coinflip();
			if(cf) {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, v}, 24));
				add(a);
				a.setCircle(4, 4, 8, Tile.LIGHTWATER);
				a.setCircle(5, 5, 7, Tile.LIGHTWATER);
			}
			else {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, h}, 24));
				add(a);
				a.setCircle(4, 4, 8, Tile.LIGHTWATER);
				a.setCircle(5, 5, 7, Tile.LIGHTWATER);
			}
			
			new AquaMage(p.drawX+480, p.drawY+480);
			faeHorde(p.drawX + 480, p.drawY + 480, 400, 2);

			if(cf) {			
				add(makeHall(v, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(v, 24);
				}
				path(v, h, space-1, v, 24, next);
			}
			else {
				add(makeHall(h, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(h, 24);
				}
				path(v, h, space-1, h, 24, next);
			}
		}
		else if(seed == 4) { //fae room box
			boolean cf = Controller.coinflip();
			if(cf) {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, v}, 24));
				add(a);
				a.setCircle(7, 7, 5, Tile.LIGHTWATER);
				a.setCircle(6, 6, 6, Tile.LIGHTWATER);
			}
			else {
				AreaRoundBox a = new AreaRoundBox(p.drawX, p.drawY, 10, 4, 4, border, inner, getLink(new int[] {(lastDir + 2) % 4, h}, 24));
				add(a);
				a.setCircle(7, 7, 5, Tile.LIGHTWATER);
				a.setCircle(6, 6, 6, Tile.LIGHTWATER);
			}

			faeHorde(p.drawX + 480, p.drawY + 480, 400, 6);

			if(cf) {			
				add(makeHall(v, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(v, 24);
				}
				path(v, h, space-1, v, 24, next);
			}
			else {
				add(makeHall(h, 20, 24, 24));
				int next = this.getSeed(0, 10, seed);
				if(next < 5 || space == 1) {
					boxToBoxShift(h, 24);
				}
				path(v, h, space-1, h, 24, next);
			}
		}
		else if(seed == 5) { //crab room crescent
			boolean left = shiftCrescent(lastDir, lastSize, lastSize, v, h); //i am NOT sure if "left" here actually means "turn relatively left"
			if(lastDir == 0) {
				new GiantCrab(p.drawX + 760, p.drawY + 460);
				new GiantCrab(p.drawX + 360, p.drawY + 460);
				faeHorde(p.drawX + 560, p.drawY + 460, 300, 3);
			}
			else if(lastDir == 1) {
				new GiantCrab(p.drawX + 460, p.drawY + 760);
				new GiantCrab(p.drawX + 460, p.drawY + 360);
				faeHorde(p.drawX + 460, p.drawY + 560, 300, 3);
			}
			else if(lastDir == 2) {
				new GiantCrab(p.drawX + 760, p.drawY + 660);
				new GiantCrab(p.drawX + 360, p.drawY + 660);
				faeHorde(p.drawX + 560, p.drawY + 660, 300, 3);
			}
			else if(lastDir == 3) {
				new GiantCrab(p.drawX + 660, p.drawY + 760);
				new GiantCrab(p.drawX + 660, p.drawY + 360);
				faeHorde(p.drawX + 660, p.drawY + 560, 300, 3);
			}
			AreaUCorridor b = makeChainHall((lastDir + 2) % 4, left, 40, 28, 28);
			add(b);

			shiftBox(lastDir, 24);
			crescentToBoxShift(lastDir);
			path(v, h, space-1, lastDir, 28, this.getSeed(0, 5, seed));		
		}
		else if(seed == 6) { //croc room crescent
			boolean left = shiftCrescent(lastDir, lastSize, lastSize, v, h); //i am NOT sure if "left" here actually means "turn relatively left"
			if(lastDir == 0) {
				new Crocodile(p.drawX + 560, p.drawY + 460);
				faeHorde(p.drawX + 560, p.drawY + 460, 300, 3);
			}
			else if(lastDir == 1) {
				new Crocodile(p.drawX + 460, p.drawY + 560);
				faeHorde(p.drawX + 460, p.drawY + 560, 300, 3);
			}
			else if(lastDir == 2) {
				new Crocodile(p.drawX + 560, p.drawY + 660);
				faeHorde(p.drawX + 560, p.drawY + 660, 300, 3);
			}
			else if(lastDir == 3) {
				new Crocodile(p.drawX + 660, p.drawY + 560);
				faeHorde(p.drawX + 660, p.drawY + 560, 300, 3);
			}
			AreaUCorridor b = makeChainHall((lastDir + 2) % 4, left, 40, 28, 28);
			add(b);

			shiftBox(lastDir, 24);
			crescentToBoxShift(lastDir);
			path(v, h, space-1, lastDir, 28, this.getSeed(0, 5, seed));	
		}
		else if(seed == 7) { //dragonfly room crescent
			boolean left = shiftCrescent(lastDir, lastSize, lastSize, v, h); //i am NOT sure if "left" here actually means "turn relatively left"
			if(lastDir == 0) {
				new GreatDragonfly(p.drawX + 560, p.drawY + 460);
				faeHorde(p.drawX + 560, p.drawY + 460, 300, 3);
			}
			else if(lastDir == 1) {
				new GreatDragonfly(p.drawX + 460, p.drawY + 560);
				faeHorde(p.drawX + 460, p.drawY + 560, 300, 3);
			}
			else if(lastDir == 2) {
				new GreatDragonfly(p.drawX + 560, p.drawY + 660);
				faeHorde(p.drawX + 560, p.drawY + 660, 300, 3);
			}
			else if(lastDir == 3) {
				new GreatDragonfly(p.drawX + 660, p.drawY + 560);
				faeHorde(p.drawX + 660, p.drawY + 560, 300, 3);
			}
			AreaUCorridor b = makeChainHall((lastDir + 2) % 4, left, 40, 28, 28);
			add(b);

			shiftBox(lastDir, 24);
			crescentToBoxShift(lastDir);
			path(v, h, space-1, lastDir, 28, this.getSeed(0, 5, seed));	
		}
		else if(seed == 8) { //aquamage room crescent
			boolean left = shiftCrescent(lastDir, lastSize, lastSize, v, h); //i am NOT sure if "left" here actually means "turn relatively left"
			if(lastDir == 0) {
				new AquaMage(p.drawX + 560, p.drawY + 460);
				faeHorde(p.drawX + 560, p.drawY + 460, 300, 2);
			}
			else if(lastDir == 1) {
				new AquaMage(p.drawX + 460, p.drawY + 560);
				faeHorde(p.drawX + 460, p.drawY + 560, 300, 2);
			}
			else if(lastDir == 2) {
				new AquaMage(p.drawX + 560, p.drawY + 660);
				faeHorde(p.drawX + 560, p.drawY + 660, 300, 2);
			}
			else if(lastDir == 3) {
				new AquaMage(p.drawX + 660, p.drawY + 560);
				faeHorde(p.drawX + 660, p.drawY + 560, 300, 2);
			}
			AreaUCorridor b = makeChainHall((lastDir + 2) % 4, left, 40, 28, 28);
			add(b);

			shiftBox(lastDir, 24);
			crescentToBoxShift(lastDir);
			path(v, h, space-1, lastDir, 28, this.getSeed(0, 5, seed));		
		}
		else { //fae room crescent
			boolean left = shiftCrescent(lastDir, lastSize, lastSize, v, h); //i am NOT sure if "left" here actually means "turn relatively left"
			if(lastDir == 0) {
				faeHorde(p.drawX + 410, p.drawY + 460, 300, 3);
				faeHorde(p.drawX + 710, p.drawY + 460, 300, 3);
			}
			else if(lastDir == 1) {
				faeHorde(p.drawX + 460, p.drawY + 410, 300, 3);
				faeHorde(p.drawX + 460, p.drawY + 710, 300, 3);
			}
			else if(lastDir == 2) {
				faeHorde(p.drawX + 410, p.drawY + 660, 300, 3);
				faeHorde(p.drawX + 710, p.drawY + 660, 300, 3);
			}
			else if(lastDir == 3) {
				faeHorde(p.drawX + 660, p.drawY + 410, 300, 3);
				faeHorde(p.drawX + 660, p.drawY + 710, 300, 3);
			}
			AreaUCorridor b = makeChainHall((lastDir + 2) % 4, left, 40, 28, 28);
			add(b);

			shiftBox(lastDir, 24);
			crescentToBoxShift(lastDir);
			path(v, h, space-1, lastDir, 28, this.getSeed(0, 5, seed));	
		}
	}
	
	public int[] getLink(int[] cardinal, int size) {
		int space = 0;
		for(int i = 0; i < cardinal.length; i++) {
			if(cardinal[i] >= 0 && cardinal[i] <= 3) {
				space += 4;
			}
		}
		int[] out = new int[space];
		int insert = 0;
		for(int i = 0; i < cardinal.length; i++) {
			if(cardinal[i] < 0) {
				continue;
			}
			else if(cardinal[i] == 0) {
				out[insert] = 0;
				out[insert+1] = 6;
				out[insert+2] = size/2 - 3;
				out[insert+3] = 0;
			}
			else if(cardinal[i] == 1) {
				out[insert] = 1;
				out[insert+1] = 6;
				out[insert+2] = 0;
				out[insert+3] = size/2 - 3;
			}
			else if(cardinal[i] == 2) {
				out[insert] = 0;
				out[insert+1] = 6;
				out[insert+2] = size/2 - 3;
				out[insert+3] = size-1;
			}
			else if(cardinal[i] == 3) {
				out[insert] = 1;
				out[insert+1] = 6;
				out[insert+2] = size-1;
				out[insert+3] = size/2 - 3;
			}
			insert += 4;
		}
		return out;
	}
}
