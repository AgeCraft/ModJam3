package elcon.mods.towncraft.structures;

import java.util.LinkedList;

import net.minecraft.nbt.NBTTagCompound;
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
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("x", x);
		nbt.setInteger("y", y);
		nbt.setInteger("z", z);
	}
}
