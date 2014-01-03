package mcdelta.core.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.world.World;

public class ParticleHammerSmash extends EntityFX
{
    private int currentLife;
    private int maximumLife;
    private final int radius;

    public ParticleHammerSmash(final World world, final double x, final double y, final double z, final int radi)
    {
        super(world, x, y, z);

        worldObj = world;

        posX = x;
        posY = y;
        posZ = z;

        radius = radi;
    }

    @Override
    public void onUpdate()
    {
        for (int i = 0; i < 150; ++i)
        {
            final double xOffset = (rand.nextInt(radius * 2) - radius) + rand.nextDouble();
            final double yOffset = rand.nextDouble();
            final double zOffset = (rand.nextInt(radius * 2) - radius) + rand.nextDouble();

            final double motionX = xOffset;
            final double motionY = yOffset;
            final double motionZ = zOffset;

            final int id = worldObj.getBlockId((int) (posX + xOffset), (int) posY - 1, (int) (posZ + zOffset));
            boolean flag = true;

            if (id == 0)
            {
                flag = false;
            }
            if (flag)
            {
                worldObj.spawnParticle("tilecrack_" + id + "_0", posX + xOffset, posY + yOffset, posZ + zOffset, motionX, motionY, motionZ);
            }
        }

        if (currentLife == maximumLife)
        {
            for (int i = 0; i < 10; ++i)
            {
                final int radiusClose = 2;

                final double xOffset = rand.nextInt(radiusClose * 2) - radiusClose;
                final double zOffset = rand.nextInt(radiusClose * 2) - radiusClose;

                worldObj.spawnParticle("cloud", posX + xOffset, posY, posZ + zOffset, 0, 0, 0);
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
