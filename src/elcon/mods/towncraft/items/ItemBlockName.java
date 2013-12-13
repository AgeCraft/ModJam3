package elcon.mods.towncraft.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockName extends ItemBlock {

	public ItemBlockName(int id) {
		super(id);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getLocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
}
