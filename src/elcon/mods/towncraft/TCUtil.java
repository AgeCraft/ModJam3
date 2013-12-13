package elcon.mods.towncraft;

import java.io.File;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TCUtil {
	
	public static String coordsToString(int x, int y, int z) {
		return x + "," + y + "," + z;
	}
	
	public static String coordsToString(TileEntity tile) {
		return coordsToString(tile.xCoord, tile.yCoord, tile.zCoord);
	}
	
	public static int getFirstUncoveredBlock(World world, int x, int z) {
		int i;
		for(i = 48; !world.isAirBlock(x, i + 1, z); i++) {}
		return i;
	}
	
	public static int getHighestBlock(World world, int x, int z) {
		int i;
		for(i = 255; !world.isAirBlock(x, i + 1, z); i--) {}
		return i;
	}

	public static String firstUpperCase(String s) {
		return Character.toString(s.charAt(0)).toUpperCase() + s.substring(1, s.length());
	}

	public static String getFileExtension(File file) {
		return getFileExtension(file.getName());
	}
	
	public static String getFileExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
		if(i > p) {
			return fileName.substring(i + 1);
		}
		return "";
	}
}
