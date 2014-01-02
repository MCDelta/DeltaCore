package mcdelta.core.item;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.logging.Logger;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;

public class ItemDelta extends Item
{
     public static Map<ToolMaterial, ItemWeapon>       swords           = new HashMap<ToolMaterial, ItemWeapon>();
     public static Map<ToolMaterial, ItemDeltaPickaxe> pickaxes         = new HashMap<ToolMaterial, ItemDeltaPickaxe>();
     public static Map<ToolMaterial, ItemDeltaShovel>  shovels          = new HashMap<ToolMaterial, ItemDeltaShovel>();
     public static Map<ToolMaterial, ItemDeltaAxe>     axes             = new HashMap<ToolMaterial, ItemDeltaAxe>();
     public static Map<ToolMaterial, ItemDeltaHoe>     hoes             = new HashMap<ToolMaterial, ItemDeltaHoe>();
     
     public static Map<ToolMaterial, ItemDeltaArmor>   helmets          = new HashMap<ToolMaterial, ItemDeltaArmor>();
     
     static
     {
          for (final ToolMaterial mat : ToolMaterial.mats)
          {
               if (mat.needsTools())
               {
                    final ItemDeltaShovel shovel = new ItemDeltaShovel(mat.getMod(), mat);
                    MinecraftForge.setToolClass(shovel, "shovel", mat.getHarvestLevel());
                    shovels.put(mat, shovel);
                    
                    final ItemDeltaPickaxe pick = new ItemDeltaPickaxe(mat.getMod(), mat);
                    MinecraftForge.setToolClass(pick, "pickaxe", mat.getHarvestLevel());
                    pickaxes.put(mat, pick);
                    
                    final ItemDeltaAxe axe = new ItemDeltaAxe(mat.getMod(), mat);
                    MinecraftForge.setToolClass(axe, "axe", mat.getHarvestLevel());
                    axes.put(mat, axe);
                    
                    final ItemWeapon sword = new ItemWeapon("sword", mat.getMod(), mat, 4.0F);
                    swords.put(mat, sword);
                    
                    final ItemDeltaHoe hoe = new ItemDeltaHoe(mat.getMod(), mat);
                    hoes.put(mat, hoe);
               }
               if (mat.armorInfo != null)
               {
                    final ItemDeltaArmor helmet = new ItemDeltaArmor(mat.getMod(), mat, 0);
                    helmets.put(mat, helmet);
               }
          }
     }
     
     public ModDelta                                   mod;
     public String                                     name;
     private boolean                                   checkUnlocalized = true;
     
     
     
     
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
          this.maxStackSize = 64;
          this.setCreativeTab(CreativeTabs.tabAllSearch);
          
          this.checkUnlocalized = checkLoc;
          
          // ItemDelta code
          this.mod = mod;
          this.name = name;
          final String unlocalized = mod.id().toLowerCase() + ":" + name;
          this.setUnlocalizedName(unlocalized);
          
          if (this.checkUnlocalized && !StatCollector.func_94522_b("item." + unlocalized + ".name"))
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
          final String s = this.name.replace(".", "_");
          
          this.itemIcon = this.doRegister(s, register);
     }
     
     
     
     
     protected Icon doRegister (final String s, final IconRegister register)
     {
          return doRegister(this.mod.id(), s, register);
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
          return this.mod.id();
     }
}
