package elcon.mods.towncraft.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.TCConfig;

public class BlockOverlay extends Block {

	public BlockOverlay(int i, Material material) {
		super(i, material);
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return TCConfig.BLOCK_OVERLAY_RENDER_ID;
	}
	
	@SideOnly(Side.CLIENT)
	public boolean shouldOverlaySideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return true;
	}

	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(int side, int metadata) {
		return null;
	}
	
	@SideOnly(Side.CLIENT)
	public Icon getBlockOverlayTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getBlockOverlayTexture(side, blockAccess.getBlockMetadata(x, y, z));
	}
	
	@SideOnly(Side.CLIENT)
	public int getBlockOverlayColor(int side, int metadata) {
		return 0xFFFFFF;
	}
	
	@SideOnly(Side.CLIENT)
	public int getBlockOverlayColor(IBlockAccess blockAccess, int x, int y, int z, int side) {
		return getBlockOverlayColor(side, blockAccess.getBlockMetadata(x, y, z));
	}
}
