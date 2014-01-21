package mcdelta.core.block.item;

import mcdelta.core.block.BlockDeltaSlab;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaSlab extends ItemBlock
{
     public boolean        isFullBlock;
     public BlockDeltaSlab theHalfSlab;
     public BlockDeltaSlab doubleSlab;
     
     
     
     
     public ItemDeltaSlab (BlockDeltaSlab half, BlockDeltaSlab thedouble, boolean b)
     {
          super((b ? thedouble.blockID : half.blockID) - 256);
          theHalfSlab = half;
          doubleSlab = thedouble;
          isFullBlock = b;
          setMaxDamage(0);
          setHasSubtypes(true);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public Icon getIconFromDamage (int meta)
     {
          return Block.blocksList[itemID].getIcon(2, meta);
     }
     
     
     
     
     @Override
     public int getMetadata (int meta)
     {
          return meta;
     }
     
     
     
     
     @Override
     public String getUnlocalizedName (ItemStack stack)
     {
          return "tile.kinetiks:" + theHalfSlab.name + ".slab";
     }
     
     
     
     
     @Override
     public boolean onItemUse (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta, float par8, float par9, float par10)
     {
          if (isFullBlock)
          {
               return super.onItemUse(stack, player, world, x, y, z, meta, par8, par9, par10);
          }
          else if (stack.stackSize == 0)
          {
               return false;
          }
          else if (!player.canPlayerEdit(x, y, z, meta, stack))
          {
               return false;
          }
          else
          {
               int i1 = world.getBlockId(x, y, z);
               int j1 = world.getBlockMetadata(x, y, z);
               int k1 = j1 & 7;
               boolean flag = (j1 & 8) != 0;
               
               if ((meta == 1 && !flag || meta == 0 && flag) && i1 == theHalfSlab.blockID && k1 == stack.getItemDamage())
               {
                    if (world.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, doubleSlab.blockID, k1, 3))
                    {
                         world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, doubleSlab.stepSound.getPlaceSound(), (doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlab.stepSound.getPitch() * 0.8F);
                         --stack.stackSize;
                    }
                    
                    return true;
               }
               else
               {
                    return func_77888_a(stack, player, world, x, y, z, meta) ? true : super.onItemUse(stack, player, world, x, y, z, meta, par8, par9, par10);
               }
          }
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean canPlaceItemBlockOnSide (World par1World, int par2, int par3, int x, int y, EntityPlayer zEntityPlayer, ItemStack metaItemStack)
     {
          int i1 = par2;
          int j1 = par3;
          int k1 = x;
          int l1 = par1World.getBlockId(par2, par3, x);
          int i2 = par1World.getBlockMetadata(par2, par3, x);
          int j2 = i2 & 7;
          boolean flag = (i2 & 8) != 0;
          
          if ((y == 1 && !flag || y == 0 && flag) && l1 == theHalfSlab.blockID && j2 == metaItemStack.getItemDamage())
          {
               return true;
          }
          else
          {
               if (y == 0)
               {
                    --par3;
               }
               
               if (y == 1)
               {
                    ++par3;
               }
               
               if (y == 2)
               {
                    --x;
               }
               
               if (y == 3)
               {
                    ++x;
               }
               
               if (y == 4)
               {
                    --par2;
               }
               
               if (y == 5)
               {
                    ++par2;
               }
               
               l1 = par1World.getBlockId(par2, par3, x);
               i2 = par1World.getBlockMetadata(par2, par3, x);
               j2 = i2 & 7;
               flag = (i2 & 8) != 0;
               return l1 == theHalfSlab.blockID && j2 == metaItemStack.getItemDamage() ? true : super.canPlaceItemBlockOnSide(par1World, i1, j1, k1, y, zEntityPlayer, metaItemStack);
          }
     }
     
     
     
     
     private boolean func_77888_a (ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int meta)
     {
          if (meta == 0)
          {
               --y;
          }
          
          if (meta == 1)
          {
               ++y;
          }
          
          if (meta == 2)
          {
               --z;
          }
          
          if (meta == 3)
          {
               ++z;
          }
          
          if (meta == 4)
          {
               --x;
          }
          
          if (meta == 5)
          {
               ++x;
          }
          
          int i1 = world.getBlockId(x, y, z);
          int j1 = world.getBlockMetadata(x, y, z);
          int k1 = j1 & 7;
          
          if (i1 == theHalfSlab.blockID && k1 == stack.getItemDamage())
          {
               if (world.checkNoEntityCollision(doubleSlab.getCollisionBoundingBoxFromPool(world, x, y, z)) && world.setBlock(x, y, z, doubleSlab.blockID, k1, 3))
               {
                    world.playSoundEffect(x + 0.5F, y + 0.5F, z + 0.5F, doubleSlab.stepSound.getPlaceSound(), (doubleSlab.stepSound.getVolume() + 1.0F) / 2.0F, doubleSlab.stepSound.getPitch() * 0.8F);
                    --stack.stackSize;
               }
               
               return true;
          }
          else
          {
               return false;
          }
     }
}
