package elcon.mods.towncraft.structures;

import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.biome.BiomeGenBase;

public class Structure {

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
	
	public LinkedList<BiomeGenBase> spawnableBiomes = new LinkedList<BiomeGenBase>();
	
	public Structure(String name) {
		this.name = name;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
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
		
		NBTTagList list = new NBTTagList();
		for(BiomeGenBase biome : spawnableBiomes) {
			list.appendTag(new NBTString());
		}
		nbt.setTag("SpawnableBiomes", list);
	}
}
