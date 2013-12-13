package elcon.mods.towncraft;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;

@Mod(modid = TCReference.MOD_ID, name = TCReference.NAME, version = TCReference.VERSION, acceptedMinecraftVersions = TCReference.MC_VERSION, dependencies = TCReference.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(packetHandler = TCPacketHandlerClient.class, channels = {"TownCraft"}), serverPacketHandlerSpec = @SidedPacketHandler(packetHandler = TCPacketHandler.class, channels = {"TownCraft"}))
public class TownCraft {

	@Instance(TCReference.MOD_ID)
	public static TownCraft instance;
	
	@SidedProxy(clientSide = TCReference.CLIENT_PROXY_CLASS, serverSide = TCReference.SERVER_PROXY_CLASS)
	public static TCCommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		TCLog.init();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
