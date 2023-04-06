package portals;

import dungeons.WildWetlands;
import project.Portal;

public class WildWetlandsPortal extends Portal{
	public WildWetlandsPortal(int x, int y, boolean restart) {
		super(x, y);
		this.destination = new WildWetlands();
		this.img = Portal.WILDWETLANDS_PORTAL;
		this.title = "Wild Wetlands";
		this.range = 50;
	}
}
