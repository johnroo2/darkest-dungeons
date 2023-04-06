package dungeons;

import areas.*;
import control.Controller;
import enemies.corruptedcatacombs.caveshrieker.CaveShrieker;
import enemies.corruptedcatacombs.cryoadept.CryoAdept;
import enemies.corruptedcatacombs.cryomage.CryoMage;
import enemies.corruptedcatacombs.cryowarlock.CryoWarlock;
import enemies.corruptedcatacombs.icesprite.IceSprite;
import enemies.corruptedcatacombs.lifecaster.Lifecaster;
import enemies.corruptedcatacombs.lostsentinel.LostSentinel;
import enemies.corruptedcatacombs.mammothhunter.MammothHunter;
import enemies.corruptedcatacombs.tenescowricrusader.TenescowriCrusader;
import enemies.corruptedcatacombs.tenescowrifleshtracker.TenescowriFleshtracker;
import enemies.corruptedcatacombs.tenescowrilieutenant.TenescowriLieutenant;
import enemies.corruptedcatacombs.tenescowrishaman.TenescowriShaman;
import enemies.corruptedcatacombs.unhallowedrevenant.UnhallowedRevenant;
import enemies.volatilevolcano.daimanarsonist.DaimanArsonist;
import enemies.volatilevolcano.forsakenchampion.ForsakenChampion;
import project.Area;
import project.Dungeon;
import project.Tile;

public class CorruptedCatacombs extends Dungeon{
	private Pen p = new Pen(0, 0);
	
	public CorruptedCatacombs() {
		super("Corrupted Catacombs", 3);
		this.spawnX = 480;
		this.spawnY = 480;
		this.border = Tile.ICEWALL;
		this.inner = Tile.ICEFLOOR;
	}

	@Override
	public void add(Area a) {
		this.areas.add(a);
	}
	
	public void icePatch(Area a, int x, int y) {
		a.setLine(1, 2, x-2, y-1, Tile.CORRUPTEDICE);
		a.setLine(1, 4, x-1, y-2, Tile.CORRUPTEDICE);
		a.setLine(1, 4, x, y-2, Tile.CORRUPTEDICE);
		a.setLine(1, 2, x+1, y-1, Tile.CORRUPTEDICE);
		a.repair();
	}
	
	public void halfStreak(AreaCorridor a) {
		if(a.getDir() % 2 == 0) {
			icePatch(a, 1, Controller.random.nextInt(0, a.getHeight()));
			icePatch(a, a.getWidth()-1, Controller.random.nextInt(0, a.getHeight()));
		}
		else {
			icePatch(a, Controller.random.nextInt(0, a.getWidth()), 1);
			icePatch(a, Controller.random.nextInt(0, a.getHeight()), a.getHeight()-1);
		}
	}
	
	public void smallGroup(int x, int y) {
		int opt = Controller.random.nextInt(0, 13);
		switch(opt){
			//mages
		case 0:
			new CryoMage(x, y);
			break;
			
			//adepts
		case 1:
			new CryoAdept(x, y);
			break;
			
			//mage + adept
		case 2:
			new CryoMage(x - 40, y - 40);
			new CryoAdept(x + 40, y + 40);
			break;
			
		case 3:
			new CryoMage(x + 40, y - 40);
			new CryoAdept(x - 40, y + 40);
			break;
			
			//crusaders x2
		case 4:
			new TenescowriCrusader(x, y);
			break;
			
			//fleshtrackers
		case 5:
			new TenescowriFleshtracker(x, y);
			break;
			
			//crusader + shaman
		case 6:
			new TenescowriCrusader(x, y + 30);
			new TenescowriShaman(x, y - 30);
			break;
			
			//fleshtracker + shaman
		case 7:
			new TenescowriFleshtracker(x + 30, y);
			new TenescowriShaman(x - 30, y);
			break;
			
			//ice sprite x4
		case 8, 9, 10:
			new IceSprite(x, y);
			break;
			 
			//ice sprites
		case 11:
			new IceSprite(x - 50, y - 50);
			new IceSprite(x + 50, y + 50);
			break;
			
			//mammoth hunter
		case 12:
			new MammothHunter(x, y);
			break;
		}
	}
	
