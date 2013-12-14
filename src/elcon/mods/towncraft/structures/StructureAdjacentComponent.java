package elcon.mods.towncraft.structures;

import net.minecraft.nbt.NBTTagCompound;

public class StructureAdjacentComponent {

	public StructureComponent component;
	public int offsetX;
	public int offsetY;
	public int offsetZ;
	public float chance;
	
	public StructureAdjacentComponent(StructureComponent component, int offsetX, int offsetY, int offsetZ, float chance) {
		this.component = component;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.chance = chance;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", component.name);
		nbt.setInteger("OffsetX", offsetX);
		nbt.setInteger("OffsetY", offsetY);
		nbt.setInteger("OffsetZ", offsetY);
		nbt.setFloat("Chance", chance);
	}
}
