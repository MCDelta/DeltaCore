package mcdelta.core.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import mcdelta.core.assets.Position;
import mcdelta.essentialalloys.block.single.machine.tileentity.TileEntityCrusher;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.Player;

public class PacketCrusherPower extends PacketDelta
{
     private int power;
     private int x;
     private int y;
     private int z;
     
     
     
     
     public PacketCrusherPower ()
     {
          super(EnumPacketTypes.CRUSHER_POWER);
     }
     
     
     
     
     public PacketCrusherPower (int i1, int i2, int i3, int i4)
     {
          super(EnumPacketTypes.CRUSHER_POWER);
          this.power = i1;
          this.x = i2;
          this.y = i3;
          this.z = i4;
     }
     
     
     
     
     @Override
     public void writeData (DataOutputStream data) throws IOException
     {    
          data.writeInt(power);
          data.writeInt(x);
          data.writeInt(y);
          data.writeInt(z);
     }
     
     
     
     
     @Override
     public void readData (DataInputStream data) throws IOException
     {
          power = data.readInt();
          x = data.readInt();
          y = data.readInt();
          z = data.readInt();
     }
     
     
     
     
     @Override
     public void execute (INetworkManager manager, Player playerParam)
     {
          EntityPlayer player = (EntityPlayer) playerParam;
          
          World world = player.worldObj;
          
          Position pos = new Position(world, x, y, z);
          
          if (pos.getTile() instanceof TileEntityCrusher)
          {
               TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();
               
               tile.power = this.power;
          }
     }
     
}
