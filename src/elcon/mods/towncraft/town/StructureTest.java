package elcon.mods.towncraft.town;

import elcon.mods.towncraft.structures.Structure;
import elcon.mods.towncraft.structures.StructureComponent;

public class StructureTest extends Structure {

	public StructureTest(String name) {
		super(name);
		setMinSize(16, 16, 16);
		setMaxSize(32, 32, 32);
		setMinMaxComponents(1, 10);
		StructureComponent component1 = new StructureComponent("component1");
		component1.setSize(4, 4, 4);
		for(int i = 0; i < 64; i++) {
			
		}
	}
}
