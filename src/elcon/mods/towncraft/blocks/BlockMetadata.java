package elcon.mods.towncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockMetadata extends Block {

	public BlockMetadata(int id, Material material) {
		super(id, material);
	}
	
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	public String getLocalizedName(ItemStack stack) {
		return getLocalizedName();
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}
}
