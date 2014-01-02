package mcdelta.core.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import cpw.mods.fml.common.network.Player;

public class PacketDelta
{
    private EnumPacketTypes type;

    public PacketDelta(EnumPacketTypes e)
    {
        this.type = e;
    }

    public byte[] populate()
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try
        {
            dos.writeByte(type.ordinal());
            this.writeData(dos);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }

        return bos.toByteArray();
    }

    public void readPopulate(DataInputStream data)
    {
        try
        {
            this.readData(data);
        }
        catch (IOException e)
        {
            e.printStackTrace(System.err);
        }
    }

    public void readData(DataInputStream data) throws IOException
    {
    }

    public void writeData(DataOutputStream dos) throws IOException
    {
    }

    public void execute(INetworkManager network, Player player)
    {
    }

    protected static void writeNBTTagCompound(NBTTagCompound tagCompound, DataOutput data) throws IOException
    {
        if (tagCompound == null)
        {
            data.writeShort(-1);
        }
        else
        {
            byte[] abyte = CompressedStreamTools.compress(tagCompound);
            data.writeShort((short) abyte.length);
            data.write(abyte);
        }
    }

    public static NBTTagCompound readNBTTagCompound(DataInput data) throws IOException
    {
        short short1 = data.readShort();

        if (short1 < 0)
        {
            return null;
        }
        else
        {
            byte[] abyte = new byte[short1];
            data.readFully(abyte);
            return CompressedStreamTools.decompress(abyte);
        }
    }
}
