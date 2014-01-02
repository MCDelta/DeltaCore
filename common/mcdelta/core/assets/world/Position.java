package mcdelta.core.assets.world;

import java.util.logging.Level;

import mcdelta.core.assets.Assets;
import mcdelta.core.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class Position
{
    public IBlockAccess world;
    public int x;
    public int y;
    public int z;
    public int dimensionID;

    public Position(IBlockAccess world, int x, int y, int z)
    {
        this(world, x, y, z, 0);
    }

    public Position(IBlockAccess world, int x, int y, int z, int dimID)
    {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        dimensionID = dimID;
    }

    public int getMeta()
    {
        return world.getBlockMetadata(x, y, z);
    }

    public Position move(EnumFacing facing)
    {
        return new Position(world, x + facing.getFrontOffsetX(), y + facing.getFrontOffsetY(), z + facing.getFrontOffsetZ(), dimensionID);
    }

    public Position move(int i, int j, int k)
    {
        return new Position(world, x + i, y + j, z + k, dimensionID);
    }

    public BlockData getBlockData()
    {
        if (Assets.isAirBlock(this))
        {
            return null;
        }
        return new BlockData(getBlock(), getMeta(), getTile());
    }

    public Block getBlock()
    {
        return Block.blocksList[world.getBlockId(x, y, z)];
    }

    public TileEntity getTile()
    {
        return world.getBlockTileEntity(x, y, z);
    }

    @Override
    public String toString()
    {
        return "Position [ x:" + x + ", y:" + y + ", z:" + z + ", dimension:" + dimensionID + " ]";
    }

    public Position copy()
    {
        return new Position(world, x, y, z, dimensionID);
    }

    public boolean isNormalCube()
    {
        if (world instanceof World)
        {
            World world = (World) this.world;

            return getBlock().isBlockNormalCube(world, x, y, z);
        }
        Logger.log(Level.SEVERE, "[ isNormalCube() ]" + Assets.worldAccessError, this);

        return false;
    }
}