package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.logging.Logger;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public class ItemDelta extends Item
{
     public ModDelta mod;
     public String   name;
     private boolean checkUnlocalized = true;
     
     
     
     
     public ItemDelta (final String name)
     {
          this(DeltaCore.instance, name);
     }
     
     
     
     
     public ItemDelta (final ModDelta mod, final String name)
     {
          this(mod, name, true);
     }
     
     
     
     
     public ItemDelta (final ModDelta mod, final String name, final boolean checkLoc)
     {
          super(mod.config().getItemID(name));
          maxStackSize = 64;
          
          checkUnlocalized = checkLoc;
          
          // ItemDelta code
          this.mod = mod;
          this.name = name;
          final String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          
          if (checkUnlocalized && !StatCollector.func_94522_b("item." + unlocalized + ".name"))
          {
               DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
          }
          
          if (this instanceof IExtraPasses)
          {
               ClientProxy.extraPasses.add(this);
          }
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          final String s = name.replace(".", "_");
          
          itemIcon = this.doRegister(s, register);
     }
     
     
     
     
     protected Icon doRegister (final String s, final IconRegister register)
     {
          return doRegister(mod.id(), s, register);
     }
     
     
     
     
     public static Icon doRegister (final String modid, final String s, final IconRegister register)
     {
          final ResourceLocation loc = new ResourceLocation(modid.toLowerCase(), "textures/items/" + s + ".png");
          
          if (Assets.resourceExists(loc))
          {
               return register.registerIcon(modid.toLowerCase() + ":" + s);
          }
          Logger.severe("missing icon! " + loc);
          return register.registerIcon(DeltaCore.MOD_ID + ":null");
     }
     
     
     
     
     public String getid ()
     {
          return mod.id();
     }
     
}
