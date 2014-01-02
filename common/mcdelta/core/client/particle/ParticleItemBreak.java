package mcdelta.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ParticleItemBreak extends EntityFX
{
     private final Entity    target;
     private final ItemStack stack;
     private int             currentLife;
     private final int       maximumLife;
     private final int       particlesPerTick;
     
     
     
     
     public ParticleItemBreak (final Entity entity, final ItemStack item, final int maxLife, final int ppt)
     {
          this(entity.worldObj, entity, entity.posX, entity.posY, entity.posZ, item, maxLife, ppt);
     }
     
     
     
     
     protected ParticleItemBreak (final World world, final Entity entity, final double x, final double y, final double z, final ItemStack item, final int maxLife, final int ppt)
     {
          super(world, x, y, z);
          
          this.worldObj = world;
          
          this.posX = x;
          this.posY = y;
          this.posZ = z;
          
          this.stack = item;
          
          this.maximumLife = maxLife;
          this.particlesPerTick = ppt;
          
          this.target = entity;
     }
     
     
     
     
     @Override
     public void onUpdate ()
     {
          for (int i = 0; i < this.particlesPerTick; ++i)
          {
               final Vec3 vec3 = this.target.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
               vec3.rotateAroundX(-this.target.rotationPitch * (float) Math.PI / 180.0F);
               vec3.rotateAroundY(-this.target.rotationYaw * (float) Math.PI / 180.0F);
               Vec3 vec31 = this.target.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5D) * 0.3D, -this.rand.nextFloat() * 0.6D - 0.3D, 0.6D);
               vec31.rotateAroundX(-this.target.rotationPitch * (float) Math.PI / 180.0F);
               vec31.rotateAroundY(-this.target.rotationYaw * (float) Math.PI / 180.0F);
               vec31 = vec31.addVector(this.target.posX, this.target.posY + this.target.getEyeHeight(), this.target.posZ);
               
               final EntityFX fx = new FXItemBreak(this.worldObj, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, this.stack, 0);
               
               Minecraft.getMinecraft().effectRenderer.addEffect(fx);
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
