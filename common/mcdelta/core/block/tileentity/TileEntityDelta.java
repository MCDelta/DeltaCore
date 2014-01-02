package mcdelta.core.block.tileentity;

import mcdelta.core.assets.world.Position;
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
     
     
     
     
     @Override
     public Packet getDescriptionPacket ()
     {
          final NBTTagCompound nbt = new NBTTagCompound();
          this.writeToNBT(nbt);
          return new Packet132TileEntityData(this.xCoord, this.yCoord, this.zCoord, 1, nbt);
     }
     
     
     
     
     @Override
     public void onDataPacket (final INetworkManager net, final Packet132TileEntityData packet)
     {
          this.readFromNBT(packet.data);
     }
}
