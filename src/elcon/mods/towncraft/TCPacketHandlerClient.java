package elcon.mods.towncraft;

import net.minecraft.client.Minecraft;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.towncraft.tileentities.TileEntityMetadata;

@SideOnly(Side.CLIENT)
public class TCPacketHandlerClient implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int packetID = dat.readInt();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			handlePacketClient(packetID, dat);
		}
	}

	private void handlePacketClient(int packetID, ByteArrayDataInput dat) {
		World world = Minecraft.getMinecraft().theWorld;
		
		switch(packetID) {
		case 0:
			handleTileEntityMetadata(world, dat);
			break;
		}
	}

	private void handleTileEntityMetadata(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		TileEntityMetadata tile = (TileEntityMetadata) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityMetadata();
			world.setBlockTileEntity(x, y, z, tile);
		}
		tile.setTileMetadata(dat.readInt());
		world.markBlockForUpdate(x, y, z);
	}
}
