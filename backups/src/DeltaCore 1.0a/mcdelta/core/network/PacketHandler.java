package mcdelta.core.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

/**
 * Network code based on Pahimar's, the ee3 dev
 */

public class PacketHandler implements IPacketHandler
{
     @Override
     public void onPacketData (INetworkManager manager, Packet250CustomPayload packet, Player player)
     {
          PacketDelta packetDelta = EnumPacketTypes.buildPacket(packet.data);
          packetDelta.execute(manager, player);
     }
}