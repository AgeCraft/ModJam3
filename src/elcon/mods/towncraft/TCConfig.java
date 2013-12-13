package elcon.mods.towncraft;

import java.io.File;

import net.minecraftforge.common.Configuration;

public class TCConfig {

	public Configuration config;
	
	public static int blockStoneID;
	public static int blockStoneSlabID;
	public static int blockStoneStairsID;
	public static int blockStoneCrackedID;
	public static int blockStoneCrackedSlabID;
	public static int blockStoneCrackedStairsID;
	public static int blockStoneMossyID;
	public static int blockStoneMossySlabID;
	public static int blockStoneMossyStairsID;
	public static int blockStoneBrickID;
	public static int blockStoneBrickSlabID;
	public static int blockStoneBrickStairsID;
	
	public TCConfig(Configuration config) {
		this.config = config;
	}
	
	public TCConfig(File file) {
		this(new Configuration(file));
	}
	
	public void load() {
		config.load();
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
