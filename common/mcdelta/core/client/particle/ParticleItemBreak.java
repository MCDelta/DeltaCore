package mcdelta.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class ParticleItemBreak extends EntityFX
{

    private final Entity target;
    private final ItemStack stack;
    private int currentLife;
    private final int maximumLife;
    private final int particlesPerTick;

    public ParticleItemBreak(Entity entity, ItemStack item, int i, int ii)
    {
        this(entity.worldObj, entity, entity.posX, entity.posY, entity.posZ, item, i, ii);
    }

    protected ParticleItemBreak(World world, Entity entity, double x, double y, double z, ItemStack item, int i, int ii)
    {
        super(world, x, y, z);

        worldObj = world;

        posX = x;
        posY = y;
        posZ = z;

        stack = item;

        maximumLife = i;
        particlesPerTick = ii;

        target = entity;
    }

    @Override
    public void onUpdate()
    {
        for (int i = 0; i < particlesPerTick; ++i)
        {
            Vec3 vec3 = target.worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.1D, (Math.random() * 0.1D) + 0.1D, 0.0D);
            vec3.rotateAroundX((-target.rotationPitch * (float) Math.PI) / 180.0F);
            vec3.rotateAroundY((-target.rotationYaw * (float) Math.PI) / 180.0F);
            Vec3 vec31 = target.worldObj.getWorldVec3Pool().getVecFromPool((rand.nextFloat() - 0.5D) * 0.3D, ((-rand.nextFloat()) * 0.6D) - 0.3D, 0.6D);
            vec31.rotateAroundX((-target.rotationPitch * (float) Math.PI) / 180.0F);
            vec31.rotateAroundY((-target.rotationYaw * (float) Math.PI) / 180.0F);
            vec31 = vec31.addVector(target.posX, target.posY + target.getEyeHeight(), target.posZ);

            EntityFX fx = new FXItemBreak(worldObj, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord, stack, 0);

            Minecraft.getMinecraft().effectRenderer.addEffect(fx);
        }

        ++currentLife;

        if (currentLife >= maximumLife)
        {
            setDead();
        }
    }

    @Override
    public int getFXLayer()
    {
        return 3;
    }
}
