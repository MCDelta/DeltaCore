package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
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
     private final ItemMaterial itemMaterial;
     
     @SideOnly (Side.CLIENT)
     protected Icon             itemOverlay;
     
     @SideOnly (Side.CLIENT)
     protected Icon             overrideIcon;
     
     private boolean            overrideExists = false;
     
     
     
     
     public ItemDeltaHoe (final ModDelta mod, final ItemMaterial mat)
     {
          super(mod, mat.name() + ".hoe");
          itemMaterial = mat;
          maxStackSize = 1;
          setMaxDamage(mat.maxUses());
          setCreativeTab(CreativeTabs.tabTools);
          
          ClientProxy.extraPasses.add(this);
     }
     
     
     
     
     @Override
     public void registerIcons (final IconRegister register)
     {
          itemIcon = doRegister("deltacore", "hoe" + "_1", register);
          itemOverlay = doRegister("deltacore", "hoe" + "_2", register);
          
          overrideExists = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/items/override/" + itemMaterial.name().toLowerCase() + "_hoe.png"));
          
          if (overrideExists)
          {
               overrideIcon = this.doRegister("/override/" + itemMaterial.name().toLowerCase() + "_hoe", register);
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          if (overrideExists)
          {
               return 1;
          }
          return 2;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
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
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          if (overrideExists)
          {
               return 0xffffff;
          }
          if (pass == 2)
          {
               return MaterialRegistry.WOOD.color();
          }
          return itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          if (pass == 1 && itemMaterial.defaultShiny())
          {
               return true;
          }
          return false;
     }
     
     
     
     
     @Override
     public boolean onItemUse (final ItemStack stack, final EntityPlayer player, final World world, final int x, final int y, final int z, final int side, final float hitX, final float hitY, final float hitZ)
     {
          if (!player.canPlayerEdit(x, y, z, side, stack))
          {
               return false;
          }
          final UseHoeEvent event = new UseHoeEvent(player, stack, world, x, y, z);
          if (MinecraftForge.EVENT_BUS.post(event))
          {
               return false;
          }
          if (event.getResult() == Result.ALLOW)
          {
               stack.damageItem(1, player);
               return true;
          }
          final int i1 = world.getBlockId(x, y, z);
          final boolean air = world.isAirBlock(x, y + 1, z);
          
          if (side != 0 && air && (i1 == Block.grass.blockID || i1 == Block.dirt.blockID))
          {
               final Block block = Block.tilledField;
               world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, block.stepSound.getStepSound(), (block.stepSound.getVolume() + 1.0F) / 2.0F, block.stepSound.getPitch() * 0.8F);
               
               if (world.isRemote)
               {
                    return true;
               }
               world.setBlock(x, y, z, block.blockID);
               stack.damageItem(1, player);
               return true;
          }
          return false;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean isFull3D ()
     {
          return true;
     }
     
     
     
     
     public String getMaterialName ()
     {
          return itemMaterial.name();
     }
}
