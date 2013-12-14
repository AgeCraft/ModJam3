package elcon.mods.towncraft.structures;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.common.ForgeDirection;

public class StructureComponentInstance {

	public String structureName;
	public String name;
	public int x;
	public int y;
	public int z;
	
	public AxisAlignedBB boundingBox;
	
	public StructureComponentInstance[] neighbors = new StructureComponentInstance[ForgeDirection.VALID_DIRECTIONS.length];
	
	public StructureComponentInstance(String structureName, String name) {
		this.structureName = structureName;
		this.name = name;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		structureName = nbt.getString("StructureName");
		name = nbt.getString("Name");
		x = nbt.getInteger("X");
		y = nbt.getInteger("Y");
		z = nbt.getInteger("Y");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("StructureName", structureName);
		nbt.setString("Name", name);
		nbt.setInteger("X", x);
		nbt.setInteger("Y", y);
		nbt.setInteger("Z", z);
	}
}
