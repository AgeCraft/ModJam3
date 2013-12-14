package elcon.mods.towncraft.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneMossy extends BlockMetadataOverlay {

	private Icon overlay;
	
	public BlockStoneMossy(int id) {
		super(id, Material.rock);
		setHardness(2.0F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return TownCraft.stone.getIcon(side, meta);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int metadata) {
		return overlay;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		overlay = iconRegister.registerIcon("towncraft:stoneMossy");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
}
