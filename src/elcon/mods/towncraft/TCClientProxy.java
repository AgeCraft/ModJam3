package elcon.mods.towncraft;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TCClientProxy extends TCCommonProxy {

	@Override
	public void registerRenderingInformation() {
		//register block rendering handlers
		TCConfig.BLOCK_OVERLAY_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		TCConfig.BLOCK_ROTATED_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_OVERLAY_RENDER_ID, new TCBlockRenderingHandler());
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_ROTATED_RENDER_ID, new TCBlockRenderingHandler());
		RenderingRegistry.registerBlockHandler(TCConfig.BLOCK_OVERLAY_ROTATED_RENDER_ID, new TCBlockRenderingHandler());
	}
}
