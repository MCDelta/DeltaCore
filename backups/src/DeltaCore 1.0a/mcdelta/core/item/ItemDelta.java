package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.DeltaMod;
import mcdelta.core.assets.Assets;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ItemDelta extends Item
{
     public String    name;
     private DeltaMod mod;
     public int       deltaID;
     
     
     
     
     public ItemDelta (DeltaMod deltaMod, int i, String s)
     {
          super(deltaMod.getConfig().getItemID(s, i));
          
          this.name = s;
          this.mod = deltaMod;
          this.setUnlocalizedName(mod.getModid() + ":" + s);
          this.deltaID = i;
          this.maxStackSize = 64;
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister iconRegister)
     {
          String s = this.name.replace(".", "_");
          
          this.itemIcon = iconRegister.registerIcon(mod.getModid() + ":" + s);
     }
     
     
     
     
     public DeltaMod getMod ()
     {
          return mod;
     }
}
