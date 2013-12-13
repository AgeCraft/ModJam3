package elcon.mods.towncraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class TCCreativeTab extends CreativeTabs {

	public TCCreativeTab(int id, String name) {
		super(id, name);
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(TownCraft.stone.blockID, 1, 0);
	}
}
