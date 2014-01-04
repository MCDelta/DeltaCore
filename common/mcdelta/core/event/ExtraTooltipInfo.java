package mcdelta.core.event;

import mcdelta.core.config.Settings;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.oredict.OreDictionary;

public class ExtraTooltipInfo
{
     @ForgeSubscribe
     public void getTooltip (final ItemTooltipEvent event)
     {
          if (Settings.OREDIC_TOOLTIP && OreDictionary.getOreName(OreDictionary.getOreID(event.itemStack)) != null && !OreDictionary.getOreName(OreDictionary.getOreID(event.itemStack)).equals("Unknown"))
          {
               event.toolTip.add(OreDictionary.getOreName(OreDictionary.getOreID(event.itemStack)));
          }
     }
}