	public void largeGroup(int x, int y) {
		int opt = Controller.random.nextInt(0, 18);
		switch(opt) {
		//camp: cave shrieker, ice sprites x2
		case 0, 1:
			new CaveShrieker(x, y);
			new IceSprite(x - 40, y - 40);
			break;
		
		//camp: cave shrieker, tenescowri shaman
		case 2:
			new CaveShrieker(x, y);
			new TenescowriShaman(x + 40, y + 40);
			break;
		
		//camp: cave shrieker
		case 3:
			new CaveShrieker(x, y);
			break;
		
		//camp: cryo warlock, 2x ice mage
		case 4: 
			new CryoWarlock(x, y);
			new CryoMage(x - 100, y);
			new CryoMage(x + 100, y);
			break;
		
		//camp: cryo warlock, ice mage, ice adept x2
		case 5, 6:
			new CryoWarlock(x, y);
			new CryoMage(x - 80, y - 80);
			new CryoAdept(x + 80, y + 80);
			break;
		
		//camp: cryo warlock, ice adept
		case 7:
			new CryoWarlock(x, y);
			new CryoAdept(x, y + 60);
			break;
		
		//camp: lifecaster, ice sprites
		case 8:
			new Lifecaster(x, y);
			new IceSprite(x - 40, y + 40);
			break;
		
		//camp: lifecaster x2
		case 9, 10:
			new Lifecaster(x, y);
			break;
		
		//camp: tenescowri lieutenant, 2x crusaders, 1x fleshtracker
		case 11:
			new TenescowriLieutenant(x, y);
			new TenescowriCrusader(x - 40, y - 40);
			new TenescowriCrusader(x - 40, y + 40);
			new TenescowriFleshtracker(x + 55, y);
			break;
		
		//camp: tenescowri lieutenant, 2x crusaders, 1x shaman
		case 12:
			new TenescowriLieutenant(x, y);
			new TenescowriCrusader(x - 40, y - 40);
			new TenescowriCrusader(x - 40, y + 40);
			new TenescowriShaman(x + 55, y);
			break;
		
		//camp: tenescowri lieutenant, 1x crusader, 1x shaman, 1x fleshtracker x2
		case 13, 14:
			new TenescowriLieutenant(x, y);
		new TenescowriFleshtracker(x - 40, y - 40);
		new TenescowriShaman(x - 40, y + 40);
		new TenescowriCrusader(x + 55, y);
			break;
		
		//camp: 2x mammoth hunters x2
		case 15, 16:
			new MammothHunter(x, y+60);
			new MammothHunter(x, y-60);
			break;
		
		//camp: 2x cryo adept, 2x cryo mage 
		case 17:
			new CryoAdept(x, y - 90);
			new CryoMage(x - 90, y);
			new CryoMage(x + 90, y);
			new CryoAdept(x, y + 90);
			break;
		}
	}

