package elcon.mods.towncraft.structures;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class StructureComponentInstance {

	public String name;
	public int x;
	public int y;
	public int z;
	public AxisAlignedBB boundingBox;
	
	public StructureComponentInstance[] neighbors = new StructureComponentInstance[ForgeDirection.VALID_DIRECTIONS.length];
	
	public StructureComponentInstance(String name) {
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
