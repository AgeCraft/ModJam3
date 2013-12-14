package elcon.mods.towncraft.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

import elcon.mods.towncraft.TCLog;
import elcon.mods.towncraft.TCUtil;

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
		TCLog.info("[Structures] Generating structure: " + structure.name);
		generateSize();
		generateComponents();
		generateInWorld();
		structureInstance.componentOccurrences.putAll(componentOccurrences);
	}

	public void generateSize() {
		sizeX = structure.minSizeX + random.nextInt(structure.maxSizeX - structure.minSizeX);
		sizeY = structure.minSizeY + random.nextInt(structure.maxSizeY - structure.minSizeY);
		sizeZ = structure.minSizeZ + random.nextInt(structure.maxSizeZ - structure.minSizeZ);
		boundingBox = AxisAlignedBB.getBoundingBox(0, 0, 0, sizeX, sizeY, sizeZ);
		componentCount = structure.minComponentCount + random.nextInt(structure.maxComponentCount - structure.minComponentCount);
		
		TCLog.info("[Structures] Structure size: " + TCUtil.coordsToString(sizeX, sizeY, sizeZ) + " with " + componentCount + " components");
		
		structureInstance.setSize(sizeX, sizeY, sizeZ);
		structureInstance.boundingBox = boundingBox.copy().addCoord(x, y, z);
		structureInstance.componentCount = componentCount;
	}
	
	public void generateComponents() {
		for(StructureComponent component : structure.components.values()) {
			componentMaxOccurrences.put(component.name, component.minOccurrences + random.nextInt(component.maxOccurrences - component.minOccurrences));
			componentOccurrences.put(component.name, 0);
			TCLog.info("                " + componentMaxOccurrences.get(component.name) + " x " + component.name);
		}
		generateComponent(structure.components.get(structure.startComponent), 0, 0, 0, null);
	}
	
	public void generateComponent(StructureComponent component, int x, int y, int z, StructureComponentInstance previousComponent) {
		TCLog.info("[Structures] Generating component " + component.name + " at " + TCUtil.coordsToString(x, y, z));
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
						boundingBoxes.add(box);
						StructureComponentInstance instance = new StructureComponentInstance(structure.name, adjacentComponentBase.name);
						instance.boundingBox = box.copy().addCoord(this.x, this.y, this.z);
						instance.setPosition(this.x + xx, this.y + yy, this.y + zz);
						instance.neighbors[ForgeDirection.OPPOSITES[direction.ordinal()]] = previousComponent;
						componentOccurrences.put(instance.name, componentOccurrences.get(instance.name) + 1);
						structureInstance.components.add(instance);
						generateComponent(adjacentComponentBase, xx, yy, zz, instance);
						proceed = true;
						TCLog.info("[Structures] Added component " + instance.name + " at " + TCUtil.coordsToString(xx, yy, zz));
					} else {
						TCLog.info("[Structures] Can't place component " + adjacentComponentBase.name + " at " + TCUtil.coordsToString(xx, yy, zz));
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
