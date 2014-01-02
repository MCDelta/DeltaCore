package mcdelta.core.block;

import mcdelta.core.DeltaMod;
import mcdelta.core.assets.Assets;
import mcdelta.core.client.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.dispenser.PositionImpl;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSided extends BlockDelta
{
     @SideOnly (Side.CLIENT)
     protected Icon frontIcon;
     
     @SideOnly (Side.CLIENT)
     protected Icon sideIcon;
     
     
     
     
     public BlockSided (DeltaMod deltaMod, int i, String s, Material mat)
     {
          super(deltaMod, i, s, mat);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void registerIcons (IconRegister register)
     {
          this.blockIcon = register.registerIcon(getMod().getModid() + ":" + this.name + "_back");
          this.frontIcon = register.registerIcon(getMod().getModid() + ":" + this.name + "_front");
          this.sideIcon = register.registerIcon(getMod().getModid() + ":" + this.name + "_side");
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public Icon getIcon (int side, int meta)
     {
          return meta == 0 ? side == 0 ? this.frontIcon : side == 1 ? this.blockIcon : this.sideIcon : meta == 1 ? side == 1 ? this.frontIcon : side == 0 ? this.blockIcon : this.sideIcon : meta > 1 && meta % 2 == 0 ? side == meta ? this.frontIcon : side == meta + 1 ? this.blockIcon : this.sideIcon : meta > 1 && !(meta % 2 == 0) ? side == meta ? this.frontIcon : side == meta - 1 ? this.blockIcon : this.sideIcon : this.sideIcon;
     }
     
     
     
     
     public void onBlockAdded (World world, int x, int y, int z)
     {
          super.onBlockAdded(world, x, y, z);
     }
     
     
     
     
     private void setDefaultDirection (World world, int x, int y, int z)
     {
          if (!world.isRemote)
          {
               int l = world.getBlockId(x, y, z - 1);
               int i1 = world.getBlockId(x, y, z + 1);
               int j1 = world.getBlockId(x - 1, y, z);
               int k1 = world.getBlockId(x + 1, y, z);
               byte b0 = 3;
               
               if (Block.opaqueCubeLookup[l] && !Block.opaqueCubeLookup[i1])
               {
                    b0 = 3;
               }
               
               if (Block.opaqueCubeLookup[i1] && !Block.opaqueCubeLookup[l])
               {
                    b0 = 2;
               }
               
               if (Block.opaqueCubeLookup[j1] && !Block.opaqueCubeLookup[k1])
               {
                    b0 = 5;
               }
               
               if (Block.opaqueCubeLookup[k1] && !Block.opaqueCubeLookup[j1])
               {
                    b0 = 4;
               }
               
               world.setBlockMetadataWithNotify(x, y, z, b0, 2);
          }
     }
     
     
     
     
     public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ)
     {
          return false;
     }
     
     
     
     
     public void onNeighborBlockChange (World world, int x, int y, int z, int neighborID)
     {
          boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
          int i1 = world.getBlockMetadata(x, y, z);
          boolean flag1 = (i1 & 8) != 0;
          
          if (flag && !flag1)
          {
               world.scheduleBlockUpdate(x, y, z, this.blockID, this.tickRate(world));
               world.setBlockMetadataWithNotify(x, y, z, i1 | 8, 4);
          }
          else if (!flag && flag1)
          {
               world.setBlockMetadataWithNotify(x, y, z, i1 & -9, 4);
          }
     }
     
     
     
     
     @Override
     public void onBlockPlacedBy (World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
     {
          super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);
          
          int l = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
          world.setBlockMetadataWithNotify(x, y, z, l, 2);
          
          if (stack.hasDisplayName())
          {
               ((TileEntityDispenser) world.getBlockTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
          }
     }
     
     
     
     
     public static IPosition getIPositionFromBlockSource (IBlockSource blockSource)
     {
          EnumFacing enumfacing = getFacing(blockSource.getBlockMetadata());
          double d0 = blockSource.getX() + 0.7D * (double) enumfacing.getFrontOffsetX();
          double d1 = blockSource.getY() + 0.7D * (double) enumfacing.getFrontOffsetY();
          double d2 = blockSource.getZ() + 0.7D * (double) enumfacing.getFrontOffsetZ();
          return new PositionImpl(d0, d1, d2);
     }
     
     
     
     
     public static EnumFacing getFacing (int par0)
     {
          return EnumFacing.getFront(par0 & 7);
     }
     
     
     
     
     @Override
     public int getRenderType ()
     {
          return ClientProxy.typeBlockSided;
     }
     
}
