package mcdelta.core.block;

import mcdelta.core.ModDelta;
import mcdelta.core.proxy.ClientProxy;
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

    public BlockSided(final String name, final Material mat)
    {
        super(name, mat);
    }

    public BlockSided(final ModDelta mod, final String name, final Material mat)
    {
        super(mod, name, mat);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(final IconRegister register)
    {
        blockIcon = doRegister(name + "_back", register);
        frontIcon = doRegister(name + "_front", register);
        sideIcon = doRegister(name + "_side", register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(final int side, final int meta)
    {
        return meta == 0 ? side == 0 ? frontIcon : side == 1 ? blockIcon : sideIcon : meta == 1 ? side == 1 ? frontIcon : side == 0 ? blockIcon : sideIcon : (meta > 1)
                && ((meta % 2) == 0) ? side == meta ? frontIcon : side == (meta + 1) ? blockIcon : sideIcon : (meta > 1) && !((meta % 2) == 0) ? side == meta ? frontIcon
                : side == (meta - 1) ? blockIcon : sideIcon : sideIcon;
    }

    @Override
    public void onBlockAdded(final World world, final int x, final int y, final int z)
    {
        super.onBlockAdded(world, x, y, z);
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float offsetX, final float offsetY,
            final float offsetZ)
    {
        return false;
    }

    @Override
    public void onNeighborBlockChange(final World world, final int x, final int y, final int z, final int neighborID)
    {
        final boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z) || world.isBlockIndirectlyGettingPowered(x, y + 1, z);
        final int i1 = world.getBlockMetadata(x, y, z);
        final boolean flag1 = (i1 & 8) != 0;

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
    public void onBlockPlacedBy(final World world, final int x, final int y, final int z, final EntityLivingBase entityLiving, final ItemStack stack)
    {
        super.onBlockPlacedBy(world, x, y, z, entityLiving, stack);

        final int l = BlockPistonBase.determineOrientation(world, x, y, z, entityLiving);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);

        if (stack.hasDisplayName())
        {
            ((TileEntityDispenser) world.getBlockTileEntity(x, y, z)).setCustomName(stack.getDisplayName());
        }
    }

    public static IPosition getIPositionFromBlockSource(final IBlockSource blockSource)
    {
        final EnumFacing facing = getFacing(blockSource.getBlockMetadata());
        final double x = blockSource.getX() + (0.7D * facing.getFrontOffsetX());
        final double y = blockSource.getY() + (0.7D * facing.getFrontOffsetY());
        final double z = blockSource.getZ() + (0.7D * facing.getFrontOffsetZ());
        return new PositionImpl(x, y, z);
    }

    public static EnumFacing getFacing(final int par0)
    {
        return EnumFacing.getFront(par0 & 7);
    }

    @Override
    public int getRenderType()
    {
        return ClientProxy.typeBlockSided;
    }
}
