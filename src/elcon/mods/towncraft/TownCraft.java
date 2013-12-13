package elcon.mods.towncraft;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkMod.SidedPacketHandler;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.towncraft.blocks.BlockStone;

@Mod(modid = TCReference.MOD_ID, name = TCReference.NAME, version = TCReference.VERSION, acceptedMinecraftVersions = TCReference.MC_VERSION, dependencies = TCReference.DEPENDENCIES)
@NetworkMod(clientSideRequired = true, serverSideRequired = false, clientPacketHandlerSpec = @SidedPacketHandler(packetHandler = TCPacketHandlerClient.class, channels = {"TownCraft"}), serverPacketHandlerSpec = @SidedPacketHandler(packetHandler = TCPacketHandler.class, channels = {"TownCraft"}))
public class TownCraft {

	@Instance(TCReference.MOD_ID)
	public static TownCraft instance;
	
	@SidedProxy(clientSide = TCReference.CLIENT_PROXY_CLASS, serverSide = TCReference.SERVER_PROXY_CLASS)
	public static TCCommonProxy proxy;
	
	public static TCConfig config;
	public static TCCreativeTab creativeTab;
	
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
		
		//init creative tab
		creativeTab = new TCCreativeTab(CreativeTabs.getNextID(), "TownCraft");
		
		//init blocks
		stone = new BlockStone(TCConfig.blockStoneID).setUnlocalizedName("TC_stone");
		//stoneSlab = new BlockStone(TCConfig.blockStoneSlabID).setUnlocalizedName("TC_stoneSlab");
		//stoneStairs = new BlockStone(TCConfig.blockStoneStairsID).setUnlocalizedName("TC_stoneStairs");
		//stoneCracked = new BlockStone(TCConfig.blockStoneID).setUnlocalizedName("TC_stoneCracked");
		//stoneCrackedSlab = new BlockStone(TCConfig.blockStoneSlabID).setUnlocalizedName("TC_stoneCrackedSlab");
		//stoneCrackedStairs = new BlockStone(TCConfig.blockStoneStairsID).setUnlocalizedName("TC_stoneCrackedStairs");
		//stoneMossy = new BlockStone(TCConfig.blockStoneMossyID).setUnlocalizedName("TC_stoneMossy");
		//stoneMossySlab = new BlockStone(TCConfig.blockStoneMossySlabID).setUnlocalizedName("TC_stoneMossySlab");
		//stoneMossyStairs = new BlockStone(TCConfig.blockStoneMossyStairsID).setUnlocalizedName("TC_stoneMossyStairs");
		//stoneBrick = new BlockStone(TCConfig.blockStoneBrickID).setUnlocalizedName("TC_stoneBrick");
		//stoneBrickSlab = new BlockStone(TCConfig.blockStoneBrickSlabID).setUnlocalizedName("TC_stoneBrickSlab");
		//stoneBrickStairs = new BlockStone(TCConfig.blockStoneBrickStairsID).setUnlocalizedName("TC_stoneBrickStairs");
		
		//register blocks
		GameRegistry.registerBlock(stone, "TC_stone");
		//GameRegistry.registerBlock(stoneSlab, "TC_stoneSlab");
		//GameRegistry.registerBlock(stoneStairs, "TC_stoneStairs");
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
