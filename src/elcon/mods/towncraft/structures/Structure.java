package elcon.mods.towncraft.structures;

import java.util.HashMap;

public class Structure {

	public String name;
	
	public int minSizeX;
	public int minSizeY;
	public int minSizeZ;
	public int maxSizeX;
	public int maxSizeY;
	public int maxSizeZ;
	
	public HashMap<String, StructureComponent> components = new HashMap<String, StructureComponent>();
	public String startComponent;
	
	public Structure(String name) {
		this.name = name;
	}
}
