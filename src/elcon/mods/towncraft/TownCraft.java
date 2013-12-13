package elcon.mods.towncraft;

import net.minecraft.block.Block;
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
	
	public static TCConfig config;
	
	public static Block stone;
	public static Block stoneSlab;
	public static Block stoneStairs;
	public static Block stoneCracked;
	public static Block stoneCrackedSlab;
	public static Block stoneCrackedStairs;
	public static Block stoneMossy;
	public static Block stoneMossySlab;
	public static Block stoneMossyStairs;
	public static Block stoneBrick;
	public static Block stoneBrickSlab;
	public static Block stoneBrickStairs;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//init log
		TCLog.init();
		
		//init and load config
		config = new TCConfig(event.getSuggestedConfigurationFile());
		config.load();
		config.save();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		//register render information
		proxy.registerRenderingInformation();
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
