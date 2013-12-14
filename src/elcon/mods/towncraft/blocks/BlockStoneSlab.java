package elcon.mods.towncraft.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneSlab extends BlockExtendedMetadataOverlay {

	public int stoneType;

	public BlockStoneSlab(int id, int stoneType) {
		super(id, Material.rock);
		this.stoneType = stoneType;
		setHardness(stoneType == 2 || stoneType == 3 ? 2.0F : 1.5F);
		setResistance(10.0F);
		setStepSound(Block.soundStoneFootstep);
		setCreativeTab(TownCraft.creativeTab);
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		int position = blockAccess.getBlockMetadata(x, y, z) & 3;
		switch(position) {
		case 0:
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
			return;
		case 1:
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
			return;
		case 2:
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
	public boolean isBlockSolid(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return (blockAccess.getBlockMetadata(x, y, z) & 3) == 2;
	}
	
	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side) {
		int position = world.getBlockMetadata(x, y, z) & 3;
		return position == 2 || (position == 0 && side == ForgeDirection.DOWN) || (position == 1 && side == ForgeDirection.UP);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
		System.out.println(blockAccess.getBlockMetadata(x, y, z) + " --> " + ((blockAccess.getBlockMetadata(x, y, z) & 239) / 8));
		return BlockStone.colors[(blockAccess.getBlockMetadata(x, y, z) & 239) / 8];
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIcon(int side, int meta) {
		int type = (meta & 11) / 4;
		int color = (meta & 239) / 8;
		int pattern = (meta & 65279) / 256;
		switch(type) {
		default:
		case 0:
		case 1:
		case 2:
			return TownCraft.stone.getIcon(side, color);
		case 3:
			return TownCraft.stoneBrick.getIcon(side, pattern + color * 8);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int meta) {
		int type = meta & 11;
		int pattern = meta & 65279;
		switch(type) {
		default:
		case 0:
			return null;
		case 1:
			return ((BlockOverlay) TownCraft.stoneCracked).getBlockOverlayTexture(side, 0);
		case 2:
			return ((BlockOverlay) TownCraft.stoneMossy).getBlockOverlayTexture(side, 0);
		case 3:
			return ((BlockOverlay) TownCraft.stoneCracked).getBlockOverlayTexture(side, type);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int id, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < 16; i++) {
			if(stoneType == 3) {
				for(int j = 0; j < 8; j++) {
					list.add(new ItemStack(id, 1, stoneType * 4 + i * 8 + j * 256));
				}
			} else {
				list.add(new ItemStack(id, 1, stoneType * 4 + i * 8));
			}
		}
	}
}
