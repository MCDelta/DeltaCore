package mcdelta.core.material;

public final class ToolInfo
{
     private final int   harvest;
     private final int   uses;
     private final float efficiency;
     private final float damage;
     private final int   enchant;
     
     
     
     
     public ToolInfo (final int harvest, final int uses, final float efficiency, final float damage, final int enchant)
     {
          this.harvest = harvest;
          this.uses = uses;
          this.efficiency = efficiency;
          this.damage = damage;
          this.enchant = enchant;
     }
     
     
     
     
     public int harvestLvL ()
     {
          return harvest;
     }
     
     
     
     
     public int maxUses ()
     {
          return uses;
     }
     
     
     
     
     public float efficiency ()
     {
          return efficiency;
     }
     
     
     
     
     public float damageVsEntity ()
     {
          return damage;
     }
     
     
     
     
     public int enchantability ()
     {
          return enchant;
     }
}
