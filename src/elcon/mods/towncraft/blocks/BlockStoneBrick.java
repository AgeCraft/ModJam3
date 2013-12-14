package elcon.mods.towncraft.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TCUtil;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneBrick extends BlockExtendedMetadataOverlay {

	public static final String[] types = new String[]{"normal", "cracked", "mossy", "small", "circle", "creeper", "chiseled", "smooth"};
	
	private Icon[] icons = new Icon[8];
	private Icon chiseledTop;
	private Icon overlayMossy;
	
	public BlockStoneBrick(int id) {
		super(id, Material.rock);
		setHardness(1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocal(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tile.TC_stoneBrick." + types[stack.getItemDamage() & 7] + ".name";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return BlockStone.colors[(meta & 120) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockStone.colors[(getMetadata(blockAccess, x, y, z) & 120) / 8];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		return (meta & 7) == 6 && (side == 0 || side == 1) ? chiseledTop : icons[meta & 7];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		if((meta & 7) == 2) {
			return overlayMossy;
		}
		return null;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 16; j++) {
				icons[i] = iconRegister.registerIcon("towncraft:stoneBrick" + TCUtil.firstUpperCase(types[i]));
			}
		}
		chiseledTop = iconRegister.registerIcon("towncraft:stoneBrickChiseledTop");
		overlayMossy = iconRegister.registerIcon("towncraft:stoneBrickMossyOverlay");
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
