package mcdelta.core.item;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ToolMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.entity.player.UseHoeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaHoe extends ItemDelta implements IExtraPasses
{
     private ToolMaterial toolMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon       itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon       overrideIcon;
     
     private boolean      overrideExists = false;
     
     
     
     
     public ItemDeltaHoe (EnumMCDMods m, ToolMaterial mat)
     {
          super(m, mat.getName() + ".hoe");
          this.toolMaterial = mat;
          this.maxStackSize = 1;
          this.setMaxDamage(mat.getMaxUses());
          this.setCreativeTab(CreativeTabs.tabTools);
          
          ClientProxy.extraPasses.add(this);
     }
     
     
     
     
     @Override
     public void registerIcons (IconRegister register)
     {
          this.itemIcon = doRegister("deltacore", "hoe" + "_1", register);
          this.itemOverlay = doRegister("deltacore", "hoe" + "_2", register);
          
          overrideExists = Assets.rescourceExists(new ResourceLocation(mod.modid.toLowerCase(), "textures/items/override/" + toolMaterial.getName().toLowerCase() + "_hoe.png"));
          
          if (overrideExists)
          {
               this.overrideIcon = doRegister("/override/" + toolMaterial.getName().toLowerCase() + "_hoe", register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (ItemStack stack)
     {
          if (overrideExists)
          {
               return 1;
          }
          
          return 2;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (ItemStack stack, int pass)
     {
          if (overrideExists)
          {
               return overrideIcon;
          }
          
          if (pass == 2)
          {
               return itemOverlay;
          }
          
          return itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (ItemStack stack, int pass)
     {
          if (overrideExists)
          {
               return 0xffffff;
          }
          
          if (pass == 2)
          {
               return ToolMaterial.WOOD.getColor();
          }
          
          return toolMaterial.getColor();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (ItemStack stack, int pass)
     {
          if(pass == 1 && toolMaterial.isShinyDefault())
          {
               return true;
          }
          
          return false;
     }
     
     
     
     
     public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
     {
          if (!player.canPlayerEdit(x, y, z, side, stack))
          {
               return false;
          }
          else
          {
               UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
               if (MinecraftForge.EVENT_BUS.post(event))
               {
                    return false;
               }
               
               if (event.getResult() == Result.ALLOW)
               {
                    stack.damageItem(1, player);
                    return true;
               }
               
               int i1 = world.getBlockId(x, y, z);
               boolean air = world.isAirBlock(x, y + 1, z);
               
               if (side != 0 && air && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID))
               {
                    Block block = Block.tilledField;
                    world.playSoundEffect((double) ((float) x + 0.5F), (double) ((float) y + 0.5F), (double) ((float) z + 0.5F), block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
                    
                    if (world.isRemote)
                    {
                         return true;
                    }
                    else
                    {
                         world.setBlock(x, y, z, block.blockID);
                         stack.damageItem(1, player);
                         return true;
                    }
               }
               else
               {
                    return false;
               }
          }
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public boolean isFull3D ()
     {
          return true;
     }
     
     
     
     
     public String getMaterialName ()
     {
          return this.toolMaterial.getName();
     }
}
