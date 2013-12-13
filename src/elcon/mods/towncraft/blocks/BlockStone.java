package elcon.mods.towncraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TownCraft;

public class BlockStone extends BlockMetadata {

	private Icon[] icons = new Icon[16];
	
	public BlockStone(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
	}
	
	@Override
	public int idDropped(int meta, Random random, int fortune) {
		return TownCraft.stoneCracked.blockID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icons[meta];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < icons.length; i++) {
			icons[i] = iconRegister.registerIcon("towncraft:stone_" + Integer.toString(i));
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}
}
