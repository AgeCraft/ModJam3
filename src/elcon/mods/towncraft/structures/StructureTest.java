package elcon.mods.towncraft.structures;

import net.minecraft.block.Block;
import net.minecraftforge.common.ForgeDirection;

public class StructureTest extends Structure {

	public StructureTest(String name) {
		super(name);
		setMinSize(16, 16, 16);
		setMaxSize(32, 32, 32);
		setMinMaxComponents(1, 10);
		startComponent = "component1";
		StructureComponent component1 = new StructureComponent("component1");
		component1.setSize(4, 4, 4);
		component1.setMinMaxOccurrences(1, 10);
		for(int i = 0; i < 64; i++) {
			component1.blockIDs[i] = Block.cloth.blockID;
			component1.blockMetadata[i] = (byte) random.nextInt(16);
		}
		component1.addAdjacentComponent(ForgeDirection.EAST, new StructureAdjacentComponent("component2", 0, 0, 1, 1.0F));
		addComponent(component1);
		
		StructureComponent component2 = new StructureComponent("component2");
		component2.setSize(4, 5, 6);
		component2.setMinMaxOccurrences(1, 10);
		for(int i = 0; i < 120; i++) {
			component2.blockIDs[i] = Block.blockDiamond.blockID;
		}
		component2.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("component1", 0.5F));
		component2.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("component3", 0.5F));
		addComponent(component2);
		
		StructureComponent component3 = new StructureComponent("component3");
		component3.setSize(9, 9, 9);
		component3.setMinMaxOccurrences(1, 10);
		for(int i = 0; i < 9; i++) {
			for(int j = 0; j < 9; j++) {
				for(int k = 0; k < 9; k++) {
					if((i == 0 || i == 8) && (j == 0 || j == 8) && (k == 0 || k == 8)) {
						component1.blockIDs[i] = Block.stoneBrick.blockID;
					}
				}
			}
		}
		addComponent(component3);
	}
}
