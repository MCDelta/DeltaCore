package mcdelta.core.assets.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class BlockData
{
    public Block block;
    public int meta;
    public TileEntity tile;

    public BlockData(final Block block)
    {
        this(block, 0, null);
    }

    public BlockData(final Block block, final int meta)
    {
        this(block, meta, null);
    }

    public BlockData(final Block block, final int meta, final TileEntity tile)
    {
        this.block = block;
        this.meta = meta;
        this.tile = tile;
    }

    @Override
    public String toString()
    {
        return "BlockData [ block:" + block.getUnlocalizedName() + ", metadata:" + meta + ", tile:" + tile + " ]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + (block == null ? 0 : block.hashCode());
        result = (prime * result) + meta;
        result = (prime * result) + (tile == null ? 0 : tile.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof BlockData))
        {
            return false;
        }
        final BlockData other = (BlockData) obj;

        if (block == null)
        {
            if (other.block != null)
            {
                return false;
            }
        } else if (!block.equals(other.block))
        {
            return false;
        }
        if (meta != other.meta)
        {
            return false;
        }
        if (tile == null)
        {
            if (other.tile != null)
            {
                return false;
            }
        } else if (!tile.equals(other.tile))
        {
            return false;
        }
        return true;
    }
}
