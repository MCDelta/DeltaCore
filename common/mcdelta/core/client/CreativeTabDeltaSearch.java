package mcdelta.core.client;

import java.util.HashMap;
import java.util.List;

import mcdelta.core.DeltaCore;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.common.registry.GameData;
import cpw.mods.fml.common.registry.ItemData;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Code for finding Item modids from ModIdentification in ProfessorMobius's
 * brilliant WAILA
 */
public class CreativeTabDeltaSearch extends CreativeTabDelta
{
     public static HashMap<Integer, String> itemMap = new HashMap<Integer, String>();
     
     
     
     
     public CreativeTabDeltaSearch ()
     {
          super("deltacore:search");
          
          NBTTagList itemDataList = new NBTTagList();
          GameData.writeItemData(itemDataList);
          
          for (int i = 0; i < itemDataList.tagCount(); i++)
          {
               ItemData itemData = new ItemData((NBTTagCompound) itemDataList.tagAt(i));
               itemMap.put(itemData.getItemId(), itemData.getModId());
          }
          
          setBackgroundImageName("item_search.png");
          iconStack = new ItemStack(Item.map);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void displayAllReleventItems (List list)
     {
          Item[] aitem = Item.itemsList;
          int i = aitem.length;
          
          for (int j = 0; j < i; ++j)
          {
               Item item = aitem[j];
               
               if (item == null)
               {
                    continue;
               }
               
               if (item.getCreativeTab() == null)
               {
                    continue;
               }
               
               String modID = itemMap.get(item.itemID);
               
               if (modID.equals(DeltaCore.MOD_ID))
               {
                    item.getSubItems(item.itemID, this, list);
               }
          }
     }
     
     
     
     
     public boolean hasSearchBar ()
     {
          return true;
     }
}
