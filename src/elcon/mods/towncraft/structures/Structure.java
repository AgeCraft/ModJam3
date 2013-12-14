package elcon.mods.towncraft.structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.biome.BiomeGenBase;

public class Structure {

	public Random random = new Random();
	
	public String name;
	
	public int minSizeX;
	public int minSizeY;
	public int minSizeZ;
	public int maxSizeX;
	public int maxSizeY;
	public int maxSizeZ;
	
	public int minComponentCount;
	public int maxComponentCount;
	
	public HashMap<String, StructureComponent> components = new HashMap<String, StructureComponent>();
	public String startComponent;
	
	public ArrayList<BiomeGenBase> spawnableBiomes = new ArrayList<BiomeGenBase>();
	
	public Structure(String name) {
		this.name = name;
	}
	
	public void setMinSize(int sizeX, int sizeY, int sizeZ) {
		minSizeX = sizeX;
		minSizeY = sizeY;
		minSizeZ = sizeZ;
	}
	
	public void setMaxSize(int sizeX, int sizeY, int sizeZ) {
		maxSizeX = sizeX;
		maxSizeY = sizeY;
		maxSizeZ = sizeZ;
	}
	
	public void setMinMaxComponents(int minComponentCount, int maxComponentCount) {
		this.minComponentCount = minComponentCount;
		this.maxComponentCount = maxComponentCount;
	}
	
	public void addComponent(StructureComponent component) {
		components.put(component.name, component);
	}
	
	public void removeComponent(String component) {
		components.remove(component);
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		name = nbt.getString("Name");
		setMinSize(nbt.getInteger("MinSizeX"), nbt.getInteger("MinSizeY"), nbt.getInteger("MinSizeZ"));
		setMaxSize(nbt.getInteger("MaxSizeX"), nbt.getInteger("MaxSizeY"), nbt.getInteger("MaxSizeZ"));
		setMinMaxComponents(nbt.getInteger("MinComponentCount"), nbt.getInteger("MaxComponentCount"));
		components.clear();
		startComponent = nbt.getString("StartComponent");
		
		NBTTagList list = nbt.getTagList("Components");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			StructureComponent component = new StructureComponent(tag.getString("Name"));
			component.readFromNBT(tag);
			addComponent(component);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("MinSizeX", minSizeX);
		nbt.setInteger("MinSizeY", minSizeY);
		nbt.setInteger("MinSizeZ", minSizeZ);
		nbt.setInteger("MaxSizeX", maxSizeX);
		nbt.setInteger("MaxSizeY", maxSizeY);
		nbt.setInteger("MaxSizeZ", maxSizeZ);
		
		nbt.setInteger("MinComponentCount", minComponentCount);
		nbt.setInteger("MaxComponentCount", maxComponentCount);
		
		nbt.setString("StartComponent", startComponent);
		
		NBTTagList list = new NBTTagList();
		for(StructureComponent component : components.values()) {
			NBTTagCompound tag = new NBTTagCompound();
			component.writeToNBT(tag);
			list.appendTag(tag);
		}
		nbt.setTag("Components", list);
	}
}
