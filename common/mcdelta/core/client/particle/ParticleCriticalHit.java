package mcdelta.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleCriticalHit extends EntityFX
{
     
     private int     currentLife;
     private Entity  theEntity;
     private int     maximumLife;
     private int     particlePerTick;
     private boolean isMagic;
     
     
     
     
     public ParticleCriticalHit (Entity target, int color)
     {
          this(target.worldObj, target, target.posX, target.posY, target.posZ, (float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, 3, 16, false);
     }
     
     
     
     
     public ParticleCriticalHit (Entity target, int color, int i, int ii, boolean b)
     {
          this(target.worldObj, target, target.posX, target.posY, target.posZ, (float) (color >> 16 & 255) / 255.0F, (float) (color >> 8 & 255) / 255.0F, (float) (color & 255) / 255.0F, i, ii, b);
     }
     
     
     
     
     public ParticleCriticalHit (World world, Entity target, double x, double y, double z, float r, float g, float b, int i, int ii, boolean bool)
     {
          super(world, x, y, z);
          
          this.worldObj = world;
          
          this.posX = x;
          this.posY = y;
          this.posZ = z;
          
          this.particleRed = r;
          this.particleGreen = g;
          this.particleBlue = b;
          
          this.maximumLife = i;
          this.particlePerTick = ii;
          
          this.theEntity = target;
          
          this.isMagic = bool;
     }
     
     
     
     
     public void onUpdate ()
     {
          for (int i = 0; i < particlePerTick; ++i)
          {
               double d0 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
               double d1 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
               double d2 = (double) (this.rand.nextFloat() * 2.0F - 1.0F);
               
               if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D)
               {
                    double d3 = this.theEntity.posX + d0 * (double) this.theEntity.width / 4.0D;
                    double d4 = this.theEntity.boundingBox.minY + (double) (this.theEntity.height / 2.0F) + d1 * (double) this.theEntity.height / 4.0D;
                    double d5 = this.theEntity.posZ + d2 * (double) this.theEntity.width / 4.0D;
                    
                    EntityFX fx = new EntityCritFX(this.worldObj, d3, d4, d5, d0, d1 + 0.2D, d2);
                    ((EntityFX) fx).setRBGColorF(particleRed, particleGreen, particleBlue);
                    
                    if (isMagic)
                    {
                         ((EntityFX) fx).nextTextureIndexX();
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
     
     
     
     
     public int getFXLayer ()
     {
          return 3;
     }
}
