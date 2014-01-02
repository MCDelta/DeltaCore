package mcdelta.core.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import mcdelta.core.DeltaCore;
import mcdelta.core.ModDelta;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class Config
{
    private final Map<String, Configuration> stringToCfg = new HashMap<String, Configuration>();
    private final Map<Configuration, String> cfgToString = new HashMap<Configuration, String>();
    public String filePath;

    private final String core = "core";
    private final String items = "items";
    private final String blocks = "blocks";
    private final String enchants = "enchants";

    public Config(FMLPreInitializationEvent event)
    {
        filePath = event.getModConfigurationDirectory().getAbsolutePath() + "/MCDelta/";

        addConfig(new String[]
        { core, items, blocks, enchants });

        initCore(getConfig(core));
        initItems(getConfig(items));
        initBlocks(getConfig(blocks));
        initEnchantments(getConfig(enchants));
    }

    private void initEnchantments(Configuration cfg)
    {
        cfg.load();

        try
        {

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        cfg.save();
    }

    private void initBlocks(Configuration cfg)
    {
        cfg.load();

        try
        {
            // DO SOMETHING
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        cfg.save();
    }

    private void initItems(Configuration cfg)
    {
        cfg.load();

        try
        {
            // DO SOMETHING
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        cfg.save();
    }

    private void initCore(Configuration cfg)
    {
        cfg.load();

        try
        {
            Class<Settings> c = Settings.class;

            //getBoolean(c, cfg, Assets.getField(c, "LOG_MASTER"), Settings.CATEGORY_LOG);
            //getBoolean(c, cfg, Assets.getField(c, "LOG_INFO"), Settings.CATEGORY_LOG);
            //getBoolean(c, cfg, Assets.getField(c, "LOG_CONFIG"), Settings.CATEGORY_LOG);
            //getBoolean(c, cfg, Assets.getField(c, "LOG_WARNING"), Settings.CATEGORY_LOG);
            //getBoolean(c, cfg, Assets.getField(c, "LOG_SEVERE"), Settings.CATEGORY_LOG);

            //getInteger(c, cfg, Assets.getField(c, "RANDOM_SEED"), Settings.CATEGORY_MISC);
            DeltaCore.rand.setSeed(Settings.RANDOM_SEED);

            //cfg.addCustomCategoryComment(Settings.CATEGORY_LOG, buildFancyComment(Settings.COMMENT_LOG, Configuration.NEW_LINE, 20));
            //cfg.addCustomCategoryComment(Settings.CATEGORY_MISC, buildFancyComment(Settings.COMMENT_MISC, Configuration.NEW_LINE, 20));
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        cfg.save();
    }

    public void addConfig(String... arr)
    {
        for (String s : arr)
        {
            addConfig(s);
        }
    }

    public void addConfig(String s)
    {
        Configuration config = new Configuration(new File(filePath + s + ".cfg"), true);

        stringToCfg.put(s, config);
        cfgToString.put(config, s);
    }

    public String getConfigName(Configuration c)
    {
        return cfgToString.get(c);
    }

    public Configuration getConfig(String s)
    {
        return stringToCfg.get(s);
    }

    private int id = 9000;

    public int getItemID(ModDelta mod, String s)
    {
        Configuration cfg = getConfig(items);

        cfg.load();

        Iterator<?> iter = cfg.getCategory(mod.id()).getValues().entrySet().iterator();
        List<Integer> occupied = new ArrayList<Integer>();

        while (iter.hasNext())
        {
            Entry<String, Property> entry = (Entry<String, Property>) iter.next();
            occupied.add(entry.getValue().getInt());
        }

        int possibleID = 0;

        while (true)
        {
            id++;

            if ((Item.itemsList[id] == null) && !occupied.contains(id))
            {
                possibleID = id;
                break;
            }
        }

        int id = cfg.get(mod.id(), s + " ID", possibleID).getInt();
        cfg.addCustomCategoryComment(mod.id(), "ingame item IDs");

        cfg.save();

        return id - 256;
    }

    public int getBlockID(ModDelta mod, String s)
    {
        Configuration cfg = getConfig(blocks);

        cfg.load();

        Iterator<?> iter = cfg.getCategory(mod.id()).getValues().entrySet().iterator();
        List<Integer> occupied = new ArrayList<Integer>();

        while (iter.hasNext())
        {
            Entry<String, Property> entry = (Entry<String, Property>) iter.next();
            occupied.add(entry.getValue().getInt());
        }

        int possibleID = 0;

        for (int i = 1; i < Block.blocksList.length; i++)
        {
            if ((Block.blocksList[i] == null) && !occupied.contains(id))
            {
                possibleID = i;
                break;
            }
        }

        int id = cfg.get(mod.id(), s + " ID", possibleID).getInt();
        cfg.addCustomCategoryComment(mod.id(), "block IDs");

        cfg.save();

        return id;
    }

    public int getEnchantmentID(ModDelta m, String s)
    {
        Configuration cfg = getConfig(enchants);

        cfg.load();

        int possibleID = 0;

        for (int i = 1; i < Enchantment.enchantmentsList.length; i++)
        {
            if (Enchantment.enchantmentsList[i] == null)
            {
                possibleID = i;
                break;
            }
        }

        int id = cfg.get(m.id(), s + " ID", possibleID).getInt();
        cfg.addCustomCategoryComment(m.id(), "enchantment IDs");

        cfg.save();

        return id;
    }
}