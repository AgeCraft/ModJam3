package elcon.mods.towncraft.structures;

import elcon.mods.towncraft.TCUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class StructureComponent {

	public String name;
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	public int[] blockIDs;
	public byte[] blockMetadata;
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
		blockIDs = new int[sizeX * sizeY * sizeX];
		blockMetadata = new byte[sizeX * sizeY * sizeX];
		blockTileEntities = new TileEntity[sizeX * sizeY * sizeX];
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
		blockMetadata[x + y * sizeX + z * sizeX * sizeY] = (byte) (metadata & 15);
	}
	
	public void setBlockTileEntity(int x, int y, int z, TileEntity tileEntity) {
		blockTileEntities[x + y * sizeX + z * sizeX * sizeY] = tileEntity;
	}
	
	public void generate(World world, int x, int y, int z) {
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeX; j++) {
				for(int k = 0; k < sizeX; k++) {
					world.setBlock(x + i, y + j, z + k, getBlockID(i, j, k), getBlockMetadata(i, j, k), 2);
					if(getBlockTileEntity(i, j, k) != null) {
						world.setBlockTileEntity(x + i, y + j, z + k, TCUtil.copyTileEntity(getBlockTileEntity(i, j, k)));
					}
				}
			}
		}
	}
	
	public void readFromNBT(NBTTagCompound nbt) {
		name = nbt.getString("Name");
		setSize(nbt.getInteger("SizeX"), nbt.getInteger("SizeY"), nbt.getInteger("SizeZ"));
		blockIDs = nbt.getIntArray("BlockIDs");
		blockMetadata = nbt.getByteArray("BlockMetadata");
		NBTTagList list = nbt.getTagList("TileEntities");
		for(int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = (NBTTagCompound) list.tagAt(i);
			TileEntity tile = TileEntity.createAndLoadEntity(tag);
			setBlockTileEntity(tile.xCoord, tile.yCoord, tile.zCoord, tile);
		}
	}
	
	public void writeToNBT(NBTTagCompound nbt) {
		nbt.setString("Name", name);
		nbt.setInteger("SizeX", sizeX);
		nbt.setInteger("SizeY", sizeY);
		nbt.setInteger("SizeZ", sizeZ);
		nbt.setIntArray("BlockIDs", blockIDs);
		nbt.setByteArray("BlockMetadata", blockMetadata);
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeX; j++) {
				for(int k = 0; k < sizeX; k++) {
					TileEntity tile = getBlockTileEntity(i, j, k);
					if(tile != null) {
						NBTTagCompound tag = new NBTTagCompound();
						tile.writeToNBT(tag);
						tag.setInteger("x", i);
						tag.setInteger("y", j);
						tag.setInteger("z", k);
						list.appendTag(tag);
					}
				}
			}
		}
		nbt.setTag("TileEntities", list);
	}
}
