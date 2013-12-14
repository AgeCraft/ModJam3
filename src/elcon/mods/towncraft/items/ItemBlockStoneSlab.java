package elcon.mods.towncraft.items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import elcon.mods.towncraft.blocks.BlockStoneSlab;

public class ItemBlockStoneSlab extends ItemBlockExtendedMetadata {

	public ItemBlockStoneSlab(int id) {
		super(id);
	}
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(Block.blocksList[world.getBlockId(x, y, z)] instanceof BlockStoneSlab && (world.getBlockMetadata(x, y, z) & 8) == 0) {
			world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) | 8, 3);
			stack.stackSize--;
			if(stack.stackSize <= 0) {
				stack = null;
			}
		} else {
			return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
		}
	}
}
