package mcdelta.core.assets.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class BlockData
{
     public Block      block;
     public int        meta;
     public TileEntity tile;
     
     
     
     
     public BlockData (final Block block)
     {
          this(block, 0, null);
     }
     
     
     
     
     public BlockData (final Block block, final int meta)
     {
          this(block, meta, null);
     }
     
     
     
     
     public BlockData (final Block block, final int meta, final TileEntity tile)
     {
          this.block = block;
          this.meta = meta;
          this.tile = tile;
     }
     
     
     
     
     @Override
     public String toString ()
     {
          return "BlockData [ block:" + this.block.getUnlocalizedName() + ", metadata:" + this.meta + ", tile:" + this.tile + " ]";
     }
     
     
     
     
     @Override
     public int hashCode ()
     {
          final int prime = 31;
          int result = 1;
          result = prime * result + (this.block == null ? 0 : this.block.hashCode());
          result = prime * result + this.meta;
          result = prime * result + (this.tile == null ? 0 : this.tile.hashCode());
          return result;
     }
     
     
     
     
     @Override
     public boolean equals (final Object obj)
     {
          if (this == obj)
          {
               return true;
          }
          if (obj == null)
          {
               return false;
          }
          if (!(obj instanceof BlockData))
          {
               return false;
          }
          final BlockData other = (BlockData) obj;
          
          if (this.block == null)
          {
               if (other.block != null)
               {
                    return false;
               }
          }
          else if (!this.block.equals(other.block))
          {
               return false;
          }
          if (this.meta != other.meta)
          {
               return false;
          }
          if (this.tile == null)
          {
               if (other.tile != null)
               {
                    return false;
               }
          }
          else if (!this.tile.equals(other.tile))
          {
               return false;
          }
          return true;
     }
}
