package mcdelta.core.block;

import net.minecraft.block.Block;

public enum Properties
{
     STONE;
     
     public void setProperties (Block block)
     {
          switch (this)
          {
               case STONE:
                    block.setHardness(2.0F);
                    block.setResistance(10.0F);
                    break;
               default:
                    break;
          
          }
     }
}
