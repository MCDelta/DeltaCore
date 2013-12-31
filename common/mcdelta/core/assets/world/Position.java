package mcdelta.core.assets.world;

import java.util.logging.Level;

import mcdelta.core.Logger;
import mcdelta.core.assets.Assets;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Position
{
     public IBlockAccess blockAccess;
     public int          x;
     public int          y;
     public int          z;
     public int          dimensionID;
     
     
     
     
     public Position (IBlockAccess world, int x, int y, int z)
     {
          this(world, x, y, z, 0);
     }
     
     
     
     
     public Position (IBlockAccess world, int i, int ii, int iii, int iiii)
     {
          this.blockAccess = world;
          this.x = i;
          this.y = ii;
          this.z = iii;
          this.dimensionID = iiii;
     }
     
     
     
     
     public int getMeta ()
     {
          return blockAccess.getBlockMetadata(x, y, z);
     }
     
     
     
     
     public Position move (EnumFacing facing)
     {
          return new Position(blockAccess, x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ(), dimensionID);
     }
     
     
     
     
     public Position move (int i, int j, int k)
     {
          return new Position(blockAccess, x + i, y + j, z + k, dimensionID);
     }
     
     
     
     
     public BlockData getBlockData ()
     {
          if (Assets.isAirBlock(this))
          {
               return null;
          }
          
          return new BlockData(getBlock(), getMeta(), getTile());
     }
     
     
     
     
     public Block getBlock ()
     {
          return Block.blocksList[blockAccess.getBlockId(x, y, z)];
     }
     
     
     
     
     public TileEntity getTile ()
     {
          return blockAccess.getBlockTileEntity(x, y, z);
     }
     
     
     
     
     @Override
     public String toString ()
     {
          return "Position [ x:" + this.x + ", y:" + this.y + ", z:" + this.z + ", dimension:" + this.dimensionID + " ]";
     }
     
     
     
     
     public Position copy ()
     {
          return new Position(this.blockAccess, this.x, this.y, this.z, this.dimensionID);
     }
     
     
     
     
     public boolean isNormalCube ()
     {
          if (blockAccess instanceof World)
          {
               World world = (World) blockAccess;
               
               return getBlock().isBlockNormalCube(world, x, y, z);
          }
          
          else
          {
               Logger.log(Level.SEVERE, "[ isNormalCube() ]" + Assets.worldAccessError, this);
               
               return false;
          }
     }
}
