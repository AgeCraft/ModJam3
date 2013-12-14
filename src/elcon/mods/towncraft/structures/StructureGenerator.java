package elcon.mods.towncraft.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

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
	public HashMap<String, Integer> componentMaxOccurrences = new HashMap<String, Integer>();
	public HashMap<String, Integer> componentOccurrences = new HashMap<String, Integer>();
	
	public AxisAlignedBB boundingBox;
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
		boundingBox = AxisAlignedBB.getBoundingBox(0, 0, 0, sizeX, sizeY, sizeZ);
		componentCount = structure.minComponentCount + random.nextInt(structure.maxComponentCount - structure.minComponentCount);
		
		structureInstance.setSize(sizeX, sizeY, sizeZ);
		structureInstance.boundingBox = AxisAlignedBB.getBoundingBox(x, y, z, x + sizeX, y + sizeY, z + sizeZ);
		structureInstance.componentCount = componentCount;
	}
	
	public void generateComponents() {
		for(StructureComponent component : structure.components.values()) {
			componentMaxOccurrences.put(component.name, component.minOccurrences + random.nextInt(component.maxOccurrences - component.minOccurrences));
			componentOccurrences.put(component.name, 0);
		}
		generateComponent(structure.components.get(structure.startComponent), 0, 0, 0, null);
	}
	
	public void generateComponent(StructureComponent component, int x, int y, int z, StructureComponentInstance previousComponent) {
		for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
			ArrayList<StructureAdjacentComponent> choosableComponents = new ArrayList<StructureAdjacentComponent>();
			choosableComponents.addAll(component.getAdjacentComponents(direction));
			boolean proceed = false;
			while(!proceed) {
				StructureAdjacentComponent adjacentComponent = choosableComponents.get(random.nextInt(choosableComponents.size()));
				StructureComponent adjacentComponentBase = structure.components.get(adjacentComponent.name);
				if(random.nextFloat() <= adjacentComponent.chance) {
					int xx = x + (direction.offsetX * component.sizeX) + adjacentComponent.offsetX;
					int yy = y + (direction.offsetY * component.sizeY) + adjacentComponent.offsetY;
					int zz = z + (direction.offsetZ * component.sizeZ) + adjacentComponent.offsetZ;
					AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xx, yy, zz, xx + adjacentComponentBase.sizeX, yy + adjacentComponentBase.sizeY, adjacentComponentBase.sizeZ);
					if(canGenerate(box)) {
						StructureComponentInstance instance = new StructureComponentInstance(structure.name, adjacentComponentBase.name);
						instance.boundingBox = box;
						instance.setPosition(this.x + xx, this.y + yy, this.y + zz);
						instance.neighbors[ForgeDirection.OPPOSITES[direction.ordinal()]] = previousComponent;
						componentOccurrences.put(instance.name, componentOccurrences.get(instance.name) + 1);
						boundingBoxes.add(instance.boundingBox);
					} else {
						choosableComponents.remove(adjacentComponent);
					}
				}
			}
		}
	}
	
	public void generateInWorld() {
		
	}
	
	public boolean canGenerate(AxisAlignedBB box) {
		if(box.minX >= boundingBox.minX && box.minY >= boundingBox.minY && box.minZ >= boundingBox.minZ && box.maxX <= boundingBox.maxX && box.maxY <= boundingBox.maxY && box.maxZ <= boundingBox.maxZ) {
			for(AxisAlignedBB b : boundingBoxes) {
				if(b.intersectsWith(box)) {
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
