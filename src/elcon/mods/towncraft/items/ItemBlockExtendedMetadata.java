package elcon.mods.towncraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.blocks.IBlockExtendedMetadata;
import elcon.mods.towncraft.tileentities.TileEntityMetadata;

public class ItemBlockExtendedMetadata extends ItemBlock {

	public ItemBlockExtendedMetadata(int id) {
		super(id);
		setMaxDamage(0);
		setHasSubtypes(true);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((IBlockExtendedMetadata) Block.blocksList[getBlockID()]).getLocalizedName(stack);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((IBlockExtendedMetadata) Block.blocksList[getBlockID()]).getUnlocalizedName(stack);
	}
	
	@Override
	public String getUnlocalizedName() {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack stack, int renderPass) {
		return Block.blocksList[getBlockID()].getRenderColor(stack.getItemDamage());
	}
	
	@Override
	public int getMetadata(int meta) {
		return 0;
	}
	
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		Block block = Block.blocksList[getBlockID()];
		if(!(block instanceof IBlockExtendedMetadata)) {
			return false;
		}
		int placedMeta = ((IBlockExtendedMetadata) block).getPlacedMetadata(player, stack, world, x, y, z, side, hitX, hitY, hitZ);
		if(!world.setBlock(x, y, z, getBlockID(), meta, 2)) {
			return false;
		}
		if(world.getBlockId(x, y, z) == getBlockID()) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null) {
				if(!(tile instanceof TileEntityMetadata)) {
					tile = new TileEntityMetadata();
					world.setBlockTileEntity(x, y, z, tile);
				}
				((TileEntityMetadata) tile).setTileMetadata(placedMeta);
			}
			block.onBlockPlacedBy(world, x, y, z, player, stack);
			block.onPostBlockPlaced(world, x, y, z, meta);
		}
		return true;
	}
	
	public int getMetadata(IBlockAccess blockAccess, int x, int y, int z) {
		TileEntity t = blockAccess.getBlockTileEntity(x, y, z);
		if(t != null && t instanceof TileEntityMetadata) {
			return ((TileEntityMetadata) t).getTileMetadata();
		}
		return 0;
	}
	
	public void setMetadata(World world, int x, int y, int z, int meta) {
		TileEntity t = world.getBlockTileEntity(x, y, z);
		if(t != null && t instanceof TileEntityMetadata) {
			((TileEntityMetadata) t).setTileMetadata(meta);
		}
	}
}
