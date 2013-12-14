package elcon.mods.towncraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import elcon.mods.towncraft.blocks.BlockMetadataOverlay;

public class ItemBlockMetadataOverlay extends ItemBlockWithMetadata {

	public ItemBlockMetadataOverlay(int id, Block block) {
		super(id, block);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return ((BlockMetadataOverlay) Block.blocksList[getBlockID()]).getLocalizedName(stack);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return ((BlockMetadataOverlay) Block.blocksList[getBlockID()]).getUnlocalizedName(stack);
	}
	
	@Override
	public String getUnlocalizedName() {
		return ((BlockMetadataOverlay) Block.blocksList[getBlockID()]).getUnlocalizedName(new ItemStack(getBlockID(), 1, 0));
	}
}
