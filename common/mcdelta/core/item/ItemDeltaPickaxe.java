package mcdelta.core.item;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.material.ToolMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemPickaxe;

public class ItemDeltaPickaxe extends ItemDeltaTool
{

    public ItemDeltaPickaxe(EnumMCDMods m, ToolMaterial mat)
    {
        super(m, "pickaxe", mat, ItemPickaxe.blocksEffectiveAgainst, 2.0F);
    }

    @Override
    public boolean canHarvestBlock(Block block)
    {
        return block == Block.obsidian ? toolMaterial.getHarvestLevel() == 3
                : ((block != Block.blockDiamond) && (block != Block.oreDiamond) ? ((block != Block.oreEmerald) && (block != Block.blockEmerald) ? ((block != Block.blockGold)
                        && (block != Block.oreGold) ? ((block != Block.blockIron) && (block != Block.oreIron) ? ((block != Block.blockLapis) && (block != Block.oreLapis) ? ((block != Block.oreRedstone)
                        && (block != Block.oreRedstoneGlowing) ? (block.blockMaterial == Material.rock ? true : (block.blockMaterial == Material.iron ? true
                        : block.blockMaterial == Material.anvil)) : toolMaterial.getHarvestLevel() >= 2) : toolMaterial.getHarvestLevel() >= 1)
                        : toolMaterial.getHarvestLevel() >= 1)
                        : toolMaterial.getHarvestLevel() >= 2)
                        : toolMaterial.getHarvestLevel() >= 2)
                        : toolMaterial.getHarvestLevel() >= 2);
    }
}
