package mcdelta.core.block.item;

import mcdelta.core.block.IMetadata;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockMeta extends ItemBlock
{
     public ItemBlockMeta (int id)
     {
          super(id);
          setMaxDamage(0);
          setHasSubtypes(true);
     }
     
     
     
     
     public String getUnlocalizedName (ItemStack stack)
     {
          return ((IMetadata) Block.blocksList[getBlockID()]).getNameForMetadata(stack.getItemDamage());
     }
     
     
     
     
     public int getMetadata (int meta)
     {
          return meta;
     }
}
