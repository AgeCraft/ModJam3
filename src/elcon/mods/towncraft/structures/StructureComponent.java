package elcon.mods.towncraft.structures;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;

public class StructureComponent {

	public String name;
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	public int[] blockIDs;
	public int[] blockMetadata;
	public TileEntity[] blockTileEntities;
	
	public StructureComponent(String name) {
		this.name = name;
	}
	
	public int[] getSize() {
		return new int[]{sizeX, sizeY, sizeZ};
	}
	
	public void setSize(int sizeX, int sizeY, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}
	
	public void setSize(int[] size) {
		setSize(size[0], size[1], size[2]);
	}

	public int getBlockID(int x, int y, int z) {
		return blockIDs[x + y * sizeX + z * sizeX * sizeY];
	}
	
	public int getBlockMetadata(int x, int y, int z) {
		return blockMetadata[x + y * sizeX + z * sizeX * sizeY];
	}
	
	public TileEntity getBlockTileEntity(int x, int y, int z) {
		return blockTileEntities[x + y * sizeX + z * sizeX * sizeY];
	}
	
	public void setBlockID(int x, int y, int z, int id) {
		blockIDs[x + y * sizeX + z * sizeX * sizeY] = id;
	}
	
	public void setBlockMetadata(int x, int y, int z, int metadata) {
		blockMetadata[x + y * sizeX + z * sizeX * sizeY] = metadata;
	}
	
	public void setBlockTileEntity(int x, int y, int z, TileEntity tileEntity) {
		blockTileEntities[x + y * sizeX + z * sizeX * sizeY] = tileEntity;
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("SizeX", sizeX);
		nbt.setInteger("SizeY", sizeY);
		nbt.setInteger("SizeZ", sizeZ);
		nbt.setIntArray("BlockIDs", blockIDs);
		nbt.setIntArray("BlockMetadata", blockMetadata);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeX; j++) {
				for(int k = 0; k < sizeX; k++) {
					TileEntity tile = blockTileEntities[i + j * sizeX + k * sizeX * sizeY];
					if(tile != null) {
						NBTTagCompound tag = new NBTTagCompound();
						tile.writeToNBT(tag);
						tag.setInteger("x", i);
						tag.setInteger("y", j);
						tag.setInteger("z", k);
					}
				}
			}
		}
	}
}
