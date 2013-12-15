package elcon.mods.towncraft.structures;

import net.minecraft.block.Block;
import net.minecraftforge.common.ForgeDirection;

public class StructureTestTown extends Structure {

	public StructureTestTown(String name) {
		super(name);
		setMinSize(96, 96, 96);
		setMaxSize(96, 96, 96);
		setMinMaxComponents(30, 50);
		startComponent = "townHall";
		
		StructureComponent townHall = new StructureComponent("townHall");
		townHall.setSize(11, 11, 11);
		townHall.setMinMaxOccurrences(1, 1);
		for(int i = 0; i < 11; i++) {
			for(int j = 0; j < 11; j++) {
				for(int k = 0; k < 11; k++) {
					if((i == 0 || i == 10) || (j == 0 || j == 10) || (k == 0 || k == 10)) {
						townHall.blockIDs[i + j * 11 + k * 121] = Block.stoneBrick.blockID;
					}
				}
			}
		}
		townHall.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("roadZ", 4, 0, 0, 1.0F));
		townHall.addAdjacentComponent(ForgeDirection.SOUTH, new StructureAdjacentComponent("roadZ", 4, 0, 0, 1.0F));
		townHall.addAdjacentComponent(ForgeDirection.EAST, new StructureAdjacentComponent("roadX", 0, 0, 4, 1.0F));
		townHall.addAdjacentComponent(ForgeDirection.WEST, new StructureAdjacentComponent("roadX", 0, 0, 4, 1.0F));
		addComponent(townHall);
		
		StructureComponent roadZ = new StructureComponent("roadZ");
		roadZ.setSize(3, 1, 10);
		roadZ.setMinMaxOccurrences(20, 20);
		for(int i = 0; i < 30; i++) {
			roadZ.blockIDs[i] = Block.dirt.blockID;
		}
		roadZ.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("roadZ", 0.5F));
		roadZ.addAdjacentComponent(ForgeDirection.SOUTH, new StructureAdjacentComponent("roadZ", 0.5F));
		roadZ.addAdjacentComponent(ForgeDirection.EAST, new StructureAdjacentComponent("roadX", 0.25F));
		roadZ.addAdjacentComponent(ForgeDirection.WEST, new StructureAdjacentComponent("roadX", 0.25F));
		roadZ.addAdjacentComponent(ForgeDirection.EAST, new StructureAdjacentComponent("house1", 0, 0, 4, 0.5F));
		roadZ.addAdjacentComponent(ForgeDirection.EAST, new StructureAdjacentComponent("house2", 0, 0, 4, 0.5F));
		roadZ.addAdjacentComponent(ForgeDirection.WEST, new StructureAdjacentComponent("house1", 0, 0, 4, 0.5F));
		roadZ.addAdjacentComponent(ForgeDirection.WEST, new StructureAdjacentComponent("house2", 0, 0, 4, 0.5F));
		addComponent(roadZ);
		
		StructureComponent roadX = new StructureComponent("roadX");
		roadX.setSize(10, 1, 3);
		roadX.setMinMaxOccurrences(20, 20);
		for(int i = 0; i < 30; i++) {
			roadX.blockIDs[i] = Block.dirt.blockID;
		}
		roadX.addAdjacentComponent(ForgeDirection.EAST, new StructureAdjacentComponent("roadX", 0.5F));
		roadX.addAdjacentComponent(ForgeDirection.WEST, new StructureAdjacentComponent("roadX", 0.5F));
		roadX.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("roadZ", 0.25F));
		roadX.addAdjacentComponent(ForgeDirection.SOUTH, new StructureAdjacentComponent("roadZ", 0.25F));
		roadX.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("house1", 4, 0, 0, 0.5F));
		roadX.addAdjacentComponent(ForgeDirection.NORTH, new StructureAdjacentComponent("house2", 4, 0, 0, 0.5F));
		roadX.addAdjacentComponent(ForgeDirection.SOUTH, new StructureAdjacentComponent("house1", 4, 0, 0, 0.5F));
		roadX.addAdjacentComponent(ForgeDirection.SOUTH, new StructureAdjacentComponent("house2", 4, 0, 0, 0.5F));
		addComponent(roadX);
		
		StructureComponent house1 = new StructureComponent("house1");
		house1.setSize(5, 6, 5);
		house1.setMinMaxOccurrences(20, 30);
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 6; j++) {
				for(int k = 0; k < 5; k++) {
					if((i == 0 || i == 4) || (j == 0 || j == 5) || (k == 0 || k == 4)) {
						house1.blockIDs[i + j * 5 + k * 30] = Block.planks.blockID;
					}
				}
			}
		}
		addComponent(house1);
		
		StructureComponent house2 = new StructureComponent("house2");
		house2.setSize(5, 6, 5);
		house2.setMinMaxOccurrences(20, 30);
		for(int i = 0; i < 5; i++) {
			for(int j = 0; j < 6; j++) {
				for(int k = 0; k < 5; k++) {
					if((i == 0 || i == 4) || (j == 0 || j == 5) || (k == 0 || k == 4)) {
						house2.blockIDs[i + j * 5 + k * 30] = Block.planks.blockID;
						house2.blockMetadata[i + j * 5 + k * 30] = 1;
					}
				}
			}
		}
		addComponent(house2);
	}
}
