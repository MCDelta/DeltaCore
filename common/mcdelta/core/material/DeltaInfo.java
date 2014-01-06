package mcdelta.core.material;

public final class DeltaInfo
{
     private final String  name;
     private final int     color;
     private final String  ore;
     private final boolean needsTools;
     private final boolean needsWeapons;
     private final boolean needsArmor;
     private final boolean defaultShiny;
     
     
     
     
     public DeltaInfo (final String name, final int color, final String ore, final boolean needsTools, final boolean needsWeapons, final boolean needsArmor, final boolean defaultShiny)
     {
          this.name = name;
          this.color = color;
          this.ore = ore;
          this.needsTools = needsTools;
          this.needsWeapons = needsWeapons;
          this.needsArmor = needsArmor;
          this.defaultShiny = defaultShiny;
     }
     
     
     
     
     public String name ()
     {
          return name;
     }
     
     
     
     
     public int color ()
     {
          return color;
     }
     
     
     
     
     public String oreName ()
     {
          return ore;
     }
     
     
     
     
     public boolean needsTools ()
     {
          return needsTools;
     }
     
     
     
     
     public boolean needsWeapons ()
     {
          return needsWeapons;
     }
     
     
     
     
     public boolean needsArmor ()
     {
          return needsArmor;
     }
     
     
     
     
     public boolean defaultShiny ()
     {
          return defaultShiny;
     }
}
