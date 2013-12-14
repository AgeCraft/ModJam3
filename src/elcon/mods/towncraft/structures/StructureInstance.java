package elcon.mods.towncraft.structures;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;

public class StructureInstance {

	public String name;
	public int x;
	public int y;
	public int z;
	public AxisAlignedBB boundingBox;
	
	public LinkedList<StructureComponentInstance> components = new LinkedList<StructureComponentInstance>();
	
	public StructureInstance(String name) {
		this.name = name;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		name = nbt.getString("Name");
		x = nbt.getInteger("x");
		y = nbt.getInteger("y");
		z = nbt.getInteger("z");
		
		components.clear();
		NBTTagList list = nbt.getTagList("Components");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			StructureComponentInstance component = new StructureComponentInstance(tag.getString("Name"));
			component.readFromNBT(tag);
			components.add(component);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
		
		NBTTagList list = new NBTTagList();
		for(StructureComponentInstance component : components) {
			NBTTagCompound tag = new NBTTagCompound();
			component.writeToNBT(tag);
			list.appendTag(tag);
		}
		nbt.setTag("Components", list);
	}
}
