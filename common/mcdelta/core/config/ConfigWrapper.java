package mcdelta.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

/**
 * This is effectively a wrapper for Forge Configurations. It allows for easier manipulation of Config files.
 */
public class ConfigWrapper
{
    public static final String CATEGORY_ENCHANT = "enchantment";
    public static final String CATEGORY_POTION = "potion";

    public static final Map<String, Integer> blockIds = new HashMap<String, Integer>();
    public static final Map<String, Integer> itemIds = new HashMap<String, Integer>();
    public static final Map<String, Integer> enchantIds = new HashMap<String, Integer>();
    public static final Map<String, Integer> potionIds = new HashMap<String, Integer>();

    private Configuration config;

    private int blockIdStart;
    private int itemIdStart;
    private int enchantIdStart;
    private int potionIdStart;

    public ConfigWrapper()
    {
        blockIdStart = 1000;
        itemIdStart = 5000;
        enchantIdStart = 52;
        potionIdStart = 0;
    }

    public ConfigWrapper(final int blockStart, final int itemStart, final int enchantStart)
    {
        blockIdStart = blockStart;
        itemIdStart = itemStart;
        enchantIdStart = enchantStart;
    }

    public void setConfiguration(final Configuration config)
    {
        this.config = config;
    }

    public Configuration getConfiguration()
    {
        return config;
    }

    public int getBlockID(final String name)
    {
        if (!blockIds.containsKey(name))
        {
            load();
            if (!config.hasKey(Configuration.CATEGORY_BLOCK, name))
            {
                for (int id = blockIdStart; id < Block.blocksList.length; ++id)
                {
                    if (Block.blocksList[id] == null && !blockIds.containsValue(Integer.valueOf(id)))
                    {
                        blockIdStart = blockIdStart == id ? blockIdStart + 1 : id;
                        blockIds.put(name, config.getBlock(name, id).getInt());
                        break;
                    }
                }
                save();
            } else
            {
                blockIds.put(name, config.getCategory(Configuration.CATEGORY_BLOCK).getValues().get(name).getInt());
            }
        }
        return blockIds.get(name);
    }

    public int getItemID(final String name)
    {
        if (!itemIds.containsKey(name))
        {
            load();
            if (!config.hasKey(Configuration.CATEGORY_ITEM, name))
            {
                for (int id = itemIdStart; id < Item.itemsList.length; ++id)
                {
                    if (Item.itemsList[id] == null && !itemIds.containsValue(Integer.valueOf(id)))
                    {
                        itemIdStart = itemIdStart == id ? itemIdStart + 1 : id;
                        itemIds.put(name, config.getItem(name, id).getInt());
                        break;
                    }
                }
                save();
            } else
            {
                itemIds.put(name, config.getCategory(Configuration.CATEGORY_ITEM).getValues().get(name).getInt());
            }
        }
        return itemIds.get(name);
    }

    public int getEnchantmentID(final String name)
    {
        if (!enchantIds.containsKey(name))
        {
            load();
            if (!config.hasKey(CATEGORY_ENCHANT, name))
            {
                for (int id = enchantIdStart; id < Enchantment.enchantmentsList.length; ++id)
                {
                    if (Enchantment.enchantmentsList[id] == null && !enchantIds.containsValue(Integer.valueOf(id)))
                    {
                        enchantIdStart = enchantIdStart == id ? enchantIdStart + 1 : id;
                        enchantIds.put(name, config.get(CATEGORY_ENCHANT, name, id).getInt());
                        break;
                    }
                }
                save();
            } else
            {
                enchantIds.put(name, config.getCategory(CATEGORY_ENCHANT).getValues().get(name).getInt());
            }
        }
        return enchantIds.get(name);
    }

    public int getPotionID(final String name)
    {
        if (!potionIds.containsKey(name))
        {
            load();
            if (!config.hasKey(CATEGORY_POTION, name))
            {
                for (int id = potionIdStart; id < Potion.potionTypes.length; ++id)
                {
                    if (Potion.potionTypes[id] == null && !potionIds.containsValue(Integer.valueOf(id)))
                    {
                        potionIdStart = potionIdStart == id ? potionIdStart + 1 : id;
                        potionIds.put(name, config.get(CATEGORY_POTION, name, id).getInt());
                        break;
                    }
                }
                save();
            } else
            {
                potionIds.put(name, config.getCategory(CATEGORY_POTION).getValues().get(name).getInt());
            }
        }
        return potionIds.get(name);
    }

    public int get(final String category, final String key, final int defaultValue)
    {
        return config.get(category, key, defaultValue).getInt();
    }

    public boolean get(final String category, final String key, final boolean defaultValue)
    {
        return config.get(category, key, defaultValue).getBoolean(defaultValue);
    }

    public String get(final String category, final String key, final String defaultValue)
    {
        return config.get(category, key, defaultValue).getString();
    }

    public Property getProperty(final String category, final String key, final int defaultValue)
    {
        return config.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final boolean defaultValue)
    {
        return config.get(category, key, defaultValue);
    }

    public Property getProperty(final String category, final String key, final String defaultValue)
    {
        return config.get(category, key, defaultValue);
    }

    public ConfigCategory getCategory(final String category)
    {
        return config.getCategory(category);
    }

    public Map<?, ?> getCategoryMap(final String category)
    {
        return config.getCategory(category).getValues();
    }

    public Set<String> getCategoryKeys(final String category)
    {
        return config.getCategory(category).getValues().keySet();
    }

    public boolean hasCategory(final String category)
    {
        return config.hasCategory(category);
    }

    public boolean hasKey(final String category, final String key)
    {
        return config.hasKey(category, key);
    }

    public void save()
    {
        config.save();
    }

    public void load()
    {
        config.load();
    }

    public boolean renameProperty(final String category, final String key, final String newCategory, final String newKey, final boolean forceValue)
    {
        if (config.hasKey(category, key))
        {
            final Property prop = config.getCategory(category).get(key);

            if (prop.isIntValue())
            {
                final int value = config.getCategory(category).getValues().get(key).getInt();
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            } else if (prop.isBooleanValue())
            {
                final boolean value = config.getCategory(category).getValues().get(key).getBoolean(false);
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            } else if (prop.isDoubleValue())
            {
                final double value = config.getCategory(category).getValues().get(key).getDouble(0.0);
                removeProperty(category, key);

                if (forceValue)
                {
                    removeProperty(newCategory, newKey);
                }
                config.get(newCategory, newKey, value);
            } else
            {
                final String value = config.getCategory(category).getValues().get(key).getString();
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

    public boolean removeProperty(final String category, final String key)
    {
        if (!config.hasKey(category, key))
        {
            return false;
        }
        config.getCategory(category).remove(key);
        return true;
    }

    public boolean renameCategory(final String category, final String newCategory)
    {
        if (!config.hasCategory(category))
        {
            return false;
        }
        for (final Property prop : config.getCategory(category).values())
        {
            renameProperty(category, prop.getName(), newCategory, prop.getName(), true);
        }
        removeCategory(category);
        return true;
    }

    public boolean removeCategory(final String category)
    {
        if (!config.hasCategory(category))
        {
            return false;
        }
        config.removeCategory(config.getCategory(category));
        return true;
    }
}
