package mcdelta.core.block;

import net.minecraft.block.Block;

public enum Properties
{
     STONE,
     IRON,
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
               case IRON:
                    block.setHardness(3.0F);
                    block.setResistance(8.0F);
                    block.setStepSound(Block.soundGlassFootstep);
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
