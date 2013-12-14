package elcon.mods.towncraft.structures;

import net.minecraft.nbt.NBTTagCompound;

public class StructureAdjacentComponent {

	public String name;
	public int offsetX;
	public int offsetY;
	public int offsetZ;
	public float chance;
	
	public StructureAdjacentComponent(String name) {
		this.name = name;
	}
	
	public StructureAdjacentComponent(String name, float chance) {
		this(name);
		this.chance = chance;
	}
	
	public StructureAdjacentComponent(String name, int offsetX, int offsetY, int offsetZ) {
		this(name);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
	
	public StructureAdjacentComponent(String name, int offsetX, int offsetY, int offsetZ, float chance) {
		this(name, chance);
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
	
	public int[] getOffsets() {
		return new int[]{offsetX, offsetY, offsetZ};
	}
	
	public void setOffsets(int offsetX, int offsetY, int offsetZ) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
	}
	
	public void setChance(float chance) {
		this.chance = chance;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		name = nbt.getString("Name");
		offsetX = nbt.getInteger("OffsetX");
		offsetY = nbt.getInteger("OffsetY");
		offsetZ = nbt.getInteger("OffsetZ");
		chance = nbt.getFloat("Chance");
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("OffsetX", offsetX);
		nbt.setInteger("OffsetY", offsetY);
		nbt.setInteger("OffsetZ", offsetZ);
		nbt.setFloat("Chance", chance);
	}
}
