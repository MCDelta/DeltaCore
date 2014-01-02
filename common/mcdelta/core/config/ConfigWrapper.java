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
 * This is effectively a wrapper for Forge Configurations. It allows for easier
 * manipulation of Config files.
 */
public class ConfigWrapper
{
     public static final String CATEGORY_ENCHANT = "enchantment";
     
     private Configuration      config;
     
     private final int          blockIdStart;
     private final int          itemIdStart;
     private final int          enchantIdStart;
     
     
     
     
     public ConfigWrapper ()
     {
          this.blockIdStart = 1000;
          this.itemIdStart = 5000;
          this.enchantIdStart = 52;
     }
     
     
     
     
     public ConfigWrapper (final int blockStart, final int itemStart, final int enchantStart)
     {
          this.blockIdStart = blockStart;
          this.itemIdStart = itemStart;
          this.enchantIdStart = enchantStart;
     }
     
     
     
     
     public void setConfiguration (final Configuration config)
     {
          this.config = config;
     }
     
     
     
     
     public Configuration getConfiguration ()
     {
          return this.config;
     }
     
     
     
     
     public int getBlockID (final String name)
     {
          this.load();
          if (!this.config.hasKey(Configuration.CATEGORY_BLOCK, name))
          {
               for (int id = this.blockIdStart; id < Block.blocksList.length; ++id)
               {
                    if (Block.blocksList[id] == null)
                    {
                         this.config.getBlock(name, id);
                         break;
                    }
               }
               this.save();
          }
          return this.config.getCategory(Configuration.CATEGORY_BLOCK).getValues().get(name).getInt();
     }
     
     
     
     
     public int getItemID (final String name)
     {
          this.load();
          if (!this.config.hasKey(Configuration.CATEGORY_ITEM, name))
          {
               for (int id = this.itemIdStart; id < Item.itemsList.length; ++id)
               {
                    if (Item.itemsList[id] == null)
                    {
                         this.config.getItem(name, id);
                         break;
                    }
               }
               this.save();
          }
          return this.config.getCategory(Configuration.CATEGORY_ITEM).getValues().get(name).getInt();
     }
     
     
     
     
     public int getEnchantmentID (final String name)
     {
          this.load();
          if (!this.config.hasKey(CATEGORY_ENCHANT, name))
          {
               for (int id = this.enchantIdStart; id < Enchantment.enchantmentsList.length; ++id)
               {
                    if (Enchantment.enchantmentsList[id] == null)
                    {
                         this.config.get(CATEGORY_ENCHANT, name, id);
                         break;
                    }
               }
               this.save();
          }
          return this.config.getCategory(CATEGORY_ENCHANT).getValues().get(name).getInt();
     }
     
     
     
     
     public int get (final String category, final String key, final int defaultValue)
     {
          return this.config.get(category, key, defaultValue).getInt();
     }
     
     
     
     
     public boolean get (final String category, final String key, final boolean defaultValue)
     {
          return this.config.get(category, key, defaultValue).getBoolean(defaultValue);
     }
     
     
     
     
     public String get (final String category, final String key, final String defaultValue)
     {
          return this.config.get(category, key, defaultValue).getString();
     }
     
     
     
     
     public Property getProperty (final String category, final String key, final int defaultValue)
     {
          return this.config.get(category, key, defaultValue);
     }
     
     
     
     
     public Property getProperty (final String category, final String key, final boolean defaultValue)
     {
          return this.config.get(category, key, defaultValue);
     }
     
     
     
     
     public Property getProperty (final String category, final String key, final String defaultValue)
     {
          return this.config.get(category, key, defaultValue);
     }
     
     
     
     
     public ConfigCategory getCategory (final String category)
     {
          return this.config.getCategory(category);
     }
     
     
     
     
     public Map<?, ?> getCategoryMap (final String category)
     {
          return this.config.getCategory(category).getValues();
     }
     
     
     
     
     public Set<String> getCategoryKeys (final String category)
     {
          return this.config.getCategory(category).getValues().keySet();
     }
     
     
     
     
     public boolean hasCategory (final String category)
     {
          return this.config.hasCategory(category);
     }
     
     
     
     
     public boolean hasKey (final String category, final String key)
     {
          return this.config.hasKey(category, key);
     }
     
     
     
     
     public void save ()
     {
          if (this.config.hasChanged())
          {
               this.config.save();
          }
     }
     
     
     
     
     public void load ()
     {
          this.config.load();
     }
     
     
     
     
     public boolean renameProperty (final String category, final String key, final String newCategory, final String newKey, final boolean forceValue)
     {
          if (this.config.hasKey(category, key))
          {
               final Property prop = this.config.getCategory(category).get(key);
               
               if (prop.isIntValue())
               {
                    final int value = this.config.getCategory(category).getValues().get(key).getInt();
                    this.removeProperty(category, key);
                    
                    if (forceValue)
                    {
                         this.removeProperty(newCategory, newKey);
                    }
                    this.config.get(newCategory, newKey, value);
               }
               else if (prop.isBooleanValue())
               {
                    final boolean value = this.config.getCategory(category).getValues().get(key).getBoolean(false);
                    this.removeProperty(category, key);
                    
                    if (forceValue)
                    {
                         this.removeProperty(newCategory, newKey);
                    }
                    this.config.get(newCategory, newKey, value);
               }
               else if (prop.isDoubleValue())
               {
                    final double value = this.config.getCategory(category).getValues().get(key).getDouble(0.0);
                    this.removeProperty(category, key);
                    
                    if (forceValue)
                    {
                         this.removeProperty(newCategory, newKey);
                    }
                    this.config.get(newCategory, newKey, value);
               }
               else
               {
                    final String value = this.config.getCategory(category).getValues().get(key).getString();
                    this.removeProperty(category, key);
                    
                    if (forceValue)
                    {
                         this.removeProperty(newCategory, newKey);
                    }
                    this.config.get(newCategory, newKey, value);
               }
               return true;
          }
          return false;
     }
     
     
     
     
     public boolean removeProperty (final String category, final String key)
     {
          if (!this.config.hasKey(category, key))
          {
               return false;
          }
          this.config.getCategory(category).remove(key);
          return true;
     }
     
     
     
     
     public boolean renameCategory (final String category, final String newCategory)
     {
          if (!this.config.hasCategory(category))
          {
               return false;
          }
          for (final Property prop : this.config.getCategory(category).values())
          {
               this.renameProperty(category, prop.getName(), newCategory, prop.getName(), true);
          }
          this.removeCategory(category);
          return true;
     }
     
     
     
     
     public boolean removeCategory (final String category)
     {
          if (!this.config.hasCategory(category))
          {
               return false;
          }
          this.config.removeCategory(this.config.getCategory(category));
          return true;
     }
}
