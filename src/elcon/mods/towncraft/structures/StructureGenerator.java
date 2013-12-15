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
		if(world != null) {
			this.random = world.rand;
		} else {
			this.random = new Random();
		}
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
		sizeX = structure.minSizeX == structure.maxSizeX ? structure.minSizeX : structure.minSizeX + random.nextInt(structure.maxSizeX - structure.minSizeX);
		sizeY = structure.minSizeY + random.nextInt(structure.maxSizeY - structure.minSizeY);
		sizeZ = structure.minSizeZ + random.nextInt(structure.maxSizeZ - structure.minSizeZ);
		boundingBox = AxisAlignedBB.getBoundingBox(-sizeX / 2, -sizeY / 2, -sizeZ / 2, sizeX / 2, sizeY / 2, sizeZ / 2);
		componentCount = structure.minComponentCount + random.nextInt(structure.maxComponentCount - structure.minComponentCount);
		
		TCLog.info("[Structures] Structure size: " + TCUtil.coordsToString(sizeX, sizeY, sizeZ) + " with " + componentCount + " components");
		
		structureInstance.setSize(sizeX, sizeY, sizeZ);
		structureInstance.boundingBox = boundingBox.copy().addCoord(x, y, z);
		structureInstance.componentCount = componentCount;
	}
	
	public void generateComponents() {
		for(StructureComponent component : structure.components.values()) {
			componentMaxOccurrences.put(component.name, component.minOccurrences == component.maxOccurrences ? component.minOccurrences : component.minOccurrences + random.nextInt(component.maxOccurrences - component.minOccurrences));
			componentOccurrences.put(component.name, 0);
			TCLog.info("            " + componentMaxOccurrences.get(component.name) + " x " + component.name);
		}
		
		StructureComponent startComponent = structure.components.get(structure.startComponent);
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(0, 0, 0, startComponent.sizeX, startComponent.sizeY, startComponent.sizeZ);
		boundingBoxes.add(box);
		StructureComponentInstance instance = new StructureComponentInstance(structure.name, startComponent.name);
		instance.boundingBox = box.copy().addCoord(this.x, this.y, this.z);
		instance.setPosition(this.x, this.y, this.z);
		componentOccurrences.put(instance.name, componentOccurrences.get(instance.name) + 1);
		structureInstance.components.add(instance);
		TCLog.info("[Structures] Added component " + instance.name + " at " + TCUtil.coordsToString(0, 0, 0));
		generateComponent(startComponent, 0, 0, 0, null);
	}
	
	public void generateComponent(StructureComponent component, int x, int y, int z, StructureComponentInstance previousComponent) {
		TCLog.info("[Structures] Generating component " + component.name + " at " + TCUtil.coordsToString(x, y, z));
		for(int i = 0; i < ForgeDirection.VALID_DIRECTIONS.length; i++) {
			ForgeDirection direction = ForgeDirection.VALID_DIRECTIONS[i];
			if(component.getAdjacentComponents(direction).size() > 0) {
				ArrayList<StructureAdjacentComponent> choosableComponents = new ArrayList<StructureAdjacentComponent>();
				choosableComponents.addAll(component.getAdjacentComponents(direction));
				boolean proceed = false;
				while(!proceed) {
					if(choosableComponents.size() > 0) {
						StructureAdjacentComponent adjacentComponent = choosableComponents.get(random.nextInt(choosableComponents.size()));
						StructureComponent adjacentComponentBase = structure.components.get(adjacentComponent.name);
						if(componentOccurrences.get(adjacentComponentBase.name) >= componentMaxOccurrences.get(adjacentComponentBase.name)) {
							TCLog.info("[Structures] Can't place component " + adjacentComponentBase.name + " too many occurrences");
							choosableComponents.remove(adjacentComponent);
						} else if(random.nextFloat() <= adjacentComponent.chance) {
							int xx = x + (direction.offsetX > 0 ? component.sizeX : 0) + adjacentComponent.offsetX + (direction.offsetX < 0 ? -adjacentComponentBase.sizeX : 0);
							int yy = y + (direction.offsetY > 0 ? component.sizeY : 0) + adjacentComponent.offsetY + (direction.offsetY < 0 ? -adjacentComponentBase.sizeY : 0);
							int zz = z + (direction.offsetZ > 0 ? component.sizeZ : 0) + adjacentComponent.offsetZ + (direction.offsetZ < 0 ? -adjacentComponentBase.sizeZ : 0);
							AxisAlignedBB box = AxisAlignedBB.getBoundingBox(xx, yy, zz, xx - (xx < 0 ? 1 : 0) + adjacentComponentBase.sizeX, yy - (yy < 0 ? 1 : 0) + adjacentComponentBase.sizeY, zz - (zz < 0 ? 1 : 0) + adjacentComponentBase.sizeZ);
							if(canGenerate(box)) {
								boundingBoxes.add(box);
								StructureComponentInstance instance = new StructureComponentInstance(structure.name, adjacentComponentBase.name);
								instance.boundingBox = box.copy().addCoord(this.x, this.y, this.z);
								instance.setPosition(this.x + xx, this.y + yy, this.z + zz);
								instance.neighbors[ForgeDirection.OPPOSITES[direction.ordinal()]] = previousComponent;
								componentOccurrences.put(instance.name, componentOccurrences.get(instance.name) + 1);
								structureInstance.components.add(instance);
								TCLog.info("[Structures] Added component " + instance.name + " at " + TCUtil.coordsToString(xx, yy, zz));
								generateComponent(adjacentComponentBase, xx, yy, zz, instance);
								proceed = true;
							} else {
								TCLog.info("[Structures] Can't place component " + adjacentComponentBase.name + " at " + TCUtil.coordsToString(xx, yy, zz));
								choosableComponents.remove(adjacentComponent);
							}
						}
					} else {
						proceed = true;
					}
				}
			}
		}
	}
	
	public void generateInWorld() {
		for(StructureComponentInstance component : structureInstance.components) {
			structure.components.get(component.name).generate(world, component.x, component.y, component.z);
		}
	}
	
	public boolean canGenerate(AxisAlignedBB box) {
		if(box.minX >= boundingBox.minX && box.minY >= boundingBox.minY && box.minZ >= boundingBox.minZ && box.maxX <= boundingBox.maxX && box.maxY <= boundingBox.maxY && box.maxZ <= boundingBox.maxZ) {
			for(AxisAlignedBB b : boundingBoxes) {
				if(b.intersectsWith(box)) {
					TCLog.info("[Structures] " + box + " intersects with " + b);
					return false;
				}
			}
			return true;
		}
		TCLog.info("[Structures] " + box + " is outside " + boundingBox);
		return false;
	}
}
