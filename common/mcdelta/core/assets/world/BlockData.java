package mcdelta.core.assets.world;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class BlockData
{
    public Block block;
    public int meta;
    public TileEntity tile;

    public BlockData(Block b)
    {
        this(b, 0, null);
    }

    public BlockData(Block b, int i)
    {
        this(b, i, null);
    }

    public BlockData(Block b, int i, TileEntity tileEntity)
    {
        block = b;
        meta = i;
        tile = tileEntity;
    }

    @Override
    public String toString()
    {
        return "BlockData [ block:" + block.getUnlocalizedName() + ", metadata:" + meta + ", tile:" + tile + " ]";
    }

    public boolean equals(BlockData data)
    {
        if ((data.block == block) && (data.meta == meta))
        {
            return true;
        }

        return false;
    }
}
