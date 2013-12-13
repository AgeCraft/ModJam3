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
import elcon.mods.towncraft.TCUtil;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneBrick extends BlockExtendedMetadata {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "pillar"};
	
	private Icon[][] icons = new Icon[8][16];
	
	public BlockStoneBrick(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile.TC_stoneBrick." + types[stack.getItemDamage() & 7] + ".name";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return icons[meta & 7][(meta - (meta & 7)) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				icons[i][j] = iconRegister.registerIcon("towncraft:stoneBrick" + TCUtil.firstUpperCase(types[i]) + "_" + Integer.toString(j));
			}
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				list.add(new ItemStack(id, 1, i + j * 8));
			}
		}
	}
}
