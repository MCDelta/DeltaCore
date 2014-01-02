package mcdelta.core.block.tileentity;

import mcdelta.core.assets.Position;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet132TileEntityData;
import net.minecraft.tileentity.TileEntity;

public class TileEntityDelta extends TileEntity
{
     public Position getPosition ()
     {
          return new Position(this.worldObj, this.xCoord, this.yCoord, this.zCoord);
     }
     
     
     
     
     public Packet getDescriptionPacket ()
     {
          NBTTagCompound nbtTag = new NBTTagCompound();
          this.writeToNBT(nbtTag);
          return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbtTag);
     }
     
     
     
     
     public void onDataPacket (INetworkManager net, Packet132TileEntityData packet)
     {
          readFromNBT(packet.data);
     }
}
