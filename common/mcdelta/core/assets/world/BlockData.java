package mcdelta.core.assets.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class BlockData
{
     public Block      block;
     public int        meta;
     public TileEntity tile;
     
     
     
     
     public BlockData (Block b)
     {
          this(b, 0, null);
     }
     
     
     
     
     public BlockData (Block b, int i)
     {
          this(b, i, null);
     }
     
     
     
     
     public BlockData (Block b, int i, TileEntity tileEntity)
     {
          this.block = b;
          this.meta = i;
          this.tile = tileEntity;
     }
     
     
     
     
     @Override
     public String toString ()
     {
          return "BlockData [ block:" + this.block.getUnlocalizedName() + ", metadata:" + this.meta + ", tile:" + this.tile + " ]";
     }
     
     
     
     
     public boolean equals (BlockData data)
     {
          if (data.block == this.block && data.meta == this.meta)
          {
               return true;
          }
          
          return false;
     }
}
