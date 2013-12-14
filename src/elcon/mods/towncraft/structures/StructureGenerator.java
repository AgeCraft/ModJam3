package elcon.mods.towncraft.structures;

import java.util.Random;

import net.minecraft.world.World;

public class StructureGenerator {
	
	public World world;
	public Random random;
	public int x;
	public int y;
	public int z;
	
	public Structure structure;
	
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	
	public StructureGenerator(World world, int x, int y, int z, Structure structure) {
		this.world = world;
		this.random = world.rand;
		this.x = x;
		this.y = y;
		this.z = z;
		this.structure = structure;
		generateSize();
	}

	public void generateSize() {
		sizeX = structure.minSizeX + random.nextInt(structure.maxSizeX - structure.minSizeX);
		sizeY = structure.minSizeY + random.nextInt(structure.maxSizeY - structure.minSizeY);
		sizeZ = structure.minSizeZ + random.nextInt(structure.maxSizeZ - structure.minSizeZ);
	}
}
