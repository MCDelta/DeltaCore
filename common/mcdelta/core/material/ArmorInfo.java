package mcdelta.core.material;

public final class ArmorInfo
{
     private final int   uses;
     private final int[] shieldBarArray;
     private final int   enchantability;
     
     
     
     
     public ArmorInfo (final int uses, final int[] shieldBarArray, final int enchantability)
     {
          this.uses = uses;
          this.shieldBarArray = shieldBarArray;
          this.enchantability = enchantability;
     }
     
     
     
     
     public int maxUses ()
     {
          return uses;
     }
     
     
     
     
     public int[] shieldBarArray ()
     {
          return shieldBarArray;
     }
     
     
     
     
     public int enchantability ()
     {
          return enchantability;
     }
}
