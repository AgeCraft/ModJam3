package elcon.mods.towncraft.structures;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import elcon.mods.towncraft.TCUtil;

public class StructureComponent {

	public String name;
	public int sizeX;
	public int sizeY;
	public int sizeZ;
	public int[] blockIDs;
	public byte[] blockMetadata;
	public TileEntity[] blockTileEntities;
	
	public int minOccurrences;
	public int maxOccurrences;
	
	public LinkedList<StructureAdjacentComponent>[] adjacentComponents = new LinkedList[ForgeDirection.VALID_DIRECTIONS.length];
	
	public StructureComponent(String name) {
		this.name = name;
		for(int i = 0; i < adjacentComponents.length; i++) {
			adjacentComponents[i] = new LinkedList<StructureAdjacentComponent>();
		}
	}
	
	public int[] getSize() {
		return new int[]{sizeX, sizeY, sizeZ};
	}
	
	public void setSize(int sizeX, int sizeY, int sizeZ) {
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
		blockIDs = new int[sizeX * sizeY * sizeZ];
		blockMetadata = new byte[sizeX * sizeY * sizeZ];
		blockTileEntities = new TileEntity[sizeX * sizeY * sizeZ];
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
	
	public void setMinMaxOccurrences(int minOccurrences, int maxOccurrences) {
		this.minOccurrences = minOccurrences;
		this.maxOccurrences = maxOccurrences;
	}

	public List<StructureAdjacentComponent> getAdjacentComponents(ForgeDirection direction) {
		return adjacentComponents[direction.ordinal()];
	}
	
	public void addAdjacentComponent(ForgeDirection direction, StructureAdjacentComponent component) {
		adjacentComponents[direction.ordinal()].add(component);
	}
	
	public void removeAdjacentComponent(ForgeDirection direction, StructureAdjacentComponent component) {
		adjacentComponents[direction.ordinal()].remove(component);
	}
	
	public void generate(World world, int x, int y, int z) {
		for(int i = 0; i < sizeX; i++) {
			for(int j = 0; j < sizeY; j++) {
				for(int k = 0; k < sizeZ; k++) {
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
		
		minOccurrences = nbt.getInteger("MinOccurrences");
		maxOccurrences = nbt.getInteger("MaxOccurrences");
		
		for(int i = 0; i < adjacentComponents.length; i++) {
			adjacentComponents[i].clear();
			if(nbt.hasKey("AdjacentComponents" + Integer.toString(i))) {
				NBTTagList componentList = nbt.getTagList("AdjacentComponents" + Integer.toString(i));
				for(int j = 0; j < componentList.tagCount(); j++) {
					NBTTagCompound tag = (NBTTagCompound) componentList.tagAt(j);
					StructureAdjacentComponent component = new StructureAdjacentComponent(tag.getString("Name"));
					component.readFromNBT(tag);
					adjacentComponents[i].add(component);
				}
			}
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
		
		nbt.setInteger("MinOccurrences", minOccurrences);
		nbt.setInteger("MaxOccurrences", maxOccurrences);
		
		for(int i = 0; i < adjacentComponents.length; i++) {
			if(adjacentComponents[i].size() > 0) {
				NBTTagList componentList = new NBTTagList();
				for(StructureAdjacentComponent component : adjacentComponents[i]) {
					NBTTagCompound tag = new NBTTagCompound();
					component.writeToNBT(tag);
					componentList.appendTag(tag);
				}
				nbt.setTag("AdjacentComponents" + Integer.toString(i), componentList);
			}
		}
	}
}
