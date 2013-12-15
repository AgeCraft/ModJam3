package elcon.mods.towncraft.structures;

public class StructureTestTown extends Structure {

	public StructureTestTown(String name) {
		super(name);
		setMinSize(48, 48, 48);
		setMaxSize(96, 96, 96);
		setMinMaxComponents(10, 20);
		startComponent = "townHall";
		
		StructureComponent townHall = new StructureComponent("townHall");
		townHall.setMinMaxOccurrences(1, 1);
	}
}
