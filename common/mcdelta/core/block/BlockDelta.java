package mcdelta.core.block;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockDelta extends Block
{
     private final ModDelta mod;
     public String          name;
     
     
     
     
     public BlockDelta (final String s, final Material mat)
     {
          this(DeltaCore.instance, s, mat);
     }
     
     
     
     
     public BlockDelta (final ModDelta mod, final String name, final Material mat)
     {
          super(mod.config().getBlockID(name), mat);
          
          this.mod = mod;
          this.name = name;
          final String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          
          GameRegistry.registerBlock(this, name);
          
          if (!StatCollector.func_94522_b("tile." + unlocalized + ".name"))
          {
               DeltaCore.localizationWarnings.append("- tile." + unlocalized + ".name \n");
          }
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          final String s = name.replace(".", "_");
          
          blockIcon = doRegister(s, register);
     }
     
     
     
     
     protected Icon doRegister (final String s, final IconRegister register)
     {
          final ResourceLocation loc = new ResourceLocation(mod.id().toLowerCase(), "textures/blocks/" + s + ".png");
          
          if (Assets.resourceExists(loc))
          {
               return register.registerIcon(mod.id() + ":" + s);
          }
          return register.registerIcon(DeltaCore.MOD_ID + ":null");
     }
     
     
     
     
     protected void setBlockBounds (final float[] shape)
     {
          this.setBlockBounds(shape[0], shape[1], shape[2], shape[3], shape[4], shape[5]);
     }
     
     
     
     
     public String getid ()
     {
          return mod.id();
     }
}
