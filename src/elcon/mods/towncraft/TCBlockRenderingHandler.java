package elcon.mods.towncraft;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import elcon.mods.towncraft.blocks.BlockOverlay;
import elcon.mods.towncraft.blocks.BlockStoneStairs;
import elcon.mods.towncraft.blocks.IBlockRotated;

public class TCBlockRenderingHandler implements ISimpleBlockRenderingHandler {

	public static final double OVERLAY_SHIFT = 0.001D;

	public int getRenderId() {
		return TCConfig.BLOCK_OVERLAY_RENDER_ID;
	}

	public boolean renderWorldBlock(IBlockAccess blockAccess, int x, int y, int z, Block block, int modelID, RenderBlocks renderer) {
		if(modelID == TCConfig.BLOCK_OVERLAY_RENDER_ID) {
			boolean flag = renderer.renderStandardBlock(block, x, y, z);
			renderBlockOverlay(blockAccess, (BlockOverlay) block, x, y, z, renderer);
			return flag;
		} else if(modelID == TCConfig.BLOCK_ROTATED_RENDER_ID) {
			renderBlockRotated(blockAccess, (IBlockRotated) block, x, y, z, renderer);
		} else if(modelID == TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID) {
			renderBlockOverlayRotated(blockAccess, block, x, y, z, renderer);
		} else if(modelID == TCConfig.BLOCK_STAIRS_RENDER_ID) {
			renderBlockStairs(blockAccess, (BlockStoneStairs) block, x, y, z, renderer);
		}
		return false;
	}

	public static boolean renderBlockWithOverlay(IBlockAccess blockAcccess, Block block, int x, int y, int z, RenderBlocks renderer) {
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		renderBlockOverlay(blockAcccess, (BlockOverlay) block, x, y, z, renderer);
		return flag;
	}

