package mcdelta.core.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleHammerSmash extends EntityFX
{
     private int currentLife;
     private int maximumLife;
     private int radius;
     private int blockID;
     
     
     
     
     public ParticleHammerSmash (World world, double x, double y, double z, int r, int id)
     {
          super(world, x, y, z);
          
          this.worldObj = world;
          
          this.posX = x;
          this.posY = y;
          this.posZ = z;
          
          this.radius = r;
          
          this.blockID = id;
     }
     
     
     
     
     public void onUpdate ()
     {
          for (int i = 0; i < 150; ++i)
          {
               double xOffset = (rand.nextInt(radius * 2) - radius) + rand.nextDouble();
               double yOffset = rand.nextDouble();
               double zOffset = (rand.nextInt(radius * 2) - radius) + rand.nextDouble();
               
               double motionX = xOffset;
               double motionY = yOffset;
               double motionZ = zOffset;
               
               int id = this.worldObj.getBlockId(((int) (this.posX + xOffset)), ((int) this.posY - 1), ((int) (this.posZ + zOffset)));
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
                    int radiusClose = 2;
                    
                    double xOffset = rand.nextInt(radiusClose * 2) - radiusClose;
                    double yOffset = rand.nextInt(radiusClose * 2) - radiusClose;
                    double zOffset = rand.nextInt(radiusClose * 2) - radiusClose;
                    
                    double motionX = xOffset;
                    double motionY = yOffset;
                    double motionZ = zOffset;
                    
                    this.worldObj.spawnParticle("cloud", this.posX + xOffset, this.posY, this.posZ + zOffset, 0, 0, 0);
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
