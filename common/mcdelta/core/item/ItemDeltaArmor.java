package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.material.ItemMaterial;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDeltaArmor extends ItemArmor implements IExtraPasses
{
     private final ItemMaterial itemMaterial;
     public ModDelta            mod;
     public String              name;
     
     
     
     
     public ItemDeltaArmor (final ModDelta mod, final ItemMaterial mat, final int type)
     {
          super(mod.config().getItemID(getArmorType(type) + "." + mat.name().toLowerCase()), mat.getArmorMaterial(), 1, type);
          
          this.mod = mod;
          name = getArmorType(type) + "." + mat.name().toLowerCase();
          final String unlocalized = mod.id().toLowerCase() + ":" + name;
          setUnlocalizedName(unlocalized);
          setCreativeTab(CreativeTabs.tabCombat);
          
          itemMaterial = mat;
          
          if (!StatCollector.func_94522_b("item." + unlocalized + ".name"))
          {
               DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
          }
          ClientProxy.extraPasses.add(this);
     }
     
     
     
     
     private static String getArmorType (final int type)
     {
          switch (type)
          {
               default:
                    return "helmet";
               case 1:
                    return "plate";
               case 2:
                    return "legs";
               case 3:
                    return "boots";
          }
     }
     
     
     
     
     @Override
     public int getPasses (final ItemStack stack)
     {
          return 1;
     }
     
     
     
     
     @Override
     public Icon getIconFromPass (final ItemStack stack, final int pass)
     {
          return itemIcon;
     }
     
     
     
     
     @Override
     public int getColorFromPass (final ItemStack stack, final int pass)
     {
          return itemMaterial.color();
     }
     
     
     
     
     @Override
     public boolean getShinyFromPass (final ItemStack stack, final int pass)
     {
          return itemMaterial.defaultShiny();
     }
     
     
     
     
     @Override
     public boolean getIsRepairable (final ItemStack repair, final ItemStack gem)
     {
          if (OreDictionary.getOres(itemMaterial.oreName()) != null && !OreDictionary.getOres(itemMaterial.oreName()).isEmpty())
          {
               return OreDictionary.itemMatches(OreDictionary.getOres(itemMaterial.oreName()).get(0), gem, false) ? true : super.getIsRepairable(repair, gem);
          }
          return super.getIsRepairable(repair, gem);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister register)
     {
          super.registerIcons(register);
          itemIcon = ItemDelta.doRegister("deltacore", "armor_" + armorType, register);
     }
     
     
     
     
     @Override
     public String getArmorTexture (final ItemStack stack, final Entity entity, final int slot, final String type)
     {
          /*
           * StringBuilder builder = new StringBuilder();
           * 
           * builder.append(Archive.MOD_ID + ":textures/models/armor/");
           * builder.append(ArmorTypes.getType(stack).getTextureName());
           * builder.append("_layer_"); switch (getPiece(stack).getType()) {
           * case 2: builder.append(2); break; default: builder.append(1);
           * break; } if (ArmorTypes.getType(stack).hasOverlay() && (type !=
           * null) && type.equalsIgnoreCase("overlay")) {
           * builder.append("_overlay"); } builder.append(".png");
           * 
           * return builder.toString();
           */
          return "";
     }
}
