package mcdelta.core.block;

import mcdelta.core.DeltaMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class BlockDelta extends Block
{
     public String    name;
     private DeltaMod mod;
     public int       deltaID;
     
     
     
     
     public BlockDelta (DeltaMod deltaMod, int i, String s, Material mat)
     {
          super(deltaMod.getConfig().getBlockID(s, i), mat);
          
          this.name = s;
          this.mod = deltaMod;
          this.setUnlocalizedName(mod.getModid() + ":" + s);
          this.setCreativeTab(CreativeTabs.tabBlock);
          this.setHardness(0.5F);
          this.deltaID = i;
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister iconRegister)
     {
          String s = this.name.replace(".", "_");
          
          this.blockIcon = iconRegister.registerIcon(mod.getModid() + ":" + s);
     }
     
     
     
     
     public DeltaMod getMod ()
     {
          return mod;
     }
     
     
     
     
     protected void setBlockBounds (float[] shape)
     {
          this.setBlockBounds(shape[0], shape[1], shape[2], shape[3], shape[4], shape[5]);
     }
}
