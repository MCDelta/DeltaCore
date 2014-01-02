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
     private final int type;
     
     
     
     
     public PacketDelta (final int type)
     {
          this.type = type;
          PacketHandler.packets[type] = this.getClass();
     }
     
     
     
     
     public byte[] populate ()
     {
          final ByteArrayOutputStream bos = new ByteArrayOutputStream();
          final DataOutputStream dos = new DataOutputStream(bos);
          
          try
          {
               dos.writeByte(this.type);
               this.writeData(dos);
          }
          catch (final IOException e)
          {
               e.printStackTrace(System.err);
          }
          return bos.toByteArray();
     }
     
     
     
     
     public void readPopulate (final DataInputStream data)
     {
          try
          {
               this.readData(data);
          }
          catch (final IOException e)
          {
               e.printStackTrace(System.err);
          }
     }
     
     
     
     
     public void readData (final DataInputStream data) throws IOException
     {
     }
     
     
     
     
     public void writeData (final DataOutputStream dos) throws IOException
     {
     }
     
     
     
     
     public void execute (final INetworkManager network, final Player player)
     {
     }
     
     
     
     
     protected static void writeNBTTagCompound (final NBTTagCompound tagCompound, final DataOutput data) throws IOException
     {
          if (tagCompound == null)
          {
               data.writeShort(-1);
          }
          else
          {
               final byte[] abyte = CompressedStreamTools.compress(tagCompound);
               data.writeShort((short) abyte.length);
               data.write(abyte);
          }
     }
     
     
     
     
     public static NBTTagCompound readNBTTagCompound (final DataInput data) throws IOException
     {
          final short short1 = data.readShort();
          
          if (short1 < 0)
          {
               return null;
          }
          final byte[] abyte = new byte[short1];
          data.readFully(abyte);
          return CompressedStreamTools.decompress(abyte);
     }
}
