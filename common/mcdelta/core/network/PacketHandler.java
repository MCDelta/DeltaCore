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
     public static Class<? extends PacketDelta>[] packets = new Class[16];
     
     
     
     
     @Override
     public void onPacketData (INetworkManager manager, Packet250CustomPayload packet, Player player)
     {
          PacketDelta packetDelta = buildPacket(packet.data);
          packetDelta.execute(manager, player);
     }
     
     
     
     
     public static PacketDelta buildPacket (byte[] data)
     {
          ByteArrayInputStream bis = new ByteArrayInputStream(data);
          int selector = bis.read();
          DataInputStream dis = new DataInputStream(bis);
          
          PacketDelta packet = null;
          
          try
          {
               packet = packets[selector].newInstance();
          }
          catch (Exception e)
          {
               e.printStackTrace(System.err);
          }
          
          packet.readPopulate(dis);
          
          return packet;
     }
}
