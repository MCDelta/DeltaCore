package mcdelta.core.network;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public enum EnumPacketTypes
{
     KEY_PRESSED (PacketKeyPressed.class), 
     CRUSHER_POWER (PacketCrusherPower.class),
     CRUSHER_EXTEND (PacketCrusherExtend.class);
     
     public Class<? extends PacketDelta> clazz;
     
     
     
     
     EnumPacketTypes (Class<? extends PacketDelta> c)
     {
          this.clazz = c;
     }
     
     
     
     
     public static PacketDelta buildPacket (byte[] data)
     {
          ByteArrayInputStream bis = new ByteArrayInputStream(data);
          int selector = bis.read();
          DataInputStream dis = new DataInputStream(bis);
          
          PacketDelta packet = null;
          
          try
          {
               packet = values()[selector].clazz.newInstance();
          }
          catch (Exception e)
          {
               e.printStackTrace(System.err);
          }
          
          packet.readPopulate(dis);
          
          return packet;
     }
}