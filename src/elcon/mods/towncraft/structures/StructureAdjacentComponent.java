package elcon.mods.towncraft.structures;

public class StructureAdjacentComponent {

	public StructureComponent component;
	public int offsetX;
	public int offsetY;
	public int offsetZ;
	public float chance;
	
	public StructureAdjacentComponent(StructureComponent component, int offsetX, int offsetY, int offsetZ, float chance) {
		this.component = component;
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.offsetZ = offsetZ;
		this.chance = chance;
	}
}
