package mcdelta.core.block;

import net.minecraft.block.Block;

public enum Properties
{
     STONE,
     LIGHT_SOURCE;
     
     public void setProperties (Block block)
     {
          switch (this)
          {
               case STONE:
                    block.setHardness(2.0F);
                    block.setResistance(10.0F);
                    block.setStepSound(Block.soundStoneFootstep);
                    break;
               case LIGHT_SOURCE:
                    block.setHardness(0.3F);
                    block.setLightValue(1.0F);
                    block.setStepSound(Block.soundGlassFootstep);
                    block.setLightValue(1.0F);
                    break;
               default:
                    break;
          
          }
     }
}
