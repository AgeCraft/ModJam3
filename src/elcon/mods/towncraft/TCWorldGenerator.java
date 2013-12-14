package elcon.mods.towncraft;

import java.util.Random;

import net.minecraft.world.World;
import cpw.mods.fml.common.IWorldGenerator;
import elcon.mods.towncraft.structures.Structure;
import elcon.mods.towncraft.structures.StructureGenerator;
import elcon.mods.towncraft.structures.StructureInstance;
import elcon.mods.towncraft.structures.StructureTest;

public class TCWorldGenerator implements IWorldGenerator {

	public Structure structure;
	public StructureGenerator structureGenerator;
	public StructureInstance structureInstance;
	
	public TCWorldGenerator() {

	}
	
	public boolean generate(World world, Random random, int x, int y, int z) {
		int chunkX = x / 16;
		int chunkZ = z / 16;
		if(chunkX == 0 && chunkZ == 0) {
			structure = new StructureTest("test");
			structureGenerator = new StructureGenerator(world, x, y, z, structure);
			structureInstance = structureGenerator.structureInstance;
			return true;
		}
		return false;
	}
}
