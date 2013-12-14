package elcon.mods.towncraft.structures;

import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

public class StructureInstance {

	public String name;
	public int x;
	public int y;
	public int z;
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	
	public int componentCount;
	public HashMap<String, Integer> componentOccurrences = new HashMap<String, Integer>();
	
	public AxisAlignedBB boundingBox;
	
	public LinkedList<StructureComponentInstance> components = new LinkedList<StructureComponentInstance>();
	
	public StructureInstance(String name) {
		this.name = name;
	}
	
	public void setPosition(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setSize(int sizeX, int sizeY, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		name = nbt.getString("Name");
		x = nbt.getInteger("X");
		y = nbt.getInteger("Y");
		z = nbt.getInteger("Z");
		sizeX = nbt.getInteger("SizeX");
		sizeY = nbt.getInteger("SizeY");
		sizeZ = nbt.getInteger("SizeZ");
		
		componentCount = nbt.getInteger("ComponentCount");
		componentOccurrences.clear();
		NBTTagList list = nbt.getTagList("");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagInt tag = (NBTTagInt) list.tagAt(i);
			componentOccurrences.put(tag.getName(), tag.data);
		}
		
		components.clear();
		list = nbt.getTagList("Components");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			StructureComponentInstance component = new StructureComponentInstance(tag.getString("Name"));
			component.readFromNBT(tag);
			components.add(component);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("X", x);
		nbt.setInteger("Y", y);
		nbt.setInteger("Z", z);
		nbt.setInteger("SizeX", sizeX);
		nbt.setInteger("SizeY", sizeY);
		nbt.setInteger("SizeZ", sizeZ);
		
		nbt.setInteger("ComponentCount", componentCount);
		NBTTagList list = new NBTTagList();
		for(String component : componentOccurrences.keySet()) {
			list.appendTag(new NBTTagInt(component, componentOccurrences.get(component)));
		}
		nbt.setTag("ComponentOccurrences", list);
		
		list = new NBTTagList();
		for(StructureComponentInstance component : components) {
			NBTTagCompound tag = new NBTTagCompound();
			component.writeToNBT(tag);
			list.appendTag(tag);
		}
		nbt.setTag("Components", list);
	}
}
