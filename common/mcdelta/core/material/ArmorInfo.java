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
          return this.uses;
     }
     
     
     
     
     public int[] shieldBarArray ()
     {
          return this.shieldBarArray;
     }
     
     
     
     
     public int enchantability ()
     {
          return this.enchantability;
     }
}
