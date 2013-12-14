package elcon.mods.towncraft.structures;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class StructureGenerator {
	
	public World world;
	public Random random;
	public int x;
	public int y;
	public int z;
	
	public Structure structure;
	public StructureInstance structureInstance;
	
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	
	public int componentCount;
	public HashMap<String, Integer> componentOccurrences = new HashMap<String, Integer>();
	
	public LinkedList<AxisAlignedBB> boundingBoxes = new LinkedList<AxisAlignedBB>();
	
	public StructureGenerator(World world, int x, int y, int z, Structure structure) {
		this.world = world;
		this.random = world.rand;
		this.x = x;
		this.y = y;
		this.z = z;
		this.structure = structure;
		structureInstance = new StructureInstance(structure.name);
		structureInstance.setPosition(x, y, z);
		generateSize();
		generateComponents();
		generateInWorld();
	}

	public void generateSize() {
		sizeX = structure.minSizeX + random.nextInt(structure.maxSizeX - structure.minSizeX);
		sizeY = structure.minSizeY + random.nextInt(structure.maxSizeY - structure.minSizeY);
		sizeZ = structure.minSizeZ + random.nextInt(structure.maxSizeZ - structure.minSizeZ);
		
		structureInstance.setSize(sizeX, sizeY, sizeZ);
		
		componentCount = structure.minComponentCount + random.nextInt(structure.maxComponentCount - structure.minComponentCount);
		structureInstance.componentCount = componentCount;
	}
	
	public void generateComponents() {
		for(StructureComponent component : structure.components.values()) {
			componentOccurrences.put(component.name, component.minOccurrences + random.nextInt(component.maxOccurrences - component.minOccurrences));
		}
	}
	
	public void generateInWorld() {
		
	}
}
