package portals;

import dungeons.SilentSands;
import project.Portal;

public class SilentSandsPortal extends Portal{
	public SilentSandsPortal(int x, int y, boolean restart) {
		super(x, y);
		this.destination = new SilentSands();
		this.img = Portal.SILENTSANDS_PORTAL;
		this.title = "Silent Sands";
		this.range = 50;
		
		if(restart) {
			this.title = "Replay Level";
		}
	}
}
