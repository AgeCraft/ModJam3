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
import elcon.mods.towncraft.blocks.BlockStoneBrick;
import elcon.mods.towncraft.blocks.BlockStoneCracked;
import elcon.mods.towncraft.blocks.BlockStoneMossy;
import elcon.mods.towncraft.blocks.BlockStoneSlab;
import elcon.mods.towncraft.items.ItemBlockExtendedMetadata;
import elcon.mods.towncraft.items.ItemBlockMetadata;
import elcon.mods.towncraft.items.ItemBlockMetadataOverlay;
import elcon.mods.towncraft.tileentities.TileEntityExtended;
import elcon.mods.towncraft.tileentities.TileEntityMetadata;

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
		// init log
		TCLog.init();

		// init and load config
		config = new TCConfig(event.getSuggestedConfigurationFile());
		config.load();
		config.save();

		// init creative tab
		creativeTab = new TCCreativeTab(CreativeTabs.getNextID(), "TownCraft");

		// init blocks
		stone = new BlockStone(TCConfig.blockStoneID).setUnlocalizedName("TC_stone");
		stoneSlab = new BlockStoneSlab(TCConfig.blockStoneSlabID, 0).setUnlocalizedName("TC_stoneSlab");
		//stoneStairs = new BlockStoneStairs(TCConfig.blockStoneStairsID, 0).setUnlocalizedName("TC_stoneStairs");
		stoneCracked = new BlockStoneCracked(TCConfig.blockStoneCrackedID).setUnlocalizedName("TC_stoneCracked");
		stoneCrackedSlab = new BlockStoneSlab(TCConfig.blockStoneCrackedSlabID, 1).setUnlocalizedName("TC_stoneCrackedSlab");
		//stoneCrackedStairs = new BlockStoneStairs(TCConfig.blockStoneCrackedStairsID, 1).setUnlocalizedName("TC_stoneCrackedStairs");
		stoneMossy = new BlockStoneMossy(TCConfig.blockStoneMossyID).setUnlocalizedName("TC_stoneMossy");
		stoneMossySlab = new BlockStoneSlab(TCConfig.blockStoneMossySlabID, 2).setUnlocalizedName("TC_stoneMossySlab");
		//stoneMossyStairs = new BlockStoneStairs(TCConfig.blockStoneMossyStairsID, 2).setUnlocalizedName("TC_stoneMossyStairs");
		stoneBrick = new BlockStoneBrick(TCConfig.blockStoneBrickID).setUnlocalizedName("TC_stoneBrick");
		stoneBrickSlab = new BlockStoneSlab(TCConfig.blockStoneBrickSlabID, 3).setUnlocalizedName("TC_stoneBrickSlab");
		//stoneBrickStairs = new BlockStoneStairs(TCConfig.blockStoneBrickStairsID, 3).setUnlocalizedName("TC_stoneBrickStairs");

		// register blocks
		GameRegistry.registerBlock(stone, ItemBlockMetadata.class, "TC_stone");
		GameRegistry.registerBlock(stoneSlab, ItemBlockExtendedMetadata.class, "TC_stoneSlab");
		//GameRegistry.registerBlock(stoneStairs, ItemBlockExtendedMetadata.class, "TC_stoneStairs");
		GameRegistry.registerBlock(stoneCracked, ItemBlockMetadataOverlay.class, "TC_stoneCracked");
		GameRegistry.registerBlock(stoneCrackedSlab, ItemBlockExtendedMetadata.class, "TC_stoneCrackedSlab");
		//GameRegistry.registerBlock(stoneCrackedStairs, ItemBlockExtendedMetadata.class, "TC_stoneCrackedStairs");
		GameRegistry.registerBlock(stoneMossy, ItemBlockMetadataOverlay.class, "TC_stoneMossy");
		GameRegistry.registerBlock(stoneMossySlab, ItemBlockExtendedMetadata.class, "TC_stoneMossySlab");
		//GameRegistry.registerBlock(stoneMossyStairs, ItemBlockExtendedMetadata.class, "TC_stoneMossyStairs");
		GameRegistry.registerBlock(stoneBrick, ItemBlockExtendedMetadata.class, "TC_stoneBrick");
		GameRegistry.registerBlock(stoneBrickSlab, ItemBlockExtendedMetadata.class, "TC_stoneBrickSlab");
		//GameRegistry.registerBlock(stoneBrickStairs, ItemBlockExtendedMetadata.class, "TC_stoneBrickStairs");

		// register tile entities
		GameRegistry.registerTileEntity(TileEntityExtended.class, "TileExtended");
		GameRegistry.registerTileEntity(TileEntityMetadata.class, "TileMetadata");
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		// register render information
		proxy.registerRenderingInformation();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