	private static boolean renderBlockOverlay(IBlockAccess blockAccess, BlockOverlay block, int x, int y, int z, RenderBlocks renderer) {
		int mixedBrightness = block.getMixedBrightnessForBlock(blockAccess, x, y, z);
		int metadata = blockAccess.getBlockMetadata(x, y, z);
		Icon overlay = null;

		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 0);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 0)) {
			int color = block.getBlockOverlayColor(blockAccess, x, y, z, 0);
			float sideR = (color >> 16 & 0xFF) / 255.0F;
			float sideG = (color >> 8 & 0xFF) / 255.0F;
			float sideB = (color & 0xFF) / 255.0F;
			renderBottomFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, sideR, sideG, sideB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 1);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 1)) {
			int color = block.getBlockOverlayColor(blockAccess, x, y, z, 1);
			float sideR = (color >> 16 & 0xFF) / 255.0F;
			float sideG = (color >> 8 & 0xFF) / 255.0F;
			float sideB = (color & 0xFF) / 255.0F;
			renderTopFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, sideR, sideG, sideB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 2);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 2)) {
			int color = block.getBlockOverlayColor(blockAccess, x, y, z, 2);
			float sideR = (color >> 16 & 0xFF) / 255.0F;
			float sideG = (color >> 8 & 0xFF) / 255.0F;
			float sideB = (color & 0xFF) / 255.0F;
			renderEastFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, sideR, sideG, sideB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 3);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 3)) {
			int color = block.getBlockOverlayColor(blockAccess, x, y, z, 3);
			float sideR = (color >> 16 & 0xFF) / 255.0F;
			float sideG = (color >> 8 & 0xFF) / 255.0F;
			float sideB = (color & 0xFF) / 255.0F;
			renderWestFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, sideR, sideG, sideB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 4);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 4)) {
			int color = block.getBlockOverlayColor(blockAccess, x, y, z, 4);
			float sideR = (color >> 16 & 0xFF) / 255.0F;
			float sideG = (color >> 8 & 0xFF) / 255.0F;
			float sideB = (color & 0xFF) / 255.0F;
			renderNorthFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, sideR, sideG, sideB);
		}
		overlay = block.getBlockOverlayTexture(blockAccess, x, y, z, 5);
		if(overlay != null && block.shouldOverlaySideBeRendered(blockAccess, x, y, z, 5)) {
			int color = block.getBlockOverlayColor(blockAccess, x, y, z, 5);
			float sideR = (color >> 16 & 0xFF) / 255.0F;
			float sideG = (color >> 8 & 0xFF) / 255.0F;
			float sideB = (color & 0xFF) / 255.0F;
			renderSouthFace(blockAccess, block, x, y, z, renderer, overlay, mixedBrightness, sideR, sideG, sideB);
		}
		return true;
	}

	protected static int determineMixedBrightness(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, int mixedBrightness) {
		return renderer.renderMinY > 0.0D ? mixedBrightness : block.getMixedBrightnessForBlock(world, x, y, z);
	}

	protected static void renderBottomFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y - 1, z, 0))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y - 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.5F * r, 0.5F * g, 0.5F * b);
		renderer.renderFaceYNeg(block, x, y - OVERLAY_SHIFT, z, textureIndex);
	}

	protected static void renderTopFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y + 1, z, 1))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y + 1, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(r, g, b);
		renderer.renderFaceYPos(block, x, y + OVERLAY_SHIFT, z, textureIndex);
	}

	protected static void renderEastFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y, z - 1, 2))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z - 1, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.8F * r, 0.8F * g, 0.8F * b);
		renderer.renderFaceZNeg(block, x, y, z - OVERLAY_SHIFT, textureIndex);
	}

	protected static void renderWestFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x, y, z + 1, 3))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x, y, z + 1, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.8F * r, 0.8F * g, 0.8F * b);
		renderer.renderFaceZPos(block, x, y, z + OVERLAY_SHIFT, textureIndex);
	}

	protected static void renderNorthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x - 1, y, z, 4))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x - 1, y, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.6F * r, 0.6F * g, 0.6F * b);
		renderer.renderFaceXNeg(block, x - OVERLAY_SHIFT, y, z, textureIndex);
	}

	protected static void renderSouthFace(IBlockAccess world, Block block, int x, int y, int z, RenderBlocks renderer, Icon textureIndex, int mixedBrightness, float r, float g, float b) {
		if((!renderer.renderAllFaces) && (!block.shouldSideBeRendered(world, x + 1, y, z, 5))) {
			return;
		}
		Tessellator tesselator = Tessellator.instance;

		tesselator.setBrightness(determineMixedBrightness(world, block, x + 1, y, z, renderer, mixedBrightness));
		tesselator.setColorOpaque_F(0.6F * r, 0.6F * g, 0.6F * b);
		renderer.renderFaceXPos(block, x + OVERLAY_SHIFT, y, z, textureIndex);
	}

	private boolean renderBlockRotated(IBlockAccess blockAccess, IBlockRotated block, int x, int y, int z, RenderBlocks renderer) {
		int direction = block.getBlockRotation(blockAccess, x, y, z) & 3;
		if(direction == 2) {
			renderer.uvRotateEast = 1;
			renderer.uvRotateWest = 1;
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
		} else if(direction == 1) {
			renderer.uvRotateSouth = 1;
			renderer.uvRotateNorth = 1;
		}
		boolean flag = renderer.renderStandardBlock((Block) block, x, y, z);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		return flag;
	}

	private boolean renderBlockOverlayRotated(IBlockAccess blockAccess, Block block, int x, int y, int z, RenderBlocks renderer) {
		int direction = ((IBlockRotated) block).getBlockRotation(blockAccess, x, y, z) & 3;
		if(direction == 2) {
			renderer.uvRotateEast = 1;
			renderer.uvRotateWest = 1;
			renderer.uvRotateTop = 1;
			renderer.uvRotateBottom = 1;
		} else if(direction == 1) {
			renderer.uvRotateSouth = 1;
			renderer.uvRotateNorth = 1;
		}
		boolean flag = renderer.renderStandardBlock(block, x, y, z);
		renderBlockOverlay(blockAccess, (BlockOverlay) block, x, y, z, renderer);
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;
		renderer.uvRotateNorth = 0;
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		return flag;
	}

	private boolean renderBlockStairs(IBlockAccess blockAccess, BlockStoneStairs block, int x, int y, int z, RenderBlocks renderer) {
		block.setLowerBoundingBox(blockAccess, x, y, z);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		boolean flag = block.setBoundingBox1(blockAccess, x, y, z);
		renderer.setRenderBoundsFromBlock(block);
		renderer.renderStandardBlock(block, x, y, z);
		if(flag && block.setBoundingBox2(blockAccess, x, y, z)) {
			renderer.setRenderBoundsFromBlock(block);
			renderer.renderStandardBlock(block, x, y, z);
		}
		return true;
	}

	public boolean shouldRender3DInInventory() {
		return true;
	}

	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		if(modelID == TCConfig.BLOCK_OVERLAY_RENDER_ID) {
			renderItemBlockOverlay((BlockOverlay) block, metadata, modelID, renderer);
		} else if(modelID == TCConfig.BLOCK_ROTATED_RENDER_ID) {
			renderItemBlock(block, metadata, modelID, renderer);
		} else if(modelID == TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID) {
			renderItemBlockOverlay((BlockOverlay) block, metadata, modelID, renderer);
		} else if(modelID == TCConfig.BLOCK_STAIRS_RENDER_ID) {
			renderItemBlockStairs(block, metadata, modelID, renderer);
		}
	}

	private void renderItemBlockOverlay(BlockOverlay block, int metadata, int modelID, RenderBlocks renderer) {
		Icon overlay = null;
		Tessellator tessellator = Tessellator.instance;
		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(metadata);
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		overlay = block.getBlockOverlayTexture(0, metadata);
		if(overlay != null) {
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		overlay = block.getBlockOverlayTexture(1, metadata);
		if(overlay != null) {
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		overlay = block.getBlockOverlayTexture(2, metadata);
		if(overlay != null) {
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		overlay = block.getBlockOverlayTexture(3, metadata);
		if(overlay != null) {
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		overlay = block.getBlockOverlayTexture(4, metadata);
		if(overlay != null) {
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		overlay = block.getBlockOverlayTexture(5, metadata);
		if(overlay != null) {
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, overlay);
		}
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	private void renderItemBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(metadata);
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);

		GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);

		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
		tessellator.draw();

		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}

	private void renderItemBlockStairs(Block block, int metadata, int modelID, RenderBlocks renderer) {
		Tessellator tessellator = Tessellator.instance;
		if(renderer.useInventoryTint) {
			int color = block.getRenderColor(metadata);
			float r = (float) (color >> 16 & 255) / 255.0F;
			float g = (float) (color >> 8 & 255) / 255.0F;
			float b = (float) (color & 255) / 255.0F;
			GL11.glColor4f(r, g, b, 1.0F);
		}
		block.setBlockBoundsForItemRender();
		renderer.setRenderBoundsFromBlock(block);
		
		for(int i = 0; i < 2; ++i) {
			if(i == 0) {
				renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5D);
			} else if(i == 1) {
				renderer.setRenderBounds(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
			}
			GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, -1.0F, 0.0F);
			renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, -1.0F);
			renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 0.0F, 1.0F);
			renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(-1.0F, 0.0F, 0.0F);
			renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
			tessellator.draw();
			tessellator.startDrawingQuads();
			tessellator.setNormal(1.0F, 0.0F, 0.0F);
			renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
			tessellator.draw();
			GL11.glTranslatef(0.5F, 0.5F, 0.5F);
		}		
	}
}
