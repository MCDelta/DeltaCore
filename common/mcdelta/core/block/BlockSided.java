package mcdelta.core.block;

import mcdelta.core.ModDelta;
import mcdelta.core.proxy.ClientProxy;
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
    @SideOnly(Side.CLIENT)
    protected Icon frontIcon;

    @SideOnly(Side.CLIENT)
    protected Icon sideIcon;

    public BlockSided(String s, Material mat)
    {
        super(s, mat);
    }

    public BlockSided(ModDelta m, String s, Material mat)
    {
        super(m, s, mat);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        blockIcon = doRegister(name + "_back", register);
        frontIcon = doRegister(name + "_front", register);
        sideIcon = doRegister(name + "_side", register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return meta == 0 ? side == 0 ? frontIcon : side == 1 ? blockIcon : sideIcon : meta == 1 ? side == 1 ? frontIcon : side == 0 ? blockIcon : sideIcon : (meta > 1)
                && ((meta % 2) == 0) ? side == meta ? frontIcon : side == (meta + 1) ? blockIcon : sideIcon : (meta > 1) && !((meta % 2) == 0) ? side == meta ? frontIcon
                : side == (meta - 1) ? blockIcon : sideIcon : sideIcon;
    }

    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        super.onBlockAdded(world, x, y, z);
    }

    private void setDefaultDirection(World world, int x, int y, int z)
    {
        if (!world.isRemote)
        {
            int zMin = world.getBlockId(x, y, z - 1);
            int zPlus = world.getBlockId(x, y, z + 1);
            int xMin = world.getBlockId(x - 1, y, z);
            int xPlus = world.getBlockId(x + 1, y, z);
            byte meta = 3;

            if (Block.opaqueCubeLookup[zMin] && !Block.opaqueCubeLookup[zPlus])
            {
                meta = 3;
            }

            if (Block.opaqueCubeLookup[zPlus] && !Block.opaqueCubeLookup[zMin])
            {
                meta = 2;
            }

            if (Block.opaqueCubeLookup[xMin] && !Block.opaqueCubeLookup[xPlus])
            {
                meta = 5;
            }

            if (Block.opaqueCubeLookup[xPlus] && !Block.opaqueCubeLookup[xMin])
            {
                meta = 4;
            }

            world.setBlockMetadataWithNotify(x, y, z, meta, 2);
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ)
    {
        return false;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
    {
        boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
        int i1 = world.getBlockMetadata(x, y, z);
        boolean flag1 = (i1 & 8) != 0;

        if (flag && !flag1)
        {
            world.scheduleBlockUpdate(x, y, z, blockID, tickRate(world));
            world.setBlockMetadataWithNotify(x, y, z, i1 | 8, 4);
        } else if (!flag && flag1)
        {
            world.setBlockMetadataWithNotify(x, y, z, i1 & -9, 4);
        }
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityLiving, ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);

        int l = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        if (stack.hasDisplayName())
        {
            ((TileEntityDispenser) world.getBlockTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
        }
    }

    public static IPosition getIPositionFromBlockSource(IBlockSource blockSource)
    {
        EnumFacing facing = getFacing(blockSource.getBlockMetadata());
        double x = blockSource.getX() + (0.7D * facing.getFrontOffsetX());
        double y = blockSource.getY() + (0.7D * facing.getFrontOffsetY());
        double z = blockSource.getZ() + (0.7D * facing.getFrontOffsetZ());
        return new PositionImpl(x, y, z);
    }

    public static EnumFacing getFacing(int par0)
    {
        return EnumFacing.getFront(par0 & 7);
    }

    @Override
    public int getRenderType()
    {
        return ClientProxy.typeBlockSided;
    }
}