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
    public void onPacketData(final INetworkManager manager, final Packet250CustomPayload packet, final Player player)
    {
        final PacketDelta packetDelta = buildPacket(packet.data);
        packetDelta.execute(manager, player);
    }

    public static PacketDelta buildPacket(final byte[] data)
    {
        final ByteArrayInputStream bis = new ByteArrayInputStream(data);
        final int selector = bis.read();
        final DataInputStream dis = new DataInputStream(bis);

        PacketDelta packet = null;

        try
        {
            packet = packets[selector].newInstance();
        } catch (final Exception e)
        {
            e.printStackTrace(System.err);
        }
        packet.readPopulate(dis);

        return packet;
    }
}
