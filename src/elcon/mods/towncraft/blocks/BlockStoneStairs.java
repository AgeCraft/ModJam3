package elcon.mods.towncraft.blocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TCConfig;
import elcon.mods.towncraft.TownCraft;

public class BlockStoneStairs extends BlockExtendedMetadataOverlay {

	private static final int[][] rayTraceParts = new int[][] {{2, 6}, {3, 7}, {2, 3}, {6, 7}, {0, 4}, {1, 5}, {0, 1}, {4, 5}};
	
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

	public void setLowerBoundingBox(IBlockAccess blockAccess, int x, int y, int z) {
		if((getMetadata(blockAccess, x, y, z) & 4) != 0) {
			setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
		} else {
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
		}
	}

	public boolean isBlockStairsID(int id) {
		return id > 0 && Block.blocksList[id] instanceof BlockStoneStairs;
	}

	private boolean isBlockStairsDirection(IBlockAccess blockAccess, int x, int y, int z, int meta) {
		return isBlockStairsID(blockAccess.getBlockId(x, y, z)) && (getMetadata(blockAccess, x, y, z) & 7) == (meta & 7);
	}

	public boolean setBoundingBox1(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = getMetadata(blockAccess, x, y, z) & 7;
		int direction = meta & 3;
		float minX = 0.0F;
		float minY = 0.5F;
		float minZ = 0.0F;
		float maxX = 1.0F;
		float maxY = 1.0F;
		float maxZ = 0.5F;
		if((meta & 4) != 0) {
			minY = 0.0F;
			maxY = 0.5F;
		}
		boolean flag = true;
		int otherMeta;
		int otherDirection;
		if(direction == 0) {
			minX = 0.5F;
			maxZ = 1.0F;
			if(isBlockStairsID(blockAccess.getBlockId(x + 1, y, z))) {
				otherMeta = getMetadata(blockAccess, x + 1, y, z) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					otherDirection = otherMeta & 3;
					if(otherDirection == 3 && !isBlockStairsDirection(blockAccess, x, y, z + 1, meta)) {
						maxZ = 0.5F;
						flag = false;
					} else if(otherDirection == 2 && !isBlockStairsDirection(blockAccess, x, y, z - 1, meta)) {
						minZ = 0.5F;
						flag = false;
					}
				}
			}
		} else if(direction == 1) {
			maxX = 0.5F;
			maxZ = 1.0F;
			if(isBlockStairsID(blockAccess.getBlockId(x - 1, y, z))) {
				otherMeta = getMetadata(blockAccess, x - 1, y, z) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					otherDirection = otherMeta & 3;
					if(otherDirection == 3 && !isBlockStairsDirection(blockAccess, x, y, z + 1, meta)) {
						maxZ = 0.5F;
						flag = false;
					} else if(otherDirection == 2 && !isBlockStairsDirection(blockAccess, x, y, z - 1, meta)) {
						minZ = 0.5F;
						flag = false;
					}
				}
			}
		} else if(direction == 2) {
			minZ = 0.5F;
			maxZ = 1.0F;
			if(isBlockStairsID(blockAccess.getBlockId(x, y, z + 1))) {
				otherMeta = getMetadata(blockAccess, x, y, z + 1) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					otherDirection = otherMeta & 3;
					if(otherDirection == 1 && !isBlockStairsDirection(blockAccess, x + 1, y, z, meta)) {
						maxX = 0.5F;
						flag = false;
					} else if(otherDirection == 0 && !isBlockStairsDirection(blockAccess, x - 1, y, z, meta)) {
						minX = 0.5F;
						flag = false;
					}
				}
			}
		} else if(direction == 3) {
			if(isBlockStairsID(blockAccess.getBlockId(x, y, z - 1))) {
				otherMeta = getMetadata(blockAccess, x, y, z - 1) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					otherDirection = otherMeta & 3;
					if(otherDirection == 1 && !isBlockStairsDirection(blockAccess, x + 1, y, z, meta)) {
						maxX = 0.5F;
						flag = false;
					} else if(otherDirection == 0 && !isBlockStairsDirection(blockAccess, x - 1, y, z, meta)) {
						minX = 0.5F;
						flag = false;
					}
				}
			}
		}
		setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		return flag;
	}

	public boolean setBoundingBox2(IBlockAccess blockAccess, int x, int y, int z) {
		int meta = blockAccess.getBlockMetadata(x, y, z) & 7;
		int direction = meta & 3;
		float minX = 0.0F;
		float minY = 0.5F;
		float minZ = 0.5F;
		float maxX = 0.5F;
		float maxY = 1.0F;
		float maxZ = 1.0F;
		if((meta & 4) != 0) {
			minY = 0.0F;
			maxY = 0.5F;
		}
		boolean flag = false;
		int otherMeta;
		int otherDirection;
		if(direction == 0) {
			if(isBlockStairsID(blockAccess.getBlockId(x - 1, y, z))) {
				otherMeta = getMetadata(blockAccess, x - 1, y, z) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					otherDirection = otherMeta & 3;
					if(otherDirection == 3 && !isBlockStairsDirection(blockAccess, x, y, z - 1, meta)) {
						minZ = 0.0F;
						maxZ = 0.5F;
						flag = true;
					} else if(otherDirection == 2 && !isBlockStairsDirection(blockAccess, x, y, z + 1, meta)) {
						minZ = 0.5F;
						maxZ = 1.0F;
						flag = true;
					}
				}
			}
		} else if(direction == 1) {
			if(isBlockStairsID(blockAccess.getBlockId(x + 1, y, z))) {
				otherMeta = getMetadata(blockAccess, x + 1, y, z) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					minX = 0.5F;
					maxX = 1.0F;
					otherDirection = otherMeta & 3;
					if(otherDirection == 3 && !isBlockStairsDirection(blockAccess, x, y, z - 1, meta)) {
						minZ = 0.0F;
						maxZ = 0.5F;
						flag = true;
					} else if(otherDirection == 2 && !isBlockStairsDirection(blockAccess, x, y, z + 1, meta)) {
						minZ = 0.5F;
						maxZ = 1.0F;
						flag = true;
					}
				}
			}
		} else if(direction == 2) {
			if(isBlockStairsID(blockAccess.getBlockId(x, y, z - 1))) {
				otherMeta = getMetadata(blockAccess, x, y, z - 1) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					minZ = 0.0F;
					maxZ = 0.5F;
					otherDirection = otherMeta & 3;
					if(otherDirection == 1 && !isBlockStairsDirection(blockAccess, x - 1, y, z, meta)) {
						flag = true;
					} else if(otherDirection == 0 && !isBlockStairsDirection(blockAccess, x + 1, y, z, meta)) {
						minX = 0.5F;
						maxX = 1.0F;
						flag = true;
					}
				}
			}
		} else if(direction == 3) {
			if(isBlockStairsID(blockAccess.getBlockId(x, y, z + 1))) {
				otherMeta = getMetadata(blockAccess, x, y, z + 1) & 7;
				if((meta & 4) == (otherMeta & 4)) {
					otherDirection = otherMeta & 3;
					if(otherDirection == 1 && !isBlockStairsDirection(blockAccess, x - 1, y, z, meta)) {
						flag = true;
					} else if(otherDirection == 0 && !isBlockStairsDirection(blockAccess, x + 1, y, z, meta)) {
						minX = 0.5F;
						maxX = 1.0F;
						flag = true;
					}
				}
			}
		}
		if(flag) {
			setBlockBounds(minX, minY, minZ, maxX, maxY, maxZ);
		}
		return flag;
	}

	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setLowerBoundingBox(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		boolean flag = setBoundingBox1(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		if(flag && setBoundingBox2(world, x, y, z)) {
			super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
		}
		setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 vec1, Vec3 vec2) {
		MovingObjectPosition[] mopArray = new MovingObjectPosition[8];
		int meta = getMetadata(world, x, y, z) & 7;
		int direction = meta & 3;
		boolean isUpsideDown = (meta & 4) != 0;
		int[] currentRayTraceParts = rayTraceParts[direction + (isUpsideDown ? 4 : 0)];
		doRayTrace = true;
		int a;
		int b;
		int c;
		for(int i = 0; i < 8; ++i) {
			rayTracePart = i;
			int[] aint1 = currentRayTraceParts;
			a = currentRayTraceParts.length;
			for(b = 0; b < a; ++b) {
				c = aint1[b];
				if(c == i) {
					;
				}
			}
			mopArray[i] = super.collisionRayTrace(world, x, y, z, vec1, vec2);
		}
		int[] currentRayTraceParts2 = currentRayTraceParts;
		int d = currentRayTraceParts.length;
		for(a = 0; a < d; ++a) {
			b = currentRayTraceParts2[a];
			mopArray[b] = null;
		}
		MovingObjectPosition mop1 = null;
		double dist1 = 0.0D;
		MovingObjectPosition[] mopArray2 = mopArray;
		c = mopArray.length;
		for(int i = 0; i < c; ++i) {
			MovingObjectPosition mop2 = mopArray2[i];
			if(mop2 != null) {
				double dist2 = mop2.hitVec.squareDistanceTo(vec2);
				if(dist2 > dist1) {
					mop1 = mop2;
					dist1 = dist2;
				}
			}
		}
		doRayTrace = false;
		return mop1;
	}

	@Override
	public int getDroppedMetadata(World world, int x, int y, int z, int meta, int fortune) {
		return meta & 1016;
	}

	@Override
	public int getPlacedMetadata(EntityPlayer player, ItemStack stack, World world, int x, int y, int z, int side, float xx, float yy, float zz) {
		int rotation = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
		int isUpsideDown = side == 1 ? 0 : (side == 0 ? 4 : (yy > 0.5F ? 4 : 0));
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
		int direction = meta & 3;
		boolean isUpsideDown = (meta & 4) != 0;
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
		/*
		 * if(direction == 0 || direction == 1) { return side == 0 || side == 1 ? iconTop : iconSide; } else if(direction == 2 || direction == 3) { return side == 2 || side == 3 ? iconTop : iconSide;
		 * } else if(direction == 4 || direction == 5) { return side == 4 || side == 5 ? iconTop : iconSide; }
		 */
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
