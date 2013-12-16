package elcon.mods.towncraft;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class TCConfig {

	public Configuration config;
	
	public static int BLOCK_OVERLAY_RENDER_ID = 0;
	public static int BLOCK_ROTATED_RENDER_ID = 0;
	public static int BLOCK_OVERLAY_ROTATED_RENDER_ID = 0;
	public static int BLOCK_STAIRS_RENDER_ID = 0;
	
	public static int blockStoneID = 4050;
	public static int blockStoneSlabID = 4051;
	public static int blockStoneStairsID = 4052;
	public static int blockStoneCrackedID = 4053;
	public static int blockStoneCrackedSlabID = 4054;
	public static int blockStoneCrackedStairsID = 4056;
	public static int blockStoneMossyID = 4057;
	public static int blockStoneMossySlabID = 4058;
	public static int blockStoneMossyStairsID = 4059;
	public static int blockStoneBrickID = 4060;
	public static int blockStoneBrickSlabID = 4061;
	public static int blockStoneBrickStairsID = 4062;
	public static int blockStoneBrickPillarID = 4063;
	public static int blockStoneBrickPillarSlabID = 4064;
	public static int blockStoneBrickPillarStairsID = 4065;
	
	public static boolean enableTestTownSpawn = false;
	
	public TCConfig(Configuration config) {
		this.config = config;
	}
	
	public TCConfig(File file) {
		this(new Configuration(file));
	}
	
	public void load() {
		config.load();
		
		blockStoneID = config.getBlock("blockStoneID", blockStoneID).getInt();
		blockStoneSlabID = config.getBlock("blockStoneSlabID", blockStoneSlabID).getInt();
		blockStoneStairsID = config.getBlock("blockStoneStairsID", blockStoneStairsID).getInt();
		blockStoneCrackedID = config.getBlock("blockStoneCrackedID", blockStoneCrackedID).getInt();
		blockStoneCrackedSlabID = config.getBlock("blockStoneCrackedSlabID", blockStoneCrackedSlabID).getInt();
		blockStoneCrackedStairsID = config.getBlock("blockStoneCrackedStairsID", blockStoneCrackedStairsID).getInt();
		blockStoneMossyID = config.getBlock("blockStoneMossyID", blockStoneMossyID).getInt();
		blockStoneMossySlabID = config.getBlock("blockStoneMossySlabID", blockStoneMossySlabID).getInt();
		blockStoneMossyStairsID = config.getBlock("blockStoneMossyStairsID", blockStoneMossyStairsID).getInt();
		blockStoneBrickID = config.getBlock("blockStoneBrickID", blockStoneBrickID).getInt();
		blockStoneBrickSlabID = config.getBlock("blockStoneBrickSlabID", blockStoneBrickSlabID).getInt();
		blockStoneBrickStairsID = config.getBlock("blockStoneBrickStairsID", blockStoneBrickStairsID).getInt();
		blockStoneBrickPillarID = config.getBlock("blockStoneBrickPillarID", blockStoneBrickPillarID).getInt();
		blockStoneBrickPillarSlabID = config.getBlock("blockStoneBrickPillarSlabID", blockStoneBrickPillarSlabID).getInt();
		blockStoneBrickPillarStairsID = config.getBlock("blockStoneBrickPillarStairsID", blockStoneBrickPillarStairsID).getInt();
		
		enableTestTownSpawn = config.get("enableTestTownSpawn", "test", enableTestTownSpawn, "We couldn't finish the mod in time, so decided to release a version with Colored Stone and Vertical Slabs. You can still see how far we got with Towns by enabeling this option, creating a new world and goto (0,64,0).").getBoolean(enableTestTownSpawn);
	}
	
	public void save() {
		config.save();
	}
	
	public void set(String categoryName, String propertyName, String newValue) {
		if(config.getCategoryNames().contains(categoryName)) {
			if(config.getCategory(categoryName).containsKey(propertyName)) {
				config.getCategory(categoryName).get(propertyName).set(newValue);
			}
		}
		config.save();
	}
}
