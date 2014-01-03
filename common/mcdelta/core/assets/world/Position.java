package mcdelta.core.assets.world;

import java.util.logging.Level;

import mcdelta.core.assets.Assets;
import mcdelta.core.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Position
{
     public IBlockAccess world;
     public int          x;
     public int          y;
     public int          z;
     public int          dimensionID;
     
     
     
     
     public Position (final IBlockAccess world, final int x, final int y, final int z)
     {
          this(world, x, y, z, 0);
     }
     
     
     
     
     public Position (final IBlockAccess world, final int x, final int y, final int z, final int dimID)
     {
          this.world = world;
          this.x = x;
          this.y = y;
          this.z = z;
          this.dimensionID = dimID;
     }
     
     
     
     
     public int getMeta ()
     {
          return this.world.getBlockMetadata(this.x, this.y, this.z);
     }
     
     
     
     
     public Position move (final EnumFacing facing)
     {
          return new Position(this.world, this.x + facing.getFrontOffsetX(), this.y + facing.getFrontOffsetY(), this.z + facing.getFrontOffsetZ(), this.dimensionID);
     }
     
     
     
     
     public Position move (final int i, final int j, final int k)
     {
          return new Position(this.world, this.x + i, this.y + j, this.z + k, this.dimensionID);
     }
     
     
     
     
     public BlockData getBlockData ()
     {
          if (Assets.isAirBlock(this))
          {
               return null;
          }
          return new BlockData(this.getBlock(), this.getMeta(), this.getTile());
     }
     
     
     
     
     public Block getBlock ()
     {
          return Block.blocksList[this.world.getBlockId(this.x, this.y, this.z)];
     }
     
     
     
     
     public TileEntity getTile ()
     {
          return this.world.getBlockTileEntity(this.x, this.y, this.z);
     }
     
     
     
     
     @Override
     public String toString ()
     {
          return "Position [ x:" + this.x + ", y:" + this.y + ", z:" + this.z + ", dimension:" + this.dimensionID + " ]";
     }
     
     
     
     
     public Position copy ()
     {
          return new Position(this.world, this.x, this.y, this.z, this.dimensionID);
     }
     
     
     
     
     public boolean isNormalCube ()
     {
          if (this.world instanceof World)
          {
               final World world = (World) this.world;
               
               return this.getBlock().isBlockNormalCube(world, this.x, this.y, this.z);
          }
          Logger.log(Level.SEVERE, "[ isNormalCube() ]" + Assets.worldAccessError, this);
          
          return false;
     }
}
