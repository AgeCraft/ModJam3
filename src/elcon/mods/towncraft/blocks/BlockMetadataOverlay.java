package elcon.mods.towncraft.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class BlockMetadataOverlay extends BlockOverlay {

	public BlockMetadataOverlay(int i, Material material) {
		super(i, material);
	}
	
	public String getLocalizedName(ItemStack stack) {
		return super.getLocalizedName();
	}

	public String getUnlocalizedName(ItemStack stack) {
		return super.getUnlocalizedName();
	}
}
