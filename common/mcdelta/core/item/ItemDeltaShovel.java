package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.material.ToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.item.ItemSpade;

public class ItemDeltaShovel extends ItemDeltaTool
{
     
     public ItemDeltaShovel (final ModDelta mod, final ToolMaterial mat)
     {
          super(mod, "shovel", mat, ItemSpade.blocksEffectiveAgainst, 1.0F);
     }
     
     
     
     
     @Override
     public boolean canHarvestBlock (final Block block)
     {
          return block == Block.snow ? true : block == Block.blockSnow;
     }
}
