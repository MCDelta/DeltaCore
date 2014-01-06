package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.material.ItemMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;

public class ItemDeltaAxe extends ItemDeltaTool
{
     public ItemDeltaAxe (final ModDelta mod, final ItemMaterial mat)
     {
          super(mod, "axe", mat, ItemAxe.blocksEffectiveAgainst, 3.0F);
     }
     
     
     
     
     @Override
     public float getStrVsBlock (final ItemStack stack, final Block block)
     {
          return block != null && (block.blockMaterial == Material.wood || block.blockMaterial == Material.plants || block.blockMaterial == Material.vine) ? efficiencyOnProperMaterial : super.getStrVsBlock(stack, block);
     }
}
