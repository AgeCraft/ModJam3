package elcon.mods.towncraft.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;

public class TileEntityMetadata extends TileEntity {

	private int meta = 0;
	public boolean droppedBlock = false;

	public TileEntityMetadata() {
	}
	
	public TileEntityMetadata(int meta) {
		setTileMetadata(meta);
	}
	
	public TileEntityMetadata(Integer meta) {
		this(meta.intValue());
	}
	
	public int getTileMetadata() {
		return meta;
	}

	public void setTileMetadata(int meta) {
		this.meta = meta;
	}

	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(0);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);

			dos.writeInt(meta);
		} catch(Exception e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "TownCraft";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;
		return packet;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		meta = nbt.getInteger("Metadata");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setInteger("Metadata", meta);
	}

	@Override
	public boolean canUpdate() {
		return false;
	}
}
