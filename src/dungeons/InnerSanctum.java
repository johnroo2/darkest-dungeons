package dungeons;

import control.Driver;
import project.Area;
import project.Dungeon;

public class InnerSanctum extends Dungeon{

	public InnerSanctum() {
		super("Inner Sanctum", 3);
	}

	@Override
	public void add(Area a) {
		
	}

	@Override
	public void generate() {
		Driver.finish();
	}
}
