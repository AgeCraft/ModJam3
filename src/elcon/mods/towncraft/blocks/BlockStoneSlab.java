package elcon.mods.towncraft.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TCConfig;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneSlab extends BlockExtendedMetadataOverlay implements IBlockRotated {

	public int stoneType;
	
	private Icon stoneBrickSmoothSide;

	public BlockStoneSlab(int id, int stoneType) {
		super(id, Material.rock);
		this.stoneType = stoneType;
		setHardness(stoneType == 2 || stoneType == 3 ? 2.0F : 1.5F);
		setResistance(10.0F);
		setLightOpacity(255);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}
	
	@Override
	public String getLocalizedName(ItemStack stack) {
		return StatCollector.translateToLocal(getUnlocalizedName(stack)) + " " + StatCollector.translateToLocal("tile.TC_stoneSlab.name");
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
			return "tile.TC_stoneBrick." + BlockStoneBrick.types[(stack.getItemDamage() & 1792) / 256] + ".name";
		case 4:
			return TownCraft.stoneBrickPillar.getUnlocalizedName() + ".name";
		}
	}
	
	@Override
	public int getBlockRotation(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		int position = meta & 7;
		boolean isFull = (meta & 8) != 0;
		if(position == 2 || position == 3) {
			return 1;
		} else if(position == 4 || position == 5) {
			return 2;
		}
		return 0;
	}
	
	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta & 2032;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
		return ((meta & 8) / 8) == 1 ? 2 : 1;
	}
	
	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		return stack.getItemDamage() | side;
	}
	
	@Override
	public void setBlockBoundsForItemRender() {
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z);
		int position = meta & 7;
		boolean isFull = (meta & 8) != 0;
		if(isFull) {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			return;
		}
		switch(position) {
		case 0:
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			return;
		case 1:
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			return;
		case 2:
			setBlockBounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F);
			return;
		case 3:
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F);
			return;
		case 4:
			setBlockBounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			return;
		case 5:
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F);
			return;
		}
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
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
		return TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID;
	}
	
	@Override
	public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return (getMetadata(blockAccess, x, y, z) & 3) == 2;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		return (getMetadata(world, x, y, z) & 8) != 0 || (getMetadata(world, x, y, z) & 7) == ForgeDirection.OPPOSITES[side.ordinal()];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderColor(int meta) {
		return BlockStone.colors[(meta & 240) / 16];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		return BlockStone.colors[(getMetadata(blockAccess, x, y, z) & 240) / 16];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int position = meta & 7;
		boolean isFull = (meta & 8) != 0;
		int color = (meta & 240) / 16;
		int pattern = (meta & 1792) / 256;
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
		if(position == 0 || position == 1) {
			return side == 0 || side == 1 ? iconTop : iconSide;
		} else if(position == 2 || position == 3) {
			return side == 2 || side == 3 ? iconTop : iconSide;
		} else if(position == 4 || position == 5) {
			return side == 4 || side == 5 ? iconTop : iconSide;
		}
		return iconSide;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		int pattern = (meta & 1792) / 256;
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
					list.add(new ItemStack(id, 1, i * 16 + j * 256));
				}
			} else {
				list.add(new ItemStack(id, 1, i * 16));
			}
		}
	}
}
