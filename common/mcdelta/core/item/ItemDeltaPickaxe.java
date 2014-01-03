package mcdelta.core.item;

import mcdelta.core.ModDelta;
import mcdelta.core.material.ItemMaterial;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemPickaxe;

public class ItemDeltaPickaxe extends ItemDeltaTool
{
     
     public ItemDeltaPickaxe (final ModDelta mod, final ItemMaterial mat)
     {
          super(mod, "pickaxe", mat, ItemPickaxe.blocksEffectiveAgainst, 2.0F);
     }
     
     
     
     
     @Override
     public boolean canHarvestBlock (final Block block)
     {
          return block == Block.obsidian ? this.itemMaterial.getHarvestLevel() == 3 : block != Block.blockDiamond && block != Block.oreDiamond ? block != Block.oreEmerald && block != Block.blockEmerald ? block != Block.blockGold && block != Block.oreGold ? block != Block.blockIron && block != Block.oreIron ? block != Block.blockLapis && block != Block.oreLapis ? block != Block.oreRedstone && block != Block.oreRedstoneGlowing ? block.blockMaterial == Material.rock ? true : block.blockMaterial == Material.iron ? true : block.blockMaterial == Material.anvil : this.itemMaterial.getHarvestLevel() >= 2 : this.itemMaterial.getHarvestLevel() >= 1 : this.itemMaterial.getHarvestLevel() >= 1 : this.itemMaterial.getHarvestLevel() >= 2 : this.itemMaterial.getHarvestLevel() >= 2 : this.itemMaterial.getHarvestLevel() >= 2;
     }
}
