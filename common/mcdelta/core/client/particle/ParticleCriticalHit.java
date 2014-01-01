package mcdelta.core.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityCritFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleCriticalHit extends EntityFX
{

    private int currentLife;
    private final Entity theEntity;
    private final int maximumLife;
    private final int particlePerTick;
    private final boolean isMagic;

    public ParticleCriticalHit(Entity target, int color)
    {
        this(target.worldObj, target, target.posX, target.posY, target.posZ, ((color >> 16) & 255) / 255.0F, ((color >> 8) & 255) / 255.0F, (color & 255) / 255.0F, 3, 16, false);
    }

    public ParticleCriticalHit(Entity target, int color, int i, int ii, boolean b)
    {
        this(target.worldObj, target, target.posX, target.posY, target.posZ, ((color >> 16) & 255) / 255.0F, ((color >> 8) & 255) / 255.0F, (color & 255) / 255.0F, i, ii, b);
    }

    public ParticleCriticalHit(World world, Entity target, double x, double y, double z, float r, float g, float b, int i, int ii, boolean bool)
    {
        super(world, x, y, z);

        worldObj = world;

        posX = x;
        posY = y;
        posZ = z;

        particleRed = r;
        particleGreen = g;
        particleBlue = b;

        maximumLife = i;
        particlePerTick = ii;

        theEntity = target;

        isMagic = bool;
    }

    @Override
    public void onUpdate()
    {
        for (int i = 0; i < particlePerTick; ++i)
        {
            double d0 = (rand.nextFloat() * 2.0F) - 1.0F;
            double d1 = (rand.nextFloat() * 2.0F) - 1.0F;
            double d2 = (rand.nextFloat() * 2.0F) - 1.0F;

            if (((d0 * d0) + (d1 * d1) + (d2 * d2)) <= 1.0D)
            {
                double d3 = theEntity.posX + ((d0 * theEntity.width) / 4.0D);
                double d4 = theEntity.boundingBox.minY + (theEntity.height / 2.0F) + ((d1 * theEntity.height) / 4.0D);
                double d5 = theEntity.posZ + ((d2 * theEntity.width) / 4.0D);

                EntityFX fx = new EntityCritFX(worldObj, d3, d4, d5, d0, d1 + 0.2D, d2);
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
    public int getFXLayer()
    {
        return 3;
    }
}
