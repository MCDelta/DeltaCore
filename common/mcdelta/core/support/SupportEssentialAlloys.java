package mcdelta.core.support;

import java.util.AbstractMap.SimpleEntry;

import mcdelta.core.material.ArmorInfo;
import mcdelta.core.material.DeltaInfo;
import mcdelta.core.material.ExtraInfo;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.material.MaterialRegistry;
import mcdelta.core.material.ToolInfo;
import net.minecraft.entity.SharedMonsterAttributes;

public class SupportEssentialAlloys implements ILimitedModSupport
{
     public static ItemMaterial BRONZE;
     public static ItemMaterial MAGIC;
     public static ItemMaterial STEEL;
     
     
     
     
     @Override
     public String modid ()
     {
          return "essentialalloys";
     }
     
     
     
     
     @Override
     public void preInit ()
     {
          BRONZE = MaterialRegistry.add(new DeltaInfo("bronze", 0xd3b838, "ingotBronze", true, true, true, false), new ToolInfo(2, 418, 9.0F, 2.0F, 22), new ArmorInfo(20, new int[]
          { 3, 6, 5, 2 }, 25), new ExtraInfo(new SimpleEntry<String, Double>(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), 0.01D)));
          MAGIC = MaterialRegistry.add(new DeltaInfo("magic", 0x7340ad, "ingotMagic", true, true, false, true), new ToolInfo(3, 205, 12.0F, 1.0F, 44), null, null);
          STEEL = MaterialRegistry.add(new DeltaInfo("steel", 0x637080, "ingotSteel", true, true, true, false), new ToolInfo(4, 1111, 4.0F, 3.0F, 1), new ArmorInfo(30, new int[]
          { 2, 10, 5, 3 }, 1), new ExtraInfo(new SimpleEntry<String, Double>(SharedMonsterAttributes.movementSpeed.getAttributeUnlocalizedName(), -0.01D)));
     }
     
     
     
     
     @Override
     public void postInit ()
     {
          
     }
}
