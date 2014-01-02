package mcdelta.core.client.item;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public interface IExtraPasses
{
     public int getPasses (ItemStack stack);
     
     
     
     
     public Icon getIconFromPass (ItemStack stack, int pass);
     
     
     
     
     public int getColorFromPass (ItemStack stack, int pass);
     
     
     
     
     /**
      * Does not account for isItemEnchanted
      * 
      * @param stack
      * @param pass
      * @return if stack should render shiny this pass
      */
     public boolean getShinyFromPass (ItemStack stack, int pass);
}
