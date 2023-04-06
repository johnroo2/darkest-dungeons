package dungeons;

import areas.AreaBox;
import areas.AreaCorridor;
import control.Controller;
import enemies.volatilevolcano.daimanarsonist.DaimanArsonist;
import enemies.volatilevolcano.daimanbrute.DaimanBrute;
import enemies.volatilevolcano.daimanwitch.DaimanWitch;
import enemies.volatilevolcano.firesprite.FireSprite;
import enemies.volatilevolcano.flamespecter.FlameSpecter;
import enemies.volatilevolcano.forsakenchampion.ForsakenChampion;
import enemies.volatilevolcano.lesserdemon.LesserDemon;
import enemies.volatilevolcano.powdermonkey.PowderMonkey;
import enemies.volatilevolcano.shunnedidol.ShunnedIdol;
import project.Area;
import project.Dungeon;
import project.Tile;

public class VolatileVolcano extends Dungeon{
	private Pen p = new Pen(0, 0);
	
	public VolatileVolcano() {
		super("Volatile Volcano", 2);
		this.spawnX = 400;
		this.spawnY = 400;
		this.border = Tile.VOLCANOWALL;
		this.inner = Tile.VOLCANOLAVA;
	}

	@Override
	public void add(Area a) {
		
		this.areas.add(a);
	}
	
	public void newDaiman(int x, int y) {
		int opt = Controller.random.nextInt(0, 3);
		if(opt == 0) {
			new DaimanArsonist(x, y);
		}
		else if(opt == 1) {
			new DaimanBrute(x, y);
		}
		else if(opt == 2) {
			new DaimanWitch(x, y); 
		}
	}
	
	public void smallGroup(int x, int y) {
		int opt = Controller.random.nextInt(0, 9);
		if(opt == 0) {
			new DaimanArsonist(x, y);
			newDaiman(x + 80, y + 80);
		}
		else if(opt == 1) {
			new DaimanBrute(x, y);
			newDaiman(x - 80, y - 80);
		}
		else if(opt == 2) {
			new DaimanWitch(x, y);
		}
		else if(opt == 3) {
			new FlameSpecter(x, y);
		}
		else if(opt == 4) {
			PowderMonkey.newHorde(x - 100, y - 100, 200, 200, 4);
		}
		else if(opt == 5) {
			PowderMonkey.newHorde(x - 100, y - 100, 200, 200, 5);
		}
		else if(opt == 6) {
			new FireSprite(x - 50, y - 50);
			new FireSprite(x + 50, y + 50);
		}
		else if(opt == 7) {
			new FireSprite(x, y + 80);
			new FireSprite(x - 80, y);
		}
		else if(opt == 8) {
			new FireSprite(x, y);
		}
	}
	
	public void largeGroup(int x, int y) {
		int opt = Controller.random.nextInt(0, 7);
		if(opt == 0) {
			new FlameSpecter(x, y);
			newDaiman(x, y + 80);
		}
		else if(opt == 1) {
			new FlameSpecter(x, y);
			new DaimanWitch(x - 80, y);
		}
		else if(opt == 2) {
			new LesserDemon(x, y);
			newDaiman(x, y - 80);
		}
		else if(opt == 3) {
			new LesserDemon(x, y);
			new DaimanArsonist(x - 80, y);
		}
		else if(opt == 4) {
			new ShunnedIdol(x, y);
			newDaiman(x, y - 80);
		}
		else if(opt == 5) {
			new ShunnedIdol(x, y);
			new DaimanBrute(x - 80, y);
		}
		else if(opt == 6) {
			newDaiman(x - 80, y - 80);
			newDaiman(x + 80, y - 80);
			newDaiman(x - 80, y + 80);
			newDaiman(x + 80, y + 80);
		}
	}
	
