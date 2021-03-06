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
          
          worldObj = world;
          
          posX = x;
          posY = y;
          posZ = z;
          
          particleRed = r;
          particleGreen = g;
          particleBlue = b;
          
          maximumLife = maxLife;
          particlePerTick = ppt;
          
          theEntity = target;
          
          isMagic = magic;
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          for (int i = 0; i < particlePerTick; ++i)
          {
               final double d0 = rand.nextFloat() * 2.0F - 1.0F;
               final double d1 = rand.nextFloat() * 2.0F - 1.0F;
               final double d2 = rand.nextFloat() * 2.0F - 1.0F;
               
               if (d0 * d0 + d1 * d1 + d2 * d2 <= 1.0D)
               {
                    final double d3 = theEntity.posX + d0 * theEntity.width / 4.0D;
                    final double d4 = theEntity.boundingBox.minY + theEntity.height / 2.0F + d1 * theEntity.height / 4.0D;
                    final double d5 = theEntity.posZ + d2 * theEntity.width / 4.0D;
                    
                    final EntityFX fx = new EntityCritFX(worldObj, d3, d4, d5, d0, d1 + 0.2D, d2);
                    fx.setRBGColorF(particleRed, particleGreen, particleBlue);
                    
                    if (isMagic)
                    {
                         fx.nextTextureIndexX();
                    }
                    Minecraft.getMinecraft().effectRenderer.addEffect(fx);
               }
          }
          ++currentLife;
          
          if (currentLife >= maximumLife)
          {
               setDead();
          }
     }
     
     
     
     
     @Override
     public int getFXLayer ()
     {
          return 3;
     }
}