	public void szRoom(int startDir) {
		Area a = null;
		if(startDir == 0) {
			p.drawX -= 5 * Area.BLOCK_SIZE;
			p.drawY -= 40 * Area.BLOCK_SIZE;
			a = new AreaBox(p.drawX, p.drawY, 50, 40, border, Tile.CORRUPTEDICE, new int[] {});
			a.setBox(10, 2, 4, 37, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(8, 2, 5, 35, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(6, 17, 6, 23, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(6, 17, 39, 0, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(39, 6, 6, 17, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(19, 8, 16, 16, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(15, 12, 20, 14, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			//boxing
			a.setBox(50, 40, 18, 29, Tile.ICEWALL, null);
			a.setBox(50, 40, -17, -29, Tile.ICEWALL, null);
		}
		else if(startDir == 1) {
			p.drawX -= 40 * Area.BLOCK_SIZE;
			p.drawY -= 38 * Area.BLOCK_SIZE;
			a = new AreaBox(p.drawX, p.drawY, 40, 50, border, Tile.CORRUPTEDICE, new int[] {});
			a.setBox(2, 10, 37, 4, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(2, 8, 35, 5, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(17, 6, 0, 6, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(17, 6, 23, 39, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(6, 39, 17, 6, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(8, 19, 16, 16, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(12, 15, 14, 20, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			//boxing
			a.setBox(40, 50, -29, 18, Tile.ICEWALL, null);
			a.setBox(40, 50, 29, -17, Tile.ICEWALL, null);
		}
		else if(startDir == 2) {
			p.drawX -= 38 * Area.BLOCK_SIZE;
			a = new AreaBox(p.drawX, p.drawY, 50, 40, border, Tile.CORRUPTEDICE, new int[] {});
			a.setBox(10, 2, 37, 1, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(8, 2, 38, 3, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(6, 17, 6, 23, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(6, 17, 39, 0, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(39, 6, 6, 17, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(19, 8, 16, 16, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(15, 12, 20, 14, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			//boxing
			a.setBox(50, 40, 18, 29, Tile.ICEWALL, null);
			a.setBox(50, 40, -17, -29, Tile.ICEWALL, null);
		}
		else if(startDir == 3) {
			p.drawY -= 5 * Area.BLOCK_SIZE;
			a = new AreaBox(p.drawX, p.drawY, 40, 50, border, Tile.CORRUPTEDICE, new int[] {});
			a.setBox(2, 10, 1, 37, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(2, 8, 3, 38, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(17, 6, 0, 6, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(17, 6, 23, 39, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(6, 39, 17, 6, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			a.setBox(8, 19, 16, 16, Tile.ICEFLOOR, Tile.ICEFLOOR);
			a.setBox(12, 15, 14, 20, Tile.ICEFLOOR, Tile.ICEFLOOR);
			
			//boxing
			a.setBox(40, 50, -29, 18, Tile.ICEWALL, null);
			a.setBox(40, 50, 29, -17, Tile.ICEWALL, null);
		}
		add(a);
	}	
	
	public void szSummon(int startDir) {
		if(startDir == 0) {
			smallGroup(p.drawX + 300, p.drawY + 800);
			largeGroup(p.drawX + 1000, p.drawY + 800);
			smallGroup(p.drawX + 1700, p.drawY + 800);
		}
		else if(startDir == 1) {
			smallGroup(p.drawX + 800, p.drawY + 300);
			largeGroup(p.drawX + 800, p.drawY + 1000);
			smallGroup(p.drawX + 800, p.drawY + 1700);
		}
		else if(startDir == 2) {
			smallGroup(p.drawX + 300, p.drawY + 800);
			largeGroup(p.drawX + 1000, p.drawY + 800);
			smallGroup(p.drawX + 1700, p.drawY + 800);
		}
		else if(startDir == 3) {
			smallGroup(p.drawX + 800, p.drawY + 300);
			largeGroup(p.drawX + 800, p.drawY + 1000);
			smallGroup(p.drawX + 800, p.drawY + 1700);
		}
	}
	public void szHall(int startDir) {
		if(startDir == 0) {
			p.drawX += 38 * Area.BLOCK_SIZE;
			p.drawY -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(startDir == 1) {
			p.drawX -= 10 * Area.BLOCK_SIZE;
			p.drawY += 5 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(startDir == 2) {
			p.drawX += 5 * Area.BLOCK_SIZE;
			p.drawY += 40 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
			p.drawY += 10 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawX += 40 * Area.BLOCK_SIZE;
			p.drawY += 38 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
			p.drawX += 10 * Area.BLOCK_SIZE;
		}
	}
	
	public void squareRoom(int startDir, int nextDir) {
		if(startDir == 2) {
			p.drawX -= 10 * Area.BLOCK_SIZE;
			p.drawY -= 28 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawY -= 10 * Area.BLOCK_SIZE;
			p.drawX -= 28 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 10 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY -= 10 * Area.BLOCK_SIZE;
		}
		
		Area a = new AreaBox(p.drawX, p.drawY, 28, 28, border, inner, new int[] {});
		if(startDir == 3 || nextDir == 3) {
			a.addExits(new int[] {1, 6, 27, 11});
		}
		if(startDir == 2 || nextDir == 2) {
			a.addExits(new int[] {0, 6, 11, 27});
		}
		if(startDir == 1 || nextDir == 1) {
			a.addExits(new int[] {1, 6, 0, 11});
		}
		if(startDir == 0 || nextDir == 0) {
			a.addExits(new int[] {0, 6, 11, 0});
		}
		
		icePatch(a, 6, 6);
		icePatch(a, 22, 6);
		icePatch(a, 6, 22);
		icePatch(a, 22, 22);
		icePatch(a, 14, 14);
		add(a);
	}
	
	public void squareSummon(int startDir, int nextDir) {
		if(Controller.coinflip()) {
			smallGroup(p.drawX + 260, p.drawY + 260);
			smallGroup(p.drawX + 860, p.drawY + 860);
		}
		else {
			smallGroup(p.drawX + 860, p.drawY + 260);
			smallGroup(p.drawX + 260, p.drawY + 860);
		}
		largeGroup(p.drawX + 560, p.drawY + 560);
	}
	
	public void squareHall(int startDir, int nextDir) {
		if(nextDir == 0) {
			p.drawX += 10 * Area.BLOCK_SIZE;
			p.drawY -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(nextDir == 1) {
			p.drawY += 10 * Area.BLOCK_SIZE;
			p.drawX -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(nextDir == 2) {
			p.drawX += 10 * Area.BLOCK_SIZE;
			p.drawY += 28 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
			p.drawY += 10 * Area.BLOCK_SIZE;
		}
		else if(nextDir == 3) {
			p.drawY += 10 * Area.BLOCK_SIZE;
			p.drawX += 28 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
			p.drawX += 10 * Area.BLOCK_SIZE;
		}
	}
	
	public void deadRoom(int startDir) {
		if(startDir == 2) {
			p.drawX -= 6 * Area.BLOCK_SIZE;
			p.drawY -= 20 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawY -= 6 * Area.BLOCK_SIZE;
			p.drawX -= 20 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 6 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY -= 6 * Area.BLOCK_SIZE;
		}
		
		Area a = new AreaBox(p.drawX, p.drawY, 20, 20, border, inner, new int[] {});
		if(startDir == 3) {
			a.addExits(new int[] {1, 6, 19, 7});
		}
		if(startDir == 2) {
			a.addExits(new int[] {0, 6, 7, 19});
		}
		if(startDir == 1) {
			a.addExits(new int[] {1, 6, 0, 7});
		}
		if(startDir == 0) {
			a.addExits(new int[] {0, 6, 7, 0});
		}
		a.fillExits();
		//maybe add a taunt here
		add(a);
	}
	
	public void deadSummon(int startDir) {
		smallGroup(p.drawX + 300, p.drawY + 400);
		smallGroup(p.drawX + 500, p.drawY + 400);
	}
	
	//
	public void tunnelRoom(int startDir) {	
		if(startDir == 0) {
			p.drawX -= 3 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY -= 3 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawY -= 36 * Area.BLOCK_SIZE;
			p.drawX -= 3 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawX -= 36 * Area.BLOCK_SIZE;
			p.drawY -= 3 * Area.BLOCK_SIZE;
		}
		AreaBox a;
		if(startDir % 2 == 0) {
			a = new AreaBox(p.drawX, p.drawY, 14, 36, border, inner, new int[] {0, 6, 4, 0, 0, 6, 4, 35});
			a.setCircle(-10, 21, 7, Tile.CORRUPTEDICE, Tile.CORRUPTEDICE);
			a.setCircle(8, 1, 7, Tile.CORRUPTEDICE, Tile.CORRUPTEDICE);
			a.repair();
		}
		else {
			a = new AreaBox(p.drawX, p.drawY, 36, 14, border, inner, new int[] {1, 6, 0, 4, 1, 6, 35, 4});
			a.setCircle(21, -10, 7, Tile.CORRUPTEDICE, Tile.CORRUPTEDICE);
			a.setCircle(1, 8, 7, Tile.CORRUPTEDICE, Tile.CORRUPTEDICE);
			a.repair();
		}
		add(a);
	}
	
	public void tunnelSummon(int startDir) {
		if(startDir % 2 == 0) {
			smallGroup(p.drawX + 280, p.drawY + 320);
			largeGroup(p.drawX + 280, p.drawY + 720);
			smallGroup(p.drawX + 280, p.drawY + 1120);
		}
		else {
			smallGroup(p.drawX + 320, p.drawY + 280);
			largeGroup(p.drawX + 720, p.drawY + 280);
			smallGroup(p.drawX + 1120, p.drawY + 280);
		}
	}
		
	public void tunnelFinalSummon(int startDir) {
		if(startDir % 2 == 0) {
			smallGroup(p.drawX + 280, p.drawY + 320);
			smallGroup(p.drawX + 280, p.drawY + 1120);
		}
		else {
			smallGroup(p.drawX + 320, p.drawY + 280);
			smallGroup(p.drawX + 1120, p.drawY + 280);
		}
	}
	
	public void tunnelHall(int startDir) {
		if(startDir == 2) {
			p.drawX += 3 * Area.BLOCK_SIZE;
			p.drawY -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(startDir == 3) {
			p.drawY += 3 * Area.BLOCK_SIZE;
			p.drawX -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(startDir == 0) {
			p.drawX += 3 * Area.BLOCK_SIZE;
			p.drawY += 36 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
			p.drawY += 10 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY += 3 * Area.BLOCK_SIZE;
			p.drawX += 36 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
			p.drawX += 10 * Area.BLOCK_SIZE;
		}
	}
	
	public void clawRoom(int startDir, int nextDir) {
		if(startDir == 2) {
			p.drawX -= 11 * Area.BLOCK_SIZE;
			p.drawY -= 30 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawY -= 11 * Area.BLOCK_SIZE;
			p.drawX -= 30 * Area.BLOCK_SIZE;
		}
		else if(startDir == 0) {
			p.drawX -= 11 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY -= 11 * Area.BLOCK_SIZE;
		}
		
		Area a = new AreaBox(p.drawX, p.drawY, 30, 30, border, inner, new int[] {});
		if(startDir == 3 || nextDir == 3) {
			a.addExits(new int[] {1, 6, 29, 12});
		}
		if(startDir == 2 || nextDir == 2) {
			a.addExits(new int[] {0, 6, 12, 29});
		}
		if(startDir == 1 || nextDir == 1) {
			a.addExits(new int[] {1, 6, 0, 12});
		}
		if(startDir == 0 || nextDir == 0) {
			a.addExits(new int[] {0, 6, 12, 0});
		}
		
		if((startDir == 0 && nextDir == 1) || (startDir == 1 && nextDir == 0)) {
			for(int i = 0; i < 6; i++) {a.setStreakDown(-10, -10, 32, 32, 9, Tile.ICEWALL);}
			for(int i = 0; i < 2; i++) {a.setStreakDown(-10, -10, 25, 25, 5, null, false);}
		}
		else if((startDir == 2 && nextDir == 3) || (startDir == 3 && nextDir == 2)) {
			for(int i = 0; i < 6; i++) {a.setStreakDown(8, 8, 32, 32, 9, Tile.ICEWALL);}
			for(int i = 0; i < 2; i++) {a.setStreakDown(15, 15, 25, 25, 5, null, false);}
		}
		else if((startDir == 1 && nextDir == 2) || (startDir == 2 && nextDir == 1)) {
			for(int i = 0; i < 6; i++) {a.setStreakUp(-10, 40, 32, 32, 9, Tile.ICEWALL);}
			for(int i = 0; i < 2; i++) {a.setStreakUp(-10, 40, 25, 25, 5, null, false);}
		}
		else {
			for(int i = 0; i < 6; i++) {a.setStreakUp(8, 22, 32, 32, 9, Tile.ICEWALL);}
			for(int i = 0; i < 2; i++) {a.setStreakUp(15, 15, 25, 25, 5, null, false);}
		}
		add(a);
	}
	
	public void clawSummon(int startDir, int nextDir) {
		if((startDir == 0 && nextDir == 1) || (startDir == 1 && nextDir == 0)) {
			largeGroup(p.drawX + 900, p.drawY + 300);
			largeGroup(p.drawX + 300, p.drawY + 900);
		}
		else if((startDir == 2 && nextDir == 3) || (startDir == 3 && nextDir == 2)) {
			largeGroup(p.drawX + 900, p.drawY + 300);
			largeGroup(p.drawX + 300, p.drawY + 900);
		}
		else if((startDir == 1 && nextDir == 2) || (startDir == 2 && nextDir == 1)) {
			largeGroup(p.drawX + 300, p.drawY + 300);
			largeGroup(p.drawX + 900, p.drawY + 900);
		}
		else {
			largeGroup(p.drawX + 300, p.drawY + 300);
			largeGroup(p.drawX + 900, p.drawY + 900);
		}
	}
	
	public void clawHall(int startDir, int nextDir) {
		if(nextDir == 0) {
			p.drawX += 11 * Area.BLOCK_SIZE;
			p.drawY -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(nextDir == 1) {
			p.drawY += 11 * Area.BLOCK_SIZE;
			p.drawX -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(nextDir == 2) {
			p.drawX += 11 * Area.BLOCK_SIZE;
			p.drawY += 30 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
			p.drawY += 10 * Area.BLOCK_SIZE;
		}
		else if(nextDir == 3) {
			p.drawY += 11 * Area.BLOCK_SIZE;
			p.drawX += 30 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
			p.drawX += 10 * Area.BLOCK_SIZE;
		}
	}
	
	public void splitRoom(int startDir, int v, int h) {
		if(startDir == 0) {
			p.drawX -= 14 * Area.BLOCK_SIZE;
			p.drawY -= 36 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY -= 14 * Area.BLOCK_SIZE;
			p.drawX -= 36 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawX -= 14 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawY -= 14 * Area.BLOCK_SIZE;
		}
		
		Area a = new AreaBox(p.drawX, p.drawY, 36, 36, border, Tile.CORRUPTEDICE, new int[] {});
		a.setCircle(9, 9, 9, inner, inner);
		if(startDir % 2 == 0) {
			a.setBox(6, 36, 15, 0, inner, inner);
			if(h == 1) {
				a.setBox(16, 6, 0, 15, inner, inner);
			}
			else {
				a.setBox(16, 6, 20, 15, inner, inner);
			}
		}
		else {
			a.setBox(36, 6, 0, 15, inner, inner);
			if(v == 0) {
				a.setBox(6, 16, 15, 0, inner, inner);
			}
			else {
				a.setBox(6, 16, 15, 20, inner, inner);
			}
		}
		add(a);
	}
	
	public void splitCraterRoom(int startDir, int v, int h) {
		if(startDir == 0) {
			p.drawX -= 14 * Area.BLOCK_SIZE;
			p.drawY -= 36 * Area.BLOCK_SIZE;
		}
		else if(startDir == 1) {
			p.drawY -= 14 * Area.BLOCK_SIZE;
			p.drawX -= 36 * Area.BLOCK_SIZE;
		}
		else if(startDir == 2) {
			p.drawX -= 14 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawY -= 14 * Area.BLOCK_SIZE;
		}
		
		Area a = new AreaBox(p.drawX, p.drawY, 36, 36, border, Tile.CORRUPTEDICE, new int[] {});
		a.setCircle(9, 9, 9, inner, inner);
		if(startDir % 2 == 0) {
			a.setBox(6, 36, 15, 0, inner, inner);
			if(h == 1) {
				a.setBox(16, 6, 0, 15, inner, inner);
			}
			else {
				a.setBox(16, 6, 20, 15, inner, inner);
			}
		}
		else {
			a.setBox(36, 6, 0, 15, inner, inner);
			if(v == 0) {
				a.setBox(6, 16, 15, 0, inner, inner);
			}
			else {
				a.setBox(6, 16, 15, 20, inner, inner);
			}
		}
		a.setCircle(13, 13, 5, Tile.CORRUPTEDICE, Tile.CORRUPTEDICE);
		add(a);
	}
	
	public void splitSummon(int startDir, int v, int h) {
		largeGroup(p.drawX + 720, p.drawY + 720);
	}
	
	public int[] splitHallStraight(int startDir, int v, int h) {
		int[] checkpoint = new int[] {p.drawX, p.drawY};
		if(startDir == 0) {
			p.drawX += 14 * Area.BLOCK_SIZE;
			p.drawY -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(startDir == 1) {
			p.drawY += 14 * Area.BLOCK_SIZE;
			p.drawX -= 10 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
		}
		else if(startDir == 2) {
			p.drawX += 14 * Area.BLOCK_SIZE;
			p.drawY += 36 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
			halfStreak(b);
			add(b);
			p.drawY += 10 * Area.BLOCK_SIZE;
		}
		else if(startDir == 3) {
			p.drawY += 14 * Area.BLOCK_SIZE;
			p.drawX += 36 * Area.BLOCK_SIZE;
			AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
			halfStreak(b);
			add(b);
			p.drawX += 10 * Area.BLOCK_SIZE;
		}
		return checkpoint;
	}
	
	//always comes after recursively generating main path
	public void splitHallPerpendicular(int startDir, int v, int h) {
		if(startDir % 2 == 0) {
			if(h == 1) {
				p.drawY += 14 * Area.BLOCK_SIZE;
				p.drawX -= 10 * Area.BLOCK_SIZE;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
				halfStreak(b);
				add(b);
			}
			else {
				p.drawY += 14 * Area.BLOCK_SIZE;
				p.drawX += 36 * Area.BLOCK_SIZE;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
				halfStreak(b);
				add(b);
				p.drawX += 10 * Area.BLOCK_SIZE;
			}
		}
		else {
			if(v == 0) {
				p.drawX += 14 * Area.BLOCK_SIZE;
				p.drawY -= 10 * Area.BLOCK_SIZE;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
				halfStreak(b);
				add(b);
			}
			else {
				p.drawX += 14 * Area.BLOCK_SIZE;
				p.drawY += 36 * Area.BLOCK_SIZE;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
				halfStreak(b);
				add(b);
				p.drawY += 10 * Area.BLOCK_SIZE;
			}
		}
	}

	@Override
	public void generate() {
		int v, h;
		if(Controller.coinflip()) {v = 0;}
		else {v = 2;}
		if(Controller.coinflip()) {h = 1;}
		else {h = 3;}
		if(Controller.coinflip()) {
			path(v, h, 1, v, -1, false); //7
		}
		else {
			path(v, h, 1, h, -1, false); //7
		}
	}
	
	public void path(int v, int h, int space, int lastDir, int seed, boolean split) {
		if(space == 0) {
			if(split) {
				deadRoom((lastDir+2)%4);
				deadSummon((lastDir+2)%4);
			}
			else {
				bossPath(v, h, 2, lastDir, 0, false);
			}
			return;
		}
		if(seed == -1) {
			AreaCircle a = new AreaCircle(p.drawX, p.drawY, 12, border, inner, new int[] {});
			icePatch(a, 5, 5);
			icePatch(a, 5, 19);
			icePatch(a, 19, 5);
			icePatch(a, 19, 19);
			add(a);
			new IceSprite(p.drawX + 200, p.drawY + 480);
			new IceSprite(p.drawX + 760, p.drawY + 480);
			
			if(lastDir == 0) {
				a.addExits(new int[] {0, 6, 9, 0});
				a.fillExits();
				p.drawX += 320;
				p.drawY -= 400;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
				halfStreak(b);
				add(b);
			}
			else if(lastDir == 1) {
				a.addExits(new int[] {1, 6, 0, 9});
				a.fillExits();
				p.drawY += 320;
				p.drawX -= 400;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
				halfStreak(b);
				add(b);
			}
			else if(lastDir == 2) {
				a.addExits(new int[] {0, 6, 9, 23});
				a.fillExits();
				p.drawX += 320;
				p.drawY += 960;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 8, 10, 0, border, inner);
				halfStreak(b);
				add(b);
				p.drawY += 400;
			}
			else if(lastDir == 3) {
				a.addExits(new int[] {1, 6, 23, 9});
				a.fillExits();
				p.drawY += 320;
				p.drawX += 960;
				AreaCorridor b = new AreaCorridor(p.drawX, p.drawY, 10, 8, 1, border, inner);
				halfStreak(b);
				add(b);
				p.drawX += 400;
			}
			
			path(v, h, space-1, lastDir, this.getSeed(0, 5, seed), split);
		}
		else if(seed == 0) {
			szRoom(lastDir);
			szSummon(lastDir);
			szHall(lastDir);
			path(v, h, space-1, lastDir, this.getSeed(0, 5, seed), split);
		}
		else if(seed == 1) {
			if(Controller.coinflip()) {
				squareRoom((lastDir+2) % 4, h);
				squareSummon((lastDir+2)%4, h);
				squareHall((lastDir+2) % 4, h);
				path(v, h, space-1, h, this.getSeed(0, 5, seed), split);
			}
			else {
				squareRoom((lastDir+2) % 4, v);
				squareSummon((lastDir+2)%4, h);
				squareHall((lastDir+2) % 4, v);
				path(v, h, space-1, v, this.getSeed(0, 5, seed), split);
			}
		}
		else if(seed == 2) {
			tunnelRoom((lastDir+2)%4);
			tunnelSummon((lastDir+2)%4);
			tunnelHall((lastDir+2)%4);
			path(v, h, space-1, lastDir, this.getSeed(0, 5, seed), split);
		}
		else if(seed == 3) {
			if(lastDir % 2 == 0) {
				clawRoom((lastDir+2)%4, h);
				clawSummon((lastDir+2)%4, h);
				clawHall((lastDir+2)%4, h);
				path(v, h, space-1, h, this.getSeed(0, 5, seed), split);
			}
			else {
				clawRoom((lastDir+2)%4, v);
				clawSummon((lastDir+2)%4, v);
				clawHall((lastDir+2)%4, v);
				path(v, h, space-1, v, this.getSeed(0, 5, seed), split);
			}
			
		}
		else if(seed == 4) {
			splitRoom(lastDir, v, h);
			splitSummon(lastDir, v, h);
			if(Controller.coinflip()) {
				int[] checkpoint = splitHallStraight(lastDir, v, h);
				path(v, h, space-1, lastDir, 2, split);
				p.drawX = checkpoint[0];
				p.drawY = checkpoint[1];
				splitHallPerpendicular(lastDir, v, h);
				if(lastDir % 2 == 0) {
					path(v, h, 1, h, 2, true);
				}
				else {
					path(v, h, 1, v, 2, true);
				}
			}
			else {
				int[] checkpoint = splitHallStraight(lastDir, v, h);
				path(v, h, 1, lastDir, 2, true);
				p.drawX = checkpoint[0];
				p.drawY = checkpoint[1];
				splitHallPerpendicular(lastDir, v, h);
				if(lastDir % 2 == 0) {
					path(v, h, space-1, h, 2, split);
				}
				else {
					path(v, h, space-1, v, 2, split);
				}
			}
		}
	}
	
	public void bossPath(int v, int h, int space, int lastDir, int seed, boolean split) {
		if(space == 0) {
			if(split) {
				//t room
				//24, 24
				if(lastDir == 0) {
					p.drawX -= 8 * Area.BLOCK_SIZE;
					p.drawY -= 24 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 24, 24, border, inner, new int[] {0, 6, 9, 23});
					add(a);
				}
				else if(lastDir == 1) {
					p.drawX -= 24 * Area.BLOCK_SIZE;
					p.drawY -= 8 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 24, 24, border, inner, new int[] {1, 6, 23, 9});
					add(a);
				}
				else if(lastDir == 2) {
					p.drawX -= 8 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 24, 24, border, inner, new int[] {0, 6, 9, 0});
					add(a);
				}
				else if(lastDir == 3) {
					p.drawY -= 8 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 24, 24, border, inner, new int[] {1, 6, 0, 9});
					add(a);
				}
				new UnhallowedRevenant(p.drawX + 480, p.drawY + 480);
				return;
			}
			else {
				//boss room
				//20, 20
				if(lastDir == 0) {
					p.drawX -= 6 * Area.BLOCK_SIZE;
					p.drawY -= 20 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 20, 20, border, inner, new int[] {0, 6, 7, 19});
					add(a);
				}
				else if(lastDir == 1) {
					p.drawX -= 20 * Area.BLOCK_SIZE;
					p.drawY -= 6 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 20, 20, border, inner, new int[] {1, 6, 19, 7});
					add(a);
				}
				else if(lastDir == 2) {
					p.drawX -= 6 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 20, 20, border, inner, new int[] {0, 6, 7, 0});
					add(a);
				}
				else if(lastDir == 3) {
					p.drawY -= 6 * Area.BLOCK_SIZE;
					AreaBox a = new AreaBox(p.drawX, p.drawY, 20, 20, border, inner, new int[] {1, 6, 0, 7});
					add(a);
				}
				new LostSentinel(p.drawX + 400, p.drawY + 105);
				return;
			}
		}
		if(seed == 0) {
			splitCraterRoom(lastDir, v, h);
			if(v == 0) { //if possible, main will be up
				int[] checkpoint = splitHallStraight(lastDir, v, h);
				if(lastDir % 2 == 0) {
					bossPath(v, h, space-1, lastDir, 1, split);
					p.drawX = checkpoint[0];
					p.drawY = checkpoint[1];
					splitHallPerpendicular(lastDir, v, h);
					bossPath(v, h, 1, h, 1, true);
				}
				else {
					bossPath(v, h, space-1, lastDir, 1, true);
					p.drawX = checkpoint[0];
					p.drawY = checkpoint[1];
					splitHallPerpendicular(lastDir, v, h);
					bossPath(v, h, 1, v, 1, split);
				}
			}
			else { //otherwise, main will be on the side
				int[] checkpoint = splitHallStraight(lastDir, v, h);
				if(lastDir % 2 == 0) {
					bossPath(v, h, 1, lastDir, 1, true);
					p.drawX = checkpoint[0];
					p.drawY = checkpoint[1];
					splitHallPerpendicular(lastDir, v, h);
					bossPath(v, h, space-1, h, 1, split);
				}
				else {
					bossPath(v, h, 1, lastDir, 1, split);
					p.drawX = checkpoint[0];
					p.drawY = checkpoint[1];
					splitHallPerpendicular(lastDir, v, h);
					bossPath(v, h, space-1, v, 1, true);
				}
			}
		}
		
		else if(seed == 1) {
			tunnelRoom((lastDir+2)%4);
			tunnelFinalSummon((lastDir+2)%4);
			tunnelHall((lastDir+2)%4);
			bossPath(v, h, space-1, lastDir, 1, split);
		}
	}
}
