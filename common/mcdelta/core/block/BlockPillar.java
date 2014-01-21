package mcdelta.core.block;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockPillar extends BlockSided
{
     public BlockPillar (String s, Material mat)
     {
          this(DeltaCore.instance, s, mat);
     }
     
     
     
     
     public BlockPillar (ModDelta mod, String name, Material mat)
     {
          super(mod, name, mat);
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          blockIcon = doRegister(name + "_top", register);
          frontIcon = doRegister(name + "_top", register);
          sideIcon = doRegister(name + "_side", register);
     }
     
     
     
     
     @Override
     public void onBlockPlacedBy (World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
     {
     }
     
     
     
     
     @Override
     public int onBlockPlaced (World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
     {
          switch (side)
          {
               case 0:
               case 1:
                    meta = 0;
                    break;
               case 2:
               case 3:
                    meta = 2;
                    break;
               case 4:
               case 5:
                    meta = 4;
          }
          
          return meta;
     }
}
