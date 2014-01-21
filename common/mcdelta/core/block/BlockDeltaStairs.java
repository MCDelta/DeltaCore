package mcdelta.core.block;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockDeltaStairs extends BlockStairs
{
     public ModDelta mod;
     public String   name;
     
     
     
     
     public BlockDeltaStairs (String s, Block block, int meta)
     {
          this(DeltaCore.instance, s, block, meta);
     }
     
     
     
     
     public BlockDeltaStairs (ModDelta m, String s, Block block, int meta)
     {
          super(m.config().getBlockID(s), block, meta);
          
          mod = m;
          name = s;
          String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          
          GameRegistry.registerBlock(this, name);
          
          if (!StatCollector.func_94522_b("tile." + unlocalized + ".name"))
          {
               DeltaCore.localizationWarnings.append("- tile." + unlocalized + ".name \n");
          }
     }
     
     
     
     
     public BlockDeltaStairs setHarvestLevel (String s, int i)
     {
          MinecraftForge.setBlockHarvestLevel(this, s, i);
          return this;
     }
     
     
     
     
     public BlockDeltaStairs setOre (String s)
     {
          OreDictionary.registerOre(s, this);
          return this;
     }
     
     
     
     
     public BlockDeltaStairs setBlockItem (ItemBlock item)
     {
          Item.itemsList[blockID] = item;
          return this;
     }
     
     
     
     
     @Override
     public BlockDeltaStairs setCreativeTab (CreativeTabs tab)
     {
          super.setCreativeTab(tab);
          return this;
     }
     
     
     
     
     public BlockDeltaStairs setProperties (Properties prop)
     {
          prop.setProperties(this);
          return this;
     }
}
