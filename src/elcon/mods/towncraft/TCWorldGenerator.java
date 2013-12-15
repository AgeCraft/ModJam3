package elcon.mods.towncraft;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import cpw.mods.fml.common.IWorldGenerator;
import elcon.mods.towncraft.structures.Structure;
import elcon.mods.towncraft.structures.StructureGenerator;
import elcon.mods.towncraft.structures.StructureInstance;

public class TCWorldGenerator implements IWorldGenerator {

	public Structure structure;
	public StructureGenerator structureGenerator;
	public StructureInstance structureInstance;
	
	public TCWorldGenerator() {

	}

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
		/*if(world.provider.dimensionId == 0 && chunkX == 0 && chunkZ == 0) {
			structure = new StructureTest("test");
			structureGenerator = new StructureGenerator(world, chunkX * 16, 64, chunkZ * 16, structure);
			structureInstance = structureGenerator.structureInstance;
		}*/
	}
}
