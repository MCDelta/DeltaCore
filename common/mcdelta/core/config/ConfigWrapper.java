package mcdelta.core.config;

import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * This is effectively a wrapper for Forge Configurations. It allows for easier manipulation of Config files.
 */
public class ConfigWrapper
{
    public static final String CATEGORY_ENCHANT = "enchantment";

    private Configuration config;

    private final int blockIdStart;
    private final int itemIdStart;
    private final int enchantIdStart;

    public ConfigWrapper()
    {
        blockIdStart = 1000;
        itemIdStart = 5000;
        enchantIdStart = 52;
    }

    public ConfigWrapper(int blockStart, int itemStart, int enchantStart)
    {
        blockIdStart = blockStart;
        itemIdStart = itemStart;
        enchantIdStart = enchantStart;
    }

    public void setConfiguration(Configuration config)
    {
        this.config = config;
    }

    public Configuration getConfiguration()
    {
        return config;
    }

    public int getBlockID(String name)
    {
        load();
        if (!config.hasKey(Configuration.CATEGORY_BLOCK, name))
        {
            for (int id = blockIdStart; id < Block.blocksList.length; ++id)
            {
                if (Block.blocksList[id] == null)
                {
                    config.getBlock(name, id);
                    break;
                }
            }
            save();
        }
        return config.getCategory(Configuration.CATEGORY_BLOCK).getValues().get(name).getInt();
    }

    public int getItemID(String name)
    {
        load();
        if (!config.hasKey(Configuration.CATEGORY_ITEM, name))
        {
            for (int id = itemIdStart; id < Item.itemsList.length; ++id)
            {
                if (Item.itemsList[id] == null)
                {
                    config.getItem(name, id);
                    break;
                }
            }
            save();
        }
        return config.getCategory(Configuration.CATEGORY_ITEM).getValues().get(name).getInt();
    }

    public int getEnchantmentID(String name)
    {
        load();
        if (!config.hasKey(CATEGORY_ENCHANT, name))
        {
            for (int id = enchantIdStart; id < Enchantment.enchantmentsList.length; ++id)
            {
                if (Enchantment.enchantmentsList[id] == null)
                {
                    config.get(CATEGORY_ENCHANT, name, id);
                    break;
                }
            }
            save();
        }
        return config.getCategory(CATEGORY_ENCHANT).getValues().get(name).getInt();
    }

    public int get(String category, String key, int defaultValue)
    {
        return config.get(category, key, defaultValue).getInt();
    }

    public boolean get(String category, String key, boolean defaultValue)
    {
        return config.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    public String get(String category, String key, String defaultValue)
    {
        return config.get(category, key, defaultValue).getString();
    }

    public Property getProperty(String category, String key, int defaultValue)
    {
        return config.get(category, key, defaultValue);
    }

    public Property getProperty(String category, String key, boolean defaultValue)
    {
        return config.get(category, key, defaultValue);
    }

    public Property getProperty(String category, String key, String defaultValue)
    {
        return config.get(category, key, defaultValue);
    }

    public ConfigCategory getCategory(String category)
    {
        return config.getCategory(category);
    }

    public Map<?, ?> getCategoryMap(String category)
    {
        return config.getCategory(category).getValues();
    }

    public Set<String> getCategoryKeys(String category)
    {
        return config.getCategory(category).getValues().keySet();
    }

    public boolean hasCategory(String category)
    {
        return config.hasCategory(category);
    }

    public boolean hasKey(String category, String key)
    {
        return config.hasKey(category, key);
    }

    public void save()
    {
        if (config.hasChanged())
        {
            config.save();
        }
    }

    public void load()
    {
        config.load();
    }

    public boolean renameProperty(String category, String key, String newCategory, String newKey, boolean forceValue)
    {
        if (config.hasKey(category, key))
        {
            Property prop = config.getCategory(category).get(key);

            if (prop.isIntValue())
            {
                int value = config.getCategory(category).getValues().get(key).getInt();
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            } else if (prop.isBooleanValue())
            {
                boolean value = config.getCategory(category).getValues().get(key).getBoolean(false);
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            } else if (prop.isDoubleValue())
            {
                double value = config.getCategory(category).getValues().get(key).getDouble(0.0);
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            } else
            {
                String value = config.getCategory(category).getValues().get(key).getString();
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            }
            return true;
        }
        return false;
    }

    public boolean removeProperty(String category, String key)
    {
        if (!config.hasKey(category, key))
        {
            return false;
        }
        config.getCategory(category).remove(key);
        return true;
    }

    public boolean renameCategory(String category, String newCategory)
    {
        if (!config.hasCategory(category))
        {
            return false;
        }
        for (Property prop : config.getCategory(category).values())
        {
            renameProperty(category, prop.getName(), newCategory, prop.getName(), true);
        }
        removeCategory(category);
        return true;
    }

    public boolean removeCategory(String category)
    {
        if (!config.hasCategory(category))
        {
            return false;
        }
        config.removeCategory(config.getCategory(category));
        return true;
    }
}