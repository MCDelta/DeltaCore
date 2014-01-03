package mcdelta.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleCriticalHit extends EntityFX
{
     private int           currentLife;
     private final Entity  theEntity;
     private final int     maximumLife;
     private final int     particlePerTick;
     private final boolean isMagic;
     
     
     
     
     public ParticleCriticalHit (final Entity target, final int color)
     {
          this(target.worldObj, target, target.posX, target.posY, target.posZ, (color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, 3, 16, false);
     }
     
     
     
     
     public ParticleCriticalHit (final Entity target, final int color, final int maxLife, final int ppt, final boolean magic)
     {
          this(target.worldObj, target, target.posX, target.posY, target.posZ, (color >> 16 & 255) / 255.0F, (color >> 8 & 255) / 255.0F, (color & 255) / 255.0F, maxLife, ppt, magic);
     }
     
     
     
     
     public ParticleCriticalHit (final World world, final Entity target, final double x, final double y, final double z, final float r, final float g, final float b, final int maxLife, final int ppt, final boolean magic)
     {
          super(world, x, y, z);
          
          this.worldObj = world;
          
          this.posX = x;
          this.posY = y;
          this.posZ = z;
          
          this.particleRed = r;
          this.particleGreen = g;
          this.particleBlue = b;
          
          this.maximumLife = maxLife;
          this.particlePerTick = ppt;
          
          this.theEntity = target;
          
          this.isMagic = magic;
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          for (int i = 0; i < this.particlePerTick; ++i)
          {
               final double d0 = this.rand.nextFloat() * 2.0F - 1.0F;
               final double d1 = this.rand.nextFloat() * 2.0F - 1.0F;
               final double d2 = this.rand.nextFloat() * 2.0F - 1.0F;
               
               if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D)
               {
                    final double d3 = this.theEntity.posX + d0 * this.theEntity.width / 4.0D;
                    final double d4 = this.theEntity.boundingBox.minY + this.theEntity.height / 2.0F + d1 * this.theEntity.height / 4.0D;
                    final double d5 = this.theEntity.posZ + d2 * this.theEntity.width / 4.0D;
                    
                    final EntityFX fx = new EntityCritFX(this.worldObj, d3, d4, d5, d0, d1 + 0.2D, d2);
                    fx.setRBGColorF(this.particleRed, this.particleGreen, this.particleBlue);
                    
                    if (this.isMagic)
                    {
                         fx.nextTextureIndexX();
                    }
                    Minecraft.getMinecraft().effectRenderer.addEffect(fx);
               }
          }
          ++this.currentLife;
          
          if (this.currentLife >= this.maximumLife)
          {
               this.setDead();
          }
     }
     
     
     
     
     @Override
     public int getFXLayer ()
     {
          return 3;
     }
}
