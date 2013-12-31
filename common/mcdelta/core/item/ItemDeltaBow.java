package mcdelta.core.item;

import mcdelta.core.DeltaCore;
import mcdelta.core.EnumMCDMods;
import mcdelta.core.client.item.IExtraPasses;
import mcdelta.core.proxy.ClientProxy;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

public class ItemDeltaBow extends ItemBow
{
    public EnumMCDMods mod;
    public String name;
    public Item[] ammo;

    public ItemDeltaBow(EnumMCDMods m, String s, Item[] arr)
    {
        super(DeltaCore.config.getItemID(m, s));

        ammo = arr;

        // ItemDelta code
        mod = m;
        name = s;
        String unlocalized = mod.modid.toLowerCase() + ":" + name;
        setUnlocalizedName(unlocalized);

        if (!StatCollector.func_94522_b("item." + unlocalized + ".name"))
        {
            DeltaCore.localizationWarnings.append("- item." + unlocalized + ".name \n");
        }

        if (this instanceof IExtraPasses)
        {
            ClientProxy.extraPasses.add(this);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        boolean flag = player.capabilities.isCreativeMode;

        for (Item item : ammo)
        {
            if (player.inventory.hasItem(item.itemID))
            {
                flag = true;
            }
        }

        if (flag)
        {
            ArrowNockEvent event = new ArrowNockEvent(player, stack);
            MinecraftForge.EVENT_BUS.post(event);
            if (event.isCanceled())
            {
                return event.result;
            }

            player.setItemInUse(stack, getMaxItemUseDuration(stack));
        }

        return stack;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.bow;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }
}