	//fourbox: points of reference -> 160, 1120, each box is 720
	//fourbox centers: 520, 1480
	public void fourBoxRoom(int startX, int startY, int dir, int endDir) {
		if(startX == 0) {
			if(startY == 0) {
				if(dir == 0) {
					p.drawY -= 7 * Area.BLOCK_SIZE;
				}
				else {
					p.drawX -= 7 * Area.BLOCK_SIZE;
				}
			}
			else {
				if(dir == 0) {
					p.drawY -= 36 * Area.BLOCK_SIZE;
				}
				else {
					p.drawX -= 7 * Area.BLOCK_SIZE;
					p.drawY -= 50 * Area.BLOCK_SIZE;
				}
			}
		}
		else {
			if(startY == 0) {
				if(dir == 0) {
					p.drawX -= 50 * Area.BLOCK_SIZE;
					p.drawY -= 7 * Area.BLOCK_SIZE;
				}
				else {
					p.drawX -= 36 * Area.BLOCK_SIZE;
				}
			}
			else {
				if(dir == 0) {
					p.drawX -= 50 * Area.BLOCK_SIZE;
					p.drawY -= 36 * Area.BLOCK_SIZE;
				}
				else {
					p.drawX -= 36 * Area.BLOCK_SIZE;
					p.drawY -= 50 * Area.BLOCK_SIZE;
				}
			}
		}
		
		AreaBox a = new AreaBox(p.drawX, p.drawY, 50, 50, border, inner, new int[] {});

		a.setBox(18, 18, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
		a.setLine(0, 17, 5, 21, Tile.LAVAFLOOR);
		a.setLine(1, 17, 21, 5, Tile.LAVAFLOOR);
		a.setBox(18, 18, 28, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
		a.setLine(0, 17, 28, 21, Tile.LAVAFLOOR);
		a.setLine(1, 17, 28, 5, Tile.LAVAFLOOR);
		a.setBox(18, 18, 4, 28, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
		a.setLine(0, 17, 5, 28, Tile.LAVAFLOOR);
		a.setLine(1, 17, 21, 28, Tile.LAVAFLOOR);
		a.setBox(18, 18, 28, 28, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
		a.setLine(0, 17, 28, 28, Tile.LAVAFLOOR);
		a.setLine(1, 17, 28, 28, Tile.LAVAFLOOR);
		
		a.setBox(4, 7, 8, 21, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		a.setBox(4, 7, 38, 21, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		a.setBox(7, 4, 21, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		a.setBox(7, 4, 21, 38, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		
		a.setLine(0, 10, 12, 21, Tile.VOLCANOWALL);
		a.setLine(1, 10, 21, 12, Tile.VOLCANOWALL);
		a.setLine(0, 10, 28, 21, Tile.VOLCANOWALL);
		a.setLine(1, 10, 28, 12, Tile.VOLCANOWALL);
		a.setLine(0, 10, 12, 28, Tile.VOLCANOWALL);
		a.setLine(1, 10, 21, 28, Tile.VOLCANOWALL);
		a.setLine(0, 10, 28, 28, Tile.VOLCANOWALL);
		a.setLine(1, 10, 28, 28, Tile.VOLCANOWALL);
		
		if(startX == 0) {
			if(startY == 0) {
				if(dir == 0) {
					a.setBox(5, 4, 0, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 8, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				
				//end at 1, 1
				if(endDir == 0) {
					a.setBox(5, 4, 45, 37, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 37, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
			else {
				if(dir == 0) {
					a.setBox(5, 4, 0, 37, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 8, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				
				//end at 1, 0
				if(endDir == 0) {
					a.setBox(5, 4, 45, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 37, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
		}
		else {
			if(startY == 0) {
				if(dir == 0) {
					a.setBox(5, 4, 45, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 37, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				
				//end at 0, 1
				if(endDir == 0) {
					a.setBox(5, 4, 0, 37, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 8, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
			else {
				if(dir == 0) {
					a.setBox(5, 4, 45, 37, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 37, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				
				if(endDir == 0) {
					a.setBox(5, 4, 0, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					a.setBox(4, 5, 8, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
		}
		
		for(int i = 0; i < 50; i++) {
			a.set(Controller.random.nextInt(4, 46), Controller.random.nextInt(4, 46), Tile.VOLCANOLAVA);
		}
		add(a);
	}
	
	public void fourBoxHall(int startX, int startY, int startDir, int endDir) {
		AreaCorridor a = null;
		if(startX == 0) {
			if(startY == 0) {
				//end at 1, 1
				if(endDir == 0) {
					p.drawX += 50 * Area.BLOCK_SIZE;
					p.drawY += 36 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 24, 6, 1, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					p.drawX += 24 * Area.BLOCK_SIZE;
					//a.setBox(5, 4, 45, 37, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					p.drawX += 36 * Area.BLOCK_SIZE;
					p.drawY += 50 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 6, 24, 0, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					p.drawY += 24 * Area.BLOCK_SIZE;
					//a.setBox(4, 5, 37, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
			else {
				//end at 1, 0
				if(endDir == 0) {
					p.drawX += 50 * Area.BLOCK_SIZE;
					p.drawY += 7 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 24, 6, 1, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					p.drawX += 24 * Area.BLOCK_SIZE;
					//a.setBox(5, 4, 45, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					p.drawX += 36 * Area.BLOCK_SIZE;
					p.drawY -= 24 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 6, 24, 0, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					//a.setBox(4, 5, 37, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
		}
		else {
			if(startY == 0) {
				//end at 0, 1
				if(endDir == 0) {
					p.drawY += 36 * Area.BLOCK_SIZE;
					p.drawX -= 24 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 24, 6, 1, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					//a.setBox(5, 4, 0, 37, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					p.drawX += 7 * Area.BLOCK_SIZE;
					p.drawY += 50 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 6, 24, 0, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					p.drawY += 24 * Area.BLOCK_SIZE;
					//a.setBox(4, 5, 8, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
			else {
				if(endDir == 0) {
					p.drawY += 7 * Area.BLOCK_SIZE;
					p.drawX -= 24 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 24, 6, 1, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					//a.setBox(5, 4, 0, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
				else {
					p.drawX += 7 * Area.BLOCK_SIZE;
					p.drawY -= 24 * Area.BLOCK_SIZE;
					a = new AreaCorridor(p.drawX, p.drawY, 6, 24, 0, border, Tile.LAVAFLOOR);
					a.randomStreak(inner);
					//a.setBox(4, 5, 8, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				}
			}
		}
		add(a);
	}
	
	public void fourBoxSummon() {
		int topright = Controller.random.nextInt(0, 5);
		int bottomleft = (topright + Controller.random.nextInt(1, 5)) % 5;
		switch(topright){
		case 0:
			new LesserDemon(p.drawX + 1480, p.drawY + 520);
			break;
		case 1:
			new ShunnedIdol(p.drawX + 1480, p.drawY + 520);
			break;
		case 2:
			new FlameSpecter(p.drawX + 1480, p.drawY + 520);
			new FireSprite(p.drawX + 1300, p.drawY + 400);
			new FireSprite(p.drawX + 1660, p.drawY + 400);
			new FireSprite(p.drawX + 1480, p.drawY + 720);
			break;
		case 3:
			newDaiman(p.drawX + 1300, p.drawY + 400);
			newDaiman(p.drawX + 1660, p.drawY + 400);
			newDaiman(p.drawX + 1480, p.drawY + 720);
			break;
		case 4:
			PowderMonkey.newHorde(p.drawX + 1320, p.drawY + 360, 320, 320, 6);
			break;
		}
		switch(bottomleft){
		case 0:
			new LesserDemon(p.drawX + 520, p.drawY + 1480);
			break;
		case 1:
			new ShunnedIdol(p.drawX + 520, p.drawY + 1480);
			break;
		case 2:
			new FlameSpecter(p.drawX + 520, p.drawY + 1480);
			new FireSprite(p.drawX + 400, p.drawY + 1300);
			new FireSprite(p.drawX + 400, p.drawY + 1660);
			new FireSprite(p.drawX + 720, p.drawY + 1480);
			break;
		case 3:
			newDaiman(p.drawX + 400, p.drawY + 1300);
			newDaiman(p.drawX + 400, p.drawY + 1660);
			newDaiman(p.drawX + 720, p.drawY + 1480);
			break;
		case 4:
			PowderMonkey.newHorde(p.drawX + 360, p.drawY + 1320, 320, 320, 6);
			break;
		}
		smallGroup(p.drawX + 520, p.drawY + 520);
		smallGroup(p.drawX + 1480, p.drawY + 1480);
	}
	
	public void heavyRectangleRoom(int startDir, int endDir) {
		if(startDir == 1) {
			p.drawY -= 13 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 13 * Area.BLOCK_SIZE; 
		}
		else if(startDir == 3) {
			p.drawY -= 13 * Area.BLOCK_SIZE;
			p.drawX -= 36 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawY -= 36 * Area.BLOCK_SIZE;
			p.drawX -= 13 * Area.BLOCK_SIZE;
		}
		
		AreaBox a;
		if(startDir % 2 == 1) {
			a = new AreaBox(p.drawX, p.drawY, 36, 32, border, inner, new int[] {});
			a.setBox(28, 24, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
			if(startDir == 1 || endDir == 1) {
				a.setBox(5, 4, 0, 14, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 0 || endDir == 0) {
				a.setBox(4, 5, 16, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 3 || endDir == 3) {
				a.setBox(5, 4, 31, 14, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 2 || endDir == 2) {
				a.setBox(4, 5, 16, 27, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			for(int i = 0; i < 25; i++) {
				a.set(Controller.random.nextInt(4, 32), Controller.random.nextInt(4, 28), Tile.VOLCANOLAVA);
			}
		}
		else {
			a = new AreaBox(p.drawX, p.drawY, 32, 36, border, inner, new int[] {});
			a.setBox(24, 28, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
			if(startDir == 1 || endDir == 1) {
				a.setBox(5, 4, 0, 16, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 0 || endDir == 0) {
				a.setBox(4, 5, 14, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 3 || endDir == 3) {
				a.setBox(5, 4, 27, 16, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 2 || endDir == 2) {
				a.setBox(4, 5, 14, 31, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			for(int i = 0; i < 25; i++) {
				a.set(Controller.random.nextInt(4, 28), Controller.random.nextInt(4, 32), Tile.VOLCANOLAVA);
			}
		}
		add(a);
	}
	
	public void heavyRectangleHall(int startDir, int endDir) {		
		AreaCorridor a = null;
		
		if(startDir % 2 == 1) {
			if(endDir == 1) {
				//24/2-3
				p.drawY += 13 * Area.BLOCK_SIZE;
				p.drawX -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);			
			}
			else if(endDir == 0) {
				p.drawX += 15 * Area.BLOCK_SIZE;
				p.drawY -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
			}
			else if(endDir == 3) {
				p.drawY += 13 * Area.BLOCK_SIZE;
				p.drawX += 36 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);		
				p.drawX += 8 * Area.BLOCK_SIZE;
			}
			else if(endDir == 2) {
				p.drawX += 15 * Area.BLOCK_SIZE;
				p.drawY += 32 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
				p.drawY += 8 * Area.BLOCK_SIZE;
			}
		}
		else {
			if(endDir == 1) {
				//24/2-3
				p.drawY += 15 * Area.BLOCK_SIZE;
				p.drawX -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);			
			}
			else if(endDir == 0) {
				p.drawX += 13 * Area.BLOCK_SIZE;
				p.drawY -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
			}
			else if(endDir == 3) {
				p.drawY += 15 * Area.BLOCK_SIZE;
				p.drawX += 32 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);		
				p.drawX += 8 * Area.BLOCK_SIZE;
			}
			else if(endDir == 2) {
				p.drawX += 13 * Area.BLOCK_SIZE;
				p.drawY += 36 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
				p.drawY += 8 * Area.BLOCK_SIZE;
			}
		}
		add(a);
	}
	
	public void heavyRectangleSummon(int startDir) {
		if(startDir % 2 == 0) {
			largeGroup(p.drawX + 640, p.drawY + 720);
			smallGroup(p.drawX + 640, p.drawY + 320);
		}
		else {
			largeGroup(p.drawX + 720, p.drawY + 640);
			smallGroup(p.drawX + 1120, p.drawY + 640);
		}
	}
	
	public void moderateRectangleRoom(int startDir, int endDir) {
		if(startDir == 1) {
			p.drawY -= 9 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 9 * Area.BLOCK_SIZE; 
		}
		else if(startDir == 3) {
			p.drawY -= 9 * Area.BLOCK_SIZE;
			p.drawX -= 40 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawY -= 40 * Area.BLOCK_SIZE;
			p.drawX -= 9 * Area.BLOCK_SIZE;
		}
		
		AreaBox a;
		if(startDir % 2 == 1) {
			a = new AreaBox(p.drawX, p.drawY, 40, 24, border, inner, new int[] {});
			a.setBox(32, 16, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
			if(startDir == 1 || endDir == 1) {
				a.setBox(5, 4, 0, 10, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 0 || endDir == 0) {
				a.setBox(4, 5, 18, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 3 || endDir == 3) {
				a.setBox(5, 4, 35, 10, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 2 || endDir == 2) {
				a.setBox(4, 5, 18, 19, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			for(int i = 0; i < 20; i++) {
				a.set(Controller.random.nextInt(4, 36), Controller.random.nextInt(4, 20), Tile.VOLCANOLAVA);
			}
		}
		else {
			a = new AreaBox(p.drawX, p.drawY, 24, 40, border, inner, new int[] {});
			a.setBox(16, 32, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
			if(startDir == 1 || endDir == 1) {
				a.setBox(5, 4, 0, 18, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 0 || endDir == 0) {
				a.setBox(4, 5, 10, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 3 || endDir == 3) {
				a.setBox(5, 4, 19, 18, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 2 || endDir == 2) {
				a.setBox(4, 5, 10, 35, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			for(int i = 0; i < 20; i++) {
				a.set(Controller.random.nextInt(4, 20), Controller.random.nextInt(4, 36), Tile.VOLCANOLAVA);
			}
		}
		add(a);
	}
	
	public void moderateRectangleHall(int startDir, int endDir) {	
		AreaCorridor a = null;
		if(startDir % 2 == 1) {
			if(endDir == 1) {
				//24/2-3
				p.drawY += 9 * Area.BLOCK_SIZE;
				p.drawX -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);			
			}
			else if(endDir == 0) {
				p.drawX += 17 * Area.BLOCK_SIZE;
				p.drawY -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
			}
			else if(endDir == 3) {
				p.drawY += 9 * Area.BLOCK_SIZE;
				p.drawX += 40 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);		
				p.drawX += 8 * Area.BLOCK_SIZE;
			}
			else if(endDir == 2) {
				p.drawX += 17 * Area.BLOCK_SIZE;
				p.drawY += 24 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
				p.drawY += 8 * Area.BLOCK_SIZE;
			}
		}
		else {
			if(endDir == 1) {
				//24/2-3
				p.drawY += 17 * Area.BLOCK_SIZE;
				p.drawX -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);			
			}
			else if(endDir == 0) {
				p.drawX += 9 * Area.BLOCK_SIZE;
				p.drawY -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
			}
			else if(endDir == 3) {
				p.drawY += 17 * Area.BLOCK_SIZE;
				p.drawX += 24 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);		
				p.drawX += 8 * Area.BLOCK_SIZE;
			}
			else if(endDir == 2) {
				p.drawX += 9 * Area.BLOCK_SIZE;
				p.drawY += 40 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
				p.drawY += 8 * Area.BLOCK_SIZE;
			}
		}
		add(a);
	}

	public void moderateRectangleSummon(int startDir) {
		if(startDir % 2 == 0) {
			largeGroup(p.drawX + 480, p.drawY + 800);
			smallGroup(p.drawX + 480, p.drawY + 350);
		}
		else {
			largeGroup(p.drawX + 800, p.drawY + 480);
			smallGroup(p.drawX + 1250, p.drawY + 480);
		}
	}
	
	public void thinRectangleRoom(int startDir, int endDir) {
		if(startDir == 1) {
			p.drawY -= 7 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 7 * Area.BLOCK_SIZE; 
		}
		else if(startDir == 3) {
			p.drawY -= 7 * Area.BLOCK_SIZE;
			p.drawX -= 50 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawY -= 50 * Area.BLOCK_SIZE;
			p.drawX -= 7 * Area.BLOCK_SIZE;
		}
		
		AreaBox a;
		if(startDir % 2 == 1) {
			a = new AreaBox(p.drawX, p.drawY, 50, 20, border, inner, new int[] {});
			a.setBox(42, 12, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
			if(startDir == 1 || endDir == 1) {
				a.setBox(5, 4, 0, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 0 || endDir == 0) {
				a.setBox(4, 5, 23, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 3 || endDir == 3) {
				a.setBox(5, 4, 45, 8, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 2 || endDir == 2) {
				a.setBox(4, 5, 23, 15, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			for(int i = 0; i < 25; i++) {
				a.set(Controller.random.nextInt(4, 46), Controller.random.nextInt(4, 16), Tile.VOLCANOLAVA);
			}
		}
		else {
			a = new AreaBox(p.drawX, p.drawY, 20, 50, border, inner, new int[] {});
			a.setBox(12, 42, 4, 4, Tile.VOLCANOWALL, Tile.LAVAFLOOR);
			if(startDir == 1 || endDir == 1) {
				a.setBox(5, 4, 0, 23, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 0 || endDir == 0) {
				a.setBox(4, 5, 8, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 3 || endDir == 3) {
				a.setBox(5, 4, 15, 23, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			if(startDir == 2 || endDir == 2) {
				a.setBox(4, 5, 8, 45, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			for(int i = 0; i < 25; i++) {
				a.set(Controller.random.nextInt(4, 16), Controller.random.nextInt(4, 46), Tile.VOLCANOLAVA);
			}
		}
		add(a);
	}
	
	public void thinRectangleHall(int startDir, int endDir) {
		AreaCorridor a = null;
		if(startDir % 2 == 1) {
			if(endDir == 1) {
				//24/2-3
				p.drawY += 7 * Area.BLOCK_SIZE;
				p.drawX -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);			
			}
			else if(endDir == 0) {
				p.drawX += 22 * Area.BLOCK_SIZE;
				p.drawY -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
			}
			else if(endDir == 3) {
				p.drawY += 7 * Area.BLOCK_SIZE;
				p.drawX += 50 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);		
				p.drawX += 8 * Area.BLOCK_SIZE;
			}
			else if(endDir == 2) {
				p.drawX += 22 * Area.BLOCK_SIZE;
				p.drawY += 20 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
				p.drawY += 8 * Area.BLOCK_SIZE;
			}
		}
		else {
			if(endDir == 1) {
				//24/2-3
				p.drawY += 22 * Area.BLOCK_SIZE;
				p.drawX -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);			
			}
			else if(endDir == 0) {
				p.drawX += 7 * Area.BLOCK_SIZE;
				p.drawY -= 8 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
			}
			else if(endDir == 3) {
				p.drawY += 22 * Area.BLOCK_SIZE;
				p.drawX += 20 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);		
				p.drawX += 8 * Area.BLOCK_SIZE;
			}
			else if(endDir == 2) {
				p.drawX += 7 * Area.BLOCK_SIZE;
				p.drawY += 50 * Area.BLOCK_SIZE;
				a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				a.randomStreak(inner);	
				p.drawY += 8 * Area.BLOCK_SIZE;
			}
		}
		add(a);
	}
	
	public void thinRectangleSummon(int startDir) {
		if(startDir % 2 == 0) {
			largeGroup(p.drawX + 400, p.drawY + 1000);
			smallGroup(p.drawX + 400, p.drawY + 400);
		}
		else {
			largeGroup(p.drawX + 1000, p.drawY + 400);
			smallGroup(p.drawX + 1600, p.drawY + 400);
		}
	}
	
	public boolean crescentRoom(int startDir) {
		if(startDir == 1) {
			p.drawY -= 19 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 19 * Area.BLOCK_SIZE; 
		}
		else if(startDir == 3) {
			p.drawY -= 19 * Area.BLOCK_SIZE;
			p.drawX -= 44 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawY -= 44 * Area.BLOCK_SIZE;
			p.drawX -= 19 * Area.BLOCK_SIZE;
		}
		AreaBox a = new AreaBox(p.drawX, p.drawY, 44, 44, border, inner, new int[] {});
		a.setCircle(4, 4, 18, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		
		boolean out = Controller.coinflip();
		if(startDir % 2 == 1) {
			if(out) {
				a.setCircle(12, 4, 10, inner, inner);
			}
			else {
				a.setCircle(12, 20, 10, inner, inner);
			}
		}
		else {
			if(out) {
				a.setCircle(4, 12, 10, inner, inner);
			}
			else {
				a.setCircle(20, 12, 10, inner, inner);
			}
		}
		add(a);
		
		if(startDir % 2 == 1) {
			a.setBox(5, 4, 0, 20, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			a.setBox(5, 4, 39, 20, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		}
		else {
			a.setBox(4, 5, 20, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			a.setBox(4, 5, 20, 39, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
		}
		
		for(int i = 0; i < 40; i++) {
			a.set(Controller.random.nextInt(4, 36), Controller.random.nextInt(4, 36), Tile.VOLCANOLAVA);
		}
		
		return out;
	}
	
	public void crescentHall(int startDir) {
		AreaCorridor a = null;
		if(startDir == 0) {
			p.drawY += 44 * Area.BLOCK_SIZE;
			p.drawX += 19 * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
			a.randomStreak(inner);	
			p.drawY += 8 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawX += 44 * Area.BLOCK_SIZE;
			p.drawY += 19 * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
			a.randomStreak(inner);	
			p.drawX += 8 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawX += 19 * Area.BLOCK_SIZE;
			p.drawY -= 8 * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
			a.randomStreak(inner);	
		}
		else if(startDir == 3) {
			p.drawY += 19 * Area.BLOCK_SIZE;
			p.drawX -= 8 * Area.BLOCK_SIZE;
			a = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
			a.randomStreak(inner);	
		}
		add(a);
	}
	
	public void crescentSummon(int startDir, boolean output) {
		if(startDir % 2 == 0) {
			if(output) {
				//4, 12
				smallGroup(p.drawX + 880, p.drawY + 300);
				
				largeGroup(p.drawX + 1280, p.drawY + 880);
			}
			else {
				//20, 12
				smallGroup(p.drawX + 880, p.drawY + 1460);
				
				largeGroup(p.drawX + 480, p.drawY + 880);
			}
		}
		else {
			if(output) {
				//12, 4
				smallGroup(p.drawX + 300, p.drawY + 880);
				
				largeGroup(p.drawX + 880, p.drawY + 1280);
			}
			else {
				//12, 20
				smallGroup(p.drawX + 1460, p.drawY + 880);
				
				largeGroup(p.drawX + 880, p.drawY + 480);
			}			
		}
	}
	
	//DO NOT CHAIN FOUR BOXES LMAO
	
	@Override
	public void generate() {
		//fourBoxRoom(1, 1, 1, 0);
		
		int v, h;
		if(Controller.coinflip()) {v = 0;}
		else {v = 2;}
		if(Controller.coinflip()) {h = 1;}
		else {h = 3;}
		if(Controller.coinflip()) {
			path(v, h, Controller.random.nextInt(7, 9), v, -1);
		}
		else {
			path(v, h, Controller.random.nextInt(7, 9), h, -1);
		}
//		for(Area a: this.areas) {
//			System.out.println(a.getX() + ", " + a.getY());
//		}
		//p.drawX += 320;
		//fourBoxRoom(0, 0, 1, 0);
		
//		new DaimanArsonist(p.drawX + 600, p.drawY + 400);
//		new DaimanArsonist(p.drawX + 1000, p.drawY + 480);
//		new DaimanArsonist(p.drawX + 600, p.drawY + 560);
//		new DaimanArsonist(p.drawX + 1000, p.drawY + 640);
		//new ShunnedIdol(p.drawX + 1480, p.drawY + 1480);d
		//PowderMonkey.newHorde(p.drawX + 1200, p.drawY + 280, 480, 480, 25);
	}
	
	public void path(int v, int h, int space, int lastDir, int seed) {
		//System.out.println(space + ", " + p.drawX + ", " + p.drawY + ", " + seed + ", " + lastDir);
		if(space == 0) {
			//30, 30
			if(lastDir == 0) {
				p.drawX -= 12 * Area.BLOCK_SIZE;
				p.drawY -= 30 * Area.BLOCK_SIZE;
				AreaBox a = new AreaBox(p.drawX, p.drawY, 30, 30, Tile.VOLCANOWALL, Tile.LAVAFLOOR, new int[] {0, 4, 13, 29});
				add(a);
				new ForsakenChampion(p.drawX + 600, p.drawY + 300);
			}
			else if(lastDir == 1) {
				p.drawX -= 30 * Area.BLOCK_SIZE;
				p.drawY -= 12 * Area.BLOCK_SIZE;
				AreaBox a = new AreaBox(p.drawX, p.drawY, 30, 30, Tile.VOLCANOWALL, Tile.LAVAFLOOR, new int[] {1, 4, 29, 13});
				add(a);
				new ForsakenChampion(p.drawX + 300, p.drawY + 600);
			}
			else if(lastDir == 2) {
				p.drawX -= 12 * Area.BLOCK_SIZE;
				AreaBox a = new AreaBox(p.drawX, p.drawY, 30, 30, Tile.VOLCANOWALL, Tile.LAVAFLOOR, new int[] {0, 4, 13, 0});
				add(a);
				new ForsakenChampion(p.drawX + 600, p.drawY + 900);
			}
			else if(lastDir == 3) {
				p.drawY -= 12 * Area.BLOCK_SIZE;
				AreaBox a = new AreaBox(p.drawX, p.drawY, 30, 30, Tile.VOLCANOWALL, Tile.LAVAFLOOR, new int[] {1, 4, 0, 13});
				add(a);
				new ForsakenChampion(p.drawX + 900, p.drawY + 600);
			}
			return;
		}
		if(seed == -1) {
			AreaBox a = new AreaBox(p.drawX, p.drawY, 16, 16, border, inner, new int[] {});
			add(a);
			new FireSprite(p.drawX + 150, p.drawY + 150);
			new FireSprite(p.drawX + 490, p.drawY + 490);
			if(lastDir == 0) {
				a.addExits(new int[] {0, 4, 6, 0});
				a.fillExits();
				p.drawX += 200;
				p.drawY -= 320;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				b.randomStreak(Tile.VOLCANOLAVA);
				add(b);
				
				a.setCircle(1, 1, 7, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				a.setBox(4, 2, 6, 0, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			else if(lastDir == 1) {
				a.addExits(new int[] {1, 4, 0, 6});
				a.fillExits();
				p.drawY += 200;
				p.drawX -= 320;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				b.randomStreak(Tile.VOLCANOLAVA);
				add(b);
				
				a.setCircle(1, 1, 7, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				a.setBox(2, 4, 0, 6, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			else if(lastDir == 2) {
				a.addExits(new int[] {0, 4, 6, 15});
				a.fillExits();
				p.drawX += 200;
				p.drawY += 640;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 6, 8, 0, border, Tile.LAVAFLOOR);
				b.randomStreak(Tile.VOLCANOLAVA);
				add(b);
				p.drawY += 320;
				
				a.setCircle(1, 1, 7, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				a.setBox(4, 2, 6, 14, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			else if(lastDir == 3) {
				a.addExits(new int[] {1, 4, 15, 6});
				a.fillExits();
				p.drawY += 200;
				p.drawX += 640;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 6, 1, border, Tile.LAVAFLOOR);
				b.randomStreak(Tile.VOLCANOLAVA);
				add(b);
				p.drawX += 320;
				
				a.setCircle(1, 1, 7, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
				a.setBox(2, 4, 14, 6, Tile.LAVAFLOOR, Tile.LAVAFLOOR);
			}
			
			path(v, h, space-1, lastDir, this.getSeed(0, 5, seed));
		}
		//crescent
		else if(seed == 0) {
			crescentSummon((lastDir + 2) % 4, crescentRoom((lastDir + 2) % 4));
			crescentHall((lastDir + 2) % 4);
			
			path(v, h, space-1, lastDir, this.getSeed(0, 5, seed));
		}
		//thinbox
		else if(seed == 1) {
			if(Controller.coinflip()) {
				thinRectangleRoom((lastDir +2) % 4, v);
				thinRectangleSummon((lastDir+2) % 4);
				thinRectangleHall((lastDir +2) % 4, v);
				path(v, h, space-1, v, this.getSeed(0, 5, seed));
			}
			else {
				thinRectangleRoom((lastDir +2) % 4, h);
				thinRectangleSummon((lastDir+2) % 4);
				thinRectangleHall((lastDir +2) % 4, h);
				path(v, h, space-1, h, this.getSeed(0, 5, seed));
			}
		}
		//moderatebox
		else if(seed == 2) {
			if(Controller.coinflip()) {
				moderateRectangleRoom((lastDir +2) % 4, v);
				moderateRectangleSummon((lastDir+2) % 4);
				moderateRectangleHall((lastDir +2) % 4, v);
				path(v, h, space-1, v, this.getSeed(0, 5, seed));
			}
			else {
				moderateRectangleRoom((lastDir +2) % 4, h);
				moderateRectangleSummon((lastDir+2) % 4);
				moderateRectangleHall((lastDir +2) % 4, h);
				path(v, h, space-1, h, this.getSeed(0, 5, seed));
			}
		}
		//heavybox
		else if(seed == 3) {
			if(Controller.coinflip()) {
				heavyRectangleRoom((lastDir +2) % 4, v);
				heavyRectangleSummon((lastDir+2) % 4);
				heavyRectangleHall((lastDir +2) % 4, v);
				path(v, h, space-1, v, this.getSeed(0, 5, seed));
			}
			else {
				heavyRectangleRoom((lastDir +2) % 4, h);
				heavyRectangleSummon((lastDir+2) % 4);
				heavyRectangleHall((lastDir +2) % 4, h);
				path(v, h, space-1, h, this.getSeed(0, 5, seed));
			}
		}
		//four boxes
		else if(seed == 4) {
			if(lastDir == 0) {
				fourBoxRoom(1, 1, 1, 1);
				fourBoxSummon();
				fourBoxHall(1, 1, 1, 1);
				path(v, h, space-1, v, this.getSeed(0, 5, seed));
			}
			else if(lastDir == 1) {
				fourBoxRoom(1, 1, 0, 0);
				fourBoxSummon();
				fourBoxHall(1, 1, 0, 0);
				path(v, h, space-1, h, this.getSeed(0, 5, seed));
			}
			else if(lastDir == 2) {
				fourBoxRoom(0, 0, 1, 1);
				fourBoxSummon();
				fourBoxHall(0, 0, 1, 1);
				path(v, h, space-1, v, this.getSeed(0, 5, seed));
			}
			else if(lastDir == 3) {
				fourBoxRoom(0, 0, 0, 0);
				fourBoxSummon();
				fourBoxHall(0, 0, 0, 0);
				path(v, h, space-1, h, this.getSeed(0, 5, seed));
			}
		}
	}
}
