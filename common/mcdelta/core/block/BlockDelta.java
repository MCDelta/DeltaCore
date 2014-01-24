package mcdelta.core.block;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.block.item.ItemBlockMeta;
import mcdelta.core.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockDelta extends Block
{
     public static Map<String, Class<? extends ItemBlock>> items = new HashMap<String, Class<? extends ItemBlock>>();
     public ModDelta                                       mod;
     public String                                         name;
     
     
     
     
     public BlockDelta (String s, Material mat)
     {
          this(DeltaCore.instance, s, mat);
     }
     
     
     
     
     public BlockDelta (ModDelta mod, String name, Material mat)
     {
          super(mod.config().getBlockID(name), mat);
          
          this.mod = mod;
          this.name = name;
          String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          
          if (this instanceof IMetadata)
          {
               GameRegistry.registerBlock(this, ItemBlockMeta.class, name);
          }
          
          else if (items.containsKey(name))
          {
               GameRegistry.registerBlock(this, items.get(name), name);
          }
          
          else
          {
               GameRegistry.registerBlock(this, name);
          }
          
          if (!StatCollector.func_94522_b("tile." + unlocalized + ".name"))
          {
               DeltaCore.localizationWarnings.append("- tile." + unlocalized + ".name \n");
          }
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          String s = name.replace(".", "_");
          
          blockIcon = doRegister(s, register);
     }
     
     
     
     
     protected Icon doRegister (String s, IconRegister register)
     {
          ResourceLocation loc = new ResourceLocation(mod.id().toLowerCase(), "textures/blocks/" + s + ".png");
          
          if (Assets.resourceExists(loc))
          {
               return register.registerIcon(mod.id() + ":" + s);
          }
          
          Logger.severe("missing icon! " + loc);
          return register.registerIcon(DeltaCore.MOD_ID + ":null");
     }
     
     
     
     
     protected void setBlockBounds (float[] shape)
     {
          this.setBlockBounds(shape[0], shape[1], shape[2], shape[3], shape[4], shape[5]);
     }
     
     
     
     
     public String getid ()
     {
          return mod.id();
     }
     
     
     
     
     public BlockDelta setHarvestLevel (String s, int i)
     {
          MinecraftForge.setBlockHarvestLevel(this, s, i);
          return this;
     }
     
     
     
     
     public BlockDelta setOre (String s)
     {
          OreDictionary.registerOre(s, this);
          return this;
     }
     
     
     
     
     public void setBlockItem (ItemBlock item)
     {
          Item.itemsList[blockID] = item;
     }
     
     
     
     
     @Override
     public BlockDelta setCreativeTab (CreativeTabs tab)
     {
          super.setCreativeTab(tab);
          return this;
     }
     
     
     
     
     public Block setProperties (Properties prop)
     {
          prop.setProperties(this);
          return this;
     }
}
