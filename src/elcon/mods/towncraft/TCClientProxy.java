package elcon.mods.towncraft;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TCClientProxy extends TCCommonProxy {

	@Override
	public void registerRenderingInformation() {
		//get render ids
		TCConfig.BLOCK_OVERLAY_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		TCConfig.BLOCK_ROTATED_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		TCConfig.BLOCK_STAIRS_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		
		//register block rendering handlers
		TCBlockRenderingHandler blockRenderingHandler = new TCBlockRenderingHandler();
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_OVERLAY_RENDER_ID, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_ROTATED_RENDER_ID, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_STAIRS_RENDER_ID, blockRenderingHandler);
	}
}
