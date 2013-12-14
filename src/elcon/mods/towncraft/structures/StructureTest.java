package elcon.mods.towncraft.structures;

import net.minecraft.block.Block;

public class StructureTest extends Structure {

	public StructureTest(String name) {
		super(name);
		setMinSize(16, 16, 16);
		setMaxSize(32, 32, 32);
		setMinMaxComponents(1, 10);
		StructureComponent component1 = new StructureComponent("component1");
		component1.setSize(4, 4, 4);
		for(int i = 0; i < 64; i++) {
			component1.blockIDs[i] = Block.cloth.blockID;
			component1.blockMetadata[i] = (byte) random.nextInt(16);
		}
		
		StructureComponent component2 = new StructureComponent("component2");
		component2.setSize(4, 5, 6);
		for(int i = 0; i < 120; i++) {
			component2.blockIDs[i] = Block.blockDiamond.blockID;
		}
	}
}
