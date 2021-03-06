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
		if(!player.isSneaking() && player.canPlayerEdit(x, y, z, side, stack) && Block.blocksList[world.getBlockId(x, y, z)] instanceof BlockStoneSlab && ((BlockStoneSlab) Block.blocksList[world.getBlockId(x, y, z)]).stoneType == ((BlockStoneSlab) Block.blocksList[stack.getItem().itemID]).stoneType && ((getMetadata(world, x, y, z) & 8) / 8) == 0) {
			int meta = getMetadata(world, x, y, z);
			if((meta & 2032) == (stack.getItemDamage() & 2032)) {
				setMetadata(world, x, y, z, meta | 8);
				world.markBlockForUpdate(x, y, z);
				stack.stackSize--;
				return true;
			}
		}
		return super.onItemUse(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
	}
}
