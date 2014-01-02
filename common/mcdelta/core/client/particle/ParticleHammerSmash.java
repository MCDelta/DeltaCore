package mcdelta.core.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleHammerSmash extends EntityFX
{
     private int       currentLife;
     private int       maximumLife;
     private final int radius;
     
     
     
     
     public ParticleHammerSmash (final World world, final double x, final double y, final double z, final int radi)
     {
          super(world, x, y, z);
          
          this.worldObj = world;
          
          this.posX = x;
          this.posY = y;
          this.posZ = z;
          
          this.radius = radi;
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          for (int i = 0; i < 150; ++i)
          {
               final double xOffset = this.rand.nextInt(this.radius * 2) - this.radius + this.rand.nextDouble();
               final double yOffset = this.rand.nextDouble();
               final double zOffset = this.rand.nextInt(this.radius * 2) - this.radius + this.rand.nextDouble();
               
               final double motionX = xOffset;
               final double motionY = yOffset;
               final double motionZ = zOffset;
               
               final int id = this.worldObj.getBlockId((int) (this.posX + xOffset), (int) this.posY - 1, (int) (this.posZ + zOffset));
               boolean flag = true;
               
               if (id == 0)
               {
                    flag = false;
               }
               if (flag)
               {
                    this.worldObj.spawnParticle("tilecrack_" + id + "_0", this.posX + xOffset, this.posY + yOffset, this.posZ + zOffset, motionX, motionY, motionZ);
               }
          }
          
          if (this.currentLife == this.maximumLife)
          {
               for (int i = 0; i < 10; ++i)
               {
                    final int radiusClose = 2;
                    
                    final double xOffset = this.rand.nextInt(radiusClose * 2) - radiusClose;
                    final double zOffset = this.rand.nextInt(radiusClose * 2) - radiusClose;
                    
                    this.worldObj.spawnParticle("cloud", this.posX + xOffset, this.posY, this.posZ + zOffset, 0, 0, 0);
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
