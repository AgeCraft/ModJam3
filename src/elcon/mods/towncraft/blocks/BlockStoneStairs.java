package elcon.mods.towncraft.blocks;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import elcon.mods.towncraft.TCConfig;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneStairs extends BlockExtendedMetadataOverlay {

	public int stoneType;
	public boolean doRayTrace;
	public int rayTracePart;

	private Icon stoneBrickSmoothSide;
	
	public BlockStoneStairs(int id, int stoneType) {
		super(id, Material.rock);
		this.stoneType = stoneType;
		setHardness(stoneType == 2 || stoneType == 3 ? 2.0F : 1.5F);
		setResistance(10.0F);
		setLightOpacity(255);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocal(getUnlocalizedName(stack)) + " " + StatCollector.translateToLocal("tile.TC_stoneStairs.name");
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		switch(stoneType) {
		default:
		case 0:
			return TownCraft.stone.getUnlocalizedName() + ".name";
		case 1:
			return TownCraft.stoneCracked.getUnlocalizedName() + ".name";
		case 2:
			return TownCraft.stoneMossy.getUnlocalizedName() + ".name";
		case 3:
			return "tile.TC_stoneBrick." + BlockStoneBrick.types[(stack.getItemDamage() & 896) / 128] + ".name";
		case 4:
			return TownCraft.stoneBrickPillar.getUnlocalizedName() + ".name";
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		if(doRayTrace) {
			setBlockBounds(0.5F * (float) (rayTracePart % 2), 0.5F * (float) (rayTracePart / 2 % 2), 0.5F * (float) (rayTracePart / 4 % 2), 0.5F + 0.5F * (float) (rayTracePart % 2), 0.5F + 0.5F * (float) (rayTracePart / 2 % 2), 0.5F + 0.5F * (float) (rayTracePart / 4 % 2));
		} else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta & 1016;
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		int rotation = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int isUpsideDown = yy >= 0.5D ? 4 : 0;
		switch(rotation) {
		case 0:
			return stack.getItemDamage() | isUpsideDown | 2;
		case 1:
			return stack.getItemDamage() | isUpsideDown | 1;
		case 2:
			return stack.getItemDamage() | isUpsideDown | 3;
		default:
		case 3:
			return stack.getItemDamage() | isUpsideDown;
		}
	}
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		onNeighborBlockChange(world, x, y, z, 0);
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return TCConfig.BLOCK_STAIRS_RENDER_ID;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int direction = meta & 3;
		boolean isUpsideDown = ((meta & 4) / 4) == 1;
		int color = (meta & 120) / 8;
		int pattern = (meta & 896) / 128;
		Icon iconTop;
		Icon iconSide;
		switch(stoneType) {
		default:
		case 0:
			iconTop = iconSide = TownCraft.stone.getIcon(side, color);
			break;
		case 1:
		case 2:
			iconTop = iconSide = TownCraft.stoneCracked.getIcon(side, color);
			break;
		case 3:
			if(pattern == 7) {
				iconSide = stoneBrickSmoothSide;
			} else {
				iconSide = TownCraft.stoneBrick.getIcon(side, pattern + color * 8);
			}
			iconTop = TownCraft.stoneBrick.getIcon(side, pattern + color * 8);
			break;
		case 4:
			iconTop = TownCraft.stoneBrickPillar.getIcon(0, color * 4);
			iconSide = TownCraft.stoneBrickPillar.getIcon(2, color * 4);
			break;
		}
		/*if(direction == 0 || direction == 1) {
			return side == 0 || side == 1 ? iconTop : iconSide;
		} else if(direction == 2 || direction == 3) {
			return side == 2 || side == 3 ? iconTop : iconSide;
		} else if(direction == 4 || direction == 5) {
			return side == 4 || side == 5 ? iconTop : iconSide;
		}*/
		if(side == 0 || side == 1) {
			return iconTop;
		}
		return iconSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		int pattern = (meta & 896) / 128;
		switch(stoneType) {
		default:
		case 0:
		case 1:
		case 4:
			return null;
		case 2:
			return ((BlockOverlay) TownCraft.stoneMossy).getBlockOverlayTexture(side, 0);
		case 3:
			return ((BlockOverlay) TownCraft.stoneBrick).getBlockOverlayTexture(side, pattern);
		}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		stoneBrickSmoothSide = iconRegister.registerIcon("towncraft:stoneBrickSmoothSlab");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			if(stoneType == 3) {
				for(int j = 0; j < 8; j++) {
					list.add(new ItemStack(id, 1, i * 8 + j * 128));
				}
			} else {
				list.add(new ItemStack(id, 1, i * 8));
			}
		}
	}
}
