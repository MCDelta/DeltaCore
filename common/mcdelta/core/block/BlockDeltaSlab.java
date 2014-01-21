package mcdelta.core.block;

import java.util.List;
import java.util.Random;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.assets.Assets;
import mcdelta.core.block.item.ItemDeltaSlab;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


public class BlockDeltaSlab extends BlockDelta
{
     protected final boolean isDoubleSlab;
     
     @SideOnly (Side.CLIENT)
     private Icon            sideIcon;
     
     private boolean         useSideIcon;
     
     
     
     
     public BlockDeltaSlab (String s, Material mat, boolean b)
     {
          this(DeltaCore.instance, s, mat, b);
     }
     
     
     
     
     public BlockDeltaSlab (ModDelta mod, String s, Material mat, boolean b)
     {
          super(mod, s + ".slab" + (b ? ".double" : ""), mat);
          isDoubleSlab = b;
          name = s;
          
          if (b)
          {
               opaqueCubeLookup[blockID] = true;
          }
          else
          {
               setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
          }
          
          setLightOpacity(255);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (IconRegister register)
     {
          super.registerIcons(register);
          
          String s = name.replace(".", "_");
          useSideIcon = Assets.resourceExists(new ResourceLocation(mod.id().toLowerCase(), "textures/blocks/" + s + "_slab_side.png"));
          
          if (useSideIcon)
          {
               sideIcon = doRegister(s + "_slab_side", register);
          }
          
          else
          {
               sideIcon = doRegister(s, register);
          }
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public Icon getIcon (int side, int meta)
     {
          int k = meta & 7;
          
          if (isDoubleSlab && (meta & 8) != 0)
          {
               side = 1;
          }
          
          return k == 0 ? side != 1 && side != 0 ? sideIcon : blockIcon : k == 1 ? Block.sandStone.getBlockTextureFromSide(side) : k == 2 ? Block.planks.getBlockTextureFromSide(side) : k == 3 ? Block.cobblestone.getBlockTextureFromSide(side) : k == 4 ? Block.brick.getBlockTextureFromSide(side) : k == 5 ? Block.stoneBrick.getIcon(side, 0) : k == 6 ? Block.netherBrick.getBlockTextureFromSide(1) : k == 7 ? Block.blockNetherQuartz.getBlockTextureFromSide(side) : blockIcon;
     }
     
     
     
     
     @Override
     public void setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z)
     {
          if (isDoubleSlab)
          {
               setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
          }
          else
          {
               boolean flag = (world.getBlockMetadata(x, y, z) & 8) != 0;
               
               if (flag)
               {
                    setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
               }
               else
               {
                    setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
               }
          }
     }
     
     
     
     
     @Override
     public void setBlockBoundsForItemRender ()
     {
          if (isDoubleSlab)
          {
               setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
          }
          else
          {
               setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
          }
     }
     
     
     
     
     @Override
     public void addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
     {
          setBlockBoundsBasedOnState(world, x, y, z);
          super.addCollisionBoxesToList(world, x, y, z, par5AxisAlignedBB, par6List, par7Entity);
     }
     
     
     
     
     @Override
     public boolean isOpaqueCube ()
     {
          return isDoubleSlab;
     }
     
     
     
     
     @Override
     public int onBlockPlaced (World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
     {
          return isDoubleSlab ? meta : side != 0 && (side == 1 || hitY <= 0.5D) ? meta : meta | 8;
     }
     
     
     
     
     @Override
     public int quantityDropped (Random rand)
     {
          return isDoubleSlab ? 2 : 1;
     }
     
     
     
     
     @Override
     public boolean renderAsNormalBlock ()
     {
          return isDoubleSlab;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean shouldSideBeRendered (IBlockAccess world, int x, int y, int z, int side)
     {
          if (isDoubleSlab)
          {
               return super.shouldSideBeRendered(world, x, y, z, side);
          }
          else if (side != 1 && side != 0 && !super.shouldSideBeRendered(world, x, y, z, side))
          {
               return false;
          }
          else
          {
               int i1 = x + Facing.offsetsXForSide[Facing.oppositeSide[side]];
               int j1 = y + Facing.offsetsYForSide[Facing.oppositeSide[side]];
               int k1 = z + Facing.offsetsZForSide[Facing.oppositeSide[side]];
               boolean flag = (world.getBlockMetadata(i1, j1, k1) & 8) != 0;
               return flag ? side == 0 ? true : side == 1 && super.shouldSideBeRendered(world, x, y, z, side) ? true : !isBlockSingleSlab(world.getBlockId(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) == 0 : side == 1 ? true : side == 0 && super.shouldSideBeRendered(world, x, y, z, side) ? true : !isBlockSingleSlab(world.getBlockId(x, y, z)) || (world.getBlockMetadata(x, y, z) & 8) != 0;
          }
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     private static boolean isBlockSingleSlab (int par0)
     {
          return par0 == Block.stoneSingleSlab.blockID || par0 == Block.woodSingleSlab.blockID;
     }
     
     
     
     
     @Override
     public int getDamageValue (World world, int x, int y, int z)
     {
          return super.getDamageValue(world, x, y, z) & 7;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public int idPicked (World world, int x, int y, int z)
     {
          return isDoubleSlab ? ((ItemDeltaSlab) Item.itemsList[blockID]).theHalfSlab.blockID : blockID;
     }
     
     
     
     
     @Override
     public int idDropped (int par1, Random rand, int par3)
     {
          return isDoubleSlab ? ((ItemDeltaSlab) Item.itemsList[blockID]).theHalfSlab.blockID : blockID;
     }
}
